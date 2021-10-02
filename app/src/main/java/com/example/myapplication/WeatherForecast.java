package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends Fragment implements AdapterView.OnItemSelectedListener
{
    protected static final String ACTIVITY_NAME = "WeatherForecastFragment";
    ImageView imageView;
    TextView curTempTextView, minTempTextView, maxTempTextView;
    ProgressBar progressBar;
    Spinner spinner;
    String selection = "ottawa,ca";

    private TextView mTextView;
    private FragmentActivity mFrgAct;
    private Intent mIntent;
    private LinearLayout mLinearLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "in onCreateView");
        View root = inflater.inflate(R.layout.activity_weather_forecast, null);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "in onViewCreated");

        imageView = view.findViewById(R.id.weather_image_view);
        curTempTextView = view.findViewById(R.id.cur_temp_text_view);
        minTempTextView = view.findViewById(R.id.min_temp_text_view);
        maxTempTextView = view.findViewById(R.id.max_temp_text_view);
        progressBar = view.findViewById(R.id.progressBar);
        spinner = view.findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.cities_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ForecastQuery f = new ForecastQuery();
        f.execute();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFrgAct = getActivity();
        mIntent = mFrgAct.getIntent(); //  Intent intent = new Intent(getActivity().getIntent());
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id)
    {
        // An item was selected. You can retrieve the selected item using
        selection = (String) parent.getItemAtPosition(pos);
        Log.i("onItemSelected", selection);

        ForecastQuery g = new ForecastQuery();
        g.execute();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String windSpeed;
        private String currentTemp;
        private String minTemp;
        private String maxTemp;
        private Bitmap picture;
        private String firstHalf = "https://api.openweathermap.org/data/2.5/weather?q=";
        private String secondHalf = "&APPID=706bf033bdf2d8a828a3dc95bb77e79f&mode=xml&units=metric";
        private String urlString = "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric";
        private String urlString2 = "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=706bf033bdf2d8a828a3dc95bb77e79f&mode=xml&units=metric";

        @Override
        protected String doInBackground(String... strings)
        {
            try
            {
                //URL url = new URL(urlString2);
                URL url = new URL(firstHalf + selection + secondHalf);


                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                // Start query
                conn.connect();

                InputStream in = conn.getInputStream();

                try
                {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);

                    int type;
                    //While you're not at the end of the document:
                    while((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT)
                    {
                        Log.i(ACTIVITY_NAME, "Starting parsing! Yay!");
                        //Are you currently at a Start Tag?
                        if(parser.getEventType() == XmlPullParser.START_TAG)
                        {
                            if(parser.getName().equals("temperature") )
                            {
                                currentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            }
                            else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";

                                Log.i(ACTIVITY_NAME,"Looking for file: " + fileName);
                                if (fileExistance(fileName))
                                {
                                    FileInputStream fis = null;
                                    try
                                    {
                                        fis = mFrgAct.openFileInput(fileName);

                                    }
                                    catch (FileNotFoundException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME,"Found the file locally");
                                    picture = BitmapFactory.decodeStream(fis);
                                }
                                else
                                {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    picture = getImage(new URL(iconUrl));

                                    FileOutputStream outputStream = mFrgAct.openFileOutput( fileName, Context.MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME,"Downloaded the file from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                            else if (parser.getName().equals("wind"))
                            {
                                parser.nextTag();
                                if(parser.getName().equals("speed") )
                                {
                                    windSpeed = parser.getAttributeValue(null, "value");
                                }
                            }
                        }
                        // Go to the next XML event
                        parser.next();
                    }
                } finally
                {
                    in.close();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return "";

        }

        public boolean fileExistance(String fname)
        {
            File file = mFrgAct.getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url)
        {
            HttpsURLConnection connection = null;
            try
            {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200)
                {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e)
            {
                return null;
            } finally {
                if (connection != null)
                {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String a) {
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(picture);
            curTempTextView.setText(getString(R.string.cur_temp) + currentTemp + "C\u00b0");
            minTempTextView.setText(getString(R.string.min_temp) + minTemp + "C\u00b0");
            maxTempTextView.setText(getString(R.string.max_temp) + maxTemp + "C\u00b0");
            //wind_speed.setText(windSpeed + "km/h");
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

    }
}

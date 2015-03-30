package cool.gis.utmconverter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static String TAG = "UTMConverter";
    private class UTM
    {
        public int zone;
        public double easting;
        public double northing;
        public UTM( int zone, double easting, double northing){
            this.zone = zone;
            this.easting = easting;
            this.northing = northing;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.convertToUTMButton);
//        editText.setRawInputType(Configuration.KEYBOARD_QWERTY);
        EditText latitudeEditText = (EditText)findViewById(R.id.latitudeEditText);
        EditText longitudeEditText = (EditText)findViewById(R.id.longitudeEditText);

        latitudeEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        longitudeEditText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                TextView utmTextView = (TextView)findViewById(R.id.utmTextView);
                //utmTextView.setText(R.string.utmZone + 17 + R.string.utmEasting + 111111 + R.string.utmNorthing + 111111);
                EditText latitudeEditText = (EditText)findViewById(R.id.latitudeEditText);
                EditText longitudeEditText = (EditText)findViewById(R.id.longitudeEditText);

                String latText = latitudeEditText.getText().toString();
                String lngText = longitudeEditText.getText().toString();
                try {
                    float lat = Float.parseFloat(latText);
                    float lng = Float.parseFloat(lngText);
                    if ((lat > 90) || (lat < -90) || (lng > 180) || (lng < -180)) {
                        utmTextView.setText(R.string.latlngWrongFormatMessage);
                        return;
                    }
                    UTM utm = convertUTM(lat, lng);
                    utmTextView.setText("Zone: " + Integer.toString(utm.zone) + ", Easting: " + String.format("%.2f",utm.easting) + ", Northing: " + String.format("%.2f", utm.northing));
                } catch (Exception e) {
                    utmTextView.setText(R.string.latlngWrongFormatMessage);
                }
            }
        });
    }

    private UTM convertUTM(float lat, float lng) {
        double pi = 3.14159265358979; //PI
        double a = 6378137; //equatorial radius for WGS 84
        double k0 = 0.9996; //scale factor
        double e = 0.081819191; //eccentricity
        double e_2 = 0.006694380015894481; //e'2
        double A0 = 6367449.146;
        double B0 = 16038.42955;
        double C0 = 16.83261333;
        double D0 = 0.021984404;
        double E0 = 0.000312705;

        int zone = 31 + (int)Math.floor(lng / 6);
        double lat_r = lat * pi / 180.0;
        double t1 = Math.sin(lat_r); // SIN(LAT)
        double t2 = e * t1 * e * t1;
        double t3 = Math.cos(lat_r); // COS(LAT)
        double t4 = Math.tan(lat_r); // TAN(LAT)
        double nu = a / (Math.sqrt(1 - t2));
        double S = A0 * lat_r - B0 * Math.sin(2 * lat_r) + C0 * Math.sin(4 * lat_r) - D0 * Math.sin(6 * lat_r) + E0 * Math.sin(8 * lat_r);
        double k1 = S * k0;
        double k2 = nu * t1 * t3 * k0 / 2.0;
        double k3 = ((nu * t1 * t2 * t2 * t2) / 24) * (5 - t4 * t4 + 9 * e_2 * t3 * t3 + 4 * e_2 * e_2 * t3 * t3 * t3 * t3) * k0;
        double k4 = nu * t3 * k0;
        double k5 = t3 * t3 * t3 * (nu / 6) * (1 - t4 * t4 + e_2 * t3 * t3) * k0;

        //double lng_r = lng*pi/180.0;
        double lng_zone_cm = 6 * zone - 183;
        double d1 = (lng - lng_zone_cm) * pi / 180.0;
        double d2 = d1 * d1;
        double d3 = d2 * d1;
        double d4 = d3 * d1;

        double x = 500000 + (k4 * d1 + k5 * d3);
        double rawy = (k1 + k2 * d2 + k3 * d4);
        double y = rawy;
        if (y < 0) {
            y = y + 10000000;
        }
        return new UTM(zone, x, y);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

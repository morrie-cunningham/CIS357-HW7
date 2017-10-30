package edu.gvsu.cis.hw4_geocalc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GeoCalcActivity extends AppCompatActivity {

    public static final int SETTINGS_SELECTION = 1;

    EditText p1la;
    EditText p1lo;
    EditText p2la;
    EditText p2lo;
    TextView outDistance;
    TextView outBearing;

    Double oDist;
    Double oBear;
    Boolean isKm = true;
    Boolean isDeg = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, SETTINGS_SELECTION);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTINGS_SELECTION && data != null) {
            String distanceUnits = data.getStringExtra("distanceUnits");
            String bearingUnits = data.getStringExtra("bearingUnits");

            if(distanceUnits.equals("Kilometers")) {
                if(!isKm) {
                    oDist = oDist / 0.621371;
                    outDistance.setText(String.format("%.2f", oDist) + " kilometers");
                    isKm = true;
                }
            } else {
                if(isKm) {
                    oDist = oDist * 0.621371;
                    outDistance.setText(String.format("%.2f", oDist) + " miles");
                    isKm = false;
                }
            }

            if(bearingUnits.equals("Degrees")) {
                if(!isDeg) {
                    oBear = oBear / 17.777777777778;
                    outBearing.setText(String.format("%.2f", oBear) + " degrees");
                    isDeg = true;
                }
            } else {
                if(isDeg) {
                    oBear = oBear * 17.777777777778;
                    outBearing.setText(String.format("%.2f", oBear) + " mils");
                    isDeg = false;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_calc);

        p1la = (EditText) findViewById(R.id.p1lat);
        p1lo = (EditText) findViewById(R.id.p1long);
        p2la = (EditText) findViewById(R.id.p2lat);
        p2lo = (EditText) findViewById(R.id.p2long);
        outDistance = (TextView) findViewById(R.id.outputDistanceView);
        outBearing = (TextView) findViewById(R.id.outputBearingView);

        Button calc = (Button) findViewById(R.id.calcButton);
        Button clear = (Button) findViewById(R.id.clearButton);

        clear.setOnClickListener(v -> {
            p1la.setText("");
            p1lo.setText("");
            p2la.setText("");
            p2lo.setText("");
            outDistance.setText("");
            outBearing.setText("");

            dismissKeyboard(this);
        });

        calc.setOnClickListener(v -> {
            String p1lastr = p1la.getText().toString();
            String p1lostr = p1lo.getText().toString();
            String p2lastr = p2la.getText().toString();
            String p2lostr = p2lo.getText().toString();
            if (p1lastr.length() == 0 || p1lostr.length() == 0 || p2lastr.length() == 0 || p2lostr.length() == 0) {
                Snackbar.make(p1la, "Enter 2 Lat/Long Points.", Snackbar.LENGTH_LONG).show();
            } else {
                Location loc1 = new Location("");
                loc1.setLatitude(Double.parseDouble(p1la.getText().toString()));
                loc1.setLongitude(Double.parseDouble(p1lo.getText().toString()));

                Location loc2 = new Location("");
                loc2.setLatitude(Double.parseDouble(p2la.getText().toString()));
                loc2.setLongitude(Double.parseDouble(p2lo.getText().toString()));

                oDist = (double) loc1.distanceTo(loc2)/1000;

                if(isKm) {
                    outDistance.setText(String.format("%.2f", oDist) + " kilometers");
                } else {
                    oDist = oDist * 0.621371;
                    outDistance.setText(String.format("%.2f", oDist) + " miles");
                    isKm = false;
                }

                oBear = (double) loc1.bearingTo(loc2);

                if(isDeg) {
                    outBearing.setText(String.format("%.2f", oBear) + " degrees");
                } else {
                    oBear = oBear * 17.777777777778;
                    outBearing.setText(String.format("%.2f", oBear) + " mils");
                    isDeg = false;
                }
            }
            dismissKeyboard(this);
        });
    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}

package edu.gvsu.cis.hw4_geocalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    private String dSelection = "Kilometers";
    private String bSelection = "Degrees";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("distanceUnits", dSelection);
                intent.putExtra("bearingUnits", bSelection);
                setResult(GeoCalcActivity.SETTINGS_SELECTION, intent);
                finish();
            }
        });

        Spinner dSpin = (Spinner) findViewById(R.id.distanceSpinner);
        Spinner bSpin = (Spinner) findViewById(R.id.bearingSpinner);

        ArrayAdapter<CharSequence> adaptD = ArrayAdapter.createFromResource(this, R.array.dUnits, android.R.layout.simple_spinner_item);

        adaptD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dSpin.setAdapter(adaptD);
        dSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dSelection = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adaptB = ArrayAdapter.createFromResource(this, R.array.bUnits, android.R.layout.simple_spinner_item);

        adaptB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bSpin.setAdapter(adaptB);
        bSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bSelection = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}

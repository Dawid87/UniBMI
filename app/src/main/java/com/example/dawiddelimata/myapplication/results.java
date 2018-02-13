package com.example.dawiddelimata.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class results extends AppCompatActivity {

    BillingProcessor bp;
    AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_resoults);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");
        //buttonListenerMethod();


        //AppRater.app_launched(this);


        // Add AdMob advertisement banner
        mAdview = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1DB7C92893CB70AD23614BEAC2C07861").build();
        mAdview.loadAd(adRequest);

        // Displaying created bar in xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("    BMI Calculator");                                           // Set name of new toolbar
        getSupportActionBar().setLogo(R.drawable.scale);
    }

    public void back(View v) {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }

    // Create three dot toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Set options of three dot menu list
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        int id = item.getItemId();
        switch (id) {
            case R.id.Remove_adds:
                bp.purchase(results.this, "com.dawid.dawiddelimata.bmicalculator_ver14");
                Toast.makeText(this, "Remove Adds", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void buttonListenerMethod() {
//        EditText editText = (EditText) findViewById(R.id.bmiCat);
//        editText.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    switch (keyCode) {
//                        case KeyEvent.KEYCODE_DPAD_CENTER:
//                        case KeyEvent.KEYCODE_ENTER:
//                            addCourseFromTextBox();
//                            return true;
//                        default:
//                            break;
//                    }
//                }
//                return false;
//            }
//
//            private void addCourseFromTextBox() {
//
//            }
//        });
//
//
//    }
}

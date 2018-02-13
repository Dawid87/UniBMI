package com.example.dawiddelimata.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{

    BillingProcessor bp;

    AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this, "Enter height, weight and click blue button", Toast.LENGTH_LONG).show();

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");
        buttonListenerMethod();


        AppRater.app_launched(this);

        bp = new BillingProcessor(this, "com.example.dawiddelimata.myapplication", this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.Remove_adds:
                bp.purchase(MainActivity.this, "com.example.dawiddelimata.myapplication");
                Toast.makeText(this, "Remove Adds", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonListenerMethod(){
        EditText editText = (EditText) findViewById(R.id.bmiCat);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    switch(keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            addCourseFromTextBox();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }

            private void addCourseFromTextBox(){

            }
        });

        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide virtual keyboard if button pressed
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                final EditText heightInput = (EditText) findViewById(R.id.heightInput);
                if(heightInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please insert Height and Weight", Toast.LENGTH_SHORT).show();
                    return;
                }

                String heightStr = heightInput.getText().toString();
                double height = Double.parseDouble(heightStr);

                final EditText weightInput = (EditText) findViewById(R.id.weightInput);
                if(heightInput.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please insert Height and Weight", Toast.LENGTH_SHORT).show();
                    return;
                }

                String weightStr = weightInput.getText().toString();
                double weight = Double.parseDouble(weightStr);

                height /= 100;
                double bmi = (weight) / (height * height);

                final EditText bmiSum = (EditText) findViewById(R.id.bmiSum);
                bmiSum.setText(Double.toString(bmi).format("%.2f", bmi));
                //String.format("%.2f", bmiSum)

                String bmi_cat;
                if(bmi < 15)
                    bmi_cat = "Very severely underweight";
                else if(bmi < 16)
                    bmi_cat = "Severely underweight";
                else if(bmi < 18.5)
                    bmi_cat = "Underweight";
                else if(bmi < 25)
                    bmi_cat = "Normal weight";
                else if(bmi < 30)
                    bmi_cat = "Overweight";
                else if(bmi < 35)
                    bmi_cat= "Moderately obese";
                else if(bmi < 40)
                    bmi_cat = "Severely obese";
                else
                    bmi_cat = "Very severely\"morbidly\" obese";

                final TextView bmiCat = (TextView) findViewById(R.id.bmiCat);
                bmiCat.setText(bmi_cat);
            }
        });
    }

    public void result(View v){
        Intent results = new Intent(this, results.class);
        startActivity(results);
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        Toast.makeText(this, "You've purchased", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}

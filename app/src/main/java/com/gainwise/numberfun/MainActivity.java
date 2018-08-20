package com.gainwise.numberfun;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.gainwise.numberfun.utilities.NetworkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.net.URL;

import osmandroid.project_basics.Task;

public class MainActivity extends AppCompatActivity {

    String shareString = "";
    TabLayout tabLayout;
    TextView textView;
    Dialog dialog;
    Dialog dialog2;
    AdView adView;
    String firstRun;
    Handler h;
    Context context;

    SharedPreferences.Editor editor;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
context = this;
        textView = (TextView)findViewById(R.id.tvResults);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        initTabs();
        adView = (AdView) findViewById(R.id.adView22);
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
        dialog = new Dialog(MainActivity.this);
        dialog2 = new Dialog(MainActivity.this);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            textView.setText("Results will appear here! This text is scrollable.");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

initThePrefs();
        if(firstRun.equals("yes")){
            seeTheDialog();
        }
         h = new Handler();
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


        if (id == R.id.follow) {

            Task.FollowOnFb(MainActivity.this,"1953614828228585","https://www.facebook.com/RealGainWise/");
            return true;
        }
        if (id == R.id.rate) {
            Task.RateApp(MainActivity.this, "com.gainwise.numberfun");
            return true;
        }

        if (id == R.id.share) {
            Task.ShareApp(MainActivity.this, "com.gainwise.numberfun", "Awesome app",
                    "Interesting Number facts!");
            return true;
        }


        if (id == R.id.otherapps) {
            Task.MoreApps(MainActivity.this, "GainWise");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeRandomNumbersSearch() {

        URL searchUrl = NetworkUtils.buildUrl("random", tabLayout.getSelectedTabPosition());
        Log.i("JOSH", "this is search query" +searchUrl.toString());
        // COMPLETED (4) Create a new GithubQueryTask and call its execute method, passing in the url to query
        new NumbersQueryTask().execute(searchUrl);
    }
    private void makeCustomNumbersSearch(String numIn) {

        URL searchUrl = NetworkUtils.buildUrl(numIn, tabLayout.getSelectedTabPosition());
        Log.i("JOSH", "this is search query" +searchUrl.toString());
        // COMPLETED (4) Create a new GithubQueryTask and call its execute method, passing in the url to query
        new NumbersQueryTask().execute(searchUrl);
    }


    public class NumbersQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {

            textView.setText("fetching...");
            URL searchUrl = params[0];
            String numbersSearchResults = null;
            try {
                numbersSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {

                e.printStackTrace();
            }

            return numbersSearchResults;
        }

        // COMPLETED (3) Override onPostExecute to display the results in the TextView
        @Override
        protected void onPostExecute(String githubSearchResults) {
            if (githubSearchResults != null && !githubSearchResults.equals("")) {

                textView.setText(githubSearchResults);
                shareString = githubSearchResults;


            }
        }
    }

    public void initTabs(){
        tabLayout.addTab(tabLayout.newTab().setText("TRIVIA"));
        tabLayout.addTab(tabLayout.newTab().setText("DATE"));
        tabLayout.addTab(tabLayout.newTab().setText("YEAR"));

        tabLayout.addTab(tabLayout.newTab().setText("MATH"));
    }
    public void button1click(View v){

        final String type = NetworkUtils.getType(tabLayout.getSelectedTabPosition());
        if(type.equals("date")){

            final View mView2 = getLayoutInflater().inflate(R.layout.dialog_date, null);
            final NumberPicker np1 = (NumberPicker)mView2.findViewById(R.id.np1);
            final NumberPicker np2 = (NumberPicker)mView2.findViewById(R.id.np2);
            np1.setMinValue(1);
            np1.setMaxValue(12);
            np2.setMinValue(1);
            np2.setMaxValue(31);
            np1.setWrapSelectorWheel(true);
            np2.setWrapSelectorWheel(true);
            np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    switch (np1.getValue()){
                        case 1:
                            np2.setMaxValue(31);
                            break;

                        case 2:
                            np2.setMaxValue(29);
                            break;
                        case 3:
                            np2.setMaxValue(31);
                            break;
                        case 4:
                            np2.setMaxValue(30);
                            break;
                        case 5:
                            np2.setMaxValue(31);
                            break;
                        case 6:
                            np2.setMaxValue(30);
                            break;
                            case 7:
                                np2.setMaxValue(31);
                            break;
                        case 8:
                            np2.setMaxValue(31);
                            break;
                        case 9:
                            np2.setMaxValue(30);
                            break;
                        case 10:
                            np2.setMaxValue(31);
                            break;
                        case 11:
                            np2.setMaxValue(30);
                            break;
                        case 12:
                            np2.setMaxValue(31);
                            break;


                    }
                }
            });
            Button button2 = (Button)mView2.findViewById(R.id.button_in_dialog2);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 StringBuilder sb = new StringBuilder();
                 sb.append(np1.getValue());
                 sb.append("/");
                 sb.append(np2.getValue());
                 String date = sb.toString();
                 Log.i("JOSH date", date);

                    makeCustomNumbersSearch(date);
                    dialog2.dismiss();
                }
            });
            dialog2.setContentView(mView2);

            dialog2.show();


        }else{

            final View mView = getLayoutInflater().inflate(R.layout.dialog_reg, null);
           final EditText et = (EditText)mView.findViewById(R.id.et_in_dialog);
            Button button = (Button)mView.findViewById(R.id.button_in_dialog);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = et.getText().toString().trim();
                    if (num.length() <= 0 ){
                        num = "random";
                    }
                    makeCustomNumbersSearch(num);
                    dialog.dismiss();
                }
            });
            dialog.setContentView(mView);

            dialog.show();

            et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    et.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            });
            et.requestFocus();




        }


    }
    public void button2click(View v){
        makeRandomNumbersSearch();
    }

    public void button3click(View v){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", textView.getText().toString());
        clipboard.setPrimaryClip(clip);
    }
    public void initThePrefs(){
        prefs = getSharedPreferences("PASSAPP", MODE_PRIVATE);
        editor = getSharedPreferences("PASSAPP", MODE_PRIVATE).edit();

        firstRun = prefs.getString("first_run", "yes");

    }

    public void seeTheDialog(){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle(" Welcome")
                .setMessage(R.string.dialog2)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("first_run", "no");

                        editor.apply();

                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }


}

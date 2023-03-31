package com.rera.property.Avtivities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rera.property.R;
import com.rera.property.Utils.Endpoints;
import com.rera.property.Utils.SmsData;
import com.rera.property.Utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SearchLead extends AppCompatActivity {


    LinearLayout  statelayout, citylayout;

    Button residentialtype, commercialtype;
    EditText city, state;
    RadioButton buyr, rentr, pgr, privater, sharer, buyers;
    Button search;
    private ProgressDialog progressDialog;
    ArrayList<String> allcity = new ArrayList<String>();




    String instance = "", listfor="",type="";


    String usertype = "", subscriptionplan ="";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_search_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search Lead");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });








        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);




        instance = getIntent().getStringExtra("instance");



        statelayout = findViewById(R.id.statelayout);
        citylayout = findViewById(R.id.citylayout);


        buyers = findViewById(R.id.buyers);



        if(instance.equals("lead")){
            getsubscription();
            statelayout.setVisibility(View.VISIBLE);
            buyers.setVisibility(View.VISIBLE);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateOnly = new SimpleDateFormat("yyyy-MM-dd");
            String date2;
            date2 = dateOnly.format(cal.getTime());

            VolleySingleton.getInstance(getApplicationContext()).setcounterdate(date2);


        }













        residentialtype = findViewById(R.id.residential);
        commercialtype = findViewById(R.id.comercial);


        city = findViewById(R.id.city);
        state = findViewById(R.id.state);

        buyr = findViewById(R.id.buyr);

        buyers = findViewById(R.id.buyers);

        if(instance.equals("lead")){
            buyr.setText("Sell");
        }else{
            buyr.setText("Buy");
        }




        search = findViewById(R.id.search);




        state.setText("Maharashtra");
        VolleySingleton.getInstance(getApplicationContext()).setsearchstate("Maharashtra");



        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleySingleton.getInstance(getApplicationContext()).setsearchlocality("");
                startActivity(new Intent(SearchLead.this, SearchCity.class));
            }
        });

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city.setText("");
                VolleySingleton.getInstance(getApplicationContext()).setsearchcity("");
                VolleySingleton.getInstance(getApplicationContext()).setsearchlocality("");
                startActivity(new Intent(SearchLead.this, SearchState.class));
            }
        });



        listfor = "Sell";

        buyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listfor = "Sell";
            }
        });


        buyers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commercialtype.setVisibility(View.VISIBLE);
                listfor = "Buyers";
            }
        });




        residentialtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                residentialtype.setTextColor(Color.GREEN);
                commercialtype.setTextColor(Color.WHITE);
                type = "Residential";






            }
        });

        commercialtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                residentialtype.setTextColor(Color.WHITE);
                commercialtype.setTextColor(Color.GREEN);
                type = "Commercial";


            }
        });







        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(instance.equals("lead")){


                    String getcity;
                    getcity = city.getText().toString().trim();

                    if (valid1(listfor, getcity, type)) {
                        Intent intent = new Intent(SearchLead.this, LeadSearch.class);
                        intent.putExtra("propertylistfor", listfor);
                        intent.putExtra("city", getcity);
                        intent.putExtra("type", type);
                        intent.putExtra("subscriptionplan", subscriptionplan);
                        startActivity(intent);
//                    showsearch(listfor, getcity, type, category);
                    }



                }


            }
        });

    }

    private boolean valid1(String listfor, String getcity, String type) {

        if(listfor.isEmpty()){
            showmessage("Please Select Property Looking for...");
            return false;
        }
        else if(getcity.isEmpty()){
            city.setError("Please Enter The City");
            showmessage("Please Enter The City");
            return false;
        }

        else if(!listfor.equals("Buyers")){
              if(type.isEmpty()){
                showmessage("Please Select Type of Property...");
                return false;
            }
        }


        return true;
    }

    private boolean valid(String listfor, String getcity, String type, String category) {

        if(getcity.isEmpty()){
            showmessage("Please Enter The City");
            return false;
        }


        return true;
    }

//    private void showsearch(final String listfor, final String getcity, final String type, final String category) {
//        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.searchproperty, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                System.out.println("response ::::: "+ response);
//                progressDialog.dismiss();
//                try {
//                    JSONObject obj = new JSONObject(response);
//                    if(obj.getBoolean("error")){
//                        showmessage(obj.getString("message"));
//                    }else{
//                        showmessage(obj.getString("message"));
//                        ListingData.getInstance(getApplicationContext()).Logout();
//                        startActivity(new Intent(SearchProperty.this, MainActivity.class));
//
//
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SearchProperty.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
//                Log.d("VOLLEY", String.valueOf(error));
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                SmsData smsData = new SmsData();
//                Map<String, String> params = new HashMap<>();
//                params.put("header", smsData.token);
//
//                params.put("listfor", listfor);
//                params.put("minprice", min);
//                params.put("maxprice", max);
//                params.put("type", type);
//                params.put("category", category);
//                params.put("city", getcity);
//
//
//                return params;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance(SearchProperty.this).addToRequestQueue(stringRequest);
//
//    }


    private void getsubscription() {
        progressDialog.show();
        final String userid = VolleySingleton.getInstance(getApplicationContext()).id();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.getsubscription, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                System.out.println("response :: " +response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getBoolean("error")){
                        showmessage(obj.getString("message"));
                    }else{
                        subscriptionplan = obj.getString("subscriptionplan");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchLead.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SmsData smsData = new SmsData();
                Map<String, String> params = new HashMap<>();
                params.put("header", smsData.token);
                params.put("userid", userid);
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



    @Override
    protected void onResume() {


        if(VolleySingleton.getInstance(getApplicationContext()).getsearchcity() != null){
            String citysearch = VolleySingleton.getInstance(getApplicationContext()).getsearchcity();
            city.setText(citysearch);
        }

        if(VolleySingleton.getInstance(getApplicationContext()).getsearchstate() != null){
            String citysearch = VolleySingleton.getInstance(getApplicationContext()).getsearchstate();
            state.setText(citysearch);
        }


        super.onResume();
    }



//    private void getcity() {
//        allcity.clear();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.getcity, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//
//                System.out.println("responseec:: "+ response);
//                try {
//                    JSONObject obj = new JSONObject(response);
//
//                    System.out.println("response :: "+ response);
//
//                    String succes = obj.getString("success");
//                    JSONArray jsonArray = obj.getJSONArray("data");
//
//
//                    if(succes.equals("1")){
//                        for(int i=0; i<jsonArray.length();i++){
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            String city =object.getString("city");
//                            allcity.add(city);
//
//                        }
//
//                    }
//
//
//
//
//                } catch (JSONException e) {
//                    showmessage("Something wrong please check network connection"+e);
//                    System.out.println("problem ::"+e);
//                    e.printStackTrace();
//                }
//                city.setAdapter(new ArrayAdapter<String>(SearchProperty.this, android.R.layout.simple_spinner_dropdown_item, allcity));
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SearchProperty.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
//                Log.d("VOLLEY",error.getMessage());
//            }
//        });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//    }


    private void showmessage(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}

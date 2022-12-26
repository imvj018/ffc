package com.example.ffc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.provider.Telephony.Mms.Part.TEXT;

public class OSMain extends AppCompatActivity implements OSAdapter.OnItemClickListener {
    public static final String EXTRA_NUMBER = "SalesOrder";
    public static final String EXTRA_AMOUNT = "TotalNetAmount";
    public static final String EXTRA_CURRENCY = "TransactionCurrency";
    public static final String EXTRA_STATUS = "OverallTotalDeliveryStatus";
    public static final String EXTRA_DATE = "PricingDate";

    private RecyclerView mRecyclerView;
    private OSAdapter mOSAdapter;
    private ArrayList<OSItem> mOSList;
    private String text;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_s_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOSList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        SharedPreferences sharedPreferences = getSharedPreferences("KEY", MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");

        parseJSON();

    }

    private void parseJSON() {
        String url = "http://185.95.4.61:8011/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder?$filter=SoldToParty%20eq%20%27"+text+"%27&$orderby=SalesOrder%20Desc&$format=json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONObject("d").getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        String OrderNumber = hit.getString("SalesOrder");
                        String NetAmount = hit.getString("TotalNetAmount");
                        String Currency = hit.getString("TransactionCurrency");
                        String DelStatus = hit.getString("OverallTotalDeliveryStatus");
                        String Date = hit.getString("PricingDate");


                        mOSList.add(new OSItem(OrderNumber, NetAmount, Currency, DelStatus, Date ));
                    }
                    mOSAdapter = new OSAdapter(OSMain.this, mOSList);
                    mRecyclerView.setAdapter(mOSAdapter);
                    mOSAdapter.setOnItemClickListener(OSMain.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "fdev_mm" + ":" + "simple@1";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;


            }

        };
        mRequestQueue.add(request);}

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, OSDetail.class);
        OSItem clickedItem = mOSList.get(position);
        detailIntent.putExtra(EXTRA_NUMBER, clickedItem.getNumber());
        detailIntent.putExtra(EXTRA_AMOUNT, clickedItem.getAmount());
        detailIntent.putExtra(EXTRA_CURRENCY, clickedItem.getCurrency());
        detailIntent.putExtra(EXTRA_STATUS, clickedItem.getStatus());
        detailIntent.putExtra(EXTRA_DATE, clickedItem.getDate());

        startActivity(detailIntent);

    }
}


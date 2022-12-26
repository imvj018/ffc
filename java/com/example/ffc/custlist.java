package com.example.ffc;

import android.content.Intent;
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

public class custlist extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {
    public static final String EXTRA_NAME = "Name";
    public static final String EXTRA_KUNNR = "KUNNR";
    public static final String EXTRA_CITY = "City";
    public static final String EXTRA_TELEPHONE = "Telephone";
    public static final String EXTRA_ADDRESS = "Address";
    public static final String EXTRA_POSTAL = "Postal";
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custlist);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://raw.githubusercontent.com/imvj018/jsonnew/main/new.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONObject("d").getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        String Name = hit.getString("Name");
                        String KUNNR = hit.getString("KUNNR");
                        String City = hit.getString("City");
                        String Telephone = hit.getString("Telephone");
                        String Address = hit.getString("Address");
                        String Postal = hit.getString("Postal");

                        mExampleList.add(new ExampleItem(Name, KUNNR, City, Telephone, Address, Postal));
                    }
                    mExampleAdapter = new ExampleAdapter(custlist.this, mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(custlist.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String credentials = "fdev_mm" + ":" + "simple@1";
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;


            }

        };
        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        ExampleItem clickedItem = mExampleList.get(position);
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getName());
        detailIntent.putExtra(EXTRA_KUNNR, clickedItem.getKUNNR());
        detailIntent.putExtra(EXTRA_CITY, clickedItem.getCity());
        detailIntent.putExtra(EXTRA_TELEPHONE, clickedItem.getTelephone());
        detailIntent.putExtra(EXTRA_ADDRESS, clickedItem.getAddress());
        detailIntent.putExtra(EXTRA_POSTAL, clickedItem.getPostal());
        startActivity(detailIntent);

    }
}
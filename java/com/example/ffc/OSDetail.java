package com.example.ffc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.widget.TextView;

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

import static com.example.ffc.OSMain.EXTRA_AMOUNT;
import static com.example.ffc.OSMain.EXTRA_CURRENCY;
import static com.example.ffc.OSMain.EXTRA_NUMBER;
import static com.example.ffc.OSMain.EXTRA_STATUS;


public class OSDetail extends AppCompatActivity {
    private RecyclerView xRecyclerView;
    private OSDAdapter xOSDAdapter;
    private ArrayList<OSDItem> xExampleList;
    private RequestQueue xRequestQueue;
    String OS;
    String orderNumber;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_s_detail);

        Intent intent = getIntent();
        String OrderNumber = intent.getStringExtra(EXTRA_NUMBER);
        String Amount = intent.getStringExtra(EXTRA_AMOUNT);
        String Currency = intent.getStringExtra(EXTRA_CURRENCY);
        String Status = intent.getStringExtra(EXTRA_STATUS);


        TextView textViewosnum = findViewById(R.id.osnumber);
        TextView textViewosnumb = findViewById(R.id.osnum);
        TextView textViewosamount = findViewById(R.id.osamount);
        TextView textViewosstatus = findViewById(R.id.osstatus);


        textViewosnum.setText("Order Number :  " + OrderNumber);
        textViewosnumb.setText(OrderNumber);
        textViewosamount.setText("Amount :  " + Amount + " " + Currency);

        if (Status.equals("A")) {
            OS = "<font color='black'>Order Status :  </font><font color='#1A73E8'> Order Created</font>";
            textViewosstatus.setText(Html.fromHtml(OS), TextView.BufferType.SPANNABLE);

        } else if (Status.equals("B")) {
            OS = "<font color='black'>Order Status :  </font><font color='#FFBF00'> Delivery Created</font>";
            textViewosstatus.setText(Html.fromHtml(OS), TextView.BufferType.SPANNABLE);
        } else if (Status.equals("C")) {
            OS = "<font color='black'>Order Status :  </font><font color='#027C4F'> Delivered</font>";
            textViewosstatus.setText(Html.fromHtml(OS), TextView.BufferType.SPANNABLE);

        }


        orderNumber = textViewosnumb.getText().toString();

        xRecyclerView = findViewById(R.id.recycler_view1);
        xRecyclerView.setHasFixedSize(true);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xExampleList = new ArrayList<>();
        xRequestQueue = Volley.newRequestQueue(this);
        parseOSD();
    }

    private void parseOSD() {

        String url = "http://185.95.4.61:8011/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder('" + orderNumber + "')/to_Item?$format=json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONObject("d").getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        String Item = hit.getString("SalesOrderItemText");
                        String Quantity = hit.getString("RequestedQuantity");
                        String Amount = hit.getString("NetAmount");
                        String Status = hit.getString("DeliveryStatus");
                        String Matnum = hit.getString("Material");
                        String Itnum = hit.getString("SalesOrderItem");
                        String Weight = hit.getString("RequestedQuantityUnit");
                        String Currency = hit.getString("TransactionCurrency");

                        xExampleList.add(new OSDItem(Item, Quantity, Amount, Status, Matnum, Itnum, Weight, Currency));
                    }
                    xOSDAdapter = new OSDAdapter(OSDetail.this, xExampleList);
                    xRecyclerView.setAdapter(xOSDAdapter);

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
        xRequestQueue.add(request);

    }


}
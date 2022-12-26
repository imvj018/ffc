package com.example.ffc;

import static android.provider.Telephony.Mms.Part.TEXT;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Saleorder extends AppCompatActivity implements View.OnClickListener {


    int finalqe,finalinv;
    LinearLayout layoutList;
    Dialog myDialog;
    Spinner spinner;
    int count=0;
    EditText editText;
    TextView invisible;
    EditText quantityedit;

    TextView stockavaialble;
    String URL = "http://185.95.4.61:8011/sap/opu/odata/sap/ZSL_EPM_DEMO_SRV/CustomSet?$filter=Mtart%20eq%20%27ZHWA%27%20and%20Labst%20ge%201%20&$format=json";
    Button buttonAdd;
    Button submitList;
    ArrayList<String> MaterialNum;
    ArrayList<String> CountryName;
    ArrayList<String> Quantity;
    private ProgressDialog pDialog;
    ArrayList<Fruits> fruitsList = new ArrayList<>();
    private static HttpURLConnection connection = null;
    private static HttpURLConnection readconn = null;
    private static HttpURLConnection writeconn = null;
    private static List<String> session = null;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_saleorder);
        CountryName =new ArrayList<>();
        MaterialNum =new ArrayList<>();
        Quantity = new ArrayList<>();
        loadSpinnerData(URL);
        quantityedit = findViewById(R.id.edit_quantity);
        invisible = findViewById(R.id.invisiblestock);
        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);

        SharedPreferences sharedPreferences = getSharedPreferences("KEY", MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        System.out.println(text);
        submitList = findViewById(R.id.button_submit_list);
        buttonAdd.setOnClickListener(this);
        submitList.setOnClickListener(this);
        myDialog = new Dialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_add:
                addView();
                break;
            case R.id.button_submit_list:
                if(checkIfValidAndRead()){
                    SubmitSalesOrder();
                }
                break;
        }
    }

    private boolean checkIfValidAndRead() {
        fruitsList.clear();
        boolean result = true;

        for(int i=0;i<layoutList.getChildCount();i++){
            View fruitsView = layoutList.getChildAt(i);

            Spinner spinner = (Spinner)fruitsView.findViewById(R.id.searchspinner);
            EditText material = (EditText)fruitsView.findViewById(R.id.edit_material);
            EditText quantity = (EditText)fruitsView.findViewById(R.id.edit_quantity);

            Fruits fruits = new Fruits();

            if(!material.getText().toString().equals("")){
                fruits.setFruitNum(material.getText().toString());
            }else {
                result = false;
                break;
            }

            if(!quantity.getText().toString().equals("") && !quantity.getText().toString().equals("0")){
                fruits.setQuantity(quantity.getText().toString());
            }else{
                result = false;
                break;
            }

            if(spinner.getSelectedItemPosition()>=0){
                fruits.setFruitName(CountryName.get(spinner.getSelectedItemPosition()));
            }else{
                result = false;
                break;
            }
            fruitsList.add(fruits);
        }
        if(fruitsList.size()==0){
            result = false;
            Toast.makeText(this, "Add fruits or quantity first", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(this, "Enter the details correctly!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private void addView() {
        final View fruitsView = getLayoutInflater().inflate(R.layout.row_add_cricketer, null, false);
        ImageView imageClose = fruitsView.findViewById(R.id.image_remove);
        editText = fruitsView.findViewById(R.id.edit_material);
        stockavaialble = fruitsView.findViewById(R.id.stockavailable);
        invisible = fruitsView.findViewById(R.id.invisiblestock);
        quantityedit = fruitsView.findViewById(R.id.edit_quantity);
        spinner =(Spinner)fruitsView.findViewById(R.id.searchspinner);
        spinner.setAdapter(new ArrayAdapter<String>(Saleorder.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String materialnum = MaterialNum.get(i);
                editText.setText(materialnum);
                String quantity = Quantity.get(i);
                invisible.setText(quantity);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(fruitsView);
            }
        });

        layoutList.addView(fruitsView);
    }

    private void removeView(View view) {
        layoutList.removeView(view);
    }
    public void increment(View v){
        String inv = invisible.getText().toString();
//        String qe = quantityedit.getText().toString();
        String qte= quantityedit.getText().toString();
        int indexOfDecimal = inv.indexOf(".");
        int finalqte= Integer.parseInt(qte);
        String invoi = inv.substring(0,indexOfDecimal);
        finalinv = Integer.parseInt(invoi);
        finalqe = Integer.parseInt(qte);
        count = finalqte;
        count++;
        quantityedit.setText(""+count);

        if(finalqe<finalinv){
            stockavaialble.setText("In Stock");
            stockavaialble.setTextColor(Color.parseColor("#027C4F"));
        }else {
            stockavaialble.setText("Out of Stock");
            stockavaialble.setTextColor(Color.parseColor("#FF0000"));
        }

    }

    public void decrement(View v){
        if(count<=0){
            count=0;
        }else
            count--;

        String inv = invisible.getText().toString();
        String qe = quantityedit.getText().toString();

        int indexOfDecimal = inv.indexOf(".");
        String invoi = inv.substring(0,indexOfDecimal);
        finalinv = Integer.parseInt(invoi);
        finalqe = Integer.parseInt(qe);
        quantityedit.setText(""+count);
        {
            if(finalqe<=(finalinv + 1 )){
                stockavaialble.setText("In Stock");
                stockavaialble.setTextColor(Color.parseColor("#027C4F"));
            }else if(finalqe>finalinv){
                stockavaialble.setText("Out of Stock");
                stockavaialble.setTextColor(Color.parseColor("#FF0000"));
            }
        }
    }
    private void loadSpinnerData(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray responseArray = jsonObject.getJSONObject("d").getJSONArray("results");
                    for(int i=0;i<responseArray.length();i++){
                        JSONObject jsonObject1=responseArray.getJSONObject(i);
                        String material=jsonObject1.getString("Matnr");
                        String country=jsonObject1.getString("Maktx");
                        String quantity=jsonObject1.getString("Labst");
                        CountryName.add(country);
                        MaterialNum.add(material);
                        Quantity.add(quantity);
                    }
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
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

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void SubmitSalesOrder() {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        StringBuffer responseContent1 = new StringBuffer();
        TextView txtclose;
        TextView txtsalesordernum;
        Button btnsalesordercreated;
        try {
            String username = "fdev_mm";
            String password = "simple@1";
            java.net.URL url = new URL("http://185.95.4.61:8011/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder('1')?$format=json");

            connection = (HttpURLConnection) url.openConnection();
            String userpass = username + ":" + password;
            String basicAuth = "Basic " + Base64.encodeToString(userpass.getBytes(), Base64.NO_WRAP);
            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            URL url1 = new URL("http://185.95.4.61:8011/sap/opu/odata/sap/API_SALES_ORDER_SRV/A_SalesOrder");
            readconn = (HttpURLConnection) url1.openConnection();
            readconn.setRequestMethod("GET");
            readconn.setRequestProperty("Authorization", basicAuth);
            readconn.setRequestProperty("X-CSRF-Token", "Fetch");
            readconn.connect();

            session = getSessionCookies(readconn);
            String xsrfToken = extractXrsfToken(readconn);

            writeconn = (HttpURLConnection) url1.openConnection();
            writeconn.setRequestProperty("x-csrf-token", xsrfToken);
            writeconn.setRequestMethod("POST");
            setSessionCookies(writeconn, session);
            writeconn.setDoInput(true);
            writeconn.setDoOutput(true);
            writeconn.setRequestProperty("Authorization", basicAuth);
            writeconn.setRequestProperty("Content-Type", "application/json");
            writeconn.setRequestProperty("charset", "utf-8");
            writeconn.connect();

            JSONObject jsonObjectFinal = new JSONObject();
            jsonObjectFinal.put("SalesOrderType","ZOR");
            jsonObjectFinal.put("SalesOrganization","2000");
            jsonObjectFinal.put("DistributionChannel","01");
            jsonObjectFinal.put("OrganizationDivision","01");
            jsonObjectFinal.put("SoldToParty",text);
            jsonObjectFinal.put("PurchaseOrderByCustomer","Created via Merchandiser Mobile App");

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
            String formattedDate = df.format(c);

            System.out.println(formattedDate);
            jsonObjectFinal.put("CustomerPurchaseOrderDate","/Date(1603524590000)/");

            JSONArray array = new JSONArray();
            for(int i=0;i<layoutList.getChildCount();i++){
                View item = layoutList.getChildAt(i);
                Spinner spinner = (Spinner)item.findViewById(R.id.searchspinner);
                EditText material = (EditText)item.findViewById(R.id.edit_material);
                EditText quantity = (EditText)item.findViewById(R.id.edit_quantity);
                TextView availStock = (TextView)item.findViewById(R.id.stockavailable);

                if(availStock.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please check the stock before submitting", Toast.LENGTH_SHORT).show();
                    return;
                }else if(availStock.getText().toString().contains("In Stock")) {
                    JSONObject arrayItem = new JSONObject();
                    arrayItem.put("Material", material.getText());
                    arrayItem.put("RequestedQuantity", quantity.getText());
                    array.put(arrayItem);
                    jsonObjectFinal.put("to_Item", array);
                }
                else if(availStock.getText().toString().contains("Out of Stock")){
                    if(layoutList.getChildCount()==1){
                        Toast.makeText(this, "This material is out of stock", Toast.LENGTH_SHORT).show();
                        return;
                    }else
                        continue;
                }}

            OutputStream os = writeconn.getOutputStream();
            BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObjectFinal.toString());
            writer.flush();
            writer.close();
            os.close();
            writeconn.connect();

            int code = writeconn.getResponseCode();

            if (code > 299) {
                reader = new BufferedReader(new InputStreamReader(writeconn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent1.append(line);
                    Toast.makeText(this, "Backend error", Toast.LENGTH_SHORT).show();
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(writeconn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent1.append(line);
                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    InputSource src = new InputSource();
                    src.setCharacterStream(new StringReader(responseContent1.toString()));

                    Document doc = builder.parse(src);
                    String salesOrderNumber = doc.getElementsByTagName("d:SalesOrder").item(0).getTextContent();


                    myDialog.setContentView(R.layout.custompopup);
                    txtsalesordernum =(TextView)myDialog.findViewById(R.id.salesOrderNumberText);
                    txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                    btnsalesordercreated = (Button) myDialog.findViewById(R.id.btnfollow);

                    txtsalesordernum.setText(salesOrderNumber);

                    txtclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent= new Intent(Saleorder.this,custlist.class);
                            startActivity(intent);
                            myDialog.dismiss();
                        }
                    });
                    btnsalesordercreated.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            myDialog.dismiss();
                        }
                    });
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.show();
                }
                reader.close();
            }
            System.out.println(code);
            System.out.println(responseContent1);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    private static final List<String> getSessionCookies(HttpURLConnection conn) {
        Map<String, List<String>> response_headers = conn.getHeaderFields();
        Iterator<String> keys = response_headers.keySet().iterator();
        String key;
        while (keys.hasNext()) {
            key = keys.next();
            if ("set-cookie".equalsIgnoreCase(key)) {
                List<String> session = response_headers.get(key);
                return session;
            }
        }

        return null;
    }

    private static final void setSessionCookies(HttpURLConnection conn, List<String> session) {
        if (session != null) {
            String agregated_cookies = "";
            for (String cookie: session) {
                agregated_cookies += cookie + "; ";
            }
            conn.setRequestProperty("cookie", agregated_cookies);
        }
    }

    private static String extractXrsfToken(HttpURLConnection conn) {
        List<String> value = null;
        Map<String, List<String>> headers = conn.getHeaderFields();
        Iterator<String> keys = headers.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if ("X-CSRF-Token".equalsIgnoreCase(key)) {
                value = headers.get(key);
            }
        }

        if (value == null || value.size() == 0) {
            return null;
        } else {
            return value.get(0);
        }


    }

}


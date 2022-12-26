package com.example.ffc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import static android.provider.Telephony.Mms.Part.TEXT;
import static com.example.ffc.custlist.EXTRA_ADDRESS;
import static com.example.ffc.custlist.EXTRA_CITY;
import static com.example.ffc.custlist.EXTRA_KUNNR;
import static com.example.ffc.custlist.EXTRA_NAME;
import static com.example.ffc.custlist.EXTRA_POSTAL;
import static com.example.ffc.custlist.EXTRA_TELEPHONE;


public class DetailActivity extends AppCompatActivity {
    Button salebutton, statusbutton;
    ImageButton callbutton, mailbutton;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"CutPasteId", "WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String creatorName = intent.getStringExtra(EXTRA_NAME);
        String KUNNR = intent.getStringExtra(EXTRA_KUNNR);
        String City = intent.getStringExtra(EXTRA_CITY);
        String Telephone = intent.getStringExtra(EXTRA_TELEPHONE);
        String Address = intent.getStringExtra(EXTRA_ADDRESS);
        String Postal = intent.getStringExtra(EXTRA_POSTAL);

        TextView textViewCnum = findViewById(R.id.custnumdetail);
        TextView textViewCname = findViewById(R.id.custnamedetail);
        TextView textViewCsnum = findViewById(R.id.custnumbdetail);
        TextView textViewCcity = findViewById(R.id.custcitydetail);
        final TextView textViewCtele = findViewById(R.id.custteledetail);
        final TextView textViewCtelephone = findViewById(R.id.custtelephonedetail);
        TextView textViewCaddress = findViewById(R.id.custaddressdetail);
        TextView textViewCpostal = findViewById(R.id.custpostaldetail);

        textViewCname.setText(creatorName);
        textViewCnum.setText(KUNNR);
        textViewCtelephone.setText(Telephone);
        textViewCsnum.setText("Customer Number :  " + KUNNR);
        textViewCcity.setText("City :  " + City);
        textViewCtele.setText("Telephone :  " + Telephone);
        textViewCaddress.setText("Address :  " + Address);
        textViewCpostal.setText("Postal :  " + Postal);

        SharedPreferences sharedPreferences = getSharedPreferences("KEY", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            editor.putString(TEXT, textViewCnum.getText().toString());
        }
        editor.apply();


        salebutton = findViewById(R.id.saleorderbutton);
        statusbutton = findViewById(R.id.statusbutton);
        callbutton = findViewById(R.id.callButton);
        mailbutton = findViewById(R.id.mailButton);

        salebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, Saleorder.class);
                startActivity(intent);
            }
        });

        statusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, OSMain.class);
                startActivity(intent);
            }
        });

        callbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + textViewCtelephone.getText().toString());

                if (textViewCtelephone.getText().toString().isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Customer Contact Number Unavailable", Toast.LENGTH_SHORT).show();

                } else {
                    Intent i = new Intent(Intent.ACTION_DIAL, u);

                    try {

                        startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}
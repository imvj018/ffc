package com.example.ffc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class fpw1 extends AppCompatActivity {
    TextInputLayout OTP;
    Button subotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpw1);
        OTP = findViewById(R.id.Enter_OTP);
        subotp = findViewById(R.id.submitotp);
        subotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String submotp = OTP.getEditText().getText().toString();
                if (submotp.isEmpty()) {
                    Toast.makeText(fpw1.this, "Enter the OTP", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(fpw1.this, fpw2.class);
                    startActivity(intent);
                }
            }
        });
    }
}
package com.example.ffc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class ForgetPassword extends AppCompatActivity {
    Button getotp;
    TextInputLayout connumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        connumber = findViewById(R.id.Enter_number);
        getotp = findViewById(R.id.otpbutton);

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contnumber = connumber.getEditText().getText().toString();
                if (contnumber.isEmpty()) {
                    Toast.makeText(ForgetPassword.this, "Enter the Registered Contact Number", Toast.LENGTH_SHORT).show();

                } else {

                    Intent intent = new Intent(ForgetPassword.this, fpw1.class);
                    startActivity(intent);
                }
            }
        });
    }
}
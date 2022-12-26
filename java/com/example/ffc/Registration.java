package com.example.ffc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Registration extends AppCompatActivity {

    TextInputLayout mFullname, mUsername, mPhone, mMail, mPassword, mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFullname = findViewById(R.id.Reg_Fullname);
        mUsername = findViewById(R.id.Reg_Username);
        mPhone = findViewById(R.id.Reg_Phone);
        mMail = findViewById(R.id.Reg_Mail);
        mPassword = findViewById(R.id.Reg_Password);
        mCity = findViewById(R.id.Reg_City);

        ImageButton RegButton = findViewById(R.id.regbtn);
        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = mFullname.getEditText().getText().toString();
                String userName = mUsername.getEditText().getText().toString();
                String pHone = mPhone.getEditText().getText().toString();
                String mAil= mMail.getEditText().getText().toString();
                String passWord =  mPassword.getEditText().getText().toString();
                String cIty = mCity.getEditText().getText().toString();
                if(fullName.isEmpty() || userName.isEmpty() || pHone.isEmpty() || mAil.isEmpty() || passWord.isEmpty() || cIty.isEmpty())
                {
                    Toast.makeText(Registration.this, "Enter all the Details", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendMail();
                }
            }
        });
    }

    private void sendMail() {
        String recipientList = "FFCMerchandiserapp.Help@gmail.com";
        String[] recipients = recipientList.split(",");
        String subject = "New Merchandiser Registration";
        String message = "Full Name :  " + mFullname.getEditText().getText().toString() + "\n" +
                "Requested User Name :  " + mUsername.getEditText().getText().toString() + "\n" +
                "Phone Number :  " + mPhone.getEditText().getText().toString() + "\n" +
                "E-Mail ID :  " + mMail.getEditText().getText().toString() + "\n" +
                "Password :  " + mPassword.getEditText().getText().toString() + "\n" +
                "City :  " + mCity.getEditText().getText().toString();


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an App to send Mail"));
    }
}
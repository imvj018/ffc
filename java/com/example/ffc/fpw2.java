package com.example.ffc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class fpw2 extends AppCompatActivity {
    TextInputLayout newPw, conPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpw2);

        newPw = findViewById(R.id.Newpw);
        conPw = findViewById(R.id.Confirmpw);

        ImageButton reqPw = findViewById(R.id.newpwbutton);
        reqPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpassword = newPw.getEditText().getText().toString();
                String conpassword = conPw.getEditText().getText().toString();
                if (newpassword.isEmpty() || conpassword.isEmpty()) {
                    Toast.makeText(fpw2.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();

                } else if (newpassword.equals(conpassword)) {
                    requestPw();

                } else {

                    Toast.makeText(fpw2.this, "Passwords are not same", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestPw() {
        String recipientList = "FFCMerchandiserapp.Help@gmail.com";
        String[] recipients = recipientList.split(",");
        String subject = "Change Password Request";
        String message = "New Password :  " + newPw.getEditText().getText().toString();


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an App to send Mail"));
    }
}
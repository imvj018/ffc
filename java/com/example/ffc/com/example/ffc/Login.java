package com.example.ffc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    TextInputLayout regUname, regPword;
    Button Loginbutton, regbutton, fpwbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        regUname= findViewById(R.id.login_Username);
        regPword=  findViewById(R.id.login_Password);
        Loginbutton= findViewById(R.id.login_button);
        regbutton= findViewById(R.id.Register_cont);
        fpwbutton= findViewById(R.id.forgot_pw);

        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = regUname.getEditText().getText().toString();
                String password = regPword.getEditText().getText().toString();
                        if(username.equals("diva") && password.equals("1234") ||
                           username.equals("vijay") && password.equals("5678"))
                        {
                            Intent intent= new Intent(Login.this,custlist.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                            {
                            Toast.makeText(Login.this,"Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        }

                }


        });

        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this, Registration.class);
                startActivity(intent);

            }
        });

        fpwbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);

            }
        });
    }
}
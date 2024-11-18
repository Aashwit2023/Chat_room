package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class  Login extends AppCompatActivity {
    TextView txtregister;

    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtregister=(TextView) findViewById(R.id.txtregister);
        btn_login=findViewById(R.id.btn_login);

        //-----------Click  here to register-------------
        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Login.this,MainActivity.class);
                startActivity(in);
            }
        });
        //-----------Click here to Login-------------
        EditText txtemail=findViewById(R.id.loginnum_email);
        EditText txtpassword=findViewById(R.id.loginpassword);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=txtemail.getText().toString();
                String password=txtpassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty())
                {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent in=new Intent(Login.this, ChatBox.class);
                                startActivity(in);
                            }
                            else {
                                Toast.makeText(Login.this, "Invalid Email and Password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    //create a new if else and check email are valid or not and also check in new if else block are password are valid or not
                }
                else {
                    Toast.makeText(Login.this, "Please fill all information Properly", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //-------Alerady login then open a Chatbox---------------
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            Intent intent=new Intent(this,ChatBox.class);
            startActivity(intent);
        }
    }
    //-----------Click here to back button-------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
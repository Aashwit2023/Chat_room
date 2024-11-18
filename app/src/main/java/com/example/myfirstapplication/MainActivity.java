package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
TextView txtlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtlogin=findViewById(R.id.txtlogin);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Login.class);
                startActivity(intent);

            }
        });

        //Click here to register
        Button btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText txtname = findViewById(R.id.txt_name);
                EditText txtemail = findViewById(R.id.txt_email);
                EditText txtPassword = findViewById(R.id.txt_password);
                String name = txtname.getText().toString();
                String email = txtemail.getText().toString();
                String password = txtPassword.getText().toString();
                RadioGroup rdbgender = findViewById((R.id.rdb_gender));
                RadioButton rdbselectedgen = findViewById(rdbgender.getCheckedRadioButtonId());
                String gender = rdbselectedgen.getText().toString();
                //---------------let's save the data in authentication and Realtime Database------------
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !gender.isEmpty()) {
                    //************ this is a loading dialog *********
                    ProgressDialog dialog=new ProgressDialog(MainActivity.this);
                            dialog.setTitle("Please Wait");
                            dialog.show();
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           //************ close the loading dialog
                            dialog.dismiss();
                            //-------------------User added in Authentication, now let's save data in realtime databse------------------
                            //-------------------download UID of user From firebase auth--------------
                            if (task.isSuccessful()) {
                                String UDI = task.getResult().getUser().getUid();
                                HashMap<String, String> user = new HashMap<>();
                                user.put("name", name);
                                user.put("email", email);
                                user.put("password", password);
                                user.put("gender", gender);
                                FirebaseDatabase.getInstance().getReference().child("user").child(UDI).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isCanceled()) {
                                            Toast.makeText(MainActivity.this, "is canceled", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "Now! You are registered", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(MainActivity.this, Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, task.getException() + " ", Toast.LENGTH_SHORT).show();

                            }
                        }

                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Please fill all Fields Properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
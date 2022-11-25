package com.example.a5mobprog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    public Button login, register;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btnlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database db = new database(Login.this);
                ArrayList<HashMap<String, String>> userList = db.GetUsers();
                email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
                password = (EditText)findViewById(R.id.editTextTextPersonName4);
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();


                for(HashMap<String, String> x : userList){
                    if(x.containsValue(email_text) && x.containsValue(password_text)){
                        db.close();
                        Intent intent = new Intent(Login.this, User.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Incorrect password or email",Toast.LENGTH_LONG).show();
                        db.close();
                    }
                }


            }
        });


        register = (Button) findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
                password = (EditText)findViewById(R.id.editTextTextPersonName4);

                String user_email = email.getText().toString();
                String user_password = password.getText().toString();

                database db = new database(Login.this);
                String department = null;
                Object name = null;
                db.insertUserDetails(user_email,user_password, null, null, null);
                Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_LONG).show();

            }
        });
    }




}
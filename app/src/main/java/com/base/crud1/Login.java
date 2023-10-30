package com.base.crud1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText email, password, role;
    CheckBox term;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        role = findViewById(R.id.et_type);
        term = findViewById(R.id.checkBoxTerms);
        login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId = email.getText().toString();
                String passwordText = password.getText().toString();
                if(emailId.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(emailId, passwordText);
                            if(userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"Invalid credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }  else {
                                String name = userEntity.email;
                                SharedPreferences preferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("userId", userEntity.getUserId()); // Use the appropriate user ID field from UserEntity.
                                editor.apply();
                                startActivity(new Intent(Login.this, Dashboard.class).putExtra("name",name));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        email.setText("");
                                        password.setText("");
                                        role.setText("");
                                        term.setChecked(false);
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });

    }
}
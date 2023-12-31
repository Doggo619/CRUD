package com.base.crud1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_PICKER_REQUEST = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    EditText email, password, role;
    CheckBox term;
    Button signup, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        role = findViewById(R.id.et_type);
        term = findViewById(R.id.checkBoxTerms);
        signup = findViewById(R.id.btn_signup);
        login = findViewById(R.id.btn_login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = UUID.randomUUID().toString();

                UserEntity userEntity = new UserEntity();
                userEntity.setUserId(userId);
                userEntity.setEmail(email.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setRole(role.getText().toString());

                if(validateInput(userEntity)) {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

    }

    private Boolean validateInput(UserEntity userEntity) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean isValid = true;

        if (userEntity.getEmail().isEmpty() || !userEntity.getEmail().matches(emailPattern)) {
            email.setError("Enter a valid email address");
            isValid = false;
        }

        if (userEntity.getPassword().length() < 6) {
            password.setError("Password must be at least 6 characters");
            isValid = false;
        }

        if (userEntity.getRole().isEmpty()) {
            role.setError("Role is required");
            isValid = false;
        }

        if (!term.isChecked()) {
            term.setError("Please agree to terms and conditions");
            Toast.makeText(getApplicationContext(), "Please agree to terms and conditions", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }
}
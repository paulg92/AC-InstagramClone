package com.example.ac_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtEmail, edtPassword;
    private Button loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        edtEmail = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPassword);

        loginButton = findViewById(R.id.edtLoginActivity);
        signUpButton = findViewById(R.id.edtSignupLoginActivity);

        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser();
            ParseUser.logOut();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.edtLoginActivity:

                loginButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        ParseUser.logInInBackground(edtEmail.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null) {
                                    FancyToast.makeText(LoginActivity.this,
                                            user.get("username") + "" +
                                                    " is logged in succesfully", FancyToast.LENGTH_LONG,
                                            FancyToast.SUCCESS, true).show();
                                    transitionToSocialMediaActivity();
                                } else {
                                    FancyToast.makeText(LoginActivity.this,
                                            e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,
                                            true).show();
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.edtSignupLoginActivity:

                signUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                break;
        }

    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }

}

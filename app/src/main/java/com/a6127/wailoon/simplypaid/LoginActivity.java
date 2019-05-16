package com.a6127.wailoon.simplypaid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail,inputPassword;
    private Button btnLogin,btnSignup;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText)findViewById(R.id.email_editText);
        inputPassword = (EditText)findViewById(R.id.password_editText);
        btnLogin = (Button) findViewById(R.id.login_Btn);
        btnSignup = (Button)findViewById(R.id.signup_Btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        findViewById(R.id.login_Btn).setOnClickListener(this);
        findViewById(R.id.signup_Btn).setOnClickListener(this);

        progressBar.setVisibility(View.GONE);

    }

    private void loginUser(){
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if(email.isEmpty()){
            inputEmail.setError("Email is required");
            inputEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            inputEmail.setError("Please enter a valid email");
            inputEmail.requestFocus();
            return;
        }

        if(password.length() < 6){
            inputPassword.setError("Password too short, it must 6 characters");
            inputPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                Intent intent = new Intent(LoginActivity.this,OnboardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "Account haven't verified, Please check your mailbox", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup_Btn:
                startActivity(new Intent(this,SignUpActivity.class));
                break;
            case R.id.login_Btn:
                loginUser();
                break;


        }
    }
}

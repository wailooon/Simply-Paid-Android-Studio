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
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText rInputEmail,rInputPassword;
    private Button btnLogin,btnSignup;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        rInputEmail = (EditText)findViewById(R.id.r_email_editText);
        rInputPassword = (EditText)findViewById(R.id.r_password_editText);
        btnLogin = (Button) findViewById(R.id.r_login_Btn);
        btnSignup = (Button)findViewById(R.id.r_signup_Btn);
        progressBar = (ProgressBar) findViewById(R.id.r_progressBar);

        findViewById(R.id.r_signup_Btn).setOnClickListener(this);
        findViewById(R.id.r_login_Btn).setOnClickListener(this);


        auth = FirebaseAuth.getInstance();

    }

    private void registerUser(){
        String email = rInputEmail.getText().toString().trim();
        String password = rInputPassword.getText().toString().trim();

        if(email.isEmpty()){
            rInputEmail.setError("Email is required");
            rInputEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            rInputPassword.setError("Password is required");
            rInputPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            rInputEmail.setError("Please enter a valid email");
            rInputEmail.requestFocus();
            return;
        }

        if(password.length() < 6){
            rInputPassword.setError("Password too short, it must 6 characters");
            rInputPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            FirebaseAuth.getInstance().getCurrentUser()
                                    .sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this, "Successfully Registered, Account verification email sent: " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(SignUpActivity.this,"Failed to send verification email",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                    Toast.makeText(getApplicationContext(), "This email already registered, Please try again ", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), "Account haven't verified, Please check your mailbox", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.r_signup_Btn:
                registerUser();
                break;
            case R.id.r_login_Btn:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.forgotPw_Btn:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    private void toastMeassge(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

package com.beyzanur.expiration_date_reminder;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.Nullable;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private LinearLayout mLinear;
    private TextInputLayout inputEmail, inputPassword;
    private EditText editEmail, editPassword;
    private String txtEmail, txtPassword;

    private void init(){
        mLinear = (LinearLayout)findViewById(R.id.giris_yap_linear);
        mAuth = FirebaseAuth.getInstance();

        inputEmail = (TextInputLayout)findViewById(R.id.login_input_Email);
        inputPassword = (TextInputLayout)findViewById(R.id.login_input_password);

        editEmail = (EditText)findViewById(R.id.login_edit_Email);
        editPassword = (EditText)findViewById(R.id.login_edit_password);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }


    public void btnLogin(View v){
        txtEmail = editEmail.getText().toString();
        txtPassword = editPassword.getText().toString();

        if (!TextUtils.isEmpty(txtEmail)){
            if (!TextUtils.isEmpty(txtPassword)){
                mProgress = new ProgressDialog(this);
                mProgress.setTitle("Logging in...");
                mProgress.show();

                mAuth.signInWithEmailAndPassword(txtEmail, txtPassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    progressAyar();
                                    Toast.makeText(LoginActivity.this, "You have successfully logged in", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }else{
                                    progressAyar();
                                    Snackbar.make(mLinear, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                                }

                            }
                        });
            }else
                inputPassword.setError("Please enter a valid password.");
        }else
            inputEmail.setError("Please enter a valid email address.");
    }
    public void btnForgetPassword(View v){
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    public void btnSignUp(View v){
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
    private void progressAyar(){
        if (mProgress.isShowing())
            mProgress.dismiss();
    }
}
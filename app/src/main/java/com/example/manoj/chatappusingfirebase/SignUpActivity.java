package com.example.manoj.chatappusingfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    TextView tvExistingUser;
    EditText etSignInEmial,etSignInPassword;
    Button btnSignUp;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        tvExistingUser = (TextView) findViewById(R.id.tv_ExistingUser);
        etSignInEmial = (EditText) findViewById(R.id.et_SignInEmail);
        etSignInPassword = (EditText) findViewById(R.id.et_SignInPassword);
        btnSignUp = (Button) findViewById(R.id.btn_SignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpUser();
            }
        });

        tvExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(SignUpActivity.this,LoginActivity.class);
                finish();
                startActivity(toLogin);
            }
        });

    }

    public void SignUpUser()
    {
        String Email = etSignInEmial.getText().toString().trim();
        String Password = etSignInPassword.getText().toString();

        if(Email.length()==0)
        {
            Toast.makeText(SignUpActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Password.trim().length()==0)
        {
            Toast.makeText(SignUpActivity.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUpActivity.this, "Congratulations.. Registration Sucessfull ",
                                    Toast.LENGTH_SHORT).show();
                            Intent toProfile = new Intent(SignUpActivity.this,ProfileActivity.class);
                            finish();
                            startActivity(toProfile);
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "Sign Up Failed.. Please Try Again",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}

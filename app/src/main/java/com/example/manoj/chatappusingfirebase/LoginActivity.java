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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText etEmail,etPassword;
    TextView tvNewUser;
    Button btnLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            Intent i = new Intent(LoginActivity.this,Users_Display_For_Chat_Activity.class);
            finish();
            startActivity(i);
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In...");

        tvNewUser = (TextView) findViewById(R.id.tv_NewUser);
        etEmail = (EditText) findViewById(R.id.et_LoginEmail);
        etPassword = (EditText) findViewById(R.id.et_LoginPassword);
        btnLogin = (Button) findViewById(R.id.btn_Login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginUser();

            }
        });
        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignUp = new Intent(LoginActivity.this,SignUpActivity.class);
                finish();
                startActivity(toSignUp);
            }
        });

    }

    public void LoginUser()
    {
        String Email = etEmail.getText().toString().trim();
        String Password = etPassword.getText().toString();

        if(Email.length()==0)
        {
            Toast.makeText(LoginActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Password.trim().length()==0)
        {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Intent gotoUsers = new Intent(LoginActivity.this,Users_Display_For_Chat_Activity.class);
                            finish();
                            startActivity(gotoUsers);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Login Failed Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

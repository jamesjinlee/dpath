package edu.dartmouth.cs.d_path;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    TextInputEditText etPasswordInput;
    TextInputEditText etEmailInput;
    Button btLogin;
    Button btRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //hide the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //get instance of firebase authentication
        mAuth = FirebaseAuth.getInstance();

        etPasswordInput = findViewById(R.id.password_input);
        etEmailInput = findViewById(R.id.email_input);
        btLogin = findViewById(R.id.login_button);
        btRegister = findViewById(R.id.register_button);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in automatically go to main activity
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user){
        if (user!=null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
    }
    //clicking register button
    public void onRegister(View v){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    //clicking login button
    public void onLogin(View v){
        final String email = etEmailInput.getText().toString();
        final String password = etPasswordInput.getText().toString();
        // Sign in
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if login in authentification is successful
                        if (task.isSuccessful()) {
                            Log.d(TAG, "SIGN IN SUCCESS");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //if login is unsuccessful
                        } else {
                            Log.d(TAG, "SIGN IN FAIL");
                            Toast.makeText(LoginActivity.this, getString(R.string.auth_fail_message), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
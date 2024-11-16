package dev.md19303.asm_and103;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText txtEmail, txtPass;
    TextView tvForgotPassword, tvSignUp;
    Button btnSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Handle SignUp button click
        tvSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Handle ForgotPassword button click
        tvForgotPassword.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();
            if (!email.isEmpty()) {
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Reset Password Email Sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Reset Password Email Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                txtEmail.setError("Email is required");
            }
        });

        // Handle SignIn button click
        btnSignIn.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();
            String pass = txtPass.getText().toString().trim();

            if (email.isEmpty()) {
                txtEmail.setError("Email is required");
                return;
            }

            if (pass.isEmpty()) {
                txtPass.setError("Password is required");
                return;
            }

            // Attempt to sign in with Firebase Authentication
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish(); // Optional: Close the login activity
                } else {
                    Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Login_Error", task.getException().getMessage());
                }
            });
        });
    }
}

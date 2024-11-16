package dev.md19303.asm_and103;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText txtEmail,txtPass,txtRe_pass;
    Button btnSignUp;
    TextView tvSignIn;
    private FirebaseAuth mAthu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);
        txtRe_pass = findViewById(R.id.txtRe_pass);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);

        mAthu = FirebaseAuth.getInstance();

        tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        btnSignUp.setOnClickListener(v -> {
            String email = txtEmail.getText().toString();
            String pass = txtPass.getText().toString();
            String re_pass = txtRe_pass.getText().toString();

            if (email.isEmpty() || pass.isEmpty() || re_pass.isEmpty()) {

                if (email.isEmpty()) {
                    txtEmail.setError("Email is required");
                }
                if (pass.isEmpty()) {
                    txtPass.setError("Password is required");
                }
            } else if(!pass.equals(re_pass)){
                txtRe_pass.setError("Password not match");
            }
            else {
                mAthu.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Register Susses", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
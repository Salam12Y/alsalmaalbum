package abbas.samih.salamalbums;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import abbas.samih.salamalbums.FullExample.MainSimpleLabelActivity;

public class logInActivity extends AppCompatActivity

{
    private TextInputEditText etEmail,etpassword;
    private Button btnLogIn,btnRegister;
    private TextView tvMember;
    private ImageView imgv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        etEmail=findViewById(R.id. etEmail);
        etpassword=findViewById(R.id. etpassword);
        btnLogIn=findViewById(R.id. btnLogIn);
        btnRegister=findViewById(R.id. btnRegister);
        tvMember=findViewById(R.id. tvMember);
        imgv2=findViewById(R.id. imgv2);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }

            private void validate() {
                boolean isOk = true;
                String email = etEmail.getText().toString();
                String password = etpassword.getText().toString();
                if (email.length() == 8) {
                    etEmail.setError("enter email");
                }
                if (password.length() < 8) {
                    etpassword.setError("password at least 8 letters");
                }
                if (isOk) {
                    signingin(email, password);
                }
            }

            private void signingin(String email, String passw) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Signing in successful", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainSimpleLabelActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "signing in error:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
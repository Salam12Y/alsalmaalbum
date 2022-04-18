package abbas.samih.salamalbums;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class SignInActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword, etRepassword, etPhone, etFullName;
    private Button btnSave;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRepassword = findViewById(R.id.etRePassword);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
        etFullName = findViewById(R.id.etFullName);
        tvSignUp = findViewById(R.id.tvSignUp);
        /**
         *
         */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }


            private void validate() {
                boolean isOk = true;
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String rePassword = etRepassword.getText().toString();
                String phone = etPhone.getText().toString();
                String fullName = etFullName.getText().toString();


                if (email.length() < 5 || email.indexOf('@') <= 0) {
                    etEmail.setError("wrong email syntax");
                    isOk = false;
                }
                // فحص طول كلمة السر
                if (password.length() < 8) {
                    etPassword.setError("at least 8");
                    isOk = false;
                }
                //  فحص اذا  كلمة سر (repassword) تساوي كلمة السر (password)
                if (password.equals(rePassword) == false) {
                    etPassword.setError("not equal passwords");
                    isOk = false;
                }
                // فحص اذا طول الاسم الكامل يساوي 8
                if (fullName.length() == 0) {
                    etFullName.setError("must to enter full name");
                    isOk = false;
                }
                if (isOk) {
                    CreateAccount(email, password);
                }
            }

            private void CreateAccount(String email, String password) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                //request                                                 //wait for response
               auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()==true)
                       {
                           finish();
                           startActivity(new Intent(getApplicationContext(),MainSimpleLabelActivity.class));
                       }
                       else
                       {
                           //dialog
                           Toast.makeText(getApplicationContext(), "error create account"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }
        }




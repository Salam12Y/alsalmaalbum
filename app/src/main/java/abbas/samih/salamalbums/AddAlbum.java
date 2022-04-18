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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Mydata.MyAlbum;

public class AddAlbum extends AppCompatActivity {
    private TextView tvNewAlbum;
    private TextInputEditText TinputET, contTeInp;
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);
        tvNewAlbum = findViewById(R.id.tvNewAlbum);
        TinputET = findViewById(R.id.TinputET);
        btnAdd = findViewById(R.id.btnAdd);
        contTeInp = findViewById(R.id.contTeInp);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AllAlbumsActivity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }



    public void validate() {
        String name = TinputET.getText().toString();
        String Content = contTeInp.getText().toString();
        boolean isOk = true;
        if (name.length() == 0) {
            TinputET.setError(" must inter subject");
            isOk = false;
        }
        if (isOk) {
            MyAlbum t = new MyAlbum();
            t.setName(name);
            t.setContent(Content);
            //current user uid
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            t.setOwner(uid);
            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference ref=db.getReference();
            String key=ref.child("myAlbums").push().getKey();
            t.setKey(key);
            // add Album to current user.
            //just this user can see this album
            ref.child("myAlbums").child(key).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {//response
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "successfuly adding", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "add not successful", Toast.LENGTH_SHORT).show();
                    }
                }


            });

        }


    }
}

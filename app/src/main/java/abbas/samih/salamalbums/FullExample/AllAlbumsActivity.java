package abbas.samih.salamalbums.FullExample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import Mydata.MyAlbum;
import Mydata.MyAlbumAdapter;
import Mydata.MyPic;
import abbas.samih.salamalbums.AddAlbum;
import abbas.samih.salamalbums.R;

public class AllAlbumsActivity extends AppCompatActivity {

    private TextView tvAllAlbum;
    private FloatingActionButton floatBtn;
    private Uri filePath;
    private Uri toUploadimageUri;
    private Uri downladuri;
    StorageTask uploadAlbum;
    private MyAlbum t;
    //read 1:
    private ListView ListV;
    private MyAlbumAdapter AlbumAdapter;

    //تحديد الواجهة الشاشة وبناء كائناتها (تعمل مرة واحدة فقط عند بناء الشاشة)
    // ممكن تجهيز الكائنات ومؤشراتها قبل العرض على الشاشة
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //هون بتبلش
        setContentView(R.layout.activity_all_albums);
        floatBtn = findViewById(R.id.floatBtn);
        // read 2:
        //تعيد مؤشر لكائن تم بناؤه على واجهة المستعمل التي ستعرض
        ListV = findViewById(R.id.ListV);
        AlbumAdapter = new MyAlbumAdapter(this, R.layout.item_album);
        // read 3:set adapter to listView (connect the data to listView)
        ListV.setAdapter(AlbumAdapter);
        //todo from where i came
        Intent i =getIntent();
        if(i!=null && i.getExtras()!=null) {
            Uri imageUri = (Uri) i.getExtras().get("image");
            if (imageUri != null) {
                ListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyAlbum item = (MyAlbum) parent.getItemAtPosition(position);
                        uploadImage(item.getName(), imageUri);// todo add album name
                    }
                });
            }
        }
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddAlbum.class));
            }
        });
    }

        // read 4:
        @Override
        protected void onResume() {
        super.onResume();
        readDataFromFireBase("");
        }
        // read :

        /**
         * read tasks from fireBase and fill the adapter data structure
         * s- is text to search, if it  is empty the method show all results
         * @param s
         */



    private void readDataFromFireBase(String s) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getUid();// current user id
        //اضافة امكانية "الحتلنة" بكل تغيير سيحصل على القيم في قاعدة البيانات
        ref.child("myAlbums").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AlbumAdapter.clear();
                for (DataSnapshot d:dataSnapshot.getChildren())
                {
                    MyAlbum t= d.getValue(MyAlbum.class);
                    AlbumAdapter.add(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }

    //upload:
    private void uploadImage(String name, Uri filePath) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            FirebaseStorage storage= FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            final StorageReference ref = storageReference.child("images/"+name+"/"+ UUID.randomUUID().toString());
            uploadAlbum=ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    downladuri = task.getResult();
                                    saveMyPic(name,downladuri);
                                  //  t.setImage(downladuri.toString());


                                }

                        });

                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }else
        {
            t.setImage("");

        }
    }

    private void saveMyPic(String name, Uri downladuri) {
        MyPic t = new MyPic();
        t.setName(name);
        t.setImage(downladuri.toString());
        //current user uid
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        t.setOwner(uid);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference ref=db.getReference();
        String key=ref.child(uid).child("myAlbums").child(name).push().getKey();
        t.setKey(key);
        // add Album to current user.
        //just this user can see this album
        ref.child(uid).child("myAlbums").child(name).child(key).setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
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
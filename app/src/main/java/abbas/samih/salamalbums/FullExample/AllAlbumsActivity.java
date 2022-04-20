package abbas.samih.salamalbums.FullExample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Mydata.MyAlbum;
import Mydata.MyAlbumAdapter;
import abbas.samih.salamalbums.AddAlbum;
import abbas.samih.salamalbums.R;

public class AllAlbumsActivity extends AppCompatActivity {

    private TextView tvAllAlbum;
    private FloatingActionButton floatBtn;
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
}
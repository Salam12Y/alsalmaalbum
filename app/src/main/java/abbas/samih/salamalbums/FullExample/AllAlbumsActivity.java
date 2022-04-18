package abbas.samih.salamalbums.FullExample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import abbas.samih.salamalbums.AddAlbum;
import abbas.samih.salamalbums.R;

public class AllAlbumsActivity extends AppCompatActivity {

    private TextView tvAllAlbum;
    private ListView ListV;
    private FloatingActionButton floatBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_albums);
        tvAllAlbum = findViewById(R.id.tvAllAlbum);
        ListV = findViewById(R.id.ListV);
        floatBtn = findViewById(R.id.floatBtn);


        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddAlbum.class));
            }
        });
    }
}
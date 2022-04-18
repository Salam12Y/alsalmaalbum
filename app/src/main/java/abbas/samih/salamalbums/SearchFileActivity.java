package abbas.samih.salamalbums;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

public class SearchFileActivity extends AppCompatActivity
{
    private SearchView searchVie;
    private TextView tvFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_file);

        searchVie=findViewById(R.id.searchVie);
        tvFileName=findViewById(R.id.tvFileName);


    }
}

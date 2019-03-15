package pl.menagochicken.memorableplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private List<String> memorablePlacesList;
    private ListView memorablePlacesListView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memorablePlacesList= new ArrayList<>();
        memorablePlacesList.add("Add new place");

        memorablePlacesListView = findViewById(R.id.MemorblePlacesList);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,memorablePlacesList);

        memorablePlacesListView.setAdapter(arrayAdapter);

        memorablePlacesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("placeNumber", position);
                startActivity(intent);

            }
        });
    }
}

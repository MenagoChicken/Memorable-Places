package pl.menagochicken.memorableplaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static List<String> memorablePlacesList = new ArrayList<>();
    public static List<LatLng> locations = new ArrayList<>();
    public static ArrayAdapter<String> arrayAdapter;

    private Intent intent;
    private ListView memorablePlacesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memorablePlacesList.add("Add new place");
        locations.add(new LatLng(0, 0));

        memorablePlacesListView = findViewById(R.id.MemorblePlacesList);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memorablePlacesList);

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

package pl.menagochicken.memorableplaces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> memorablePlacesList = new ArrayList<>();
    public static ArrayList<LatLng> locations = new ArrayList<>();
    public static ArrayAdapter<String> arrayAdapter;

    private Intent intent;
    private ListView memorablePlacesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("pl.menagochicken.memorableplaces", Context.MODE_PRIVATE);

        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        memorablePlacesList.clear();
        latitudes.clear();
        latitudes.clear();
        locations.clear();

        try {
            memorablePlacesList = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitudes", ObjectSerializer.serialize(new ArrayList<String>())));
            longitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longitudes", ObjectSerializer.serialize(new ArrayList<String>())));


        } catch (IOException e) {
            e.printStackTrace();
        }

        if (memorablePlacesList.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0) {

            if (memorablePlacesList.size() == latitudes.size() && latitudes.size() == longitudes.size()) {

                for (int i = 0; i < latitudes.size(); i++) {
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
        } else {
            memorablePlacesList.add("Add new place");
            locations.add(new LatLng(0, 0));
        }

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
                                                       }
        );

        memorablePlacesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int itemClicked = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                memorablePlacesList.remove(itemClicked);
                                locations.remove(itemClicked);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getSharedPreferences("pl.menagochicken.memorableplaces", Context.MODE_PRIVATE);

                                try {
                                    ArrayList<String> latitude = new ArrayList<>();
                                    ArrayList<String> longitude = new ArrayList<>();

                                    for (LatLng cordinates : MainActivity.locations) {
                                        latitude.add(Double.toString(cordinates.latitude));
                                        longitude.add(Double.toString(cordinates.longitude));
                                    }

                                    sharedPreferences.edit().putString("places", ObjectSerializer.serialize(MainActivity.memorablePlacesList)).apply();
                                    sharedPreferences.edit().putString("latitudes", ObjectSerializer.serialize(latitude)).apply();
                                    sharedPreferences.edit().putString("longitudes", ObjectSerializer.serialize(longitude)).apply();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(getApplicationContext(), "Location deleted", Toast.LENGTH_LONG).show();

                            }
                        }).setNegativeButton("No", null)
                        .show();


                return true;
            }
        });
    }
}

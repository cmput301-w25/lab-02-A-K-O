package com.example.listycity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button add_city_button;
    Button remove_city_button;

    int current_list_index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //do this
        setContentView(R.layout.activity_main); //and this first always

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        add_city_button = findViewById(R.id.add_city_button);
        remove_city_button = findViewById(R.id.remove_city_button);

        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Paris", "London",};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.context, dataList);

        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                current_list_index = position;
            }
        });

        add_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCityDialog();
                }
        });

        remove_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataList.isEmpty() || current_list_index < 0 || current_list_index >= dataList.size()) {
                    return;
                }
                dataList.remove(current_list_index);
                cityAdapter.notifyDataSetChanged();
                current_list_index = -1;
            }
        });
    }

    //learned and used general structure of AlertDialog
    //taken from https://stackoverflow.com/questions/10903754/input-text-dialog-android
    //reply/answer from Michal, edited by Stevisiak
    //taken by: Aaron Onate on Jan 17, 2025
    private void AddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City");

        final EditText input_field = new EditText(this);
        input_field.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input_field);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input_string = input_field.getText().toString().trim();
                if (!input_string.isEmpty()) {
                    dataList.add(input_string);
                    cityAdapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
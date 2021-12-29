package com.example.itssbandroidtraining;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AmionicScreen extends AppCompatActivity {

    Spinner from, to;
    List<Aiports> airportsList = new ArrayList<>();
    List<Aiports> airportsList2 = new ArrayList<>();
    int fromAirport=0,toAirport=0;
    EditText flightDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amionic_screen);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        flightDate=findViewById(R.id.edtFlightDate);
        loadSpinner();
        from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Aiports selectedFrom= (Aiports) from.getSelectedItem();
                fromAirport=selectedFrom.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Aiports selectedTo= (Aiports) to.getSelectedItem();
                toAirport=selectedTo.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadSpinner() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("http://10.0.2.2:5000/api/port/list")
                .method("GET", null)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Failure", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String res = response.body().string();
                    JSONArray jsonArray = new JSONArray(res);
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject obj= jsonArray.getJSONObject(i);
                        int id= obj.getInt("id");
                        String name=obj.getString("name");
                        airportsList.add(new Aiports(id,name));
                        airportsList2.add(new Aiports(id,name));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayAdapter<Aiports> arrayAdapter=new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,airportsList);
                                ArrayAdapter<Aiports> arrayAdapter2=new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,airportsList);
                                from.setAdapter(arrayAdapter);
                                to.setAdapter(arrayAdapter2);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void searchFlights(View view) {
        if(fromAirport==toAirport){
            showAlertDialog();
            return;
        }
        String date=flightDate.getText().toString();
        String url="http://10.0.2.2:5000/api/schedule/list?from="+fromAirport+"&to="+toAirport+"&date="+date;
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("Failure",e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String res=response.body().string();
                Log.d("Response",res);
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("From and To Airports cannot be same");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
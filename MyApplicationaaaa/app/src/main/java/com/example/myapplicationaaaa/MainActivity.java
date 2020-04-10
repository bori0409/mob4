package com.example.myapplicationaaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;

import java.math.RoundingMode;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String roomstag = "MYROOMS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAndShowAllFreeRooms();
    }

    private void getAndShowAllFreeRooms() {
        RoomReservationService service = ApiSet.getRoomService();
      //  Call<List<Room>> getAllRooms = service.getAllRooms();
        Call<List<Room>> getFreeRoomsCall = service.getFreeRooms(Math.toIntExact(System.currentTimeMillis() / 1000));
        getFreeRoomsCall.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()){
                    List<Room> allfreerooms = response.body();
                    Log.d(roomstag, allfreerooms.toString());
                    populateRecyclerView(allfreerooms);
                }else {
                    String message = " Problem" + response.code()+" "+ response.message();
                Log.d(roomstag, message);

                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
               Log.e(roomstag,t.getMessage());

            }
        });
    }
    private void populateRecyclerView(List<Room> allfreerooms){
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdaptar adapter = new RecyclerViewAdaptar<>(allfreerooms);
        recyclerView.setAdapter(adapter);



    }
}
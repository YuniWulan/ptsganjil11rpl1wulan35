
package com.example.ptsganjil202111rpl1wulan35;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String URL = "https://www.thesportsdb.com/api/v1/json/1/all_sports.php";
    private RecyclerView recyclerView;
    ArrayList<TeamModel> arrayList;
    TeamAdapter adapter;
    FloatingActionButton fabFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        arrayList = new ArrayList<>();
        fabFav = findViewById(R.id.favoritebtn);
        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent);
            }
        });


        getData();


    }
    private void getData(){

        AndroidNetworking.get(URL)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray teamsArray = response.getJSONArray("sports");
                            for (int i = 0; i < teamsArray.length(); i++){
                                JSONObject teamObject = teamsArray.getJSONObject(i);
                                String sport = teamObject.getString("strSport");
                                String desc = teamObject.getString("strSportDescription");
                                String image = teamObject.getString("strSportThumb");
                                arrayList.add(new TeamModel(image, sport, desc));

                                adapter = new TeamAdapter(getApplicationContext(), arrayList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);

                                adapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        Intent intent = new Intent(getApplicationContext(), ActivityDetail.class);
                                        intent.putExtra("name", arrayList.get(position).getSport());
                                        intent.putExtra("desc", arrayList.get(position).getDesc());
                                        intent.putExtra("image", arrayList.get(position).getImage());
                                        intent.putExtra("Favorite", "false");
                                        startActivity(intent);
                                    }
                                });

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });


    }

}
package com.example.ptsganjil202111rpl1wulan35;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Realm realm;
    RealmHelper realmHelper;
    List<TeamModel> teamlist;
    TeamAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Setup Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        teamlist = new ArrayList<>();

        teamlist = realmHelper.getAllTeams();

        show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        teamAdapter.notifyDataSetChanged();
        show();
    }

    public void show(){
        teamAdapter = new TeamAdapter(this, teamlist);
        recyclerView.setAdapter(teamAdapter);

        teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ActivityDetail.class);
                intent.putExtra("id", teamlist.get(position).getId());
                intent.putExtra("name", teamlist.get(position).getSport());
                intent.putExtra("desc", teamlist.get(position).getDesc());
                intent.putExtra("image", teamlist.get(position).getImage());
                intent.putExtra("Favorite", true);
                startActivity(intent);
            }
        });
    }
}
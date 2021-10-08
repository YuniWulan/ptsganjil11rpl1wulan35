package com.example.ptsganjil202111rpl1wulan35;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ActivityDetail extends AppCompatActivity {
    String name, desc, image;
    FloatingActionButton fabFav;
    TeamModel teamModel;
    Realm realm;
    RealmHelper realmHelper;
    boolean Favorite;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);

        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        TextView tv_name = findViewById(R.id.tv_name_detail);
        TextView tv_desc = findViewById(R.id.tv_desc_detail);
        ImageView img_team = findViewById(R.id.img_team_detail);
        fabFav = findViewById(R.id.favoritebtn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             name = bundle.getString("name");
             desc = bundle.getString("desc");
             image = bundle.getString("image");
             Favorite = bundle.getBoolean("Favorite");

             if (Favorite) id = bundle.getInt("id");

            tv_name.setText(name);
            tv_desc.setText(desc);
            Picasso.get().load(image).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round).into(img_team);
        }

        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Favorite) {
                    realmHelper.delete(id);
                    Toast.makeText(ActivityDetail.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    teamModel = new TeamModel(image, name, desc);
                    realmHelper.save(teamModel);

                    Toast.makeText(ActivityDetail.this, "Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
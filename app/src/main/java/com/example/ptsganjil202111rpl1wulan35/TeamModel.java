package com.example.ptsganjil202111rpl1wulan35;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TeamModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String sport,image,desc;

    public TeamModel (){}

    public TeamModel(String image, String sport, String desc) {
        this.sport = sport;
        this.image = image;
        this.desc = desc;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public void setSport(String sport) { this.sport = sport; }

    public String getSport() { return sport; }

    public void setImage(String image) { this.image = image; }

    public String getImage() { return image; }

    public void setDesc(String desc) { this.desc = desc; }

    public String getDesc() { return desc; }


}

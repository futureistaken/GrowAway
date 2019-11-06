package com.sarah.growaway;

import java.util.List;

public class Plant {
    private String Name;
    private String Water;
    private String Sun;


public Plant(){


}

public void setName(String Name) {
    this.Name = Name;
}

public void setWater(String Water) {this.Water = Water;}

public void setSun(String Sun){this.Sun = Sun;}


public Plant(String Name, String Water, String Sun){
    this.Name = Name;
    this.Sun = Sun;
    this.Water = Water;
}

public String getName() {return Name; }
public String getWater() {return Water; }
public String getSun() {return Sun; }

}

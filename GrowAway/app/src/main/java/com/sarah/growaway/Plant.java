package com.sarah.growaway;

import java.util.List;

public class Plant {
    private String Image;
 //   private String Description;
    private String Name;
    private String Water;
    private String Sun;


public Plant(){


}

public void setImage(String Image) {this.Image = Image;}

public void setName(String Name) {
    this.Name = Name;
}

public void setWater(String Water) {this.Water = Water;}

public void setSun(String Sun){this.Sun = Sun;}

//public void setDescription(String Description){this.Description = Description;}


public Plant(String Image, String Name, String Water, String Sun){
    this.Image = Image;
   // this.Description = Description;
    this.Name = Name;
    this.Sun = Sun;
    this.Water = Water;
}

public String getImage() {return Image; }
public String getName() {return Name; }
public String getWater() {return Water; }
public String getSun() {return Sun; }
//public String getDescription() {return Description;}

}

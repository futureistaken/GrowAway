package com.sarah.growaway;

public class SavedFilters {

        int sunlightAmount = -1;
        //boolean maintenanceAmount = false;
        int waterAmount = -1;

        public void SavedFilters() {
        this.sunlightAmount = -1;
        //this.maintenanceAmount = false;
        this.waterAmount = -1;
        }

        public void saveFilters(int sunlightAmount, int waterAmount) {
        this.sunlightAmount = sunlightAmount;
        //this.maintenanceAmount = maintenanceAmount;
        this.waterAmount = waterAmount;

        }
}
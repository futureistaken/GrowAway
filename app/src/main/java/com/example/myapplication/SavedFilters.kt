package com.example.myapplication

object SavedFilters {

    var sunlightAmount = -1
    var maintenanceAmount = false;
    var waterAmount = -1

    fun SavedFilters() {
        this.sunlightAmount = -1
        this.maintenanceAmount = -1
        this.waterAmount = -1
    }

    fun saveFilters(sunlightAmount: Int, maintenanceAmount: Boolean, waterAmount: Int) {
        this.sunlightAmount = sunlightAmount
        this.maintenanceAmount = maintenanceAmount
        this.waterAmount = waterAmount

    }
}
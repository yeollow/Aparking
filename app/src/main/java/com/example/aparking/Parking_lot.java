package com.example.aparking;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;

public class Parking_lot {
    public String parking_name;
    public String address;
    public double latitude;
    public double longitude;
    public int price;
    public int total_space;
    public int remaining_space;

    public Parking_lot(String name, String address, double la, double lo, int price, int ts, int rs){
        parking_name = name;
        this.address = address;
        latitude = la;
        longitude = lo;
        this.price = price;
        total_space = ts;
        remaining_space = rs;
    }

    @Exclude
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", parking_name);
        result.put("address", address);
        result.put("latitude",latitude);
        result.put("longitude",longitude);
        result.put("price",price);
        result.put("total_space",total_space);
        result.put("remaining_space",remaining_space);

        return result;
    }
}

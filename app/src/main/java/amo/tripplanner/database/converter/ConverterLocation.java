package amo.tripplanner.database.converter;

import androidx.room.TypeConverter;


import com.google.gson.Gson;

import amo.tripplanner.pojo.Location;

public class ConverterLocation {

    @TypeConverter
    public String fromLocationToString(Location location){
      return new Gson().toJson(location);
    }


    @TypeConverter
    public Location fromStringToLocation(String strLocation){
        return new Gson().fromJson(strLocation,Location.class);
    }

}

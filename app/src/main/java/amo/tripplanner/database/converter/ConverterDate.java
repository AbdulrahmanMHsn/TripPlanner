package amo.tripplanner.database.converter;

import androidx.room.TypeConverter;


import java.util.Date;

import amo.tripplanner.pojo.Location;

public class ConverterDate {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


}

package amo.tripplanner.database.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import amo.tripplanner.pojo.Note;

public class ConverterNote {

    @TypeConverter
    public String fromCountryLangList(List<Note> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Note>>() {}.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<Note> toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Note>>() {}.getType();
        List<Note> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}

package com.rsamani.preflin.serializer;

import com.rsamani.preflin.Serializer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rasool on 1/28/2019.
 * Email: Rasoul.Samani@gmail.com
 */
public class GsonSerializer implements Serializer {

    @Override
    public String toString(Object o) {
        try {
            return new Gson().toJson(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T objectFromString(String s, Class<T> tClass) {
        try {
            return new Gson().fromJson(s, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> listObjectFromString(String s, Class<T> tClass) {
        try {
            ArrayList<T> t = new Gson().fromJson(s, new TypeToken<List<T>>() {}.getType());
            List<T> finalList = new ArrayList<>();

            for (int i = 0; i < t.size(); i++) {
                String item = String.valueOf(t.get(i));
                finalList.add(new Gson().fromJson(item, tClass));
            }

            return finalList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

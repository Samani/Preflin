package com.rsamani.preflin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rasoul Samani on 1/24/2019.
 */
public class DefaultSerializer implements Serializer {

    @Override
    public String toString(Object o) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return mapper.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T objectFromString(String s, Class<T> tClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(s, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> listObjectFromString(String s, Class<T> tClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, tClass);
            return mapper.readValue(s, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

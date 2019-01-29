package com.rsamani.preflin.serializer;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by Rasoul Samani on 1/24/2019.
 */
public class DefaultSerializer implements Serializer {

    @Override
    public String toString(Object o) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(o);
            so.flush();
            return Base64.encodeToString(bo.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T objectFromString(String s, Class<T> tClass) {
        try {
            byte b[] = Base64.decode(s.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            Object o = si.readObject();

            if (o instanceof List) {
                List tList = (List) o;
                return tClass.cast(tList.toArray());
            }

            return tClass.cast(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> listObjectFromString(String s, Class<T> tClass) {
        try {
            byte b[] = Base64.decode(s.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            Object o = si.readObject();
            Class<List<T>> clazz = (Class) List.class;
            return clazz.cast(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

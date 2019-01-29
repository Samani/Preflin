package com.rsamani.preflin.serializer;

import java.util.List;

/**
 * Created by rasool on 1/28/2019.
 * Email: Rasoul.Samani@gmail.com
 */
public interface Serializer {

    String toString(Object o);

    <T> T objectFromString(String s, Class<T> tClass);

    <T> List<T> listObjectFromString(String s, Class<T> tClass);

}

package com.rsamani.preflin.demo;

import com.rsamani.preflin.Preflin;
import com.rsamani.preflin.serializer.GsonSerializer;

/**
 * Created by rasool on 1/24/2019.
 * Email: Rasoul.Samani@gmail.com
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Preflin.init(this);

        // Preflin With Gson Serializer
//        Preflin.init(this, new GsonSerializer());
    }

}

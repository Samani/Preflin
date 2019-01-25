package com.rsamani.preflin.demo;

import com.rsamani.preflin.Preflin;

/**
 * Created by rasool on 1/24/2019.
 * Email: Rasoul.Samani@gmail.com
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Preflin.init(this);
    }

}

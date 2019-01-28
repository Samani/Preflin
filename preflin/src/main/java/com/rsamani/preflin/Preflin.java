package com.rsamani.preflin;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rasoul Samani on 1/24/2019.
 */
@SuppressWarnings("unused")
public class Preflin {

    private static final String DEFAULT = "";
    static final int COMMIT_BY_DEFAULT = 0;
    static final int APPLY_BY_DEFAULT = 1;

    private static Context mContext = null;
    private static Serializer mSerializer = null;
    static int mDefaultCommitBehavior;

    private static HashMap<String, PrefInternal> prefHashMap;

    public static void init(Context ctx, int defaultCommitBehavior, Serializer serializer) {
        if (ctx == null) {
            throw new NullPointerException("Preflin.init called will null context");
        }
        mSerializer = serializer;
        mContext = ctx.getApplicationContext();
        prefHashMap = new HashMap<>();
        prefHashMap.put(DEFAULT, new PrefInternal(PreferenceManager.getDefaultSharedPreferences(mContext), serializer));

        mDefaultCommitBehavior = defaultCommitBehavior;
    }

    public static void init(Context ctx, Serializer serializer) {
        init(ctx, COMMIT_BY_DEFAULT, serializer);
    }

    public static void init(Context ctx) {
        init(ctx, COMMIT_BY_DEFAULT, new DefaultSerializer());
    }

    public static void deInit() {
        mContext = null;
        if (prefHashMap != null) {
            prefHashMap.clear();
            prefHashMap = null;
        }
    }

    public static Observable<String> listenOn(String key) {
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.listenOn(key);
    }

    public static PrefInternal from(String name) {
        ensureInit();
        return from(name, Context.MODE_PRIVATE);
    }

    public static PrefInternal from(String name, int mode) {
        ensureInit();
        PrefInternal prefInternal;
        if (prefHashMap.containsKey(name)) {
            prefInternal = prefHashMap.get(name);
        } else {
            prefInternal = new PrefInternal(mContext.getSharedPreferences(name, mode), mSerializer);
            prefHashMap.put(name, prefInternal);
        }
        return prefInternal;
    }

    protected static void ensureInit() {
        if (mContext == null || prefHashMap == null || prefHashMap.get(DEFAULT) == null) {
            throw new PreflinNotInitedException();
        }
    }

    public static boolean putString(String key, String value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putString(key, value);
    }

    public static boolean isKeyExists(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.isKeyExists(key);
    }

    public static boolean deleteValue(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.deleteValue(key);
    }

    public static String getString(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getString(key);
    }

    public static String getString(String key, String defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getString(key, defaultValue);
    }

    public static boolean putFloat(String key, Float value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putFloat(key, value);
    }

    public static Float getFloat(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getFloat(key);
    }

    public static Float getFloat(String key, Float defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getFloat(key, defaultValue);
    }

    public static boolean putDouble(String key, Double value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putDouble(key, value);
    }

    public static Double getDouble(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getDouble(key);
    }

    public static Double getDouble(String key, Double defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getDouble(key, defaultValue);
    }

    public static boolean putInt(String key, Integer value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putInt(key, value);
    }

    public static Integer getInt(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getInt(key);
    }

    public static Integer getInt(String key, Integer defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getInt(key, defaultValue);
    }

    public static boolean putBoolean(String key, Boolean value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putBoolean(key, value);
    }

    public static Boolean getBoolean(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getBoolean(key);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getBoolean(key, defaultValue);
    }

    public static boolean putLong(String key, Long value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putLong(key, value);
    }

    public static Long getLong(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getLong(key);
    }

    public static Long getLong(String key, Long defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getLong(key, defaultValue);
    }

    public static boolean putStringList(String key, List<String> value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putStringList(key, value);
    }

    public static List<String> getStringList(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getStringList(key);
    }

    public static List<String> getStringList(String key, List<String> defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getStringList(key, defaultValue);
    }

    public static boolean putFloatList(String key, List<Float> value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putFloatList(key, value);
    }

    public static List<Float> getFloatList(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getFloatList(key);
    }

    public static List<Float> getFloatList(String key, List<Float> defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getFloatList(key, defaultValue);
    }

    public static boolean putDoubleList(String key, List<Double> value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putDoubleList(key, value);
    }

    public static List<Double> getDoubleList(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getDoubleList(key);
    }

    public static List<Double> getDoubleList(String key, List<Double> defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getDoubleList(key, defaultValue);
    }

    public static boolean putIntList(String key, List<Integer> value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putIntList(key, value);
    }

    public static List<Integer> getIntList(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getIntList(key);
    }

    public static List<Integer> getIntList(String key, List<Integer> defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getIntList(key, defaultValue);
    }

    public static boolean putBooleanList(String key, List<Boolean> value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putBooleanList(key, value);
    }

    public static List<Boolean> getBooleanList(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getBooleanList(key);
    }

    public static List<Boolean> getBooleanList(String key, List<Boolean> defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getBooleanList(key, defaultValue);
    }

    public static boolean putLongList(String key, List<Long> value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putLongList(key, value);
    }

    public static List<Long> getLongList(String key) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getLongList(key);
    }

    public static List<Long> getLongList(String key, List<Long> defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getLongList(key, defaultValue);
    }

    public static <T> boolean putObjectList(String key, List<T> value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putObjectList(key, value);
    }

    public static <T> List<T> getObjectList(String key, Class<T> tClass) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getObjectList(key, tClass);
    }

    public static <T> List<T> getObjectList(String key, Class<T> tClass, List<T> defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getObjectList(key, tClass, defaultValue);
    }

    public static <T> boolean putObject(String key, T value) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.putObject(key, value);
    }

    public static <T> T getObject(String key, Class<T> tClass) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getObject(key, tClass);
    }

    public static <T> T getObject(String key, Class<T> tClass, T defaultValue) {
        ensureInit();
        PrefInternal pref = prefHashMap.get(DEFAULT);
        return pref.getObject(key, tClass, defaultValue);
    }

    protected static class PreflinNotInitedException extends RuntimeException {
        public PreflinNotInitedException() {
            super("Preflin not initialized. Use Preflin.init(this) in your application");
        }
    }
}
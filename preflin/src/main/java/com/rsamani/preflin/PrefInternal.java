package com.rsamani.preflin;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposables;

/**
 * Created by Rasoul Samani on 1/24/2019.
 */
public class PrefInternal implements PrefInterface {

    private static final float DEFAULT_FLOAT = -1;
    private static final Integer DEFAULT_INT = -1;
    private static final Boolean DEFAULT_BOOLEAN = false;
    private static final Long DEFAULT_LONG = -1L;
    private final Observable<String> mObservable;

    private SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    private Serializer serializer;

    @SuppressLint("CommitPrefEdits")
    public PrefInternal(final SharedPreferences pref, Serializer serializer) {
        this.pref = pref;
        editor = pref.edit();
        this.serializer = serializer;
        mObservable = Observable.create(emitter -> {
            final SharedPreferences.OnSharedPreferenceChangeListener listener = (sharedPreferences, key) -> emitter.onNext(key);
            pref.registerOnSharedPreferenceChangeListener(listener);
            emitter.setDisposable(Disposables.fromAction(() -> {
                pref.unregisterOnSharedPreferenceChangeListener(listener);
            }));
        });
    }

    private <T> T getCastObject(String key, Class<T> tClass) {
        String s = pref.getString(key, null);
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        try {
            return serializer.objectFromString(s, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> T getCastObject(String key, Class<T> tClass, T defaultValue) {
        T obj = null;

        String s = pref.getString(key, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                obj = serializer.objectFromString(s, tClass);
            } catch (Exception e) {
                e.printStackTrace();
                obj = null;
            }
        }
        if (obj == null) {
            obj = defaultValue;
        }

        return obj;
    }

    private <T> List<T> getCastListObject(String key, Class<T> tClass) {
        String s = pref.getString(key, null);
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        try {
            return serializer.listObjectFromString(s, tClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> List<T> getCastListObject(String key, Class<T> tClass, List<T> defaultValue) {
        List<T> obj = null;

        String s = pref.getString(key, null);
        if (!TextUtils.isEmpty(s)) {
            try {
                obj = serializer.listObjectFromString(s, tClass);
            } catch (Exception e) {
                e.printStackTrace();
                obj = null;
            }
        }
        if (obj == null) {
            obj = defaultValue;
        }

        return obj;
    }

    @Override
    public boolean isKeyExists(String key) {
        Map<String, ?> map = pref.getAll();
        if (map.containsKey(key)) {
            return true;
        } else {
            Log.e("Preflin", "No element founded in sharedPrefs with the key " + key);
            return false;
        }
    }

    @Override
    public boolean deleteValue(String key) {
        if (isKeyExists(key)) {
            editor.remove(key);
            editor.apply();
            return true;
        }

        return false;
    }

    @Override
    public boolean putString(String key, String value) {
        return storeEntry(editor.putString(key, value));
    }

    private boolean storeEntry(SharedPreferences.Editor editor) {
        switch (Preflin.mDefaultCommitBehavior) {
            case Preflin.APPLY_BY_DEFAULT:
                editor.apply();
                return true;
            case Preflin.COMMIT_BY_DEFAULT:
            default:
                return editor.commit();
        }
    }

    @Override
    public String getString(String key) {
        return getString(key, "");
    }

    @Override
    public String getString(String key, String defaultValue) {
        return pref.getString(key, defaultValue);
    }

    @Override
    public boolean putFloat(String key, Float value) {
        return storeEntry(editor.putFloat(key, value));
    }

    @Override
    public Float getFloat(String key) {
        return pref.getFloat(key, DEFAULT_FLOAT);
    }

    @Override
    public Float getFloat(String key, Float defaultValue) {
        return pref.getFloat(key, defaultValue);
    }

    @Override
    public boolean putDouble(String key, Double value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public Double getDouble(String key) {
        return getCastObject(key, Double.class);
    }

    @Override
    public Double getDouble(String key, Double defaultValue) {
        return getCastObject(key, Double.class, defaultValue);
    }

    @Override
    public boolean putInt(String key, Integer value) {
        return storeEntry(editor.putInt(key, value));
    }

    @Override
    public Integer getInt(String key) {
        return getInt(key, DEFAULT_INT);
    }

    @Override
    public Integer getInt(String key, Integer defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    @Override
    public boolean putBoolean(String key, Boolean value) {
        return storeEntry(editor.putBoolean(key, value));
    }

    @Override
    public Boolean getBoolean(String key) {
        return getBoolean(key, DEFAULT_BOOLEAN);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    @Override
    public boolean putLong(String key, Long value) {
        return storeEntry(editor.putLong(key, value));
    }

    @Override
    public Long getLong(String key) {
        return getLong(key, DEFAULT_LONG);
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        return pref.getLong(key, defaultValue);
    }

    @Override
    public boolean putStringList(String key, List<String> value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public List<String> getStringList(String key) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, String[].class, new String[0])));
    }

    @Override
    public List<String> getStringList(String key, List<String> defaultValue) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, String[].class, defaultValue.toArray(new String[defaultValue.size()]))));
    }

    @Override
    public boolean putFloatList(String key, List<Float> value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public List<Float> getFloatList(String key) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Float[].class, new Float[0])));
    }

    @Override
    public List<Float> getFloatList(String key, List<Float> defaultValue) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Float[].class, defaultValue.toArray(new Float[defaultValue.size()]))));
    }

    @Override
    public boolean putDoubleList(String key, List<Double> value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public List<Double> getDoubleList(String key) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Double[].class, new Double[0])));
    }

    @Override
    public List<Double> getDoubleList(String key, List<Double> defaultValue) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Double[].class, defaultValue.toArray(new Double[defaultValue.size()]))));
    }

    @Override
    public boolean putIntList(String key, List<Integer> value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public List<Integer> getIntList(String key) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Integer[].class, new Integer[0])));
    }

    @Override
    public List<Integer> getIntList(String key, List<Integer> defaultValue) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Integer[].class, defaultValue.toArray(new Integer[defaultValue.size()]))));
    }

    @Override
    public boolean putBooleanList(String key, List<Boolean> value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public List<Boolean> getBooleanList(String key) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Boolean[].class, new Boolean[0])));
    }

    @Override
    public List<Boolean> getBooleanList(String key, List<Boolean> defaultValue) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Boolean[].class, defaultValue.toArray(new Boolean[defaultValue.size()]))));
    }

    @Override
    public boolean putLongList(String key, List<Long> value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public List<Long> getLongList(String key) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Long[].class, new Long[0])));
    }

    @Override
    public List<Long> getLongList(String key, List<Long> defaultValue) {
        return new ArrayList<>(Arrays.asList(getCastObject(key, Long[].class, defaultValue.toArray(new Long[defaultValue.size()]))));
    }

    @Override
    public <T> boolean putObject(String key, T value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public <T> T getObject(String key, Class<T> tClass) {
        return getCastObject(key, tClass);
    }

    @Override
    public <T> T getObject(String key, Class<T> tClass, T defaultValue) {
        return getCastObject(key, tClass, defaultValue);
    }

    @Override
    public <T> boolean putObjectList(String key, List<T> value) {
        return putString(key, serializer.toString(value));
    }

    @Override
    public <T> List<T> getObjectList(String key, Class<T> tClass) {
        return getCastListObject(key, tClass);
    }

    @Override
    public <T> List<T> getObjectList(String key, Class<T> tClass, List<T> defaultValue) {
        return getCastListObject(key, tClass, defaultValue);
    }

    public Observable<String> listenOn(final String key) {
        return mObservable.filter(s -> s.equals(key));
    }
}

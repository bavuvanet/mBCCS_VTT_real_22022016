package com.viettel.bss.viettelpos.v4.commons.auto;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.lang.reflect.Type;

/**
 * Created by diepdc-pc on 8/3/2016.
 */
public class ComplexPreferences {
    private static ComplexPreferences complexPreferences;
    //  private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static Gson GSON = new Gson();
    Type typeOfObject = new TypeToken<Object>() {
    }.getType();

    private ComplexPreferences(Context context, String namePreferences, int mode) {
        //  this.context = context;
        if (namePreferences == null || "".equals(namePreferences)) {
            namePreferences = "complex_preferences";
        }
        preferences = context.getSharedPreferences(namePreferences, mode);
        editor = preferences.edit();
    }

    public static synchronized ComplexPreferences getComplexPreferences(Context context, String namePreferences, int mode) {
//		if (complexPreferences == null) {
        complexPreferences = new ComplexPreferences(context,
                namePreferences, mode);
//		}
        return complexPreferences;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }
        //if (key.equals("") || key == null) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key is empty or null");
        }
        editor.putString(key, GSON.toJson(object));
    }

    public void commit() {
        editor.commit();
    }

    public void clearObject() {
        editor.clear();
    }

    public <T> T getObject(String key, Class<T> a) {
        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object storaged with key " + key + " is instanceof other class");
            }
        }
    }
}

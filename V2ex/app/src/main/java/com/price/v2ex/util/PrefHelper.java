
package com.price.v2ex.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Set;

/**
 * 本类处理SharePreference相关.
 * 
 */
public class PrefHelper {

    public static boolean getBoolean(Context context, String prefName, String prefKey,
            boolean defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, prefName);
        return sp != null ? sp.getBoolean(prefKey, defaultValue) : defaultValue;
    }
    
    public static float getFloat(Context context, String prefName, String prefKey,
            float defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, prefName);
        return sp != null ? sp.getFloat(prefKey, defaultValue) : defaultValue;
    }

    public static int getInt(Context context, String prefName, String prefKey, int defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, prefName);
        return sp != null ? sp.getInt(prefKey, defaultValue) : defaultValue;
    }

    public static long getLong(Context context, String prefName, String prefKey, long defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, prefName);
        return sp != null ? sp.getLong(prefKey, defaultValue) : defaultValue;
    }

    public static String getString(Context context, String prefName, String prefKey,
            String defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, prefName);
        return sp != null ? sp.getString(prefKey, defaultValue) : defaultValue;
    }

    public static Set<String> getStringSet(Context context, String prefName, String prefKey,
                                           Set<String> defaultValue) {
        SharedPreferences sp = getSharedPreferences(context, prefName);
        return sp != null ? sp.getStringSet(prefKey, defaultValue) : defaultValue;
    }

    public static void putBoolean(Context context, String prefName, String prefKey, boolean value) {
        getSharedPreferences(context, prefName).edit().putBoolean(prefKey, value).apply();
    }

    public static void putFloat(Context context, String prefName, String prefKey, float value) {
        getSharedPreferences(context, prefName).edit().putFloat(prefKey, value).apply();
    }

    public static void putInt(Context context, String prefName, String prefKey, int value) {
        getSharedPreferences(context, prefName).edit().putInt(prefKey, value).apply();
    }

    public static void putLong(Context context, String prefName, String prefKey, long value) {
        getSharedPreferences(context, prefName).edit().putLong(prefKey, value).apply();
    }
    
    public static void putString(Context context, String prefName, String prefKey, String value) {
        getSharedPreferences(context, prefName).edit().putString(prefKey, value).apply();
    }

    public static void putStringSet(Context context, String prefName, String prefKey, Set<String> value) {
        getSharedPreferences(context, prefName).edit().putStringSet(prefKey, value).apply();
    }

    public static void remove(Context context, String prefName, String prefKey) {
        getSharedPreferences(context, prefName).edit().remove(prefKey).apply();
    }
    
    public static void removeKeys(Context context, String prefName, String[] prefKeys) {
        if (prefKeys == null || prefKeys.length == 0) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(context, prefName);
        Editor editor = sp.edit();
        for (String key : prefKeys) {
            if (!TextUtils.isEmpty(key)) {
                editor.remove(key);
            }
        }
        editor.apply();
    }

    public static boolean getBoolean(Context context, String prefKey, boolean defaultValue) {
        return getSharedPreferences(context).getBoolean(prefKey, defaultValue);
    }

    public static float getFloat(Context context, String prefKey, float defaultValue) {
        return getSharedPreferences(context).getFloat(prefKey, defaultValue);
    }

    public static int getInt(Context context, String prefKey, int defaultValue) {
        return getSharedPreferences(context).getInt(prefKey, defaultValue);
    }

    public static long getLong(Context context, String prefKey, long defaultValue) {
        return getSharedPreferences(context).getLong(prefKey, defaultValue);
    }

    public static String getString(Context context, String prefKey, String defaultValue) {
        return getSharedPreferences(context).getString(prefKey, defaultValue);
    }

    public static Set<String> getStringSet(Context context, String prefKey,
                                           Set<String> defaultValue) {
        return getSharedPreferences(context).getStringSet(prefKey, defaultValue);
    }

    public static void putBoolean(Context context, String prefKey, boolean value) {
        getSharedPreferences(context).edit().putBoolean(prefKey, value).apply();
    }

    public static void putFloat(Context context, String prefKey, float value) {
        getSharedPreferences(context).edit().putFloat(prefKey, value).apply();
    }

    public static void putInt(Context context, String prefKey, int value) {
        getSharedPreferences(context).edit().putInt(prefKey, value).apply();
    }

    public static void putLong(Context context, String prefKey, long value) {
        getSharedPreferences(context).edit().putLong(prefKey, value).apply();
    }

    public static void putString(Context context, String prefKey, String value) {
        getSharedPreferences(context).edit().putString(prefKey, value).apply();
    }

    public static void putStringSet(Context context, String prefKey, Set<String> value) {
        getSharedPreferences(context).edit().putStringSet(prefKey, value).apply();
    }

    public static void remove(Context context, String prefKey) {
        getSharedPreferences(context).edit().remove(prefKey).apply();
    }
    
    public static void removeKeys(Context context, String[] prefKeys) {
        if (prefKeys == null || prefKeys.length == 0) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();
        for (String key : prefKeys) {
            if (!TextUtils.isEmpty(key)) {
                editor.remove(key);
            }
        }
        editor.apply();
    }

    /**
     * 得到默认SharePreference
     * 
     * @param context
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return getSharedPreferences(context, null);
    }

    /**
     * 根据名字得到SharePreference
     * 
     * @param context
     * @param prefName
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context, String prefName) {
        if (TextUtils.isEmpty(prefName)) {
            return PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        }
    }
}

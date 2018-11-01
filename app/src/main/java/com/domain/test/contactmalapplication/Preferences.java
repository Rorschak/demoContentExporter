package com.domain.test.contactmalapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    /**
     * Sets long preference.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setLongPreference(Context context, final String key, final Long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Sets string preference.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setStringPreference(Context context, final String key, final String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Sets boolean preference.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setBooleanPreference(Context context, final String key, final boolean value) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets string preference.
     *
     * @param context the context
     * @param key     the key
     * @return the string preference
     */
    public static String getStringPreference(Context context, final String key) {

        String value = null;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            value = preferences.getString(key, "");
        } catch (Exception ex) {
            ex.printStackTrace();
            value = "";
        }
        return value;
    }

    /**
     * Gets boolean preference.
     *
     * @param context the context
     * @param key     the key
     * @return the boolean preference
     */
    public static boolean getBooleanPreference(Context context, final String key) {
        boolean value = false;
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            value = preferences.getBoolean(key, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }
    /**
     * Gets long preference.
     *
     * @param context the context
     * @param key     the key
     * @return the long preference
     */
    public static long getLongPreference(Context context, final String key) {
        long value = 0;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getLong(key, 0);
        return value;
    }

    /**
     * Sets int preference.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void setIntPreference(Context context, final String key, final int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Gets int preference.
     *
     * @param context the context
     * @param key     the key
     * @return the int preference
     */
    public static int getIntPreference(Context context, final String key) {
        int value = 0;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        value = preferences.getInt(key, 0);
        return value;
    }
}
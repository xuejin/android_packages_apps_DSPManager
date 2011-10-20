package com.bel.android.dspmanager.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.bel.android.dspmanager.R;

/**
 * Setting utility for CyanogenMod's DSP capabilities.
 * This page is displays the top-level configurations menu.
 * 
 * @author alankila
 */
public final class DSPManager extends PreferenceActivity {
	public static final String SHARED_PREFERENCES_BASENAME = "com.bel.android.dspmanager";
	public static final String ACTION_UPDATE_PREFERENCES = "com.bel.android.dspmanager.UPDATE";
	
	public static final int NOTIFY_FOREGROUND_ID = 1;

	private final OnSharedPreferenceChangeListener serviceLauncher = new OnSharedPreferenceChangeListener() {
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {			
			sendBroadcast(new Intent(ACTION_UPDATE_PREFERENCES));
		}
	};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);
		SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_BASENAME + "_preferences", 0);
		preferences.registerOnSharedPreferenceChangeListener(serviceLauncher);
		/* mostly for development: ensure that the service is running if it isn't running yet. */
		sendBroadcast(new Intent(ACTION_UPDATE_PREFERENCES));
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
		SharedPreferences preferences = getSharedPreferences(SHARED_PREFERENCES_BASENAME + "_preferences", 0);
		preferences.unregisterOnSharedPreferenceChangeListener(serviceLauncher);
	}
}

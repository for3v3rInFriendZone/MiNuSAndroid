package fragments;

import android.app.Activity;
import android.com.minus.R;
import android.com.minus.activities.AddBillActivity;
import android.com.minus.activities.MainActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.widget.RelativeLayout;

import util.FontHelper;


public class SettingsFragment extends PreferenceFragment {

    private SharedPreferences shared_font;
    private SharedPreferences shared_color;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private SharedPreferences.Editor sharedPreferencesEditorColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        shared_font = getActivity().getApplicationContext().getSharedPreferences("font", 0);
        shared_color = getActivity().getApplicationContext().getSharedPreferences("color", 0);

        sharedPreferencesEditor = shared_font.edit();
        sharedPreferencesEditorColor = shared_color.edit();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if(preference.getKey().equals("pref_sync_font")) {
            final ListPreference font = (ListPreference) preference;
            font.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.equals("Serif")) {
                        sharedPreferencesEditor.putString("app_font", "serif");
                    } else if(newValue.equals("Sans")) {
                        sharedPreferencesEditor.putString("app_font", "sans");
                    } else if(newValue.equals("Monospace")) {
                        sharedPreferencesEditor.putString("app_font", "monospace");
                    } else if(newValue.equals("Arbutus")) {
                        sharedPreferencesEditor.putString("app_font", "arbutus");
                    } else {
                        sharedPreferencesEditor.putString("app_font", "catamaran");
                    }

                    sharedPreferencesEditor.apply();
                    return true;
                }
            });
        } else if(preference.getKey().equals("pref_sync_colors")) {
            final ListPreference font = (ListPreference) preference;
            font.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.equals("#ff3333")) {
                        sharedPreferencesEditorColor.putString("bgColor", "#ff3333");
                    } else if(newValue.equals("#3333ff")) {
                        sharedPreferencesEditorColor.putString("bgColor", "#3333ff");
                    } else if(newValue.equals("#006622")) {
                        sharedPreferencesEditorColor.putString("bgColor", "#006622");
                    } else if(newValue.equals("#4d2600")) {
                        sharedPreferencesEditorColor.putString("bgColor", "#4d2600");
                    } else if(newValue.equals("#e6e600")) {
                        sharedPreferencesEditorColor.putString("bgColor", "#e6e600");
                    } else if(newValue.equals("#b30086")) {
                        sharedPreferencesEditorColor.putString("bgColor", "#b30086");
                    } else {
                        sharedPreferencesEditorColor.putString("bgColor", "default");
                    }

                    sharedPreferencesEditorColor.apply();
                    return true;
                }
            });
        }


        return super.onPreferenceTreeClick(preferenceScreen, preference);


    }
}

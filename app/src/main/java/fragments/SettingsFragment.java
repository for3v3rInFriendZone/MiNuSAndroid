package fragments;

import android.com.minus.R;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import util.FontHelper;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if(preference.getKey().equals("pref_sync_font")) {
            final ListPreference font = (ListPreference) preference;
            font.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.equals("Serif")) {
                        FontHelper.setDefaultFont(getActivity().getBaseContext(), "DEFAULT", "fonts/NewWaltDisney.ttf");
                    }
                    return true;
                }
            });
        }


        return super.onPreferenceTreeClick(preferenceScreen, preference);


    }
}

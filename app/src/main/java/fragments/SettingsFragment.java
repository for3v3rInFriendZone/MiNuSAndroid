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
import android.provider.Settings;
import android.widget.RelativeLayout;

import DAO.UserDAO;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.FontHelper;
import util.RetrofitBuilder;
import util.SharedSession;


public class SettingsFragment extends PreferenceFragment {

    private User logedUser;
    private UserDAO userDao;
    private Retrofit retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        logedUser = SharedSession.getSavedObjectFromPreference(getActivity().getApplicationContext(), "userSession", "user", User.class);
        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        userDao = retrofit.create(UserDAO.class);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if(preference.getKey().equals("pref_sync_font")) {
            final ListPreference font = (ListPreference) preference;
            font.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.equals("Serif")) {
                        logedUser.setFont("serif");
                    } else if(newValue.equals("Sans")) {
                        logedUser.setFont("sans");
                    } else if(newValue.equals("Monospace")) {
                        logedUser.setFont("monospace");
                    } else if(newValue.equals("Arbutus")) {
                        logedUser.setFont("arbutus");
                    } else {
                        logedUser.setFont("catamaran");
                    }

                    SharedSession.saveObjectToSharedPreference(getActivity().getApplicationContext(), "userSession", "user", logedUser);
                    userDao.editUser(logedUser).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                    return true;
                }
            });
        } else if(preference.getKey().equals("pref_sync_colors")) {
            final ListPreference font = (ListPreference) preference;
            font.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.equals("#ff3333")) {
                        logedUser.setColor("#ff3333");
                    } else if(newValue.equals("#3333ff")) {
                        logedUser.setColor("#3333ff");
                    } else if(newValue.equals("#006622")) {
                        logedUser.setColor("#006622");
                    } else if(newValue.equals("#4d2600")) {
                        logedUser.setColor("#4d2600");
                    } else if(newValue.equals("#e6e600")) {
                        logedUser.setColor("#e6e600");
                    } else if(newValue.equals("#b30086")) {
                        logedUser.setColor("#b30086");
                    } else {
                        logedUser.setColor("default");
                    }

                    SharedSession.saveObjectToSharedPreference(getActivity().getApplicationContext(), "userSession", "user", logedUser);
                    userDao.editUser(logedUser).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                    return true;
                }
            });
        }



        return super.onPreferenceTreeClick(preferenceScreen, preference);


    }
}

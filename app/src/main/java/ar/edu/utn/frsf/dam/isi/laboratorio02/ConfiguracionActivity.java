package ar.edu.utn.frsf.dam.isi.laboratorio02;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConfiguracionActivity extends AppCompatActivity {

    public static class ConfiguracionFragment extends PreferenceFragmentCompat {
        private ListPreference mListPreference;
        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            setPreferencesFromResource(R.xml.configuracion_ui, s);
            //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        }

        //@Override
        //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //    mListPreference = (ListPreference)  getPreferenceManager().findPreference("retira");
        //    mListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
        //        @Override
        //        public boolean onPreferenceChange(Preference preference, Object newValue) {
        //            return true;
        //        }
        //    });

        //    return inflater.inflate(R.layout.activity_configuracion, container, false);
        //}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new ConfiguracionFragment())
                .commit();
    }
    }
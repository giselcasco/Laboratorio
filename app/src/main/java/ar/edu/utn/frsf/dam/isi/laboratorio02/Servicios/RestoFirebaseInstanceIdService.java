package ar.edu.utn.frsf.dam.isi.laboratorio02.Servicios;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;

//FirebaseInstanceIdService deprecate, asi que uso FirebaseMessagingService
public class RestoFirebaseInstanceIdService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseIIDService";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Refreshed token: SERVICIO CREADO!!!!");
    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String refreshedToken =
                FirebaseInstanceId.getInstance().toString();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        guardarToken(refreshedToken);
        guardarToken(leerToken());
    }

    private void guardarToken(String _token){
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("registration_id", _token);
        editor.apply();
    }

    private String leerToken(){
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("registration_id", null);
    }
}

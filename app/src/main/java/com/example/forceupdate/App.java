package com.example.forceupdate;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {

// cntrl+ o
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

        Map<String,Object> defaultvalue =  new HashMap<>();
        defaultvalue.put(UpdateHelper.KEY_UPDATE_ENABLE,false);
        defaultvalue.put(UpdateHelper.KEY_UPDATE_VERSION,"1.0");
        defaultvalue.put(UpdateHelper.KEY_UPDATE_URL,"your app url on App Store");
        remoteConfig.setDefaultsAsync(defaultvalue);
        remoteConfig.fetch(5).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    remoteConfig.fetchAndActivate();
                }

            }
        });


    }
}

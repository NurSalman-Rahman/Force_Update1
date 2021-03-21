package com.example.forceupdate;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {   //helper start

    public  static  String KEY_UPDATE_ENABLE = "is_update";
    public  static  String KEY_UPDATE_VERSION= "version";
    public  static  String KEY_UPDATE_URL = "update_url";

//interface


    public interface  OnUpdateCheckListener {
        void onUpdateCheckListener(String  urlApp);
    }


    //Context new buildere
    public static Builder with(Context context)
    {
        return new Builder(context);
    }



    private OnUpdateCheckListener onUpdateCheckListener;
    private Context context;

    public UpdateHelper(Context context,OnUpdateCheckListener onUpdateCheckListener)
    {
        this.onUpdateCheckListener = onUpdateCheckListener;
        this.context = context;
    }



    //Check

    public void check ()
    {
        FirebaseRemoteConfig  remoteConfig = FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(KEY_UPDATE_ENABLE))
        {
            String currentVersion = remoteConfig.getString(KEY_UPDATE_VERSION);
            String appVersion= getAppVersion(context);
            String updateURL = remoteConfig.getString(KEY_UPDATE_URL);


           if(!TextUtils.equals(currentVersion,appVersion) && onUpdateCheckListener !=null)
               onUpdateCheckListener.onUpdateCheckListener(updateURL);


        }
    }
      //app version

    private String getAppVersion(Context context) {

        String result = "";

        try {
            result = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]] |-","");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
            return  result;
    }




    //Builder


    public static class Builder{



        private Context context;
        private OnUpdateCheckListener onUpdateCheckListener ;

        public Builder(Context context) {
            this.context = context;
        }




     public Builder onUpdateCheck (OnUpdateCheckListener onUpdateCheckListener)
     {
         this.onUpdateCheckListener = onUpdateCheckListener;
         return  this;

     }

     public UpdateHelper build()
     {
         return new UpdateHelper(context,onUpdateCheckListener);
     }

         public UpdateHelper check()
         {
             UpdateHelper updateHelper = build();
             updateHelper.check();
             return  updateHelper;

         }



    } //Bulder End





} //helper end

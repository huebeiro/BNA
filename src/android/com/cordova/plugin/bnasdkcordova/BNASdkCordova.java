package com.cordova.plugin.bnasdkcordova;

import com.ericsson.bnasdk.BnaSDK;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import android.util.Log;

import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import javax.security.auth.callback.Callback;

public class BNASdkCordova extends CordovaPlugin {

    private static final String TAG = BNASdkCordova.class.getCanonicalName();
    private static final String BNA_GO      = "go";
    private static final String BNA_STOP    = "stop";

    private CordovaInterface mCordovaInterface;
    
    CallbackContext context;

     String [] permissions = { Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
			
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.mCordovaInterface = cordova;
    }

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callbackContext) throws JSONException {
        
        switch (action) {
            case BNA_GO:

                try{
					LOG.d(TAG, "We are entering execute");
                    BnaSDK.create(this.mCordovaInterface.getContext().getApplicationContext());
                
					context = callbackContext;
					if(hasPermisssion())
					{
						LOG.d(TAG, "execute() - Initializing BNA");
						BnaSDK.instance().go(this.mCordovaInterface.getContext().getApplicationContext());
						PluginResult r = new PluginResult(PluginResult.Status.OK);
						context.sendPluginResult(r);
						callbackContext.success();
						return true;
					}
					else {
						LOG.d(TAG, "execute() - Hasn't permissions");
						PermissionHelper.requestPermissions(this, 0, permissions);
					}
				}catch (Exception e){
                    Log.d("Description", e.toString());
                }
                return true;

            case BNA_STOP:
                LOG.d(TAG, "Stopping SDK");
                BnaSDK.instance().stop(this.mCordovaInterface.getContext().getApplicationContext());
                callbackContext.success();
                return true;
            default:
                LOG.d(TAG, "Unexpected action: " + action);
                return false;
        }

    }
	
    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) throws JSONException
    {
		
		LOG.d(TAG, "onRequestPermissionResult()");
        PluginResult result;
        //This is important if we're using Cordova without using Cordova, but we have the geolocation plugin installed
        if(context != null) {
            for (int r : grantResults) {
                if (r == PackageManager.PERMISSION_DENIED) {
                    LOG.d(TAG, "Permission Denied!");
                    result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
                    context.sendPluginResult(result);
                    return;
                }

            }
			
			LOG.d(TAG, "onRequestPermissionResult() - Initializing BNA");
            BnaSDK.instance().go(this.mCordovaInterface.getContext().getApplicationContext());
            result = new PluginResult(PluginResult.Status.OK);
            context.sendPluginResult(result);
        }
    }

    public boolean hasPermisssion() {
        for(String p : permissions)
        {
            if(!PermissionHelper.hasPermission(this, p))
            {
				LOG.d(TAG, "Does not have permission: " + p);
                return false;
            }
        }
        return true;
    }

    /*
     * We override this so that we can access the permissions variable, which no longer exists in
     * the parent class, since we can't initialize it reliably in the constructor!
     */

    public void requestPermissions(int requestCode)
    {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }

}
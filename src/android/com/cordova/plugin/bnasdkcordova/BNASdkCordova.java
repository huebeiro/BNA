package com.cordova.plugin.bnasdkcordova;

import com.ericsson.bnasdk.BnaSDK;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import android.util.Log;

import org.json.JSONException;

public class BNASdkCordova extends CordovaPlugin {

    private static final String TAG = BNASdkCordova.class.getCanonicalName();
    private static final String BNA_GO      = "go";
    private static final String BNA_STOP    = "stop";

    private CordovaInterface mCordovaInterface;

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
                    BnaSDK.create(this.mCordovaInterface.getContext().getApplicationContext());
                }catch (Exception e){
                    Log.d("Description", e.toString());
                }
                BnaSDK.instance().go(this.mCordovaInterface.getContext().getApplicationContext());
                callbackContext.success();
                return true;
            case BNA_STOP:
                BnaSDK.instance().stop(this.mCordovaInterface.getContext().getApplicationContext());
                callbackContext.success();
                return true;
            default:
                return false;
        }

    }
}
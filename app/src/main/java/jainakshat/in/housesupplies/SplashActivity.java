package jainakshat.in.housesupplies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by Akshat Jain on 11/23/2015.
 */
public class SplashActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    static final String TAG = "HouseSupplies_SplashAct";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        Typeface custom_font = Typeface.createFromAsset(getAssets(), "JosefinSans-Regular.ttf");
        TextView tx = (TextView)findViewById(R.id.textView);
        tx.setTypeface(custom_font);

        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
            }
        };

        final Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                    Intent menuIntent = new Intent("android.intent.action.MAINACT");
                    startActivity(menuIntent);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                }

            };
        };
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
        /*if (checkPlayServices()) {


            //logoTimer.start();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("registrationComplete"));
    }

    private boolean checkPlayServices() {
        Log.d(TAG, "*******************S T A R T ****************");
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            Log.d(TAG, "*************** S T O P ********************");
            return false;
        }
        return true;
    }

}

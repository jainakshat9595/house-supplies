package jainakshat.in.housesupplies;

import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Akshat Jain on 12/1/2015.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "HouseSupplies_MyInstanceIDListenerService";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

}

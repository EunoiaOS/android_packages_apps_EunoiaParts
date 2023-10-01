/*
 * SPDX-FileCopyrightText: 2015 The CyanogenMod Project
 * SPDX-FileCopyrightText: 2017-2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.eunoiaos.eunoiaparts.eunoiastats;

import android.app.IntentService;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.util.Log;

public class ReportingService extends IntentService {
    /* package */ static final String TAG = "EunoiaStats";
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);

    public ReportingService() {
        super(ReportingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        JobScheduler js = getSystemService(JobScheduler.class);

        Context context = getApplicationContext();

        String deviceId = Utilities.getUniqueID(context);
        String deviceName = Utilities.getDevice();
        String deviceVersion = Utilities.getModVersion();
        String deviceCountry = Utilities.getCountryCode(context);
        String deviceCarrier = Utilities.getCarrier(context);
        String deviceCarrierId = Utilities.getCarrierId(context);

        final int eunoiaOldJobId = AnonymousStats.getLastJobId(context);
        final int eunoiaComJobId = AnonymousStats.getNextJobId(context);

        if (DEBUG) Log.d(TAG, "scheduling job id: " + eunoiaComJobId);

        PersistableBundle eunoiaBundle = new PersistableBundle();
        eunoiaBundle.putString(StatsUploadJobService.KEY_DEVICE_NAME, deviceName);
        eunoiaBundle.putString(StatsUploadJobService.KEY_UNIQUE_ID, deviceId);
        eunoiaBundle.putString(StatsUploadJobService.KEY_VERSION, deviceVersion);
        eunoiaBundle.putString(StatsUploadJobService.KEY_COUNTRY, deviceCountry);
        eunoiaBundle.putString(StatsUploadJobService.KEY_CARRIER, deviceCarrier);
        eunoiaBundle.putString(StatsUploadJobService.KEY_CARRIER_ID, deviceCarrierId);
        eunoiaBundle.putLong(StatsUploadJobService.KEY_TIMESTAMP, System.currentTimeMillis());

        // set job types
        eunoiaBundle.putInt(StatsUploadJobService.KEY_JOB_TYPE,
                StatsUploadJobService.JOB_TYPE_EUNOIACOM);

        // schedule eunoia stats upload
        js.schedule(new JobInfo.Builder(eunoiaComJobId, new ComponentName(getPackageName(),
                StatsUploadJobService.class.getName()))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setMinimumLatency(1000)
                .setExtras(eunoiaBundle)
                .setPersisted(true)
                .build());

        // cancel old job in case it didn't run yet
        js.cancel(eunoiaOldJobId);

        // reschedule
        AnonymousStats.updateLastSynced(this);
        ReportingServiceManager.setAlarm(this);
    }
}

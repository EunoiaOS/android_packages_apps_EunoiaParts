/*
 * SPDX-FileCopyrightText: 2012 The CyanogenMod Project
 * SPDX-FileCopyrightText: 2017-2019,2021,2023 The LineageOS project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.eunoiaos.eunoiaparts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.UserManager;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.eunoiaos.eunoiaparts.contributors.ContributorsCloudFragment;
import com.eunoiaos.eunoiaparts.gestures.TouchscreenGestureSettings;
import com.eunoiaos.eunoiaparts.input.ButtonSettings;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "PartsBootReceiver";
    private static final String ONE_TIME_TUNABLE_RESTORE = "hardware_tunable_restored";

    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (!ctx.getSystemService(UserManager.class).isPrimaryUser()) {
            Log.d(TAG, "Not running as the primary user, skipping tunable restoration.");
            return;
        }

        if (!hasRestoredTunable(ctx)) {
            /* Restore the hardware tunable values */
            ButtonSettings.restoreKeyDisabler(ctx);
            setRestoredTunable(ctx);
        }

        ButtonSettings.restoreKeySwapper(ctx);
        TouchscreenGestureSettings.restoreTouchscreenGestureStates(ctx);

        // Extract the contributors database
        ContributorsCloudFragment.extractContributorsCloudDatabase(ctx);
    }

    private boolean hasRestoredTunable(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(ONE_TIME_TUNABLE_RESTORE, false);
    }

    private void setRestoredTunable(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(ONE_TIME_TUNABLE_RESTORE, true).apply();
    }
}

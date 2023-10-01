/*
 * SPDX-FileCopyrightText: 2017-2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.eunoiaos.eunoiaparts.notificationlight;

import android.content.ContentResolver;
import android.content.Context;
import android.os.UserHandle;
import android.util.AttributeSet;

import eunoiaos.providers.EunoiaSettings;

public class BatteryBrightnessPreference extends BrightnessPreference {
    private static final String TAG = "BatteryBrightnessPreference";

    private final ContentResolver mResolver;

    public BatteryBrightnessPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResolver = context.getContentResolver();
    }

    @Override
    protected int getBrightnessSetting() {
        return EunoiaSettings.System.getIntForUser(mResolver,
                EunoiaSettings.System.BATTERY_LIGHT_BRIGHTNESS_LEVEL,
                LIGHT_BRIGHTNESS_MAXIMUM, UserHandle.USER_CURRENT);
    }

    @Override
    protected void setBrightnessSetting(int brightness) {
        EunoiaSettings.System.putIntForUser(mResolver,
                EunoiaSettings.System.BATTERY_LIGHT_BRIGHTNESS_LEVEL,
                brightness, UserHandle.USER_CURRENT);
    }
}

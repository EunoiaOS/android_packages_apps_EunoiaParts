/*
 * SPDX-FileCopyrightText: 2014 The CyanogenMod Project
 * SPDX-FileCopyrightText: 2020-2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.eunoiaos.eunoiaparts.profiles.actions.item;

import android.content.Context;

import eunoiaos.app.Profile;

public class ProfileNameItem extends Item {
    private final Profile mProfile;

    public ProfileNameItem(Profile profile) {
        mProfile = profile;
    }

    @Override
    public String getTitle(Context context) {
        return mProfile.getName();
    }

    @Override
    public String getSummary(Context context) {
        return null;
    }
}

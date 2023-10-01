/*
 * SPDX-FileCopyrightText: 2018-2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */
package com.eunoiaos.eunoiaparts.trust;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import eunoiaos.providers.EunoiaSettings;
import eunoiaos.trust.TrustInterface;

import com.eunoiaos.eunoiaparts.R;

public class TrustOnBoardingActivity extends AppCompatActivity {
    private ImageView mImage;

    private TrustInterface mInterface;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_trust_onboarding);

        Button learnMore = findViewById(R.id.trust_onboarding_learn_more);
        Button dismiss = findViewById(R.id.trust_onboarding_done);
        mImage = findViewById(R.id.trust_onboarding_image);

        learnMore.setOnClickListener(v -> openTrustSettings());
        dismiss.setOnClickListener(v -> onDismissClick());

        mInterface = TrustInterface.getInstance(this);

        new Handler(Looper.getMainLooper()).postDelayed(this::showAnimation, 800);
    }

    private void showAnimation() {
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) mImage.getDrawable();
        if (drawable != null) {
            drawable.start();
        }
    }

    private void openTrustSettings() {
        setOnboardingCompleted();
        startActivity(new Intent("com.eunoiaos.eunoiaparts.TRUST_INTERFACE"));
        finish();
    }

    private void onDismissClick() {
        setOnboardingCompleted();
        finish();
    }

    private void setOnboardingCompleted() {
        EunoiaSettings.System.putInt(getContentResolver(),
                EunoiaSettings.System.TRUST_INTERFACE_HINTED, 1);
        // Run security check test now that the user is aware of what Trust is
        mInterface.runTest();
    }
}
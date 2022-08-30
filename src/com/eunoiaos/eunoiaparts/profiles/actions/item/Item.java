/*
 * Copyright (C) 2014 The CyanogenMod Project
 *               2020 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eunoiaos.eunoiaparts.profiles.actions.item;

import android.content.Context;

public abstract class Item {
    public abstract String getTitle(Context context);
    public abstract String getSummary(Context context);

    public boolean isHeader() {
        return false;
    }

    public boolean isEnabled(Context context) {
        return true;
    }
}
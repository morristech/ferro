/*
 * Copyright 2015 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.ferro.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


/**
 * This fragment provide access to own {@link PersistentScreenScope}
 * PSS - PersistentScreenScope
 *
 * The name from {@link HasName#getName()} used for distinguish one PersistentScreenScope from
 * another inside one Activity. You can use this name for logging, analytics etc.
 */
public abstract class PSSFragmentV4 extends Fragment implements HasName {

    private PersistentScreenScope screenScope;
    private boolean screenRecreated = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPersistentScreenScope();
    }

    /**
     * @return true if screen was recreated after changing configuration
     */
    public boolean isScreenRecreated() {
        return screenRecreated;
    }

    /**
     * @return {@link PersistentScreenScope} of this screen
     */
    public PersistentScreenScope getPersistentScreenScope() {
        return screenScope;
    }

    /**
     * Clear all objects from own {@link PersistentScreenScope}
     */
    public void clearPersistentScreenScope() {
        if (screenScope != null) {
            screenScope.clear();
        }
    }

    private void initPersistentScreenScope() {
        screenScope = PersistentScreenScope.find(this);
        if (screenScope == null) {
            screenScope = createPersistentScreenScope();
            screenScope.attach(this);
            screenRecreated = false;
        } else {
            screenRecreated = true;
        }
    }


    protected PersistentScreenScope createPersistentScreenScope() {
        return new PersistentScreenScope();
    }

}

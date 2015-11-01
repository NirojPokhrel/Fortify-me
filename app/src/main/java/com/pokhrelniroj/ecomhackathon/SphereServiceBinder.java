package com.pokhrelniroj.ecomhackathon;

import android.os.Binder;

public class SphereServiceBinder extends Binder {
    private final SphereService service;

    public SphereServiceBinder(final SphereService service) {
        this.service = service;
    }

    public SphereService getService() {
        return service;
    }
}

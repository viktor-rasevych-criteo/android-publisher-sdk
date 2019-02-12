package com.criteo.pubsdk;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.text.TextUtils;

import com.criteo.pubsdk.model.AdUnit;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;

import java.util.List;

public final class Criteo implements LifecycleObserver {
    public static final String EVENT_INACTIVE = "Inactive";
    public static final String EVENT_ACTIVE = "Active";
    public static final String EVENT_LAUNCH = "Launch";
    private static Criteo criteo;
    private BidManager bidManager;

    public static Criteo init(Context context, List<AdUnit> adUnits, int networkId) {
        synchronized (Criteo.class) {
            if (criteo == null) {
                criteo = new Criteo(context, adUnits, networkId);
            }
        }
        return criteo;
    }

    private Criteo(Context context, List<AdUnit> adUnits, int networkId) {
        if (context == null) throw new IllegalArgumentException("Application context is required.");
        if (adUnits == null || adUnits.size() == 0)
            throw new IllegalArgumentException("AdUnits are required.");
        for (AdUnit adUnit : adUnits) {
            if (TextUtils.isEmpty(adUnit.getPlacementId()) || adUnit.getSize() == null
                    || adUnit.getSize().getWidth() <= 0 || adUnit.getSize().getHeight() <= 0) {
                throw new IllegalArgumentException("Found an invalid adUnit: " + adUnit);
            }
        }
        if (networkId == 0) throw new IllegalArgumentException("NetworkId is required.");
        this.bidManager = new BidManager(context, networkId, adUnits);
        ProcessLifecycleOwner.get().getLifecycle()
                .addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void appStart() {
        bidManager.prefetch();
        bidManager.postAppEvent(EVENT_ACTIVE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void appCreate() {
        bidManager.postAppEvent(EVENT_LAUNCH);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void appPause() {
        bidManager.postAppEvent(EVENT_INACTIVE);
    }

    public PublisherAdRequest.Builder setBidsForAdUnit(PublisherAdRequest.Builder request, AdUnit adUnit) {
        return bidManager.enrichBid(request, adUnit);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void appStop() {
        bidManager.cancelLoad();
    }

}
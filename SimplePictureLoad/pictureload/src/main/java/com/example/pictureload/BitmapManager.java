package com.example.pictureload;

import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by chtlei on 19-9-4.
 */

public class BitmapManager {
    private static volatile BitmapManager sInstance;

    public static BitmapManager getsInstance() {
        if (sInstance == null) {
            synchronized (BitmapManager.class) {
                if (sInstance == null) {
                    sInstance = new BitmapManager();
                }
            }
        }
        return sInstance;
    }

    private BitmapManager() {
        start();
    }

    private LinkedBlockingQueue<BitmapRequest> requestsQueue = new LinkedBlockingQueue<>();
    private BitmapDispatcher[] bitmapDispatchers;

    public void addBitmapRequest (BitmapRequest bitmapRequest) {
        if (bitmapRequest != null && !requestsQueue.contains(bitmapRequest)) {
            requestsQueue.add(bitmapRequest);
        }
    }

    private void start() {
        stop();
        startAllDispatch();
    }

    private void startAllDispatch() {
        int threadCount = Runtime.getRuntime().availableProcessors();
        Log.i("BitmapManager","availableProcessors is " + threadCount);
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < bitmapDispatchers.length; i++) {
            bitmapDispatchers[i] = new BitmapDispatcher(requestsQueue);
        }

        for (BitmapDispatcher bitmapDispatcher : bitmapDispatchers) {
            if (bitmapDispatcher != null) {
                bitmapDispatcher.start();
            }
        }
    }

    private void stop() {
        if (bitmapDispatchers != null && bitmapDispatchers.length > 0) {
            for (BitmapDispatcher bitmapDispatcher : bitmapDispatchers) {
                if (!bitmapDispatcher.isInterrupted()) {
                    bitmapDispatcher.isInterrupted();
                }
            }
        }
    }
}

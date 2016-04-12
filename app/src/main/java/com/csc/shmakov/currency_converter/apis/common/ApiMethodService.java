package com.csc.shmakov.currency_converter.apis.common;

import android.os.AsyncTask;

import java.util.concurrent.Callable;

/**
 * Created by Pavel on 3/12/2016.
 */
public abstract class ApiMethodService<T> {

    private ApiRequestCallback<T> callback;

    private ApiRequestResult<T> pendingResult;

    protected final void launchRequest(final Callable<ApiRequestResult<T>> executionCallable) {
        pendingResult = null;
        new AsyncTask<Void, Void, ApiRequestResult<T>>() {
            @Override
            protected ApiRequestResult<T> doInBackground(Void... params) {
                try {
                    return executionCallable.call();
                } catch (Exception e) {
                    return new ApiRequestResult.Error<>(e);
                }
            }

            @Override
            protected void onPostExecute(ApiRequestResult<T> result) {
                if (callback != null) {
                    pendingResult = null;
                    result.deliverToCallback(callback);
                } else {
                    pendingResult = result;
                }
            }
        }.execute();
    }

    public final void setCallback(ApiRequestCallback<T> callback) {
        this.callback = callback;
    }

    public final void deliverPendingResult() {
        if (pendingResult != null && callback != null) {
            pendingResult.deliverToCallback(callback);
            pendingResult = null;
        }
    }
}

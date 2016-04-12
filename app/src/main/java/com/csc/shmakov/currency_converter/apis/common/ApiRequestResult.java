package com.csc.shmakov.currency_converter.apis.common;

/**
 * Created by Pavel on 3/12/2016.
 */
public interface ApiRequestResult<T> {
    void deliverToCallback(ApiRequestCallback<T> callback);

    class Success<T> implements ApiRequestResult<T> {
        private final T result;

        public Success(T result) {
            this.result = result;
        }

        @Override
        public void deliverToCallback(ApiRequestCallback<T> callback) {
            if (callback != null) {
                callback.onSuccess(result);
            }
        }
    }

    class Error<T> implements ApiRequestResult<T> {
        private final Throwable error;

        public Error(Throwable error) {
            this.error = error;
        }

        @Override
        public void deliverToCallback(ApiRequestCallback<T> callback) {
            if (callback != null) {
                callback.onError(error);
            }
        }
    }
}
package com.csc.shmakov.currency_converter.model;

import android.app.Activity;

import com.csc.shmakov.currency_converter.apis.common.ApiRequestCallback;
import com.csc.shmakov.currency_converter.apis.currency.CurrencyApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pavel on 4/3/2016.
 */
public class Model {
    public static final Model INSTANCE = new Model();

    private final CurrencyApi api;

    private StorageManager storageManager;

    private DataChangeObserver dataChangeObserver;
    private List<Currency> data = new ArrayList<>();
    private boolean updatedWhenUnboundToContext;

    private Model() {
        api = new CurrencyApi();
        api.setCallback(new CurrencyApiCallback());
    }

    public void bindToContext(Activity activity, DataChangeObserver dataChangeObserver) {
        this.dataChangeObserver = dataChangeObserver;
        storageManager = new StorageManager(activity, itemsLoadCallback);
        if (updatedWhenUnboundToContext) {
            storageManager.write(data);
            dataChangeObserver.onDataReset();
            updatedWhenUnboundToContext = false;
        } else {
            storageManager.load();
        }
    }

    public void unbindFromContext() {
        storageManager = null;
        dataChangeObserver = null;
    }

    public Currency getItem(int position) {
        return data.get(position);
    }

    public int getSize() {
        return data.size();
    }

    public void update() {
        api.fetchCurrencies();
    }


    private static final Comparator<Currency> itemComparator = new Comparator<Currency>() {
        @Override
        public int compare(Currency lhs, Currency rhs) {
            return lhs.name.compareTo(rhs.name);
        }
    };

    private final StorageManager.LoadCallback itemsLoadCallback = new StorageManager.LoadCallback() {
        @Override
        public void onItemsLoaded(List<Currency> data) {
            Model.this.data = data;
            Collections.sort(Model.this.data, itemComparator);
            dataChangeObserver.onDataReset();
        }
    };
    private class CurrencyApiCallback implements ApiRequestCallback<List<Currency>> {

        @Override
        public void onSuccess(List<Currency> result) {
            Model.this.data = result;
            if (dataChangeObserver != null) {
                dataChangeObserver.onDataReset();
            }
            if (storageManager != null) {
                storageManager.write(result);
            } else {
                updatedWhenUnboundToContext = true;
            }
        }
        @Override
        public void onError(Throwable error) {

        }

    }
}

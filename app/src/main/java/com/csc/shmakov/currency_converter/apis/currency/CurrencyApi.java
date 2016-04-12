package com.csc.shmakov.currency_converter.apis.currency;

import com.csc.shmakov.currency_converter.apis.common.ApiMethodService;
import com.csc.shmakov.currency_converter.apis.common.ApiRequestResult;
import com.csc.shmakov.currency_converter.model.Currency;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Pavel on 3/7/2016.
 */
public class CurrencyApi extends ApiMethodService<List<Currency>> {

    private final OkHttpClient client = new OkHttpClient();

    public void fetchCurrencies() {
        launchRequest(new Callable<ApiRequestResult<List<Currency>>>() {
            @Override
            public ApiRequestResult<List<Currency>> call() throws Exception {
                return parseResponse(client.newCall(buildRequest()).execute());
            }
        });
    }

    private Request buildRequest() throws UnsupportedEncodingException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("api.fixer.io")
                .addPathSegment("latest")
                .addQueryParameter("base", "RUB")
                .build();
        return new Request.Builder()
                .url(url)
                .get()
                .build();
    }

    private ApiRequestResult<List<Currency>> parseResponse(Response response) throws JSONException, IOException {
        if (response.code() != 200) {
            return new ApiRequestResult.Error<>(new Exception("Code " + response.code()));
        }

        JSONObject currencyJson = new JSONObject(response.body().string())
                .getJSONObject("rates");
        List<Currency> currencyList = new ArrayList<>();
        for (Iterator<String> iter = currencyJson.keys(); iter.hasNext();) {
            String name = iter.next();
            currencyList.add(new Currency(name, 1 / (float) currencyJson.getDouble(name)));
        }

        return new ApiRequestResult.Success<>(currencyList);
    }
}

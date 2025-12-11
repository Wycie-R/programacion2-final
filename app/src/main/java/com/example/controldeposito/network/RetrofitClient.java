package com.example.controldeposito.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // CAMBIO: Ponemos solo la raíz del sitio web (IMPORTANTE: Con la barra al final)
    private static final String BASE_URL = "https://webhook.site/";

    private static Retrofit retrofit = null;

    public static WebhookService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(WebhookService.class);
    }
}
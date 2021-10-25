package com.androidnativesample.webservice;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyOkHttpInterceptor implements Interceptor {
        final String tokenServer;

        public MyOkHttpInterceptor(String tokenServer) {
            this.tokenServer = tokenServer;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String token = tokenServer;// get token logic
            Request newRequest = originalRequest.newBuilder()
                    .header("Authorization", token)
                    .build();
            return chain.proceed(newRequest);
        }
    }
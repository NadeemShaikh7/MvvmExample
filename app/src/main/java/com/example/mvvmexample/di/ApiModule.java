package com.example.mvvmexample.di;

import com.example.mvvmexample.model.CountryApi;
import com.example.mvvmexample.model.CountryService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    public static final String BASE_URL = "https://raw.githubusercontent.com";

    @Provides
    public CountryApi provideCountriesApi(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(CountryApi.class);
    }

    @Provides
    public CountryService providesServiceModule(){
        return CountryService.getInstance();
    }
}

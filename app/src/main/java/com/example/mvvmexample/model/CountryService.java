package com.example.mvvmexample.model;

import android.util.Log;

import com.example.mvvmexample.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class CountryService{

    public static CountryService instance;

    @Inject
    public CountryApi api;

    public CountryService() {
        DaggerApiComponent.create().inject(this);
    }

    public static CountryService getInstance(){
        if (instance == null) {
            return new CountryService();
        }
        return instance;
    }

    public Single<List<CountryModel>> getCountries(){
        Log.e("Nads"," get Countries received");
        return api.getCountries();
    }
}

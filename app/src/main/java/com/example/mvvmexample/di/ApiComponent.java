package com.example.mvvmexample.di;

import com.example.mvvmexample.model.CountryService;
import com.example.mvvmexample.viewmodel.ListViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(CountryService countryService);
    void inject(ListViewModel listViewModel);
}

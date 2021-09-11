package com.example.mvvmexample.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmexample.di.DaggerApiComponent;
import com.example.mvvmexample.model.CountryModel;
import com.example.mvvmexample.model.CountryService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends ViewModel {
    public MutableLiveData<List<CountryModel>> countryList = new MutableLiveData<List<CountryModel>>();
    public MutableLiveData<Boolean> countryError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    @Inject
    public CountryService countryService;

    public ListViewModel() {
        super();
        DaggerApiComponent.create().inject(this);
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void refresh(){
        fetchCountryList();
    }

    private void fetchCountryList() {
        compositeDisposable.add(countryService.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<CountryModel>>() {
                    @Override
                    public void onSuccess(@NotNull List<CountryModel> countryModels) {
                        loading.setValue(false);
                        countryList.setValue(countryModels);
                        countryError.setValue(false);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        loading.setValue(false);
                        countryError.setValue(true);
                    }
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}

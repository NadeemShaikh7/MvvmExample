package com.example.mvvmexample;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.mvvmexample.model.CountryModel;
import com.example.mvvmexample.model.CountryService;
import com.example.mvvmexample.viewmodel.ListViewModel;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class ListViewModelTest {

    private Single<List<CountryModel>> testSingle;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    CountryService countryService;

    @InjectMocks
    ListViewModel listViewModel = new ListViewModel();

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCountriesSuccess(){
        CountryModel countryModel =  new CountryModel("countryName","capital","flag");
        ArrayList<CountryModel> countryList = new ArrayList<>();
        countryList.add(countryModel);

        testSingle = Single.just(countryList);

        Mockito.when(countryService.getCountries()).thenReturn(testSingle);
        listViewModel.refresh();

        Assert.assertEquals(1,listViewModel.countryList.getValue().size());
        Assert.assertEquals(false,listViewModel.loading.getValue());
        Assert.assertEquals(false,listViewModel.countryError.getValue());
    }

    @Test
    public void getCountriesFailure(){

        testSingle = Single.error(new Throwable());
        Mockito.when(countryService.getCountries()).thenReturn(testSingle);
        listViewModel.refresh();

        Assert.assertEquals(false,listViewModel.loading.getValue());
        Assert.assertEquals(true,listViewModel.countryError.getValue());
    }

    @Before
    public void setupSchedulers(){
        Scheduler immediate = new Scheduler() {
            @NotNull
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run,true);
            }
        };

        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }
}

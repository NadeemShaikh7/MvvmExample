package com.example.mvvmexample.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mvvmexample.R;
import com.example.mvvmexample.viewmodel.ListViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.loader)
    ProgressBar loader;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private ListViewModel viewModel;
    private ListAdapter adapter = new ListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        viewModel.refresh();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        observeViewModel();
    }


    private void observeViewModel() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        viewModel.countryList.observe(this,countryModels -> {
            if(countryModels != null){
                Log.e("Nads"," models received");
                recyclerView.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);
                adapter.updateCountries(countryModels);
            }
        });

        viewModel.countryError.observe(this,error -> {
            if(error != null){
                tvError.setVisibility(error ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.loading.observe(this,loading -> {
            if(loading != null){
                Log.e("Nads"," loading received");
                tvError.setVisibility(loading ? View.VISIBLE : View.GONE);
                if(loading){
                    recyclerView.setVisibility(View.GONE);
                    tvError.setVisibility(View.GONE);
                }
            }
        });
    }
}
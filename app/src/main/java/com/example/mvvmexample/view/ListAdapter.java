package com.example.mvvmexample.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmexample.R;
import com.example.mvvmexample.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CountryViewHolder> {


    ArrayList<CountryModel> countryList;

    public ListAdapter(ArrayList<CountryModel> countryList) {
        this.countryList = countryList;
    }

    public void updateCountries(List<CountryModel> newCountries){
        countryList.clear();
        countryList.addAll(newCountries);
        notifyDataSetChanged();
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
       View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_row,parent,false);
       return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ListAdapter.CountryViewHolder holder, int position) {
        holder.bind(countryList.get(position));
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView)
        ImageView countryImage;

        @BindView(R.id.countryName)
        TextView countryName;

        @BindView(R.id.capital)
        TextView capital;

        public CountryViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(CountryModel countryModel){
            countryName.setText(countryModel.getCountryName());
            capital.setText(countryModel.getCapital());
            Util.loadImage(countryImage,countryModel.getFlag());
        }
    }
}

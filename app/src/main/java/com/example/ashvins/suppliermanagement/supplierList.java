package com.example.ashvins.suppliermanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class supplierList extends Fragment {
    private RecyclerView recyclerView;
    RecyclerView_Config.SupplierAdapter supAdapter;
    List<supplierDB> supplier;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.supplier_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_supplier);
        setHasOptionsMenu(true);
        new FirebaseDBHelper().readSupplier(new FirebaseDBHelper.dStatus() {
            @Override
            public void DataLoaded(List<supplierDB> suppliers, List<String> keys) {
                new RecyclerView_Config().setConfig(recyclerView, getActivity(), suppliers, keys);
                supplier = suppliers;
                supAdapter = new RecyclerView_Config().setConfig(getActivity(), supplier);
            }

            @Override
            public void DataAdded() {

            }

            @Override
            public void DataUpdated() {

            }

            @Override
            public void DataDeleted() {

            }
        });
        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                supAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                supAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item); // important line
    }
}

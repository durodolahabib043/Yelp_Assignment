package com.durodola.mobile.rbc_yelp_app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.durodola.mobile.rbc_yelp_app.Abstract.AbstractResturantFragment;
import com.durodola.mobile.rbc_yelp_app.Utils.Constant;
import com.durodola.mobile.rbc_yelp_app.R;
import com.durodola.mobile.rbc_yelp_app.Adapter.ResturantAdapter;
import com.durodola.mobile.rbc_yelp_app.Utils.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResturantFragment extends AbstractResturantFragment implements SearchView.OnQueryTextListener {
    YelpAPIFactory yelpAPIFactory;
    ResturantAdapter resturantAdapter;
    ArrayList<Business> data;
    RecyclerView rv;
    GridLayoutManager gridLayoutManager;
    Map<String, String> params;
    SearchView searchView;
    com.yelp.clientlib.connection.YelpAPI yelpAPI;

    public ResturantFragment() {

    }

    // singleton
    public static ResturantFragment newInstance() {
        return new ResturantFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resturant, container, false);
        // check for internet
        if (isConnected() == true) {
            Init(view);
        } else {
            noNetworkAlert();
        }

        return view;
    }

    // initialization  of views
    private void Init(View view) {
        yelpAPIFactory = new YelpAPIFactory(Constant.CONSUMER_KEY, Constant.CONSUMER_SECRET, Constant.TOKEN, Constant.TOKEN_SECRET);
        yelpAPI = yelpAPIFactory.createAPI();
        params = new HashMap<>();
        searchView = (SearchView) view.findViewById(R.id.searchview);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(gridLayoutManager);
        downloadResturantDeatils();
        final ProgressDialog loading;
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.setQuery("", false);
        searchView.clearFocus();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // searchview functionality
    @Override
    public boolean onQueryTextChange(String query) {
        searchResturant(query);
        rv.scrollToPosition(0);
        return true;
    }

    // Query the recyclerview
    private void searchResturant(String search) {
        params.put("term", search);
        params.put("limit", "10");
        searchResutrantService();
    }

    private void searchResutrantService() {

        Call<SearchResponse> call = yelpAPI.search("toronto", params);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, final Response<SearchResponse> response) {
                data = response.body().businesses();
                sortResturant(data);
                resturantAdapter = new ResturantAdapter(getContext(), data);
                rv.setAdapter(resturantAdapter);
                if (resturantAdapter != null) {
                    resturantAdapter.SetOnItemCLickListener(new ResturantAdapter.MyItemClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            String name = response.body().businesses().get(position).name();
                            String address = response.body().businesses().get(position).location().displayAddress().toString();
                            String imageUrl = response.body().businesses().get(position).imageUrl();
                            String latestReview = response.body().businesses().get(position).snippetText();
                            String phone = response.body().businesses().get(position).displayPhone();
                            Double rate = response.body().businesses().get(position).rating();
                            Switchfragment(ResturantDetail.newInstance(), name, address, imageUrl, latestReview, phone, rate);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
            }
        });
    }

    // download random 10 resturant
    private void downloadResturantDeatils() {
        final ProgressDialog loading = ProgressDialog.show(getContext(), Constant.RESTURANT_M, Constant.WAIT, false, false);
        params.put("term", "resturant");
        params.put("limit", "10");
        Call<SearchResponse> call = yelpAPI.search("toronto", params);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, final Response<SearchResponse> response) {
                data = response.body().businesses();
                sortResturant(data);
                resturantAdapter = new ResturantAdapter(getContext(), data);
                rv.setAdapter(resturantAdapter);
                loading.dismiss();
                if (resturantAdapter != null) {
                    resturantAdapter.SetOnItemCLickListener(new ResturantAdapter.MyItemClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            String name = response.body().businesses().get(position).name();
                            String address = response.body().businesses().get(position).location().displayAddress().toString();
                            String imageUrl = response.body().businesses().get(position).imageUrl();
                            String latestReview = response.body().businesses().get(position).snippetText();
                            String phone = response.body().businesses().get(position).displayPhone();
                            Double rate = response.body().businesses().get(position).rating();
                            Switchfragment(ResturantDetail.newInstance(), name, address, imageUrl, latestReview, phone, rate);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
            }
        });

    }


}

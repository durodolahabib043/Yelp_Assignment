package com.durodola.mobile.rbc_yelp_app.Abstract;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.durodola.mobile.rbc_yelp_app.R;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mobile on 2016-03-16.
 */
public abstract class AbstractResturantFragment extends Fragment {
    // switch fragment
    protected void Switchfragment(Fragment fragment, String name, String address, String imageurl, String review, String phone, Double rate) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contentlayout, fragment);
        transaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("address", address);
        bundle.putString("imageUrl", imageurl);
        bundle.putString("review", review);
        bundle.putString("phone", phone);
        bundle.putDouble(String.valueOf("rating"), rate);
        fragment.setArguments(bundle);
        transaction.commit();
    }

    // filter adapter
    public List<Business> filter(List<Business> models, String query) {
        query = query.toLowerCase();

        final List<Business> filteredModelList = new ArrayList<>();
        for (Business model : models) {
            final String text = model.name().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    // check if internet is  available
    public boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /// dialog if network is not available
    public void noNetworkAlert() {
        // display dialog when no internet
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Uh Oh");
        builder.setMessage("We couldn't retrieve the data. Please check your network connection and try again.");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }

        });
        builder.show();
    }

    //sort arraylist
    public void sortResturant(ArrayList<Business> arr) {

        Collections.sort(arr, new Comparator<Business>() {

            @Override
            public int compare(Business o1, Business o2) {
                return o1.name().compareTo(o2.name());
            }
        });
    }


}

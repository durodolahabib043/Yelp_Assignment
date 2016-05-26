package com.durodola.mobile.rbc_yelp_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by mobile on 2016-05-25.
 */
public class ResturantDetail extends AbstractResturantFragment {
    Bundle bundle;
    Double rating;
    String name, address, imageUrl, review, phone;
    TextView nametxt, addresstxt, reviewtxt, phonetxt, ratingtxt;
    ImageView imageView;


    public ResturantDetail() {
        // Required empty public constructor
    }


    public static ResturantDetail newInstance() {
        return new ResturantDetail();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resturant_details, container, false);
        Init(view);
        return view;
    }

    private void Init(View view) {
        nametxt = (TextView) view.findViewById(R.id.name);
        addresstxt = (TextView) view.findViewById(R.id.address);
        reviewtxt = (TextView) view.findViewById(R.id.review);
        phonetxt = (TextView) view.findViewById(R.id.phone);
        imageView = (ImageView) view.findViewById(R.id.imagel);
        ratingtxt = (TextView) view.findViewById(R.id.rating);
        bundle = this.getArguments();
        address = bundle.getString(Constant.ADDRESS);
        review = bundle.getString(Constant.REVIEW);
        imageUrl = bundle.getString(Constant.IMAGE_URL);
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        rating = Double.valueOf(bundle.getDouble(String.valueOf("rating")));
        nametxt.setText(name);
        reviewtxt.setText(review);
        addresstxt.setText(address.substring(1, address.length() - 1));
        phonetxt.setText(phone);
        ratingtxt.setText("" + rating + "/5.0 ");
        Picasso.with(this.getContext()).load(imageUrl).into(imageView);
    }
}

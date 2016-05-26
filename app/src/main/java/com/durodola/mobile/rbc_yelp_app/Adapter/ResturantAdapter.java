package com.durodola.mobile.rbc_yelp_app.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.durodola.mobile.rbc_yelp_app.R;
import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobile on 2016-04-01.
 */
public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.PersonViewHolder> {

    ArrayList<Business> searchResponseArrayList;
    Context context;

    private static MyItemClickListener mItemClickListener;

    public ResturantAdapter(Context context, ArrayList<Business> searchResponseArrayList) {
        this.searchResponseArrayList = searchResponseArrayList;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(PersonViewHolder holder, final int position) {
        String address = searchResponseArrayList.get(position).location().displayAddress().toString();
        holder.name_cardview.setText(searchResponseArrayList.get(position).name());
        holder.address_cardview.setText(address.substring(1, address.length() - 1));
        Picasso.with(this.context).load(searchResponseArrayList.get(position).imageUrl()).into(holder.photo);
    }
    @Override
    public int getItemCount() {
        return searchResponseArrayList.size();
    }


    public void animateTo(List<Business> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Business> newModels) {
        for (int i = searchResponseArrayList.size() - 1; i >= 0; i--) {
            final Business model = searchResponseArrayList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Business> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Business model = newModels.get(i);
            if (!searchResponseArrayList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Business> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Business model = newModels.get(toPosition);
            final int fromPosition = searchResponseArrayList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }


    public Business removeItem(int position) {
        final Business model = searchResponseArrayList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Business model) {
        searchResponseArrayList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Business model = searchResponseArrayList.remove(fromPosition);
        searchResponseArrayList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public interface MyItemClickListener {
        public void onItemClick(int position, View v);

    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView address_cardview;
        TextView name_cardview;
        ImageView photo;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            address_cardview = (TextView) itemView.findViewById(R.id.name_cardview);
            name_cardview = (TextView) itemView.findViewById(R.id.address_cardview);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getAdapterPosition(), v);

            }
        }
    }

    public void SetOnItemCLickListener(MyItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

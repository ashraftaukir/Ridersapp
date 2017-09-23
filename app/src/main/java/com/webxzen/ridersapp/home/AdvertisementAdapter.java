package com.webxzen.ridersapp.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.model.AdvertisementModel;

import java.util.ArrayList;


public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.MyViewHolder> {

    private ArrayList<AdvertisementModel> advertiseList;
    public AdvertisementAdapter(ArrayList<AdvertisementModel> advertiseList) {
        this.advertiseList = advertiseList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        AdvertisementModel advertisementModel = advertiseList.get(position);
        holder.subtittle_tv.setText(advertisementModel.getSubheader());
        holder.advertisement_tittle_tv.setText(advertisementModel.getTittle());
        holder.document_tv.setText(advertisementModel.getContent());

    }

    @Override
    public int getItemCount() {
        return advertiseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subtittle_tv, advertisement_tittle_tv, document_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            subtittle_tv = (TextView) itemView.findViewById(R.id.subtittle_tv);
            advertisement_tittle_tv = (TextView) itemView.findViewById(R.id.advertisement_tittle_tv);
            document_tv = (TextView) itemView.findViewById(R.id.document_tv);
        }


    }
}

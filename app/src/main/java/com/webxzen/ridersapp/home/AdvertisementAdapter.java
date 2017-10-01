package com.webxzen.ridersapp.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.model.AdvertisementModel;

import java.util.ArrayList;

import static com.thefinestartist.utils.content.ContextUtil.startActivity;


public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.MyViewHolder> {

    private ArrayList<AdvertisementModel> advertiseList;
    private Activity activity;
    public AdvertisementAdapter(ArrayList<AdvertisementModel> advertiseList,Activity activity) {
        this.advertiseList = advertiseList;
        this.activity=activity;

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

        TextView subtittle_tv, advertisement_tittle_tv, document_tv,visit_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            visit_tv = (TextView) itemView.findViewById(R.id.visit_our_tv);
            visit_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("http://arabaworld.com");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    activity.startActivity(intent);
                }
            });

            subtittle_tv = (TextView) itemView.findViewById(R.id.subtittle_tv);
            advertisement_tittle_tv = (TextView) itemView.findViewById(R.id.advertisement_tittle_tv);
            document_tv = (TextView) itemView.findViewById(R.id.document_tv);
        }
    }
}

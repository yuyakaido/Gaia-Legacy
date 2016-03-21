package com.yuyakaido.android.genesis.presentation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuyakaido.android.genesis.R;
import com.yuyakaido.android.genesis.domain.entity.PixabayMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyakaido on 3/5/16.
 */
public class PixabayAdapter extends ArrayAdapter<PixabayMedia> {

    private List<PixabayMedia> pixabayMedias = new ArrayList<>();

    public PixabayAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item_pixabay, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PixabayMedia pixabayMedia = getItem(position);

        Glide.with(getContext())
                .load(pixabayMedia.previewUrl)
                .into(holder.image);

        return convertView;
    }

    @Override
    public int getCount() {
        return pixabayMedias.size();
    }

    @Override
    public PixabayMedia getItem(int position) {
        return pixabayMedias.get(position);
    }

    public void setPixabayMedias(List<PixabayMedia> pixabayMedias) {
        this.pixabayMedias = pixabayMedias;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView image;

        public ViewHolder(View view) {
            this.image = (ImageView) view.findViewById(R.id.item_pixabay_image);
        }
    }

}

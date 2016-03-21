package com.yuyakaido.android.genesis.presentation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yuyakaido.android.genesis.R;
import com.yuyakaido.android.genesis.domain.entity.QiitaItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyakaido on 3/12/16.
 */
public class QiitaAdapter extends ArrayAdapter<QiitaItem> {

    private List<QiitaItem> qiitaItems = new ArrayList<>();

    public QiitaAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item_qiita, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        QiitaItem qiitaItem = getItem(position);

        holder.title.setText(qiitaItem.title);

        return convertView;
    }

    @Override
    public QiitaItem getItem(int position) {
        return qiitaItems.get(position);
    }

    @Override
    public int getCount() {
        return qiitaItems.size();
    }

    public void setQiitaItems(List<QiitaItem> qiitaItems) {
        this.qiitaItems = qiitaItems;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView title;

        public ViewHolder(View view) {
            this.title = (TextView) view.findViewById(R.id.item_qiita_title);
        }
    }

}

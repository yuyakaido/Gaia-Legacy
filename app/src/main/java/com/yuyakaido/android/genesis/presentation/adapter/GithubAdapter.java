package com.yuyakaido.android.genesis.presentation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuyakaido.android.genesis.domain.entity.GithubContributor;
import com.yuyakaido.android.genesis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyakaido on 2/15/16.
 */
public class GithubAdapter extends ArrayAdapter<GithubContributor> {

    private List<GithubContributor> githubContributors = new ArrayList<>();

    public GithubAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item_github, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GithubContributor githubContributor = getItem(position);
        holder.login.setText(githubContributor.login);
        Glide.with(getContext())
                .load(githubContributor.avatarUrl)
                .into(holder.image);

        return convertView;
    }

    @Override
    public GithubContributor getItem(int position) {
        return githubContributors.get(position);
    }

    @Override
    public int getCount() {
        return githubContributors.size();
    }

    public void setGithubContributors(List<GithubContributor> githubContributors) {
        this.githubContributors = githubContributors;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView login;
        public ImageView image;

        public ViewHolder(View view) {
            this.login = (TextView) view.findViewById(R.id.item_github_login);
            this.image = (ImageView) view.findViewById(R.id.item_github_image);
        }
    }

}

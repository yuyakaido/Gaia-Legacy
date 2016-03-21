package com.yuyakaido.android.genesis.domain.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.OnConflict;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuyakaido on 2/15/16.
 */
@Table(value = "GithubContributor")
public class GithubContributor {

    @Column(value = "Login", unique = true, uniqueOnConflict = OnConflict.REPLACE)
    @SerializedName("login")
    public String login;

    @Column(value = "AvatarUrl")
    @SerializedName("avatar_url")
    public String avatarUrl;

    @Column(value = "HtmlUrl")
    @SerializedName("html_url")
    public String htmlUrl;

}

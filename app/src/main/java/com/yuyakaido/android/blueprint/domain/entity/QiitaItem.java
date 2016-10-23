package com.yuyakaido.android.blueprint.domain.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.OnConflict;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuyakaido on 3/12/16.
 */
@Table(value = "QiitaItem")
public class QiitaItem {

    @Column(value = "id", unique = true, uniqueOnConflict = OnConflict.REPLACE)
    @SerializedName("id")
    public String id;

    @Column(value = "Title")
    @SerializedName("title")
    public String title;

    @Column(value = "Url")
    @SerializedName("url")
    public String url;

}

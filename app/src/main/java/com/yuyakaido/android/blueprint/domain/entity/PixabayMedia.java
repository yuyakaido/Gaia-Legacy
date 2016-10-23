package com.yuyakaido.android.blueprint.domain.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.OnConflict;
import com.github.gfx.android.orma.annotation.Table;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yuyakaido on 3/5/16.
 */
@Table(value = "PixabayMedia")
public class PixabayMedia {

    @SerializedName("id")
    @Column(value = "MediaId", unique = true, uniqueOnConflict = OnConflict.REPLACE)
    public long mediaId;

    @SerializedName("previewURL")
    @Column(value = "PreviewUrl")
    public String previewUrl;

    @SerializedName("pageURL")
    @Column(value = "PageUrl")
    public String pageUrl;

}

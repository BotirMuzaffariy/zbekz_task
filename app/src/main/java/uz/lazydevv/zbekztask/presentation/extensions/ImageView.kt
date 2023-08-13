package uz.lazydevv.zbekztask.presentation.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import uz.lazydevv.zbekztask.R

fun ImageView.loadImageUrl(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.img_placeholder)
        .into(this)
}
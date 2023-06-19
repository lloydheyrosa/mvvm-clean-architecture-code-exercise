package com.lloydheyrosa.contactlist.ui.adapter.binding

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lloydheyrosa.contactlist.R

/**
 * Adapter for `app:imageUrl` & `app:imagePlaceholder` for `<ImageView>` and subclasses.
 *
 * Loading the [imageUrl] is handled by Glide.
 *
 * The optional [imagePlaceholder] parameter can either be a ColorRes, DrawableRes.
 */
@SuppressLint("CheckResult")
@BindingAdapter(
    "imageUrl",
    "imagePlaceholder",
    "imagePlaceholderDrawable",
    "imageForceLoad",
    "imageTransform",
    requireAll = false
)
fun setImageUrl(
    imageView: ImageView,
    imageUrl: String?,
    @DrawableRes imagePlaceholder: Int,
    imagePlaceholderDrawable: Drawable? = null,
    isForceLoad: Boolean = false,
    transform: Transformation<Bitmap>? = null,
) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .apply {
            if (isForceLoad) {
                apply(RequestOptions.skipMemoryCacheOf(true))
                apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            }
            if (transform != null) {
                transform(transform)
            }
            when {
                imagePlaceholderDrawable != null -> placeholder(imagePlaceholderDrawable)
                imagePlaceholder != 0 -> placeholder(imagePlaceholder)
                else -> placeholder(R.color.gray)
            }
        }
        .into(imageView)
}
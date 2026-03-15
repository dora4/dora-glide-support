package dora.glide

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import dora.glide.transformation.BlurTransformation
import dora.glide.transformation.CircleBorderTransform
import dora.lifecycle.glide.R
import dora.util.ScreenUtils
import java.io.File
import androidx.core.graphics.drawable.toDrawable
import dora.glide.transformation.RoundRectBorderTransform
import kotlin.Float

/**
 * 默认RequestOptions。
 */
private val defaultOptions = RequestOptions()
    .placeholder(R.drawable.dora_default_placeholder)
    .error(R.drawable.dora_default_placeholder)
    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

/**
 * 基础加载。
 */
fun ImageView.setUrl(url: String) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .apply(defaultOptions)
        .into(this)
}

/**
 * 无占位图。
 */
fun ImageView.setUrlNoPlaceholder(url: String) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .placeholder(Color.TRANSPARENT.toDrawable())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

/**
 * 不使用缓存。
 */
fun ImageView.setUrlNoCache(url: String) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .priority(Priority.HIGH)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .apply(defaultOptions)
        .into(this)
}

/**
 * 圆形头像。
 */
fun ImageView.setUrlCircle(url: String) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .apply(defaultOptions)
        .circleCrop()
        .into(this)
}

/**
 * 圆形带边框头像。
 */
fun ImageView.setUrlCircleBorder(url: String, borderWidth: Float, borderColor: Int) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .apply(defaultOptions)
        .transform(CircleBorderTransform(borderWidth, borderColor))
        .into(this)
}

/**
 * 圆角矩形头像。
 */
fun ImageView.setUrlRoundRect(url: String, radius: Int = 10) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .apply(defaultOptions)
        .transform(CenterCrop(), RoundedCorners(radius))
        .into(this)
}

/**
 * 圆角矩形带边框头像。
 */
fun ImageView.setUrlRoundRect(url: String, radius: Int = 10, borderWidth: Float, borderColor: Int) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .apply(defaultOptions)
        .transform(
            CenterCrop(),
            RoundRectBorderTransform(radius, borderWidth, borderColor)
        )
        .into(this)
}

/**
 * 错误图自定义。
 */
fun ImageView.setUrlWithErrorIcon(url: String, @DrawableRes errorRes: Int) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .placeholder(errorRes)
        .error(errorRes)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

/**
 * Bitmap回调。
 */
fun ImageView.setUrlAsBitmap(url: String, block: ((Bitmap) -> Unit)? = null) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .asBitmap()
        .load(url)
        .apply(defaultOptions)
        .into(object : CustomTarget<Bitmap>() {

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                block?.invoke(resource)
                setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

/**
 * GIF加载。
 */
fun ImageView.setUrlAsGif(url: String) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .asGif()
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(defaultOptions)
        .into(this)
}

/**
 * 模糊图片。
 */
fun ImageView.setUrlBlur(url: String, radius: Int = 25, sampling: Int = 1) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(url)
        .apply(defaultOptions)
        .transform(BlurTransformation(radius, sampling))
        .into(this)
}

/**
 * 自适应屏幕宽度。
 */
fun ImageView.setUrlAutoFitImage(url: String) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .asDrawable()
        .load(url)
        .apply(defaultOptions)
        .into(object : CustomTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                val width = resource.intrinsicWidth
                val height = resource.intrinsicHeight
                val lp = layoutParams
                lp.width = ScreenUtils.getScreenWidth(context)
                lp.height = (height * (lp.width.toFloat() / width)).toInt()
                layoutParams = lp
                setImageDrawable(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

/**
 * 加载本地文件。
 */
fun ImageView.loadFile(file: File) {
    if (!assertValidRequest(context)) return
    Glide.with(context)
        .load(file)
        .apply(defaultOptions)
        .circleCrop()
        .into(this)
}

/**
 * 检查Activity是否有效。
 */
private fun assertValidRequest(context: Context): Boolean {
    val activity = when (context) {
        is Activity -> context
        is ContextWrapper -> context.baseContext as? Activity
        else -> null
    }
    return activity?.let { !it.isFinishing && !it.isDestroyed } ?: true
}
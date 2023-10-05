package dora.glide

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

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
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import dora.glide.transformation.BlurTransformation
import dora.glide.transformation.CircleBorderTransform
import dora.lifecycle.glide.R
import dora.util.ScreenUtils
import dora.util.TaskStackManager
import java.io.File

fun ImageView.setUrl(url: String) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).load(url)
            .placeholder(R.drawable.dora_default_placeholder) // 占位符，异常时显示的图片
            .error(R.drawable.dora_default_placeholder) // 错误时显示的图片
            .skipMemoryCache(false) // 启用内存缓存
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // 磁盘缓存策略
            .into(this)
}

fun ImageView.setUrlNoPlaceholder(url: String) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).load(url)
        .placeholder(ColorDrawable(Color.TRANSPARENT))
        .error(R.drawable.dora_default_circle_placeholder) // 错误时显示的图片
        .skipMemoryCache(false) //启用内存缓存
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) //磁盘缓存策略
        .into(this)
}

/**
 * 设置图片，不开启缓存。
 */
fun ImageView.setUrlNoCache(url: String) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).load(url)
        .placeholder(R.drawable.dora_default_placeholder)
        .error(R.drawable.dora_default_placeholder)
        .priority(Priority.HIGH)
        .skipMemoryCache(true) // 不启动缓存
        .diskCacheStrategy(DiskCacheStrategy.NONE) // 不启用磁盘策略
        .into(this)
}

/**
 * 加载圆形图片。
 */
fun ImageView.setUrlCircle(url: String) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    //请求配置
    val options = RequestOptions.circleCropTransform()
    Glide.with(context).load(url)
        .placeholder(R.drawable.dora_default_circle_placeholder)
        .error(R.drawable.dora_default_circle_placeholder)
        .skipMemoryCache(false) // 启用内存缓存
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .apply(options) // 圆形
        .into(this)
}

/**
 * 加载边框圆形图片。
 *
 * @param borderWidth 边框宽度
 * @param borderColor 边框颜色
 */
fun ImageView.setUrlCircleBorder(url: String, borderWidth: Float, borderColor: Int) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).load(url)
        .placeholder(R.drawable.dora_default_placeholder)
        .error(R.drawable.dora_default_placeholder)
        .skipMemoryCache(false) //启用内存缓存
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .transform(CircleBorderTransform(borderWidth, borderColor)) // 圆形
        .into(this)
}

/**
 * 加载圆角图片。
 *
 * 注意：glide的图片裁剪和ImageView  scaleType有冲突，
 * bitmap会先圆角裁剪，再加载到ImageView中，如果bitmap图片尺寸大于ImageView尺寸，则会看不到
 * 使用CenterCrop()重载，会先将bitmap居中裁剪，再进行圆角处理，这样就能看到了。
 */
fun ImageView.setUrlRound(url: String, radius: Int = 10) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).load(url)
        .placeholder(R.drawable.dora_default_placeholder)
        .error(R.drawable.dora_default_placeholder)
        .skipMemoryCache(false) // 启用内存缓存
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .transform(CenterCrop(), RoundedCorners(radius))
        .into(this)
}

fun ImageView.setUrlWithErrorIcon(url: String, @DrawableRes errorRes: Int) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).load(url)
        .placeholder(errorRes)
        .error(errorRes)
        .priority(Priority.HIGH)
        .skipMemoryCache(true) //不启动缓存
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}

fun ImageView.setUrlAsBitmap(url: String, block: ((Bitmap) -> Unit)? = null) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).asBitmap().load(url)
        .placeholder(R.drawable.dora_default_placeholder)
        .error(R.drawable.dora_default_placeholder)
        .into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                // 可在这里对位图进行一些处理
                block?.invoke(bitmap)
                setImageBitmap(bitmap)
            }
        })
}

fun ImageView.setUrlAsGif(url: String) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).asGif().load(url)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(R.drawable.dora_default_placeholder)
        .error(R.drawable.dora_default_placeholder)
        .into(this)
}

/**
 * 设置图片高斯模糊。
 *
 * @param radius 设置模糊度(在0.0到25.0之间)，默认25
 * @param sampling  图片缩放比例，默认1
 */
fun ImageView.setUrlBlur(url: String, radius: Int = 25, sampling: Int = 1) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    //请求配置
    val options = RequestOptions.bitmapTransform(BlurTransformation(radius, sampling))
    Glide.with(context)
            .load(url)
            .placeholder(R.drawable.dora_default_placeholder)
            .error(R.drawable.dora_default_placeholder)
            .apply(options)
            .into(this)
}

/**
 * 适配屏幕宽度，高度自适应。
 */
fun ImageView.setUrlAutoFitImage(url: String) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    Glide.with(context).asDrawable().load(url)
            .placeholder(R.drawable.dora_default_placeholder)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .error(R.drawable.dora_default_placeholder)
            .into(object : CustomTarget<Drawable?>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                    val width = resource.intrinsicWidth
                    val height = resource.intrinsicHeight
                    val lp = layoutParams
                    lp.width = ScreenUtils.getScreenWidth(context)
                    val tempHeight = height * (lp.width.toFloat() / width)
                    lp.height = tempHeight.toInt()
                    layoutParams = lp
                    layoutParams = lp
                    setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
}

fun ImageView.loadFile(file: File) {
    if (TaskStackManager.getInstance().topActivity != null
        && TaskStackManager.getInstance().topActivity.isFinishing) {
        return
    }
    // 请求配置
    val options = RequestOptions.circleCropTransform()
    Glide.with(context).load(file)
        .placeholder(R.drawable.dora_default_placeholder) // 占位符，异常时显示的图片
        .error(R.drawable.dora_default_placeholder) // 错误时显示的图片
        .skipMemoryCache(false) // 启用内存缓存
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // 磁盘缓存策略
        .apply(options) // 圆形
        .into(this)
}


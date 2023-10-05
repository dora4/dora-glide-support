package dora.glide

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class DoraGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 获取最大可用内存
        val maxMemory = Runtime.getRuntime().maxMemory().toInt()
        // 设置缓存的大小
        val cacheSize = maxMemory / 8
        // 设置Bitmap的缓存池
        builder.setBitmapPool(LruBitmapPool(30))
        // 设置内存缓存
        builder.setMemoryCache(LruResourceCache(cacheSize.toLong()))
        // 设置磁盘缓存
        builder.setDiskCache(InternalCacheDiskCacheFactory(context))
        // 设置读取不在缓存中资源的线程
        builder.setSourceExecutor(GlideExecutor.newSourceExecutor())
        // 设置读取磁盘缓存中资源的线程
        builder.setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor())
        // 设置日志级别
        builder.setLogLevel(Log.VERBOSE)
        // 设置全局选项
        val requestOptions = RequestOptions().format(DecodeFormat.PREFER_RGB_565)
        builder.setDefaultRequestOptions(requestOptions)
    }
}
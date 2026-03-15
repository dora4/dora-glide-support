package dora.glide.transformation

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

/**
 * 圆角矩形头像边框 Transform。
 */
class RoundRectBorderTransform(
    private val radius: Int,
    private val borderWidth: Float,
    private val borderColor: Int
) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(("RoundRectBorderTransform$radius$borderWidth$borderColor").toByteArray())
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {

        val width = toTransform.width
        val height = toTransform.height

        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())

        val shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader

        canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)

        if (borderWidth > 0) {
            val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            borderPaint.style = Paint.Style.STROKE
            borderPaint.color = borderColor
            borderPaint.strokeWidth = borderWidth

            val borderRect = RectF(
                borderWidth / 2,
                borderWidth / 2,
                width - borderWidth / 2,
                height - borderWidth / 2
            )

            canvas.drawRoundRect(borderRect, radius.toFloat(), radius.toFloat(), borderPaint)
        }

        return bitmap
    }
}
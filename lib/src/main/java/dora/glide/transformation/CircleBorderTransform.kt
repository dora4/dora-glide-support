package dora.glide.transformation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import java.security.MessageDigest

/**
 * 圆形头像边框 Transform。
 */
class CircleBorderTransform(
    private val borderWidth: Float,
    private val borderColor: Int
) : CircleCrop() {

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {

        val bitmap = super.transform(pool, toTransform, outWidth, outHeight)

        val canvas = Canvas(bitmap)

        val width = bitmap.width
        val height = bitmap.height

        val radius = (width.coerceAtMost(height) / 2f) - borderWidth / 2f

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
        }

        canvas.drawCircle(width / 2f, height / 2f, radius, paint)

        return bitmap
    }

    override fun equals(other: Any?): Boolean {
        if (other is CircleBorderTransform) {
            return borderWidth == other.borderWidth && borderColor == other.borderColor
        }
        return false
    }

    override fun hashCode(): Int {
        return ("CircleBorderTransform$borderWidth$borderColor").hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(
            ("CircleBorderTransform$borderWidth$borderColor").toByteArray()
        )
    }
}
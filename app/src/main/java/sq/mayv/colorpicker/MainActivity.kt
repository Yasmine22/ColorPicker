package sq.mayv.colorpicker

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val photoImageView = findViewById<ImageView>(R.id.photoImageView)
        val colorPickerIcon = findViewById<ImageView>(R.id.colorPickerIcon)
        val selectedColor = findViewById<ImageView>(R.id.IVSelectedColor)

        photoImageView.setOnTouchListener { _, event ->
            val x = event.x.toInt()
            val y = event.y.toInt()

            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    colorPickerIcon.visibility = View.VISIBLE
                    colorPickerIcon.x = x - colorPickerIcon.width / 2f
                    colorPickerIcon.y = y - colorPickerIcon.height / 2f

                    val color = getColorFromBitmap(photoImageView, x, y)

                    selectedColor.setBackgroundColor(color)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    colorPickerIcon.visibility = View.GONE
                }
            }

            true
        }


    }

    private fun getColorFromBitmap(imageView: ImageView, x: Int, y: Int): Int {
        val drawable = imageView.drawable as? BitmapDrawable ?: return Color.TRANSPARENT
        val bitmap = drawable.bitmap

        val imageViewWidth = imageView.width.toFloat()
        val imageViewHeight = imageView.height.toFloat()
        val bitmapWidth = bitmap.width
        val bitmapHeight = bitmap.height

        val scaledX = (x * bitmapWidth / imageViewWidth).toInt()
        val scaledY = (y * bitmapHeight / imageViewHeight).toInt()

        if (scaledX in 0..<bitmapWidth && scaledY >= 0 && scaledY < bitmapHeight) {
            return bitmap.getPixel(scaledX, scaledY)
        }

        return Color.TRANSPARENT
    }

}
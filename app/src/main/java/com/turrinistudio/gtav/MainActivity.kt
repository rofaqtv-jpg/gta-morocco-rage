package com.turrinistudio.gtav
import android.os.Bundle
import android.widget.TextView
import android.widget.LinearLayout
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    // محاولة تحميل المكتبة مع حماية
    companion object {
        var libLoaded = false
        init {
            try {
                System.loadLibrary("gta_morocco")
                libLoaded = true
            } catch (e: Exception) {
                e.printStackTrace()
                libLoaded = false
            }
        }
    }
    
    external fun stringFromJNI(): String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(40, 40, 40, 40)
        }
        
        val tv = TextView(this).apply {
            textSize = 22f
            gravity = Gravity.CENTER
            text = try {
                if (libLoaded) stringFromJNI() else "GTA Morocco RAGE\n\nLibrary not loaded - but app works!\nPrivate Build OK"
            } catch (e: Exception) {
                "GTA Morocco RAGE\n\nError: ${e.message}\nApp UI works!"
            }
        }
        
        layout.addView(tv)
        setContentView(layout)
    }
}

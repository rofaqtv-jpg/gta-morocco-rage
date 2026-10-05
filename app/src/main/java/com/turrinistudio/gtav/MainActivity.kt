package com.turrinistudio.gtav
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    external fun stringFromJNI(): String
    companion object { init { System.loadLibrary("gta_morocco") } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tv = TextView(this)
        tv.text = stringFromJNI()
        tv.textSize = 24f
        setContentView(tv)
    }
}

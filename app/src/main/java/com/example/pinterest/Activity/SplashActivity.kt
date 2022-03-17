package com.example.pinterest.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.pinterest.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.navigationBarColor = resources.getColor(R.color.splash)

        initViews()
    }

    fun initViews() {
        countDownTimer()
    }

    fun countDownTimer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                callSignInActivity()
            }
        }.start()
    }

    fun callSignInActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
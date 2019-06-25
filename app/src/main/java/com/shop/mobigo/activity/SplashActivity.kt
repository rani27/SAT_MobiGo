package com.shop.mobigo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.shop.mobigo.R
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startAnimation();
    }

    private fun startAnimation() {
        val fadeOutAnimation: Animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_out)

        fadeOutAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity, CategoryActivity::class.java))
                this@SplashActivity.finish()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        imgSplash.startAnimation(fadeOutAnimation)
    }

}

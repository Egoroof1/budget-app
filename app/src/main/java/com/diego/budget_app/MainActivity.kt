package com.diego.budget_app

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.diego.budget_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isUpDown: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.katIncome, UserFragment())
                .commit()
        }

        animHeader(binding.ivBtnDown, binding.katIncome)


    }

    private fun animHeader(btnUpDown: ImageView, header: FrameLayout){

//        val height = header.layoutParams.height
//        val x = height
        btnUpDown.setOnClickListener {

            if (!isUpDown) {
                val startHeight = header.height
                val params = header.layoutParams

                val animator = ValueAnimator.ofInt(startHeight, 0).apply {
                    duration = 300
                    addUpdateListener {
                        params.height = it.animatedValue as Int
                        header.layoutParams = params
                    }
                    doOnEnd {
                        findViewById<TextView>(R.id.textView).text = "Нажал"
                        header.visibility = View.GONE
                        btnUpDown.rotation = 0f
                    }
                }
                isUpDown = true
                animator.start()
            } else {
                val startHeight = header.height
                val params = header.layoutParams
                header.visibility = View.VISIBLE
                val animator1 = ValueAnimator.ofInt(startHeight, findViewById<FrameLayout>(R.id.katInomeFragment).height).apply {
                    duration = 300
                    addUpdateListener {
                        params.height = it.animatedValue as Int
                        header.layoutParams = params
                    }
                    doOnEnd {
                        findViewById<TextView>(R.id.textView).text = "Ещё нажал"
                        btnUpDown.rotation = 180f
                    }
                }
                isUpDown = false
                animator1.start()
            }
        }
    }

}
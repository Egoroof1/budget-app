package com.diego.budget_app

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {
    private var isUpDown: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnUpDown: ImageView = findViewById(R.id.iv_btnDown)
        val header: FrameLayout = findViewById(R.id.header)


        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.header, UserFragment())
                .commit()
        }

        animHeader(btnUpDown, header)

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
                val animator1 = ValueAnimator.ofInt(startHeight, findViewById<FrameLayout>(R.id.headerFragment).height).apply {
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

//    private fun animHeader(btnUpDown: ImageView, header: FrameLayout) {
//        var originalHeight = 0
//
//        // Получаем реальную высоту до скрытия
//        if (header.visibility != View.GONE) {
//            originalHeight = header.height
//        } else {
//            // Альтернативный способ получить высоту для GONE-элементов
//            header.measure(
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//            )
//            originalHeight = header.measuredHeight
//        }
//
//        btnUpDown.setOnClickListener {
//            if (!isUpDown) {
//                // Анимация скрытия
//                ValueAnimator.ofInt(originalHeight, 0).apply {
//                    duration = 3000
//                    addUpdateListener { anim ->
//                        header.layoutParams.height = anim.animatedValue as Int
//                        header.requestLayout()
//                    }
//                    doOnEnd {
//                        header.visibility = View.GONE
//                        btnUpDown.rotation = 0f
//                        findViewById<TextView>(R.id.textView).text = "Нажал"
//                    }
//                    start()
//                }
//                isUpDown = true
//            } else {
//                // Анимация появления
//                header.visibility = View.VISIBLE
//                ValueAnimator.ofInt(0, originalHeight).apply {
//                    duration = 3000
//                    addUpdateListener { anim ->
//                        header.layoutParams.height = anim.animatedValue as Int
//                        header.requestLayout()
//                    }
//                    doOnEnd {
//                        btnUpDown.rotation = 180f
//                        findViewById<TextView>(R.id.textView).text = "Ещё нажал"
//                    }
//                    start()
//                }
//                isUpDown = false
//            }
//        }
//    }
}
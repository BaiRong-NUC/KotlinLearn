// 包名与 src/main/java 下的路径一致；绕过 Kotlin 插件对嵌套源码目录的路径误报。
@file:Suppress("PackageDirectoryMismatch")

package com.example.kotlinlearn.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinlearn.R

class TestActivity : AppCompatActivity(R.layout.activity_test) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 页面创建后，通过 XML 中的 id 找到文字和按钮。
        val messageText = findViewById<TextView>(R.id.message_text)
        val changeButton = findViewById<Button>(R.id.change_button)

        // 花括号里的代码，会在点击按钮时执行。
        changeButton.setOnClickListener {
            messageText.setText(R.string.clicked_message)
        }
    }
}

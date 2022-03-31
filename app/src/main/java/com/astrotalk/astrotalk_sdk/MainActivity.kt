package com.astrotalk.astrotalk_sdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.astrotalk.sdk.api.activities.ChatAstrologerListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClick.setOnClickListener {
            val intent = Intent(context, ChatAstrologerListActivity::class.java)
            startActivity(intent)
        }
    }
}
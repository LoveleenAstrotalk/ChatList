package com.astrotalk.astrotalk_sdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.astrotalk.sdk.api.activities.AstroChatAstrologerListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClick.setOnClickListener {
            val intent = Intent(context, AstroChatAstrologerListActivity::class.java)
            intent.putExtra("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJXaW56b0dhbWVQYXJ0ZXJzQXV0aENsYWltIjoie1wiYWNjZXNzVG9rZW5cIjpudWxsLFwidXNlcklkXCI6XCIzODkwNkFFNS0wRDkyLTQyNTMtQkE3Qi00MkY2RTI4QjI5Q0ZcIixcInJlZGlyZWN0VVJMXCI6bnVsbCxcInNlc3Npb25JZFwiOm51bGwsXCJnYW1lUGFydG5lcklkXCI6XCJcIixcImdhbWVQYXJ0bmVyVG9rZW5cIjpcImJvczVWSmRoUXRxZEFkUXRhZE1FK3pVL0FVL2pBRnhyLzlwSjBHUTVWU1BTSlJLYWRwdGU5WllHZEc5ZjNPeFNoUG5oR2hMaWloa2lmOXRkcHdNSTJvUEFaaWh4NjVabHRNQ2VacEluRlZZckNMWGtlOGZvS3VyYmRUWUtNU05HV2hVYWtnPT1cIn0iLCJuYmYiOjE2NTEwNTkxODgsImV4cCI6MTczNzQ1OTE4OCwiaWF0IjoxNjUxMDU5MTg4LCJpc3MiOiJ3aW56by5jb20iLCJhdWQiOiJ3aW56byBnYW1lIHBhcnRuZXJzIn0.pP7O2NyLFgcWPUAV8nOU6BdPNdPwq7xkOtTSIoaF7sg")
            intent.putExtra("client_id", "winzo")
            intent.putExtra("client_secret", "F6bCtBdjeQzP3ZZF")
            intent.putExtra("production", false)
            startActivity(intent)
        }

        btnClickLive.setOnClickListener {
            val intent = Intent(context, AstroChatAstrologerListActivity::class.java)
            intent.putExtra("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJXaW56b0dhbWVQYXJ0ZXJzQXV0aENsYWltIjoie1wiYWNjZXNzVG9rZW5cIjpudWxsLFwidXNlcklkXCI6XCIzODkwNkFFNS0wRDkyLTQyNTMtQkE3Qi00MkY2RTI4QjI5Q0ZcIixcInJlZGlyZWN0VVJMXCI6bnVsbCxcInNlc3Npb25JZFwiOm51bGwsXCJnYW1lUGFydG5lcklkXCI6XCJcIixcImdhbWVQYXJ0bmVyVG9rZW5cIjpcImJvczVWSmRoUXRxZEFkUXRhZE1FK3pVL0FVL2pBRnhyLzlwSjBHUTVWU1BTSlJLYWRwdGU5WllHZEc5ZjNPeFNoUG5oR2hMaWloa2lmOXRkcHdNSTJvUEFaaWh4NjVabHRNQ2VacEluRlZZckNMWGtlOGZvS3VyYmRUWUtNU05HV2hVYWtnPT1cIn0iLCJuYmYiOjE2NTEwNTkxODgsImV4cCI6MTczNzQ1OTE4OCwiaWF0IjoxNjUxMDU5MTg4LCJpc3MiOiJ3aW56by5jb20iLCJhdWQiOiJ3aW56byBnYW1lIHBhcnRuZXJzIn0.pP7O2NyLFgcWPUAV8nOU6BdPNdPwq7xkOtTSIoaF7sg")
            intent.putExtra("client_id", "winzo_sdk")
            intent.putExtra("client_secret", "YrBFXe9UxBudXLb2")
            intent.putExtra("production", true)
            startActivity(intent)
        }
    }
}
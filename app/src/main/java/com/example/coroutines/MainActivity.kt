package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var tvadvi: TextView
    private lateinit var btnadvi: Button

    val adviceUrl = "https://api.adviceslip.com/advice"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvadvi = findViewById(R.id.tvadvi)
        btnadvi = findViewById(R.id.btngetadvi)

        btnadvi.setOnClickListener() {

            CoroutineScope(Dispatchers.IO).launch {

                val data = async {

                    getadvi()

                }.await()

                if (data.isNotEmpty()) {

                    anthoradvi(data)
                }

            }
        }


    }

    private fun getadvi(): String {

        var response = ""
        try {
            response = URL(adviceUrl).readText(Charsets.UTF_8)

        } catch (e: Exception) {
            println("Error $e")

        }
        return response

    }

    private suspend fun anthoradvi(data: String) {
        withContext(Dispatchers.Main)
        {

            val jsonObject = JSONObject(data)
            val slip = jsonObject.getJSONObject("slip")
            val id = slip.getInt("id")
            val advice = slip.getString("advice")

            tvadvi.text = advice

        }

    }

}
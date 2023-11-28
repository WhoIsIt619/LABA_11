package com.example.laba_11

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity(){
    private var user_field: EditText? = null
    private var main_button: Button? = null
    private var result_info: TextView? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_field = findViewById(R.id.user_field)
        main_button = findViewById(R.id.main_button)
        result_info = findViewById(R.id.result_info)

        main_button?.setOnClickListener{
            if(user_field?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Введите город", Toast.LENGTH_LONG).show()
            else{
                val city: String = user_field?.text.toString()
                val key: String = "0a483d041fac9fa6cd89b6cd75954be1"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric&lang=ru"

                GlobalScope.async(Dispatchers.Default) {
                    val apiResponse = URL(url).readText()

                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val desc = weather.getJSONObject(0).getString("description")

                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temp = main.getString("temp")

                    result_info?.text = "Температура: $temp\n$desc"

                }
            }
        }
    }
}
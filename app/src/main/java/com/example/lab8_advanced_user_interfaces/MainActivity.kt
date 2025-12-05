package com.example.lab8_advanced_user_interfaces

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cityEditText: EditText
    private lateinit var predictButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityEditText = findViewById(R.id.city_edittext)
        predictButton = findViewById(R.id.predict_button)

        cityEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    predictButton.visibility = View.GONE
                } else {
                    predictButton.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }
        })

        predictButton.setOnClickListener {
            val city = cityEditText.text.toString()
            val intent = Intent(this, PredictionActivity::class.java).apply {
                putExtra("EXTRA_LOCATION", city)
            }
            startActivity(intent)
        }
    }
}

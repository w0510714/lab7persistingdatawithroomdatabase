package com.example.lab7_persisting_data_with_room_database

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var predictButton: Button
    private lateinit var enterNameEditText: EditText

    private val predictionActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enterNameEditText = findViewById(R.id.activity_main_enter_name)
        predictButton = findViewById(R.id.activity_main_predict_button)

        predictButton.setOnClickListener {
            val name = enterNameEditText.text.toString()
            val intent = PredictionActivity.newIntent(this, name)
            predictionActivityLauncher.launch(intent)
        }
    }
}

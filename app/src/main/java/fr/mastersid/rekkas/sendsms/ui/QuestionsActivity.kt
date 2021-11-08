package fr.mastersid.rekkas.sendsms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import fr.mastersid.rekkas.sendsms.databinding.QuestionsMainBinding

@AndroidEntryPoint
class QuestionsActivity : AppCompatActivity() {
    private lateinit var _binding: QuestionsMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = QuestionsMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }
}
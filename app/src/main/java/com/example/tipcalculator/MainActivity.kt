package com.example.tipcalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.tipcalculator.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.calculate.setOnClickListener{view->
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            if(binding.costOfServiceEditText.text.toString()=="")
            {
                binding.TipAmount.text=""
                Toast.makeText(this, "Please enter cost of service", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val total:Double=binding.costOfServiceEditText.text.toString().toDouble()
            val selectedOption=binding.tipOptions.checkedRadioButtonId
            val percent:Double=when(selectedOption)
            {
                binding.fifteenPercent.id->0.15
                binding.eighteenPercent.id->0.18
                else->0.20
            }
            val tip:Double=if(binding.roundUp.isChecked) ceil(percent*total) else percent*total
            binding.TipAmount.text=String.format("%.2f",tip)
        }
        binding.calculate.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}
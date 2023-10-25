package com.example.simplecalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mysimplecalculator.R

class MainActivity : AppCompatActivity() {
    private lateinit var solutionTV: TextView
    private var currentInput: StringBuilder = StringBuilder()
    private var currentOperator: String = ""
    private var currentOperand: Int = 0
    private var isPositive: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        solutionTV = findViewById(R.id.solution_tv)

        val numberButtons = arrayOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9
        )

        val ceButton = findViewById<Button>(R.id.button_ce)
        val cButton = findViewById<Button>(R.id.button_c)
        val bsButton = findViewById<Button>(R.id.button_bs)
        val divideButton = findViewById<Button>(R.id.button_divide)
        val multiplyButton = findViewById<Button>(R.id.button_multiply)
        val minusButton = findViewById<Button>(R.id.button_minus)
        val plusButton = findViewById<Button>(R.id.button_plus)
        val equalsButton = findViewById<Button>(R.id.button_equals)
        val negativeButton = findViewById<Button>(R.id.button_negative)

        ceButton.setOnClickListener {
            clearEntry()
        }

        cButton.setOnClickListener {
            clear()
        }

        bsButton.setOnClickListener {
            backspace()
        }

        divideButton.setOnClickListener {
            operatorClicked("/")
        }

        multiplyButton.setOnClickListener {
            operatorClicked("*")
        }

        minusButton.setOnClickListener {
            operatorClicked("-")
        }

        plusButton.setOnClickListener {
            operatorClicked("+")
        }

        equalsButton.setOnClickListener {
            calculate()
        }
        negativeButton.setOnClickListener {
            toggleSign()
        }
        for (numberButton in numberButtons) {
            findViewById<Button>(numberButton).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }
    }

    private fun toggleSign() {
        if (currentInput.toString() != "0") {
            isPositive = !isPositive
            if (!isPositive) {
                if (currentInput[0] != '-') {
                    currentInput.insert(0, '-')
                }
            }else {
                if (currentInput[0] == '-') {
                    currentInput.deleteCharAt(0)
                }
            }
            updateSolutionTextView()
        }
    }

    private fun clearEntry() {
        currentInput = StringBuilder("0")
        updateSolutionTextView()
    }

    private fun clear() {
        currentInput = StringBuilder("0")
        currentOperator = ""
        currentOperand = 0
        updateSolutionTextView()
    }

    private fun backspace() {
        if (currentInput.length > 1) {
            currentInput.deleteCharAt(currentInput.length - 1)
        } else {
            currentInput = StringBuilder("0")
        }
        updateSolutionTextView()
    }

    private fun numberClicked(number: String) {
        if (currentInput.toString() == "0" || currentInput.toString() == "Error") {
            currentInput = StringBuilder(number)
        } else {
            currentInput.append(number)
        }
        updateSolutionTextView()
    }

    private fun operatorClicked(operator: String) {
        if (currentOperator.isEmpty()) {
            currentOperator = operator
            currentOperand = currentInput.toString().toInt()
            currentInput = StringBuilder("0")
        } else {
            calculate()
            currentOperator = operator
        }
    }

    private fun calculate() {
        if (currentOperator.isNotEmpty()) {
            val secondOperand = currentInput.toString().toInt()
            when (currentOperator) {
                "+" -> currentOperand += secondOperand
                "-" -> currentOperand -= secondOperand
                "*" -> currentOperand *= secondOperand
                "/" -> {
                    if (secondOperand != 0) {
                        currentOperand /= secondOperand
                    } else {
                        currentInput = StringBuilder("Error")
                        updateSolutionTextView()
                        return
                    }
                }
            }
            currentInput = StringBuilder(currentOperand.toString())
            currentOperator = ""
            updateSolutionTextView()
        }
    }

    private fun updateSolutionTextView() {
        solutionTV.text = currentInput.toString()
    }
}
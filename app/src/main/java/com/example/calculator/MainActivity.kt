package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT

class MainActivity : AppCompatActivity() {
    private var text: TextView? = null
    public var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.text)
    }

    fun Digit(view: View) {
        // when press any button then one will appear on screen
        text?.append((view as Button).text)
        lastNumeric = true

    }

    fun Clear(view: View) {
        text?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun Dot(view: View) {
        if (lastNumeric && !lastDot) {
            text?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
  fun back(view: View)
  {
      var length=text?.length()
      if(length!! >0) {
          if(text!!.text[length-1]=='+' || text!!.text[length-1]=='-' || text!!.text[length-1]=='X' || text!!.text[length-1]=='/')
          {lastNumeric=true; }
          text?.text = text?.text?.subSequence(0, length - 1)

      }
    }

    fun onOperator(view: View) {

        text?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                text?.append((view as Button).text)
                lastNumeric = false // Update the flag
                lastDot = false    // Reset the DOT flag
            }
        }
    }
    private fun isOperatorAdded(value: String): Boolean {

        /**
         * Here first we will check that if the value starts with "-" then will ignore it.
         * As it is the result value and perform further calculation.
         */

        if (value.startsWith("-")) {

           return   false
        } else {
           return  (value.contains("/")
                    || value.contains("X")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }
    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }
    fun onEqual(view: View) {
        // If the last input is a number only, solution can be found.
        if (lastNumeric) {
            // Read the textView value
            var tvValue = text?.text.toString()
            var prefix = ""
            try {

                // Here if the value starts with '-' then we will separate it and perform the calculation with value.
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1);
                }

                // If the inputValue contains the Division operator
                when {
                    tvValue.contains("/") -> {
                        // Will split the inputValue using Division operator
                        val splitedValue = tvValue.split("/")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /*Here as the value one and two will be calculated based on the operator and
                                if the result contains the zero after decimal point will remove it.
                                And display the result to TextView*/
                        text?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                    tvValue.contains("X") -> {
                        // If the inputValue contains the Multiplication operator
                        // Will split the inputValue using Multiplication operator
                        val splitedValue = tvValue.split("X")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /** Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        text?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValue.contains("-") -> {

                        // If the inputValue contains the Subtraction operator
                        // Will split the inputValue using Subtraction operator
                        val splitedValue = tvValue.split("-")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /** Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        text?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        // If the inputValue contains the Addition operator
                        // Will split the inputValue using Addition operator
                        val splitedValue = tvValue.split("+")

                        var one = splitedValue[0] // Value One
                        val two = splitedValue[1] // Value Two

                        if (prefix.isNotEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                            one = prefix + one
                        }

                        /**Here as the value one and two will be calculated based on the operator and
                        if the result contains the zero after decimal point will remove it.
                        And display the result to TextView
                         */
                        text?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

}
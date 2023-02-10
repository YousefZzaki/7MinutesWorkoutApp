package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkoutapp.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode


class BMIActivity : AppCompatActivity() {

    private var binding: ActivityBmiactivityBinding? = null

    private companion object{
        const val METRIC_UNITS_VIEW = "METRIC_UNITS_VIEW"
        const val US_UNITS_VIEW = "US_UNITS_VIEW"

    }

    private lateinit var currentVisibleView: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbBMi)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Calculate BMI"

        binding?.tbBMi?.setNavigationOnClickListener {
            onBackPressed()
        }

        //Radio group on check listener
        setRadioGroup()

        binding?.btnCalc?.setOnClickListener {

            if (binding?.rgUnits?.checkedRadioButtonId != R.id.rbUSUnit) {
                makeMetricUnitsVisible()
                Log.e("TAG", "metric checked")
                calcUnits()
            } else {
                makeUsUnitsVisible()
                Log.e("TAG", "us checked")
                calcUnits()
            }
        }
    }

    private fun setRadioGroup(){
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbMetricUnit) {
                makeMetricUnitsVisible()
                Log.e("TAG", "metric checked")
            }
            else {
                makeUsUnitsVisible()
                Log.e("TAG", "us checked")
            }
        }
    }

    private fun makeUsUnitsVisible(){
        currentVisibleView = US_UNITS_VIEW

        binding?.tiWeight?.visibility = View.INVISIBLE
        binding?.tiHeight?.visibility = View.INVISIBLE


        binding?.tiUSWeight?.visibility = View.VISIBLE
        binding?.llUSHeight?.visibility = View.VISIBLE
        binding?.tiUSHeightFeet?.visibility = View.VISIBLE
        binding?.tiUSWeight?.visibility = View.VISIBLE

        binding?.etWeight?.text!!.clear()
        binding?.etHeight?.text!!.clear()

        binding?.llMetric?.visibility = View.INVISIBLE

    }

    private fun makeMetricUnitsVisible(){

        currentVisibleView = METRIC_UNITS_VIEW

        Log.e("TAG", "Make metric views visible")
        binding?.tiWeight?.visibility = View.VISIBLE
        binding?.tiHeight?.visibility = View.VISIBLE


        binding?.llUSHeight?.visibility = View.INVISIBLE
        binding?.tiUSHeightFeet?.visibility = View.INVISIBLE
        binding?.tiUSWeight?.visibility = View.INVISIBLE

        binding?.etUSFeet?.text!!.clear()
        binding?.etUSWeight?.text!!.clear()
        binding?.etUSInches?.text!!.clear()

        binding?.llMetric?.visibility = View.INVISIBLE

    }

    private fun calcUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
            Log.e("TAG", "Calc metric")
            if (validateData()) {
                Log.e("TAG", "Calc metric")
                val heightVal = binding?.etHeight?.text.toString().toFloat() / 100
                val weightVal = binding?.etWeight?.text.toString().toFloat()

                val bmi = weightVal / (heightVal * heightVal)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(this@BMIActivity, "Please, enter valid values.", Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            Log.e("TAG", "Calc us")
            if (validateData()) {

                val usHeightValFeet =
                    binding?.etUSInches?.text.toString()

                val usHeightValInch =
                    binding?.etUSInches?.text.toString()

                val usWeightVal: Float = binding?.etUSWeight?.text.toString().toFloat()

                val heightVal =
                    usHeightValFeet.toFloat() + usHeightValInch.toFloat() * 12

                val bmi = 703 * (usWeightVal / (heightVal * heightVal))
                displayBMIResult(bmi)
            } else {
                Toast.makeText(
                    this@BMIActivity,
                    "Please, enter valid values.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun displayBMIResult(bmi: Float){
        binding?.llMetric?.visibility = View.VISIBLE

        Log.e("TAG", "BMI Res")
        val bmiLabel: String
        val bmiDescription: String

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding?.llMetric?.visibility = View.VISIBLE
        binding?.tvBMIType?.visibility = View.VISIBLE
        binding?.tvYourBMI?.visibility = View.VISIBLE
        binding?.tvBMIValue?.visibility = View.VISIBLE
        binding?.tvBMIDescription?.visibility = View.VISIBLE
        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }
     private fun validateData(): Boolean{

        var isValid = true
         Log.e("TAG", "Not valid")
         if (currentVisibleView == METRIC_UNITS_VIEW){
             if (binding?.etWeight?.text.toString().isEmpty())
                 isValid = false
             else if (binding?.etHeight?.text.toString().isEmpty())
                 isValid = false
         }else{
              if (binding?.etUSWeight?.text.toString().isEmpty())
                 isValid = false
             else if (binding?.etUSFeet?.text.toString().isEmpty())
                 isValid = false
             else if (binding?.etUSInches?.text.toString().isEmpty())
                 isValid = false

         }
        return isValid
    }

}
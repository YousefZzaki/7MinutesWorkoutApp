package com.example.a7minutesworkoutapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import com.example.a7minutesworkoutapp.databinding.CustomDialogConfirmationBinding
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentPosition = -1

    private var tts: TextToSpeech? = null

    private var soundPlayer: MediaPlayer? = null

    private var exerciseAdepter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.exerciseToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.exerciseToolbar?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        exerciseList = Constants.defaultExerciseList()

        //Initialing TextToSpeech object
        tts = TextToSpeech(this, this)

        setUpExerciseRecyclerView()

        setView()
    }

    private fun setUpExerciseRecyclerView() {

        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        exerciseAdepter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdepter 

    }

    private fun setView(){
        setupRestView()
    }

    private fun setupRestView(){

        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvNextExercise?.visibility = View.VISIBLE
        binding?.tvUpcoming?.visibility = View.VISIBLE

        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.exerciseImage?.visibility = View.INVISIBLE

        if (restTimer != null) {
            restProgress = 0
            restTimer?.cancel()
        }

        //get next exercise name
        binding?.tvNextExercise?.text = exerciseList!![++currentPosition].getName()

        //Make the sound run on rest beginning
        try {
            val soundUri = Uri.parse("android.resource://com.example.a7minutesworkoutapp/" + R.raw.press_start)
            soundPlayer = MediaPlayer.create(applicationContext, soundUri)
            soundPlayer?.isLooping = false
            soundPlayer?.start()
        }
        catch (e: Exception){
            e.printStackTrace()
        }

        setRestProgressBar()
    }

    private fun setRestProgressBar(){

        binding?.progressBar?.max = 10

        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(remaiming: Long) {
                Log.e("TAG", remaiming.toString())
                binding?.progressBar?.progress = restProgress
                binding?.tvTimer?.text = (restProgress).toString()
                restProgress++
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {

                exerciseList!![currentPosition].setIsSelected(true)
                exerciseList!![currentPosition].setIsCompleted(false)
                exerciseAdepter?.notifyDataSetChanged()

                setUpExerciseView()
            }
        }.start()
    }


    private fun setUpExerciseView(){

        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvNextExercise?.visibility = View.INVISIBLE
        binding?.tvUpcoming?.visibility = View.INVISIBLE

        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.exerciseImage?.visibility = View.VISIBLE


        binding?.exerciseImage?.setImageResource(exerciseList!![currentPosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentPosition].getName()

        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        //Read the text
        val exerciseName = exerciseList!![currentPosition].getName()
        speakOut(exerciseName)

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){

        binding?.exerciseProgressBar?.max  = 30

        exerciseTimer = object : CountDownTimer(30000, 1000){
            override fun onTick(remaiming: Long) {
                Log.e("TAG", remaiming.toString())
                binding?.exerciseProgressBar?.progress =  exerciseProgress
                binding?.tvTimerExercise?.text = (exerciseProgress).toString()
                exerciseProgress++
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {

                exerciseList!![currentPosition].setIsSelected(false)
                exerciseList!![currentPosition].setIsCompleted(true)
                exerciseAdepter?.notifyDataSetChanged()

                if (currentPosition < exerciseList?.size!! - 1 )
                    setupRestView()
                else {
                    lifecycleScope.launch {
                        startActivity(Intent(this@ExerciseActivity, FinishActivity::class.java))
                    }
                }
            }
        }.start()
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = CustomDialogConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            this.finish()
            customDialog.dismiss()
        }

        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun speakOut(text: String) {
        if (text.isNotEmpty()) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        tts?.stop()
        tts?.shutdown()
        tts = null

        binding = null
    }

    override fun onInit(stutes: Int) {
        if (stutes == 0){
            val result = tts?.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED )
                Log.e("TTS", "The language specified not found!")
        }
        else
            Log.e("TTS", "Initialization failed!")
    }
}
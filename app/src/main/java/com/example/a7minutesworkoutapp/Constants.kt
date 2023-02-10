package com.example.a7minutesworkoutapp

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel> {

        val exerciseList = ArrayList<ExerciseModel>()

        //List of exercises
        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks
        )
        exerciseList.add(jumpingJacks)

        val wallSit = ExerciseModel(
            2,
            "Step Up On Chair",
            R.drawable.ic_step_up_onto_chair,
        )
        exerciseList.add(wallSit)

        val pushUp = ExerciseModel(
            3,
            "Wall Sit",
            R.drawable.ic_wall_sit
        )
        exerciseList.add(pushUp)

        val abdominalCrunches = ExerciseModel(
            4,
            "Abdominal Crunches",
            R.drawable.ic_abdominal_crunch
        )
        exerciseList.add(abdominalCrunches)

        val setUpOnChair = ExerciseModel(
            5,
            "High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place
        )
        exerciseList.add(setUpOnChair)

        val highKneesRunning = ExerciseModel(
            6,
            "Push Up And Rotation",
            R.drawable.ic_push_up_and_rotation
        )
        exerciseList.add(highKneesRunning)

        val lung = ExerciseModel(
            7,
            "Push Up",
            R.drawable.ic_push_up
        )
        exerciseList.add(lung)

        val triceps = ExerciseModel(
            8,
            "Triceps On Chair",
            R.drawable.ic_triceps_dip_on_chair
        )
        exerciseList.add(triceps)

        val pushUpRotation = ExerciseModel(
            9,
            "Lunge",
            R.drawable.ic_lunge
        )
        exerciseList.add(pushUpRotation)

        val squat = ExerciseModel(
            10,
            "Squat",
            R.drawable.ic_squat
        )
        exerciseList.add(squat)

        val sidePlank = ExerciseModel(
            11,
            "Side Plank",
            R.drawable.ic_side_plank
        )
        exerciseList.add(sidePlank)

        val plank = ExerciseModel(
            12,
            "Plank",
            R.drawable.ic_plank
        )
        exerciseList.add(plank)

        return exerciseList
    }
}
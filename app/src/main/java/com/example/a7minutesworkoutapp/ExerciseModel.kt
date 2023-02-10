package com.example.a7minutesworkoutapp

class ExerciseModel(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var isSelected: Boolean = false,
    private var isCompleted: Boolean = false,) {

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getName(): String {
        return name
    }

    fun seImage(image: Int) {
        this.image = image
    }

    fun getImage(): Int {
        return image
    }

    fun setIsSelected(status: Boolean) {
        this.isSelected = status
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsCompleted(status: Boolean) {
        this.isCompleted = status
    }

    fun getIsCompleted(): Boolean {
        return isCompleted
    }
}
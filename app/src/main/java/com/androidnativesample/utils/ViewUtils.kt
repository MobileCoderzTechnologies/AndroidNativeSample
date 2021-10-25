package com.androidnativesample.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.setBackGround(radiusValue:Float){
    val gradientDrawable = GradientDrawable(
        GradientDrawable.Orientation.LEFT_RIGHT,
        intArrayOf(
            Color.parseColor("#ffffff"),
            Color.parseColor("#ffffff"))
    )
    gradientDrawable.cornerRadius = 100f
}
fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = Utility.SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}


fun View.showSnackbar(message: Int) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).also { snackbar ->
        snackbar.view.setBackgroundColor(Color.parseColor("#3E6588"))
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.showStringSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
           snackbar.view.setBackgroundColor(Color.parseColor("#3E6588"))
        snackbar.setActionTextColor(Color.WHITE)
        //snackbar.setDuration(5000)
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}
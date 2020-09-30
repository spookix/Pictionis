package com.androidkotlin.pictionis.utils

import android.view.MotionEvent

object Coord {

    var action = MotionEvent.ACTION_CANCEL
    var x = 0f
    var y = 0f

    operator fun invoke(x: Float, y: Float, action: Int): Coord {
        Coord.x = x
        Coord.y = y
        Coord.action = action
        return this
    }

}

object Draw { var coord : MutableList<Coord>? = null }
package com.androidnativesample.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

class MontserratBoldTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Bold.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Bold.ttf")
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Bold.ttf")
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratMediumTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratLightTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Light.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Light.ttf")
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Light.ttf")
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratRegularTextView : AppCompatTextView {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Regular.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Regular.ttf")
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Regular.ttf")
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratBoldEditText : AppCompatEditText {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Bold.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Bold.ttf")
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
            context,
            attrs,
            defStyle
    ) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Bold.ttf")
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}

class MontserratMediumEditText : AppCompatEditText {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}


class MontserratMediumButton : AppCompatButton {

    internal var face: Typeface
    internal var context: Context

    constructor(context: Context) : super(context) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
        this.gravity = Gravity.START

    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
        this.gravity = Gravity.START
        face = Typeface.createFromAsset(context.assets, "fonts/Montserrat-Medium.ttf")
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}
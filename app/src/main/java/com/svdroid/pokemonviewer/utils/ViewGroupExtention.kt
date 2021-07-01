package com.svdroid.pokemonviewer.utils

import android.view.ViewGroup
import android.view.ViewStub
import com.svdroid.pokemonviewer.R

/**
 * Created by SVDroid on 7/1/21.
 */
fun ViewGroup.createLoadingStub() {
    val vs = ViewStub(context)
    addView(vs)
    vs.layoutResource = R.layout.loading_view_stub
    vs.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    vs.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
    vs.inflatedId = R.id.loadingContainer
    vs.inflate()
}

fun ViewGroup.createErrorStub() {
    val vs = ViewStub(context)
    addView(vs)
    vs.layoutResource = R.layout.error_view_stub
    vs.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    vs.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
    vs.inflatedId = R.id.errorContainer
    vs.inflate()
}
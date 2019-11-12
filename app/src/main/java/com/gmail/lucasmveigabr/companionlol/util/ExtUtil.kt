package com.gmail.lucasmveigabr.companionlol.util

import android.view.View
import com.gmail.lucasmveigabr.companionlol.model.SpellSumm

fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun SpellSumm.cooldownForTimer(): Long {
    return if (this.cdUntil <= 0L)
        this.cooldown.toLong() * 1000
    else this.cdUntil - System.currentTimeMillis()
}
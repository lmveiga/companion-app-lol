package com.gmail.lucasmveigabr.companionlol.util

import android.view.View
import com.gmail.lucasmveigabr.companionlol.model.SpellSumm
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*

fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun SpellSumm.cooldownForTimer(): Long {
    return if (this.cdUntil <= 0L)
        this.cooldown.toLong() * 1000
    else this.cdUntil - System.currentTimeMillis()
}

fun <T> List<T>.replace(newItem: T, where: (T) -> Boolean): List<T>{
    return map {
        if (where(it)) newItem else it
    }
}

fun executeAsync(func: () -> Unit){
    Single.create<Unit>{
        func()
    }.subscribeOn(Schedulers.io()).subscribe()
}

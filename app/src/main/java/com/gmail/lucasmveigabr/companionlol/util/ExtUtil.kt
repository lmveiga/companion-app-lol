package com.gmail.lucasmveigabr.companionlol.util

import android.view.View
import com.gmail.lucasmveigabr.companionlol.data.model.SummonerSpell
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun SummonerSpell.cooldownForTimer(): Long {
    return if (this.cdUntil <= 0L)
        this.cooldown.toLong() * 1000
    else this.cdUntil - System.currentTimeMillis()
}

fun <T> List<T>.replace(newItem: T, where: (T) -> Boolean): List<T> {
    return map {
        if (where(it)) newItem else it
    }
}

fun executeAsync(func: () -> Unit) {
    Single.create<Unit> {
        func()
    }.subscribeOn(Schedulers.io()).subscribe()
}

fun View.showLongSnackBar(resID: Int){
    Snackbar.make(this, resID, Snackbar.LENGTH_LONG).show()
}
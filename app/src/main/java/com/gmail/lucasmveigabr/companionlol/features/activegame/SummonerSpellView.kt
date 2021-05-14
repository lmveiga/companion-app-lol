package com.gmail.lucasmveigabr.companionlol.features.activegame

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.data.model.SummonerSpell
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import com.gmail.lucasmveigabr.companionlol.util.cooldownForTimer
import com.gmail.lucasmveigabr.companionlol.util.setVisible


class SummonerSpellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes), View.OnClickListener {

    private var cooldownTimer: CountDownTimer? = null
    private var spell: SummonerSpell? = null
    private var isRunning = false

    init {
        LayoutInflater.from(context).inflate(R.layout.view_summ_spell, this, true)
        setOnClickListener(this)
    }


    fun setSpell(spell: SummonerSpell) {
        this.spell = spell
        Glide.with(context)
            .load(Endpoints.spellIcon(spell.icon))
            .into(findViewById(R.id.spell_image))
        if (spell.cdUntil > 0L) {
            setupTimer()
        }
    }


    private fun setupTimer() {
        spell?.let { spell ->
            isRunning = true
            cooldownTimer = object : CountDownTimer(
                spell.cooldownForTimer(),
                1000
            ) {
                override fun onFinish() {
                    findViewById<TextView>(R.id.spell_cd).text = ""
                    findViewById<View>(R.id.spell_darkoverlay).setVisible(false)
                    spell.cdUntil = 0
                    isRunning = false
                }

                override fun onTick(millisUntilFinished: Long) {
                    findViewById<View>(R.id.spell_darkoverlay).setVisible(true)
                    findViewById<TextView>(R.id.spell_cd).text = (millisUntilFinished / 1000).toString()
                    spell.cdUntil = System.currentTimeMillis() + millisUntilFinished
                }
            }
            cooldownTimer?.start()
        }
    }

    override fun onClick(v: View?) {
        if (cooldownTimer == null || !isRunning) {
            setupTimer()
        }
    }

}
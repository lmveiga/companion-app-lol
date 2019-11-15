package com.gmail.lucasmveigabr.companionlol.model

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.gmail.lucasmveigabr.companionlol.R
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import com.gmail.lucasmveigabr.companionlol.util.cooldownForTimer
import com.gmail.lucasmveigabr.companionlol.util.setVisible
import kotlinx.android.synthetic.main.view_summ_spell.view.*


class SummSpellView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes), View.OnClickListener {

    private var cooldownTimer: CountDownTimer? = null
    private var spell: SpellSumm? = null
    private var isRunning = false

    init {
        LayoutInflater.from(context).inflate(R.layout.view_summ_spell, this, true)
        setOnClickListener(this)
    }


    fun setSpell(spell: SpellSumm) {
        this.spell = spell
        Glide.with(context)
            .load(Endpoints.spellIcon(spell.icon))
            .into(spell_image)
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
                    spell_cd.text = ""
                    spell_darkoverlay.setVisible(false)
                    spell.cdUntil = 0
                    isRunning = false
                }

                override fun onTick(millisUntilFinished: Long) {
                    spell_darkoverlay.setVisible(true)
                    spell_cd.text = (millisUntilFinished / 1000).toString()
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
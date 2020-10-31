package com.caster.notes.dsl.features.add.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.caster.notes.dsl.R
import com.caster.notes.dsl.common.BaseActivity
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.features.add.di.NoteAddInjector
import com.caster.notes.dsl.features.add.domain.NoteAddState
import com.caster.notes.dsl.features.add.domain.NoteAddViewModel
import kotlinx.android.synthetic.main.activity_add_note.*
import javax.inject.Inject

class NoteAddActivity : BaseActivity<NoteAddViewModel, NoteAddState>() {

    override val clazz: Class<NoteAddViewModel> = NoteAddViewModel::class.java
    private val screen by lazy { NoteAddScreenImpl() }

    companion object {
        fun create(context: Context): Intent {
            return Intent(context, NoteAddActivity::class.java)
        }
    }

    @Inject
    lateinit var view: NoteAddView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        NoteAddInjector.of(this, contentView)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        initListeners()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initListeners() {
        vm.stateLiveData.observe(
            this,
            Observer {
                it?.let {
                    _state.onNext(it)
                }
            })
        screen.bind(view, state.share())
            .addTo(compositeBag)
        screen.withSaveButtonClick(view)
        screen.event.observe(
            this,
            Observer { event ->
                when (event) {
                    is SubmitClicked -> {
                        vm.insertNote(event.title, event.content)
                    }
                    is AddNoteSuccess -> {
                        finish()
                    }
                }
            }
        )
    }
}
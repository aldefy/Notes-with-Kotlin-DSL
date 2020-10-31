package com.caster.notes.dsl.features.details.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.caster.notes.dsl.R
import com.caster.notes.dsl.common.BaseActivity
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.features.details.di.NoteDetailsInjector
import com.caster.notes.dsl.features.details.domain.NoteDetailsState
import com.caster.notes.dsl.features.details.domain.NoteDetailsViewModel
import com.caster.notes.dsl.model.Note
import kotlinx.android.synthetic.main.activity_add_note.*
import javax.inject.Inject

private const val EXTRA_NOTE = "extras_note"

class NoteAddActivity : BaseActivity<NoteDetailsViewModel, NoteDetailsState>() {

    override val clazz: Class<NoteDetailsViewModel> = NoteDetailsViewModel::class.java
    private val screen by lazy { NoteDetailsScreenImpl() }

    companion object {
        fun create(context: Context, note: Note? = null): Intent {
            return Intent(context, NoteAddActivity::class.java)
                .also { intent ->
                    note?.let {
                        intent.putExtra(EXTRA_NOTE, it)
                    }
                }
        }
    }

    @Inject
    lateinit var view: NoteDetailsView
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        NoteDetailsInjector.of(this, contentView)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        initListeners()
        processIntent(intent)
    }

    private fun processIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_NOTE)) {
            note = intent.getParcelableExtra(EXTRA_NOTE)
        }
        note?.let {
            screen.withNote(it, view)
        }
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
        screen.withInputActionDone(view)
        screen.withSaveButtonClick(view)
        screen.event.observe(
            this,
            Observer { event ->
                when (event) {
                    is SubmitClicked -> {
                        vm.saveNote(generateNote(event))
                    }
                    is AddNoteSuccess -> {
                        finish()
                    }
                }
            }
        )
    }

    private fun generateNote(event: SubmitClicked) : Note {
        return if(note == null){
            Note(
                title = event.title,
                content = event.content,
                createdAt = System.currentTimeMillis()
            )
        } else {
            note?.apply {
                title = event.title
                content = event.content
                updatedAt = System.currentTimeMillis()
            }!!
        }
    }
}
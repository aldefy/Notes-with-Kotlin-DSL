package com.caster.notes.dsl.features.details.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.caster.notes.dsl.R
import com.caster.notes.dsl.common.BaseActivity
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.features.details.di.NoteDetailsInjector
import com.caster.notes.dsl.features.details.domain.NoteDetailsState
import com.caster.notes.dsl.features.details.domain.NoteDetailsViewModel
import com.caster.notes.dsl.model.Note
import kotlinx.android.synthetic.main.activity_details_note.*
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
        setContentView(R.layout.activity_details_note)
        NoteDetailsInjector.of(this, contentView)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        initListeners()
        processIntent(intent)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val deleteMenuItem = menu.findItem(R.id.action_delete)
        deleteMenuItem.isVisible = note != null
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_delete -> {
                vm.deleteNote(note!!)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun processIntent(intent: Intent) {
        if (intent.hasExtra(EXTRA_NOTE)) {
            note = intent.getParcelableExtra(EXTRA_NOTE)
        }
        note?.let {
            screen.withNote(it, view)
        }
        invalidateOptionsMenu()
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
                    is NoteDeletedEvent->{
                        finish()
                    }
                    is NoteAddFailedEvent -> {
                        AlertDialog.Builder(this)
                            .setTitle(resources.getString(R.string.error_saving_note))
                            .setMessage(event.throwable.message ?: "Something went wrong !!")
                            .setCancelable(false)
                            .setPositiveButton("Ok") { _, _ ->
                                finish()
                            }.show()
                    }
                    is AddNoteSuccess -> {
                        finish()
                    }
                }
            }
        )
    }

    private fun generateNote(event: SubmitClicked): Note {
        return if (note == null) {
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
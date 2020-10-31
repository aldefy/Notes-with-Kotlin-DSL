package com.caster.notes.dsl.features.list.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.caster.notes.dsl.R
import com.caster.notes.dsl.common.BaseActivity
import com.caster.notes.dsl.common.addTo
import com.caster.notes.dsl.features.add.presentation.NoteAddActivity
import com.caster.notes.dsl.features.list.di.NotesInjector
import com.caster.notes.dsl.features.list.domain.NotesListViewModel
import com.caster.notes.dsl.features.list.domain.NotesState
import com.caster.notes.dsl.views.NoteSearchView
import kotlinx.android.synthetic.main.activity_notes.*
import javax.inject.Inject

class NotesListActivity : BaseActivity<NotesListViewModel, NotesState>() {

    override val clazz: Class<NotesListViewModel> = NotesListViewModel::class.java
    private val screen by lazy { NotesScreenImpl() }

    @Inject
    lateinit var view: NotesView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        NotesInjector.of(this, contentView)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        initListeners()
        vm.getNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> true
            R.id.action_search -> with(item) {
                actionView = view.searchView
                (actionView as NoteSearchView).apply {
                    onActionViewExpanded()
                    observeTextChanges(
                        onSuccess = { query ->
                            screen.searchNotes(query, view)
                        },
                        disposable = compositeBag
                    )
                    setCloseClickedListener {

                    }
                }
                true
            }
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
        screen.withFAB(view = view)
        screen.event.observe(
            this,
            Observer { event ->
                when (event) {
                    is NotesFetchedEvent -> {

                    }
                    is FABClickedEvent -> {
                        startActivity(NoteAddActivity.create(this))
                    }
                }

            }
        )
    }
}
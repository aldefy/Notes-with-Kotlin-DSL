package com.caster.notes.dsl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.caster.notes.dsl.model.Note
import com.caster.notes.dsl.model.NotesDao
import com.caster.notes.dsl.model.NotesDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NotesDaoTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var db: NotesDatabase
    private lateinit var dao: NotesDao
    private val note =  Note.create {
        title = "title"
        content = "content"
    }


    @Before
    fun start() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NotesDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        note.id =1
        dao = db.notesDao()
    }

    @After
    fun stop() {
        db.close()
    }

    @Test
    fun getNotes_shouldReturnValidResult() {
        clearAndAddData()

        dao.getAllNotes()
            .test()
            .assertValue{
                it.isNotEmpty()
            }
    }

    @Test
    fun save_NoteWithSameKey_shouldReplaceTheExistingNote() {
        clearAndAddData()
        dao.addOrUpdate(note = note)
            .test()
        dao.getAllNotes()
            .test()
            .assertValueCount(1)
    }

    @Test
    fun givenNote_delete_shouldDeleteTheNote() {
        clearAndAddData()
        dao.getAllNotes()
            .test()
            .assertValueCount(1)

        dao.delete(note)
            .test()

        dao.getAllNotes()
            .test()
            .assertValue {
                it.isEmpty()
            }
            .assertValueCount(1)
    }



    private fun clearAndAddData() {
        dao.nuke()
        dao.addOrUpdate(note).subscribe()
    }
}
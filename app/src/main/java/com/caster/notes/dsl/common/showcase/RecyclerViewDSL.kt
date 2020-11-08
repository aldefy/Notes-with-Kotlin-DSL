package com.caster.notes.dsl.common.showcase

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

interface RecyclerViewHandler {
    fun getAdapter(): DSLRVAdapter
}

class RecyclerViewHandlerImpl internal constructor(val dslAdapter: DSLRVAdapter) :
    RecyclerViewHandler {
    override fun getAdapter(): DSLRVAdapter {
        return dslAdapter
    }
}

class RecyclerViewDSLBuilder internal constructor(
    private val recyclerView: RecyclerView
) {
    private lateinit var clickEvent: (model: DataModel, position: Int) -> Unit
    var items = mutableListOf<DataModel>()

    fun withLayoutManager(layoutManager: RecyclerView.LayoutManager): RecyclerViewDSLBuilder {
        recyclerView.layoutManager = layoutManager
        return this
    }

    inline fun <reified T : DataModel> withField(item: T.() -> Unit): RecyclerViewDSLBuilder {
        val entry = T::class.java.newInstance()
        entry.item()
        this.items.add(entry)
        return this
    }

    fun withClick(event: (model: DataModel, position: Int) -> Unit): RecyclerViewDSLBuilder {
        clickEvent = event
        return this
    }

    internal fun build(): RecyclerViewHandlerImpl {
        return RecyclerViewHandlerImpl(DSLRVAdapter(items) { model, position ->
            clickEvent(model, position)
        })
    }
}

fun RecyclerView.dsl(block: RecyclerViewDSLBuilder.() -> Unit): RecyclerViewHandler {
    val builder = RecyclerViewDSLBuilder(this).apply(block)
    if (layoutManager == null) {
        layoutManager = LinearLayoutManager(context)
    }
    return builder.build().also {
        adapter = it.dslAdapter
    }
}
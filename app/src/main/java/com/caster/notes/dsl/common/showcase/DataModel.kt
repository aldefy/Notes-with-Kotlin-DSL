package com.caster.notes.dsl.common.showcase

import android.view.View

abstract class DataModel(
    open val layout: Int // The layout resource ID to inflate for this row.
) {
    /**
     * define criteria for including this field in a search result
     */
    open fun search(query: String): Boolean {
        return true
    }

    abstract fun bind(
        itemView: View,
        adapter: DSLRVAdapter,
        position: Int,
        event: (model: DataModel) -> Unit
    )

    /**
     * the value that this row should return when called in @DSLRVAdapter
     */
    abstract fun getValue(): Any
}
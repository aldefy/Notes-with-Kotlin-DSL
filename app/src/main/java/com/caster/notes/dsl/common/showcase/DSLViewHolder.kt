package com.caster.notes.dsl.common.showcase

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class DSLViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        dataModel: DataModel,
        uiComposeAdapter: DSLRVAdapter,
        position: Int,
        listener: (dataModel: DataModel) -> Unit
    ) {
        dataModel.bind(itemView, uiComposeAdapter, position, listener)
    }
}
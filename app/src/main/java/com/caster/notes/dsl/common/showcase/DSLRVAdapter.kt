package com.caster.notes.dsl.common.showcase

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView

open class DSLRVAdapter(
    private val models: MutableList<DataModel>,
    private var event: (model: DataModel, position: Int) -> Unit
) : RecyclerView.Adapter<DSLViewHolder>(), Filterable {
    private val completeList: MutableList<DataModel> = arrayListOf()
    private var search = ""

    init {
        completeList.addAll(models)
    }

    override fun getFilter(): Filter {
        return FilterData()
    }

    override fun getItemViewType(position: Int): Int {
        return models[position].layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DSLViewHolder {
        return DSLViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: DSLViewHolder, position: Int) {
        holder.bind(models[position], this, position) {
            event(it, position)
        }
    }

    /**
     * Update the models in this adapter
     */
    fun updateModels(models: MutableList<out DataModel>) {
        this.completeList.clear()
        this.completeList.addAll(models)
        filter.filter(search)
    }

    /**
     * Add new models in this adapter
     */
    fun addModels(models: MutableList<out DataModel>) {
        this.completeList.addAll(models)
        filter.filter(search)
    }

    /**
     * retrieve models in this adapter
     */
    fun models(): MutableList<DataModel> {
        return models
    }

    inner class FilterData : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            return performSearch(constraint)
        }

        private fun performSearch(constraint: CharSequence?): FilterResults {
            search = constraint?.toString() ?: ""
            val filterResult = FilterResults()

            val result = constraint?.toString()?.let { query ->
                if (query.isBlank()) completeList else completeList.filter {
                    it.search(query)
                }
            } ?: kotlin.run { completeList }

            filterResult.count = result.size
            filterResult.values = result

            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            models.clear()
            models.addAll(
                results?.values as? MutableList<DataModel>
                    ?: kotlin.run { mutableListOf<DataModel>() }
            )
            notifyDataSetChanged()
        }
    }
}

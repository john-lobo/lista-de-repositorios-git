package com.jlndev.listaderepositriosgit.bases

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<MODEL : BaseAdapter.BaseDiffItemView, VH : BaseAdapter.ViewHolder<MODEL>, LISTENER : BaseAdapter.AdapterListener<MODEL>>(
    open var listener: LISTENER
) : RecyclerView.Adapter<VH>() {

    protected lateinit var recyclerView: RecyclerView
    private var items: List<MODEL> = emptyList()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    open fun submitList(newItems: List<MODEL>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    abstract class ViewHolder<MODEL>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: MODEL)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun removeAt(index: Int) {
        val newItems = items.toMutableList()
        newItems.removeAt(index)
        submitList(newItems)
    }

    fun add(index: Int, item: MODEL) {
        val newItems = items.toMutableList()
        newItems.add(index, item)
        submitList(newItems)
    }

    fun replace(item: MODEL, position: Int) {
        val newItems = items.toMutableList()
        newItems.removeAt(position)
        newItems.add(position, item)
        submitList(newItems)
    }

    fun removeLastItem() {
        val newItems = items.toMutableList()
        if (newItems.isNotEmpty()) {
            newItems.removeAt(newItems.size - 1)
            submitList(newItems)
        }
    }

    fun clear() {
        submitList(emptyList())
    }

    fun addAll(newItems: List<MODEL>) {
        val updatedItems = items.toMutableList()
        updatedItems.addAll(newItems)
        submitList(updatedItems)
    }

    inner class DiffUtilCallback(
        private val oldList: List<MODEL>,
        private val newList: List<MODEL>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    interface AdapterListener<T> {
        fun onAdapterItemClicked(position: Int, item: T, view: View? = null)
    }

    abstract class BaseDiffItemView {
        abstract val id: String
    }
}

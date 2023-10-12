package com.jlndev.listaderepositriosgit.bases.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<MODEL : BaseDiffItemView, VH : BaseViewHolder<MODEL>, LISTENER : BaseAdapterListener<MODEL>>(
    open var listener: LISTENER
) : RecyclerView.Adapter<VH>() {

    protected val controller = BaseAdapterController<MODEL>()

    override fun getItemCount() = controller.getItems().size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(controller.getItems()[position])
        holder.itemView.setOnClickListener {
            listener.onAdapterItemClicked(position, controller.getItems()[position], holder.itemView)
        }
    }

    open fun submitList(newItems: List<MODEL>) {
        val diffResult = DiffUtil.calculateDiff(BaseDiffUtilCallback(controller.getItems(), newItems))
        controller.setItems(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

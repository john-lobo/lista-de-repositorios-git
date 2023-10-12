package com.jlndev.listaderepositriosgit.bases.adapter

class BaseAdapterController<MODEL : BaseDiffItemView> {
    private var items: List<MODEL> = emptyList()

    fun getItems(): List<MODEL> {
        return items
    }

    fun setItems(newItems: List<MODEL>) {
        items = newItems
    }

    fun removeAt(index: Int) {
        if (index in 0 until items.size) {
            val newItems = items.toMutableList()
            newItems.removeAt(index)
            items = newItems
        }
    }

    fun addItem(item: MODEL) {
        val newItems = items.toMutableList()
        newItems.add(item)
        items = newItems
    }

    fun removeItem(item: MODEL) {
        val index = items.indexOf(item)
        if (index >= 0) {
            val newItems = items.toMutableList()
            newItems.remove(item)
            items = newItems
        }
    }

    fun replace(item: MODEL, position: Int) {
        if (position in 0 until items.size) {
            val newItems = items.toMutableList()
            newItems.removeAt(position)
            newItems.add(position, item)
            items = newItems
        }
    }

    fun removeLastItem() {
        if (items.isNotEmpty()) {
            val newItems = items.toMutableList()
            newItems.removeAt(newItems.size - 1)
            items = newItems
        }
    }

    fun clear() {
        items = emptyList()
    }

    fun addAll(newItems: List<MODEL>) {
        val updatedItems = items.toMutableList()
        updatedItems.addAll(newItems)
        items = updatedItems
    }
}

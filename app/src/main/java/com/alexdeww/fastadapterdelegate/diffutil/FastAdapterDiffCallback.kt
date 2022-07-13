package com.alexdeww.fastadapterdelegate.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.mikepenz.fastadapter.GenericItem

open class FastAdapterDiffCallback<I : GenericItem>(
    private val oldItems: List<I>,
    private val newItems: List<I>,
    private val callback: DiffUtil.ItemCallback<I>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = callback
        .areItemsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = callback
        .areContentsTheSame(oldItems[oldItemPosition], newItems[newItemPosition])

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? = callback
        .getChangePayload(oldItems[oldItemPosition], newItems[newItemPosition])

}

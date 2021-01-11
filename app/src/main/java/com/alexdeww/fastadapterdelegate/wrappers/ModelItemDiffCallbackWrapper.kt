package com.alexdeww.fastadapterdelegate.wrappers

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.IModelItem

class ModelItemDiffCallbackWrapper<M : Any, I : IModelItem<M, out RecyclerView.ViewHolder>>(
    private val itemDiffCallback: DiffUtil.ItemCallback<M>
) : DiffUtil.ItemCallback<I>() {

    override fun areItemsTheSame(oldItem: I, newItem: I): Boolean =
        itemDiffCallback.areItemsTheSame(oldItem.model, newItem.model)

    override fun areContentsTheSame(oldItem: I, newItem: I): Boolean =
        itemDiffCallback.areContentsTheSame(oldItem.model, newItem.model)

    override fun getChangePayload(oldItem: I, newItem: I): Any? =
        itemDiffCallback.getChangePayload(oldItem.model, newItem.model)

}

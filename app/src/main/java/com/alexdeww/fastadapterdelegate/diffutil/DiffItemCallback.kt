package com.alexdeww.fastadapterdelegate.diffutil

import androidx.recyclerview.widget.DiffUtil

class DiffItemCallback<T : Any>(
    private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val areContentsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val getChangePayload: ((oldItem: T, newItem: T) -> Any?)? = null
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame.invoke(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame.invoke(oldItem, newItem)

    override fun getChangePayload(oldItem: T, newItem: T): Any? =
        getChangePayload?.invoke(oldItem, newItem)

}

fun <T : Any> diffUtilItemCallback(
    areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    areContentsTheSame: (oldItem: T, newItem: T) -> Boolean,
    getChangePayload: ((oldItem: T, newItem: T) -> Any?)? = null
) = DiffItemCallback(areItemsTheSame, areContentsTheSame, getChangePayload)

fun <T : Any> DiffUtil.ItemCallback<T>.with(
    areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    areContentsTheSame: (oldItem: T, newItem: T) -> Boolean,
    getChangePayload: ((oldItem: T, newItem: T) -> Any?)? = null
) = DiffItemCallback(
    areItemsTheSame = { oldItem: T, newItem: T ->
        areItemsTheSame.invoke(oldItem, newItem) || this.areItemsTheSame(oldItem, newItem)
    },
    areContentsTheSame = { oldItem: T, newItem: T ->
        areContentsTheSame.invoke(oldItem, newItem) || this.areContentsTheSame(oldItem, newItem)
    },
    getChangePayload = { oldItem: T, newItem: T ->
        getChangePayload?.invoke(oldItem, newItem) ?: this.getChangePayload(oldItem, newItem)
    }
)

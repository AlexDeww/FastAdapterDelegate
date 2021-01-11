package com.alexdeww.fastadapterdelegate.wrappers

import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem

/**
 * Враппер для FastAdapter, который сообщает, что достигнут конец списка
 */
open class EndlessFastAdapterWrapper<T : GenericItem>(
    fastAdapter: FastAdapter<T>,
    protected val onEndList: (Int) -> Unit
) : BaseFastAdapterWrapper<T>(fastAdapter) {

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (position >= itemCount - 1) doOnEnd()
    }

    protected fun doOnEnd() {
        onEndList.invoke(itemCount)
    }
}

fun <T : GenericItem> FastAdapter<T>.withEndless(
    onEndAction: (Int) -> Unit
) = EndlessFastAdapterWrapper(this, onEndAction)

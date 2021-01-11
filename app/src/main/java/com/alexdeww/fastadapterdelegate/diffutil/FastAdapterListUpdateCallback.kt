package com.alexdeww.fastadapterdelegate.diffutil

import androidx.recyclerview.widget.ListUpdateCallback
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.ModelAdapter

class FastAdapterListUpdateCallback<A : ModelAdapter<M, I>, M, I : GenericItem>(
    private val adapter: A
) : ListUpdateCallback {

    private val preItemCountByOrder: Int
        get() = adapter.fastAdapter?.getPreItemCountByOrder(adapter.order) ?: 0

    override fun onInserted(position: Int, count: Int) {
        adapter.fastAdapter?.notifyAdapterItemRangeInserted(preItemCountByOrder + position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.fastAdapter?.notifyAdapterItemRangeRemoved(preItemCountByOrder + position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.fastAdapter?.notifyAdapterItemMoved(preItemCountByOrder + fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapter.fastAdapter?.notifyAdapterItemRangeChanged(
            preItemCountByOrder + position,
            count,
            payload
        )
    }

}

package com.alexdeww.fastadapterdelegate.adapters

import androidx.recyclerview.widget.DiffUtil
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.IAdapterNotifier
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.alexdeww.fastadapterdelegate.diffutil.FastAdapterDiffCallback
import com.alexdeww.fastadapterdelegate.diffutil.FastAdapterListUpdateCallback

open class DiffModelAdapter<Model, Item : GenericItem>(
    protected val diffCallback: DiffUtil.ItemCallback<Item>,
    interceptor: (element: Model) -> Item?
) : ModelAdapter<Model, Item>(interceptor) {

    override fun setInternal(
        items: List<Item>,
        resetFilter: Boolean,
        adapterNotifier: IAdapterNotifier?
    ): ModelAdapter<Model, Item> {
        val oldItems = FastAdapterDiffUtil.prepare(this, items)
        val diffCallback = FastAdapterDiffCallback(oldItems, items, diffCallback)
        val diffResult = DiffUtil.calculateDiff(diffCallback, true)
        FastAdapterDiffUtil.postCalculate(this, items)
        diffResult.dispatchUpdatesTo(FastAdapterListUpdateCallback(this))

        return this
    }

}

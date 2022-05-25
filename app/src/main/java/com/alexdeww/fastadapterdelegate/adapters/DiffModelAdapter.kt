package com.alexdeww.fastadapterdelegate.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexdeww.fastadapterdelegate.diffutil.FastAdapterDiffCallback
import com.alexdeww.fastadapterdelegate.diffutil.FastAdapterListUpdateCallback
import com.alexdeww.fastadapterdelegate.wrappers.ModelItemDiffCallbackWrapper
import com.mikepenz.fastadapter.IAdapterNotifier
import com.mikepenz.fastadapter.IModelItem
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil

open class DiffModelAdapter<Model : Any, Item : IModelItem<out Model, out RecyclerView.ViewHolder>>(
    diffCallback: DiffUtil.ItemCallback<Model>,
    interceptor: (element: Model) -> Item?
) : ModelAdapter<Model, Item>(interceptor) {

    protected val itemsDiffCallback = ModelItemDiffCallbackWrapper<Model, Item>(diffCallback)

    override fun setInternal(
        items: List<Item>,
        resetFilter: Boolean,
        adapterNotifier: IAdapterNotifier?
    ): ModelAdapter<Model, Item> {
        val oldItems = FastAdapterDiffUtil.prepare(this, items)
        val diffCallback = FastAdapterDiffCallback(oldItems, items, itemsDiffCallback)
        val diffResult = DiffUtil.calculateDiff(diffCallback, true)
        FastAdapterDiffUtil.postCalculate(this, items)
        diffResult.dispatchUpdatesTo(FastAdapterListUpdateCallback(this))

        return this
    }

}

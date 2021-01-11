package com.alexdeww.fastadapterdelegate

import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.IItemAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter

open class ModelFastAdapter<M, T : GenericItem>(
    val adapter: ModelAdapter<M, T>
) : IItemAdapter<M, T> by adapter, FastAdapter<T>() {
    init {
        @Suppress("LeakingThis")
        addAdapter<IAdapter<T>>(0, adapter)
        cacheSizes()
    }
}

fun <M, I : GenericItem, A : ModelAdapter<M, I>> modelFastAdapter(itemAdapter: A) =
    ModelFastAdapter(itemAdapter)

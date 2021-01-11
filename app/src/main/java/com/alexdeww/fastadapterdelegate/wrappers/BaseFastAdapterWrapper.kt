package com.alexdeww.fastadapterdelegate.wrappers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem

@Suppress("DeteKt.TooManyFunctions")
abstract class BaseFastAdapterWrapper<T : GenericItem>(
    val fastAdapter: FastAdapter<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        fastAdapter.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        fastAdapter.unregisterAdapterDataObserver(observer)
    }

    override fun getItemViewType(position: Int): Int = fastAdapter.getItemViewType(position)

    override fun getItemId(position: Int): Long = fastAdapter.getItemId(position)

    override fun getItemCount(): Int = fastAdapter.itemCount

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        fastAdapter.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        fastAdapter.onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        fastAdapter.onBindViewHolder(holder, position, payloads)
    }

    override fun setHasStableIds(hasStableIds: Boolean) {
        fastAdapter.setHasStableIds(hasStableIds)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean =
        fastAdapter.onFailedToRecycleView(holder)

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewAttachedToWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        fastAdapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        fastAdapter.onDetachedFromRecyclerView(recyclerView)
    }

    open fun getItem(position: Int): T? = fastAdapter.getItem(position)

}

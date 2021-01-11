package com.alexdeww.fastadapterdelegate.viewholder

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import kotlinx.android.extensions.LayoutContainer

abstract class FastAdapterContentViewHolder<I : GenericItem>(
    final override val containerView: View
) : FastAdapter.ViewHolder<I>(containerView), LayoutContainer {

    val context: Context get() = containerView.context

    fun <V : View> findViewById(@IdRes id: Int): V = itemView.findViewById(id) as V

}

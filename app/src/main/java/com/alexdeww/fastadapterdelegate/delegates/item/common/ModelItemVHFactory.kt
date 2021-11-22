package com.alexdeww.fastadapterdelegate.delegates.item.common

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

interface ModelItemVHFactory<VH : RecyclerView.ViewHolder> {

    fun getViewHolder(parent: ViewGroup, @LayoutRes layoutRes: Int): VH

}

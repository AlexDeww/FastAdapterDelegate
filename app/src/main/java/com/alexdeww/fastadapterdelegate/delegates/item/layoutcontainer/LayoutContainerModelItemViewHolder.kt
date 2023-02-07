package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.ModelItemVHCreator

interface LayoutContainerModelItemVHCreator<M, I> :
    ModelItemVHCreator<LayoutContainerModelItemViewHolder<M, I>>
        where I : AbsDelegationModelItem<M, I> {

    val layoutId: Int

    @CallSuper
    override fun createViewHolder(parent: ViewGroup): LayoutContainerModelItemViewHolder<M, I> {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return LayoutContainerModelItemViewHolder(view)
    }

}

class LayoutContainerModelItemViewHolder<M, I : AbsDelegationModelItem<M, *>>(
    itemView: View
) : AbsDelegationModelItem.ViewHolder<M, I>(itemView) {

    fun <V : View> findViewById(@IdRes id: Int): V = itemView.findViewById(id) as V

}

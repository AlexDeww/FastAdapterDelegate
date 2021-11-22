package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.ModelItemVHFactory
import kotlinx.android.extensions.LayoutContainer

interface LayoutContainerModelItemVHFactory<M, I : AbsDelegationModelItem<M, *, *>> :
    ModelItemVHFactory<LayoutContainerModelItemViewHolder<M, I>> {

    override fun getViewHolder(
        parent: ViewGroup,
        layoutRes: Int
    ): LayoutContainerModelItemViewHolder<M, I> =
        getViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))

    fun getViewHolder(view: View): LayoutContainerModelItemViewHolder<M, I> =
        LayoutContainerModelItemViewHolder(view)

}

class LayoutContainerModelItemViewHolder<M, I : AbsDelegationModelItem<M, *, *>>(
    override val containerView: View
) : AbsDelegationModelItem.ViewHolder<M, I>(containerView), LayoutContainer {

    fun <V : View> findViewById(@IdRes id: Int): V = containerView.findViewById(id) as V

}

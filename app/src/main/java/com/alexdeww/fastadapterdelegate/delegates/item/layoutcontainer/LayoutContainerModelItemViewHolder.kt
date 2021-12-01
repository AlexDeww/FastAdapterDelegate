package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.ModelItemVHCreator
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.extensions.LayoutContainer

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

@ContainerOptions(cache = CacheImplementation.SPARSE_ARRAY)
class LayoutContainerModelItemViewHolder<M, I : AbsDelegationModelItem<M, *>>(
    override val containerView: View
) : AbsDelegationModelItem.ViewHolder<M, I>(containerView), LayoutContainer {

    fun <V : View> findViewById(@IdRes id: Int): V = containerView.findViewById(id) as V

}

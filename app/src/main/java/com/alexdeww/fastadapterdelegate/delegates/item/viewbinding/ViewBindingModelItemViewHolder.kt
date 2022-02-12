package com.alexdeww.fastadapterdelegate.delegates.item.viewbinding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.ModelItemVHCreator

interface ViewBindingModelItemVHCreator<M, I, VB : ViewBinding> :
    ModelItemVHCreator<ViewBindingModelItemViewHolder<M, I, VB>>
        where I : AbsDelegationModelItem<M, I> {

    val viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB

    @CallSuper
    override fun createViewHolder(parent: ViewGroup): ViewBindingModelItemViewHolder<M, I, VB> {
        val viewBinding = viewBindingProvider(LayoutInflater.from(parent.context), parent)
        return ViewBindingModelItemViewHolder(viewBinding)
    }

}

class ViewBindingModelItemViewHolder<M, I : AbsDelegationModelItem<M, *>, VB : ViewBinding>(
    val viewBinding: VB,
    itemView: View = viewBinding.root
) : AbsDelegationModelItem.ViewHolder<M, I>(itemView)

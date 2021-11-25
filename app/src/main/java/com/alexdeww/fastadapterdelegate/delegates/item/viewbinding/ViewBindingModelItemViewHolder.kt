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
        where I : AbsDelegationModelItem<M, I, ViewBindingModelItemViewHolder<M, I, VB>> {

    val viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB

    @CallSuper
    override fun createViewHolder(parent: ViewGroup): ViewBindingModelItemViewHolder<M, I, VB> {
        val binding = viewBinding(LayoutInflater.from(parent.context), parent)
        return ViewBindingModelItemViewHolder(binding)
    }

}

class ViewBindingModelItemViewHolder<M, I : AbsDelegationModelItem<M, *, *>, VB : ViewBinding>(
    val binding: VB,
    itemView: View = binding.root
) : AbsDelegationModelItem.ViewHolder<M, I>(itemView)

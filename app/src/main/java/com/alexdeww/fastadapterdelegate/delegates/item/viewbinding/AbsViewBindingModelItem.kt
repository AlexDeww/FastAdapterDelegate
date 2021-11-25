package com.alexdeww.fastadapterdelegate.delegates.item.viewbinding

import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem

abstract class AbsViewBindingModelItem<M, I, VB : ViewBinding>(model: M) :
    AbsDelegationModelItem<M, I, ViewBindingModelItemViewHolder<M, I, VB>>(model)
        where I : AbsViewBindingModelItem<M, I, VB>

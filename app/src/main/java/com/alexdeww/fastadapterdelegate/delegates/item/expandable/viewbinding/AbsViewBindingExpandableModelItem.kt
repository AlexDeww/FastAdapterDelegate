package com.alexdeww.fastadapterdelegate.delegates.item.expandable.viewbinding

import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsDelegationExpandableModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.viewbinding.ViewBindingModelItemViewHolder

abstract class AbsViewBindingExpandableModelItem<M, I, VB : ViewBinding>(model: M) :
    AbsDelegationExpandableModelItem<M, I, ViewBindingModelItemViewHolder<M, I, VB>>(model)
        where I : AbsViewBindingExpandableModelItem<M, I, VB>

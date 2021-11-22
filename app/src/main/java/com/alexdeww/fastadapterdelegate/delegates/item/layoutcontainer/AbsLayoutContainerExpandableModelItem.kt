package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsDelegationExpandableModelItem

abstract class AbsLayoutContainerExpandableModelItem<M, I>(
    model: M,
    isAutoExpanding: Boolean = true
) : AbsDelegationExpandableModelItem<M, I, LayoutContainerModelItemViewHolder<M, I>>(
    model,
    isAutoExpanding
), LayoutContainerModelItemVHFactory<M, I> where I : AbsLayoutContainerExpandableModelItem<M, I>

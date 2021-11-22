package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem

abstract class AbsLayoutContainerModelItem<M, I>(
    model: M
) : AbsDelegationModelItem<M, I, LayoutContainerModelItemViewHolder<M, I>>(model),
    LayoutContainerModelItemVHFactory<M, I> where I : AbsLayoutContainerModelItem<M, I>
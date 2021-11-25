package com.alexdeww.fastadapterdelegate.delegates.item.expandable.layoutcontainer

import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsDelegationExpandableModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerModelItemViewHolder

abstract class AbsLayoutContainerExpandableModelItem<M, I>(model: M) :
    AbsDelegationExpandableModelItem<M, I, LayoutContainerModelItemViewHolder<M, I>>(model)
        where I : AbsLayoutContainerExpandableModelItem<M, I>

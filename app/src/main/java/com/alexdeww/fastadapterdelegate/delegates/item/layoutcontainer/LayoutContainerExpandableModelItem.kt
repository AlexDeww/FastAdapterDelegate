package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

typealias LayoutContainerExpandableModelItemVH<M> = LayoutContainerModelItemViewHolder<M, LayoutContainerExpandableModelItem<M>>

class LayoutContainerExpandableModelItem<M>(
    model: M,
    isAutoExpanding: Boolean = true
) : AbsLayoutContainerExpandableModelItem<M, LayoutContainerExpandableModelItem<M>>(
    model,
    isAutoExpanding
)

package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

typealias LayoutContainerModelItemVH<M> = LayoutContainerModelItemViewHolder<M, LayoutContainerModelItem<M>>

class LayoutContainerModelItem<M>(model: M) :
    AbsLayoutContainerModelItem<M, LayoutContainerModelItem<M>>(model)

package com.alexdeww.fastadapterdelegate.delegates.item.expandable

import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem
import com.mikepenz.fastadapter.IExpandable
import com.mikepenz.fastadapter.ISubItem
import com.mikepenz.fastadapter.MutableSubItemList

abstract class AbsDelegationExpandableModelItem<M, I>(
    model: M
) : AbsDelegationModelItem<M, I>(model),
    IExpandable<AbsDelegationModelItem.ViewHolder<M, I>> where I : AbsDelegationExpandableModelItem<M, I> {

    override var isAutoExpanding: Boolean = true
        internal set

    override var isExpanded: Boolean = false

    @Suppress("LeakingThis")
    override var subItems: MutableList<ISubItem<*>> = MutableSubItemList(this)

    @Suppress("UNUSED_PARAMETER")
    override var isSelectable: Boolean
        get() = subItems.isEmpty()
        set(value) { /* empty */ }

}

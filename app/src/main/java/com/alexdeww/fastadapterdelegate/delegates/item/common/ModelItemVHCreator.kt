package com.alexdeww.fastadapterdelegate.delegates.item.common

import android.view.ViewGroup

interface ModelItemVHCreator<VH : AbsDelegationModelItem.ViewHolder<*, *>> {

    fun createViewHolder(parent: ViewGroup): VH

}

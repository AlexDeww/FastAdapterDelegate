package com.alexdeww.fastadapterdelegate.delegates.item.expandable

import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate

class DefaultExpandableModelItem<M>(model: M) :
    AbsDelegationExpandableModelItem<M, DefaultExpandableModelItem<M>>(model)

@PublishedApi
internal fun <M : BM, BM> defaultExpandableModelItemInterceptor(
    model: M,
    @Suppress("UNUSED_PARAMETER") delegates: List<ModelItemDelegate<BM>>
): DefaultExpandableModelItem<M> = DefaultExpandableModelItem(model)

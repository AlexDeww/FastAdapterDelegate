package com.alexdeww.fastadapterdelegate.delegates.item.common

import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate

class DefaultModelItem<M>(model: M) : AbsDelegationModelItem<M, DefaultModelItem<M>>(model)

@PublishedApi
internal fun <M : BM, BM> defaultModelItemInterceptor(
    model: M,
    @Suppress("UNUSED_PARAMETER") delegates: List<ModelItemDelegate<BM>>
): DefaultModelItem<M> = DefaultModelItem(model)

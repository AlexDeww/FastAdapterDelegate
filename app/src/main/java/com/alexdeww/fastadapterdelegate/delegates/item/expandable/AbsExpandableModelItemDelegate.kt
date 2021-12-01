package com.alexdeww.fastadapterdelegate.delegates.item.expandable

import androidx.annotation.CallSuper
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsModelItemDelegate

abstract class AbsExpandableModelItemDelegate<M : BM, BM, I, VH>(
    type: Int,
    private val isAutoExpanding: Boolean = true,
    on: (model: BM) -> Boolean,
    itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    private val subItemsInitializer: M.() -> List<BM> = { emptyList() },
    itemInitializer: (I.() -> Unit)? = null,
    delegateInitializer: VH.() -> Unit
) : AbsModelItemDelegate<M, BM, I, VH>(
    type = type,
    on = on,
    itemInterceptor = itemInterceptor,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
) where I : AbsDelegationExpandableModelItem<M, I>,
        VH : AbsDelegationModelItem.ViewHolder<M, I> {

    @CallSuper
    override fun internalInitializeItem(item: I, delegates: List<ModelItemDelegate<BM>>) {
        super.internalInitializeItem(item, delegates)
        item.isAutoExpanding = isAutoExpanding
        val subItems = subItemsInitializer.invoke(item.model).mapNotNull { subModel ->
            delegates
                .find { delegate -> delegate.isForViewType(subModel) }
                ?.intercept(subModel, delegates)
        }
        item.subItems.addAll(subItems)
    }

}

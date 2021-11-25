package com.alexdeww.fastadapterdelegate.delegates.item.expandable.layoutcontainer

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerModelItemViewHolder

typealias LayoutContainerExpandableModelItemVH<M> = LayoutContainerModelItemViewHolder<M, LayoutContainerExpandableModelItem<M>>

inline fun <reified M : BM, BM> expandableModelItemDelegateLayoutContainer(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    isAutoExpanding: Boolean = true,
    noinline subItemsInitializer: M.() -> List<BM> = { emptyList() },
    noinline itemInitializer: (LayoutContainerExpandableModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerExpandableModelItemVH<M>.() -> Unit
): ModelItemDelegate<BM> = customExpandableModelItemDelegateLayoutContainer(
    type = type,
    layoutId = layoutId,
    isAutoExpanding = isAutoExpanding,
    itemInterceptor = ::layoutContainerExpandableModelItemInterceptor,
    subItemsInitializer = subItemsInitializer,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

class LayoutContainerExpandableModelItem<M>(model: M) :
    AbsLayoutContainerExpandableModelItem<M, LayoutContainerExpandableModelItem<M>>(model)

@PublishedApi
internal fun <M : BM, BM> layoutContainerExpandableModelItemInterceptor(
    model: M,
    @Suppress("UNUSED_PARAMETER") delegates: List<ModelItemDelegate<BM>>
): LayoutContainerExpandableModelItem<M> = LayoutContainerExpandableModelItem(model)

package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate

typealias LayoutContainerModelItemVH<M> = LayoutContainerModelItemViewHolder<M, LayoutContainerModelItem<M>>

inline fun <reified M : BM, BM> modeItemDelegateLayoutContainer(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    noinline itemInitializer: (LayoutContainerModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerModelItemVH<M>.() -> Unit
): ModelItemDelegate<BM> = customModeItemDelegateLayoutContainer(
    type = type,
    layoutId = layoutId,
    itemInterceptor = ::layoutContainerModelItemInterceptor,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

inline fun <reified M> modeItemDelegateLayoutContainerSimple(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    noinline itemInitializer: (LayoutContainerModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerModelItemVH<M>.() -> Unit
): ModelItemDelegate<M> = modeItemDelegateLayoutContainer(
    type = type,
    layoutId = layoutId,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

class LayoutContainerModelItem<M>(model: M) :
    AbsLayoutContainerModelItem<M, LayoutContainerModelItem<M>>(model)

@PublishedApi
internal fun <M : BM, BM> layoutContainerModelItemInterceptor(
    model: M,
    @Suppress("UNUSED_PARAMETER") delegates: List<ModelItemDelegate<BM>>
): LayoutContainerModelItem<M> = LayoutContainerModelItem(model)

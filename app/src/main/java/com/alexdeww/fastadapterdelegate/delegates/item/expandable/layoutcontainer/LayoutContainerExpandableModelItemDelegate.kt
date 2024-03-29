package com.alexdeww.fastadapterdelegate.delegates.item.expandable.layoutcontainer

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsDelegationExpandableModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsExpandableModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.DefaultExpandableModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.defaultExpandableModelItemInterceptor
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerModelItemVHCreator
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerModelItemViewHolder

@Suppress("LongParameterList")
inline fun <reified M : BM, BM, I> customExpandableModelItemDelegateLayoutContainer(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    isAutoExpanding: Boolean = true,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline subItemsInitializer: M.() -> List<BM> = { emptyList() },
    noinline on: (model: BM) -> Boolean = { model: BM -> model is M },
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerModelItemViewHolder<M, I>.() -> Unit
): ModelItemDelegate<BM> where I : AbsDelegationExpandableModelItem<M, I> {
    return LayoutContainerExpandableModelItemDelegate(
        type = type,
        layoutId = layoutId,
        isAutoExpanding = isAutoExpanding,
        on = on,
        itemInterceptor = itemInterceptor,
        subItemsInitializer = subItemsInitializer,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

typealias LayoutContainerDefaultExpandableModelItemVH<M> =
        LayoutContainerModelItemViewHolder<M, DefaultExpandableModelItem<M>>

@Suppress("LongParameterList")
inline fun <reified M : BM, BM> expandableModelItemDelegateLayoutContainer(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    isAutoExpanding: Boolean = true,
    noinline subItemsInitializer: M.() -> List<BM> = { emptyList() },
    noinline on: (model: BM) -> Boolean = { model: BM -> model is M },
    noinline itemInitializer: (DefaultExpandableModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerDefaultExpandableModelItemVH<M>.() -> Unit
): ModelItemDelegate<BM> = customExpandableModelItemDelegateLayoutContainer(
    type = type,
    layoutId = layoutId,
    isAutoExpanding = isAutoExpanding,
    itemInterceptor = ::defaultExpandableModelItemInterceptor,
    subItemsInitializer = subItemsInitializer,
    on = on,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

@Suppress("LongParameterList")
@PublishedApi
internal class LayoutContainerExpandableModelItemDelegate<M : BM, BM, I>(
    type: Int,
    override val layoutId: Int,
    isAutoExpanding: Boolean = true,
    on: (model: BM) -> Boolean,
    itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    subItemsInitializer: M.() -> List<BM> = { emptyList() },
    itemInitializer: (I.() -> Unit)? = null,
    delegateInitializer: LayoutContainerModelItemViewHolder<M, I>.() -> Unit
) : AbsExpandableModelItemDelegate<M, BM, I, LayoutContainerModelItemViewHolder<M, I>>(
    type = type,
    isAutoExpanding = isAutoExpanding,
    on = on,
    itemInterceptor = itemInterceptor,
    subItemsInitializer = subItemsInitializer,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
), LayoutContainerModelItemVHCreator<M, I> where I : AbsDelegationExpandableModelItem<M, I>

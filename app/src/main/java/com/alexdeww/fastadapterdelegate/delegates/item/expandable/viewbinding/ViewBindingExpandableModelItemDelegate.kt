package com.alexdeww.fastadapterdelegate.delegates.item.expandable.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsDelegationExpandableModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsExpandableModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.DefaultExpandableModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.defaultExpandableModelItemInterceptor
import com.alexdeww.fastadapterdelegate.delegates.item.viewbinding.ViewBindingModelItemVHCreator
import com.alexdeww.fastadapterdelegate.delegates.item.viewbinding.ViewBindingModelItemViewHolder

@Suppress("LongParameterList")
inline fun <reified M : BM, BM, I, VB : ViewBinding> customExpandableModelItemDelegateViewBinding(
    type: Int,
    noinline viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    isAutoExpanding: Boolean = true,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline subItemsInitializer: M.() -> List<BM> = { emptyList() },
    noinline on: (model: BM) -> Boolean = { model: BM -> model is M },
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingModelItemViewHolder<M, I, VB>.() -> Unit
): ModelItemDelegate<BM> where I : AbsDelegationExpandableModelItem<M, I> {
    return ViewBindingExpandableModelItemDelegate(
        type = type,
        viewBindingProvider = viewBindingProvider,
        isAutoExpanding = isAutoExpanding,
        on = on,
        itemInterceptor = itemInterceptor,
        subItemsInitializer = subItemsInitializer,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer,
    )
}

typealias ViewBindingDefaultExpandableModelItemVH<M, VB> =
        ViewBindingModelItemViewHolder<M, DefaultExpandableModelItem<M>, VB>

@Suppress("LongParameterList")
inline fun <reified M : BM, BM, VB : ViewBinding> expandableModelItemDelegateViewBinding(
    @IdRes type: Int,
    noinline viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    isAutoExpanding: Boolean = true,
    noinline subItemsInitializer: M.() -> List<BM> = { emptyList() },
    noinline on: (model: BM) -> Boolean = { model: BM -> model is M },
    noinline itemInitializer: (DefaultExpandableModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingDefaultExpandableModelItemVH<M, VB>.() -> Unit
): ModelItemDelegate<BM> = customExpandableModelItemDelegateViewBinding(
    type = type,
    viewBindingProvider = viewBindingProvider,
    isAutoExpanding = isAutoExpanding,
    itemInterceptor = ::defaultExpandableModelItemInterceptor,
    subItemsInitializer = subItemsInitializer,
    on = on,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

@Suppress("LongParameterList")
@PublishedApi
internal class ViewBindingExpandableModelItemDelegate<M : BM, BM, I, VB : ViewBinding>(
    type: Int,
    override val viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    isAutoExpanding: Boolean = true,
    on: (model: BM) -> Boolean,
    itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    subItemsInitializer: M.() -> List<BM> = { emptyList() },
    itemInitializer: (I.() -> Unit)? = null,
    delegateInitializer: ViewBindingModelItemViewHolder<M, I, VB>.() -> Unit
) : AbsExpandableModelItemDelegate<M, BM, I, ViewBindingModelItemViewHolder<M, I, VB>>(
    type = type,
    isAutoExpanding = isAutoExpanding,
    on = on,
    itemInterceptor = itemInterceptor,
    subItemsInitializer = subItemsInitializer,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
), ViewBindingModelItemVHCreator<M, I, VB> where I : AbsDelegationExpandableModelItem<M, I>

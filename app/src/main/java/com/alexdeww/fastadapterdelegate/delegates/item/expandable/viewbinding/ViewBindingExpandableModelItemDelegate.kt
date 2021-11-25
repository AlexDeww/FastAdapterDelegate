package com.alexdeww.fastadapterdelegate.delegates.item.expandable.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsExpandableModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.viewbinding.ViewBindingModelItemVHCreator
import com.alexdeww.fastadapterdelegate.delegates.item.viewbinding.ViewBindingModelItemViewHolder

@Suppress("LongParameterList")
inline fun <reified M : BM, BM, I, VB : ViewBinding> customExpandableModelItemDelegateViewBinding(
    type: Int,
    noinline viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    isAutoExpanding: Boolean = true,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline subItemsInitializer: M.() -> List<BM> = { emptyList() },
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingModelItemViewHolder<M, I, VB>.() -> Unit
): ModelItemDelegate<BM> where I : AbsViewBindingExpandableModelItem<M, I, VB> {
    return ViewBindingExpandableModelItemDelegate(
        type = type,
        viewBinding = viewBinding,
        isAutoExpanding = isAutoExpanding,
        on = { model: BM -> model is M },
        itemInterceptor = itemInterceptor,
        subItemsInitializer = subItemsInitializer,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer,
    )
}

@PublishedApi
internal class ViewBindingExpandableModelItemDelegate<M : BM, BM, I, VB : ViewBinding>(
    type: Int,
    override val viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
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
), ViewBindingModelItemVHCreator<M, I, VB> where I : AbsViewBindingExpandableModelItem<M, I, VB>

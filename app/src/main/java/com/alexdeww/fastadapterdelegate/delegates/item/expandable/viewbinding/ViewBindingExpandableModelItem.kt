package com.alexdeww.fastadapterdelegate.delegates.item.expandable.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.viewbinding.ViewBindingModelItemViewHolder

typealias ViewBindingExpandableModelItemVH<M, VM> = ViewBindingModelItemViewHolder<M, ViewBindingExpandableModelItem<M, VM>, VM>

inline fun <reified M : BM, BM, VB : ViewBinding> expandableModelItemDelegateViewBinding(
    @IdRes type: Int,
    noinline viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    isAutoExpanding: Boolean = true,
    noinline subItemsInitializer: M.() -> List<BM> = { emptyList() },
    noinline itemInitializer: (ViewBindingExpandableModelItem<M, VB>.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingExpandableModelItemVH<M, VB>.() -> Unit
): ModelItemDelegate<BM> = customExpandableModelItemDelegateViewBinding(
    type = type,
    viewBinding = viewBinding,
    isAutoExpanding = isAutoExpanding,
    itemInterceptor = ::viewBindingExpandableModelItemInterceptor,
    subItemsInitializer = subItemsInitializer,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

class ViewBindingExpandableModelItem<M, VB : ViewBinding>(model: M) :
    AbsViewBindingExpandableModelItem<M, ViewBindingExpandableModelItem<M, VB>, VB>(model)

@PublishedApi
internal fun <M : BM, BM, VB : ViewBinding> viewBindingExpandableModelItemInterceptor(
    model: M,
    @Suppress("UNUSED_PARAMETER") delegates: List<ModelItemDelegate<BM>>
): ViewBindingExpandableModelItem<M, VB> = ViewBindingExpandableModelItem(model)

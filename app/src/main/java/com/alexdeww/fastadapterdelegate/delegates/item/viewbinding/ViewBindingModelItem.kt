package com.alexdeww.fastadapterdelegate.delegates.item.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate

typealias ViewBindingModelItemVH<M, VB> = ViewBindingModelItemViewHolder<M, ViewBindingModelItem<M, VB>, VB>

inline fun <reified M : BM, BM, VB : ViewBinding> modeItemDelegateViewBinding(
    @IdRes type: Int,
    noinline viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline itemInitializer: (ViewBindingModelItem<M, VB>.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingModelItemVH<M, VB>.() -> Unit
): ModelItemDelegate<BM> = customModeItemDelegateViewBinding(
    viewBinding = viewBinding,
    type = type,
    itemInterceptor = ::viewBindingModelItemInterceptor,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

inline fun <reified M, VB : ViewBinding> modeItemDelegateViewBindingSimple(
    @IdRes type: Int,
    noinline viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline itemInitializer: (ViewBindingModelItem<M, VB>.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingModelItemVH<M, VB>.() -> Unit
): ModelItemDelegate<M> = modeItemDelegateViewBinding(
    viewBinding = viewBinding,
    type = type,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

class ViewBindingModelItem<M, VB : ViewBinding>(model: M) :
    AbsViewBindingModelItem<M, ViewBindingModelItem<M, VB>, VB>(model)

@PublishedApi
internal fun <M : BM, BM, VB : ViewBinding> viewBindingModelItemInterceptor(
    model: M,
    @Suppress("UNUSED_PARAMETER") delegates: List<ModelItemDelegate<BM>>
): ViewBindingModelItem<M, VB> = ViewBindingModelItem(model)

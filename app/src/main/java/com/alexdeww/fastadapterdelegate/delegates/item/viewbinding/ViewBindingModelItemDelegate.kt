package com.alexdeww.fastadapterdelegate.delegates.item.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.DefaultModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.defaultModelItemInterceptor

/**
 * Создать делегат для кастомного элемента.
 *
 * @param BM Базовый тип модели
 * @param M Тип модели с базовым типом [BM], за которую отвечает этот делегат
 * @param I Тип элемента для модели [M]
 * @param viewBindingProvider
 * @param type уникальный тип для этого элемента
 * @param itemInterceptor Функция которая возвращаеят элемент [I], вызывается когда данные были
 * переданны в адаптер списка
 * @param itemInitializer Инициализация итема, вызывается сразу после [itemInterceptor]
 * @param delegateInitializer Блок инициализации, в нем задаются методы bind/unbind
 */
@Suppress("LongParameterList")
inline fun <reified M : BM, BM, I, VB : ViewBinding> customModeItemDelegateViewBinding(
    @IdRes type: Int,
    noinline viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline on: (model: BM) -> Boolean = { model: BM -> model is M },
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingModelItemViewHolder<M, I, VB>.() -> Unit
): ModelItemDelegate<BM> where I : AbsDelegationModelItem<M, I> {
    return ViewBindingModelItemDelegate(
        viewBindingProvider = viewBindingProvider,
        type = type,
        on = on,
        itemInterceptor = itemInterceptor,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

@Suppress("LongParameterList")
inline fun <reified M, I, VB : ViewBinding> customModeItemDelegateViewBindingSimple(
    @IdRes type: Int,
    noinline viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<M>>) -> I,
    noinline on: (model: M) -> Boolean = { true },
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingModelItemViewHolder<M, I, VB>.() -> Unit
): ModelItemDelegate<M> where I : AbsDelegationModelItem<M, I> {
    return customModeItemDelegateViewBinding(
        viewBindingProvider = viewBindingProvider,
        type = type,
        itemInterceptor = itemInterceptor,
        on = on,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

typealias ViewBindingDefaultModelItemVH<M, VB> = ViewBindingModelItemViewHolder<M, DefaultModelItem<M>, VB>

inline fun <reified M : BM, BM, VB : ViewBinding> modeItemDelegateViewBinding(
    @IdRes type: Int,
    noinline viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline on: (model: BM) -> Boolean = { model: BM -> model is M },
    noinline itemInitializer: (DefaultModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingDefaultModelItemVH<M, VB>.() -> Unit
): ModelItemDelegate<BM> = customModeItemDelegateViewBinding(
    viewBindingProvider = viewBindingProvider,
    type = type,
    itemInterceptor = ::defaultModelItemInterceptor,
    on = on,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

inline fun <reified M, VB : ViewBinding> modeItemDelegateViewBindingSimple(
    @IdRes type: Int,
    noinline viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline on: (model: M) -> Boolean = { true },
    noinline itemInitializer: (DefaultModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingDefaultModelItemVH<M, VB>.() -> Unit
): ModelItemDelegate<M> = modeItemDelegateViewBinding(
    viewBindingProvider = viewBindingProvider,
    type = type,
    on = on,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

@PublishedApi
internal class ViewBindingModelItemDelegate<M : BM, BM, I, VB : ViewBinding>(
    override val viewBindingProvider: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    type: Int,
    on: (model: BM) -> Boolean,
    itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    itemInitializer: (I.() -> Unit)? = null,
    delegateInitializer: ViewBindingModelItemViewHolder<M, I, VB>.() -> Unit
) : AbsModelItemDelegate<M, BM, I, ViewBindingModelItemViewHolder<M, I, VB>>(
    type = type,
    on = on,
    itemInterceptor = itemInterceptor,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
), ViewBindingModelItemVHCreator<M, I, VB> where I : AbsDelegationModelItem<M, I>

package com.alexdeww.fastadapterdelegate.delegates.item.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsModelItemDelegate

/**
 * Создать делегат для кастомного элемента.
 *
 * @param BM Базовый тип модели
 * @param M Тип модели с базовым типом [BM], за которую отвечает этот делегат
 * @param I Тип элемента для модели [M]
 * @param viewBinding
 * @param type уникальный тип для этого элемента
 * @param itemInterceptor Функция которая возвращаеят элемент [I], вызывается когда данные были
 * переданны в адаптер списка
 * @param itemInitializer Инициализация итема, вызывается сразу после [itemInterceptor]
 * @param delegateInitializer Блок инициализации, в нем задаются методы bind/unbind
 */
inline fun <reified M : BM, BM, I, VB : ViewBinding> customModeItemDelegateViewBinding(
    @IdRes type: Int,
    noinline viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingModelItemViewHolder<M, I, VB>.() -> Unit
): ModelItemDelegate<BM> where I : AbsViewBindingModelItem<M, I, VB> {
    return ViewBindingModelItemDelegate(
        viewBinding = viewBinding,
        type = type,
        on = { model: BM -> model is M },
        itemInterceptor = itemInterceptor,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

inline fun <reified M, I, VB : ViewBinding> customModeItemDelegateViewBindingSimple(
    @IdRes type: Int,
    noinline viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<M>>) -> I,
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: ViewBindingModelItemViewHolder<M, I, VB>.() -> Unit
): ModelItemDelegate<M> where I : AbsViewBindingModelItem<M, I, VB> {
    return customModeItemDelegateViewBinding(
        viewBinding = viewBinding,
        type = type,
        itemInterceptor = itemInterceptor,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

@PublishedApi
internal class ViewBindingModelItemDelegate<M : BM, BM, I, VB : ViewBinding>(
    override val viewBinding: (layoutInflater: LayoutInflater, parent: ViewGroup) -> VB,
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
), ViewBindingModelItemVHCreator<M, I, VB> where I : AbsViewBindingModelItem<M, I, VB>

package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsModelItemDelegate

/**
 * Создать делегат для кастомного элемента.
 *
 * @param BM Базовый тип модели
 * @param M Тип модели с базовым типом [BM], за которую отвечает этот делегат
 * @param I Тип элемента для модели [M]
 * @param layoutId
 * @param type уникальный тип для этого элемента
 * @param itemInterceptor Функция которая возвращаеят элемент [I], вызывается когда данные были
 * переданны в адаптер списка
 * @param itemInitializer Инициализация итема, вызывается сразу после [itemInterceptor]
 * @param delegateInitializer Блок инициализации, в нем задаются методы bind/unbind
 */
inline fun <reified M : BM, BM, I> customModeItemDelegateLayoutContainer(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerModelItemViewHolder<M, I>.() -> Unit
): ModelItemDelegate<BM> where I : AbsLayoutContainerModelItem<M, I> {
    return LayoutContainerModelItemDelegate(
        type = type,
        layoutId = layoutId,
        on = { model: BM -> model is M },
        itemInterceptor = itemInterceptor,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

inline fun <reified M, I> customModeItemDelegateLayoutContainerSimple(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<M>>) -> I,
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerModelItemViewHolder<M, I>.() -> Unit
): ModelItemDelegate<M> where I : AbsLayoutContainerModelItem<M, I> {
    return customModeItemDelegateLayoutContainer(
        layoutId = layoutId,
        type = type,
        itemInterceptor = itemInterceptor,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

@PublishedApi
internal class LayoutContainerModelItemDelegate<M : BM, BM, I>(
    type: Int,
    override val layoutId: Int,
    on: (model: BM) -> Boolean,
    itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    itemInitializer: (I.() -> Unit)? = null,
    delegateInitializer: LayoutContainerModelItemViewHolder<M, I>.() -> Unit
) : AbsModelItemDelegate<M, BM, I, LayoutContainerModelItemViewHolder<M, I>>(
    type = type,
    on = on,
    itemInterceptor = itemInterceptor,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
), LayoutContainerModelItemVHCreator<M, I> where I : AbsLayoutContainerModelItem<M, I>

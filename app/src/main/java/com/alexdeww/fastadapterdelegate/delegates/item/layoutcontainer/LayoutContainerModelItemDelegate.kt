package com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
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
 * @param layoutId
 * @param type уникальный тип для этого элемента
 * @param itemInterceptor Функция которая возвращаеят элемент [I], вызывается когда данные были
 * переданны в адаптер списка
 * @param itemInitializer Инициализация итема, вызывается сразу после [itemInterceptor]
 * @param delegateInitializer Блок инициализации, в нем задаются методы bind/unbind
 */
@Suppress("LongParameterList")
inline fun <reified M : BM, BM, I> customModeItemDelegateLayoutContainer(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline on: (model: BM) -> Boolean = { model: BM -> model is M },
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerModelItemViewHolder<M, I>.() -> Unit
): ModelItemDelegate<BM> where I : AbsDelegationModelItem<M, I> {
    return LayoutContainerModelItemDelegate(
        type = type,
        layoutId = layoutId,
        on = on,
        itemInterceptor = itemInterceptor,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

@Suppress("LongParameterList")
inline fun <reified M, I> customModeItemDelegateLayoutContainerSimple(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    noinline itemInterceptor: (model: M, delegates: List<ModelItemDelegate<M>>) -> I,
    noinline on: (model: M) -> Boolean = { true },
    noinline itemInitializer: (I.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerModelItemViewHolder<M, I>.() -> Unit
): ModelItemDelegate<M> where I : AbsDelegationModelItem<M, I> {
    return customModeItemDelegateLayoutContainer(
        layoutId = layoutId,
        type = type,
        itemInterceptor = itemInterceptor,
        on = on,
        itemInitializer = itemInitializer,
        delegateInitializer = delegateInitializer
    )
}

typealias LayoutContainerDefaultModelItemVH<M> = LayoutContainerModelItemViewHolder<M, DefaultModelItem<M>>

inline fun <reified M : BM, BM> modeItemDelegateLayoutContainer(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    noinline on: (model: BM) -> Boolean = { model: BM -> model is M },
    noinline itemInitializer: (DefaultModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerDefaultModelItemVH<M>.() -> Unit
): ModelItemDelegate<BM> = customModeItemDelegateLayoutContainer(
    type = type,
    layoutId = layoutId,
    itemInterceptor = ::defaultModelItemInterceptor,
    on = on,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

inline fun <reified M> modeItemDelegateLayoutContainerSimple(
    @IdRes type: Int,
    @LayoutRes layoutId: Int,
    noinline on: (model: M) -> Boolean = { true },
    noinline itemInitializer: (DefaultModelItem<M>.() -> Unit)? = null,
    noinline delegateInitializer: LayoutContainerDefaultModelItemVH<M>.() -> Unit
): ModelItemDelegate<M> = modeItemDelegateLayoutContainer(
    type = type,
    layoutId = layoutId,
    on = on,
    itemInitializer = itemInitializer,
    delegateInitializer = delegateInitializer
)

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
), LayoutContainerModelItemVHCreator<M, I> where I : AbsDelegationModelItem<M, I>

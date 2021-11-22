package com.alexdeww.fastadapterdelegate.delegates.item

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.customModeItemDelegateEx
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.AbsLayoutContainerModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerModelItemVH
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerModelItemViewHolder

/**
 * Создать делегат для элемента.
 *
 * @param BM Базовый тип модели
 * @param M Тип модели, за которую отвечает этот делегат
 * @param I Тип элемента для модели [M]
 * @param layoutId
 * @param type уникальный тип для этого элемента
 * @param initItem Инициализация итема, вызывается сразу после [initializeBlock]
 * @param initializeBlock Блок инициализации, в нем задаются методы bind/unbind, вызывается
 */
inline fun <reified M : BM, BM, I : AbsLayoutContainerModelItem<M, I>> modeItemDelegateEx(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline interceptItem: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline initItem: (I.() -> Unit)? = null,
    noinline initializeBlock: LayoutContainerModelItemViewHolder<M, I>.() -> Unit
): ModelItemDelegate<BM> = customModeItemDelegateEx(
    layoutId = layoutId,
    type = type,
    interceptItem = interceptItem,
    initItem = initItem,
    initializeBlock = initializeBlock
)

/**
 * Создать делегат для элемента. Без базовой модели. Базовой моделью будет являться [M]
 *
 * @param M Тип модели, за которую отвечает этот делегат
 * @param layoutId
 * @param type уникальный тип для этого элемента
 * @param initItem Инициализация итема, вызывается сразу после [initializeBlock]
 * @param initializeBlock Блок инициализации, в нем задаются методы bind/unbind, вызывается
 */
inline fun <reified M : BM, BM> modeItemDelegate(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline initItem: (LayoutContainerModelItem<M>.() -> Unit)? = null,
    noinline initializeBlock: LayoutContainerModelItemVH<M>.() -> Unit
): ModelItemDelegate<BM> = modeItemDelegateEx(
    layoutId = layoutId,
    type = type,
    interceptItem = { model, _ -> LayoutContainerModelItem(model) },
    initItem = initItem,
    initializeBlock = initializeBlock
)

inline fun <reified M> modeItemDelegateSimple(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline initItem: (LayoutContainerModelItem<M>.() -> Unit)? = null,
    noinline initializeBlock: LayoutContainerModelItemVH<M>.() -> Unit
): ModelItemDelegate<M> = modeItemDelegate(
    layoutId = layoutId,
    type = type,
    initItem = initItem,
    initializeBlock = initializeBlock
)

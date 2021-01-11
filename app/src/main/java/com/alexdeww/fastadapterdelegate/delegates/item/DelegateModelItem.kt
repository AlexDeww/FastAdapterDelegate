package com.alexdeww.fastadapterdelegate.delegates.item

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.DelegateModelAbstractItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.DelegateModelItemViewHolder
import com.alexdeww.fastadapterdelegate.delegates.item.common.customModeItemDelegateEx

class DelegateModelItem<M>(model: M) : DelegateModelAbstractItem<M, DelegateModelItem<M>>(model)

/**
 * Создать делегат для элемента [DelegateModelItem].
 *
 * @param BM Базовый тип модели
 * @param M Тип модели, за которую отвечает этот делегат
 * @param layoutId
 * @param type уникальный тип для этого элемента
 * @param initItem Инициализация итема, вызывается сразу после [initializeBlock]
 * @param initializeBlock Блок инициализации, в нем задаются методы bind/unbind, вызывается
 */
inline fun <reified M : BM, BM> modeItemDelegateEx(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline initItem: (DelegateModelItem<M>.() -> Unit)? = null,
    noinline initializeBlock: DelegateModelItemViewHolder<M, DelegateModelItem<M>>.() -> Unit
): ModelItemDelegate<BM> = customModeItemDelegateEx(
    layoutId,
    type,
    { model, _ -> DelegateModelItem(model) },
    initItem,
    initializeBlock
)

/**
 * Создать делегат для элемента [DelegateModelItem]. Без базовой модели. Базовой моделью будет являться [M]
 *
 * @param M Тип модели, за которую отвечает этот делегат
 * @param layoutId
 * @param type уникальный тип для этого элемента
 * @param initItem Инициализация итема, вызывается сразу после [initializeBlock]
 * @param initializeBlock Блок инициализации, в нем задаются методы bind/unbind, вызывается
 */
inline fun <reified M> modeItemDelegate(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline initItem: (DelegateModelItem<M>.() -> Unit)? = null,
    noinline initializeBlock: DelegateModelItemViewHolder<M, DelegateModelItem<M>>.() -> Unit
): ModelItemDelegate<M> = modeItemDelegateEx(
    layoutId,
    type,
    initItem,
    initializeBlock
)

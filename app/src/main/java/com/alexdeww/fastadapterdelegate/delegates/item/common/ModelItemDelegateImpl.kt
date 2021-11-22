package com.alexdeww.fastadapterdelegate.delegates.item.common

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem.ViewHolder

/**
 * Базовый делегат, для всех текущих и будущих делегатов.
 *
 * @param BM Базовый тип модели, которую может хранить элемент
 * @param M Тип модели с базовым типом [BM], за которую отвечает этот делегат
 * @param I Тип элемента для модели [M]
 * @param layoutId
 * @param type уникальный тип для этого элемента
 * @param on Проверка, которая должна быть выполнена, если Delegate отвечает за предоставленню ему модель
 * @param interceptItem Функция которая возвращаеят элемент [I], вызывается когда данные были
 * переданны в адаптер
 * @param initItem Инициализация элемента, вызывается сразу после [initializeBlock]
 * @param initializeBlock Блок инициализации, в нем задаются методы bind/unbind, вызывается
 * сразу после [interceptItem]
 */
class ModelItemDelegateImpl<M : BM, BM, I : AbsDelegationModelItem<M, I, VH>, VH>(
    @LayoutRes private val layoutId: Int,
    @IdRes private val type: Int,
    private val on: (model: BM) -> Boolean,
    private val interceptItem: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    private val initItem: (I.() -> Unit)? = null,
    private val initializeBlock: VH.() -> Unit
) : ModelItemDelegate<BM> where VH : ViewHolder<M, I> {

    override fun isForViewType(model: BM): Boolean = on.invoke(model)

    @Suppress("UNCHECKED_CAST")
    override fun intercept(
        model: BM,
        delegates: List<ModelItemDelegate<BM>>
    ): GenericDelegationModelItem<BM> = interceptItem
        .invoke(model as M, delegates)
        .also { modelItem ->
            modelItem.setParams(layoutId, type, initializeBlock)
            initItem?.invoke(modelItem)
        }

}

/**
 * Создать делегат для кастомного элемента.
 *
 * @param BM Базовый тип модели
 * @param M Тип модели с базовым типом [BM], за которую отвечает этот делегат
 * @param I Тип элемента для модели [M]
 * @param layoutId
 * @param type уникальный тип для этого элемента
 * @param interceptItem Функция которая возвращаеят элемент [I], вызывается когда данные были
 * переданны в адаптер списка
 * @param initItem Инициализация итема, вызывается сразу после [initializeBlock]
 * @param initializeBlock Блок инициализации, в нем задаются методы bind/unbind, вызывается
 */
inline fun <reified M : BM, BM, I : AbsDelegationModelItem<M, I, VH>, VH> customModeItemDelegateEx(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline interceptItem: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline initItem: (I.() -> Unit)? = null,
    noinline initializeBlock: VH.() -> Unit
): ModelItemDelegate<BM> where VH : ViewHolder<M, I> = ModelItemDelegateImpl(
    layoutId = layoutId,
    type = type,
    on = { model: BM -> model is M },
    interceptItem = interceptItem,
    initItem = initItem,
    initializeBlock = initializeBlock
)

/**
 * Создать делегат для кастомного итема. Без базовой модели. Базовой моделью будет являться [M]
 *
 * @param M Тип модели, за которую отвечает этот делегат
 * @param I Тип итема для модели [M]
 * @param layoutId
 * @param type уникальный тип для этого итема
 * @param interceptItem Функция которая возвращаеят итем [I], вызывается когда данные были
 * переданны в адаптер
 * @param initItem Инициализация элемента, вызывается сразу после [initializeBlock]
 * @param initializeBlock Блок инициализации, в нем задаются методы bind/unbind, вызывается
 */
inline fun <reified M, I : AbsDelegationModelItem<M, I, VH>, VH> customModeItemDelegate(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline interceptItem: (model: M, delegates: List<ModelItemDelegate<M>>) -> I,
    noinline initItem: (I.() -> Unit)? = null,
    noinline initializeBlock: VH.() -> Unit
): ModelItemDelegate<M> where VH : ViewHolder<M, I> = customModeItemDelegateEx(
    layoutId = layoutId,
    type = type,
    interceptItem = interceptItem,
    initItem = initItem,
    initializeBlock = initializeBlock
)

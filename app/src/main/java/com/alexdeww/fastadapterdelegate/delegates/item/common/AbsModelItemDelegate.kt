package com.alexdeww.fastadapterdelegate.delegates.item.common

import android.view.ViewGroup
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem.ViewHolder
import com.mikepenz.fastadapter.IItemVHFactory

/**
 * Базовый делегат, для всех текущих и будущих делегатов.
 *
 * @param BM Базовый тип модели, которую может хранить элемент
 * @param M Тип модели с базовым типом [BM], за которую отвечает этот делегат
 * @param I Тип элемента для модели [M]
 * @param type уникальный тип для этого элемента
 * @param on Проверка, которая должна быть выполнена, если Delegate отвечает за предоставленню ему модель
 * @param itemInterceptor Функция которая возвращаеят элемент [I], вызывается когда данные были
 * переданны в адаптер
 * @param itemInitializer Инициализация элемента, вызывается сразу после [itemInterceptor]
 * @param delegateInitializer Блок инициализации, в нем задаются методы bind/unbind
 */
abstract class AbsModelItemDelegate<M : BM, BM, I, VH>(
    private val type: Int,
    private val on: (model: BM) -> Boolean,
    private val itemInterceptor: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    private val itemInitializer: (I.() -> Unit)? = null,
    private val delegateInitializer: VH.() -> Unit
) : ModelItemDelegate<BM>, IItemVHFactory<VH>,
    ModelItemVHCreator<VH> where I : AbsDelegationModelItem<M, I>,
                                 VH : ViewHolder<M, I> {

    final override fun isForViewType(model: BM): Boolean = on.invoke(model)

    final override fun intercept(
        model: BM,
        delegates: List<ModelItemDelegate<BM>>
    ): GenericDelegationModelItem<BM> {
        @Suppress("UNCHECKED_CAST")
        val item = itemInterceptor(model as M, delegates)
        item.type = type
        item.viewHolderFactory = this
        internalInitializeItem(item, delegates)
        itemInitializer?.invoke(item)
        return item
    }

    final override fun getViewHolder(parent: ViewGroup): VH =
        createViewHolder(parent).apply(delegateInitializer)

    protected open fun internalInitializeItem(item: I, delegates: List<ModelItemDelegate<BM>>) {
        // empty
    }

}

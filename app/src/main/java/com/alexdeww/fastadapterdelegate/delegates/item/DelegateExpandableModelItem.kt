package com.alexdeww.fastadapterdelegate.delegates.item

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.IExpandable
import com.mikepenz.fastadapter.ISubItem
import com.mikepenz.fastadapter.MutableSubItemList
import com.mikepenz.fastadapter.items.ModelAbstractItem
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.DelegateModelAbstractItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.DelegateModelItemViewHolder
import com.alexdeww.fastadapterdelegate.delegates.item.common.customModeItemDelegateEx

typealias GenericExpandableModelItem<M> = DelegateExpandableModelAbstractItem<M, *>

abstract class DelegateExpandableModelAbstractItem<M, I : ModelAbstractItem<M, *>>(
    model: M,
    override val isAutoExpanding: Boolean = true
) : DelegateModelAbstractItem<M, I>(model), IExpandable<RecyclerView.ViewHolder> {
    override var isExpanded: Boolean = false

    @Suppress("LeakingThis")
    override var subItems: MutableList<ISubItem<*>> = MutableSubItemList(this)

    @Suppress("UNUSED_PARAMETER")
    override var isSelectable: Boolean
        get() = subItems.isEmpty()
        set(value) {}
}

class DelegateExpandableModelItem<M>(
    model: M,
    isAutoExpanding: Boolean = true
) : DelegateExpandableModelAbstractItem<M, DelegateExpandableModelItem<M>>(model, isAutoExpanding)

@Suppress("LongParameterList")
inline fun <reified M : BM, BM, I : GenericExpandableModelItem<M>> expandableModelItemDelegateEx(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline interceptItem: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline initSubItems: M.() -> List<BM> = { emptyList() },
    noinline initItem: (I.() -> Unit)? = null,
    noinline initializeBlock: DelegateModelItemViewHolder<M, I>.() -> Unit
): ModelItemDelegate<BM> = customModeItemDelegateEx(
    layoutId,
    type,
    { model, delegates ->
        interceptItem.invoke(model, delegates).also {
            val subItems = initSubItems.invoke(it.model)
                .mapNotNull { m ->
                    delegates
                        .find { delegate -> delegate.isForViewType(m) }
                        ?.intercept(m, delegates)
                }
            it.subItems.addAll(subItems)
        }
    },
    initItem,
    initializeBlock
)

/**
 * Создает делегат для расскрывающегося элемента списка.
 *
 * @param BM Базовый тип модели данных для элемента
 * @param M Тип модели с базовым типом [BM] для элемента
 */
inline fun <reified M : BM, BM> expandableModelItemDelegate(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    isAutoExpanding: Boolean = true,
    noinline initSubItems: M.() -> List<BM> = { emptyList() },
    noinline initItem: (DelegateExpandableModelItem<M>.() -> Unit)? = null,
    noinline initializeBlock: DelegateModelItemViewHolder<M, DelegateExpandableModelItem<M>>.() -> Unit
) = expandableModelItemDelegateEx(
    layoutId,
    type,
    { model, _ -> DelegateExpandableModelItem(model, isAutoExpanding) },
    initSubItems,
    initItem,
    initializeBlock
)

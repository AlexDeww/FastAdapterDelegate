package com.alexdeww.fastadapterdelegate.delegates.item

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.expandable.AbsDelegationExpandableModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.common.customModeItemDelegateEx
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerExpandableModelItem
import com.alexdeww.fastadapterdelegate.delegates.item.layoutcontainer.LayoutContainerExpandableModelItemVH

@Suppress("LongParameterList")
inline fun <reified M : BM, BM, I : AbsDelegationExpandableModelItem<M, I, VH>, VH> customExpandableModelItemDelegateEx(
    @LayoutRes layoutId: Int,
    @IdRes type: Int,
    noinline interceptItem: (model: M, delegates: List<ModelItemDelegate<BM>>) -> I,
    noinline initSubItems: M.() -> List<BM> = { emptyList() },
    noinline initItem: (I.() -> Unit)? = null,
    noinline initializeBlock: VH.() -> Unit
): ModelItemDelegate<BM> where VH : AbsDelegationModelItem.ViewHolder<M, I> =
    customModeItemDelegateEx<M, BM, I, VH>(
        layoutId = layoutId,
        type = type,
        interceptItem = { model, delegates ->
            interceptItem.invoke(model, delegates).also {
                val subItems = initSubItems.invoke(it.model).mapNotNull { subModel ->
                    delegates
                        .find { delegate -> delegate.isForViewType(subModel) }
                        ?.intercept(subModel, delegates)
                }
                it.subItems.addAll(subItems)
            }
        },
        initItem = initItem,
        initializeBlock = initializeBlock
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
    noinline initItem: (LayoutContainerExpandableModelItem<M>.() -> Unit)? = null,
    noinline initializeBlock: LayoutContainerExpandableModelItemVH<M>.() -> Unit
) = customExpandableModelItemDelegateEx(
    layoutId = layoutId,
    type = type,
    interceptItem = { model, _ -> LayoutContainerExpandableModelItem(model, isAutoExpanding) },
    initSubItems = initSubItems,
    initItem = initItem,
    initializeBlock = initializeBlock
)

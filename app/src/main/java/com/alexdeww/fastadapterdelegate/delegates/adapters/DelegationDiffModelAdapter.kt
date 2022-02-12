package com.alexdeww.fastadapterdelegate.delegates.adapters

import androidx.recyclerview.widget.DiffUtil
import com.alexdeww.fastadapterdelegate.adapters.DiffModelAdapter
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegationModelItem
import com.alexdeww.fastadapterdelegate.wrappers.ModelItemDiffCallbackWrapper

/**
 * Адаптер.
 *
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 * @param diffCallback diffUtilItemCallback()
 */
open class DelegationDiffModelAdapter<BaseModel : Any>(
    protected val delegates: List<ModelItemDelegate<BaseModel>>,
    diffCallback: DiffUtil.ItemCallback<BaseModel>
) : DiffModelAdapter<BaseModel, GenericDelegationModelItem<BaseModel>>(
    ModelItemDiffCallbackWrapper(diffCallback),
    { model -> delegates.find { it.isForViewType(model) }?.intercept(model, delegates) }
)

/**
 * Адаптер.
 *
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 * @param diffCallback diffUtilItemCallback()
 */
fun <BaseModel : Any> delegationDiffModelAdapter(
    diffCallback: DiffUtil.ItemCallback<BaseModel>,
    vararg delegates: ModelItemDelegate<BaseModel>
) = DelegationDiffModelAdapter(delegates.asList(), diffCallback)

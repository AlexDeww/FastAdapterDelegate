package com.alexdeww.fastadapterdelegate.delegates.adapters

import androidx.recyclerview.widget.DiffUtil
import com.alexdeww.fastadapterdelegate.adapters.DiffModelAdapter
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegationModelItem
import com.alexdeww.fastadapterdelegate.wrappers.ModelItemDiffCallbackWrapper

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 * @param diffCallback diffUtilItemCallback()
 */
open class DelegationDiffModelAdapter<Model : BaseModel, BaseModel : Any>(
    protected val delegates: List<ModelItemDelegate<BaseModel>>,
    diffCallback: DiffUtil.ItemCallback<BaseModel>
) : DiffModelAdapter<Model, GenericDelegationModelItem<BaseModel>>(
    ModelItemDiffCallbackWrapper(diffCallback),
    { model -> delegates.find { it.isForViewType(model) }?.intercept(model, delegates) }
)

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 * @param diffCallback diffUtilItemCallback()
 */
fun <Model : BaseModel, BaseModel : Any> delegationDiffModelAdapter(
    diffCallback: DiffUtil.ItemCallback<BaseModel>,
    vararg delegates: ModelItemDelegate<BaseModel>
) = DelegationDiffModelAdapter<Model, BaseModel>(delegates.asList(), diffCallback)

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param delegates список делегатов
 * @param diffCallback diffUtilItemCallback()
 */
fun <Model : Any> delegationDiffModelAdapterSimple(
    diffCallback: DiffUtil.ItemCallback<Model>,
    vararg delegates: ModelItemDelegate<Model>
) = delegationDiffModelAdapter(diffCallback, *delegates)

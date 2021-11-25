package com.alexdeww.fastadapterdelegate.delegates.adapters

import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegationModelItem
import com.mikepenz.fastadapter.adapters.ModelAdapter

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 */
open class DelegationModelItemAdapter<Model : BaseModel, BaseModel>(
    protected val delegates: List<ModelItemDelegate<BaseModel>>
) : ModelAdapter<BaseModel, GenericDelegationModelItem<BaseModel>>(
    { model -> delegates.find { it.isForViewType(model) }?.intercept(model, delegates) }
)

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 */
fun <Model : BaseModel, BaseModel> delegationModelItemAdapter(
    vararg delegates: ModelItemDelegate<BaseModel>
) = DelegationModelItemAdapter<Model, BaseModel>(delegates.asList())

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param delegates список делегатов
 */
fun <Model> delegationModelItemAdapterSimple(
    vararg delegates: ModelItemDelegate<Model>
) = delegationModelItemAdapter(*delegates)

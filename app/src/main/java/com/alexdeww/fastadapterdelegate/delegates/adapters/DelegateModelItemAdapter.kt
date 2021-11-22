package com.alexdeww.fastadapterdelegate.delegates.adapters

import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegationModelItem

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 */
open class DelegateModelItemAdapter<Model : BaseModel, BaseModel>(
    protected val delegates: List<ModelItemDelegate<BaseModel>>
) : ModelAdapter<BaseModel, GenericDelegationModelItem<BaseModel>>(
    { model -> delegates.find { it.isForViewType(model) }?.intercept(model, delegates) }
)

/**
 * Адаптер.
 *
 * @param BaseModel Базовый тип элементов которые может хранить этот адаптер
 * @param delegates список делегатов
 */
fun <BaseModel> delegateModelItemAdapter(
    vararg delegates: ModelItemDelegate<BaseModel>
) = DelegateModelItemAdapter(delegates.asList())

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 */
fun <Model : BaseModel, BaseModel> delegateModelItemAdapterEx(
    vararg delegates: ModelItemDelegate<BaseModel>
) = DelegateModelItemAdapter<Model, BaseModel>(delegates.asList())

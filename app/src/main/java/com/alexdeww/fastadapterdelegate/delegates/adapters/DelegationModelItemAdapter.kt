package com.alexdeww.fastadapterdelegate.delegates.adapters

import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegationModelItem
import com.mikepenz.fastadapter.adapters.ModelAdapter

/**
 * Адаптер.
 *
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 */
open class DelegationModelItemAdapter<BaseModel : Any>(
    protected val delegates: List<ModelItemDelegate<BaseModel>>
) : ModelAdapter<BaseModel, GenericDelegationModelItem<BaseModel>>(
    { model -> delegates.find { it.isForViewType(model) }?.intercept(model, delegates) }
)

/**
 * Адаптер.
 *
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 */
fun <BaseModel : Any> delegationModelItemAdapter(
    vararg delegates: ModelItemDelegate<BaseModel>
) = DelegationModelItemAdapter(delegates.asList())

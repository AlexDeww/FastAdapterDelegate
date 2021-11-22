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
open class DelegateDiffModelAdapter<Model : BaseModel, BaseModel : Any>(
    protected val delegates: List<ModelItemDelegate<BaseModel>>,
    diffCallback: DiffUtil.ItemCallback<BaseModel>
) : DiffModelAdapter<Model, GenericDelegationModelItem<BaseModel>>(
    ModelItemDiffCallbackWrapper(diffCallback),
    { model -> delegates.find { it.isForViewType(model) }?.intercept(model, delegates) }
)

/**
 * Адаптер.
 *
 * @param BaseModel Базовый тип элементов которые может хранить этот адаптер
 * @param delegates список делегатов
 * @param diffCallback diffUtilItemCallback()
 */
fun <BaseModel : Any> delegateDiffModelAdapter(
    diffCallback: DiffUtil.ItemCallback<BaseModel>,
    vararg delegates: ModelItemDelegate<BaseModel>
) = DelegateDiffModelAdapter(delegates.asList(), diffCallback)

/**
 * Адаптер.
 *
 * @param Model тип элементов которые может хранить этот адаптер
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 * @param diffCallback diffUtilItemCallback()
 */
fun <Model : BaseModel, BaseModel : Any> delegateDiffModelAdapterEx(
    diffCallback: DiffUtil.ItemCallback<BaseModel>,
    vararg delegates: ModelItemDelegate<BaseModel>
) = DelegateDiffModelAdapter<Model, BaseModel>(delegates.asList(), diffCallback)

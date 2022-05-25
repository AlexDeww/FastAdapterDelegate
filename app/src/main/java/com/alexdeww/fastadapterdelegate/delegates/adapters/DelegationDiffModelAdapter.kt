package com.alexdeww.fastadapterdelegate.delegates.adapters

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.alexdeww.fastadapterdelegate.adapters.AsyncDiffModelAdapter
import com.alexdeww.fastadapterdelegate.adapters.DiffModelAdapter
import com.alexdeww.fastadapterdelegate.delegates.ModelItemDelegate
import com.alexdeww.fastadapterdelegate.delegates.item.common.GenericDelegationModelItem

/**
 * Адаптер.
 *
 * @param BaseModel тип элементов которые может отображать этот адаптер
 * @param delegates список делегатов
 * @param diffCallback diffUtilItemCallback()
 */
open class DelegationDiffModelAdapter<BaseModel : Any>(
    delegates: List<ModelItemDelegate<BaseModel>>,
    diffCallback: DiffUtil.ItemCallback<BaseModel>
) : DiffModelAdapter<BaseModel, GenericDelegationModelItem<BaseModel>>(
    diffCallback = diffCallback,
    interceptor = { model ->
        delegates.find { it.isForViewType(model) }?.intercept(model, delegates)
    }
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
): DelegationDiffModelAdapter<BaseModel> = DelegationDiffModelAdapter(
    delegates = delegates.asList(),
    diffCallback = diffCallback
)


open class DelegationAsyncDiffModelAdapter<BaseModel : Any>(
    delegates: List<ModelItemDelegate<BaseModel>>,
    asyncDifferConfig: AsyncDifferConfig<BaseModel>,
) : AsyncDiffModelAdapter<BaseModel, GenericDelegationModelItem<BaseModel>>(
    asyncDifferConfig = asyncDifferConfig,
    interceptor = { model ->
        delegates.find { it.isForViewType(model) }?.intercept(model, delegates)
    }
) {

    constructor(
        delegates: List<ModelItemDelegate<BaseModel>>,
        diffCallback: DiffUtil.ItemCallback<BaseModel>
    ) : this(
        delegates = delegates,
        asyncDifferConfig = AsyncDifferConfig.Builder(diffCallback).build()
    )

}

fun <BaseModel : Any> delegationAsyncDiffModelAdapter(
    asyncDifferConfig: AsyncDifferConfig<BaseModel>,
    vararg delegates: ModelItemDelegate<BaseModel>
): DelegationAsyncDiffModelAdapter<BaseModel> = DelegationAsyncDiffModelAdapter(
    delegates = delegates.asList(),
    asyncDifferConfig = asyncDifferConfig
)

fun <BaseModel : Any> delegationAsyncDiffModelAdapter(
    diffCallback: DiffUtil.ItemCallback<BaseModel>,
    vararg delegates: ModelItemDelegate<BaseModel>
): DelegationAsyncDiffModelAdapter<BaseModel> = DelegationAsyncDiffModelAdapter(
    delegates = delegates.asList(),
    diffCallback = diffCallback
)

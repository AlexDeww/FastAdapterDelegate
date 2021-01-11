package com.alexdeww.fastadapterdelegate

import com.alexdeww.fastadapterdelegate.extensions.SwitchExtension
import com.mikepenz.fastadapter.*
import com.mikepenz.fastadapter.adapters.GenericModelAdapter
import com.mikepenz.fastadapter.expandable.ExpandableExtension
import com.mikepenz.fastadapter.expandable.expandableExtension
import com.mikepenz.fastadapter.select.SelectExtension
import com.mikepenz.fastadapter.select.selectExtension

fun <A : IAdapter<*>> fastAdapter(vararg adapters: A): FastAdapter<GenericItem> =
    FastAdapter.with(adapters.asList())

@Suppress("UNCHECKED_CAST")
fun FastAdapter<GenericItem>.setAdapters(
    adapters: List<IItemAdapter<*, *>>
): FastAdapter<GenericItem> {
    adapters.forEachIndexed { i, itemAdapter ->
        addAdapter(i, itemAdapter as GenericModelAdapter<GenericItem>)
    }
    return this
}

/**
 * Включает поддержку раскрывающихся элеметов
 */
fun <I : GenericItem, FA : FastAdapter<I>> FA.withExpandable(
    block: ExpandableExtension<I>.() -> Unit
): FA {
    expandableExtension(block)
    return this
}

/**
 * Включает поддержку выбора элементов
 */
fun <I : GenericItem, FA : FastAdapter<I>> FA.withSelectable(
    block: SelectExtension<I>.() -> Unit
): FA {
    selectExtension(block)
    return this
}

fun <I : GenericItem> SelectExtension<I>.selectionListener(
    action: (extension: SelectExtension<I>, item: I, selected: Boolean) -> Unit
) {
    selectionListener = object : ISelectionListener<I> {
        override fun onSelectionChanged(item: I, selected: Boolean) {
            action.invoke(this@selectionListener, item, selected)
        }
    }
}

fun <I : GenericItem, FA : FastAdapter<I>> FA.getSwitchExtension(): SwitchExtension<I> {
    SwitchExtension.toString() // enforces the vm to lead in the companion object
    return requireOrCreateExtension()
}

fun <I : GenericItem, FA : FastAdapter<I>> FA.withSwitchable(
    block: SwitchExtension<I>.() -> Unit
): FA {
    getSwitchExtension().apply(block)
    return this
}

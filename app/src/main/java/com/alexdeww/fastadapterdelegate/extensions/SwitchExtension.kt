package com.alexdeww.fastadapterdelegate.extensions

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.IAdapterExtension
import com.mikepenz.fastadapter.extensions.ExtensionFactory
import com.mikepenz.fastadapter.extensions.ExtensionsFactories
import java.util.*

class SwitchExtension<Item : GenericItem>(
    private val fastAdapter: FastAdapter<Item>
) : IAdapterExtension<Item> {

    companion object : ExtensionFactory<SwitchExtension<*>> {

        override val clazz: Class<SwitchExtension<*>> = SwitchExtension::class.java

        override fun create(
            fastAdapter: FastAdapter<out GenericItem>
        ): SwitchExtension<*> = SwitchExtension(fastAdapter)

        init {
            ExtensionsFactories.register(this)
        }

    }

    private var currentSelectionPos: Int = RecyclerView.NO_POSITION
    private val pendingDeselect = LinkedList<Int>()

    val selectedItemPosition: Int
        get() {
            if (currentSelectionPos > RecyclerView.NO_POSITION) return currentSelectionPos
            (0 until fastAdapter.itemCount).forEach { position ->
                if (fastAdapter.getItem(position)?.isSelected == true) pendingDeselect.add(position)
            }
            currentSelectionPos = pendingDeselect.poll() ?: RecyclerView.NO_POSITION
            return currentSelectionPos
        }
    val hasSelection: Boolean get() = selectedItemPosition > RecyclerView.NO_POSITION
    val selectedItem: Item? get() = fastAdapter.getItem(selectedItemPosition)

    var allowDeselect: Boolean = false
    var notifyItemChanges: Boolean = true

    var onSelectionChangedListener: ((globalPosition: Int, item: Item?) -> Unit)? = null

    override fun notifyAdapterDataSetChanged() {
        currentSelectionPos = RecyclerView.NO_POSITION
    }

    override fun notifyAdapterItemMoved(fromPosition: Int, toPosition: Int) {
        notifyAdapterDataSetChanged()
    }

    override fun notifyAdapterItemRangeChanged(position: Int, itemCount: Int, payload: Any?) {
        notifyAdapterDataSetChanged()
    }

    override fun notifyAdapterItemRangeInserted(position: Int, itemCount: Int) {
        notifyAdapterDataSetChanged()
    }

    override fun notifyAdapterItemRangeRemoved(position: Int, itemCount: Int) {
        notifyAdapterDataSetChanged()
    }

    override fun onClick(v: View, pos: Int, fastAdapter: FastAdapter<Item>, item: Item): Boolean {
        if (!item.isSelectable) return false

        if (!item.isSelected) {
            if (hasSelection && pos != selectedItemPosition) deselectCurrent()
            changeItemSelection(item, pos, true)
            currentSelectionPos = pos
            onSelectionChangedListener?.invoke(pos, item)
        } else if (item.isSelected && allowDeselect) {
            changeItemSelection(item, pos, false)
            currentSelectionPos = RecyclerView.NO_POSITION
            onSelectionChangedListener?.invoke(RecyclerView.NO_POSITION, null)
        }

        return false
    }

    override fun onLongClick(
        v: View,
        pos: Int,
        fastAdapter: FastAdapter<Item>,
        item: Item
    ): Boolean = false

    override fun onTouch(
        v: View,
        event: MotionEvent,
        position: Int,
        fastAdapter: FastAdapter<Item>,
        item: Item
    ): Boolean = false

    override fun performFiltering(constraint: CharSequence?) {}

    override fun saveInstanceState(savedInstanceState: Bundle?, prefix: String) {}

    override fun set(items: List<Item>, resetFilter: Boolean) {}

    override fun withSavedInstanceState(savedInstanceState: Bundle?, prefix: String) {}

    private fun deselectCurrent() {
        while (pendingDeselect.isNotEmpty()) {
            val pos = pendingDeselect.pop()
            val item = fastAdapter.getItem(pos) ?: continue
            changeItemSelection(item, pos, false)
        }
        selectedItem?.let { changeItemSelection(it, currentSelectionPos, false) }
    }

    private fun changeItemSelection(item: Item, position: Int, isSelected: Boolean) {
        if (item.isSelected == isSelected) return

        item.isSelected = isSelected
        if (notifyItemChanges) fastAdapter.notifyItemChanged(position, Unit)
    }

}

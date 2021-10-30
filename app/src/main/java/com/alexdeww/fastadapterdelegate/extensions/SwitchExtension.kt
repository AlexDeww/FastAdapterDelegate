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
import java.lang.reflect.Method
import kotlin.math.max

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

    private var currentSelectedItemPos: Int = RecyclerView.NO_POSITION
    private val adapterCacheSizesMethod: Method by lazy {
        FastAdapter::class.java
            .getDeclaredMethod("cacheSizes")
            .also { it.isAccessible = true }
    }

    val hasSelection: Boolean get() = currentSelectedItemPos > RecyclerView.NO_POSITION
    val selectedItemPosition: Int get() = currentSelectedItemPos
    val selectedItem: Item? get() = getItemByPosition(currentSelectedItemPos)

    var allowDeselect: Boolean = false
    var notifyItemChanges: Boolean = true

    /**
     * Событие вызывается когда меняется состояние итема isSelected,
     * globalPosition - Глобальная позиция заселекченного итема, -1 ничего не выбрано.
     */
    var onSelectionChangedListener: ((globalPosition: Int, item: Item?) -> Unit)? = null

    override fun notifyAdapterDataSetChanged() {
        requestAdapterCacheSizes()
        onItemRangeChanged(0, fastAdapter.itemCount)
    }

    override fun notifyAdapterItemMoved(fromPosition: Int, toPosition: Int) {
        if (!hasSelection) return

        requestAdapterCacheSizes()
        when (currentSelectedItemPos) {
            fromPosition -> setCurrentSelection(toPosition)
            toPosition -> setCurrentSelection(fromPosition)
        }
    }

    override fun notifyAdapterItemRangeChanged(position: Int, itemCount: Int, payload: Any?) {
        requestAdapterCacheSizes()
        onItemRangeChanged(position, itemCount)
    }

    override fun notifyAdapterItemRangeInserted(position: Int, itemCount: Int) {
        requestAdapterCacheSizes()
        onItemRangeChanged(position, itemCount)
    }

    override fun notifyAdapterItemRangeRemoved(position: Int, itemCount: Int) {
        requestAdapterCacheSizes()
        when {
            currentSelectedItemPos < position -> return
            currentSelectedItemPos in position.until(position + itemCount) -> resetCurrentSelection()
            else -> setCurrentSelection(currentSelectedItemPos - itemCount)
        }
    }

    override fun onClick(v: View, pos: Int, fastAdapter: FastAdapter<Item>, item: Item): Boolean {
        if (!item.isSelectable) return false

        if (!item.isSelected) {
            if (pos != currentSelectedItemPos) deselectCurrent()
            changeItemSelection(item, pos, true)
            setCurrentSelection(pos)
        } else if (item.isSelected && allowDeselect) {
            changeItemSelection(item, pos, false)
            resetCurrentSelection()
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

    private fun onItemRangeChanged(position: Int, itemCount: Int) {
        var foundSelection = hasSelection && selectedItem?.isSelected == true

        position
            .until(position + itemCount)
            .asSequence()
            .mapNotNull { pos -> getItemByPosition(pos)?.to(pos) }
            .filter { (item, pos) -> item.isSelected && pos != currentSelectedItemPos }
            .forEach { (item, pos) ->
                if (!foundSelection) {
                    setCurrentSelection(pos)
                    foundSelection = true
                } else {
                    changeItemSelection(item, pos, false)
                }
            }

        if (!foundSelection) resetCurrentSelection()
    }

    private fun deselectCurrent() {
        if (!hasSelection) return

        selectedItem?.let { changeItemSelection(it, currentSelectedItemPos, false) }
    }

    private fun changeItemSelection(item: Item, position: Int, isSelected: Boolean) {
        if (item.isSelected == isSelected) return

        item.isSelected = isSelected
        if (notifyItemChanges) fastAdapter.notifyItemChanged(position, Unit)
    }

    private fun resetCurrentSelection() {
        setCurrentSelection(RecyclerView.NO_POSITION)
    }

    private fun setCurrentSelection(position: Int) {
        val fixedPosition = max(position, RecyclerView.NO_POSITION)
        if (currentSelectedItemPos == fixedPosition) return

        currentSelectedItemPos = fixedPosition
        onSelectionChangedListener?.invoke(fixedPosition, getItemByPosition(fixedPosition))
    }

    private fun getItemByPosition(position: Int): Item? = fastAdapter.getItem(position)

    private fun requestAdapterCacheSizes() {
        runCatching { adapterCacheSizesMethod.invoke(fastAdapter) }
    }

}

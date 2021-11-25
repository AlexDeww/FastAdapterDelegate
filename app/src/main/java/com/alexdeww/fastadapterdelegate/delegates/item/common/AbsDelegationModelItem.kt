package com.alexdeww.fastadapterdelegate.delegates.item.common

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem.ViewHolder
import com.mikepenz.fastadapter.*
import com.mikepenz.fastadapter.items.BaseItem

typealias BindBlockAction = (payloads: List<Any>) -> Unit
typealias UnbindBlockAction = () -> Unit

typealias GenericDelegationModelItem<M> = AbsDelegationModelItem<out M, *, out ViewHolder<out M, *>>

abstract class AbsDelegationModelItem<M, I, VH>(
    final override var model: M
) : BaseItem<VH>(), IModelItem<M, VH>, ISubItem<VH>,
    IClickable<I> where I : AbsDelegationModelItem<M, I, VH>,
                        VH : ViewHolder<M, I> {

    internal lateinit var viewHolderFactory: IItemVHFactory<VH>
    final override var type: Int = -1
        internal set

    final override var parent: IParentItem<*>? = null
    final override var onItemClickListener: ClickListener<I>? = null
    final override var onPreItemClickListener: ClickListener<I>? = null
    final override val factory: IItemVHFactory<VH> get() = viewHolderFactory

    final override fun attachToWindow(holder: VH) {
        super.attachToWindow(holder)
    }

    final override fun bindView(holder: VH, payloads: List<Any>) {
        super.bindView(holder, payloads)
        holder._item = this
        holder.bindAction?.invoke(payloads)
    }

    final override fun detachFromWindow(holder: VH) {
        super.detachFromWindow(holder)
    }

    final override fun failedToRecycle(holder: VH): Boolean {
        return super.failedToRecycle(holder)
    }

    final override fun unbindView(holder: VH) {
        holder.unBindAction?.invoke()
        super.unbindView(holder)
    }

    abstract class ViewHolder<M, I>(itemView: View) : RecyclerView.ViewHolder(itemView)
            where I : AbsDelegationModelItem<M, *, *> {

        private object Uninitialized

        internal var _item: Any = Uninitialized
        internal var bindAction: BindBlockAction? = null
            private set
        internal var unBindAction: UnbindBlockAction? = null
            private set

        @Suppress("UNCHECKED_CAST")
        val item: I
            get() = when {
                _item === Uninitialized -> throw IllegalArgumentException("Item has not been set yet")
                else -> _item as I
            }
        val model: M get() = item.model
        val context: Context get() = itemView.context
        val resources: Resources get() = context.resources

        fun bind(block: (payloads: List<Any>) -> Unit) {
            check(bindAction == null) { "bind { ... } is already defined." }
            bindAction = block
        }

        fun unbind(block: () -> Unit) {
            check(unBindAction == null) { "unbind { ... } is already defined." }
            unBindAction = block
        }

    }

}

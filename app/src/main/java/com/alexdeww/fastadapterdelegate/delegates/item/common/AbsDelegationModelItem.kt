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

typealias GenericDelegationModelItem<M> = AbsDelegationModelItem<out M, *>

abstract class AbsDelegationModelItem<M, I>(
    final override var model: M
) : BaseItem<ViewHolder<M, I>>(), IModelItem<M, ViewHolder<M, I>>, ISubItem<ViewHolder<M, I>>,
    IClickable<I> where I : AbsDelegationModelItem<M, I> {

    internal lateinit var viewHolderFactory: IItemVHFactory<out ViewHolder<M, I>>
    final override var type: Int = -1
        internal set

    final override var parent: IParentItem<*>? = null
    final override var onItemClickListener: ClickListener<I>? = null
    final override var onPreItemClickListener: ClickListener<I>? = null

    @Suppress("UNCHECKED_CAST")
    final override val factory: IItemVHFactory<ViewHolder<M, I>>
        get() = viewHolderFactory as IItemVHFactory<ViewHolder<M, I>>

    final override fun attachToWindow(holder: ViewHolder<M, I>) {
        holder.attachToWindowAction?.invoke()
    }

    final override fun bindView(holder: ViewHolder<M, I>, payloads: List<Any>) {
        super.bindView(holder, payloads)
        holder._item = this
        holder.bindAction?.invoke(payloads)
    }

    final override fun detachFromWindow(holder: ViewHolder<M, I>) {
        holder.detachFromWindowAction?.invoke()
    }

    final override fun failedToRecycle(holder: ViewHolder<M, I>): Boolean {
        return super.failedToRecycle(holder)
    }

    final override fun unbindView(holder: ViewHolder<M, I>) {
        holder.unBindAction?.invoke()
        super.unbindView(holder)
    }

    abstract class ViewHolder<M, I : AbsDelegationModelItem<M, *>>(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private object Uninitialized

        internal var _item: Any = Uninitialized
        internal var bindAction: BindBlockAction? = null
            private set
        internal var unBindAction: UnbindBlockAction? = null
            private set
        internal var attachToWindowAction: (() -> Unit)? = null
            private set
        internal var detachFromWindowAction: (() -> Unit)? = null
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

        fun onViewAttachedToWindow(block: () -> Unit) {
            check(attachToWindowAction == null) { "onViewAttachedToWindow { ... } is already defined." }
            attachToWindowAction = block
        }

        fun onViewDetachedFromWindow(block: () -> Unit) {
            check(detachFromWindowAction == null) { "onViewDetachedFromWindow { ... } is already defined." }
            detachFromWindowAction = block
        }

    }

}

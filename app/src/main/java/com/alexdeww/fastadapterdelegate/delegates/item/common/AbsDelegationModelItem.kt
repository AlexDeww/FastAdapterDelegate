package com.alexdeww.fastadapterdelegate.delegates.item.common

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.alexdeww.fastadapterdelegate.delegates.item.common.AbsDelegationModelItem.ViewHolder
import com.mikepenz.fastadapter.*
import com.mikepenz.fastadapter.items.BaseItem

typealias BindBlockAction = (payloads: List<Any>) -> Unit
typealias UnbindBlockAction = () -> Unit

typealias GenericDelegationModelItem<M> = AbsDelegationModelItem<out M, *, out ViewHolder<out M, *>>

abstract class AbsDelegationModelItem<M, I, VH>(
    final override var model: M
) : BaseItem<VH>(), IModelItem<M, VH>, IItemVHFactory<VH>, ISubItem<VH>, IClickable<I>,
    ModelItemVHFactory<VH> where I : AbsDelegationModelItem<M, I, VH>,
                                 VH : ViewHolder<M, I> {

    protected lateinit var bindBlock: VH.() -> Unit
    private var _layoutRes: Int = -1
    private var _type: Int = -1

    @get:LayoutRes
    val layoutRes: Int
        get() = _layoutRes
    final override val type: Int get() = _type
    final override var parent: IParentItem<*>? = null
    final override var onItemClickListener: ClickListener<I>? = null
    final override var onPreItemClickListener: ClickListener<I>? = null

    final override fun getViewHolder(parent: ViewGroup): VH =
        getViewHolder(parent, layoutRes).apply(bindBlock)

    internal fun setParams(layoutRes: Int, type: Int, bindBlock: VH.() -> Unit) {
        _layoutRes = layoutRes
        _type = type
        this.bindBlock = bindBlock
    }

    abstract class ViewHolder<M, I : AbsDelegationModelItem<M, *, *>>(
        itemView: View
    ) : FastAdapter.ViewHolder<I>(itemView) {

        private object Uninitialized

        private var _item: Any = Uninitialized
        private var bindAction: BindBlockAction? = null
        private var unBindAction: UnbindBlockAction? = null

        @Suppress("UNCHECKED_CAST")
        val item: I
            get() = when {
                _item === Uninitialized -> throw IllegalArgumentException("Item has not been set yet")
                else -> _item as I
            }
        val model: M get() = item.model
        val context: Context get() = itemView.context
        val resources: Resources get() = context.resources

        override fun bindView(item: I, payloads: List<Any>) {
            _item = item
            this.bindAction?.invoke(payloads)
        }

        override fun unbindView(item: I) {
            unBindAction?.invoke()
        }

        fun bind(bindingBlock: (payloads: List<Any>) -> Unit) {
            check(bindAction == null) { "bind { ... } is already defined." }
            bindAction = bindingBlock
        }

        fun unbind(unBindBlock: () -> Unit) {
            check(unBindAction == null) { "unbind { ... } is already defined." }
            unBindAction = unBindBlock
        }

    }

}

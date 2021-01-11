package com.alexdeww.fastadapterdelegate.delegates.item.common

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.*
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.extensions.LayoutContainer

typealias GenericDelegateModelItem<M> = DelegateModelAbstractItem<M, *>
typealias BindBlockAction = (payloads: List<Any>) -> Unit
typealias UnbindBlockAction = () -> Unit

abstract class DelegateModelAbstractItem<M, I : ModelAbstractItem<M, *>>(
    model: M
) : ModelAbstractItem<M, RecyclerView.ViewHolder>(model),
    ISubItem<RecyclerView.ViewHolder>,
    IClickable<I> {

    private lateinit var bindBlock: RecyclerView.ViewHolder.() -> Unit
    private var _layoutRes: Int = -1
    private var _type: Int = -1

    final override val layoutRes: Int get() = _layoutRes
    final override val type: Int get() = _type
    final override var parent: IParentItem<*>? = null
    final override var onItemClickListener: ClickListener<I>? = null
    final override var onPreItemClickListener: ClickListener<I>? = null

    final override fun getViewHolder(v: View): RecyclerView.ViewHolder =
        DelegateModelItemViewHolder<M, I>(v).apply(bindBlock)

    internal fun setParams(
        layoutRes: Int,
        type: Int,
        bindBlock: RecyclerView.ViewHolder.() -> Unit
    ) {
        _layoutRes = layoutRes
        _type = type
        this.bindBlock = bindBlock
    }

}

class DelegateModelItemViewHolder<M, I : ModelAbstractItem<M, *>>(
    override val containerView: View
) : FastAdapter.ViewHolder<I>(containerView), LayoutContainer {

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
    val context: Context = containerView.context
    val resources: Resources = context.resources

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

    fun <V : View> findViewById(@IdRes id: Int): V = itemView.findViewById(id) as V

}

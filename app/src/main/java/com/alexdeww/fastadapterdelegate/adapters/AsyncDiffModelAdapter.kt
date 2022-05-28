package com.alexdeww.fastadapterdelegate.adapters

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexdeww.fastadapterdelegate.diffutil.FastAdapterDiffCallback
import com.alexdeww.fastadapterdelegate.diffutil.FastAdapterListUpdateCallback
import com.alexdeww.fastadapterdelegate.wrappers.ModelItemDiffCallbackWrapper
import com.mikepenz.fastadapter.IModelItem
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import java.util.concurrent.Executor

@SuppressLint("RestrictedApi")
open class AsyncDiffModelAdapter<Model : Any, Item : IModelItem<out Model, out RecyclerView.ViewHolder>>(
    private val asyncDifferConfig: AsyncDifferConfig<Model>,
    interceptor: (element: Model) -> Item?
) : ModelAdapter<Model, Item>(interceptor) {

    companion object {
        private val DEF_MAIN_THREAD_EXECUTOR by lazy { DefaultMainThreadExecutor() }
    }

    private var globalRunId: Int = 0
    private val mainThreadExecutor by lazy {
        asyncDifferConfig.mainThreadExecutor ?: DEF_MAIN_THREAD_EXECUTOR
    }
    private val itemsDiffCallback = ModelItemDiffCallbackWrapper(asyncDifferConfig.diffCallback)

    fun submitList(newList: List<Model>, commitCallback: (() -> Unit)? = null) {
        val runId = ++globalRunId
        val newItemsList = intercept(newList)
        val oldItemsList = FastAdapterDiffUtil.prepare(this, newItemsList)
        val diffCb = FastAdapterDiffCallback(oldItemsList, newItemsList, itemsDiffCallback)
        asyncDifferConfig.backgroundThreadExecutor.execute {
            val diffResult = DiffUtil.calculateDiff(diffCb, true)
            mainThreadExecutor.execute {
                if (globalRunId == runId) applyListChanges(newItemsList, diffResult, commitCallback)
            }
        }
    }

    private fun applyListChanges(
        newItemsList: List<Item>,
        diffResult: DiffUtil.DiffResult,
        commitCallback: (() -> Unit)?
    ) {
        FastAdapterDiffUtil.postCalculate(this, newItemsList)
        diffResult.dispatchUpdatesTo(FastAdapterListUpdateCallback(this))
        commitCallback?.invoke()
    }

    private class DefaultMainThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            handler.post(command)
        }

    }

}

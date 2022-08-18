package com.example.materialapp.ui.view.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val onItemSwiped: (position: Int) -> Unit,
    private val moveItems: (positionFrom: Int, positionTo: Int) -> Unit,
    private val replaceItems: (positionFrom: Int, positionTo: Int) -> Unit
) :
    ItemTouchHelper.Callback() {

    private var dragFrom = -1
    private var dragTo = -1


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val drag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipe = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(drag, swipe)
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val from = viewHolder.adapterPosition
        val to = target.adapterPosition
        if (dragFrom == -1) {
            dragFrom = from
        }
        dragTo = to
        moveItems(from, to)
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (dragFrom != -1 && dragTo != -1 && dragFrom != dragTo) {
            replaceItems(dragFrom, dragTo)
        }

        dragFrom = -1
        dragTo = -1
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        onItemSwiped.invoke(position)
    }
}
package com.example.diploma_work

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.diploma_work.R
import com.example.diploma_work.UpdateUserActivity
import com.example.diploma_work.UsersAdapter

class SwipeToDeleteCallback(private val adapter: UsersAdapter, private val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.baseline_delete)
    private val updateIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.baseline_edit_24)

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val user = adapter.getUserAtPosition(position)

        if (direction == ItemTouchHelper.LEFT) {
            adapter.deleteUser(position)
            adapter.notifyItemRemoved(position)

            adapter.deleteUserFromDatabase(user.id)
        } else if (direction == ItemTouchHelper.RIGHT) {
            val intent = Intent(context, UpdateUserActivity::class.java)
            intent.putExtra("user_id", user.id)
            context.startActivity(intent)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isSwipeLeft = dX < 0

        val iconMargin = (itemHeight - deleteIcon?.intrinsicHeight!!) / 2
        val iconSize = (itemHeight * 0.6).toInt()
        val iconTop = itemView.top + (itemHeight - iconSize) / 2
        val iconBottom = iconTop + iconSize
        val iconLeft: Int
        val iconRight: Int

        if (isSwipeLeft) {
            iconLeft = itemView.right - iconMargin - iconSize
            iconRight = itemView.right - iconMargin
            deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            deleteIcon?.draw(c)
        } else {
            iconLeft = itemView.left + iconMargin
            iconRight = itemView.left + iconMargin + iconSize
            updateIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            updateIcon?.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}

package com.example.diploma_work

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(private val adapter: UsersAdapter, private val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

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
}
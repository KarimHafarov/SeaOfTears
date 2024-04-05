package com.example.diploma_work

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter(private var users: List<User>, private val context: Context, private val adminId: Int) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val db: UsersDataBaseHelper = UsersDataBaseHelper(context)

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val surnameTextView: TextView = itemView.findViewById(R.id.surnameTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val rankTextView: TextView = itemView.findViewById(R.id.RankTextView)
        val fatherTextView: TextView = itemView.findViewById(R.id.fathernameTextView)
        val dutyTextView: TextView = itemView.findViewById(R.id.dutyTextView)
        val pinnedButton: ImageView = itemView.findViewById(R.id.pinnedButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.rankTextView.text = user.rank
        holder.fatherTextView.text = user.father
        holder.nameTextView.text = user.name
        holder.surnameTextView.text = user.surname
        holder.timeTextView.text = user.time
        holder.dutyTextView.text = user.duty

        holder.pinnedButton.setOnClickListener {
            val pinnedUser = users[position]
            val updatedList = users.toMutableList()
            updatedList.removeAt(position)
            updatedList.add(0, pinnedUser)
            users = updatedList.toList()
            notifyDataSetChanged()
            Toast.makeText(context, "User pinned", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserAtPosition(position: Int): User {
        return users[position]
    }

    fun deleteUser(position: Int) {
        users = users.filterIndexed { index, _ -> index != position }
    }

    fun deleteUserFromDatabase(userId: Int) {
        db.deleteUser(userId)
    }

    fun refreshData(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}
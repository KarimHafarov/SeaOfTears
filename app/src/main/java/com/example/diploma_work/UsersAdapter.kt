package com.example.diploma_work

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter (private var users: List<User>, context: Context) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val db: UsersDataBaseHelper = UsersDataBaseHelper(context)

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val surnameTextView: TextView = itemView.findViewById(R.id.surnameTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val rankTextView: TextView = itemView.findViewById(R.id.RankTextView)
        val fatherTextView: TextView = itemView.findViewById(R.id.fathernameTextView)
        val dutyTextView: TextView = itemView.findViewById(R.id.dutyTextView)


        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.rankTextView.text = user.rank
        holder.fatherTextView.text = user.fathername
        holder.nameTextView.text = user.name
        holder.surnameTextView.text = user.surname
        holder.timeTextView.text = user.time
        holder.dutyTextView.text = user.duty

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateUserActivity::class.java).apply {
                putExtra("user_id", user.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteUser(user.id.toLong())
            refreshData(db.getAllUsers())
            Toast.makeText(holder.itemView.context, "User deleted", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}


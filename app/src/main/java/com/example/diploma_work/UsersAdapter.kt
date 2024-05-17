package com.example.diploma_work

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

class UsersAdapter(
    private var users: List<User>,
    private val context: Context,
    private val adminId: Int
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val db: UsersDataBaseHelper = UsersDataBaseHelper(context)

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val surnameTextView: TextView = itemView.findViewById(R.id.surnameTextView)
        val rankTextView: TextView = itemView.findViewById(R.id.RankTextView)
        val fatherTextView: TextView = itemView.findViewById(R.id.fathernameTextView)

        init {
            itemView.setOnLongClickListener {
                val position = adapterPosition
                val user = getUserAtPosition(position)

                val bottomSheetDialog = BottomSheetDialog(itemView.context)
                val view = LayoutInflater.from(itemView.context).inflate(R.layout.bottom_sheet_layout, null)

                view.findViewById<TextView>(R.id.bottomSheetRankTextView).text = user.rank
                view.findViewById<TextView>(R.id.bottomSheetSurnameTextView).text = user.surname
                view.findViewById<TextView>(R.id.bottomSheetTimeTextView).text = user.time
                view.findViewById<TextView>(R.id.bottomSheetDutyTextView).text = user.duty
                view.findViewById<TextView>(R.id.bottomSheetCommentTextView).text = user.comment

                bottomSheetDialog.setContentView(view)
                bottomSheetDialog.show()

                true
            }
        }
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

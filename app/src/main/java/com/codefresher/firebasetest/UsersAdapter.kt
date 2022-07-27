package com.codefresher.firebasetest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.firebasetest.databinding.UserItemBinding

class UsersAdapter(
    var context: Context,
    var userList: ArrayList<Users>
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(val adapterUserBinding: UserItemBinding) :
        RecyclerView.ViewHolder(adapterUserBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.adapterUserBinding.textViewName.text = userList[position].userName
        holder.adapterUserBinding.textViewAge.text = userList[position].userAge.toString()
        holder.adapterUserBinding.textViewEmail.text = userList[position].userEmail

        holder.adapterUserBinding.linearLayout.setOnClickListener{
            val  intent = Intent(context,UpdateUserActivity::class.java)
            intent.putExtra("id", userList[position].userId)
            intent.putExtra("name", userList[position].userName)
            intent.putExtra("age", userList[position].userAge)
            intent.putExtra("email", userList[position].userEmail)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    fun getUserId(position:Int): String{
        return  userList[position].userId
    }
}
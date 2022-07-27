package com.codefresher.firebasetest

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codefresher.firebasetest.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")

    val userList = ArrayList<Users>()
    lateinit var userAdapter: UsersAdapter

    override

    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mainBinding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = userAdapter.getUserId(viewHolder.adapterPosition)
                myReference.child(id).removeValue()
                Toast.makeText(applicationContext, "The user was deleted", Toast.LENGTH_LONG).show()

            }

        }).attachToRecyclerView(mainBinding.recyclerView)
        retrieveDataFromDatabase()
    }

    fun retrieveDataFromDatabase() {
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (eachuser in snapshot.children) {
                    val user = eachuser.getValue(Users::class.java)
                    if (user != null) {
                        println("userId: ${user.userId}")
                        println("userId: ${user.userName}")
                        println("userId: ${user.userAge}")
                        println("userId: ${user.userEmail}")
                        println("*******************************")

                        userList.add(user)
                    }

                    userAdapter = UsersAdapter(this@MainActivity, userList)

                    mainBinding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    mainBinding.recyclerView.adapter = userAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_all, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll) {
            showDialogMessage()
        }else if(item.itemId == R.id.signOut){
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Users")
        dialogMessage.setMessage("If click yes all users will be deleted," + "If you want to delete")
        dialogMessage.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
            })
      dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
          myReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful){
                userAdapter.notifyDataSetChanged()

                Toast.makeText(applicationContext,"ALl users were be deleted",Toast.LENGTH_LONG).show()
            }
          }
      })
        dialogMessage.show()
    }


}
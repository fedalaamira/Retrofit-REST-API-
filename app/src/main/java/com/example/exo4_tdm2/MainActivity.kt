package com.example.exo4_tdm2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_dialog.view.*
import kotlinx.android.synthetic.main.post_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var position:Int=0


    var itemList : MutableList<post> = ArrayList()
    lateinit var list : MutableList<Int>
    lateinit var adapter: RecyclerAdapter
    val REQUEST_PERMESSION=1
    lateinit var intent2: Intent
    lateinit var layoutManager : LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent2 = Intent(this, Main2Activity::class.java)
        list = allNote(itemList)
        val add=findViewById<Button>(R.id.addTacheBtnView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter(this)
        recyclerView.adapter = adapter
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.INTERNET),REQUEST_PERMESSION)
        }
        else {
            call()
        }
        add.setOnClickListener{
            val mDialog= LayoutInflater.from(this).inflate(R.layout.layout_dialog,null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialog)
                .setTitle("Ajouter post")
            val mAlert= mBuilder.show()
            mDialog.ajouter.setOnClickListener{
                mAlert.dismiss()

                val newPost = post()
                newPost.title= mDialog.title.text.toString()
                newPost.body = mDialog.description.text.toString()
                newPost.userId=10
                newPost.id=1


                val retrofit = Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()



                val service = retrofit.create(BlogService::class.java)
                val postRequest = service.sendPost(newPost)
                postRequest.enqueue(object : Callback<post> {
                    override fun onResponse(call: Call<post>, response: Response<post>) {
                        if (response.isSuccessful) {
                            finish()

                            Toast.makeText(this@MainActivity, "Successfully Added", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "Failed to add item", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<post>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_LONG).show()
                    }



                })
                //  db1.daoNote().insert(note)
                itemList.add(newPost)
                update()

            }


            }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==REQUEST_PERMESSION){
            call()


        }

    }
    fun call(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(BlogService::class.java)
        val postRequest = service.getPosts()
        postRequest.enqueue(object : Callback<List<post>> {
            override fun onResponse(call: Call<List<post>>, response: Response<List<post>>) {
                val allposts = response.body()
                Log.w("test",allposts.toString())
                if (allposts != null) {
                    //info("HERE is ALL posts FROM SERVER:")

                    for (res in allposts)
                    {
                        val titre = res.title.toString()
                        val description = res.body.toString()
                        val id =res.id
                          val user=res.userId
                        val posts = post(user,id,titre,description)

                        itemList.add(posts)

                        update()
                    }
                }
            }
            override fun onFailure(call: Call<List<post>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun update(){

        list.clear()
        list =allNote(itemList)
        adapter.notifyDataSetChanged()

    }
    fun allNote(lists : MutableList<post>):MutableList<Int>{
        var list : MutableList<Int> = ArrayList()
        for (i in 1 until lists.size){
            list.add(i)
        }
        return list
    }
}

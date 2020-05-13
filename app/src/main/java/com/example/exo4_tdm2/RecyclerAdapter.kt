package com.example.exo4_tdm2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
/**
 *Created by Fedala Amira.
 */
class RecyclerAdapter(val activity : MainActivity) : RecyclerView.Adapter<RecyclerAdapter.recyclerViewHolder>(){

    class recyclerViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val itemLayout = v.findViewById<RelativeLayout>(R.id.tacheLayoutView)
        val image=v.findViewById<ImageView>(R.id.delete)
        val titre = v.findViewById<TextView>(R.id.titre)
        val desc=v.findViewById<TextView>(R.id.desc)





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder {
        return recyclerViewHolder(LayoutInflater.from(activity).inflate(R.layout.post_view, parent, false))

    }

    override fun getItemCount(): Int {
        return activity.list.size
    }

    override fun onBindViewHolder(holder: recyclerViewHolder, position: Int) {

        activity.position=position

        holder.titre.text = activity.itemList[activity.list[position]].title
        holder.desc.text=activity.itemList[activity.list[position]].body

        holder.image.setOnClickListener{





        }
        holder.image.setOnClickListener{


            activity.itemList.removeAt(activity.list[position])



            activity.update()
        }
        holder.itemLayout.setOnClickListener {


            activity.intent2.putExtra("titre",activity.itemList[activity.list[position]].title)

            activity.intent2.putExtra("desc",activity.itemList[activity.list[position]].body)

            ContextCompat.startActivity(activity, activity.intent2, null)


        }




    }
}
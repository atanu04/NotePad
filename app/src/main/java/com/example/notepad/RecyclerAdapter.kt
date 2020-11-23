package com.example.notepad



import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import androidx.recyclerview.widget.RecyclerView

import com.example.notepad.model.Note
import kotlinx.android.synthetic.main.item_layout.view.*

class RecyclerAdapter(private val listener: NoteClicked) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()
     {

    private var noteList = emptyList<Note>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView = itemView.item_title_id
        val  desc:TextView = itemView.item_desc_Id
        val date:TextView = itemView.item_date_id


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(noteList[viewHolder.adapterPosition])
        }
        view.setOnLongClickListener {
            listener.onItemLongClicked(noteList[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentNote = noteList[position]
        holder.title.text = currentNote.title
        holder.desc.text = currentNote.desc
        holder.date.text = currentNote.date

    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setData(notes:List<Note>){
        this.noteList = notes
        notifyDataSetChanged()
    }


}
interface NoteClicked{
    fun onItemClicked(note : Note)
    fun onItemLongClicked(note : Note):Boolean
}

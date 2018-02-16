package com.mobileasone.dagger2workshop.presentation.notelist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobileasone.dagger2workshop.R
import com.mobileasone.dagger2workshop.domain.Note

class NotesAdapter(
        private val noteList: MutableList<Note>,
        private val listener: OnNoteItemListener) :
        RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup?,
            viewType: Int): NotesAdapter.ViewHolder {

        return ViewHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount() = noteList.count()

    override fun onBindViewHolder(
            holder: NotesAdapter.ViewHolder?,
            position: Int) {

        val note = noteList[position]
        holder?.title?.text = note.title
        holder?.description?.text = note.description
        holder?.itemView?.setOnClickListener({
            listener.onNoteClicked(note)
        })
    }

    fun setList(newNoteList: List<Note>) {
        noteList.clear()
        noteList.addAll(newNoteList)
        notifyDataSetChanged()
    }

    interface OnNoteItemListener {
        fun onNoteClicked(note: Note)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.note_detail_title)
        var description: TextView = itemView.findViewById(R.id.note_detail_description)
    }

}

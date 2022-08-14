package com.example.materialapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.materialapp.R
import com.example.materialapp.databinding.NoteItemBinding
import com.example.materialapp.domain.data.notesDB.NoteEntity

class NotesFragmentAdapter(val removeItem: (item: NoteEntity) -> Unit) :
    ListAdapter<NoteEntity, NotesFragmentAdapter.NotesViewHolder>(object :
        DiffUtil.ItemCallback<NoteEntity>() {
        override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
            oldItem == newItem

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
    )

    fun removeItem(position: Int) {
        val item = currentList[position]
        removeItem.invoke(item)
        val newList = ArrayList(currentList)
        newList.removeAt(position)
        submitList(newList)
    }

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NoteItemBinding.bind(view)
        fun bind(entity: NoteEntity) {
            binding.title.text = entity.title
            binding.text.text = entity.note
            binding.date.text = entity.date
        }
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
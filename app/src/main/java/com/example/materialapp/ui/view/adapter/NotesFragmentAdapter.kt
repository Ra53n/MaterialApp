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
import java.util.*

class NotesFragmentAdapter(
    private val removeItem: (item: NoteEntity) -> Unit,
    private val replaceItems: (firstItem: NoteEntity, secondItem: NoteEntity) -> Unit,
    private val onItemClicked: (item: NoteEntity) -> Unit
) :
    ListAdapter<NoteEntity, NotesFragmentAdapter.NotesViewHolder>(
        object :
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
            binding.priority.text = PRIORITY_PREF + entity.priority
            binding.root.setOnClickListener { onItemClicked(entity) }
        }
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun sortByPriority(isIncreasing: Boolean) {
        val sortedList = ArrayList(currentList)
        if (isIncreasing) {
            sortedList.sortBy { it.priority }
        } else {
            sortedList.sortByDescending { it.priority }
        }
        submitList(sortedList)
    }

    fun moveItems(positionFrom: Int, positionTo: Int) {
        val replaceList = ArrayList(currentList)
        Collections.swap(replaceList, positionFrom, positionTo)
        submitList(replaceList)
    }

    fun replaceItems(positionFrom: Int, positionTo: Int) {
        val firstItem = currentList[positionFrom]
        val secondItem = currentList[positionTo]
        replaceItems(firstItem, secondItem)
    }

    companion object {
        private const val PRIORITY_PREF = "Proirity: "
    }
}
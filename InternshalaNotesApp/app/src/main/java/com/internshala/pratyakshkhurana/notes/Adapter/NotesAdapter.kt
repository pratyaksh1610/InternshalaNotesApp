package com.internshala.pratyakshkhurana.notes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.internshala.pratyakshkhurana.notes.Model.NotesEntity
import com.internshala.pratyakshkhurana.notes.R
import com.internshala.pratyakshkhurana.notes.databinding.RecyclerViewItemNoteBinding

class NotesAdapter(
    private val requireContext: Context,
    private val notesList: List<NotesEntity>,
    private val listen: OnClick
) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(val binding: RecyclerViewItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            RecyclerViewItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val curr = notesList[position]

        // bind data in recycler view
        holder.binding.titleRecyclerView.text = curr.title
        holder.binding.descRecyclerView.text = curr.description
        holder.binding.dateRecyclerView.text = curr.lastUpdatedTime + ", " + curr.requiredDateFormat

        holder.binding.cardViewRecyclerView.setOnClickListener {
            listen.onClick(curr)
        }
        holder.binding.titleRecyclerView.setOnClickListener {
            listen.onClick(curr)
        }
        holder.binding.descRecyclerView.setOnClickListener {
            listen.onClick(curr)
        }
        holder.binding.dateRecyclerView.setOnClickListener {
            listen.onClick(curr)
        }
        holder.binding.deleteButtonRecyclerView.setOnClickListener {
            // alert the user
            MaterialAlertDialogBuilder(requireContext)
                .setTitle(R.string.delete_note_dialog_title)
                .setCancelable(false)
                .setNegativeButton(R.string.no) { _, _ ->
                }
                .setPositiveButton(R.string.yes) { _, _ ->
                    listen.deleteNote(curr)
                }
                .show()
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}

// interface for call backs
interface OnClick {
    fun onClick(note: NotesEntity)
    fun deleteNote(note: NotesEntity)
}

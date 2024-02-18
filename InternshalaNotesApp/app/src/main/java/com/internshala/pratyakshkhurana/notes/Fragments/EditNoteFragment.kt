package com.internshala.pratyakshkhurana.notes.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.internshala.pratyakshkhurana.notes.Model.NotesEntity
import com.internshala.pratyakshkhurana.notes.R
import com.internshala.pratyakshkhurana.notes.ViewModel.NotesViewModel
import com.internshala.pratyakshkhurana.notes.databinding.FragmentEditNoteBinding
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class EditNoteFragment : Fragment() {
    private lateinit var binding: FragmentEditNoteBinding
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var prefs: SharedPreferences
    private lateinit var userEmail: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(layoutInflater, container, false)
        prefs = requireActivity().getSharedPreferences(
            getString(R.string.shared_preferences_key),
            Context.MODE_PRIVATE
        )!!
        userEmail = prefs.getString("userEmail", "").toString()

        val args = this.arguments

        val title = args?.get("title").toString()
        val description = args?.get("description").toString()
        val id = args?.get("id")

        binding.titleActEditNote.setText(title)
        binding.descriptionActEditNote.setText(description)
        if (binding.descriptionActEditNote.text.isEmpty()) {
            binding.descriptionActEditNote.hint = "Add description..."
            binding.descriptionActEditNote.hintTextColors
        }

        binding.titleActEditNote.setText(title)
        binding.descriptionActEditNote.setText(description)
        if (binding.descriptionActEditNote.text.isEmpty()) {
            binding.descriptionActEditNote.hint = "Add description..."
            binding.descriptionActEditNote.hintTextColors
        }

        val time: LocalTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now(ZoneId.systemDefault())
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
        val currentTime = dtf.format(time)

        binding.saveFloatingButtonEditNote.setOnClickListener {
            val newNote = NotesEntity(
                id = id as Int?,
                title = binding.titleActEditNote.text.toString(),
                description = binding.descriptionActEditNote.text.toString(),
                lastUpdatedTime = currentTime,
                lastUpdatedDate = currentDateFormat(),
                requiredDateFormat = currentDateFormat(),
                userEmail = userEmail
            )

            if (binding.titleActEditNote.text.toString().isNotEmpty()) {
                viewModel.updateNote(newNote)
                Toast.makeText(requireContext(), "Last updated : $currentTime", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Add a title", Toast.LENGTH_SHORT).show()
            }
            // take reference of view model and insert
        }

        binding.backBtnCreateNote.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragmentContainer, NotesFragment())
                    .commit()
            }
        }

        return binding.root
    }

    private fun currentDateFormat(): String {
        val time: LocalTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime.now(ZoneId.systemDefault())
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
        val currentTime = dtf.format(time)

        Log.e("@", currentTime)

        val currentDate: String =
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()).toString()
        Log.e("@@", currentDate) // 27-08-2024

        val date = (currentDate[0].toString() + currentDate[1].toString())
        val month = (currentDate[3].toString() + currentDate[4].toString())
        val year =
            currentDate[6].toString() + currentDate[7].toString() + currentDate[8].toString() + currentDate[9].toString()

        var requiredDateFormat = ""
        when (month) {
            "01" -> requiredDateFormat += "Jan"
            "02" -> requiredDateFormat += "Feb"
            "03" -> requiredDateFormat += "Mar"
            "04" -> requiredDateFormat += "April"
            "05" -> requiredDateFormat += "May"
            "06" -> requiredDateFormat += "June"
            "07" -> requiredDateFormat += "July"
            "08" -> requiredDateFormat += "Aug"
            "09" -> requiredDateFormat += "Sept"
            "10" -> requiredDateFormat += "Oct"
            "11" -> requiredDateFormat += "Nov"
            "12" -> requiredDateFormat += "Dec"
        }
        requiredDateFormat += " $date, $year"
        return requiredDateFormat
    }
}

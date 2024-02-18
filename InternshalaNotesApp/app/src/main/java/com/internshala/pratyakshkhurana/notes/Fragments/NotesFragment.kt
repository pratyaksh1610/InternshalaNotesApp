package com.internshala.pratyakshkhurana.notes.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.internshala.pratyakshkhurana.notes.Adapter.NotesAdapter
import com.internshala.pratyakshkhurana.notes.Adapter.OnClick
import com.internshala.pratyakshkhurana.notes.Model.NotesEntity
import com.internshala.pratyakshkhurana.notes.R
import com.internshala.pratyakshkhurana.notes.ViewModel.NotesViewModel
import com.internshala.pratyakshkhurana.notes.databinding.FragmentNotesBinding
import kotlin.system.exitProcess

class NotesFragment : Fragment(), OnClick {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var prefs: SharedPreferences
    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var userEmail: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(layoutInflater, container, false)
        prefs = requireActivity().getSharedPreferences(
            getString(R.string.shared_preferences_key),
            Context.MODE_PRIVATE
        )!!
        firebaseAuth = FirebaseAuth.getInstance()
        userEmail = prefs.getString("userEmail", "").toString()
        binding.emailTv.text = prefs.getString("userEmail", "").toString()

        loadAllNotes()

        binding.toolbar.setOnMenuItemClickListener(object :
                Toolbar.OnMenuItemClickListener,
                androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.item_logout -> {
                            showLogoutDialog()
                        }

                        R.id.item_add -> {
                            navigateToCreateNoteFragment()
                        }

                        R.id.item_close -> {
                            closeApp()
                        }
                    }
                    return true
                }
            })
        return binding.root
    }

    private fun closeApp() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.close_app_dialog_title))
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                // safe abort
                exitProcess(0)
            }
            .show()
    }

    private fun loadAllNotes() {
        viewModel.getAllNotes(userEmail).observe(viewLifecycleOwner) { notesList ->
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter =
                NotesAdapter(requireContext(), notesList, this@NotesFragment)
        }
    }

    private fun navigateToCreateNoteFragment() {
        fragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragmentContainer, CreateNoteFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.logout_dialog_title))
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->

                googleSignInClient =
                    GoogleSignIn.getClient(requireActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN)

                // Sign out from google
                googleSignInClient.signOut().addOnCompleteListener { task ->
                    // Check condition
                    if (task.isSuccessful) {
                        // When task is successful sign out from firebase
                        firebaseAuth.signOut()
                    }

                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.fragmentContainer, LoginFragment())
                        commit()
                    }
                }
            }
            .show()
    }

    override fun onClick(note: NotesEntity) {
        // data is passed between fragments in the form of bundles
        val bundle = Bundle()

        bundle.putString("title", note.title)
        note.id?.let { bundle.putInt("id", it) }
        bundle.putString("description", note.description)

        // set destination
        val fragment = EditNoteFragment()
        fragment.arguments = bundle

        // use fragment manager
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainer, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun deleteNote(note: NotesEntity) {
        viewModel.deleteNote(note)
    }
}

interface OnBackPressedListener {
    fun onBackPressed()
}

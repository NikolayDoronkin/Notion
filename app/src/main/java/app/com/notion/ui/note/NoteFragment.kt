package app.com.notion.ui.note

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import app.com.notion.App
import app.com.notion.R
import app.com.notion.core.BaseFragment
import app.com.notion.core.extension.getViewModelExt
import app.com.notion.core.extension.navigateExt
import app.com.notion.core.extension.showAlertDialogExt
import app.com.notion.core.stringToBitmap
import app.com.notion.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        arguments?.let {
            val args = NoteFragmentArgs.fromBundle(it)
            val id = args.id
            noteViewModel = getViewModelExt {
                NoteViewModel(
                    noteRepository = App.instance.noteRepository,
                    id = id
                )
            }
            initObservers()
            noteViewModel.load()
        }
        return binding.root
    }

    private fun initObservers() {
        noteViewModel.note.observe(viewLifecycleOwner) { n ->
            binding.tittleView.text = n.tittle
            binding.textView.text = n.text
            binding.dateView.text = n.datetime
            if (n.icon.isNotEmpty()) {
                binding.noteImage.setImageBitmap(stringToBitmap(n.icon))
            } else {
                binding.noteImage.setImageResource(R.drawable.ic_image_outline_24)
            }
        }
        noteViewModel.deletedNote.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.redact_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.edit -> {
                navigateExt(NoteFragmentDirections.actionNavNoteToNavAddNote(noteViewModel.id))
                true
            }
            R.id.delete -> {
                showAlertDialogExt(R.string.dialog_delete) {
                    noteViewModel.delete()
                }
                true
            }
            else -> false
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
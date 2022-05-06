package app.com.notion.ui.event

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import app.com.notion.App
import app.com.notion.R
import app.com.notion.core.BaseFragment
import app.com.notion.core.date.DateFormat
import app.com.notion.core.extension.getViewModelExt
import app.com.notion.core.extension.navigateExt
import app.com.notion.core.extension.showAlertDialogExt
import app.com.notion.databinding.FragmentEventBinding
import java.util.*

class EventFragment : BaseFragment() {

    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        arguments?.let {
            val args = EventFragmentArgs.fromBundle(it)
            val id = args.id
            eventViewModel = getViewModelExt {
                EventViewModel(
                    eventRepository = App.instance.eventRepository,
                    id = id
                )
            }
            initObservers()
            eventViewModel.load()
        }
        return binding.root
    }

    private fun initObservers() {
        eventViewModel.event.observe(viewLifecycleOwner) { e ->
            binding.tittleView.text = e.tittle
            binding.dateView.text = DateFormat.rangeDate(Date(e.start), Date(e.ending))
            binding.descriptionView.text = e.description

            val repeatResId = when (e.repeat) {
                0 -> R.string.repeat_once
                1 -> R.string.repeat_daily
                2 -> R.string.repeat_on_weekdays
                3 -> R.string.repeat_annualy
                else -> R.string.repeat_once
            }
            binding.repeatView.text = getString(repeatResId)
        }
        eventViewModel.deletedEvent.observe(viewLifecycleOwner) {
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
                navigateExt(EventFragmentDirections.actionNavEventToNavNewEvent(eventViewModel.id))
                true
            }
            R.id.delete -> {
                showAlertDialogExt(R.string.dialog_delete) {
                    eventViewModel.delete()
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
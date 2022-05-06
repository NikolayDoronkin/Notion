package app.com.notion.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.com.notion.core.viewmodel.SingleLiveEvent
import app.com.notion.data.entity.Event
import app.com.notion.data.repository.EventRepository
import kotlinx.coroutines.launch
import java.util.*


class CalendarViewModel(
    private val eventRepository: EventRepository,
    val userId: Long
) : ViewModel() {

    var clickDate: Date = Calendar.getInstance(Locale.ENGLISH).time
    var calendar: Calendar = Calendar.getInstance(Locale.ENGLISH)

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _deletedEvent = SingleLiveEvent<Boolean>()
    val deletedEvent: LiveData<Boolean> = _deletedEvent

    fun loadEvents() {
        viewModelScope.launch {
            _events.value = eventRepository.getEvents(userId)
        }
    }

    fun delete(event: Event) {
        viewModelScope.launch {
            eventRepository.delete(event)
            _deletedEvent.value = true
        }
    }

}
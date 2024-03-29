package app.com.notion.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.com.notion.core.viewmodel.SingleLiveEvent
import app.com.notion.data.entity.Event
import app.com.notion.data.repository.EventRepository
import kotlinx.coroutines.launch

class EventViewModel(
    private val eventRepository: EventRepository,
    val id: Long
) : ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    private val _deletedEvent = SingleLiveEvent<Boolean>()
    val deletedEvent: LiveData<Boolean> = _deletedEvent

    fun load() {
        if (id > 0) {
            viewModelScope.launch {
                val events = eventRepository.getEvent(id)
                if (events.isNotEmpty()) {
                    _event.value = events[0]
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            _event.value?.let {
                eventRepository.delete(it)
                _deletedEvent.value = true
            }
        }
    }

}
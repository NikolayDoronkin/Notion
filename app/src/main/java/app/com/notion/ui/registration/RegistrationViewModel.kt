package app.com.notion.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.com.notion.core.viewmodel.SingleLiveEvent
import app.com.notion.data.entity.User
import app.com.notion.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _existUser = SingleLiveEvent<Boolean>()
    val existUser: LiveData<Boolean> = _existUser

    private val _userId = MutableLiveData<Long>()
    val userId: LiveData<Long> = _userId

    fun save(user: User) {
        viewModelScope.launch {
            val users = userRepository.existUser(name = user.name, email = user.email)
            if (users.isEmpty()) {
                _userId.value = userRepository.insert(user)
            } else {
                _existUser.value = true
            }
        }
    }

}
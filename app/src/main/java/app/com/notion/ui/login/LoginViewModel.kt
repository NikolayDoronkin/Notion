package app.com.notion.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.com.notion.core.viewmodel.SingleLiveEvent
import app.com.notion.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _userId = MutableLiveData<Long>()
    val userId: LiveData<Long> = _userId

    private val _loginError = SingleLiveEvent<Boolean>()
    val loginError: LiveData<Boolean> = _loginError

    fun login(name: String, password: String) {
        viewModelScope.launch {
            val users = userRepository.getUser(name, password)
            if (users.isEmpty()) {
                _loginError.value = true
            } else {
                _userId.value = users[0].id
            }
        }
    }

}
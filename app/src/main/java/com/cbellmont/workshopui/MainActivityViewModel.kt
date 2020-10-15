package com.cbellmont.workshopui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cbellmont.datamodel.User
import com.cbellmont.sources.GetAllUsers
import kotlinx.coroutines.*

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _mainActivityStatus: MutableLiveData<MainActivityStatus> by lazy { MutableLiveData<MainActivityStatus>() }
    val mainActivityStatus: LiveData<MainActivityStatus> get() = _mainActivityStatus

    private val _userList : MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
    val userList : LiveData<List<User>> get() = _userList

    enum class MainActivityStatus {
        WAITING,
        LOADING,
        FINISHED
    }

    fun downloadStarted() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                _mainActivityStatus.value = MainActivityStatus.LOADING
            }
        }
    }

    fun downloadCancelled() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main) {
                _mainActivityStatus.value = MainActivityStatus.WAITING
            }
        }
    }

    fun downloadFinished(users: List<User>) {
        CoroutineScope(Dispatchers.Main).launch {
            _userList.value = users
            _mainActivityStatus.value = MainActivityStatus.FINISHED
        }
    }

    fun downloadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                downloadStarted()
                delay(3000)
                GetAllUsers.send(this@MainActivityViewModel)
            }
        }
    }

}
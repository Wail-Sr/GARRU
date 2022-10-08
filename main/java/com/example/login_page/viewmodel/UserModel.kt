package com.example.login_page.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login_page.entity.Emailandmdp
import com.example.login_page.entity.User
import com.example.login_page.retrofit.UserEndpoint
import kotlinx.coroutines.*

class UserModel: ViewModel() {

    var user= MutableLiveData<User?>()
    val loading = MutableLiveData<Boolean>()
    val existe = MutableLiveData<Boolean>()
    val creation = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError(throwable.localizedMessage)
    }

    private fun onError(message: String?) {
        errorMessage.postValue(message!!)
    }

    fun addUser(user: User){
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = UserEndpoint.createInstance().addUser(user = user)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    creation.postValue(true)
                } else {
                    creation.postValue(false)
                    onError(response.message())
                }
            }
        }
    }

    fun loadUser2(email:String){
        existe.value=false
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = UserEndpoint.createInstance().getUserByEmail(email = email)
            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    if(response.body()!!.isNotEmpty()) existe.postValue(true)
                    loading.postValue(false)
                }
                else{
                    onError(response.message())
                }
            }
        }
    }

    fun gettuser(email: String, password: String){
        if(user.value==null) {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val emailandmdp = Emailandmdp(email = email, password = password)
                val response = UserEndpoint.createInstance().getUserByEmailAndMdp(emailandmdp = emailandmdp)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())

                    } else {
                        onError(response.message())
                    }
                }
            }

        }
        else{
            onError("User already loaded")
        }
    }

    fun getuser(email: String){
        if(user.value==null) {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val email = email
                val response = UserEndpoint.createInstance().getUserByEmail(email = email)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body()!!.isNotEmpty()) {
                        user.postValue(response.body()!![0])
                    } else {
                        onError(response.message())
                    }
                }
            }

        }
    }

}
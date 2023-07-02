package com.Ibnuumar.makananringan_ibnuumar.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.Ibnuumar.makananringan_ibnuumar.model.Snacks
import com.Ibnuumar.makananringan_ibnuumar.repository.SnacksRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SnacksViewModel(private val repository: SnacksRepository):ViewModel() {
    val allSnacks: LiveData<List<Snacks>> = repository.allsnacks.asLiveData()

    fun insert(snacks: Snacks) = viewModelScope.launch {
        repository.insertSnacks(snacks)
    }
    fun delete(snacks: Snacks) = viewModelScope.launch {
        repository.deleteSnacks(snacks)
    }
    fun update(snacks: Snacks) = viewModelScope.launch {
        repository.updateSnacks(snacks)
    }
}
class SnacksViewModelFactory (private val repository: SnacksRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((SnacksViewModel::class.java))){
            return SnacksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

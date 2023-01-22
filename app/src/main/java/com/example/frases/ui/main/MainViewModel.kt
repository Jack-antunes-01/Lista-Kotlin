package com.example.frases.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.frases.data.Frase
import com.example.frases.data.repository.MemoryRepository

class MainViewModel: ViewModel() {
    private var memoryRepository: MemoryRepository = MemoryRepository(mutableListOf())
    private val _listaDeFrases = MutableLiveData<List<Frase>>()

    val listaDeFrases: LiveData<List<Frase>> = _listaDeFrases

    fun iniciarDados() {
        _listaDeFrases.value = memoryRepository.retornarLista()
    }

    fun salvarFrase(frase: Frase){
        Log.i("appinfo", "Frase recebida $frase")

        memoryRepository.salvar(frase)
        atualizarListaFrases()
    }

    fun limparListaDeFrases(){
        memoryRepository.limparLista()
        _listaDeFrases.value = memoryRepository.retornarLista()
    }

    private fun atualizarListaFrases() {
        _listaDeFrases.value = memoryRepository.retornarLista()
    }
}
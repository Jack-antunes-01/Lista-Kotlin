package com.example.frases.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.frases.R
import com.example.frases.data.Frase
import com.example.frases.databinding.ActivityMainBinding
import com.example.frases.ui.incluirFrase.IncluirFraseActivity

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val retornoFrase = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            activityResult.data?.let {
                if (it.hasExtra(RETORNO)) {
                    Log.i("appinfo", "Retorno: ${it.getParcelableExtra<Frase>(RETORNO)}")
                    viewModel.salvarFrase(it.getParcelableExtra(RETORNO)!!)
                }
            }
        } else {
            Log.e("apperror", "Não foi possível obter os dados de retorno da activity.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        iniciarDados()
        configurarListeners()
        configurarObservers()
    }

    private fun configurarObservers() {
        configurarRecyclerView()
    }

    private fun configurarRecyclerView() {
        viewModel.listaDeFrases.observe(this) { lista ->
            atualizarLista(lista)
        }
    }

    private fun atualizarLista(lista: List<Frase>) {
        if(lista.isNullOrEmpty()){
            binding.rvListaFrases.visibility = View.GONE
            binding.tvMensagemListaVazia.visibility = View.VISIBLE
        } else {
            binding.tvMensagemListaVazia.visibility = View.GONE
            binding.rvListaFrases.visibility = View.VISIBLE
            binding.rvListaFrases.adapter = FrasesAdapter(listaDeFrases = lista)
        }
    }

    private fun iniciarDados() {
        viewModel.iniciarDados()
    }

    private fun configurarListeners() {
        configurarFabListener()
    }

    private fun configurarFabListener() {
        binding.fabAddNovaFrase.setOnClickListener {
            Intent(this, IncluirFraseActivity::class.java).let {
                retornoFrase.launch(it)
            }
        }

        binding.fabAddNovaFrase.setOnLongClickListener {
            viewModel.limparListaDeFrases()
            Toast.makeText(this, R.string.lista_limpa_sucesso, Toast.LENGTH_LONG).show()
            it.isLongClickable
        }
    }

    companion object {
        const val RETORNO = "retorno_frases"
    }
}
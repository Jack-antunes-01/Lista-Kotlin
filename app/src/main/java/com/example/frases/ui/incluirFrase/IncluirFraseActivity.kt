package com.example.frases.ui.incluirFrase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.frases.R
import com.example.frases.data.Frase
import com.example.frases.databinding.ActivityIncluirFraseBinding
import com.example.frases.ui.main.MainActivity

class IncluirFraseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIncluirFraseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncluirFraseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configurarListeners()
    }

    private fun configurarListeners() {
        configurarListenetBtnCancelar()
        configurarListenetBtnSalvar()
    }

    private fun configurarListenetBtnSalvar() {
        binding.btnSalvar.setOnClickListener {
            salvarFrase()
        }
    }

    private fun configurarListenetBtnCancelar() {
        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun salvarFrase() {
        binding.apply {
            val autor = autoresFraseET.text.toString()
            val frase = frasesET.text.toString()

            autoresFraseTIL.error = if (autor.isEmpty()) {
                getString(R.string.erro_sem_autor)
            } else {
                null
            }

            fraseTIL.error = if (frase.isEmpty()) {
                getString(R.string.erro_sem_citacao)
            } else {
                null
            }

            if (autor.isNotEmpty() && frase.isNotEmpty()) {
                Intent().apply {
                    putExtra(MainActivity.RETORNO, Frase(
                        autor = autor,
                        frase = frase
                    ))
                    setResult(RESULT_OK, this)
                }
                finish()
            }
        }


    }


}
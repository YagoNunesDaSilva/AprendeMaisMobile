package com.example.aprendemais

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aprendemais.database.AppDatabase
import com.example.aprendemais.repository.UserRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar banco de dados
        val database = AppDatabase.getDatabase(this)
        userRepository = UserRepository(database.userDao())

        val etEmail = findViewById<EditText>(R.id.etEmailLogin)
        val etSenha = findViewById<EditText>(R.id.etSenhaLogin)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnIrCadastro = findViewById<Button>(R.id.btnIrCadastro)

        btnEntrar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val senha = etSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fazerLogin(email, senha)
        }

        btnIrCadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }

    private fun fazerLogin(email: String, senha: String) {
        lifecycleScope.launch {
            try {
                val usuario = userRepository.obterUsuarioPorEmail(email)

                if (usuario != null && usuario.password == senha) {
                    Toast.makeText(
                        this@MainActivity,
                        "Bem-vindo, ${usuario.name}!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Navegar para tela principal
                    startActivity(Intent(this@MainActivity, TelaPrincipal::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Email ou senha incorretos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Erro: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
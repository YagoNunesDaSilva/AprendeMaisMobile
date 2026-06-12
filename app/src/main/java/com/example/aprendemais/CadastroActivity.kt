package com.example.aprendemais

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aprendemais.database.AppDatabase
import com.example.aprendemais.database.User
import com.example.aprendemais.repository.UserRepository
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val database = AppDatabase.getDatabase(this)
        userRepository = UserRepository(database.userDao())

        val etNome = findViewById<EditText>(R.id.etNomeCadastro)
        val etEmail = findViewById<EditText>(R.id.etEmailCadastro)
        val etCpf = findViewById<EditText>(R.id.etCpfCadastro)
        val etDataNasc = findViewById<EditText>(R.id.etDataNascCadastro)
        val etSenha = findViewById<EditText>(R.id.etSenhaCadastro)
        val btnCriarConta = findViewById<Button>(R.id.btnCriarConta)

        btnCriarConta.setOnClickListener {
            val nome = etNome.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val cpf = etCpf.text.toString().trim()
            val dataNasc = etDataNasc.text.toString().trim()
            val senha = etSenha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            cadastrarUsuario(nome, email, cpf, dataNasc, senha)
        }
    }

    private fun cadastrarUsuario(nome: String, email: String, cpf: String, dataNasc: String, senha: String) {
        lifecycleScope.launch {
            try {
                val usuarioExistente = userRepository.obterUsuarioPorEmail(email)

                if (usuarioExistente != null) {
                    Toast.makeText(this@CadastroActivity, "Email já cadastrado", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val novoUsuario = User(
                    name = nome,
                    email = email,
                    password = senha,
                    cpf = cpf,
                    birthDate = dataNasc,
                    role = "student"
                )

                userRepository.inserirUsuario(novoUsuario)
                Toast.makeText(this@CadastroActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@CadastroActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.flateonbot20

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var layoutPrincipal: LinearLayout
    private val pontosDaRota = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mostrarTelaPrincipal()
    }

    private fun mostrarTelaPrincipal() {

        layoutPrincipal = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 40, 32, 32)
        }

        val titulo = TextView(this).apply {
            text = "🤖 FlateonBot2.0"
            textSize = 28f
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
        }

        val subtitulo = TextView(this).apply {
            text = "Painel de controle"
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 30)
        }

        val criarRota = Button(this).apply {
            text = "🗺️ Criar rota"

            setOnClickListener {
                mostrarCriadorDeRota()
            }
        }

        val minhasRotas = Button(this).apply {
            text = "📂 Minhas rotas"

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Minhas rotas serão adicionadas.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val ativarControle = Button(this).apply {
            text = "🎮 Ativar controle"

            setOnClickListener {
                abrirAcessibilidade()
            }
        }

        val iniciar = Button(this).apply {
            text = "▶️ Iniciar painel"

            setOnClickListener {
                iniciarPainel()
            }
        }

        val parar = Button(this).apply {
            text = "⏹️ Parar painel"

            setOnClickListener {
                FlateonAccessibilityService.pararPainel()

                Toast.makeText(
                    this@MainActivity,
                    "Painel parado.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val configuracoes = Button(this).apply {
            text = "⚙️ Configurações"

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Configurações serão adicionadas.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        layoutPrincipal.addView(titulo)
        layoutPrincipal.addView(subtitulo)
        layoutPrincipal.addView(criarRota)
        layoutPrincipal.addView(minhasRotas)
        layoutPrincipal.addView(ativarControle)
        layoutPrincipal.addView(iniciar)
        layoutPrincipal.addView(parar)
        layoutPrincipal.addView(configuracoes)

        setContentView(layoutPrincipal)
    }

    private fun abrirAcessibilidade() {

        try {

            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)

            startActivity(intent)

            Toast.makeText(
                this,
                "Procure por FlateonBot2.0 em Serviços instalados e ative o serviço.",
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "Não foi possível abrir as configurações de acessibilidade.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun iniciarPainel() {

        if (!FlateonAccessibilityService.estaAtivo()) {

            Toast.makeText(
                this,
                "Primeiro ative o FlateonBot2.0 em Acessibilidade.",
                Toast.LENGTH_LONG
            ).show()

            abrirAcessibilidade()
            return
        }

        FlateonAccessibilityService.iniciarPainel()

        Toast.makeText(
            this,
            "Painel do FlateonBot iniciado.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun mostrarCriadorDeRota() {

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 40, 32, 32)
        }

        val titulo = TextView(this).apply {
            text = "🗺️ Criar nova rota"
            textSize = 26f
            gravity = Gravity.CENTER
        }

        val nomeRota = EditText(this).apply {
            hint = "Nome da rota"
            textSize = 18f
        }

        val listaPontos = TextView(this).apply {
            text = "Nenhum ponto adicionado."
            textSize = 17f
            setPadding(0, 30, 0, 30)
        }

        val adicionarPonto = Button(this).apply {
            text = "➕ Adicionar ponto"

            setOnClickListener {

                val numero = pontosDaRota.size + 1

                pontosDaRota.add("Ponto $numero")

                listaPontos.text = pontosDaRota.joinToString(
                    separator = "\n"
                )

                Toast.makeText(
                    this@MainActivity,
                    "Ponto $numero adicionado!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val salvar = Button(this).apply {
            text = "💾 Salvar rota"

            setOnClickListener {

                val nome = nomeRota.text.toString().trim()

                if (nome.isEmpty()) {

                    Toast.makeText(
                        this@MainActivity,
                        "Digite um nome para a rota.",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

                Toast.makeText(
                    this@MainActivity,
                    "Rota \"$nome\" salva com ${pontosDaRota.size} ponto(s).",
                    Toast.LENGTH_LONG
                ).show()

                pontosDaRota.clear()

                mostrarTelaPrincipal()
            }
        }

        val voltar = Button(this).apply {
            text = "⬅️ Voltar"

            setOnClickListener {
                pontosDaRota.clear()
                mostrarTelaPrincipal()
            }
        }

        layout.addView(titulo)
        layout.addView(nomeRota)
        layout.addView(listaPontos)
        layout.addView(adicionarPonto)
        layout.addView(salvar)
        layout.addView(voltar)

        setContentView(layout)
    }
}

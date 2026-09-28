package com.flateonbot20

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.graphics.Color
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
            setPadding(32, 48, 32, 32)
        }

        val titulo = TextView(this).apply {
            text = "FlateonBot2.0"
            textSize = 30f
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
        }

        val subtitulo = TextView(this).apply {
            text = "Automação e gerenciamento de rotas"
            textSize = 17f
            gravity = Gravity.CENTER
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
                    "As rotas salvas serão mostradas aqui.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val iniciar = Button(this).apply {
            text = "▶️ Iniciar"

            setOnClickListener {
                iniciarPainel()
            }
        }

        val pausar = Button(this).apply {
            text = "⏸️ Pausar"

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Bot pausado.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val parar = Button(this).apply {
            text = "⏹️ Parar"

            setOnClickListener {
                pararPainel()
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
        layoutPrincipal.addView(iniciar)
        layoutPrincipal.addView(pausar)
        layoutPrincipal.addView(parar)
        layoutPrincipal.addView(configuracoes)

        setContentView(layoutPrincipal)
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

    private fun iniciarPainel() {

        if (!Settings.canDrawOverlays(this)) {

            Toast.makeText(
                this,
                "Precisamos permitir a sobreposição na tela.",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )

            startActivity(intent)

        } else {

            val intent = Intent(this, BotOverlayService::class.java)

            startService(intent)

            Toast.makeText(
                this,
                "Painel do FlateonBot iniciado.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun pararPainel() {

        val intent = Intent(this, BotOverlayService::class.java)

        stopService(intent)

        Toast.makeText(
            this,
            "Painel do FlateonBot parado.",
            Toast.LENGTH_SHORT
        ).show()
    }
}

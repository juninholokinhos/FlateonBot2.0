package com.flateonbot20

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        criarTela()
    }

    private fun criarTela() {

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val titulo = TextView(this).apply {
            text = "FlateonBot2.0"
            textSize = 30f
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
        }

        val subtitulo = TextView(this).apply {
            text = "\nAutomação e gerenciamento de rotas"
            textSize = 17f
            gravity = Gravity.CENTER
        }

        val criarRota = Button(this).apply {
            text = "🗺️ Criar rota"

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Criador de rotas será adicionado em seguida.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val minhasRotas = Button(this).apply {
            text = "📂 Minhas rotas"

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Suas rotas aparecerão aqui.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val iniciar = Button(this).apply {
            text = "▶️ Iniciar"

            setOnClickListener {
                Toast.makeText(
                    this@MainActivity,
                    "Bot iniciado.",
                    Toast.LENGTH_SHORT
                ).show()
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
                Toast.makeText(
                    this@MainActivity,
                    "Bot parado.",
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

        layout.addView(titulo)
        layout.addView(subtitulo)
        layout.addView(criarRota)
        layout.addView(minhasRotas)
        layout.addView(iniciar)
        layout.addView(pausar)
        layout.addView(parar)
        layout.addView(configuracoes)

        setContentView(layout)
    }
}

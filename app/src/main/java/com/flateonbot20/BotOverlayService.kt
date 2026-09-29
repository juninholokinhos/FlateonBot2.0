package com.flateonbot20

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class BotOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var painel: View

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        criarPainel()
    }

    private fun criarPainel() {

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            setBackgroundColor(Color.rgb(30, 30, 30))
        }

        val titulo = TextView(this).apply {
            text = "FlateonBot2.0"
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val status = TextView(this).apply {
            text = "Pontos: 0"
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val adicionar = Button(this).apply {
            text = "Adicionar ponto"

            setOnClickListener {
                status.text = "Ponto adicionado"
            }
        }

        val fechar = Button(this).apply {
            text = "Fechar"

            setOnClickListener {
                stopSelf()
            }
        }

        layout.addView(titulo)
        layout.addView(status)
        layout.addView(adicionar)
        layout.addView(fechar)

        painel = layout

        val tipoJanela =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }

        val parametros = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            tipoJanela,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        parametros.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        parametros.y = 100

        try {

            windowManager.addView(painel, parametros)

            Toast.makeText(
                this,
                "Painel criado com sucesso.",
                Toast.LENGTH_SHORT
            ).show()

        } catch (erro: Exception) {

            Toast.makeText(
                this,
                "Erro: ${erro.javaClass.simpleName} - ${erro.message}",
                Toast.LENGTH_LONG
            ).show()

            stopSelf()
        }
    }

    override fun onDestroy() {

        if (::painel.isInitialized) {
            try {
                windowManager.removeView(painel)
            } catch (erro: Exception) {
            }
        }

        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

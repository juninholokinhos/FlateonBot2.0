package com.flateonbot20

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Color
import android.graphics.Path
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlin.math.sqrt

class FlateonAccessibilityService : AccessibilityService() {

    private var windowManager: WindowManager? = null
    private var painel: View? = null

    private var joystickBaseX = 0f
    private var joystickBaseY = 0f

    companion object {

        private var instancia: FlateonAccessibilityService? = null

        fun estaAtivo(): Boolean {
            return instancia != null
        }

        fun iniciarPainel() {
            instancia?.mostrarPainel()
        }

        fun pararPainel() {
            instancia?.removerPainel()
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        instancia = this

        Toast.makeText(
            this,
            "FlateonBot2.0 ativado.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onAccessibilityEvent(event: android.view.accessibility.AccessibilityEvent?) {
        // O serviço não precisa processar eventos neste estágio.
    }

    override fun onInterrupt() {
        // Serviço interrompido pelo sistema.
    }

    override fun onDestroy() {

        removerPainel()

        instancia = null

        super.onDestroy()
    }

    private fun mostrarPainel() {

        if (painel != null) {
            return
        }

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(12, 12, 12, 12)
            setBackgroundColor(Color.argb(210, 20, 20, 20))
        }

        val titulo = TextView(this).apply {
            text = "🤖 FlateonBot"
            textSize = 16f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
        }

        val status = TextView(this).apply {
            text = "Joystick pronto"
            textSize = 12f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(8, 4, 8, 8)
        }

        val joystick = View(this).apply {

            setBackgroundColor(Color.DKGRAY)

            setOnTouchListener { view, event ->

                when (event.action) {

                    MotionEvent.ACTION_DOWN -> {

                        joystickBaseX = event.x
                        joystickBaseY = event.y

                        status.text = "Joystick ativo"

                        true
                    }

                    MotionEvent.ACTION_MOVE -> {

                        val dx = event.x - joystickBaseX
                        val dy = event.y - joystickBaseY

                        val distancia = sqrt(
                            dx * dx + dy * dy
                        )

                        if (distancia > 25f) {

                            enviarMovimento(
                                dx,
                                dy
                            )
                        }

                        true
                    }

                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> {

                        status.text = "Joystick parado"

                        true
                    }

                    else -> true
                }
            }
        }

        val fechar = Button(this).apply {

            text = "Fechar painel"

            setOnClickListener {
                removerPainel()
            }
        }

        layout.addView(
            titulo,
            LinearLayout.LayoutParams(
                260,
                60
            )
        )

        layout.addView(
            status,
            LinearLayout.LayoutParams(
                260,
                50
            )
        )

        layout.addView(
            joystick,
            LinearLayout.LayoutParams(
                260,
                260
            )
        )

        layout.addView(
            fechar,
            LinearLayout.LayoutParams(
                260,
                60
            )
        )

        val params = WindowManager.LayoutParams(
            300,
            440,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.CENTER_VERTICAL or Gravity.START
        params.x = 20
        params.y = 0

        try {

            windowManager?.addView(
                layout,
                params
            )

            painel = layout

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "Não foi possível abrir o painel.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun removerPainel() {

        val view = painel ?: return

        try {
            windowManager?.removeView(view)
        } catch (_: Exception) {
        }

        painel = null
    }

    private fun enviarMovimento(
        dx: Float,
        dy: Float
    ) {

        val centroX = resources.displayMetrics.widthPixels / 2f
        val centroY = resources.displayMetrics.heightPixels / 2f

        val distancia = sqrt(
            dx * dx + dy * dy
        )

        if (distancia < 20f) {
            return
        }

        val normalX = dx / distancia
        val normalY = dy / distancia

        val tamanho = 350f

        val destinoX = centroX + normalX * tamanho
        val destinoY = centroY + normalY * tamanho

        val path = Path()

        path.moveTo(
            centroX,
            centroY
        )

        path.lineTo(
            destinoX,
            destinoY
        )

        val stroke =
            GestureDescription.StrokeDescription(
                path,
                0,
                250
            )

        val gesture =
            GestureDescription.Builder()
                .addStroke(stroke)
                .build()

        dispatchGesture(
            gesture,
            null,
            null
        )
    }
}

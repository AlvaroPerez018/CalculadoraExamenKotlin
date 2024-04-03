package com.adso.calculadoraexamen

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var tvRes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRes = findViewById(R.id.tvRes)
    }

    fun calcular(view: View) {
        val boton = view as Button
        val textoBoton = boton.text.toString()
        val concatenar = tvRes?.text.toString() + textoBoton
        val mostrar = quitarCerosizquierda(concatenar)

        tvRes?.text = mostrar
    }

    fun quitarCerosizquierda(str: String): String {
        var i = 0
        while (i < str.length && str[i] == '0') i++
        val sb = StringBuffer(str)
        sb.replace(0, i, "")
        return sb.toString()
    }

    fun sumar(view: View) {
        realizarOperacion('+')
    }

    fun restar(view: View) {
        realizarOperacion('-')
    }

    fun multiplicar(view: View) {
        realizarOperacion('*')
    }

    fun dividir(view: View) {
        realizarOperacion('/')
        tvRes?.text = "/"
    }

    fun borrar(view: View) {
        tvRes?.text = ""
    }


    private fun realizarOperacion(operador: Char) {
        val expresion = tvRes?.text.toString()
        val resultado: String = try {
            evaluarExpresion(expresion)
        } catch (e: ArithmeticException) {
            "Error"
        }

        tvRes?.text = resultado
    }

    private fun evaluarExpresion(expresion: String): String {


        val partes = expresion.split(Regex("(?=[+*/-])|(?<=[+*/-])"))


        val operando1 = partes[0].toDouble()
        val operando2 = partes[2].toDouble()
        val operador = partes[1]


        return when (operador) {
            "+" -> (operando1 + operando2).toString()
            "-" -> (operando1 - operando2).toString()
            "*" -> (operando1 * operando2).toString()
            "/" -> {
                if (operando2 == 0.0) {
                    throw ArithmeticException("División por cero")
                } else {
                    (operando1 / operando2).toString()
                }
            }
            else -> throw IllegalArgumentException("Operador no válido: $operador")
        }
    }
}
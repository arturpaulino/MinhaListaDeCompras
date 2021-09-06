package com.example.listadecompras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val txt_produto = findViewById<EditText>(R.id.txt_produto)
        val txt_qtd =  findViewById<EditText>(R.id.txt_qtd)
        val txt_valor = findViewById<EditText>(R.id.txt_valor)


        val btn_inserir = findViewById<Button>(R.id.btn_inserir)
        btn_inserir.setOnClickListener{

            //pegando os valores digitados pelo usuário
            val produto = txt_produto.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_valor.text.toString()


            if (produto.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {
                val prod = Produto(produto, qtd.toInt(), valor.toDouble() )

                produtosGlobal.add(prod)
                txt_produto.text.clear()
                txt_qtd.text.clear()
                txt_valor.text.clear()
                txt_produto.text.clear()
            }else{

                txt_produto.error = if (txt_produto.text.isEmpty()) "Preencha o nome do produto" else null
                txt_qtd.error = if (txt_qtd.text.isEmpty()) "Preencha a quantidade" else null
                txt_valor.error = if (txt_valor.text.isEmpty()) "Preencha o valor" else null
            }
        }

    }
}
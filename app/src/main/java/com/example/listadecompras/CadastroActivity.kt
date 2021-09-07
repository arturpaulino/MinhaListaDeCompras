package com.example.listadecompras

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class CadastroActivity : AppCompatActivity() {
    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val txt_produto = findViewById<EditText>(R.id.txt_produto)
        val txt_qtd = findViewById<EditText>(R.id.txt_qtd)
        val txt_valor = findViewById<EditText>(R.id.txt_valor)
        val btn_inserir = findViewById<Button>(R.id.btn_inserir)
        val img_foto_produto = findViewById<ImageView>(R.id.img_foto_produto)

        img_foto_produto.setOnClickListener {
            abrirGaleria()
        }


        btn_inserir.setOnClickListener {

            //pegando os valores digitados pelo usuário
            val produto = txt_produto.text.toString()
            val qtd = txt_qtd.text.toString()
            val valor = txt_valor.text.toString()


            if (produto.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {
              ///  val prod = Produto(produto, qtd.toInt(), valor.toDouble())
                val prod = Produto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)
                produtosGlobal.add(prod)
                txt_produto.text.clear()
                txt_qtd.text.clear()
                txt_valor.text.clear()
                txt_produto.text.clear()
            } else {

                txt_produto.error =
                    if (txt_produto.text.isEmpty()) "Preencha o nome do produto" else null
                txt_qtd.error = if (txt_qtd.text.isEmpty()) "Preencha a quantidade" else null
                txt_valor.error = if (txt_valor.text.isEmpty()) "Preencha o valor" else null
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)
        val img_foto_produto = findViewById<ImageView>(R.id.img_foto_produto)
        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                //lendo a uri com a imagem
                val inputStream = contentResolver.openInputStream(data.getData()!!);
                //transformando o resultado em bitmap
                 imageBitMap = BitmapFactory.decodeStream(inputStream)
                //Exibir a imagem no aplicativo
                img_foto_produto.setImageBitmap(imageBitMap)


            }
        }
    }

    fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser( intent, "Selecion uma imagem "), COD_IMAGE)
    }




}
package com.example.listadecompras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.listadecompras.databinding.ActivityCadastroBinding
import com.example.listadecompras.databinding.ActivityMainBinding
import com.example.listadecompras.databinding.ListViewItemBinding
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import java.text.NumberFormat
import java.util.*

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val produtosAdapter = ProdutoAdapter(this)
        binding.listViewProdutos.adapter = produtosAdapter;

        binding.btnAdicionar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(
                intent
            )
        }

        //definição do ouvinte da lista para clicks longos
        binding.listViewProdutos.setOnItemLongClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            //buscando o item clicado
            val item = produtosAdapter.getItem(i)
            //removendo o item clicado da lista
            produtosAdapter.remove(item)

            //deletando do banco de dados
            deletarProduto( item?.id!!)
            toast("item deletado com sucesso")

            true
        }

        val view = binding.root
        setContentView(view)

    }

    fun deletarProduto(idProduto:Int) {
        database.use {
            delete("produtos", "id = {id}", "id" to idProduto)
        }
    }

    override fun onResume() {
        super.onResume()
        val adapter = binding.listViewProdutos.adapter as ProdutoAdapter
        database.use{

            select("produtos").exec {

                val parser = rowParser {

                        id: Int, nome: String,
                        quantidade: Int,
                        valor:Double,
                        foto:ByteArray? ->
                    //Colunas do banco de dados


                    //Montagem do objeto Produto com as colunas do banco
                    Produto(id, nome, quantidade, valor, foto?.toBitmap() )
                }


                var listaProdutos = parseList(parser)


                adapter.clear()
                adapter.addAll(listaProdutos)


                val soma = listaProdutos.sumByDouble { it.valor * it.quantidade }

                val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
               binding.txtTotal.text = "TOTAL: ${ f.format(soma)}"

            }

        }




    }
}
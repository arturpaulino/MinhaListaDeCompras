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

            true
        }

        val view = binding.root
        setContentView(view)

    }

    override fun onResume() {
        super.onResume()
        val adapter = binding.listViewProdutos.adapter as ProdutoAdapter
        adapter.clear()
        adapter.addAll(produtosGlobal)
    }
}
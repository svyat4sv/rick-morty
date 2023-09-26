package com.example.rick_morty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rick_morty.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val adapter = MyAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val characterApi = retrofit.create(CharacterApi::class.java)
        binding.rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rcView.adapter = adapter
        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
                Pager(
                    // Configure how data is loaded by passing additional properties to
                    // PagingConfig, such as prefetchDistance.
                    PagingConfig(pageSize = 20)
                ) {
                    ResultPagingSource(characterApi)
                }.flow.cachedIn(CoroutineScope(Dispatchers.Main)).collectLatest{
                    print(it.toString())
                    adapter.submitData(it)
                }

            }
//        }


        CoroutineScope(Dispatchers.IO).launch {
            val characterList = characterApi.getAllCharacters()
            CoroutineScope(Dispatchers.Main).launch {




                binding.updateBtn.setOnClickListener {
                    val newCharacterList = characterList.results.shuffled()
                    adapter.submitData(newCharacterList.toMutableList())
                }
            }
        }

    }


}
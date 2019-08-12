package io.github.rybalkinsd.kohttp.android_kohttp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.ivsivak.android_kohttp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var adapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        adapter = RepositoryAdapter()
        mainRepositoryList.layoutManager = LinearLayoutManager(baseContext)
        mainRepositoryList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.responseLiveData.observe(this, Observer {
            mainTextView.text = it.status
            adapter.update(it.repositories)
        })
    }
}

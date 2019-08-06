package io.github.ivsivak.android_kohttp

import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    lateinit var adapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainRepositoryList.layoutManager = LinearLayoutManager(baseContext)
        adapter = RepositoryAdapter()
        mainRepositoryList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.responseLiveData.observe(this, Observer {
            adapter.update(it.list)
            mainTextView.visibility = GONE
        })
    }
}

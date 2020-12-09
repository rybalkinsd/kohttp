package io.github.rybalkinsd.kohttp.android_kohttp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.ivsivak.android_kohttp.R
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private val flits = mutableListOf<Flit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_repository, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = flits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(flits[position])
    }

    fun update(flits: List<Flit>) {
        this.flits.clear()
        this.flits.addAll(flits)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Flit) {
            itemView.name.text = 
                """
                    ${item.userName}
                    ${item.content}
                """.trimIndent()
        }
    }
}

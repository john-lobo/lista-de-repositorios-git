package com.jlndev.listaderepositriosgit.view.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jlndev.listaderepositriosgit.databinding.ItemRepositoryBinding
import com.jlndev.listaderepositriosgit.utils.ext.gone
import com.jlndev.listaderepositriosgit.view.home.adapter.model.GitRepositoryItem
import javax.sql.DataSource

class GitRepositoriesAdapter(private val listener: RepositoryAdapterListener) : RecyclerView.Adapter<GitRepositoriesAdapter.RepositoryViewHolder>() {

    private var repositories = emptyList<GitRepositoryItem>()

    fun submitList(newRepositories: List<GitRepositoryItem>) {
        val oldRepositories = repositories.toList()
        repositories = newRepositories
        val diffResult = DiffUtil.calculateDiff(RepositoriesDiffCallback(oldRepositories, newRepositories))
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val itemBinding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    inner class RepositoryViewHolder(
        private val itemBinding: ItemRepositoryBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(repository: GitRepositoryItem) {
            itemBinding.run {
                itemRepositoryNameView.text = "Repositório: ${repository.repositoryName}"
                itemOwnerNameView.text = "Autor: ${repository.ownerName}"
                itemTitleStarView.text =   "Estrelas: ${repository.stargazersCount}"
                itemTitleForkView.text =  "Forks: ${repository.forksCount}"

                Glide.with(itemView)
                    .load(repository.avatarUrl)
                    .fitCenter()
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                            loadingItemView.gone()
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean): Boolean {
                            loadingItemView.gone()
                            return false
                        }
                        
                    })
                    .into(itemImageview)

                itemView.setOnClickListener {
                    listener.onRepositoryClicked(repository)
                }
            }
        }
    }

    class RepositoriesDiffCallback(
        private val oldRepositories: List<GitRepositoryItem>,
        private val newRepositories: List<GitRepositoryItem>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldRepositories.size

        override fun getNewListSize(): Int = newRepositories.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldRepositories[oldItemPosition].repositoryId == newRepositories[newItemPosition].repositoryId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldRepositories[oldItemPosition] == newRepositories[newItemPosition]
        }
    }

    interface RepositoryAdapterListener {
        fun onRepositoryClicked(repository: GitRepositoryItem)
    }
}

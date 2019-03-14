package de.adessomobile.postbookchallenge.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.adessomobile.postbookchallenge.R
import de.adessomobile.postbookchallenge.databinding.ItemPostBinding

/**
 * RecyclerAdapter to display the posts in the PostsActivity
 */
class PostsRecyclerAdapter(
    private val onPostClickListener: OnPostClickListener,
    private val onPostFavoriteClickListener: OnPostFavoriteClickListener
) : ListAdapter<PostPresentationModel, PostsRecyclerAdapter.PostViewHolder>(PostDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemPostBinding>(inflater, R.layout.item_post, parent, false)
        return PostViewHolder(binding, onPostClickListener, onPostFavoriteClickListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int) = getItem(position).id.toLong()

    class PostViewHolder(
        private val binding: ItemPostBinding,
        private val onPostClickListener: OnPostClickListener,
        private val onPostFavoriteClickListener: OnPostFavoriteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostPresentationModel) {
            binding.item = post
            binding.executePendingBindings()
            binding.postCbFavorite.setOnClickListener {
                onPostFavoriteClickListener(post.id, binding.postCbFavorite.isChecked)
            }
            binding.root.setOnClickListener {
                onPostClickListener(post.id)
            }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<PostPresentationModel>() {

        override fun areItemsTheSame(oldItem: PostPresentationModel, newItem: PostPresentationModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PostPresentationModel, newItem: PostPresentationModel) =
            oldItem == newItem

    }
}

/**
 * Callback to use when a post was clicked by the user.
 */
typealias OnPostClickListener = (postId: Int) -> Unit

/**
 * Callback to use when the favorite state of a post was changed.
 */
typealias OnPostFavoriteClickListener = (postId: Int, favored: Boolean) -> Unit
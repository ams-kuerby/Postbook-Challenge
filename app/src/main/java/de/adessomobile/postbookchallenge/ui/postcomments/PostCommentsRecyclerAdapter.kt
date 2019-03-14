package de.adessomobile.postbookchallenge.ui.postcomments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.adessomobile.postbookchallenge.R
import de.adessomobile.postbookchallenge.databinding.ItemCommentsCommentBinding
import de.adessomobile.postbookchallenge.databinding.ItemCommentsPostBinding
import de.adessomobile.postbookchallenge.ui.postcomments.PostCommentsPresentationModel.CommentPresentationModel
import de.adessomobile.postbookchallenge.ui.postcomments.PostCommentsPresentationModel.PostPresentationModel

/**
 * RecyclerAdapter to display the post and its comments in the PostCommentsActivity
 */
class PostCommentsRecyclerAdapter(
    private val onPostFavoriteClickListener: OnPostFavoriteClickListener
) : ListAdapter<PostCommentsPresentationModel, RecyclerView.ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_POST -> {
                val binding = DataBindingUtil.inflate<ItemCommentsPostBinding>(
                    inflater,
                    R.layout.item_comments_post,
                    parent,
                    false
                )
                PostViewHolder(binding, onPostFavoriteClickListener)
            }
            VIEW_TYPE_COMMENT -> {
                val binding = DataBindingUtil.inflate<ItemCommentsCommentBinding>(
                    inflater,
                    R.layout.item_comments_comment,
                    parent,
                    false
                )
                CommentViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown View type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> holder.bind(getItem(position) as PostPresentationModel)
            is CommentViewHolder -> holder.bind(getItem(position) as CommentPresentationModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PostPresentationModel -> VIEW_TYPE_POST
            is CommentPresentationModel -> VIEW_TYPE_COMMENT
        }
    }

    class PostViewHolder(
        private val binding: ItemCommentsPostBinding,
        private val onPostFavoriteClickListener: OnPostFavoriteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostPresentationModel) {
            binding.item = post
            binding.executePendingBindings()
            binding.postCbFavorite.setOnClickListener {
                onPostFavoriteClickListener(post.id, binding.postCbFavorite.isChecked)
            }
        }
    }

    class CommentViewHolder(
        private val binding: ItemCommentsCommentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: CommentPresentationModel) {
            binding.item = comment
            binding.executePendingBindings()
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<PostCommentsPresentationModel>() {

        override fun areItemsTheSame(
            oldItem: PostCommentsPresentationModel,
            newItem: PostCommentsPresentationModel
        ): Boolean {
            return if (oldItem is PostPresentationModel && newItem is PostPresentationModel) {
                oldItem.id == newItem.id
            } else if (oldItem is CommentPresentationModel && newItem is CommentPresentationModel) {
                oldItem.id == newItem.id
            } else {
                false
            }
        }

        override fun areContentsTheSame(
            oldItem: PostCommentsPresentationModel,
            newItem: PostCommentsPresentationModel
        ) = oldItem == newItem
    }

    companion object {
        private const val VIEW_TYPE_POST = 1
        private const val VIEW_TYPE_COMMENT = 2
    }
}

/**
 * Callback to use when the favorite state of a post was changed.
 */
typealias OnPostFavoriteClickListener = (postId: Int, favored: Boolean) -> Unit
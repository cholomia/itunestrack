package com.pocholomia.itunestrack.features.tracks.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pocholomia.itunestrack.R
import com.pocholomia.itunestrack.databinding.ItemTrackBinding
import com.pocholomia.itunestrack.domain.model.Track

class TrackInnerListAdapter(
    private val onItemClickCallback: (Track) -> Unit
) : ListAdapter<Track, TrackInnerListAdapter.TrackViewHolder>(TrackDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder =
        TrackViewHolder(
            ItemTrackBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ),
            onItemClickCallback
        )

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TrackViewHolder(
        private val binding: ItemTrackBinding,
        private val onItemClickCallback: (Track) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            Glide.with(binding.root)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                )
                .load(track.artworkUrl100)
                .into(binding.imgTrack)
            binding.txtTrackName.text = track.title
            binding.txtPrice.text = track.priceDisplay
            binding.txtGenre.text = track.primaryGenreName
            binding.root.setOnClickListener { onItemClickCallback(track) }
        }
    }

    object TrackDiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
            oldItem.trackId == newItem.trackId

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
            oldItem == newItem
    }

}
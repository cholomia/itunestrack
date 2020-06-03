package com.pocholomia.itunestrack.features.tracks.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pocholomia.itunestrack.databinding.ItemTrackHeaderBinding
import com.pocholomia.itunestrack.databinding.ItemTrackListBinding
import com.pocholomia.itunestrack.domain.model.Track

class TrackListAdapter(
    private val onItemClickCallback: (Track) -> Unit
) : ListAdapter<TrackDisplay, RecyclerView.ViewHolder>(TrackDisplayDiff) {

    companion object {
        private const val VIEW_HEADER = 1
        private const val VIEW_ITEM = 2
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is TrackDisplay.Header -> VIEW_HEADER
        is TrackDisplay.Items -> VIEW_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_HEADER -> HeaderViewHolder(
                ItemTrackHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            VIEW_ITEM -> ItemViewHolder(
                ItemTrackListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onItemClickCallback
            )
            else -> throw IllegalStateException("unknown viewType: $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val trackDisplay = getItem(position)) {
            is TrackDisplay.Header -> (holder as HeaderViewHolder).bind(trackDisplay.title)
            is TrackDisplay.Items -> (holder as ItemViewHolder).bind(trackDisplay.tracks)
        }
    }

    class HeaderViewHolder(private val binding: ItemTrackHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: String) {
            binding.txtHeader.text = header
        }
    }

    class ItemViewHolder(
        binding: ItemTrackListBinding,
        onItemClickCallback: (Track) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val adapter = TrackInnerListAdapter(onItemClickCallback)

        init {
            binding.recyclerviewTrack.layoutManager =
                GridLayoutManager(binding.root.context, 2, GridLayoutManager.HORIZONTAL, false)
            binding.recyclerviewTrack.adapter = adapter
        }

        fun bind(tracks: List<Track>) {
            adapter.submitList(tracks)
        }
    }

    object TrackDisplayDiff : DiffUtil.ItemCallback<TrackDisplay>() {
        override fun areItemsTheSame(oldItem: TrackDisplay, newItem: TrackDisplay): Boolean = when {
            oldItem is TrackDisplay.Header && newItem is TrackDisplay.Header -> oldItem.title == newItem.title
            oldItem is TrackDisplay.Items && newItem is TrackDisplay.Items -> oldItem.tracks.map { "${it.trackId}" }
                .joinToString { ";" } == newItem.tracks.map { "${it.trackId}" }
                .joinToString { ";" }
            else -> false
        }

        override fun areContentsTheSame(oldItem: TrackDisplay, newItem: TrackDisplay): Boolean =
            when {
                oldItem is TrackDisplay.Header && newItem is TrackDisplay.Header -> oldItem.title == newItem.title
                oldItem is TrackDisplay.Items && newItem is TrackDisplay.Items -> oldItem.tracks == newItem.tracks
                else -> false
            }

    }

}
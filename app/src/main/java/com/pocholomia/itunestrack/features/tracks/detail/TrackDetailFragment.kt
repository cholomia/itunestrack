package com.pocholomia.itunestrack.features.tracks.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pocholomia.itunestrack.R
import com.pocholomia.itunestrack.databinding.FragmentTrackDetailBinding
import com.pocholomia.itunestrack.di.factory.ViewModelFactory
import com.pocholomia.itunestrack.utils.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TrackDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: TrackDetailViewModel

    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentTrackDetailBinding? = null
    private val binding get() = _binding!!

    private var trackId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trackId = TrackDetailFragmentArgs.fromBundle(requireArguments()).trackId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = withViewModel(this, viewModelFactory) {
            observe(observableState, ::render)
            dispatch(TrackDetailViewModel.Action.GetTrack(trackId))
        }
    }

    private fun render(state: TrackDetailViewModel.State) {
        state.error?.getContentIfNotHandled()?.let {
            requireContext().showErrorDialog(
                it.localizedMessage ?: "Error occurred. Please try again"
            )
        }

        Glide.with(binding.root)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
            )
            .load(state.track?.artworkUrl100)
            .into(binding.imgTrack)
        binding.txtTrackName.text = state.track?.title
        binding.txtArtist.text = state.track?.artistName
        binding.txtPrice.text = state.track?.priceDisplay
        binding.txtGenre.text = state.track?.primaryGenreName
        binding.txtReleaseDate.text = state.track?.releaseDate
            ?.toDate("yyyy-MM-dd'T'HH:mm:ssZ")
            ?.toReadableString("MMMM dd, yyyy")
        binding.txtDescription.text =
            state.track?.longDescription ?: state.track?.description ?: "No Description Available"
    }

}
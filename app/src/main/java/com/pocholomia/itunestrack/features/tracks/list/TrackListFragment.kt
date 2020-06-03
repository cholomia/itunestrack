package com.pocholomia.itunestrack.features.tracks.list

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pocholomia.itunestrack.databinding.FragmentTrackListBinding
import com.pocholomia.itunestrack.di.factory.ViewModelFactory
import com.pocholomia.itunestrack.utils.PreferenceHelper.get
import com.pocholomia.itunestrack.utils.PreferenceHelper.put
import com.pocholomia.itunestrack.utils.observe
import com.pocholomia.itunestrack.utils.showErrorDialog
import com.pocholomia.itunestrack.utils.toReadableString
import com.pocholomia.itunestrack.utils.withViewModel
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

class TrackListFragment : DaggerFragment() {

    companion object {
        private const val PREVIOUS_VISIT = "previous_visit"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var viewModel: TrackListViewModel
    private lateinit var adapter: TrackListAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentTrackListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrackListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.txtPreviousVisitDate.text =
            sharedPreferences.get(PREVIOUS_VISIT, "No previous visit yet")
        val previousVisit = "Previous Visit: ${Date().toReadableString("MMMM dd, yyyy hh:mm aa")}"
        sharedPreferences.put(PREVIOUS_VISIT, previousVisit)

        viewModel = withViewModel(this, viewModelFactory) {
            observe(observableState, ::render)
        }

        adapter = TrackListAdapter {
            findNavController().navigate(TrackListFragmentDirections.openDetail(it.trackId))
        }

        // for smooth scrolling of inner recyclerview
        binding.recyclerview.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                if (e.action == MotionEvent.ACTION_DOWN &&
                    rv.scrollState == RecyclerView.SCROLL_STATE_SETTLING
                ) {
                    rv.stopScroll()
                }
                return false
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.dispatch(TrackListViewModel.Action.Refresh) }
    }

    private fun render(state: TrackListViewModel.State) {
        binding.swipeRefreshLayout.isRefreshing = state.isLoading

        state.error?.getContentIfNotHandled()?.let {
            requireContext().showErrorDialog(
                it.localizedMessage ?: "Error occurred. Please try again"
            )
        }
        adapter.submitList(state.tracks)
    }

}
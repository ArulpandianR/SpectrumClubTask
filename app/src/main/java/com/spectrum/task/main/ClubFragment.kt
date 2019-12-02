package com.spectrum.task.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.spectrum.task.adapter.CompanyAdapter
import com.spectrum.task.databinding.ClubFragmentBinding


class ClubFragment : Fragment() {

    companion object {
        fun newInstance() = ClubFragment()
    }

    private lateinit var viewDataBinding: ClubFragmentBinding
    private lateinit var viewModel: ClubViewModel
    private lateinit var companyAdapter: CompanyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ClubViewModel(requireActivity().application)
        viewDataBinding = ClubFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(ClubViewModel::class.java)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
    }

    /**
     * Implemented CompanyAdapter List adapter
     */
    private fun setupListAdapter() {
        companyAdapter = CompanyAdapter()
        viewDataBinding.companyList.setHasFixedSize(true)

        viewModel?.companyList?.observe(this, androidx.lifecycle.Observer { list ->
            if (!list.isNullOrEmpty()) {
                companyAdapter.submitList(list)
                viewDataBinding.companyList.adapter = companyAdapter
            }
        })
        viewModel?.isLoadingLiveData?.observe(this, androidx.lifecycle.Observer { isLoading ->
            if (isLoading) {
                showProgressBar()
            } else {
                if (viewModel.companyList.value.isNullOrEmpty()) {
                    showDataFound()
                } else {
                    showRecycler()
                }
            }
        })

        viewModel?.loadData(requireActivity().application)

        searchListener()

    }

    /**
     * Implemented Search Listener
     */
    private fun searchListener() {
        viewDataBinding.searchInput.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                (viewDataBinding.companyList.adapter as CompanyAdapter).submitList(
                    viewModel?.getFilteredList(
                        newText
                    ) ?: emptyList()
                )
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })
    }

    /**
     * To show progress bar when network call started
     */
    private fun showProgressBar() {
        viewDataBinding.companyList.visibility = View.GONE
        viewDataBinding.noData.visibility = View.GONE
        viewDataBinding.progressBar.visibility = View.VISIBLE
    }

    /**
     * To show Recycler view when network call success
     */
    private fun showRecycler() {
        viewDataBinding.companyList.visibility = View.VISIBLE
        viewDataBinding.noData.visibility = View.GONE
        viewDataBinding.progressBar.visibility = View.GONE
    }

    /**
     * To show no data found view when network call data has no data
     */
    private fun showDataFound() {
        viewDataBinding.companyList.visibility = View.GONE
        viewDataBinding.noData.visibility = View.VISIBLE
        viewDataBinding.progressBar.visibility = View.GONE
    }

}

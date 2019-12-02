package com.spectrum.task.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.spectrum.task.R
import com.spectrum.task.adapter.MemberAdapter
import com.spectrum.task.databinding.MemberFragmentBinding
import kotlinx.android.synthetic.main.member_fragment.*
import kotlinx.android.synthetic.main.member_sort_layout.view.*

class MemberFragment : Fragment() {

    companion object {
        fun newInstance() = MemberFragment()
    }

    private lateinit var viewDataBinding: MemberFragmentBinding
    private lateinit var viewModel: MemberViewModel
    private lateinit var memberAdapter: MemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = MemberViewModel(requireActivity().application)
        viewDataBinding = MemberFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(MemberViewModel::class.java)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setFilter()
    }

    /**
     * Implemented MemberList adapter
     */
    private fun setupListAdapter() {
        memberAdapter = MemberAdapter()
        viewDataBinding.companyList.setHasFixedSize(true)

        viewModel.memberList.observe(this, androidx.lifecycle.Observer { list ->
            if (!list.isNullOrEmpty()) {
                memberAdapter.submitList(list)
                viewDataBinding.companyList.adapter = memberAdapter
            }
        })
        searchListener()
    }

    /**
     * Implemented Search Listener
     */
    private fun searchListener() {
        viewDataBinding.searchInput.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                (viewDataBinding.companyList.adapter as MemberAdapter).submitList(
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

    private fun setFilter() {
        filterFAB.setOnClickListener {
            setAlertDialog()
        }
    }

    private fun setAlertDialog() {
        /**
         * Inflate the dialog with custom view
         */
        val mDialogView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.member_sort_layout, null)
        /**
         *  AlertDialogBuilder
         */
        val mBuilder = AlertDialog.Builder(requireActivity())
            .setView(mDialogView)
            .setTitle("Sorting By Name")
        /**
         *  show dialog
         */
        val mAlertDialog = mBuilder.show()
        /**
         * login button click of custom layout
         */
        mDialogView.dialogNameAscending.setOnClickListener {
            mAlertDialog.dismiss()
            viewDataBinding.searchInput.setQuery("", false)
            viewDataBinding.searchInput.clearFocus()

            if (!viewModel.memberList.value.isNullOrEmpty()) {
                (viewDataBinding.companyList.adapter as MemberAdapter).submitList(
                    viewModel.memberList.value!!.sortedBy { (it.name.first + it.name.last) }
                )
                Toast.makeText(
                    requireContext(),
                    "Sorted by member name ascending",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        mDialogView.dialogNameDescending.setOnClickListener {
            mAlertDialog.dismiss()
            viewDataBinding.searchInput.setQuery("", false)
            viewDataBinding.searchInput.clearFocus()
            if (!viewModel.memberList.value.isNullOrEmpty()) {
                (viewDataBinding.companyList.adapter as MemberAdapter).submitList(
                    viewModel.memberList.value!!.sortedByDescending { (it.name.first + it.name.last) }
                )
                Toast.makeText(
                    requireContext(),
                    "Sorted by member name descending",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        mDialogView.dialogAgeAscending.setOnClickListener {
            mAlertDialog.dismiss()
            viewDataBinding.searchInput.setQuery("", false)
            viewDataBinding.searchInput.clearFocus()

            if (!viewModel.memberList.value.isNullOrEmpty()) {
                (viewDataBinding.companyList.adapter as MemberAdapter).submitList(
                    viewModel.memberList.value!!.sortedBy { (it.age) }
                )
                Toast.makeText(
                    requireContext(),
                    "Sorted by member Age ascending",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        mDialogView.dialogAgeDescending.setOnClickListener {
            mAlertDialog.dismiss()
            viewDataBinding.searchInput.setQuery("", false)
            viewDataBinding.searchInput.clearFocus()
            if (!viewModel.memberList.value.isNullOrEmpty()) {
                (viewDataBinding.companyList.adapter as MemberAdapter).submitList(
                    viewModel.memberList.value!!.sortedByDescending { (it.age) }
                )
                Toast.makeText(
                    requireContext(),
                    "Sorted by member Age descending",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}

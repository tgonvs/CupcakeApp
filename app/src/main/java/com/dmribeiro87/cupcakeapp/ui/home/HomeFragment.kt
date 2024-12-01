package com.dmribeiro87.cupcakeapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmribeiro87.cupcakeapp.MainActivity
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentHomeBinding
import com.dmribeiro87.cupcakeapp.ui.cart.CartViewModel
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var homeAdapter: HomeAdapter
    private var menuNotificationTextView: TextView? = null
    private val cartViewModel: CartViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val window: Window = requireActivity().window
        window.statusBarColor = resources.getColor(R.color.brown)
        val mActivity = (activity as MainActivity).supportActionBar
        mActivity?.setBackgroundDrawable(
            resources.getDrawable(
                R.color.brown,
                resources.newTheme()
            )
        )
        setHasOptionsMenu(true)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cart, menu)
        val actionNotificationIcon: View? = menu.findItem(R.id.action_cart).actionView
        menuNotificationTextView = actionNotificationIcon?.findViewById(R.id.tv_counter) as? TextView
        val icon = actionNotificationIcon?.findViewById(R.id.iv_icon) as? ImageView
        icon?.setImageResource(R.drawable.ic_cart)
        actionNotificationIcon?.setOnClickListener {
            Log.d("***Cart", "Cart")
            openCart()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun openCart() {
        NavHostFragment.findNavController(this@HomeFragment).navigate(R.id.nav_cart)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lifecycleScope.launch {
//            viewModel.populateFirebaseWithCupcakes()
//        }
        viewModel.getCupcakes()
        setupRecyclerView()
        addObserver()
        cartViewModel.loadOrders()
    }

    private fun addObserver(){
        viewModel.cupcakes.observe(viewLifecycleOwner){ list ->
            if (!list.isNullOrEmpty()){
                homeAdapter.setData(list)
            }else{
                Log.d("Error", list.size.toString())
            }
        }

        cartViewModel.orders.observe(viewLifecycleOwner){ ordersList ->
            Log.d("***CartOrder", ordersList.toString())
            Log.d("***CartSize", ordersList.size.toString())
            if (ordersList.isNotEmpty()){
                menuNotificationTextView?.visibility = View.VISIBLE
                menuNotificationTextView?.text = ordersList[0].list.size.toString()
            }else{
                menuNotificationTextView?.visibility = View.GONE
            }
        }

        viewModel.progress.observe(viewLifecycleOwner){ visible ->
            if (visible){
                binding.pbHome.visibility = View.VISIBLE
            }else{
                binding.pbHome.visibility = View.GONE
            }
        }
    }

    private fun setupRecyclerView() {
        context.let { context ->
            homeAdapter = HomeAdapter()
            binding.rvList.layoutManager = LinearLayoutManager(context)
            binding.rvList.adapter = homeAdapter
        }
        homeAdapter.setAction {
            val action = HomeFragmentDirections.actionNavHomeToDetailsFragment(it)
            NavHostFragment.findNavController(this).navigate(action)
        }
    }



}
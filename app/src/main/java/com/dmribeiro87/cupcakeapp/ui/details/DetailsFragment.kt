package com.dmribeiro87.cupcakeapp.ui.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dmribeiro87.cupcakeapp.MainActivity
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentDetailsBinding
import com.dmribeiro87.cupcakeapp.ui.cart.CartViewModel
import com.dmribeiro87.cupcakeapp.utils.twoDecimals
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment : Fragment() {

    private val binding: FragmentDetailsBinding by viewBinding()
    private val args: DetailsFragmentArgs by navArgs()
    private var menuNotificationTextView: TextView? = null
    private val viewModel: DetailsViewModel by viewModel()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        setListeners()
        addObserver()
        cartViewModel.loadOrders()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cart, menu)
        val actionNotificationIcon: View? = menu.findItem(R.id.action_cart).actionView
        menuNotificationTextView = actionNotificationIcon?.findViewById(R.id.tv_counter) as? TextView
        val icon = actionNotificationIcon?.findViewById(R.id.iv_icon) as? ImageView
        icon?.setImageResource(R.drawable.ic_cart)
        actionNotificationIcon?.setOnClickListener {
            openCart()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setListeners() {
        binding.btAddCart.setOnClickListener {
            val selectedCupcake = args.selectedCupcake
            viewModel.initializeOrAddCupcakeToSelection(selectedCupcake)
            viewModel.createOrderForCheckout()
            cartViewModel.loadOrders() // Isto pode não ser necessário se a CartViewModel não estiver mais gerenciando os pedidos.
        }
    }


    private fun addObserver() {
        cartViewModel.orders.observe(viewLifecycleOwner){ ordersList ->
            if (ordersList.isNotEmpty()){
                menuNotificationTextView?.visibility = View.VISIBLE
                menuNotificationTextView?.text = ordersList[0].list.size.toString()
            }else{
                menuNotificationTextView?.visibility = View.GONE
            }
        }
    }

    private fun openCart() {
        NavHostFragment.findNavController(this@DetailsFragment).navigate(R.id.nav_cart)
    }

    private fun bindViews() {
        val cupcake = args.selectedCupcake
        binding.tvFlavour.text = cupcake.flavor
        binding.tvDescription.text = cupcake.description
        binding.tvPrice.text = "R$ ${twoDecimals(cupcake.price)}"

        Glide.with(this@DetailsFragment)
            .load(cupcake.image)
            .placeholder(R.drawable.cupcake_chocolate_img)
            .into(binding.ivCupcake)
    }




}
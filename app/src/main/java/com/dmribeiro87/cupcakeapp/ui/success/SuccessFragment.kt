package com.dmribeiro87.cupcakeapp.ui.success

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentSuccessBinding
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SuccessFragment : Fragment() {

    private val binding: FragmentSuccessBinding by viewBinding()
    private val viewModel: SuccessViewModel by viewModel()
    private val orderId = "unique-order-of-the-galaxy"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.deleteOrder(orderId)
        goToHome()
    }



    private fun goToHome() {
        Handler(Looper.getMainLooper()).postDelayed({
            NavHostFragment.findNavController(this).popBackStack(R.id.nav_home, false)
        }, 5000)
    }

}
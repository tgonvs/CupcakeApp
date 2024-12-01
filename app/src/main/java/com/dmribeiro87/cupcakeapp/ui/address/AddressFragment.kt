package com.dmribeiro87.cupcakeapp.ui.address

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.dmribeiro87.cupcakeapp.CupcakeRepository
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentAdressBinding
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import com.dmribeiro87.model.Address
import com.dmribeiro87.model.Client
import com.dmribeiro87.model.Order
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressFragment : Fragment() {

    private val binding: FragmentAdressBinding by viewBinding()
    private val args: AddressFragmentArgs by navArgs()
    private val viewModel: AddressViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("***OrderId", args.orderId)
        setListeners()
    }

    private fun setListeners() {
        binding.btnPix.setOnClickListener {
            if (isValid(
                    binding.edtName.text.toString(),
                    binding.edtCity.text.toString(),
                    binding.edtState.text.toString(),
                    binding.edtNeighborhood.text.toString(),
                    binding.edtStreet.text.toString(),
                    binding.edtNumber.text.toString(),
                    binding.edtComplement.text.toString()
                )){
                viewModel.addClientInfoToOrder(
                    args.orderId,
                    clientName = binding.edtName.text.toString(),
                    Address(
                        binding.edtCity.text.toString(),
                        binding.edtState.text.toString(),
                        binding.edtNeighborhood.text.toString(),
                        binding.edtStreet.text.toString(),
                        binding.edtNumber.text.toString(),
                        binding.edtComplement.text.toString()
                    )
                )
                goToPixPayment()

            }else{
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnCard.setOnClickListener {
            // Show Progress
            if (isValid(
                    binding.edtName.text.toString(),
                    binding.edtCity.text.toString(),
                    binding.edtState.text.toString(),
                    binding.edtNeighborhood.text.toString(),
                    binding.edtStreet.text.toString(),
                    binding.edtNumber.text.toString(),
                    binding.edtComplement.text.toString()
                )){
                viewModel.addClientInfoToOrder(
                    args.orderId,
                    clientName = binding.edtName.text.toString(),
                    Address(
                        binding.edtCity.text.toString(),
                        binding.edtState.text.toString(),
                        binding.edtNeighborhood.text.toString(),
                        binding.edtStreet.text.toString(),
                        binding.edtNumber.text.toString(),
                        binding.edtComplement.text.toString()
                    )
                )
                goToCardPayment()

            }else{
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToPixPayment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_adress_to_pixPaymentFragment)
    }

    private fun goToCardPayment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_adress_to_nav_card_payment)
    }

    private fun isValid(
        name: String,
        city: String,
        state: String,
        neighborhood: String,
        street: String,
        number: String,
        reference: String
    ): Boolean {

        if (
            name.isNullOrBlank() ||
            city.isNullOrBlank() ||
            state.isNullOrBlank() ||
            neighborhood.isNullOrBlank() ||
            street.isNullOrBlank() ||
            number.isNullOrBlank() ||
            reference.isNullOrBlank()
            ){
            return false
        }

        return true
    }




}
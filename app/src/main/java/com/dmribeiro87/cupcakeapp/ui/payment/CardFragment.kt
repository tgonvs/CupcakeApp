package com.dmribeiro87.cupcakeapp.ui.payment

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.dmribeiro87.cupcakeapp.R
import com.dmribeiro87.cupcakeapp.databinding.FragmentCardBinding
import com.dmribeiro87.cupcakeapp.utils.formatCardExpiryDate
import com.dmribeiro87.cupcakeapp.utils.formatCreditCardNumber
import com.dmribeiro87.cupcakeapp.utils.viewBinding
import com.dmribeiro87.model.CardPayment
import com.dmribeiro87.model.PaymentMethod
import com.dmribeiro87.model.PaymentType
import org.koin.androidx.viewmodel.ext.android.viewModel


class CardFragment : Fragment() {

    private val binding: FragmentCardBinding by viewBinding()
    val orderId = "unique-order-of-the-galaxy"
    private val viewModel: CardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupExpiryDateFormatter()
        setupCardNumberFormatter()
        setListeners()
    }

    // Incomplete

    private fun setupCardNumberFormatter() {
        val filter = InputFilter.LengthFilter(20)
        binding.edtNumberCard.filters = arrayOf(filter)
        binding.edtNumberCard.addTextChangedListener(object : TextWatcher {
            var previousText = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text != previousText) {
                    val formatted = formatCreditCardNumber(text)
                    val cursorPosition = binding.edtNumberCard.selectionStart
                    val diff = formatted.length - text.length

                    previousText = formatted
                    binding.edtNumberCard.removeTextChangedListener(this)
                    binding.edtNumberCard.setText(formatted)
                    try {
                        binding.edtNumberCard.setSelection((cursorPosition + diff).coerceAtMost(formatted.length))
                    } catch (e: Exception) {
                        val newCursorPosition = (formatted.length).coerceAtMost(binding.edtNumberCard.length())
                        binding.edtNumberCard.setSelection(newCursorPosition)
                    }
                    binding.edtNumberCard.addTextChangedListener(this)
                }
            }

            // Outros métodos...
        })
    }


    private fun setupExpiryDateFormatter() {
        binding.edtDateCard.addTextChangedListener(object : TextWatcher {
            var previousText = ""
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text != previousText) {
                    val formatted = formatCardExpiryDate(text)
                    val cursorPosition = binding.edtDateCard.selectionStart
                    val diff = formatted.length - text.length

                    previousText = formatted
                    binding.edtDateCard.removeTextChangedListener(this)
                    binding.edtDateCard.setText(formatted)
                    try {
                        binding.edtDateCard.setSelection((cursorPosition + diff).coerceAtMost(formatted.length))
                    } catch (e: Exception) {
                        binding.edtDateCard.setSelection(formatted.length)
                    }
                    binding.edtDateCard.addTextChangedListener(this)
                }
            }

            // Outros métodos...
        })
    }


    private fun setListeners() {

        binding.btFinish.setOnClickListener {
            if (isValid(
                    binding.edtNameCard.text.toString(),
                    binding.edtNumberCard.text.toString(),
                    binding.edtDateCard.text.toString(),
                    binding.edtCvcCard.text.toString()
                )
            ) {
                viewModel.updatePaymentType(
                    orderId,
                    PaymentType(
                        PaymentMethod.CARD,
                        cardPayment = CardPayment(
                            binding.edtNameCard.text.toString(),
                            binding.edtNumberCard.text.toString(),
                            binding.edtDateCard.text.toString(),
                            binding.edtCvcCard.text.toString()
                        )
                    )
                )

                goToSuccess()
            }else{
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun goToSuccess() {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_card_payment_to_nav_success)
    }


    private fun isValid(
        name: String,
        cardNumber: String,
        dueDate: String,
        cvc: String,
    ): Boolean {

        if (
            name.isNullOrBlank() ||
            cardNumber.isNullOrBlank() ||
            dueDate.isNullOrBlank() ||
            cvc.isNullOrBlank()
        ) {
            return false
        }

        return true
    }


}
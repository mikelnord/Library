package gb.com.lesson1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import gb.com.lesson1.data.RegisterState
import gb.com.lesson1.data.UserInfo
import gb.com.lesson1.databinding.FragmentRegisterBinding
import gb.com.lesson1.viewmodels.LoginViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val TAG = "RegisterFragment"
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        viewModel.registerState.observe(viewLifecycleOwner) { registerState ->
            when (registerState) {
                RegisterState.REGISTER -> {
                    Toast.makeText(context, "User successfully added", Toast.LENGTH_SHORT).show()
                    viewModel.resetRegisterState()
                    navController.popBackStack()
                }
                RegisterState.NOTREGISTER -> Toast.makeText(
                    context,
                    "Error adding a user",
                    Toast.LENGTH_SHORT
                ).show()
                RegisterState.SERVERERROR -> Toast.makeText(
                    context,
                    "Server error",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Log.e(
                    TAG,
                    "Register state that doesn't require any UI change $registerState"
                )
            }
        }

        binding.registerButton.setOnClickListener {
            val textLogin = binding.loginEditText.text.toString().trim()
            if (textLogin.isBlank()) {
                binding.loginEditText.error = "Enter a Login"
                return@setOnClickListener
            }

            val textPassword = binding.passwordEditText.text.toString().trim()
            if (textPassword.isBlank()) {
                binding.passwordEditText.error = "Enter a Password"
                return@setOnClickListener
            }
            binding.progressBar.visibility = View.VISIBLE
            binding.registerButton.isEnabled = false
            viewModel.onRegister(UserInfo(textLogin, textPassword))
            binding.progressBar.visibility = View.GONE
            binding.registerButton.isEnabled = true

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
package gb.com.lesson1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import gb.com.lesson1.R
import gb.com.lesson1.data.AuthenticationState
import gb.com.lesson1.data.UserInfo
import gb.com.lesson1.databinding.FragmentLoginBinding
import gb.com.lesson1.util.hideKeyboard
import gb.com.lesson1.viewmodels.LoginViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val TAG = "LoginFragment"
    private val viewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navController.popBackStack(R.id.mainFragment, false)
        }
        viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> navController.popBackStack()
                AuthenticationState.INVALID_AUTHENTICATION -> {
                    Toast.makeText(
                        context,
                        "No user or incorrect password",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetAuthenticationState()
                }
                AuthenticationState.SERVERERROR -> {
                    Toast.makeText(
                        context,
                        "Server error",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetAuthenticationState()
                }
                AuthenticationState.UNAUTHENTICATED ->{}
                else -> Log.e(
                    TAG,
                    "Authentication state that doesn't require any UI change $authenticationState"
                )
            }
        }

        viewModel.progressState.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.loginButton.isEnabled = !it
            binding.forgotButton.isEnabled = !it
            binding.registerButton.isEnabled = !it
            binding.loginEditText.isEnabled = !it
            binding.passwordEditText.isEnabled = !it
        }

        binding.loginButton.setOnClickListener {
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
            viewModel.onLogin(
                UserInfo(textLogin, textPassword)
            )
            binding.progressBar.visibility = View.GONE

            activity?.let { it1 -> hideKeyboard(it1) }
        }

        binding.registerButton.setOnClickListener {
            clearEditView(binding)
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearEditView(binding: FragmentLoginBinding) {
        binding.passwordEditText.text.clear()
        binding.loginEditText.text.clear()
    }
}
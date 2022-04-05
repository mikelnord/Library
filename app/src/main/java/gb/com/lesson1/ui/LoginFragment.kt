package gb.com.lesson1.ui

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gb.com.lesson1.MainActivity.Companion.presenter
import gb.com.lesson1.R
import gb.com.lesson1.databinding.FragmentLoginBinding
import gb.com.lesson1.model.AuthenticationState
import gb.com.lesson1.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val TAG = "LoginFragment"

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

        presenter.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> navController.popBackStack()
                AuthenticationState.INVALID_AUTHENTICATION -> Toast.makeText(
                    context,
                    "No user or incorrect password",
                    Toast.LENGTH_SHORT
                ).show()
                AuthenticationState.SERVERERROR-> Toast.makeText(
                    context,
                    "Server error",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Log.e(
                    TAG,
                    "Authentication state that doesn't require any UI change $authenticationState"
                )
            }
        }

        binding.buttonLogin.setOnClickListener {
            val textLogin = binding.textLogin.text.toString().trim()
            if (textLogin.isBlank()) {
                binding.textLogin.error = "Enter a Login"
                return@setOnClickListener
            }

            val textPassword = binding.textPassword.text.toString().trim()
            if (textPassword.isBlank()) {
                binding.textPassword.error = "Enter a Password"
                return@setOnClickListener
            }
            GlobalScope.launch(Dispatchers.Main) {
                binding.progressBar.visibility = View.VISIBLE
                binding.buttonLogin.isEnabled = false
                binding.buttonForgot.isEnabled = false
                binding.buttonReg.isEnabled = false
                presenter.onLogin(
                    UserInfo(textLogin, textPassword)
                )
                binding.progressBar.visibility = View.GONE
                binding.buttonLogin.isEnabled = true
                binding.buttonForgot.isEnabled = true
                binding.buttonReg.isEnabled = true

            }
            activity?.let { it1 -> hideKeyboard(it1) }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
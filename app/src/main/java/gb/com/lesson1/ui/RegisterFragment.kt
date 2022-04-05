package gb.com.lesson1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gb.com.lesson1.MainActivity.Companion.presenter
import gb.com.lesson1.databinding.FragmentRegisterBinding
import gb.com.lesson1.model.RegisterState
import gb.com.lesson1.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val TAG = "RegisterFragment"

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
        presenter.registerState.observe(viewLifecycleOwner) { registerState ->
            when (registerState) {
                RegisterState.REGISTER -> {
                    Toast.makeText(context, "User successfully added", Toast.LENGTH_SHORT).show()
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

        binding.buttonReg.setOnClickListener {
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
                binding.buttonReg.isEnabled = false
                presenter.onRegister(UserInfo(textLogin, textPassword))
                binding.progressBar.visibility = View.GONE
                binding.buttonReg.isEnabled = true

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
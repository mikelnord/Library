package gb.com.lesson1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import gb.com.lesson1.MainActivity
import gb.com.lesson1.R
import gb.com.lesson1.databinding.FragmentMainBinding
import gb.com.lesson1.model.AuthenticationState


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.presenter.authenticationState.observe(
            viewLifecycleOwner,
            Observer { authenticationState ->
                when (authenticationState) {
                    (AuthenticationState.UNAUTHENTICATED) -> {
//                    binding.authButton.text = getString(R.string.logout_button_text)
//                    binding.authButton.setOnClickListener {
//                        // TODO implement logging out user in next step
//                        AuthUI.getInstance().signOut(requireContext())
//                    }
                        findNavController().navigate(R.id.loginFragment)
                    }
                    else -> {
                        // TODO 3. Lastly, if there is no logged-in user,
                        // auth_button should display Login and
                        //  launch the sign in screen when clicked.
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
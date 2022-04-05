package gb.com.lesson1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gb.com.lesson1.MainActivity.Companion.presenter
import gb.com.lesson1.R
import gb.com.lesson1.databinding.FragmentMainBinding
import gb.com.lesson1.model.AuthenticationState


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.authenticationState.observe(
            viewLifecycleOwner
        ) { authenticationState ->
            when (authenticationState) {
                (AuthenticationState.UNAUTHENTICATED) -> {
                    findNavController().navigate(R.id.loginFragment)
                }
                else -> {
                }
            }
        }
        binding.textScreen.text = "Welcome user ${presenter.currentUser?.username}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
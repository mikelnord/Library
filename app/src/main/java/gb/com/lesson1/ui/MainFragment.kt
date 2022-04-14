package gb.com.lesson1.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import gb.com.lesson1.R
import gb.com.lesson1.databinding.FragmentMainBinding
import gb.com.lesson1.data.AuthenticationState
import gb.com.lesson1.data.network.MockRepository
import gb.com.lesson1.viewmodels.LoginViewModel
import gb.com.lesson1.viewmodels.LoginViewModelFactory


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<LoginViewModel> {
        LoginViewModelFactory(
            MockRepository()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.authenticationState.observe(
            viewLifecycleOwner
        ) { authenticationState ->
            when (authenticationState) {
                (AuthenticationState.UNAUTHENTICATED) -> {
                    findNavController().navigate(R.id.loginFragment)
                }
                (AuthenticationState.INVALID_AUTHENTICATION)->{
                    findNavController().navigate(R.id.loginFragment)
                }
                else -> {
                }
            }
        }
        "Welcome user ${viewModel.currentUser?.username}".also { binding.mainScreenViewText.text = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_mainfragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.onLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
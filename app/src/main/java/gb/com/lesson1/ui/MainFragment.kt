package gb.com.lesson1.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import gb.com.lesson1.ui.MainActivity.Companion.presenter
import gb.com.lesson1.R
import gb.com.lesson1.databinding.FragmentMainBinding
import gb.com.lesson1.domain.model.AuthenticationState


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
                (AuthenticationState.INVALID_AUTHENTICATION)->{
                    findNavController().navigate(R.id.loginFragment)
                }
                else -> {
                }
            }
        }
        binding.mainScreenViewText.text = "Welcome user ${presenter.currentUser?.username}"
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
                presenter.onLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
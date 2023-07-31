package com.ichwan.disasterlist.presentation.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.ichwan.disasterlist.R
import com.ichwan.disasterlist.databinding.FragmentSettingBinding
import com.ichwan.disasterlist.di.SetDataStore
import com.ichwan.disasterlist.di.SetViewModel
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var dataStore: SetDataStore
    private lateinit var viewModel: SetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this@SettingFragment)[SetViewModel::class.java]
        dataStore = SetDataStore.getInstance(requireContext())

        checkThemeMode()

        binding.apply {
            btnBack.setOnClickListener {
                Navigation.findNavController(requireView()).navigate(R.id.action_settingFragment_to_mapsFragment)
            }

            switchMode.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    when(isChecked) {
                        true -> viewModel.setThemeDark(true)
                        false -> viewModel.setThemeDark(false)
                    }
                }
            }
        }

        return binding.root
    }

    private fun checkThemeMode() {
        binding.apply {
            viewModel.getTheme.observe(viewLifecycleOwner) { isDarkMode ->
                when(isDarkMode){
                    true -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        switchMode.isChecked = true
                        switchMode.text = "Night Mode"
                    }
                    false -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        switchMode.isChecked = false
                        switchMode.text = "Light Mode"
                    }
                }
            }
        }
    }
}
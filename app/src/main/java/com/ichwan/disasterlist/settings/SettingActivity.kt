package com.ichwan.disasterlist.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ichwan.disasterlist.databinding.ActivitySettingBinding
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var dataStore: SetDataStore
    private lateinit var viewModel: SetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@SettingActivity)[SetViewModel::class.java]
        dataStore = SetDataStore(this)

        checkThemeMode()

        binding.apply {
            switchMode.setOnCheckedChangeListener { _, isChecked ->
                lifecycleScope.launch {
                    when(isChecked) {
                        true -> viewModel.setThemeDark(true)
                        false -> viewModel.setThemeDark(false)
                    }
                }
            }
        }
    }

    private fun checkThemeMode() {
        binding.apply {
            viewModel.getTheme.observe(this@SettingActivity) { isDarkMode ->
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
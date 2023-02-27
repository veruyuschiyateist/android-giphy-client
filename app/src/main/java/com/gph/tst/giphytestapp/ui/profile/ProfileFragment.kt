package com.gph.tst.giphytestapp.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.annotation.Nullable


@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        setupButtons()
        setUpFields()

        binding.profileImage.setOnClickListener {
            selectImage()
        }

        return binding.root
    }

    private fun setUpFields() {
        lifecycleScope.launchWhenStarted {
            viewModel.profileUiState.collect {
                when (it) {
                    ProfileViewModel.ProfileUiState.Loading -> {
                        showMessage(getString(R.string.loading))
                    }
                    is ProfileViewModel.ProfileUiState.Loaded -> {
                        with(binding) {
                            editNameTextField.editText?.setText(it.userProfile.name)
                            editSurnameTextField.editText?.setText(it.userProfile.surname)
                            editPhoneTextField.editText?.setText(it.userProfile.phone)
                        }
                    }
                    is ProfileViewModel.ProfileUiState.Failure -> {
                        showMessage(getString(R.string.try_again))
                    }
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupButtons() {
        binding.continueButton.setOnClickListener {
            val name = binding.editNameTextField.editText?.text.toString()
            val surname = binding.editSurnameTextField.editText?.text.toString()
            val phone = binding.editPhoneTextField.editText?.text.toString()
            viewModel.onSave(
                name = name,
                surname = surname,
                phone = phone
            )
        }

        binding.exitButton.setOnClickListener {
            viewModel.logout()

            findNavController().navigate(R.id.signInFragment)
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && data != null && data.data != null) {
            uri = data.data;
            binding.profileImage.setImageURI(uri)
        }
    }

}
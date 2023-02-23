package com.gph.tst.giphytestapp.ui.auth.signup

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.gph.tst.giphytestapp.R
import com.gph.tst.giphytestapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        setupSpannable()

        return binding.root
    }

    private fun setupSpannable() {
        val string = getString(R.string.already_have_an_account_login)

        val spannableString = SpannableString(string)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                navigateToSignIn()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)

                ds.color = ContextCompat.getColor(requireContext(), R.color.primary)
                ds.isUnderlineText = false
            }
        }

        spannableString.setSpan(
            clickableSpan,
            string.indexOf(getString(R.string.login)),
            string.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.signInMessage.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun navigateToSignIn() {
        val navController = findNavController()

        navController.navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    companion object {
        private const val TAG = "SignUpFragment"
    }


}
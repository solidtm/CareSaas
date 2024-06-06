package com.caresaas.app.features.login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.caresaas.app.R
import com.caresaas.app.core.ResultState
import com.caresaas.app.data.model.DomainModel
import com.caresaas.app.databinding.FragmentLoginBinding
import com.caresaas.app.features.MainActivity
import com.caresaas.app.util.extensions.showDialog
import com.caresaas.app.util.helpers.CareSaasSharePreference
import com.caresaas.app.util.helpers.KEY.CARE_HOME_ID
import com.caresaas.app.util.helpers.KEY.FIRST_NAME
import com.caresaas.app.util.helpers.KEY.SHORT_CODE
import com.caresaas.app.util.helpers.KEY.USER_ID
import com.caresaas.app.util.ui.ProgressLoader
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passLayout: TextInputLayout
    private val loginViewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var preference: CareSaasSharePreference
    @Inject
    lateinit var progressLoader: ProgressLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        emailLayout = binding.emailLayout
        passLayout = binding.passLayout
        registerListeners()
        setObservers()
        return binding.root
    }

    private fun getColoredClickableText(text: String): SpannableString {
        val spannableString = SpannableString(text)

        // Make "Terms & Conditions" clickable
        val clickableSpan1 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "Terms & Conditions Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        val startTerms = text.indexOf("T")
        val endTerms = text.lastIndexOf("s") + 1
        spannableString.setSpan(clickableSpan1, startTerms, endTerms, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Make "Privacy Policy" clickable
        val clickableSpan2 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(requireContext(), "Privacy Policy Clicked", Toast.LENGTH_SHORT).show()
            }
        }

        val startPrivacy = text.indexOf("and ") + 4
        val endPrivacy = text.length
        spannableString.setSpan(clickableSpan2, startPrivacy, endPrivacy, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set blue color for the clickable spans
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorAccent)), startTerms, endTerms, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorAccent)), startPrivacy, endPrivacy, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannableString
    }

    private fun validateUsername(): Boolean {
        val value = emailLayout.editText?.text.toString().trim()

        return when {
            value.isEmpty() -> {
                emailLayout.error = "Field cannot be empty"
                emailLayout.errorIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_error)
                false
            }
            else -> {
                emailLayout.error = null
                emailLayout.isErrorEnabled = false
                true
            }
        }
    }

    private fun validatePassword(value: String): Boolean {
        return when {
            value.isEmpty() -> {
                passLayout.error = "Field cannot be empty"
                passLayout.errorIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_error)
                false
            }
            else -> {
                passLayout.error = null
                passLayout.isErrorEnabled = false
                true
            }
        }
    }

    private fun validateFields(): Boolean {
        return validateUsername() && validatePassword(passLayout.editText?.text.toString().trim())
    }

    private fun registerListeners() {
        //click listeners
        binding.apply {
            textTerms.text = getColoredClickableText("By clicking ‘Sign in’ above you agree to Arocare’s Terms & Conditions and Privacy Policy.")
            buttonSignIn.setOnClickListener{
                if(validateFields()){
                    val loginRequest = DomainModel.LoginRequest(
                        emailLayout.editText?.text.toString().trim(),
                        passLayout.editText?.text.toString().trim()
                    )
                    loginViewModel.login(loginRequest)
                }
            }
            textViewContactSupport.setOnClickListener {}

            forgotPassword.setOnClickListener {}
        }
    }

    private fun setObservers(){
        loginViewModel.loginDetails.observe(viewLifecycleOwner) {
            val state = it.getContentIfNotHandled() ?: return@observe
            when (state) {
                is ResultState.Loading -> {progressLoader.show("Please wait...")}
                is ResultState.Error -> {
                    progressLoader.hide()
                    requireContext().showDialog(
                        title = "Error",
                        message = state.error
                    ){}
                }
                is ResultState.Success -> {
                    progressLoader.hide()
                    val user = state.data.data.user
                    CareSaasSharePreference(requireContext()).setString(SHORT_CODE, user.organization)
                    CareSaasSharePreference(requireContext()).setString(CARE_HOME_ID, "2")
                    CareSaasSharePreference(requireContext()).setString(USER_ID, user.userId)
                    CareSaasSharePreference(requireContext()).setString(FIRST_NAME, user.givenName)
                    MainActivity.start(requireActivity())
                    requireActivity().finish()
                }
            }
        }
    }
}
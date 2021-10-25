package com.androidnativesample.onboard.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.androidnativesample.R
import com.androidnativesample.databinding.ActivityLoginSignupBinding
import android.graphics.drawable.GradientDrawable
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.androidnativesample.di.component.DaggerAppComponent
import com.androidnativesample.di.factory.ViewModelFactory
import com.androidnativesample.onboard.model.RegisterRequest
import com.androidnativesample.onboard.sealed.LoginState
import com.androidnativesample.onboard.viewmodel.LoginViewModel
import com.androidnativesample.utils.setSafeOnClickListener
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


class LoginSignupActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: LoginViewModel
    lateinit var mBinding: ActivityLoginSignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.create().injectFieldsLoginActivity(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_signup)
        clickEvents()

        mBinding.ccp.setOnCountryChangeListener(this::setCountry)
        setObservers()


    }

    private fun clickEvents() {
        mBinding.tvContinue.setOnClickListener {
            callLoginApi()
        }
    }

    private fun setCountry() {
        mBinding.etCountryCode.setText(StringBuilder("+")
            .append(mBinding.ccp.selectedCountryCode))

    }

    fun callLoginApi(){
        viewModel.getLoginResponse(
            RegisterRequest(mBinding.etPhoneNumber.text.toString(),
            "+91",
            "PHONE"))
    }

    fun setObservers(){
        lifecycleScope.launchWhenStarted {
            viewModel.loginState.collect {
                when(it){
                    is LoginState.Error -> {
                        Log.e("Error",it.throwable.message.toString() )
                    }
                    LoginState.ShowLoading -> {
                        //Show Progress dialog
                    }
                    is LoginState.Success -> {
                        Log.e("Success",it.user?.phoneNumber.toString() )
                    }
                }
            }
        }
    }
}
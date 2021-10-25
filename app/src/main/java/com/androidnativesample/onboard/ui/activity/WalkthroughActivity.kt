package com.androidnativesample.onboard.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.androidnativesample.R
import com.androidnativesample.databinding.ActivityWalkthroughBinding
import com.androidnativesample.di.component.DaggerAppComponent
import com.androidnativesample.di.factory.ViewModelFactory
import com.androidnativesample.onboard.ui.adapter.WalkthroughAdapter
import com.androidnativesample.onboard.viewmodel.WalkthroughViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class WalkthroughActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mBinding: ActivityWalkthroughBinding
    var tabHeight=15
    var tabWidhtActive=150
    var tabWidhtInActive=50
    val title: Array<String> by lazy { resources.getStringArray(R.array.walkthrough_title) }
    val desc: Array<String> by lazy { resources.getStringArray(R.array.walkthrough_desc) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerAppComponent.create().injectFieldsWalkthrough(this)
        val viewmodel = ViewModelProvider(this, viewModelFactory)[WalkthroughViewModel::class.java]
        viewmodel.data?.observe(this,{
            Log.e("onCreate: ", it)
        })
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_walkthrough)
        createTabs()
        mBinding.pager.adapter=WalkthroughAdapter(this)
        mBinding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateTabs(position)
            }
        })

        mBinding.ivNext.setOnClickListener {
            if (mBinding.pager.currentItem==2){
                lifecycleScope.launch {
                    startActivity(Intent(this@WalkthroughActivity, LoginSignupActivity::class.java))
                }

            }else{
                mBinding.pager.currentItem=mBinding.pager.currentItem+1
            }
        }

    }

    private fun createTabs(){
        for (i in 1..3){
            val view=LinearLayout(this)
            val params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // This will define text view width
                LinearLayout.LayoutParams.WRAP_CONTENT // This will define text view height
            )
            view.apply {
                minimumHeight=tabHeight
                if (i>1){
                    minimumWidth=tabWidhtInActive
                    params.marginStart=40
                    background=ContextCompat.getDrawable(this@WalkthroughActivity,R.drawable.grey_round)
                }else{
                    minimumWidth=tabWidhtActive
                    background=ContextCompat.getDrawable(this@WalkthroughActivity,R.drawable.blue_round)
                }
                view.layoutParams=params
            }

            mBinding.lltabs.addView(view)
        }
    }

    private fun updateTabs(position:Int){
        mBinding.tvTitle.text=title[position]
        mBinding.tvDesc.text=desc[position]
        mBinding.lltabs.children.iterator().forEach {
            (it as LinearLayout).apply {
                minimumWidth=tabWidhtInActive
                background=ContextCompat.getDrawable(this@WalkthroughActivity,R.drawable.grey_round)
            }

        }
        mBinding.lltabs.getChildAt(position).apply {
            minimumWidth=tabWidhtActive
            background=ContextCompat.getDrawable(this@WalkthroughActivity,R.drawable.blue_round)
        }

    }
}
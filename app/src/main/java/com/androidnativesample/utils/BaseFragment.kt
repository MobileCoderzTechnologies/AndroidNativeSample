package com.remnant_tribe.utils
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.remnant_tribe.utils.BaseActivity

abstract class BaseFragment : Fragment() {
    @StringRes
    abstract fun getTitle(): Int
    abstract fun getFragmentTag(): String?

   // private lateinit var firebaseAnalytics: FirebaseAnalytics

    val isActivityAlive: Boolean
        get() {
            val activity = activity as FragmentActivity
            return !activity.isFinishing
        }
    //public abstract void setTypeOfValue();

    fun replaceFragment(fragment: Fragment, holder: Int) {
        (activity as BaseActivity).replaceFragment(fragment, holder)
    }
    fun replaceFragmentWithSharedElement(frg: Fragment, iv: ImageView, transitionName: String, holder: Int) {
        (activity as BaseActivity).replaceFragmentWithSharedElement(frg, iv, transitionName, holder)
    }

    fun getFragmentByTag(tag: String): Fragment? {
        return (activity as BaseActivity).getFragmentByTag(tag)
    }

    fun addFragment(fragment: Fragment, holder: Int) {
        (activity as BaseActivity).addFragment(fragment, holder)
    }

    fun setTitle(title: String) {
        if (activity != null) {
            activity?.title = title
        }
    }

    fun setHeaderTitle(title: String) {

    }

    fun setHeaderTitle(stringResId: Int) {
        setHeaderTitle(getString(stringResId))
    }

    fun removeTopfragment() {
        (activity as BaseActivity).removeTopfragment()
    }

    fun removeFragmentByTag(tag: String) {
        (activity as BaseActivity).removeFragmentByTag(tag)
    }

    fun showHomeFragment() {
        (activity as BaseActivity).showHomeFragment()
    }

//    fun setAnalytics(){
//        var FragmentTag = getFragmentTag()
//        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
//        val bundle = Bundle()
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, FragmentTag)
//        /* bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
//         bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")*/
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      /*  var FragmentTag = getFragmentTag()
        firebaseAnalytics = FirebaseAnalytics.getInstance(context!!)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, FragmentTag)
        *//* bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
         bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")*//*
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)*/

    }
}

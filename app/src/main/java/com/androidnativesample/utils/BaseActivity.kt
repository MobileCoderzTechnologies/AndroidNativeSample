package com.remnant_tribe.utils

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {
 //   private var socket: Socket? = OonaApp.instance.getAppSocket()
  //  private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var lastFragmentName = ""
    abstract fun getActivitytTag(): String?
    private val backStackCount: Int
        get() {
            val fm = supportFragmentManager
            return fm.backStackEntryCount
        }

   /* override fun onStart() {
        super.onStart()
        if(socket!=null){
            if(!socket!!.connected()){
                socket?.connect()
            }
        }
    }*/

    protected abstract fun backStackChangeListner(backStackCount: Int)

    fun addFragment(fragment: Fragment?, holder: Int) {
        if (fragment != null) {


            val fragmentManager = supportFragmentManager
            fragmentManager.addOnBackStackChangedListener(getListener(holder))

            val transaction = fragmentManager.beginTransaction()

            transaction.addToBackStack(fragment.javaClass.simpleName)
            supportFragmentManager.backStackEntryCount

            transaction.add(holder, fragment, fragment.javaClass.simpleName)
            transaction.commitAllowingStateLoss()
        }
    }


        fun replaceFragment(fragment: Fragment?, holder: Int) {
        if (fragment != null) {
            //isResettingStack = false;
            val fragmentManager = supportFragmentManager
            fragmentManager.addOnBackStackChangedListener(getListener(holder))

            val transaction = fragmentManager.beginTransaction()

            transaction.addToBackStack(fragment.javaClass.simpleName)
            supportFragmentManager.backStackEntryCount
            transaction.replace(holder, fragment, fragment.javaClass.simpleName)
            transaction.commitAllowingStateLoss()

        }
    }


    fun getFragmentByTag(tag: String): Fragment? {

        val fragmentManager = supportFragmentManager

        return fragmentManager.findFragmentByTag(tag)

    }

   /* override fun onDestroy() {
        super.onDestroy()
        if(socket!=null){
            if(socket!!.connected()){
               // socket?.disconnect()
            }
        }

    }*/

    fun replaceFragmentWithSharedElement(fragment: Fragment?, view: View,
                                         transitionName: String, holder: Int) {
        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.addOnBackStackChangedListener(getListener(holder))

            val transaction = fragmentManager.beginTransaction()

            transaction.addToBackStack(fragment.javaClass.simpleName)
            supportFragmentManager.backStackEntryCount
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transaction.addSharedElement(view, transitionName)
            }

            transaction.replace(holder, fragment, fragment.javaClass.simpleName)
            transaction.commit()
        }
    }

    fun removeFragment(fragment: Fragment?) {
        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager
                    .beginTransaction()

            transaction.remove(fragment)
            transaction.commit()
            fragmentManager.popBackStack()
        }
    }


    fun removeTopfragment() {
        val fm = supportFragmentManager
        fm.popBackStack()
    }

    fun removeFragmentByTag(tag: String) {
        val fragmentManager = supportFragmentManager
        val trans = fragmentManager.beginTransaction()
        val frg = fragmentManager.findFragmentByTag(tag)
        if (frg != null) {
            trans.remove(frg)
        }
        trans.commit()
        fragmentManager.popBackStack()
    }

    //protected boolean isResettingStack = false;
    fun resetBackStack() {
        val fm = supportFragmentManager
        //isResettingStack = true;
        for (i in 1 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
        //isResettingStack = false;
    }


    fun showHomeFragment() {
        val fm = supportFragmentManager
        //fm.removeOnBackStackChangedListener(getListener());
        for (i in 1 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }


    private fun getListener(holder: Int): () -> Unit {
        return {
            val manager = supportFragmentManager

            if (manager != null) {

                //if( !isResettingStack )
                backStackChangeListner(backStackCount)

                val currFrag: BaseFragment? = manager
                        .findFragmentById(holder) as BaseFragment?

                if (lastFragmentName != currFrag?.javaClass?.name) {
                    lastFragmentName = currFrag?.javaClass?.simpleName.toString()
                    currFrag?.onResume()
                }
            }
        }
    }
//    fun setAnalytics(){
//        var ActivityName = getActivitytTag()
//        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
//        val bundle = Bundle()
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, ActivityName)
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
//        /* bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
//     bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")*/
//    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        /*var ActivityName = getActivitytTag()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, ActivityName)
       *//* bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")*//*
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)*/
    }

   // abstract val newMessage: Emitter.Listener?
}

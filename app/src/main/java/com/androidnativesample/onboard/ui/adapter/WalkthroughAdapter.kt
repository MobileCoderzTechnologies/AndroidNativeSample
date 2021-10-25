package com.androidnativesample.onboard.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.androidnativesample.R
import com.androidnativesample.databinding.WalkthroughItemBinding

class WalkthroughAdapter(private val mContext: Context): RecyclerView.Adapter<WalkthroughAdapter.WalkthroughViewHolder>() {

    var images= arrayOf(R.mipmap.walkthrough1,R.mipmap.walkthrouth3,R.mipmap.walkthrough2)


    class WalkthroughViewHolder(mBinding: WalkthroughItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        val mBinding: WalkthroughItemBinding = mBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkthroughViewHolder {
        val mBinding: WalkthroughItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.walkthrough_item,
            parent,
            false
        )
        return WalkthroughViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: WalkthroughViewHolder, position: Int) {
        Glide.with(mContext)
            .load(images[position])
            .centerCrop()
            .into(holder.mBinding.ivPic)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}
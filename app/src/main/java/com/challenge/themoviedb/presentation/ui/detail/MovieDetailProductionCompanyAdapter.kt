package com.challenge.themoviedb.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.challenge.themoviedb.BuildConfig
import com.challenge.themoviedb.data.network.response.ProductionCompany
import com.challenge.themoviedb.databinding.ProductionCompanyItemRowBinding

class MovieDetailProductionCompanyAdapter : ListAdapter<ProductionCompany, ProductionCompanyItemViewHolder>(
    ProductionCompanyItemCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductionCompanyItemViewHolder {
        return ProductionCompanyItemViewHolder(ProductionCompanyItemRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductionCompanyItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductionCompanyItemCallback : DiffUtil.ItemCallback<ProductionCompany>() {
    override fun areItemsTheSame(
        oldItem: ProductionCompany,
        newItem: ProductionCompany
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ProductionCompany,
        newItem: ProductionCompany
    ): Boolean {
        return oldItem == newItem
    }
}

class ProductionCompanyItemViewHolder(private val binding: ProductionCompanyItemRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(productionCompany: ProductionCompany) {
        binding.productionCompanyName.text = productionCompany.name
        Glide.with(binding.root)
            .load(BuildConfig.TMDB_BASE_IMAGES_URL.plus(productionCompany.logo_path))
            .into(binding.productionCompanyImage)
    }
}
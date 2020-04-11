package com.beloushkin.fooddataviewer.foodlist.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beloushkin.fooddataviewer.R
import com.beloushkin.fooddataviewer.model.Product
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.food_list_product_item.*
import kotlinx.android.synthetic.main.product_layout_small.*


class FoodListAdapter:ListAdapter<Product, FoodListProductViewHolder>(
    DiffUtilCallback()
) {

    private val productClicksSubject = PublishSubject.create<Int>()
    val productClicks: Observable<Product> = productClicksSubject
        .map { position -> getItem(position) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListProductViewHolder {
        return FoodListProductViewHolder(LayoutInflater.from(parent.context).inflate(
            viewType,
            parent,
            false
            ),
            productClicksSubject
        )
    }

    override fun onBindViewHolder(holder: FoodListProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.food_list_product_item
    }
}

private class DiffUtilCallback: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}

class FoodListProductViewHolder(
    override val containerView: View,
    val productClicksSubject: PublishSubject<Int>
)
    :RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(product: Product) {
        val context = containerView.context

        productNameView.text = product.name
        brandNameView.text = product.brands
        energyValue.text = context.getString(
            R.string.scan_energy_value,
            product.nutriments?.energy
        )
        carbsValue.text = context.getString(
            R.string.scan_macro_value,
            product.nutriments?.carbohydrates
        )
        proteinsValue.text = context.getString(
            R.string.scan_macro_value,
            product.nutriments?.proteins
        )
        fatValue.text = context.getString(
            R.string.scan_macro_value,
            product.nutriments?.fat
        )
        Glide.with(context)
            .load(product.imageUrl)
            .fitCenter()
            .into(productImageView)

        productCard.clicks().map { adapterPosition }.subscribe(productClicksSubject)
    }
}
package com.nmp.ubayakost_160420016.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.model.Kost
import com.nmp.ubayakost_160420016.util.loadImage
import com.nmp.ubayakost_160420016.view.FavoriteListFragmentDirections
import com.nmp.ubayakost_160420016.view.KostListFragmentDirections
import kotlinx.android.synthetic.main.item_kost.view.*

class KostAdapter(
    private val kostList: ArrayList<Kost>,
    private val fragment: String,
    val favorite: (Int) -> Unit
): RecyclerView.Adapter<KostAdapter.KostViewHolder>() {
    class KostViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_kost, parent, false)
        return KostViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: KostViewHolder, position: Int) {
        val kost = kostList[position]

        with(holder.view) {
            imgKost.loadImage(kost.mainPhoto)
            txtNamaItemKost.text = kost.name
            txtLocationItemKost.text = "${kost.distance} meter"
            txtPriceItemKost.text = "${kost.pricePerMonth}"
            txtOwnerItemKost.text = kost.owner
            ratingBarItemKost.rating = kost.rating
            txtRatingItemKost.text = "${kost.rating}"
            txtBedroomItemKost.text = "${kost.capacity}"
            txtBathroomItemKost.text = "${kost.bathroom}"

            imgBtnFavoriteItemKost.setImageResource(
                if (kost.isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            )

            imgBtnFavoriteItemKost.setOnClickListener {
                kost.isFavorite = !kost.isFavorite
                favorite(kost.id)
                imgBtnFavoriteItemKost.setImageResource(
                    if (kost.isFavorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
                )
            }

            this.setOnClickListener {
                if (fragment == "home") {
                    val action = KostListFragmentDirections.actionHomeToKostDetail("home", kost.id)
                    Navigation.findNavController(this).navigate(action)
                } else {
                    val action = FavoriteListFragmentDirections.actionFavoriteToKostDetail("favorite", kost.id)
                    Navigation.findNavController(this).navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int = kostList.size

    fun updateKostList(newKostList: ArrayList<Kost>) {
        kostList.clear()
        kostList.addAll(newKostList)
        notifyDataSetChanged()
    }
}
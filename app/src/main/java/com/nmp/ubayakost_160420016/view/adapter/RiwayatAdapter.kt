package com.nmp.ubayakost_160420016.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.nmp.ubayakost_160420016.R
import com.nmp.ubayakost_160420016.model.Order
import com.nmp.ubayakost_160420016.util.loadImage
import com.nmp.ubayakost_160420016.view.KostListFragmentDirections
import kotlinx.android.synthetic.main.item_order.view.*

class RiwayatAdapter(
    private val riwayatList: ArrayList<Order>
): RecyclerView.Adapter<RiwayatAdapter.KostViewHolder>() {
    class KostViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_order, parent, false)
        return KostViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: KostViewHolder, position: Int) {
        val order = riwayatList[position]

        with(holder.view) {
            imgKostItemRiwayat.loadImage(order.kost.mainPhoto)
            txtNamaItemRiwayat.text = order.kost.name
            txtLocationItemRiwayat.text = "${order.kost.distance} meter"
            txtPriceItemRiwayat.text = "${order.kost.pricePerMonth}"
            txtOwnerItemRiwayat.text = order.kost.owner
            ratingBarItemRiwayat.rating = order.kost.rating
            txtRatingItemRiwayat.text = "${order.kost.rating}"
            txtLastBookedDateItemRiwayat.text = "Last booked ${order.dateBooked}"
            txtLastStayDateItemRiwayat.text = "Stay until ${order.dateStayedUntil}"
            txtPaymentMethodItemRiwayat.text = "Paid using ${order.paymentMethod}"

            this.setOnClickListener {
                val action = KostListFragmentDirections.actionHomeToKostDetail()
                Navigation.findNavController(this).navigate(action)
            }
        }
    }

    override fun getItemCount(): Int = riwayatList.size

    fun updateRiwayatList(newRiwayatList: ArrayList<Order>) {
        riwayatList.clear()
        riwayatList.addAll(newRiwayatList)
        notifyDataSetChanged()
    }
}
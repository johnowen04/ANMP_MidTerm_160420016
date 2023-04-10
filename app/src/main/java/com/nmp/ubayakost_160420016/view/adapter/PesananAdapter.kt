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

class PesananAdapter(
    private val pesananList: ArrayList<Order>,
    private val fragment: String
): RecyclerView.Adapter<PesananAdapter.KostViewHolder>() {
    class KostViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_order, parent, false)
        return KostViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: KostViewHolder, position: Int) {
        val order = pesananList[position]

        with(holder.view) {
            imgKostItemOrder.loadImage(order.kost.mainPhoto)
            txtNamaItemOrder.text = order.kost.name
            txtLocationItemOrder.text = "${order.kost.distance} meter"
            txtPriceItemOrder.text = "${order.kost.pricePerMonth}"
            txtOwnerItemOrder.text = order.kost.owner
            ratingBarItemOrder.rating = order.kost.rating
            txtRatingItemOrder.text = "${order.kost.rating}"
            txtLastBookedDateItemOrder.text = if (fragment == "riwayat") "Last booked ${order.dateBooked}" else "Booked at ${order.dateBooked}"
            txtLastStayDateItemOrder.text = "Stay until ${order.dateStayedUntil}"
            txtPaymentMethodItemOrder.text = "Paid using ${order.paymentMethod}"
        }
    }

    override fun getItemCount(): Int = pesananList.size

    fun updatePesananList(newPesananList: ArrayList<Order>) {
        pesananList.clear()
        pesananList.addAll(newPesananList)
        notifyDataSetChanged()
    }
}
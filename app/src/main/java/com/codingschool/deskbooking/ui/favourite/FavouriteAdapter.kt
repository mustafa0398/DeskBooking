package com.codingschool.deskbooking.ui.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.authentication.favourites.GetFavouriteResponse
import com.codingschool.deskbooking.data.model.dto.bookings.CreateBooking

class FavouriteAdapter(private val favReserveClickListener: FavReserveClickListener) :
    ListAdapter<GetFavouriteResponse, FavouriteAdapter.FavouriteViewHolder>(FavouriteDiffCallback()) {

    interface FavReserveClickListener {
        fun onFavReserveClicked(createBooking: CreateBooking)
        fun onDeleteFavouriteClicked(favouriteId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favourite, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val favourite = getItem(position)
        holder.bind(favourite)
    }

    inner class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val favDeskLabel: TextView = itemView.findViewById(R.id.tvFavDeskLabel)
        private val favDeskOfficeName: TextView = itemView.findViewById(R.id.tvDeskOfficeName)
        private val btnFav: Button = itemView.findViewById(R.id.btnFavReserve)
        private val btnDel: Button = itemView.findViewById(R.id.btnFavDelete)

        init {
            btnFav.setOnClickListener {
                val favourite = getItem(adapterPosition)
                val createBooking = CreateBooking(dateStart = "", dateEnd = "", desk = favourite.id)
                favReserveClickListener.onFavReserveClicked(createBooking)
            }
            btnFav.setOnClickListener {
                val favourite = getItem(adapterPosition)
                favReserveClickListener.onDeleteFavouriteClicked(favourite.id)
            }
        }

        fun bind(favourite: GetFavouriteResponse) {
            favDeskLabel.text = favourite.desk.label
            favDeskOfficeName.text = favourite.desk.office.name
        }
    }

    class FavouriteDiffCallback : DiffUtil.ItemCallback<GetFavouriteResponse>() {
        override fun areItemsTheSame(oldItem: GetFavouriteResponse, newItem: GetFavouriteResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GetFavouriteResponse,
            newItem: GetFavouriteResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}

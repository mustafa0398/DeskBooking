package com.codingschool.deskbooking.ui.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.bookings.BookingResponse
import com.codingschool.deskbooking.data.model.authentication.favourites.CreateFavouriteResponse
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationAdapter(
    private val commentClickListener: CommentClickListener,
    private val favoriteClickListener: FavoriteClickListener
) :
    ListAdapter<BookingResponse, ReservationAdapter.ReservationViewHolder>(BookingDiffCallback()) {

    interface CommentClickListener {
        fun onCommentSendClicked(booking: BookingResponse, comment: String)
    }

    interface FavoriteClickListener {
        fun onFavoriteClicked(favourite: CreateFavouriteResponse) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val booking = getItem(position)
        holder.bind(booking)
    }

    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deskLabel: TextView = itemView.findViewById(R.id.tvDeskLabel)
        private val deskOfficeName: TextView = itemView.findViewById(R.id.tvDeskOfficeName)
        private val dateStart: TextView = itemView.findViewById(R.id.tvDateStart)
        private val dateEnd: TextView = itemView.findViewById(R.id.tvDateEnd)
        private val etComment: EditText = itemView.findViewById(R.id.etComment)
        private val btnSend: ImageButton = itemView.findViewById(R.id.btnSend)
        private val btnFav: Button = itemView.findViewById(R.id.btnFav)

        init {
            btnSend.setOnClickListener {
                val booking = getItem(adapterPosition)
                val comment = etComment.text.toString()
                commentClickListener.onCommentSendClicked(booking, comment)
            }
            btnFav.setOnClickListener {
                val favourite = getItem(adapterPosition)
                val favouriteResponse =
                    CreateFavouriteResponse(favourite.desk.id, "user_id", favourite.desk.id)
                favoriteClickListener.onFavoriteClicked(favouriteResponse)
            }
        }

        fun bind(booking: BookingResponse) {
            deskLabel.text = booking.desk.label
            deskOfficeName.text = booking.desk.office.name
            dateStart.text = formatDate(booking.dateStart)
            dateEnd.text = formatDate(booking.dateEnd)
        }
    }

    class BookingDiffCallback : DiffUtil.ItemCallback<BookingResponse>() {
        override fun areItemsTheSame(oldItem: BookingResponse, newItem: BookingResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BookingResponse,
            newItem: BookingResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            // Handle parsing exception
            ""
        }
    }
}

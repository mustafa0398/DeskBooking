package com.codingschool.deskbooking.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.bookings.CreateBooking
import com.codingschool.deskbooking.ui.bookingplan.desks.DesksViewModel
import org.koin.android.ext.android.inject
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class FavouriteFragment : Fragment(), FavouriteAdapter.FavReserveClickListener {

    private val favouriteViewModel: FavouriteViewModel by inject()
    private lateinit var desksViewModel: DesksViewModel
    private lateinit var favouriteAdapter: FavouriteAdapter
    private var userId: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favourite, container, false)


        favouriteAdapter = FavouriteAdapter(this)

        val recyclerView: RecyclerView = root.findViewById(R.id.rvFavorites)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = favouriteAdapter

        favouriteViewModel.favourites.observe(viewLifecycleOwner, { favourites ->
            favouriteAdapter.submitList(favourites)
        })

        favouriteViewModel.getUserFavourites(userId)

        return root
    }

    override fun onFavReserveClicked(createBooking: CreateBooking) {
        val deskId = createBooking.desk
        val startDate = LocalDateTime.now()
        val endDate = LocalDateTime.now()
        desksViewModel.createBooking(
            deskId,
            startDate.format(DateTimeFormatter.ISO_DATE_TIME),
            endDate.format(DateTimeFormatter.ISO_DATE_TIME)
        )
    }

    override fun onDeleteFavouriteClicked(favouriteId: String) {
        // Call the deleteFavourite function from the ViewModel
        favouriteViewModel.deleteFavourite(favouriteId)
    }

}

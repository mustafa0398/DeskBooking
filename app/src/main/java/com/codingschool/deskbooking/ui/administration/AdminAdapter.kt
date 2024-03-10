package com.codingschool.deskbooking.ui.administration

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.deskbooking.R
import com.codingschool.deskbooking.data.model.dto.comments.CommentResponse
import com.codingschool.deskbooking.data.model.dto.desks.FixDeskResponse


class AdminAdapter(private var items: MutableList<Any> = mutableListOf(), private val context: Context, private val listener: AdminActionListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_FIX_DESK_REQUEST = 0
        private const val TYPE_COMMENT = 1
        private const val TYPE_HEADER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = items[position]) {
            is FixDeskResponse -> TYPE_FIX_DESK_REQUEST
            is CommentResponse -> TYPE_COMMENT
            is String -> when(item) {
                context.getString(R.string.header_fix_desk_requests), context.getString(R.string.header_comments) -> TYPE_HEADER
                else -> {
                    throw IllegalArgumentException("Unknown header type: $item")
                }
            }
            else -> {
                throw IllegalArgumentException("Unknown item type: $item")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FIX_DESK_REQUEST -> FixDeskRequestViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_admin_fix_desk_request, parent, false),
                listener
            )
            TYPE_COMMENT -> CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_admin_comment, parent, false))
            TYPE_HEADER -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false))
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FixDeskRequestViewHolder -> holder.bind(items[position] as FixDeskResponse)
            is CommentViewHolder -> holder.bind(items[position] as CommentResponse)
            is HeaderViewHolder -> holder.bind(items[position] as String)
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(headerTitle: String) {
            itemView.findViewById<TextView>(R.id.headerTitle).text = headerTitle
        }
    }
    override fun getItemCount(): Int = items.size

    class FixDeskRequestViewHolder(itemView: View, private val listener: AdminActionListener) : RecyclerView.ViewHolder(itemView) {
        fun bind(fixDeskRequest: FixDeskResponse) {
            val context = itemView.context
            val tvUser: TextView = itemView.findViewById(R.id.tvDeskRequestUser)
            val tvDesk: TextView = itemView.findViewById(R.id.tvDeskRequestDesk)

            tvUser.text = "${fixDeskRequest.user.firstname} ${fixDeskRequest.user.lastname}"
            tvDesk.text = fixDeskRequest.desk.label

            when (fixDeskRequest.status) {
                "approved" -> {
                    itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorApproved))
                }
                "rejected" -> {
                    itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRejected))
                }
                else -> itemView.setBackgroundColor(Color.TRANSPARENT)
            }

            itemView.findViewById<Button>(R.id.btnApproveFixDesk).setOnClickListener {
                listener.onFixDeskRequestConfirm(fixDeskRequest)
            }
            itemView.findViewById<Button>(R.id.btnDeclineFixDesk).setOnClickListener {
                listener.onFixDeskRequestDecline(fixDeskRequest)
            }
        }
    }

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUser: TextView = itemView.findViewById(R.id.tvUser)
        private val tvComment: TextView = itemView.findViewById(R.id.tvComment)

        fun bind(comment: CommentResponse) {
            tvUser.text = "${comment.user.firstname} ${comment.user.lastname}"
            tvComment.text = comment.comment
        }
    }

    fun updateItems(newItems: MutableList<Any>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
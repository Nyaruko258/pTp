package com.example.ptp.composition


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ptp.R

data class Peer(
    val name: String,
    val address: String
)

class PeerAdapter(
    private val peerList: List<Peer>
) : RecyclerView.Adapter<PeerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val peerName: TextView = view.findViewById(R.id.peerName)
        val peerAddress: TextView = view.findViewById(R.id.peerAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.peer_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val peer = peerList[position]
            Toast.makeText(parent.context, "you clicked view ${peer.name}", Toast.LENGTH_SHORT).show()
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val peer = peerList[position]
        holder.peerName.text = peer.name
        holder.peerAddress.text = peer.address
    }

    override fun getItemCount(): Int = peerList.size
}
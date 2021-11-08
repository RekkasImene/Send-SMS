package fr.mastersid.rekkas.sendsms.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.mastersid.rekkas.sendsms.R

class QuestionsViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val answercount: TextView = itemView.findViewById(R.id.answerCount)
    val buttonSend : ImageButton= itemView.findViewById(R.id.send_sms)
}
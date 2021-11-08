package fr.mastersid.rekkas.sendsms.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.room.Entity
import fr.mastersid.rekkas.sendsms.R
import fr.mastersid.rekkas.sendsms.models.Item

@Entity(tableName = "question_table", primaryKeys = ["title"])
data class Questions(val title: String, val answerCount: Int) {
    class DiffCallback: DiffUtil.ItemCallback <Questions >() {
        override fun areItemsTheSame(oldItem: Questions , newItem: Questions): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areContentsTheSame(oldItem: Questions , newItem: Questions): Boolean {
            return oldItem.title == newItem.title && oldItem.answerCount ==
                    newItem.answerCount
        }
    }
}
class QuestionsListAdapter(private val clickListner:(item:Questions)-> Unit): ListAdapter<Questions, QuestionsViewHolder>
( Questions.DiffCallback ()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.questions_item, parent,  false)
        return QuestionsViewHolder(view)
    }

    @SuppressLint("StringFormatInvalid")
    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val item = getItem(position)

        holder.title.text = "${item.title}"
        holder.answercount.text = "${item.answerCount}"
        holder.buttonSend.setOnClickListener {
            clickListner(item)
        }
    }

}
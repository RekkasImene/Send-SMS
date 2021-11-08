package fr.mastersid.rekkas.sendsms.adapters

import com.squareup.moshi.FromJson
import fr.mastersid.rekkas.sendsms.models.ClassJsonToKotlin

class QuestionsMoshiAdapter {
    @FromJson
    fun fromJson(classJsonToKotlin: ClassJsonToKotlin): List <Questions> {
        return classJsonToKotlin.items.map {
                Item -> Questions(Item.title, Item.answer_count)
        }
    }
}
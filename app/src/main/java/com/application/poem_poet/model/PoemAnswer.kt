package com.application.poem_poet.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class PoemAnswer : Serializable {
    @PrimaryKey
    var id: String = ""
    var username: String = ""
    var titlePoem: String = ""
    var namePoet: String = ""
    var genre: String = ""
    var poem: String = ""
    var avatar: String = ""
    var uid: String = ""
    var like: Int = 0
}


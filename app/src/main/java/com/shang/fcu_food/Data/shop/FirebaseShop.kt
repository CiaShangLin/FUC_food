package com.shang.fcu_food.Data.shop

import android.content.Context
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.Query
import com.google.firebase.storage.StorageReference
import java.io.File

interface FirebaseShop {
    abstract open fun getQuery(): Query

    abstract open fun getOption(): FirebaseRecyclerOptions<Shop>

    abstract open fun getSnapParser(): SnapshotParser<Shop>
}
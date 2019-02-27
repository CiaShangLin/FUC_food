package com.shang.fcu_food.Data.shop

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.database.Query

interface FirebaseShop {
    abstract open fun getQuery(): Query

    abstract open fun getOption(): FirebaseRecyclerOptions<Shop>

    abstract open fun getSnapParser(): SnapshotParser<Shop>
}
package io.imhungry.repository.firebase.model.places

import com.google.firebase.Timestamp

data class FirebaseReview(
    val authorUid: String,
    val comment: String,
    val created: Timestamp,
    val placesId: String,
    val rating: Int
) {
    companion object {
        const val FIREBASE_COLLECTION_NAME_REVIEWS = "reviews"
    }
}
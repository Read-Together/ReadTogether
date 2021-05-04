package ar.edu.unq.readtogether.readtogether.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import org.springframework.stereotype.Service
import java.io.IOException
import javax.annotation.PostConstruct


@Service
class FireBaseInitialization {

    val database = "readtogether-4453b"

    @PostConstruct
    @Throws(IOException::class)
    private fun initfirebase() {
        val serviceAccount = javaClass.classLoader.getResourceAsStream("readtogether-firebase-key.json")
        val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://$database.firebaseio.com")
                .build()
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }

    val firestore: Firestore
        get() = FirestoreClient.getFirestore()
}
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
class FirebaseInitialization {

    val database = "readtogether-4453b"

    @PostConstruct
    @Throws(IOException::class)
    private fun initfirebase() {
        val serviceAccount = javaClass.classLoader.getResourceAsStream("readtogether-firebase-key.json")
        //val serviceAccount = FileInputStream("./resources/readtogether-firebase-key.json")
        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://$database.firebaseio.com/")
                .build()
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }

    fun getFirestore() : Firestore {
        return FirestoreClient.getFirestore()
    }

}
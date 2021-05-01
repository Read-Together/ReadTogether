package ar.edu.unq.readtogether.readtogether.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.stereotype.Service
import java.io.FileInputStream


@Service
class FireBaseInitialization {


    fun initialization(){
        val serviceAccount = FileInputStream("./serviceAccountKey.json")
        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        FirebaseApp.initializeApp(options)
    }

}
package br.com.danillo.desafiosoftplan.services;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    @PostConstruct
    private void initialize() throws IOException {
        try {
            var serviceAccount = new FileInputStream("firebaseService.json");
            //this.getClass().getClassLoader().getResourceAsStream("./firebaseService.json");
            var options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("desafio-softplan")
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }
}

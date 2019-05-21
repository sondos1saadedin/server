package com.example.demo.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;

/**
 * Created by sondo on 02/03/2018.
 */
public class BaseDatabase {

    private static FirebaseDatabase mDatabase;

    public static FirebaseAuth getmAuth() {
        if (baseDatabase == null) {
            baseDatabase = new BaseDatabase();
        }
        return mAuth;
    }

    private static FirebaseAuth mAuth;

    private static FirebaseApp firebaseApp;


    private static BaseDatabase baseDatabase;

    private BaseDatabase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\sondo\\IdeaProjects\\spring-boot-react-example-master\\server\\src\\main\\resources\\service-account.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://my-awesome-project-25af5.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
            firebaseApp = FirebaseApp.initializeApp(options, "app1");
            mDatabase = FirebaseDatabase.getInstance(firebaseApp);
            mAuth = FirebaseAuth.getInstance(firebaseApp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() {
        try {

            DatabaseReference ref = mDatabase.getReference("server/saving-data/fireblog");
            ref.child("alanisawesome").setValue("hello");
            System.out.println("Helloddddddddddddd");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static FirebaseDatabase getmDatabase() {
        if (baseDatabase == null) {
            baseDatabase = new BaseDatabase();
        }
        return mDatabase;
    }


}

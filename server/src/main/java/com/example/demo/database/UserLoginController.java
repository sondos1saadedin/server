package com.example.demo.database;

import com.example.demo.data.Action;
import com.example.demo.data.Password;
import com.example.demo.data.PersonUser;
import com.example.demo.data.Scenario;
import com.example.demo.selenium.RunSeleniumUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

@RestController
public class UserLoginController {



    public UserLoginController() {

    }



    // localhost:8090/sign-in?email = sondos@email.com & password = 123456
    @GetMapping("/sign-in")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList SignIn(@RequestParam("email") String email, @RequestParam("password") String password) {


        try {
            String userId = TestDatabase.getmAuth().getUserByEmailAsync(email).get().getUid();
            final Semaphore semaphore = new Semaphore(0);
            final String[] storedPassword = new String[1];
            DatabaseReference userRef = TestDatabase.getmDatabase().getReference("PersonUser").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    PersonUser personUser = dataSnapshot.getValue(PersonUser.class);

                    storedPassword[0] = personUser.getPassword();

                    semaphore.release();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            semaphore.acquire();
            System.out.println(storedPassword[0]);

            if (Password.check( password, storedPassword[0])) {
                System.out.println("Successfully fetched user data: " + email);
                return new ArrayList<>(Arrays.asList("true"));
            } else {
                System.out.println("Failed to login in");
                return new ArrayList<>(Arrays.asList("false"));

            }


        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList("false"));
        }
    }


    // localhost:8090/sign-up?
    @GetMapping("/sign-up")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList SignUp(@RequestParam("email") String email, @RequestParam("password") String password,
                            @RequestParam("username") String username, @RequestParam("phone") String phone,
                            @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {


        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setEmailVerified(false)
                    .setPassword(Password.getSaltedHash(password))
                    .setDisplayName(username)
                    .setDisabled(false);

            UserRecord userRecord = TestDatabase.getmAuth().createUserAsync(request).get();
            System.out.println("Successfully created new user: " + userRecord.getUid());
            PersonUser personUser = new PersonUser(firstName, lastName, email, Password.getSaltedHash(password), phone, username);
            TestDatabase.getmDatabase().getReference("PersonUser").child(userRecord.getUid()).setValue(personUser);

            return new ArrayList<>(Arrays.asList("true"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList("false"));
        }
    }


    @PostMapping("/save-scenario")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public String saveScenario(@RequestBody String data) {
        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");

        System.out.println(data);

        JSONObject array = new JSONObject(data);
        System.out.println(array.toString());

        System.out.println(array.get("data").toString());

        try {
            ObjectMapper mapper = new ObjectMapper();
            Scenario scenario = mapper.readValue(data, Scenario.class);
            //    StreamSource json = new StreamSource(array.toString());

            //Pretty print
            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scenario);
            System.out.println(prettyStaff1);

//            System.out.println(scenario.getData().get(0).getAction());



            DatabaseReference ref = TestDatabase.getmDatabase().getReference("ScenarioUsers");
            String userId = TestDatabase.getmAuth().getUserByEmailAsync(scenario.getEmail()).get().getUid();
            ref.child(userId).child(scenario.getTitle()).setValueAsync(scenario);

            RunSeleniumUtils runSeleniumUtils = new RunSeleniumUtils();
            runSeleniumUtils.execute(scenario,userId );
            System.out.println(scenario.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @GetMapping("/get-scenarios")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList getScenarios(@RequestParam("email") String email) {

        try {
            final Semaphore semaphore = new Semaphore(0);
             ArrayList<Scenario> scenarios = new ArrayList<>();
            String userId = TestDatabase.getmAuth().getUserByEmailAsync(email).get().getUid();
            TestDatabase.getmDatabase().getReference().child("ScenarioUsers").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<Scenario> tempScenarios = scenarios;

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        scenarios.add(postSnapshot.getValue(Scenario.class));
                    }
                    semaphore.release();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("cancelled");
                }
            });
            semaphore.acquire();
            return scenarios;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("NULLLLLLL");
        return null;
    }

}
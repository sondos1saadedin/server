package com.example.demo.database;

import com.example.demo.data.Password;
import com.example.demo.data.PersonUser;
import com.example.demo.data.Scenario;
import com.example.demo.selenium.RunSeleniumUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

@RestController
public class UserController {


    public UserController() {

    }


    // localhost:8090/sign-in?email = sondos@email.com & password = 123456
    @GetMapping("/sign-in")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList SignIn(@RequestParam("email") String email, @RequestParam("password") String password) {


        try {
            String userId = BaseDatabase.getmAuth().getUserByEmailAsync(email).get().getUid();
            final Semaphore semaphore = new Semaphore(0);
            final String[] storedPassword = new String[1];
            DatabaseReference userRef = BaseDatabase.getmDatabase().getReference("PersonUser").child(userId);

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

            if (Password.check(password, storedPassword[0])) {
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

            UserRecord userRecord = BaseDatabase.getmAuth().createUserAsync(request).get();
            System.out.println("Successfully created new user: " + userRecord.getUid());
            PersonUser personUser = new PersonUser(firstName, lastName, email, Password.getSaltedHash(password), phone, username);
            BaseDatabase.getmDatabase().getReference("PersonUser").child(userRecord.getUid()).setValue(personUser);

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

            String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scenario);
            System.out.println(prettyStaff1);


            DatabaseReference ref = BaseDatabase.getmDatabase().getReference("ScenarioUsers");
            String userId = BaseDatabase.getmAuth().getUserByEmailAsync(scenario.getEmail()).get().getUid();
            ref.child(userId).child(scenario.getTitle()).setValueAsync(scenario);

            RunSeleniumUtils runSeleniumUtils = new RunSeleniumUtils();
            runSeleniumUtils.execute(scenario, userId);
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
            System.out.println("email:::" + email);
            final Semaphore semaphore = new Semaphore(0);
            ArrayList<Scenario> scenarios = new ArrayList<>();
            String userId = BaseDatabase.getmAuth().getUserByEmailAsync(email).get().getUid();
            BaseDatabase.getmDatabase().getReference().child("ScenarioUsers").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<Scenario> tempScenarios = scenarios;

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
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
        return null;
    }


    @GetMapping("/get-user")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList getUser(@RequestParam("email") String email) {
        System.out.println("two");

        try {
            final Semaphore semaphore = new Semaphore(0);
            ArrayList<PersonUser> arrayList = new ArrayList<>();
            String userId = BaseDatabase.getmAuth().getUserByEmailAsync(email).get().getUid();
            BaseDatabase.getmDatabase().getReference().child("PersonUser").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    arrayList.add(dataSnapshot.getValue(PersonUser.class));
                    semaphore.release();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("cancelled");
                }
            });
            semaphore.acquire();

            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }




    @GetMapping("/update-user")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList updateUser(@RequestParam("preEmail") String preEmail, @RequestParam("email") String email, @RequestParam("password") String password,
                            @RequestParam("username") String username, @RequestParam("phone") String phone,
                            @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {


        try {




            UserRecord userRecord = BaseDatabase.getmAuth().getUserByEmailAsync(preEmail).get();
            System.out.println("Successfully created new user: " + userRecord.getUid());
            BaseDatabase.getmDatabase().getReference("PersonUser").child(userRecord.getUid()).child("email").setValue(email);
            BaseDatabase.getmDatabase().getReference("PersonUser").child(userRecord.getUid()).child("firstName").setValue(firstName);
            BaseDatabase.getmDatabase().getReference("PersonUser").child(userRecord.getUid()).child("lastName").setValue(lastName);
            BaseDatabase.getmDatabase().getReference("PersonUser").child(userRecord.getUid()).child("username").setValue(username);
            BaseDatabase.getmDatabase().getReference("PersonUser").child(userRecord.getUid()).child("phoneNumber").setValue(phone);



            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(userRecord.getUid())
                    .setEmail(email)
                    .setPhoneNumber("+" + phone)
                    .setEmailVerified(true)
                    .setDisplayName(username);
            UserRecord userRecord2 = FirebaseAuth.getInstance().updateUserAsync(request).get();
            System.out.println("Successfully updated user: " + userRecord.getUid());




            return new ArrayList<>(Arrays.asList("true"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList("false"));
        }
    }

    @GetMapping("/share-scenario")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList shareScenario(@RequestParam("myEmail") String myEmail,
                                   @RequestParam("hisEmail") String hisEmail,
                                   @RequestParam("sName") String sName) {

        try {
            System.out.println(myEmail + "       " + hisEmail + "       " + sName);
            final Semaphore semaphore = new Semaphore(0);
            final DatabaseReference ref = BaseDatabase.getmDatabase().getReference("ScenarioUsers");
            final DatabaseReference shareScenarioUser = BaseDatabase.getmDatabase().getReference("ShareScenarioUsers");
            String userId = BaseDatabase.getmAuth().getUserByEmailAsync(myEmail).get().getUid();
            final String userId2 = BaseDatabase.getmAuth().getUserByEmailAsync(hisEmail).get().getUid();
            ref.child(userId).child(sName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Scenario scenario = (dataSnapshot.getValue(Scenario.class));

                    shareScenarioUser.child(userId2).child(sName).setValue(scenario);
                    semaphore.release();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("cancelled");
                }
            });
            semaphore.acquire();

            return new ArrayList<>(Arrays.asList("true"));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
        return new ArrayList<>(Arrays.asList("false"));
    }



    @GetMapping("/get-shared-scenarios")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList getSharedScenarios(@RequestParam("email") String email) {

        try {
            System.out.println("email:::" + email);
            final Semaphore semaphore = new Semaphore(0);
            ArrayList<Scenario> scenarios = new ArrayList<>();
            String userId = BaseDatabase.getmAuth().getUserByEmailAsync(email).get().getUid();
            BaseDatabase.getmDatabase().getReference().child("ShareScenarioUsers").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<Scenario> tempScenarios = scenarios;

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
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
        return null;
    }
}
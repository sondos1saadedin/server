package com.example.demo.testScenarios;

import com.example.demo.database.TestDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
public class TestScenarioController {
    private TestScenarioRepository repository;
   // String[] arr = {"A", "B", "C", "D", "U", "A"};

    public TestScenarioController(TestScenarioRepository repository) {
        this.repository = repository;
    }
    ArrayList<String> returnedList;

    @GetMapping("/test-scenarios")
    @CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
    public ArrayList scenarioList() {
        //ArrayList<String> arrayList = new ArrayList<String>();
      //  Collections.addAll(arrayList, arr);


        DatabaseReference ref = TestDatabase.getmDatabase().getReference("test-scenarios");
        //ref.setValue(arrayList);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                 returnedList = (ArrayList<String>) dataSnapshot.getValue();
                System.out.println(returnedList);
                // ...

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("canceled");
            }
        };
        ref.addValueEventListener(postListener);

        return returnedList;
    }




    public TestScenario getTestScenarioById(int id) {


        return new TestScenario("Aseel", 22);
    }

    private boolean isGreat(TestScenario testScenario) {
        return !testScenario.getName().equals("A") &&
                !testScenario.getName().equals("B") &&
                !testScenario.getName().equals("C");
    }
}
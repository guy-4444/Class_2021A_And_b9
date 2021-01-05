package com.classy.class_2021a_and_b9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Activity_Panel extends AppCompatActivity {

    private MaterialButton panel_BTN_updateUser;
    private MaterialButton panel_BTN_updateItems;
    private TextView panel_LBL_name;

    private HashMap<String, Product> productsMap = new HashMap<>();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        findViews();
        initViews();
        generateProducts();
        user = new User().setName("Guest");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //String value = dataSnapshot.getValue(String.class);
                //Log.d("pttt", "Value is: " + value);

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    Log.d("pttt", "Product: " + product.getName() + " " + product.getPrice());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("pttt", "Failed to read value.", error.toException());
            }
        });

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersRef = database.getReference("users");
        usersRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    User value = dataSnapshot.getValue(User.class);
                    user = value;
                    panel_LBL_name.setText("Hi " + user.getName());
                    updateFavoriteProduct();
                    Log.d("pttt", "Value is: " + value);
                } catch (Exception ex) { }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("pttt", "Failed to read value.", error.toException());
            }
        });
    }

    private void updateFavoriteProduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");
        ValueEventListener vel = myRef.child(user.getFavoriteProduct()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                if (product == null) {
                    return;
                }
                panel_LBL_name.setText("Hi " + user.getName() + "\n" + product.getName() + " " + product.getPrice() + " $");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void generateProducts() {
        productsMap.put("P0001", new Product()
                .setKey("P0001")
                .setName("Bread")
                .setPrice(15.0)
                .setMilky(false)
                .setGluten(true));

        productsMap.put("P0002", new Product()
                .setKey("P0002")
                .setName("Pretzel")
                .setPrice(8.0)
                .setMilky(false)
                .setGluten(true));

        productsMap.put("P0003", new Product()
                .setKey("P0003")
                .setName("Burekas cheese & spinach")
                .setPrice(25.0)
                .setMilky(true)
                .setGluten(false));
    }

    private void updateItems() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");

        for (Map.Entry<String, Product> entry : productsMap.entrySet()) {
            Product product = entry.getValue();
            myRef.child(product.getKey()).setValue(product);
        }
    }

    private void updateUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String uid = firebaseUser.getUid();
        String phone = firebaseUser.getPhoneNumber();

        Log.d("pttt", "uid=" + uid);
        Log.d("pttt", "phone=" + phone);

        User user = new User()
                .setName("Stav Finz")
                .setPhone(phone)
                .setUid(uid)
                .setFavoriteProduct("P0002");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(user.getUid()).setValue(user);
    }

    private void initViews() {
        panel_BTN_updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        panel_BTN_updateItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItems();
            }
        });
    }

    private void findViews() {
        panel_BTN_updateUser = findViewById(R.id.panel_BTN_updateUser);
        panel_BTN_updateItems = findViewById(R.id.panel_BTN_updateItems);
        panel_LBL_name = findViewById(R.id.panel_LBL_name);
    }
}
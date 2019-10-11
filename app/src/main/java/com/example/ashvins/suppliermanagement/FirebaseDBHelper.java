package com.example.ashvins.suppliermanagement;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDBHelper {
    private FirebaseDatabase firebaseDB;
    private DatabaseReference ref;
    private List<supplierDB> suppliers = new ArrayList<>();

    public interface dStatus {
        void DataLoaded(List<supplierDB> suppliers, List<String> keys);
        void DataAdded();
        void DataUpdated();
        void DataDeleted();
    }

    public FirebaseDBHelper() {
        firebaseDB = FirebaseDatabase.getInstance();
        ref = firebaseDB.getReference("Supplier");
    }

    public void readSupplier(final dStatus ds) {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                suppliers.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    supplierDB supplier = keyNode.getValue(supplierDB.class);
                    suppliers.add(supplier);
                }
                ds.DataLoaded(suppliers, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addSupplier(supplierDB supplier, final dStatus ds) {
        String key = ref.push().getKey();

        ref.child(key).setValue(supplier)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ds.DataAdded();
                    }
                });
    }

    public void updateSupplier(String key, supplierDB supplier, final dStatus ds) {
        ref.child(key).setValue(supplier)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ds.DataUpdated();
                    }
                });
    }

    public void deleteSupplier(String key, final dStatus ds) {
        ref.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ds.DataDeleted();
                    }
                });
    }
}

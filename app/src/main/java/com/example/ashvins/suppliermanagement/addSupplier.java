package com.example.ashvins.suppliermanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class addSupplier extends Fragment {
    EditText editPD, editAddr, editLNO, editMNO, editEmail;
    Button add;
    Spinner pCat, cName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_supplier, container, false);

        pCat = v.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.ProductCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pCat.setAdapter(adapter);



        cName = v.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.CompanyName, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cName.setAdapter(adapter2);

        editPD = (EditText) v.findViewById(R.id.editPD);
        editAddr = (EditText) v.findViewById(R.id.editAddr);
        editLNO = (EditText) v.findViewById(R.id.editLNO2);
        editMNO = (EditText) v.findViewById(R.id.editMNO2);
        editEmail = (EditText) v.findViewById(R.id.editEmail);
        add = (Button) v.findViewById(R.id.addBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supplierDB supplier = new supplierDB();
                String pc = pCat.getSelectedItem().toString();
                String pd = editPD.getText().toString();
                String cn = cName.getSelectedItem().toString();
                String addr = editAddr.getText().toString();
                Long lno = Long.parseLong(editLNO.getText().toString());
                Long mno = Long.parseLong(editMNO.getText().toString());
                String email = editEmail.getText().toString();

                supplier.setpCategory(pCat.getSelectedItem().toString());
                supplier.setpDesc(editPD.getText().toString());
                supplier.setcName(cName.getSelectedItem().toString());
                supplier.setcAddr(editAddr.getText().toString());
                supplier.setLandline(lno);
                supplier.setMobile(mno);
                supplier.setEmail(editEmail.getText().toString());

                new FirebaseDBHelper().addSupplier(supplier, new FirebaseDBHelper.dStatus() {
                    @Override
                    public void DataLoaded(List<supplierDB> suppliers, List<String> keys) {

                    }

                    @Override
                    public void DataAdded() {
                        Toast.makeText(getActivity(), "Data added successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataUpdated() {

                    }

                    @Override
                    public void DataDeleted() {

                    }
                });

            }
        });
        return v;
    }
}

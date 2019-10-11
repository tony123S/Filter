package com.example.ashvins.suppliermanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class viewSupplier extends AppCompatActivity {
    EditText pc, pd, cn, ca, lno, mno, eMail;
    Button update, delete;

    private String key;
    private String pCategory;
    private String pDesc;
    private String cName;
    private String cAddr;
    private Long landline;
    private Long mobile;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_supplier);

        key = getIntent().getStringExtra("key");
        pCategory = getIntent().getStringExtra("pCategory");
        pDesc = getIntent().getStringExtra("pDesc");
        cName = getIntent().getStringExtra("cName");
        cAddr = getIntent().getStringExtra("cAddr");
        //landline = getIntent().getLongExtra("landline", landline);
        //mobile = getIntent().getLongExtra("mobile", mobile);
        email = getIntent().getStringExtra("email");

        pc = (EditText)findViewById(R.id.editPC2);
        pc.setText(pCategory);
        pd = (EditText)findViewById(R.id.editPD2);
        pd.setText(pDesc);
        cn = (EditText)findViewById(R.id.editCN2);
        cn.setText(cName);
        ca = (EditText)findViewById(R.id.editAddr2);
        ca.setText(cAddr);
        lno = (EditText)findViewById(R.id.editLNO2);
        //lno.setText(landline.toString());
        mno = (EditText)findViewById(R.id.editMNO2);
        //mno.setText(mobile.toString());
        eMail = (EditText)findViewById(R.id.editEmail2);
        eMail.setText(email);
        update = (Button)findViewById(R.id.updateBtn);
        delete = (Button)findViewById(R.id.deleteBtn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supplierDB supplier = new supplierDB();
                supplier.setpCategory(pc.getText().toString());
                supplier.setpDesc(pd.getText().toString());
                supplier.setcName(cn.getText().toString());
                supplier.setcAddr(ca.getText().toString());
                supplier.setLandline(Long.parseLong(lno.getText().toString()));
                supplier.setMobile(Long.parseLong(mno.getText().toString()));
                supplier.setEmail(eMail.getText().toString());

                new FirebaseDBHelper().updateSupplier(key, supplier, new FirebaseDBHelper.dStatus() {
                    @Override
                    public void DataLoaded(List<supplierDB> suppliers, List<String> keys) {

                    }

                    @Override
                    public void DataAdded() {

                    }

                    @Override
                    public void DataUpdated() {
                        Toast.makeText(viewSupplier.this, "Data updated successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataDeleted() {

                    }
                });

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FirebaseDBHelper().deleteSupplier(key, new FirebaseDBHelper.dStatus() {
                    @Override
                    public void DataLoaded(List<supplierDB> suppliers, List<String> keys) {

                    }

                    @Override
                    public void DataAdded() {

                    }

                    @Override
                    public void DataUpdated() {

                    }

                    @Override
                    public void DataDeleted() {
                        Toast.makeText(viewSupplier.this, "Data deleted successfully", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}

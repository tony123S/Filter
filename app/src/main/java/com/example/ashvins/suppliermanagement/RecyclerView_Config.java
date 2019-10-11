package com.example.ashvins.suppliermanagement;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerView_Config {
    private Context sContext;
    private SupplierAdapter supAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<supplierDB> suppliers, List<String> keys) {
        sContext = context;
        supAdapter = new SupplierAdapter(suppliers, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(supAdapter);
    }

    public SupplierAdapter setConfig(Context context, List<supplierDB> suppliers) {
        sContext = context;
        supAdapter = new SupplierAdapter(suppliers);
        return supAdapter;
    }

    class SupplierView extends RecyclerView.ViewHolder {
        private TextView pc, pd, cn, ca, lno, mno, email;
        private String key;

        public SupplierView(ViewGroup parent) {
            super(LayoutInflater.from(sContext).inflate(R.layout.supplier_list_2, parent, false));

            pc = (TextView) itemView.findViewById(R.id.pCat_View);
            pd = (TextView) itemView.findViewById(R.id.pDesc_View);
            cn = (TextView) itemView.findViewById(R.id.cName_View);
            ca = (TextView) itemView.findViewById(R.id.cAddr_View);
            lno = (TextView) itemView.findViewById(R.id.lNo_View);
            mno = (TextView) itemView.findViewById(R.id.mNo_View);
            email = (TextView) itemView.findViewById(R.id.eMail_View);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(sContext, viewSupplier.class);
                    intent.putExtra("key", key);
                    intent.putExtra("pCategory", pc.getText().toString());
                    intent.putExtra("pDesc", pd.getText().toString());
                    intent.putExtra("cName", cn.getText().toString());
                    intent.putExtra("cAddr", ca.getText().toString());
                    //intent.putExtra("landline", lno.getText().toString());
                    //intent.putExtra("mobile", mno.getText().toString());
                    intent.putExtra("email", email.getText().toString());

                    sContext.startActivity(intent);
                }
            });
        }

        public void bind(supplierDB supplier, String key) {
            pc.setText(supplier.getpCategory());
            pd.setText(supplier.getpDesc());
            cn.setText(supplier.getcName());
            ca.setText(supplier.getcAddr());
            lno.setText(supplier.getLandline().toString());
            mno.setText(supplier.getMobile().toString());
            email.setText(supplier.getEmail());
            this.key = key;
        }
    }

    class SupplierAdapter extends RecyclerView.Adapter<SupplierView> implements Filterable {
        private List<supplierDB> supplierList;
        private List<String> keys;
        private List<supplierDB> supplierListFull;

        public SupplierAdapter(List<supplierDB> supplierList, List<String> keys) {
            this.supplierList = supplierList;
            this.keys = keys;
            supplierListFull = new ArrayList<>(supplierList);
        }

        public SupplierAdapter(List<supplierDB> supplierList) {
            supplierListFull = new ArrayList<>(supplierList);
            this.supplierList = supplierList;
        }

        @Override
        public SupplierView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SupplierView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SupplierView holder, int position) {
            holder.bind(supplierList.get(position), keys.get(position));
        }

        @Override
        public int getItemCount() {
            return supplierList.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
//                List<supplierDB> filteredList = new ArrayList<>();

                    if (charString.isEmpty()) {
                        supplierList = supplierListFull;
                    } else {
                        List<supplierDB> filteredList = new ArrayList<>();
                        for (supplierDB supplier : supplierListFull) {
                            if (supplier.getcName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(supplier);
                            }
                        }
                        supplierList = filteredList;
                    }

                    FilterResults results = new FilterResults();
                    results.values = supplierList;

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    supplierList = (ArrayList<supplierDB>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }
    }
}

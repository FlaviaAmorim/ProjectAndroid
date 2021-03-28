package com.example.imoveis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class CustomAdapterRealtor extends ArrayAdapter<Realtor> implements View.OnClickListener {

    public ArrayList<Realtor> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtId;
        TextView txtEmail;
        TextView txtPhone;

    }

    public CustomAdapterRealtor(ArrayList<Realtor> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Realtor dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtId = (TextView) convertView.findViewById(R.id.id);
            viewHolder.txtEmail = (TextView) convertView.findViewById(R.id.email);
            viewHolder.txtPhone = (TextView) convertView.findViewById(R.id.phone);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText("Nome: " + dataModel.getName());
        viewHolder.txtId.setText("Código: " + dataModel.getId()+ "");
        viewHolder.txtEmail.setText("E-mail: " + dataModel.getEmail());
        viewHolder.txtPhone.setText("Telefone: " + dataModel.getPhone());
        return convertView;
    }
}


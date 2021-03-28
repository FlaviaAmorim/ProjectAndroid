package com.example.imoveis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterProperty extends ArrayAdapter<Property> implements View.OnClickListener {

    public ArrayList<Property> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtId;
        TextView txtTitle;
        TextView txtAddress;
        TextView txtNumber;
        TextView txtValue;
        TextView txtOwner;
        TextView txtContact;
        TextView txtDescription;
    }

    public CustomAdapterProperty(ArrayList<Property> data, Context context) {
        super(context, R.layout.property_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Property dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.property_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.titleProporty);
            viewHolder.txtId = (TextView) convertView.findViewById(R.id.idProporty);
            viewHolder.txtAddress = (TextView) convertView.findViewById(R.id.AddressProporty);
            viewHolder.txtNumber = (TextView) convertView.findViewById(R.id.numberProporty);
            viewHolder.txtValue = (TextView) convertView.findViewById(R.id.valueProporty);
            viewHolder.txtOwner = (TextView) convertView.findViewById(R.id.ownerProporty);
            viewHolder.txtContact = (TextView) convertView.findViewById(R.id.contactProporty);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.descriptionProporty);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtTitle.setText("Titulo: " + dataModel.getTitle());
        viewHolder.txtId.setText("Código: " + dataModel.getId()+ "");
        viewHolder.txtAddress.setText("Endereco: " + dataModel.getAddress());
        viewHolder.txtNumber.setText("Nº: " + dataModel.getNumber());
        viewHolder.txtValue.setText("Valor: " + dataModel.getValue());
        viewHolder.txtContact.setText("Telefone: " + dataModel.getPhone());
        viewHolder.txtDescription.setText("Descricao: " + dataModel.getDescription());
        return convertView;
    }
}

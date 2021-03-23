package com.example.imoveis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListViewPropertyActivity extends AppCompatActivity {


    private ListView listViewProperty;
    private Button btnSign, btnClear;

    ArrayList<Property> arrayList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listproperty);
        PropertyDataBase db = PropertyDataBase.getDatabase(this);
        List<Property> properties =  db.propertyDAO().queryAll();
        setTitle(getString(R.string.lista_imoveis));

        if (properties == null || properties.size() == 0) {
            insertMockDatabase();
            properties = db.propertyDAO().queryAll();
        }

        listViewProperty = findViewById(R.id.listViewProperty);

        for (Property c : properties) {
            arrayList.add(c);
        }

        CustomAdapterProperty adapter = new CustomAdapterProperty(arrayList, getApplicationContext());

        listViewProperty.setAdapter(adapter);

        listViewProperty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Property dataModel= arrayList.get(position);

                Snackbar.make(view, dataModel.getTitle()+"\n"+dataModel.getValue(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        btnSign = findViewById(R.id.btnsign);
        btnClear = findViewById(R.id.btnclear);

        btnSign.setOnClickListener(v -> {
            Intent intent = new Intent(this, PropertyRegisterActivity.class);
            startActivity(intent);
        });

        btnClear.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Limpar lista")
                    .setMessage("Tem certeza que deseja limpar a lista?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clearTableButton();
                            adapter.notifyDataSetChanged();
                            listViewProperty.refreshDrawableState();
                        }
                    })
                    .setNegativeButton("Não", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        });
    }

    public void clearTableButton() {
        PropertyDataBase db = PropertyDataBase.getDatabase(this);
        Property property = new Property();
        db.propertyDAO().deleteAll();
        arrayList.clear();
    }

    private void insertMockDatabase() {
        PropertyDataBase db = PropertyDataBase.getDatabase(this);
        db.propertyDAO().insert(new Property ("Apartamento 2 quartos", "Rua Francisco Deroso", "12", "R$250.000,00","41 99233-3232", "Excelente apartamento com 2 quartos, sala e cozinha conjugada."));
        db.propertyDAO().insert(new Property ("Casa 3 quartos", "Rua Isabel Fonseca", "2323", "R$1.200,00","41 99234-3632", "Casa em ótimo estado, com 3 quartos, sala, cozinha, banhiero e lavanderia."));
        db.propertyDAO().insert(new Property ("Loteamento 200m²", "Av São Cristovao", "543", "R$1.250.000,00","41 3030-4567", "Loteamento amplo, com excelente localização."));
        db.propertyDAO().insert(new Property ("Casa 2 quartos", "Rua Luiz da Cunha", "897", "R$550.000,00","41 3304-1567", "Casa grande em localização central."));
        db.propertyDAO().insert(new Property ("Casa para alugar", "Rua Santos Andrade", "1200", "R$900,00","41 99464-0987", "Aluga-se casa para casal, com 1 quarto, cozinha, sala, banheiro e lavanderia."));
        db.propertyDAO().insert(new Property ("Apartamento", "Av. Antônio Faria", "450", "R$190.000,00","41 98723-0094", "Vende-se apartamento na planta, com previsao de entrega para maio de 2021."));
        db.propertyDAO().insert(new Property ("Terreno", "Rua Padre Anchieta", "2035", "R$800.000,00","41 99046-3214", "Vende-se terreno com 700m², localizado no melhor bairro da região."));
        db.propertyDAO().insert(new Property ("Apartamento para alugar", "Rua Munhoz da Rocha", "115", "R$1.250,00","41 98267-8960", "Apartamento mobiliado para alugar, com 55m², 3 quartos, sala, cozinha e lavanderia"));
        db.propertyDAO().insert(new Property ("Vende-se Casa", "Av. Paraná", "490", "R$300.000,00","41 98528-7634", "Vende-se casa de 100m²"));
        db.propertyDAO().insert(new Property ("Apartamento em excelente localização", "Rua Bento Viana", "87", "R$200.000,00","41 99803-5521", "Apartamento a venda com 44m², 2 quartos, sala e cozinha"));
    }
}

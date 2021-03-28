package com.example.imoveis;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListViewPropertyActivity extends AppCompatActivity {


    private ListView listViewProperty;
    private Button btnSign, btnClear;
    private ActionMode actionMode;
    private View       viewSelected;
    private int        positionSelected= -1;
    private ArrayAdapter<Property> adapterProperty;

    ArrayList<Property> arrayList = new ArrayList<>();

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.menu_acao_contextual, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuItemEditar:
                    alter();
                    mode.finish();
                    return true;
                case R.id.menuItemExcluir:
                    Property property = (Property) listViewProperty.getItemAtPosition(positionSelected);
                    clearTableButton(property);
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelected != null){
                viewSelected.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode         = null;
            viewSelected    = null;
            listViewProperty.setEnabled(true);

        }
    };

    private void loadProperty() {
        PropertyDataBase db = PropertyDataBase.getDatabase(this);
        List<Property> properties =  db.propertyDAO().queryAll();
        setTitle(getString(R.string.lista_imoveis));

        for (Property c : properties) {
            arrayList.add(c);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listproperty);

        btnSign = findViewById(R.id.btnsign);
        btnClear = findViewById(R.id.btnclear);

        adapterProperty = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                arrayList);

        loadProperty();
        listViewProperty = findViewById(R.id.listViewProperty);

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

        listViewProperty.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        if (actionMode != null){
                            return false;
                        }

                        positionSelected = position;

                        view.setBackgroundColor(Color.LTGRAY);

                        viewSelected = view;

                        listViewProperty.setEnabled(false);

                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                });

        btnSign.setOnClickListener(v -> {
            Intent intent = new Intent(this, PropertyRegisterActivity.class);
            startActivity(intent);
        });
    }

    public void clearTableButton(final Property property) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.atencao)
                .setMessage(R.string.tem_certeza_que_deseja_excluir_este_item)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PropertyDataBase dataBase = PropertyDataBase.getDatabase(ListViewPropertyActivity.this);
                        dataBase.propertyDAO().delete(property);
                        adapterProperty.remove(property);
                        loadProperty();
                        CustomAdapterProperty adapter = new CustomAdapterProperty(arrayList, getApplicationContext());
                        listViewProperty.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton(R.string.nao, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void alter() {
        Property property = arrayList.get(positionSelected);
        PropertyRegisterActivity.alterPropery(this, property);

    }
}

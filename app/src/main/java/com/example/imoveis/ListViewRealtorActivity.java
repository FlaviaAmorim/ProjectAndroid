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
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class ListViewRealtorActivity extends AppCompatActivity {

    private ListView listViewRealtor;
    private ArrayAdapter<Realtor> adapter;
    private ActionMode actionMode;
    private View       viewSelected;
    private int        positionSelected = -1;

    ArrayList<Realtor> arrayList = new ArrayList<>();

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
                    Realtor realtor = (Realtor) listViewRealtor.getItemAtPosition(positionSelected);
                    delete(realtor);
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
            listViewRealtor.setEnabled(true);

        }
    };

    private void loadRealtor() {
        RealtorDataBase db = RealtorDataBase.getDatabase(this);
        List<Realtor> realtor = db.realtorDAO().queryAll();
        setTitle(getString(R.string.lista_corretores));

        arrayList.clear();
        for (Realtor c : realtor) {
            arrayList.add(c);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listclient);

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                arrayList);

        loadRealtor();

        listViewRealtor = findViewById(R.id.listViewClient);

        CustomAdapterRealtor adapter = new CustomAdapterRealtor(arrayList, getApplicationContext());
        listViewRealtor.setAdapter(adapter);

        listViewRealtor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Realtor realtor = (Realtor) parent.getItemAtPosition(position);

                positionSelected = position;
                alter();

                Snackbar.make(view, realtor.getName() + "\n"+realtor.getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        listViewRealtor.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewRealtor.setOnItemLongClickListener(
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

                        listViewRealtor.setEnabled(false);

                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                });
    }

    private void alter() {
        Realtor realtor = arrayList.get(positionSelected);
        RealtorRegisterActivity.alterRealtor(this,
                realtor);

    }

    private void delete (final Realtor realtor) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.atencao)
                .setMessage(R.string.tem_certeza_que_deseja_excluir_este_item)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RealtorDataBase dataBase = RealtorDataBase.getDatabase(ListViewRealtorActivity.this);
                        dataBase.realtorDAO().delete(realtor);
                        adapter.remove(realtor);
                        loadRealtor();
                        CustomAdapterRealtor adapter = new CustomAdapterRealtor(arrayList, getApplicationContext());
                        listViewRealtor.setAdapter(adapter);
;                       adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.nao, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                Intent intent = new Intent(this, RealtorRegisterActivity.class);
                startActivityForResult(intent,1);
                return true;
            case R.id.menuItemSobre:
                Intent intent2 = new Intent(this, AboutActivity.class);
                startActivityForResult(intent2,2);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}

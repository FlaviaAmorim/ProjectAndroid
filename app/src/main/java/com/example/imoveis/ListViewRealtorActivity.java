package com.example.imoveis;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.view.ActionMode.;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ActionMode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListViewRealtorActivity extends AppCompatActivity {

    private ListView listViewClient;
    private ArrayAdapter<Realtor> adapter;
    private androidx.appcompat.view.ActionMode actionMode;
    private View       viewSelecionada;
    private int        posicaoSelecionada = -1;
    ArrayList<Realtor> arrayList = new ArrayList<>();
    DataBaseRealtor db = new DataBaseRealtor(this);

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
                    alterar();
                    mode.finish();
                    return true;
                case R.id.menuItemExcluir:
                    excluir();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode         = null;
            viewSelecionada    = null;
            listViewClient.setEnabled(true);

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listclient);

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                arrayList);

        listViewClient = findViewById(R.id.listViewClient);

        registerForContextMenu(listViewClient);

        List<Realtor> clients = db.clientList();
        setTitle(getString(R.string.lista_corretores));

        if (clients == null || clients.size() == 0) {
            insertMockDatabase();
            clients = db.clientList();
        }

        for (Realtor c : clients) {
            arrayList.add(c);
        }

        CustomAdapterRealtor adapter = new CustomAdapterRealtor(arrayList, getApplicationContext());

        listViewClient.setAdapter(adapter);

        listViewClient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Realtor dataModel= arrayList.get(position);

                Snackbar.make(view, dataModel.getName() + "\n"+dataModel.getEmail(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        listViewClient.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewClient.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        if (actionMode != null){
                            return false;
                        }

                        posicaoSelecionada = position;

                        view.setBackgroundColor(Color.LTGRAY);

                        viewSelecionada = view;

                        listViewClient.setEnabled(false);

                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                });
    }

    private void alterar() {

        Realtor realtor = arrayList.get(posicaoSelecionada);
        RealtorRegisterActivity.alterarPessoa(this, realtor);

    }

    private void excluir () {

        new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Tem certeza que deseja excluir este item?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayList.remove(posicaoSelecionada);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Não", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    ///CRIAR MENU OPCOES
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

    private void insertMockDatabase() {
//        db.addClient(new Realtor ("Michele da Silva","41 99999-9999","flavia@gmail.com","1234"));
//        db.addClient(new Realtor ("Diego Carvalho","41 99845-4545","diego@gmail.com","4321"));
//        db.addClient(new Realtor ("Luis Felipe","41 98764-4590","luis@gmail.com","1342"));
//        db.addClient(new Realtor ("Daniela dos Santos","41 98832-1456","danila@gmail.com","2431"));
//        db.addClient(new Realtor ("Barbara Antunes","41 99806-2567","barbara@gmail.com","9876"));
//        db.addClient(new Realtor ("Alisson Barbosa","41 99803-7748","alisson@gmail.com","4575"));
//        db.addClient(new Realtor ("Pedro Henrique","41 98528-6534","pedro@gmail.com","9448"));
//        db.addClient(new Realtor ("Carol Freitas","41 99066-8776","carol@gmail.com","8745"));
//        db.addClient(new Realtor ("Thiago Farias","41 99346-2309","thiago@gmail.com","2367"));
//        db.addClient(new Realtor ("Felipe da Silva","41 98725-0876","felipe@gmail.com","9009"));
    }

}

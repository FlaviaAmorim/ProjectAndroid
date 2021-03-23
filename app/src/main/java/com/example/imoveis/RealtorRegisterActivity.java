package com.example.imoveis;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RealtorRegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone, editTextPassWord;
    private RadioButton rdButtonM, rdButtonF, rdButtonNe;
    private RadioGroup rdGender;
    private CheckBox cbTerms;
    public Spinner spUF;
    public static final String NOME    = "NOME";
    public static final String MODO    = "MODO";
    public static final int    ALTERAR = 2;

    DataBaseRealtor db = new DataBaseRealtor(this);

    public static void alterarPessoa(AppCompatActivity activity, Realtor realtor){

        Intent intent = new Intent(activity, RealtorRegisterActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(NOME, realtor.getName());

        activity.startActivityForResult(intent, ALTERAR);
    }

    ///CRIAR MENU OPCOES
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opcao_cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItemSalvar:
                save();
                return true;
            case R.id.menuItemLimpar:
                clear();
                Toast.makeText(this, R.string.campos_limpos, Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);



        setTitle(getString(R.string.CADASTRO_CORRETOR));

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextTelefone);
        editTextPassWord = findViewById(R.id.editTextSenha);
        rdButtonM = findViewById(R.id.radioButtonM);
        rdButtonF = findViewById(R.id.radioButtonF);
        rdButtonNe = findViewById(R.id.radioButtonNe);
        rdGender = findViewById(R.id.radioGroupGenero);
        cbTerms = findViewById(R.id.checkBoxConcordo);
        spUF = findViewById(R.id.spinnerUF);

        loadSpinner();

    }

    private void loadSpinner() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Selecione");
        lista.add("PR");
        lista.add("ES");
        lista.add("GO");
        lista.add("MA");
        lista.add("AC");
        lista.add("AL");
        lista.add("AP");
        lista.add("AM");
        lista.add("BA");
        lista.add("CE");
        lista.add("MT");
        lista.add("MS");
        lista.add("MG");
        lista.add("PA");
        lista.add("PB");
        lista.add("PE");
        lista.add("PI");
        lista.add("RJ");
        lista.add("RN");
        lista.add("RS");
        lista.add("RO");
        lista.add("RR");
        lista.add("SC");
        lista.add("SP");
        lista.add("SE");
        lista.add("TO");
        lista.add("DF");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        spUF.setAdapter(adapter);
    }

    private boolean validateEmailFormat(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public void save() {

        String nome = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String telefone = editTextPhone.getText().toString();
        String senha = editTextPassWord.getText().toString();
        String spinner = spUF.getSelectedItem().toString();

        Boolean sameFieldIsEmpty = nome.trim().isEmpty() || email.trim().isEmpty() || telefone.trim().isEmpty() || senha.trim().isEmpty();

        switch (rdGender.getCheckedRadioButtonId()){

            case R.id.radioButtonM:
                break;

            case R.id.radioButtonF:
                break;

            case R.id.radioButtonNe:
                break;

            default:
                Toast.makeText(this, R.string.selecione_o_genero, Toast.LENGTH_SHORT).show();
                return;
        }

        if (sameFieldIsEmpty) {
            Toast.makeText(this, R.string.todos_os_campos_devem_ser_preenchidos, Toast.LENGTH_SHORT).show();

        } else if (spinner.equals(getString(R.string.selecione))) {
            Toast.makeText(this, R.string.selecione_um_estado, Toast.LENGTH_SHORT).show();

        } else if (!validateEmailFormat(email)) {
            Toast.makeText(this, R.string.email_invalido, Toast.LENGTH_SHORT).show();

        } else if (!cbTerms.isChecked()) {
            Toast.makeText(this, R.string.voce_deve_aceitar_os_termos_de_uso, Toast.LENGTH_SHORT).show();
            return;

        } else {
            db.addClient(new Realtor (nome,telefone,email,senha));
            Toast.makeText(this, R.string.cadastro_efetuado_com_sucesso, Toast.LENGTH_SHORT).show();
            clear();

            Intent intent = new Intent(this, ListViewRealtorActivity.class);
            startActivity(intent);
        }
    }


    private void clear() {

        editTextName.setText(null);
        editTextPhone.setText(null);
        editTextEmail.setText(null);
        editTextPassWord.setText(null);
        rdButtonM.setChecked(false);
        rdButtonF.setChecked(false);
        rdButtonNe.setChecked(false);
        cbTerms.setChecked(false);
        spUF.setAdapter(null);

        editTextName.requestFocus();

    }
}

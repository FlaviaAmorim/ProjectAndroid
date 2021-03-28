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
    public static final String NAME    = "NAME";
    public static final String PHONE    = "PHONE";
    public static final String EMAIL    = "EMAIL";
    public static final String PASSWORD    = "PASSWORD";
    public static final String MODE    = "MODO";
    public static final int NEW = 1;
    public static final int    ALTER = 2;
    private int mode;
    private String nameOriginal;
    private String phoneOriginal;
    private String emailOriginal;
    private String passawordOriginal;

    public static void alterRealtor(AppCompatActivity activity, Realtor realtor){
        Intent intent = new Intent(activity, RealtorRegisterActivity.class);
        intent.putExtra(MODE, ALTER);
        intent.putExtra(NAME, realtor.getName());
        intent.putExtra(PHONE, realtor.getPhone());
        intent.putExtra(EMAIL, realtor.getEmail());
        intent.putExtra(PASSWORD, realtor.getPassword());
        activity.startActivityForResult(intent, ALTER);
    }

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
          mode = bundle.getInt(MODE, NEW);

          if (mode == NEW) {
              setTitle(getString(R.string.CADASTRO_CORRETOR));
          } else {

              nameOriginal = bundle.getString(NAME);
              editTextName.setText(nameOriginal);

              phoneOriginal = bundle.getString(PHONE);
              editTextPhone.setText(phoneOriginal);

              emailOriginal = bundle.getString(EMAIL);
              editTextEmail.setText(emailOriginal);

              passawordOriginal = bundle.getString(PASSWORD);
              editTextPassWord.setText(passawordOriginal);

          }
        }

        loadSpinner();
    }

    private void loadSpinner() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Selecione");
        list.add("PR");
        list.add("ES");
        list.add("GO");
        list.add("MA");
        list.add("AC");
        list.add("AL");
        list.add("AP");
        list.add("AM");
        list.add("BA");
        list.add("CE");
        list.add("MT");
        list.add("MS");
        list.add("MG");
        list.add("PA");
        list.add("PB");
        list.add("PE");
        list.add("PI");
        list.add("RJ");
        list.add("RN");
        list.add("RS");
        list.add("RO");
        list.add("RR");
        list.add("SC");
        list.add("SP");
        list.add("SE");
        list.add("TO");
        list.add("DF");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        spUF.setAdapter(adapter);
    }

    private boolean validateEmailFormat(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public void save() {
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String phone = editTextPhone.getText().toString();
        String password = editTextPassWord.getText().toString();
        String spinner = spUF.getSelectedItem().toString();

        Boolean sameFieldIsEmpty = name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty() || password.trim().isEmpty();

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

            RealtorDataBase database = RealtorDataBase.getDatabase(this);
            database.realtorDAO().insert(new Realtor (name,phone,email,password));
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

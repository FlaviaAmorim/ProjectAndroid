package com.example.imoveis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PropertyRegisterActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextAddress, editTextNumber, editTextValue, editTextNameOwner, editTextNumberPhone, editTextDescription;
    private RadioButton radioButtonApto, radioButtonHouse, radioButtonGround, radioButtonSale, radioButtonRent;
    private RadioGroup rdGroup, rdContract;
    private Button buttonSalvar2, buttonLimpar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        setTitle(getString(R.string.cadastro_imovel));

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextValue = findViewById(R.id.editTextValue);
        editTextNameOwner = findViewById(R.id.editTextNameOwner);
        editTextNumberPhone = findViewById(R.id.editTextNumberPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        radioButtonApto = findViewById(R.id.radioButtonApto);
        radioButtonHouse = findViewById(R.id.radioButtonHouse);
        radioButtonGround = findViewById(R.id.radioButtonGround);
        radioButtonSale = findViewById(R.id.radioButtonSale);
        radioButtonRent = findViewById(R.id.radioButtonRent);
        rdGroup = findViewById(R.id.rdGroup);
        rdContract = findViewById(R.id.rdContract);
        buttonSalvar2 = findViewById(R.id.buttonSalvar2);
        buttonLimpar2 = findViewById(R.id.buttonLimpar2);

        buttonSalvar2.setOnClickListener(v -> {
            save();
        });

        buttonLimpar2.setOnClickListener(v -> {
            clear();
            Toast.makeText(this, R.string.campos_limpos, Toast.LENGTH_SHORT).show();
        });
    }

    public void save() {

        String titulo = editTextTitle.getText().toString();
        String endereco = editTextAddress.getText().toString();
        String numero = editTextNumber.getText().toString();
        String preco = editTextValue.getText().toString();
        String contato = editTextNumberPhone.getText().toString();
        String descricao = editTextDescription.getText().toString();
        String responsavel = editTextNameOwner.getText().toString();

        Boolean fieldIsEmpty = titulo.isEmpty() || endereco.isEmpty() || numero.isEmpty() || preco.isEmpty() || contato.isEmpty() || descricao.isEmpty();

        if (fieldIsEmpty) {
            if (radioButtonApto.isChecked() == false) {
                if (radioButtonHouse.isChecked() == false) {
                    if (radioButtonGround.isChecked() == false) {
                        if (radioButtonRent.isChecked() == false) {
                            if (radioButtonSale.isChecked() == false) {
                                //Toast.makeText(this, "Todos os campos devem ser preenchidos!", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(this, R.string.selecione_o_tipo_de_contrato, Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(this, R.string.selecione_o_tipo_de_imovel, Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(this, R.string.todos_os_campos_devem_ser_preenchidos, Toast.LENGTH_SHORT).show();
            }
        } else {
            PropertyDataBase database = PropertyDataBase.getDatabase(this);
            database.propertyDAO().insert(new Property(titulo, endereco, numero, preco, contato, descricao));
            Intent intent = new Intent(this, ListViewPropertyActivity.class);
            startActivity(intent);
        }
    }

    private void clear() {

        editTextTitle.setText(null);
        editTextAddress.setText(null);
        editTextNumber.setText(null);
        editTextValue.setText(null);
        editTextNumberPhone.setText(null);
        editTextDescription.setText(null);
        editTextNameOwner.setText(null);
        radioButtonApto.setChecked(false);
        radioButtonHouse.setChecked(false);
        radioButtonGround.setChecked(false);
        radioButtonSale.setChecked(false);
        radioButtonRent.setChecked(false);


        editTextTitle.requestFocus();

    }
}

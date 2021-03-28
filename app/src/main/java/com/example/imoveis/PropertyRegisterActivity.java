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
    public static final int NEW = 1;
    public static final int    ALTER = 2;
    private int mode;

    public static final String MODE    = "MODE";
    public static final String TITLE = "TITLE";
    public static final String ADDRESS = "ADDRESS";
    public static final String NUMBER = "NUMBER";
    public static final String VALUE = "VALUE";
    public static final String PHONE = "PHONE";
    public static final String DESCRIPTION = "DESCRIPTION";

    private String titleOriginal;
    private String addressOriginal;
    private String numberOriginal;
    private String phoneOriginal;
    private String valueOriginal;
    private String descriptionOriginal;



    public static void alterPropery(AppCompatActivity activity, Property property ){

        Intent intent = new Intent(activity, PropertyRegisterActivity.class);
        intent.putExtra(MODE, ALTER);
        intent.putExtra(TITLE, property.getTitle());
        intent.putExtra(PHONE, property.getPhone());
        intent.putExtra(ADDRESS, property.getAddress());
        intent.putExtra(NUMBER, property.getNumber());
        intent.putExtra(VALUE, property.getValue());
        intent.putExtra(DESCRIPTION, property.getDescription());
        activity.startActivityForResult(intent, ALTER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            mode = bundle.getInt(MODE, NEW);

            if (mode == NEW) {
                setTitle(getString(R.string.cadastro_imovel));
            } else {

                setTitle(getString(R.string.alterar_propriedade));

                titleOriginal = bundle.getString(TITLE);
                editTextTitle.setText(titleOriginal);

                addressOriginal = bundle.getString(ADDRESS);
                editTextAddress.setText(addressOriginal);

                numberOriginal = bundle.getString(NUMBER);
                editTextNumber.setText(numberOriginal);

                valueOriginal = bundle.getString(VALUE);
                editTextValue.setText(valueOriginal);

                phoneOriginal = bundle.getString(PHONE);
                editTextNumberPhone.setText(phoneOriginal);

                descriptionOriginal = bundle.getString(DESCRIPTION);
                editTextDescription.setText(descriptionOriginal);

            }
        }

        buttonSalvar2.setOnClickListener(v -> {
            save();
        });

        buttonLimpar2.setOnClickListener(v -> {
            clear();
            Toast.makeText(this, R.string.campos_limpos, Toast.LENGTH_SHORT).show();
        });
    }

    public void save() {
        String title = editTextTitle.getText().toString();
        String address = editTextAddress.getText().toString();
        String number = editTextNumber.getText().toString();
        String value = editTextValue.getText().toString();
        String phone = editTextNumberPhone.getText().toString();
        String description = editTextDescription.getText().toString();
        String property = editTextNameOwner.getText().toString();

        Boolean fieldIsEmpty = title.isEmpty() || address.isEmpty() || number.isEmpty() || value.isEmpty() || phone.isEmpty() || description.isEmpty();

        if (fieldIsEmpty) {
            if (radioButtonApto.isChecked() == false) {
                if (radioButtonHouse.isChecked() == false) {
                    if (radioButtonGround.isChecked() == false) {
                        if (radioButtonRent.isChecked() == false) {
                            if (radioButtonSale.isChecked() == false) {
                                Toast.makeText(this, R.string.todos_os_campos_devem_ser_preenchidos, Toast.LENGTH_SHORT).show();
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
            database.propertyDAO().insert(new Property(title, address, number, value, phone, description));
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

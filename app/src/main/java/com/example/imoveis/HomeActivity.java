package com.example.imoveis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class HomeActivity extends AppCompatActivity {

    private Button registerRealtor, registerProperty;
    private Switch myswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else setTheme(R.style.Theme_Imoveis);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myswitch = (Switch)findViewById(R.id.myswitch);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            myswitch.setChecked(true);
        }

        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    restartApp();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    restartApp();
                }
            }
        });

        registerRealtor = findViewById(R.id.btnRealtor);
        registerProperty = findViewById(R.id.btnHouse);

        registerRealtor.setOnClickListener(v -> {
            Intent intent = new Intent(this, RealtorRegisterActivity.class);
            startActivity(intent);
        });
        registerProperty.setOnClickListener(v -> {
            Intent intent = new Intent(this, PropertyRegisterActivity.class);
            startActivity(intent);
        });
    }

    public void restartApp() {
        Intent i = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        finish();
    }
}

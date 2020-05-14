package com.example.caffeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;
    private String drink;
    private String name;
    private String password;
    private StringBuilder additionsBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        textViewHello = findViewById(R.id.textViewAskOrder);
        textViewAdditions = findViewById(R.id.textViewAdditions);
        String additions = String.format(getString(R.string.ask_additions), drink);
        textViewAdditions.setText(additions);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        additionsBuilder = new StringBuilder();

        Intent intent = getIntent();
        if (intent.hasExtra("name") && intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        } else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }
        String hello = String.format(getString(R.string.ask_order), name);
        textViewHello.setText(hello);
        drink = getString(R.string.tea);

    }

    public void changeDrink(View view) {
        RadioButton button = (RadioButton) view;
        int id = button.getId();
        if (id == R.id.radioButtonTea) {
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(view.VISIBLE);
            spinnerCoffee.setVisibility(view.INVISIBLE);
            checkBoxLemon.setVisibility(view.VISIBLE);
        } else if (id == R.id.radioButtonCoffee) {
            drink = getString(R.string.coffee);
            spinnerTea.setVisibility(view.INVISIBLE);
            spinnerCoffee.setVisibility(view.VISIBLE);
            checkBoxLemon.setVisibility(view.INVISIBLE);
            checkBoxLemon.setChecked(false);
        }
        String additions = String.format(getString(R.string.ask_additions), drink);
        textViewAdditions.setText(additions);
    }

    public void sendOrder(View view) {
        additionsBuilder.setLength(0);
        if (checkBoxMilk.isChecked()) {
            additionsBuilder.append(getString(R.string.milk)).append(" ");
        }
        if (checkBoxSugar.isChecked()) {
            additionsBuilder.append(getString(R.string.sugar)).append(" ");
        }
        if (checkBoxLemon.isChecked()) {
            additionsBuilder.append(getString(R.string.milk)).append(" ");
        }

        String drinkOption = "";
        if (drink.equals(getString(R.string.tea))){
            drinkOption = spinnerTea.getSelectedItem().toString();
        } else {
            drinkOption = spinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.full_order), name, password, drink, drinkOption, additionsBuilder.toString());

        /* String additions;
        if (additionsBuilder.length() > 0){
            additions = "Необходимые добавки: " + additionsBuilder.toString();
        } else {
            additions = "";
        }
        String fullOrder = order + additions; */

        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", order);
        startActivity(intent);
    }
}

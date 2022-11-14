package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String RESULT_VALUE = "Resultado";
    private EditText etNumber1;
    private EditText etNumber2;
    private TextView tvResult;
    private Spinner spOp;
    private Button btnOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber1 = (EditText) findViewById(R.id.etNumber1);
        etNumber2 = (EditText) findViewById(R.id.etNumber2);
        tvResult = (TextView) findViewById(R.id.tvResult);
        spOp = (Spinner) findViewById(R.id.spinner);

        String[] operationList = {"+", "-", "x", "/"};
        ArrayAdapter<String> adapter = new ArrayAdapter(
                this, R.layout.spinner_custom_text, operationList);
        spOp.setAdapter(adapter);
        btnOperation = (Button) findViewById(R.id.btnCalculate);
        btnOperation.setOnClickListener((view -> {
            etNumber1.onEditorAction(EditorInfo.IME_ACTION_DONE);
            etNumber2.onEditorAction(EditorInfo.IME_ACTION_DONE);
            double result = Calculate();
            //tvResult.setText("Resultado: " + result);
            /**
             * @brief Mostrar el mensaje en otra pantalla
             */
            Intent intent = new Intent(this, resultActivity.class);
            intent.putExtra(RESULT_VALUE, Double.toString(result));
            startActivity(intent);
        }));
    }

    private double Calculate() {
        double number1 = Double.parseDouble(etNumber1.getText().toString());
        double number2 = Double.parseDouble(etNumber2.getText().toString());

        String selectedOP = spOp.getSelectedItem().toString();
        double result = 0.0;
        switch (selectedOP) {
            case "+":
                result = number1 + number2;
                break;
            case "-":
                result = number1 - number2;
                break;
            case "x":
                result = number1 * number2;
                break;
            case "/":
                if (number2 != 0) {
                    result = number1 / number2;
                } else {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "No se puede dividir por cero", Toast.LENGTH_LONG);
                    toast1.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
                    toast1.show();
                }
                break;
            default:
                result = 0.0;
        }
        return result;
    }

    @Override
    protected void onStop() {
        etNumber1.setText("");
        etNumber2.setText("");
        //tvResult.setText("Resultado: ");
        super.onStop();
    }
}
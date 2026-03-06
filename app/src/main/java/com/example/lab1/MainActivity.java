package com.example.lab1; // Переконайся, що пакет відповідає твоєму

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements InputFragment.OnInputListener, ResultFragment.OnCancelListener {

    private InputFragment inputFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Додаємо InputFragment при першому запуску
            inputFragment = new InputFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_input, inputFragment, "INPUT_FRAGMENT")
                    .commit();
        } else {
            inputFragment = (InputFragment) getSupportFragmentManager().findFragmentByTag("INPUT_FRAGMENT");
        }
    }

    // Метод з інтерфейсу OnInputListener (спрацьовує при натисканні ОК)
    @Override
    public void onDataSent(String type, String brand) {
        String result = "Тип: " + type + "\nФірма: " + brand;

        // Створюємо другий фрагмент і передаємо йому текст
        ResultFragment resultFragment = ResultFragment.newInstance(result);

        // Відображаємо його у нижньому контейнері
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_result, resultFragment, "RESULT_FRAGMENT")
                .commit();
    }

    // Метод з інтерфейсу OnCancelListener (спрацьовує при натисканні Cancel)
    @Override
    public void onCancelClicked() {
        // Знаходимо другий фрагмент та видаляємо його
        ResultFragment resultFragment = (ResultFragment) getSupportFragmentManager().findFragmentByTag("RESULT_FRAGMENT");
        if (resultFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(resultFragment)
                    .commit();
        }

        // Очищаємо форму в першому фрагменті
        if (inputFragment != null) {
            inputFragment.clearForm();
        }
    }
}
package com.example.lab1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment {

    private Spinner spinnerPhoneType;
    private RadioGroup radioGroupBrand;
    private OnInputListener listener;

    public interface OnInputListener {
        void onDataSent(String type, String brand);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnInputListener) {
            listener = (OnInputListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnInputListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        spinnerPhoneType = view.findViewById(R.id.spinnerPhoneType);
        radioGroupBrand = view.findViewById(R.id.radioGroupBrand);
        Button buttonOk = view.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(v -> {
            int selectedBrandId = radioGroupBrand.getCheckedRadioButtonId();
            if (selectedBrandId == -1) {
                Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
            } else {
                String selectedType = spinnerPhoneType.getSelectedItem().toString();
                RadioButton selectedBrandButton = view.findViewById(selectedBrandId);
                String selectedBrand = selectedBrandButton.getText().toString();

                listener.onDataSent(selectedType, selectedBrand);
            }
        });

        return view;
    }

    public void clearForm() {
        spinnerPhoneType.setSelection(0);
        radioGroupBrand.clearCheck();
    }
}
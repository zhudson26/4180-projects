package com.example.inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//InClass07
//Evan Hemming and Zaccary Hudson

public class NewContactFragment extends Fragment {

    public NewContactFragment() {
        // Required empty public constructor
    }

    public static NewContactFragment newInstance() {
        NewContactFragment fragment = new NewContactFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_contact, container, false);
        ((MainActivity) getActivity()).setTitle(getString(R.string.new_contact));

        EditText nameEdit = view.findViewById(R.id.editTextName);
        EditText emailEdit = view.findViewById(R.id.editTextEmail);
        EditText phoneEdit = view.findViewById(R.id.editTextPhone);
        Spinner typeSpinner = view.findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        Button submitButton = view.findViewById(R.id.submitButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String phoneNumber = phoneEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String type = typeSpinner.getSelectedItem().toString();

                if (!name.matches("") && !phoneNumber.matches("") && phoneNumber.length() >= 10 && !email.matches("") && !type.matches("")) {
                    String phoneNumberFormatted = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) +
                            '-' + phoneNumber.substring(6, 10);
                    mListener.addContact(name, email, phoneNumberFormatted, type);
                } else {
                    Toast.makeText(getContext(), getString(R.string.entry_warning), Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.returnToList();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mListener = (NewContactFragment.IListener) context;
    }
    NewContactFragment.IListener mListener;

    public interface IListener{
        void addContact(String name, String email, String phone, String type);
        void returnToList();
    }

}
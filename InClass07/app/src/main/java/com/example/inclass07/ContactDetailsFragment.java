package com.example.inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


//InClass07
//Evan Hemming and Zaccary Hudson

public class ContactDetailsFragment extends Fragment {

    Contact contact;

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    public static ContactDetailsFragment newInstance() {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        ((MainActivity) getActivity()).setTitle(getString(R.string.contact_details));

        TextView nameView = view.findViewById(R.id.textViewDetailName);
        TextView emailView = view.findViewById(R.id.textViewDetailEmail);
        TextView phoneView = view.findViewById(R.id.textViewDetailPhone);
        TextView typeView = view.findViewById(R.id.textViewDetailType);

        nameView.setText(contact.name);
        emailView.setText(contact.email);
        phoneView.setText(contact.phone);
        typeView.setText(contact.type);

        Button deleteButton = view.findViewById(R.id.deleteButtonDetails);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.deleteContactFromDetails(contact);
            }
        });

        Button updateButton = view.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", contact.name);
                mListener.goToUpdate(contact);
            }
        });

        return view;
    }

    public void setContact(Contact cont){
        contact = cont;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mListener = (ContactDetailsFragment.IListener) context;
    }
    ContactDetailsFragment.IListener mListener;

    public interface IListener{
        void goToUpdate(Contact contact);
        void deleteContactFromDetails(Contact contact);
    }
}
package com.example.inclass07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//InClass07
//Evan Hemming and Zaccary Hudson

public class ContactListFragment extends Fragment {

    ArrayList<Contact> contactsList = new ArrayList<>();
    ContactAdapter contactAdapter;
    ListView listView;


    public ContactListFragment() {
        // Required empty public constructor
    }

    public static ContactListFragment newInstance() {
        ContactListFragment fragment = new ContactListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        ((MainActivity) getActivity()).setTitle(getString(R.string.contact_list));

        mListener.getContacts();

        listView = view.findViewById(R.id.contactListView);
        contactAdapter = new ContactAdapter(getContext(), R.layout.contact_list_item, contactsList);
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.goToDetails(contactsList.get(i));
            }
        });

        Button addButton = view.findViewById(R.id.addContactButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToAdd();
            }
        });

        return view;
    }

    public class ContactAdapter extends ArrayAdapter<Contact>{

        public ContactAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Contact> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, parent, false);
            }

            Contact contact = getItem(position);

            TextView nameView = convertView.findViewById(R.id.nameTextView);
            TextView emailView = convertView.findViewById(R.id.emailTextView);
            TextView phoneView = convertView.findViewById(R.id.phoneTextView);
            TextView typeView = convertView.findViewById(R.id.typeTextView);
            Button deleteButton = convertView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteContactFromList(contact);
                }
            });

            nameView.setText(contact.name);
            emailView.setText(contact.email);
            phoneView.setText(contact.phone);
            typeView.setText(contact.type);

            return convertView;
        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mListener = (ContactListFragment.IListener) context;
    }
    ContactListFragment.IListener mListener;

    public interface IListener{
        void deleteContactFromList(Contact contact);
        void goToAdd();
        void goToDetails(Contact contact);
        void getContacts();
    }

    public void updateContacts(ArrayList<Contact> contacts){
        contactsList.clear();
        contactsList.addAll(contacts);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contactAdapter.notifyDataSetChanged();
            }
        });
    }
}
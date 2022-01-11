package com.example.inclass07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

//InClass07
//Evan Hemming and Zaccary Hudson

public class MainActivity extends AppCompatActivity implements ContactListFragment.IListener, NewContactFragment.IListener, ContactDetailsFragment.IListener, EditContactFragment.IListener{
    private final OkHttpClient client = new OkHttpClient();
    ContactListFragment contactListFragment = new ContactListFragment();
    ContactDetailsFragment contactDetailsFragment = new ContactDetailsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, contactListFragment, "ListFragment")
                .commit();
    }

    @Override
    public void deleteContactFromList(Contact contact) {
        deleteContact(contact);
    }

    @Override
    public void goToAdd() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new NewContactFragment(), "AddFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToDetails(Contact contact) {
        contactDetailsFragment.setContact(contact);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, contactDetailsFragment, "DetailsFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void getContacts() {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contacts/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    try {
                        ArrayList<Contact> contacts = new ArrayList<>();
                        JSONObject json = new JSONObject(response.body().string());
                        JSONArray contactsJson = json.getJSONArray("contacts");
                        for(int i = 0; i < contactsJson.length(); i++){
                            JSONObject contactJsonObject = contactsJson.getJSONObject(i);
                            Contact contact = new Contact(contactJsonObject.getString("Name"),
                                    contactJsonObject.getString("Email"),
                                    contactJsonObject.getString("Phone"),
                                    contactJsonObject.getString("PhoneType"),
                                    contactJsonObject.getInt("Cid"));
                            contacts.add(contact);
                        }
                        updateContacts(contacts);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void deleteContact(Contact contact){
        FormBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(contact.id))
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/delete")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("test", "onResponse: " + body);
                    getContacts();
                } else {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("test", "onResponse: " + body);
                }
            }
        });
    }

    public void updateContacts(ArrayList<Contact> contacts){
        contactListFragment.updateContacts(contacts);
    }

    @Override
    public void addContact(String name, String email, String phone, String type) {
        FormBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("type", type)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/create")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("test", "onResponse: " + body);
                    getContacts();
                    getSupportFragmentManager().popBackStack();
                } else {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("test", "onResponse: " + body);
                }
            }
        });
    }

    @Override
    public void returnToList() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToUpdate(Contact contact) {
        EditContactFragment editContactFragment = new EditContactFragment();
        editContactFragment.setContact(contact);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, editContactFragment, "EditFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteContactFromDetails(Contact contact) {
        deleteContact(contact);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void updateContact(String name, String email, String phone, String type, int id) {
        Contact cont = new Contact(name, email, phone, type, id);

        FormBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .add("type", type)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/contact/json/update")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("test", "onResponse: " + body);
                    getContacts();
                    contactDetailsFragment.setContact(cont);
                    getSupportFragmentManager().popBackStack();
                } else {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d("test", "onResponse: " + body);
                }
            }
        });
    }
}
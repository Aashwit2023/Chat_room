package com.example.myfirstapplication;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapplication.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatFragment extends Fragment {
    private SearchView searchView;
    ArrayList<UserModel> users=new ArrayList<>();


    LinearLayout txtbox;
    public ChatFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_chat, container, false);
//        searchView= view.findViewById(R.id.searchView);
//        searchView.clearFocus();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterList(newText);
//                return false;
//            }
//        });
//      Inflate the layout for this fragment
        Toast.makeText(getActivity(), "Welcome to the Chatifier World", Toast.LENGTH_LONG).show();
        View v= inflater.inflate(R.layout.fragment_chat, container, true);

        //************* create a Arraylist of all userModel type to store info of all users *********


        RecyclerView recycler=v.findViewById(R.id.recycler_users);
        UserListAdapter adapter=new UserListAdapter(getContext(),users);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //in this automatic shows a vertical

//        HashMap<String,String> Data=new HashMap<>();
//        SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
//        Data.put("date",date.format(new Date()));

        //********************* let's select all record of users table******************
        FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
            for (DataSnapshot data:snapshot.getChildren())
            {
                UserModel user=new UserModel();
//                user.date = data.child("date").getValue(String.class);
                user.uid= data.getKey();
                user.name=data.child("name").getValue(String.class);
                user.email=data.child("email").getValue(String.class);
                user.password=data.child("password").getValue(String.class);
                user.gender=data.child("gender").getValue(String.class);
                user.pic=data.child("pic").getValue(String.class);
                user.about=data.child("about").getValue(String.class);
                if(!user.uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    users.add(user);//add user to the list
            }
            adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }

//    private void filterList(String Text) {
//        ArrayList filteredArray=new ArrayList<>();
//        for(UserModel usermodel :users){
//            if(usermodel.name.toLowerCase().contains(Text.toLowerCase())){
//                filteredArray.add(usermodel);
//            }
//            if(filteredArray.isEmpty()){
//                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
//            }
//            else UserListAdapter.setfilteredArray(filteredArray);
//
//        }
//    }
}
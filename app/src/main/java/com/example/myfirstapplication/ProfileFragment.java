package com.example.myfirstapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    ImageView img_profile,img_changeprofile;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //*************open galary to chosse profile picture*********************

        img_profile=v.findViewById(R.id.img_profile);
        img_changeprofile=v.findViewById(R.id.img_changeprofile);
        img_changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("image/*");
                //in.setType(text/*);
                //in.setType(pdf/*);
                startActivityForResult(in,1);
            }
        });

        //**** select all info about  current user from database
        TextView txt_pname=v.findViewById(R.id.txt_pname);
        TextView txt_pname2=v.findViewById(R.id.txt_pname2);
        TextView txt_pemail=v.findViewById(R.id.txt_pemail);
        TextView txt_useremail=v.findViewById(R.id.txt_useremail);
        TextView txt_userpassword=v.findViewById(R.id.et_userpassword);
        TextView txt_pabout=v.findViewById(R.id.txt_pabout);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("user").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

               txt_pname.setText(snapshot.child("name").getValue(String.class));
               txt_pname2.setText(snapshot.child("name").getValue(String.class));
                txt_pemail.setText(snapshot.child("email").getValue(String.class));
                txt_useremail.setText(snapshot.child("email").getValue(String.class));
               txt_userpassword.setText(snapshot.child("password").getValue(String.class));
               //***********when their  the profile fragment is crash then we change a .toString() to show a this command
                //***********txt_pname.setText(snapshot.child("name").getValue(String.class));
                Picasso.get()
                        .load(snapshot.child("pic").getValue(String.class))
                        .placeholder(R.drawable.baseline_person_24)
                        .error(R.drawable.baseline_person_outline_24)
                        .into(img_profile);
                txt_pabout.setText(snapshot.child("about").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Button btn_logout1 = v.findViewById(R.id.btn_logout1);
        btn_logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent in = new Intent(getContext(), Login.class);
                startActivity(in);

            }
        });
        //change about of user on click about pencile
        ImageView img_changeabout=v.findViewById(R.id.img_changeabout);
        img_changeabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                alert.setTitle("About");
                alert.setTitle("Tell me your Current Feeling.....");
                EditText input=new EditText(getContext());
                alert.setView(input);
                alert.setPositiveButton("change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       HashMap<String,Object> data=new HashMap<>();
                       data.put("about",input.getText().toString());
                       FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(data);
                       txt_pabout.setText(input.getText());
                       dialog.dismiss();
                    }
                });
                alert.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
        //back button click event
        ImageView img_back=v.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return v;
    }
    //***********to set a data of profile*******************
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            //****************set the image data on firebase******************
            img_profile.setImageURI(data.getData());
            FirebaseStorage.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid()).putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override

                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            HashMap<String,Object> data=new HashMap<>();
                            data.put("pic",uri.toString());
                            FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(data);
                            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}
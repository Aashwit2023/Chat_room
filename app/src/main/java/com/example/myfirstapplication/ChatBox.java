package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class ChatBox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        //let's set the Adapter into viewpager--------------------
        ViewPager2 views=findViewById(R.id.viewpager1);

        ChatFragmentAdapter adapter=new ChatFragmentAdapter(getSupportFragmentManager(),getLifecycle());
        views.setAdapter(adapter);
          //let's set position of tabs according to positions of viewpager
      TabLayout tabs = findViewById(R.id.tabs);

      new TabLayoutMediator(tabs, views, new TabLayoutMediator.TabConfigurationStrategy() {
          @Override
          public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
              if (position==0) {
                  tab.setText("CHAT");
              }
              else if (position==1) {
                  tab.setText("STATUS");
              }
              else {
                  tab.setText("PROFILE");
              }

          }
   }).attach();
//      tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//          @Override
//          public void onTabSelected(TabLayout.Tab tab) {
//              views.setCurrentItem(tab.getPosition());
//
//          }
//
//          @Override
//          public void onTabUnselected(TabLayout.Tab tab) {
//
//          }
//
//          @Override
//          public void onTabReselected(TabLayout.Tab tab) {
//
//          }
//
//      });
        // Change position of tabs on change viewpager pages on tab is color orange
    }

    protected  void onStart(){
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Toast.makeText(this, "Please Login First", Toast.LENGTH_LONG).show();
            Intent in =new Intent(this, Login.class);
            startActivity(in);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Event App is Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "The App is Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "The App is Stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "App is Destroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    //    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//    }
}
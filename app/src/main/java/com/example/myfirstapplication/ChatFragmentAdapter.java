package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myfirstapplication.ChatFragment;
import com.example.myfirstapplication.ProfileFragment;
import com.example.myfirstapplication.StatusFragment;

import java.util.ArrayList;

public class ChatFragmentAdapter extends FragmentStateAdapter {
    public ChatFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0)
            return new ChatFragment();
        else if (position==1)
            return new StatusFragment();
        else
            return new ProfileFragment();
    }

        @Override
        public int getItemCount () {
            return 3;
        }
    }

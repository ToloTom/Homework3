package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView titleImage;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        titleImage = findViewById(R.id.imageView_logo);
        tabLayout = findViewById(R.id.tabLayout_main);
        Picasso.get().load("file:///android_asset/Rick_and_Morty_logo.png").into(titleImage);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == 0){
                    loadFragment(new CharacterFragment(), R.id.fragmentContainerView_Main);
                }
                else if(tab.getPosition() == 1){
                    loadFragment(new LocationFragment(), R.id.fragmentContainerView_Main);
                }
                else if(tab.getPosition() == 2){
                    loadFragment(new EpisodeFragment(), R.id.fragmentContainerView_Main);
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                if(tab.getPosition() == 0){
                    loadFragment(new CharacterFragment(), R.id.fragmentContainerView_Main);
                }
                else if(tab.getPosition() == 1){
                    loadFragment(new LocationFragment(), R.id.fragmentContainerView_Main);
                }
                else if(tab.getPosition() == 2){
                    loadFragment(new EpisodeFragment(), R.id.fragmentContainerView_Main);
                }
            }
        });
    }

    public void loadFragment(Fragment fragment, int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "title";
            String description = "My notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
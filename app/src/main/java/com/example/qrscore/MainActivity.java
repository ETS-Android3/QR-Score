package com.example.qrscore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavView;
    HomeFragment homeFragment = new HomeFragment();
    MapFragment mapFragment = new MapFragment();
    ScanFragment scanFragment = new ScanFragment();
    LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bottom Nav selector.
        // https://www.youtube.com/watch?v=OV25x3a55pk
        bottomNavView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, homeFragment).commit();
        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_fragment_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, homeFragment).commit();
                        return true;
                    case R.id.map_fragment_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, mapFragment).commit();
                        return true;
                    case R.id.scan_fragment_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, scanFragment).commit();
                        return true;
                    case R.id.leaderboard_fragment_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, leaderboardFragment).commit();
                        return true;
                    case R.id.profile_fragment_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, profileFragment).commit();
                        return true;
                }
                return false;
            }

        });
    }
}
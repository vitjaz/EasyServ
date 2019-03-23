package easyserv.aapp.customserv.com.myapplication;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.jaeger.library.StatusBarUtil;
import com.kelin.translucentbar.library.TranslucentBarManager;

import easyserv.aapp.customserv.com.myapplication.Fragment.FavoritesFragment;
import easyserv.aapp.customserv.com.myapplication.Fragment.HomeFragment;
import easyserv.aapp.customserv.com.myapplication.Fragment.InfoFragment;
import easyserv.aapp.customserv.com.myapplication.Fragment.ProfileFragment;
import easyserv.aapp.customserv.com.myapplication.Fragment.SearchFragment;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //StatusBarCompat.translucentStatusBar(this);

        StatusBarUtil.setTransparent(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setLabelVisibilityMode(View.VISIBLE);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.nav_profile:
                    SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor.apply();
                    selectedFragment = new ProfileFragment();
                    break;
                case R.id.nav_liked:
                    selectedFragment = new FavoritesFragment();
                    break;
                case R.id.nav_info:
                    selectedFragment = new InfoFragment();
                    break;
            }

            if(selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        }
    };
}

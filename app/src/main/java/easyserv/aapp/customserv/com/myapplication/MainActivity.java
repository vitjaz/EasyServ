package easyserv.aapp.customserv.com.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //StatusBarCompat.translucentStatusBar(this);

        //StatusBarUtil.setTransparent(this);

        progressBar = findViewById(R.id.progressBar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setLabelVisibilityMode(View.VISIBLE);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        ConnectivityManager mgr = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }else {
                progressBar.setVisibility(ProgressBar.VISIBLE);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Нет соединения с интернетом")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();


            }
        } else {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Нет соединения с интернетом")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();


        }

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

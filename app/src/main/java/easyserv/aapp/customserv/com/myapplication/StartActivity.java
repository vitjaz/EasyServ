package easyserv.aapp.customserv.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import mehdi.sakout.fancybuttons.FancyButton;

public class StartActivity extends AppCompatActivity {

    private FancyButton login, register;
    private FirebaseUser firebaseUser;
    private TextView tvWhy;

    @Override
    protected void onStart() {
        super.onStart();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        tvWhy = findViewById(R.id.tv_why);

        tvWhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new LovelyCustomDialog(StartActivity.this)
                        .setView(R.layout.content_start_info_dialog_layout)
                        .setTopColorRes(R.color.colorNewPrimary)
                        .setTitle("Зачем это нужно")
                        .setListener(R.id.ok_button_3, true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setIcon(R.drawable.ic_info_outline)
                        .show();
            }
        });

        login = findViewById(R.id.login_button_start_activity);
        register = findViewById(R.id.register_button_start_activity);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

package easyserv.aapp.customserv.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import easyserv.aapp.customserv.com.myapplication.Fragment.ProfileFragment;
import easyserv.aapp.customserv.com.myapplication.Model.User;
import mehdi.sakout.fancybuttons.FancyButton;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class RegisterActivity extends AppCompatActivity {

    private ExtendedEditText username, fullname, email, password, phoneNumber;
    private FancyButton register;
    private TextView txt_login;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //editText
        username = findViewById(R.id.username_registration);
        fullname = findViewById(R.id.full_name_registration);
        phoneNumber = findViewById(R.id.phoneNumber);
        phoneNumber.setText("+7");
        phoneNumber.setSelection(2); /// ставим курсор после +7
        email = findViewById(R.id.email_registration);
        password = findViewById(R.id.password_registration);

        //Button
        register = findViewById(R.id.register_new_user_button);

        //TextView
        txt_login = findViewById(R.id.text_login);

        auth = FirebaseAuth.getInstance();

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final String str_username = username.getText().toString();
                final String str_fullname = fullname.getText().toString();
                final String str_email = email.getText().toString();
                final String str_password = password.getText().toString();
                final String str_phone = phoneNumber.getText().toString();




                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                ref.orderByChild("phoneNumber").equalTo(phoneNumber.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getValue() != null) {
                            Toast.makeText(RegisterActivity.this, "Номер уже зарегистрирован!", Toast.LENGTH_SHORT).show();
                        } else {

                            if(TextUtils.isEmpty(str_username))
                                username.setError("Нужно заполнить!");
                            else if(TextUtils.isEmpty(str_fullname))
                                fullname.setError("Нужно заполнить!");
                            else if(TextUtils.isEmpty(str_email))
                                email.setError("Нужно заполнить!");
                            else if(str_phone.isEmpty())
                                phoneNumber.setError("Введите телефон!");
                            else if(TextUtils.isEmpty(str_password))
                                password.setError("Нужно заполнить!");
                            else if(str_password.length() < 6 || str_password.length() > 15)
                                password.setError("Неверное количество символов!");
                             else
                                register(str_username, str_fullname, str_email, str_password, str_phone);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

    private void register(final String username, final String fullname, String email, final String password, final String phoneNumber){

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Минуточку...");
        try {
            progressDialog.show();
        }
        catch (WindowManager.BadTokenException e) {
            //use a log message
        }


        Intent intent = new Intent(RegisterActivity.this, PhoneVerification.class);
        intent.putExtra("phone", phoneNumber);
        intent.putExtra("name", username);
        intent.putExtra("email", email);
        intent.putExtra("fullname", fullname);
        intent.putExtra("bio", "");
        intent.putExtra("imageURL", "https://firebasestorage.googleapis.com/v0/b/easyserv-b00ef.appspot.com/o/uploads%2F1560340813628.null?alt=media&token=c151bc77-ce89-4bf2-9fa6-892a8580307d");
        intent.putExtra("password", password);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        progressDialog.dismiss();

    }

}

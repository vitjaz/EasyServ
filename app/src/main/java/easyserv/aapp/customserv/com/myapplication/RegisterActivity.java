package easyserv.aapp.customserv.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import easyserv.aapp.customserv.com.myapplication.Fragment.ProfileFragment;
import mehdi.sakout.fancybuttons.FancyButton;
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

                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_phone = phoneNumber.getText().toString();

                if(TextUtils.isEmpty(str_username))
                    username.setError("Нужно заполнить!");
                else if(TextUtils.isEmpty(str_fullname))
                    fullname.setError("Нужно заполнить!");
                else if(TextUtils.isEmpty(str_email))
                    email.setError("Нужно заполнить!");
                else if(TextUtils.isEmpty(str_password))
                    password.setError("Нужно заполнить!");
                else if(str_password.length() < 6 || str_password.length() > 15)
                    password.setError("Неверное количество символов!");
                else register(str_username, str_fullname, str_email, str_password, str_phone);
            }
        });
    }

    private void register(final String username, final String fullname, String email, final String password, final String phoneNumber){

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Минуточку...");
        progressDialog.show();



        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


                            databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(firebaseUser.getUid());

<<<<<<< HEAD
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
=======
>>>>>>> branch_2

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", firebaseUser.getUid());
                            hashMap.put("username", username);
                            hashMap.put("fullname", fullname);
                            hashMap.put("bio", "");
                            hashMap.put("imageURL", "https://firebasestorage.googleapis.com/v0/b/easyserv-b00ef.appspot.com/o/gYu3KiI7cN4.jpg?alt=media&token=66483395-78f1-4163-bfca-897b31287005");
                            hashMap.put("password", password);
                            hashMap.put("phoneNumber", phoneNumber);

                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this, PhoneVerification.class);
                                        intent.putExtra("phone", phoneNumber);
                                        intent.putExtra("id", firebaseUser.getUid());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Не получилось зарегистрироваться!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}

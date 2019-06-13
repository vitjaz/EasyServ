package easyserv.aapp.customserv.com.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import easyserv.aapp.customserv.com.myapplication.Fragment.HomeFragment;
import easyserv.aapp.customserv.com.myapplication.Fragment.ProfileFragment;
import easyserv.aapp.customserv.com.myapplication.Model.User;


public class PhoneVerification extends AppCompatActivity {

    EditText editTextCode;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    String codeSent;
    String phoneNumber;
    FirebaseAuth firebaseAuth;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = getIntent();
        Toast.makeText(this, "Name: " + i.getStringExtra("name"), Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_phone_verification);
        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        phoneNumber = i.getStringExtra("phone");
        editTextCode = findViewById(R.id.editTextCode);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks




        findViewById(R.id.ButtonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });
    }



    private void verifySignInCode() {

        String code = editTextCode.getText().toString();

        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
            toast.show();
        }





    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseAuth.createUserWithEmailAndPassword(i.getStringExtra("email"), i.getStringExtra("password"))
                                    .addOnCompleteListener(PhoneVerification.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();




                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());


                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("id", firebaseUser.getUid());
                                                hashMap.put("username", i.getStringExtra("name"));
                                                hashMap.put("fullname", i.getStringExtra("fullname"));
                                                hashMap.put("bio", "");
                                                hashMap.put("imageURL", i.getStringExtra("imageURL"));
                                                hashMap.put("password", i.getStringExtra("password"));
                                                hashMap.put("phoneNumber", i.getStringExtra("phone"));



                                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){

                                                            firebaseAuth.signInWithEmailAndPassword(i.getStringExtra("email"), i.getStringExtra("password"))
                                                                    .addOnCompleteListener(PhoneVerification.this, new OnCompleteListener<AuthResult>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                            if(task.isSuccessful()) {
                                                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                                                        .child(firebaseAuth.getCurrentUser().getUid());

                                                                                databaseReference.addValueEventListener(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                        Toast.makeText(PhoneVerification.this, "Верификация прошла успешно! Пожалуйста авторизируйтесь", Toast.LENGTH_SHORT).show();
                                                                                        Intent i = new Intent(PhoneVerification.this, MainActivity.class);
                                                                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                        startActivity(i);
                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                    }
                                                                                });
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                            } else{

                                                Toast.makeText(PhoneVerification.this, "Не получилось зарегистрироваться!", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });




                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(PhoneVerification.this, "Что-то пошло не так...", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };
}

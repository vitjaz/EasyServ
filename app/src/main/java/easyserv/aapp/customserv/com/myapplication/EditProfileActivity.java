package easyserv.aapp.customserv.com.myapplication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import easyserv.aapp.customserv.com.myapplication.Model.User;
import es.dmoral.toasty.Toasty;
import qiu.niorgai.StatusBarCompat;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class EditProfileActivity extends AppCompatActivity {


    private ImageView close, save;
    private CircleImageView profile_image;
    private TextView change_photo, tv_email;
    private ExtendedEditText username, fullname;
    private FirebaseUser firebaseUser;

    private Uri mImageUri;
    private StorageTask uploadTask;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        StatusBarCompat.translucentStatusBar(this);

        close = findViewById(R.id.close_edit_profile);
        save = findViewById(R.id.save_edit_profile);
        profile_image = findViewById(R.id.image_profile_edit);
        change_photo = findViewById(R.id.tv_change_photo);
        username = findViewById(R.id.et_username_edit);
        fullname = findViewById(R.id.et_fullname_edit);
        tv_email = findViewById(R.id.tv_email_edit_activity);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullname.setText(user.getFullname());
                username.setText(user.getUsername());
                Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                tv_email.setText(firebaseUser.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TapTargetView.showFor(this,
                TapTarget.forView(findViewById(R.id.save_edit_profile), "Кнопка сохранить", "Позволит вам сохранить изменения в профиле")
        .targetRadius(40));

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(EditProfileActivity.this);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(fullname.getText().toString(), username.getText().toString());
            }

        });
    }

    private void updateProfile(String fullname, String username) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fullname", fullname);
        hashMap.put("username", username);

        reference.updateChildren(hashMap);

        Toasty.success(this, "Информация обновлена", Toast.LENGTH_SHORT).show();

    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImg(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Загружаем новое фото");
        pd.show();



        if(mImageUri != null){
            final StorageReference filereference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            uploadTask = filereference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageURL", ""+myUrl);

                        databaseReference.updateChildren(hashMap);
                        pd.dismiss();

                        Toasty.success(EditProfileActivity.this, "Фото обновлено!", Toast.LENGTH_SHORT, true).show();
                    } else{
                        Toasty.error(EditProfileActivity.this, "Что-то не так", Toast.LENGTH_SHORT, true).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(EditProfileActivity.this, "Не выбрали новую картинку", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
                mImageUri = result.getUri();
                uploadImg();


        }else {
            Toasty.error(EditProfileActivity.this, "Что-то пошло не так", Toast.LENGTH_SHORT, true).show();
        }
    }
}

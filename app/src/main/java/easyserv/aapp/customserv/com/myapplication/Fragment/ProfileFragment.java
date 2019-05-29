package easyserv.aapp.customserv.com.myapplication.Fragment;


import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;


import de.hdodenhof.circleimageview.CircleImageView;
import easyserv.aapp.customserv.com.myapplication.EditProfileActivity;

import easyserv.aapp.customserv.com.myapplication.Model.User;
import easyserv.aapp.customserv.com.myapplication.R;

import easyserv.aapp.customserv.com.myapplication.StartActivity;
import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;


public class ProfileFragment extends Fragment {

    private FancyButton logoutButton;
    private TextView username, spendBonusInfo, getBonusInfo, phoneText;
    private FancyButton clickOK_1, clickOK_2;
    private CircleImageView profileImage;
    private ImageView editProfileImage;

    public FirebaseAuth auth;
    public FirebaseUser firebaseUser;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutButton = view.findViewById(R.id.logout_button);
        username = view.findViewById(R.id.text_view_profile_username);
        profileImage = view.findViewById(R.id.profile_image);
        phoneText = view.findViewById(R.id.phoneNumberId);
        editProfileImage = view.findViewById(R.id.edit_profile_image);
        spendBonusInfo = view.findViewById(R.id.tv_spend_bonus);
        getBonusInfo = view.findViewById(R.id.tv_get_bonus);
        clickOK_1 = view.findViewById(R.id.ok_button_1);
        clickOK_2 = view.findViewById(R.id.ok_button_2);

//        TranslucentBarManager translucentBarManager = new TranslucentBarManager(this);
//        translucentBarManager.translucent(this, view, android.R.color.holo_orange_dark);

        auth = FirebaseAuth.getInstance();


        assert getArguments() != null;



//        Toast.makeText(getContext(), "user: " + firebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null)
        {
            Toasty.info(getContext(), "Мы авторизованы", Toast.LENGTH_SHORT, true).show();
        }
        else
        {
            Toast.makeText(getContext(), "Мы не авторизованы", Toast.LENGTH_SHORT).show();
        }


        TapTargetView.showFor(getActivity(),
                TapTarget.forView(view.findViewById(R.id.edit_profile_image), "Иконка редактирования", "Позволит вам отредактировать профиль"));



        TapTargetView.showFor(getActivity(),
                TapTarget.forView(view.findViewById(R.id.edit_profile_image), "Иконка редактирования", "Позволит вам отредактировать профиль"));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        //загрузка даты юзера
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                phoneText.setText(user.getPhoneNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //обработчик кнопки редактирования профиля
        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(i);
            }
        });


        //обработчик кнопки выхода
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new LovelyCustomDialog(getContext())
                        .setView(R.layout.content_dialog_out_layout)
                        .setTopColorRes(R.color.colorNewPrimary)
                        .setTitle("Вы хотите выйти?")
                        .setListener(R.id.exit_button, true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                auth.signOut();
                                Intent i = new Intent(getActivity(), StartActivity.class);
                                startActivity(i);
                            }
                        })
                        .setListener(R.id.not_exit_button, true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //просто закрываем диалог
                            }
                        })
                        .setIcon(R.drawable.ic_info_outline)
                        .show();



            }
        });


        //диалог с инфой о том как потратить бонусы
        spendBonusInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LovelyCustomDialog(getContext())
                        .setView(R.layout.content_spend_dialog_layout)
                        .setTopColorRes(R.color.colorNewPrimary)
                        .setTitle("Потратить бонусы")
                        .setListener(R.id.ok_button_1, true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //просто закрываем диалог
                            }
                        })
                        .setIcon(R.drawable.ic_info_outline)
                        .show();
            }
        });

        //диалог с инфой о том как получить бонусы
        getBonusInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LovelyCustomDialog(getContext())
                        .setView(R.layout.content_dialog_layout)
                        .setTopColorRes(R.color.colorNewPrimary)
                        .setTitle("Получить бонусы")
                        .setListener(R.id.ok_button_2, true, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //просто закрываем диалог
                            }
                        })
                        .setIcon(R.drawable.ic_info_outline)
                        .show();

            }
        });



        return view;
    }
}






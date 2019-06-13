package easyserv.aapp.customserv.com.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.swipeselector.OnSwipeItemSelectedListener;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

import java.util.Calendar;

import easyserv.aapp.customserv.com.myapplication.Fragment.DatePickerFragment;
import easyserv.aapp.customserv.com.myapplication.Fragment.TimePickerFragment;
import easyserv.aapp.customserv.com.myapplication.Model.GMailSender;
import easyserv.aapp.customserv.com.myapplication.Model.SimpleMail;
import easyserv.aapp.customserv.com.myapplication.Model.User;
import mehdi.sakout.fancybuttons.FancyButton;
import qiu.niorgai.StatusBarCompat;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;

public class BookingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private SwipeSelector swipeSelector;

    private TextView nameInfo, personInfo, orderInfo, dateInfo, timeInfo, person, nameOf;
    private FancyButton timeButton, dateButton, bookingButton;
    private ExtendedEditText nameOfEt;

    private FirebaseUser user;
    private DatabaseReference ref;

    Intent i;
    String passwordOfUser, currentDate, time, personOf = "1", fullnameOfUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        StatusBarCompat.translucentStatusBar(this);

        nameInfo = findViewById(R.id.tv_name_booking);
        personInfo = findViewById(R.id.tv_max_person_booking);
        orderInfo = findViewById(R.id.tv_order_booking);
        dateInfo = findViewById(R.id.tv_date_booking);
        timeInfo = findViewById(R.id.tv_time_booking);
        person = findViewById(R.id.tv_person_booking);      //в данном конетксте имеется ввиду то кол-во гостей, которое выбрал пользователь
        nameOf = findViewById(R.id.tv_nameOf_booking);      //имя бронирующего

        timeButton = findViewById(R.id.time_button);
        dateButton = findViewById(R.id.date_button);
        bookingButton = findViewById(R.id.booking_button_email);

//        nameOfEt = findViewById(R.id.nameOf_booking_et);  //ввод имени бронирующего, если это понадобится

        swipeSelector =  findViewById(R.id.swipePerson);

        user = FirebaseAuth.getInstance().getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                nameOf.setText(user.getFullname());
                passwordOfUser = user.getPassword();
                fullnameOfUser = user.getFullname();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        i = getIntent();

        nameInfo.setText(i.getStringExtra("name"));
        personInfo.setText(i.getStringExtra("max_person"));
        orderInfo.setText(i.getStringExtra("order"));


//        nameOfEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                nameOf.setText(nameOfEt.getText().toString());
//            }
//        });



        swipeSelector.setItems(
                new SwipeItem(0, "Одна персона", "Одинокий волк? А может, и волчица?"),
                new SwipeItem(1, "Две персоны", "Милая парочка? Мммм"),
                new SwipeItem(2, "Три персоны", "Возможно, уютная семья?"),
                new SwipeItem(3,"Четыре персоны", "Дружная компания?"),
                new SwipeItem(4, "Больше персон", "Неужели нашли денег на корпоратив?")
        );

        swipeSelector.setOnItemSelectedListener(new OnSwipeItemSelectedListener() {
            @Override
            public void onItemSelected(SwipeItem item) {
                switch ((Integer) item.value) {
                    case 0: person.setText("1");
                    personOf = "1";
                    break;
                    case 1: person.setText("2");
                    personOf = "2";
                    break;
                    case 2: person.setText("3");
                    personOf = "3";
                    break;
                    case 3: person.setText("4");
                    personOf = "4";
                    break;
                    case 4: person.setText("Больше 4");
                    personOf = "Больше 4";
                    break;
                }
            }
        });



        //выбор времени
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //выбор даты
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //дефолтный метод
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("message/rfc822");
//                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
//                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
//                try {
//                    startActivity(Intent.createChooser(i, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(BookingActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }

                try{
//                    GMailSender sender = new GMailSender("jameshudsonrandall@gmail.com", "vitalexeev2606q");
//                    sender.sendMail("Бронь в " + i.getStringExtra("name"),
//                            "This is Body",
//                            user.getEmail(),
//                            "jameshudsonrandall@gmail.com");

                    SimpleMail.sendEmail("develop@customserv.ru",
                              "Бронь в " + i.getStringExtra("name"),
                                "Бронь на имя: " + fullnameOfUser + "<br>" +
                                        "Дата брони: " + currentDate + "<br>" +
                                        "Время брони: " + time + "<br>" +
                                        "Количество персон: " + personOf + "<br>" +
                                        "Телефон бронирующего: " + user.getPhoneNumber() + "<br>" +
                                        "Email бронирующего: " + user.getEmail());

                    new AlertDialog.Builder(BookingActivity.this)
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage("Запрос на бронь отправлен")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                }
                catch (Exception e){
                    Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time = String.valueOf(hourOfDay)+":"+String.valueOf(minute);
            timeInfo.setText(time);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = String.valueOf(year)+":"+String.valueOf(month)+":"+String.valueOf(dayOfMonth);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            currentDate = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(c.getTime());
            dateInfo.setText(currentDate);
    }
}

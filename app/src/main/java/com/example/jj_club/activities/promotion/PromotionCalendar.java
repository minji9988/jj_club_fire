package com.example.jj_club.activities.promotion;
import com.example.jj_club.R;
import com.example.jj_club.models.CalendarItem;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PromotionCalendar extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // 파이어베이스 데이터베이스 객체 생성
    private ArrayList<CalendarItem> calendarItems = new ArrayList<>(); // CalendarItem 객체를 저장할 ArrayList 생성

    // 캘린더를 클릭하면 DatePickerDialog가 실행되는 이벤트를 처리하는 리스너
    View.OnClickListener calendarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(PromotionCalendar.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String date = dayOfMonth + "-" + (month + 1) + "-" + year;

                            // 해당 날짜에 대한 일정을 입력받는 함수 호출
                            inputEvent(date);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_calendar);

        // 파이어베이스에서 일정을 불러오는 함수 호출
        loadEvents();
    }

    private void loadEvents() {
        db.collection("calendarItems")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CalendarItem item = document.toObject(CalendarItem.class);
                            calendarItems.add(item);
                            // 캘린더에 일정을 표시하는 함수 호출
                            displayEventOnCalendar(item);
                        }
                    } else {
                        Toast.makeText(this, "일정을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayEventOnCalendar(CalendarItem item) {
        // 캘린더에 일정을 표시하는 코드를 여기에 작성해야 합니다.
    }

    private void inputEvent(String date) {
        // 해당 날짜에 대한 일정을 입력받는 코드를 여기에 작성해야 합니다.
    }

    private void saveEventToFirebase(CalendarItem item) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("date", item.getDate());
        docData.put("event", item.getEvent());

        db.collection("calendarItems").add(docData)
                .addOnSuccessListener(documentReference -> Toast.makeText(this, "일정이 성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "일정 저장에 실패했습니다.", Toast.LENGTH_SHORT).show());
    }
}

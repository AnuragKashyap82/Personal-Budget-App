package kashyap.anurag.personalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.Adapters.AdapterToday;
import kashyap.anurag.personalcare.Model.Data;
import kashyap.anurag.personalcare.databinding.ActivityHistoryBinding;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActivityHistoryBinding binding;
    private FirebaseAuth firebaseAuth;

    private AdapterToday adapterToday;
    private ArrayList<Data> dataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryActivity.this, MainActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        dataArrayList = new ArrayList<>();
        adapterToday = new AdapterToday(HistoryActivity.this, dataArrayList);
        binding.historyRv.setAdapter(adapterToday);

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONDAY),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        int months = month + 1;
        String m = String.valueOf(months);
        String d = String.valueOf(dayOfMonth);
        String y = String.valueOf(year);

        if(Integer.parseInt(String.valueOf(m)) < 10){
            m = "0" + m;
        }if(Integer.parseInt(String.valueOf(d)) < 10){
            d  = "0" + d ;
        }

        String date = d + "-" + m + "-" + y;
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();

        binding.searchBtn.setText(date);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Expenses").child(firebaseAuth.getUid());
        Query query = reference.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data data = dataSnapshot.getValue(Data.class);
                    dataArrayList.add(data);
                }
                adapterToday.notifyDataSetChanged();
                binding.historyRv.setVisibility(View.VISIBLE);

                int totalAmount = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object total = map.get("amount");
                    int pTotal = Integer.parseInt(String.valueOf(total));
                    totalAmount += pTotal;
                    if (totalAmount > 0) {
                        binding.historyTotalAmountSpent.setVisibility(View.VISIBLE);
                        binding.historyTotalAmountSpent.setText("Rs." + totalAmount);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
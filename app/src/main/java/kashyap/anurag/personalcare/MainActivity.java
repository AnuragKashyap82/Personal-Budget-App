package kashyap.anurag.personalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.databinding.ActivityMainBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private int totalAmountBudget = 0;
    private int totalAmountMonth = 0;
    private int totalAmountWeekly = 0;
    private int totalAmountDaily = 0;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        loadBudget();
        loadTodaySpending();
        loadWeeklySpending();
        loadMonthlySpending();

        binding.budgetCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BudgetActivity.class));
            }
        });
        binding.todayCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TodayActivity.class));
            }
        });
        binding.weekCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WeeklyActivity.class));
            }
        });
        binding.monthCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MonthlyActivity.class));
            }
        });
        binding.analyticsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChooseAnalyticActivity.class));
            }
        });
        binding.historyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
    }

    private void loadBudget() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount()> 0){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmountBudget += pTotal;
                    }
                    binding.budgetTv.setText("Rs."+totalAmountBudget);
                    updateBudgetInDataBase(totalAmountBudget);
                }else {
                    binding.budgetTv.setText("Rs.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateBudgetInDataBase(int totalAmountBudget) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("budget", totalAmountBudget);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
        databaseReference.updateChildren(hashMap);

    }

    private void loadTodaySpending() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount()> 0){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmountDaily += pTotal;
                    }
                    binding.todayTv.setText("Rs."+totalAmountDaily);
                }else {
                    binding.todayTv.setText("Rs.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadWeeklySpending() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());

        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        Months months = Months.monthsBetween(epoch, now);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("week").equalTo(weeks.getWeeks());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount()> 0){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmountWeekly += pTotal;
                    }
                    binding.weekTv.setText("Rs."+totalAmountWeekly);
                }else {
                    binding.weekTv.setText("Rs.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadMonthlySpending() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());

        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        Months months = Months.monthsBetween(epoch, now);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("month").equalTo(months.getMonths());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount()> 0){
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmountMonth += pTotal;
                        updateExpenditureInDataBase(totalAmountMonth);
                    }
                    binding.monthTv.setText("Rs."+totalAmountMonth);

                }else {
                    binding.monthTv.setText("Rs.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateExpenditureInDataBase(int totalAmountMonth) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("expenditure", totalAmountMonth);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
        databaseReference.updateChildren(hashMap);
        loadSavings();
    }

    private void loadSavings() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int budget;
                    if (snapshot.hasChild("budget")){
                        budget = Integer.parseInt(snapshot.child("budget").getValue().toString());
                    }else {
                        budget = 0;
                    }
                    int expenditure;
                    if (snapshot.hasChild("expenditure")){
                        expenditure = Integer.parseInt(snapshot.child("expenditure").getValue().toString());
                    }else {
                        expenditure = 0;
                    }

                    int savings = budget - expenditure;
                    binding.savingsTv.setText("Rs."+savings);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
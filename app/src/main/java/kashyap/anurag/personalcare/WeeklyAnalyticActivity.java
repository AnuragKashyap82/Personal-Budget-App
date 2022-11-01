package kashyap.anurag.personalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.databinding.ActivityWeeklyAnalyticBinding;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class WeeklyAnalyticActivity extends AppCompatActivity {
    private ActivityWeeklyAnalyticBinding binding;
    private FirebaseAuth firebaseAuth;
    int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeeklyAnalyticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        getTotalDailyEducationExpenditure();
        getTotalDailyTransportExpenditure();
        getTotalDailyFoodExpenditure();
        getTotalDailyHouseExpenditure();
        getTotalDailyEntertainmentExpenditure();
        getTotalDailyCharityExpenditure();
        getTotalDailyApparelExpenditure();
        getTotalDailyHealthExpenditure();
        getTotalDailyPersonalExpenditure();
        getTotalDailyOthersExpenditure();
        getTotalWeekExpenditure();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadGraph();
                setStatusAndImageResource();
            }
        }, 4000);
    }

    private void getTotalDailyEducationExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Education" + weeks.getWeeks();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsEducationAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekEdu").setValue(totalAmount);
                } else {
                    binding.linearLayoutEducation.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekEdu").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyTransportExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Transport" + weeks.getWeeks();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsTransportAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekTrans").setValue(totalAmount);
                } else {
                    binding.linearLayoutTransport.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekTrans").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyFoodExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Food" + weeks.getWeeks();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsFoodAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekFood").setValue(totalAmount);
                } else {
                    binding.linearLayoutFood.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekFood").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyHouseExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "House" + weeks.getWeeks();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsHouseExpensesAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekHouse").setValue(totalAmount);
                } else {
                    binding.linearLayoutFoodHouse.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekHouse").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyEntertainmentExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Entertainment" + weeks.getWeeks();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsEntertainmentAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekEntertainment").setValue(totalAmount);
                } else {
                    binding.linearLayoutEntertainment.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekEntertainment").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyCharityExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Charity" + weeks.getWeeks();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsCharityAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekCharity").setValue(totalAmount);
                } else {
                    binding.linearLayoutCharity.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekCharity").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyApparelExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Apparel" + weeks.getWeeks();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsApparelAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekApparel").setValue(totalAmount);
                } else {
                    binding.linearLayoutApparel.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekApparel").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyHealthExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Health" + weeks.getWeeks();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsHealthAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekHealth").setValue(totalAmount);
                } else {
                    binding.linearLayoutHealth.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekHealth").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyPersonalExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Personal" + weeks.getWeeks();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsPersonalExpensesAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekPersonal").setValue(totalAmount);
                } else {
                    binding.linearLayoutPersonalExp.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekPersonal").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyOthersExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);
        String itemWeek = "Others" + weeks.getWeeks();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemWeek").equalTo(itemWeek);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsOtherAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekOthers").setValue(totalAmount);
                } else {
                    binding.linearLayoutOther.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("weekOthers").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalWeekExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("week").equalTo(weeks.getWeeks());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.totalBudgetAmountTextView.setText("Rs." + totalAmount);
                        binding.monthSpentAmount.setText("Total Expenditure: Rs." + totalAmount);
                    }
                } else {
                    binding.totalBudgetAmountTextView.setText("Rs.0");
                    binding.anyChartView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WeeklyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGraph() {
        Toast.makeText(this, "Graph", Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    int transportTotal = 0;
                    if (snapshot.hasChild("weekTrans")) {
                        transportTotal = Integer.parseInt(snapshot.child("weekTrans").getValue().toString());
                    } else {
                        transportTotal = 0;
                    }

                    int weekEdu = 0;
                    if (snapshot.hasChild("weekEdu")) {
                        weekEdu = Integer.parseInt(snapshot.child("weekEdu").getValue().toString());
                    } else {
                        weekEdu = 0;
                    }

                    int weekFood = 0;
                    if (snapshot.hasChild("weekFood")) {
                        weekFood = Integer.parseInt(snapshot.child("weekFood").getValue().toString());
                    } else {
                        weekFood = 0;
                    }

                    int weekHouse = 0;
                    if (snapshot.hasChild("weekHouse")) {
                        weekHouse = Integer.parseInt(snapshot.child("weekHouse").getValue().toString());
                    } else {
                        weekHouse = 0;
                    }

                    int weekEntertainment = 0;
                    if (snapshot.hasChild("weekEntertainment")) {
                        weekEntertainment = Integer.parseInt(snapshot.child("weekEntertainment").getValue().toString());
                    } else {
                        weekEntertainment = 0;
                    }

                    int weekCharity = 0;
                    if (snapshot.hasChild("weekCharity")) {
                        weekCharity = Integer.parseInt(snapshot.child("weekCharity").getValue().toString());
                    } else {
                        weekCharity = 0;
                    }

                    int weekApparel = 0;
                    if (snapshot.hasChild("weekApparel")) {
                        weekApparel = Integer.parseInt(snapshot.child("weekApparel").getValue().toString());
                    } else {
                        weekApparel = 0;
                    }

                    int weekHealth = 0;
                    if (snapshot.hasChild("weekHealth")) {
                        weekHealth = Integer.parseInt(snapshot.child("weekHealth").getValue().toString());
                    } else {
                        weekHealth = 0;
                    }

                    int weekPersonal = 0;
                    if (snapshot.hasChild("weekPersonal")) {
                        weekPersonal = Integer.parseInt(snapshot.child("weekPersonal").getValue().toString());
                    } else {
                        weekPersonal = 0;
                    }

                    int weekOthers = 0;
                    if (snapshot.hasChild("weekOthers")) {
                        weekOthers = Integer.parseInt(snapshot.child("weekOthers").getValue().toString());
                    } else {
                        weekOthers = 0;
                    }

                    Pie pie = AnyChart.pie();
                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Transport", transportTotal));
                    data.add(new ValueDataEntry("Food", weekFood));
                    data.add(new ValueDataEntry("House", weekHouse));
                    data.add(new ValueDataEntry("Entertainment", weekEntertainment));
                    data.add(new ValueDataEntry("Education", weekEdu));
                    data.add(new ValueDataEntry("Charity", weekCharity));
                    data.add(new ValueDataEntry("Apparel", weekApparel));
                    data.add(new ValueDataEntry("Health", weekHealth));
                    data.add(new ValueDataEntry("Personal", weekPersonal));
                    data.add(new ValueDataEntry("Others", weekOthers));

                    pie.data(data);
                    pie.title("Weekly Analytics");
                    pie.labels().position("outside");
                    pie.legend().title()
                            .text(":Items Spent On")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend().position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    binding.anyChartView.setChart(pie);

                } else {
                    Toast.makeText(WeeklyAnalyticActivity.this, "No Expenditure!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setStatusAndImageResource() {
        Toast.makeText(this, "Graph", Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    int transportTotal = 0;
                    if (snapshot.hasChild("weekTrans")) {
                        transportTotal = Integer.parseInt(snapshot.child("weekTrans").getValue().toString());
                    } else {
                        transportTotal = 0;
                    }

                    int weekEdu = 0;
                    if (snapshot.hasChild("weekEdu")) {
                        weekEdu = Integer.parseInt(snapshot.child("weekEdu").getValue().toString());
                    } else {
                        weekEdu = 0;
                    }

                    int weekFood = 0;
                    if (snapshot.hasChild("weekFood")) {
                        weekFood = Integer.parseInt(snapshot.child("weekFood").getValue().toString());
                    } else {
                        weekFood = 0;
                    }

                    int weekHouse = 0;
                    if (snapshot.hasChild("weekHouse")) {
                        weekHouse = Integer.parseInt(snapshot.child("weekHouse").getValue().toString());
                    } else {
                        weekHouse = 0;
                    }

                    int weekEntertainment = 0;
                    if (snapshot.hasChild("weekEntertainment")) {
                        weekEntertainment = Integer.parseInt(snapshot.child("weekEntertainment").getValue().toString());
                    } else {
                        weekEntertainment = 0;
                    }

                    int weekCharity = 0;
                    if (snapshot.hasChild("weekCharity")) {
                        weekCharity = Integer.parseInt(snapshot.child("weekCharity").getValue().toString());
                    } else {
                        weekCharity = 0;
                    }

                    int weekApparel = 0;
                    if (snapshot.hasChild("weekApparel")) {
                        weekApparel = Integer.parseInt(snapshot.child("weekApparel").getValue().toString());
                    } else {
                        weekApparel = 0;
                    }

                    int weekHealth = 0;
                    if (snapshot.hasChild("weekHealth")) {
                        weekHealth = Integer.parseInt(snapshot.child("weekHealth").getValue().toString());
                    } else {
                        weekHealth = 0;
                    }

                    int weekPersonal = 0;
                    if (snapshot.hasChild("weekPersonal")) {
                        weekPersonal = Integer.parseInt(snapshot.child("weekPersonal").getValue().toString());
                    } else {
                        weekPersonal = 0;
                    }

                    int weekOthers = 0;
                    if (snapshot.hasChild("weekOthers")) {
                        weekOthers = Integer.parseInt(snapshot.child("weekOthers").getValue().toString());
                    } else {
                        weekOthers = 0;
                    }



                    float monthTotalSpentAmount;
                    if (snapshot.hasChild("week")) {
                        monthTotalSpentAmount = Integer.parseInt(snapshot.child("week").getValue().toString());
                    } else {
                        monthTotalSpentAmount = 0;
                    }


                    float tranRatio;
                    if (snapshot.hasChild("weekTransportRatio")) {
                        tranRatio = Integer.parseInt(snapshot.child("weekTransportRatio").getValue().toString());
                    } else {
                        tranRatio = 0;
                    }

                    float weekEducationRatio;
                    if (snapshot.hasChild("weekEducationRatio")) {
                        weekEducationRatio = Integer.parseInt(snapshot.child("weekEducationRatio").getValue().toString());
                    } else {
                        weekEducationRatio = 0;
                    }

                    float weekFoodRatio;
                    if (snapshot.hasChild("weekFoodRatio")) {
                        weekFoodRatio = Integer.parseInt(snapshot.child("weekFoodRatio").getValue().toString());
                    } else {
                        weekFoodRatio = 0;
                    }

                    float weekHouseRatio;
                    if (snapshot.hasChild("weekHouseRatio")) {
                        weekHouseRatio = Integer.parseInt(snapshot.child("weekHouseRatio").getValue().toString());
                    } else {
                        weekHouseRatio = 0;
                    }

                    float weekEntertainmentRatio;
                    if (snapshot.hasChild("weekEntertainmentRatio")) {
                        weekEntertainmentRatio = Integer.parseInt(snapshot.child("weekEntertainmentRatio").getValue().toString());
                    } else {
                        weekEntertainmentRatio = 0;
                    }

                    float weekCharityRatio;
                    if (snapshot.hasChild("weekCharityRatio")) {
                        weekCharityRatio = Integer.parseInt(snapshot.child("weekCharityRatio").getValue().toString());
                    } else {
                        weekCharityRatio = 0;
                    }

                    float weekApparelRatio;
                    if (snapshot.hasChild("weekApparelRatio")) {
                        weekApparelRatio = Integer.parseInt(snapshot.child("weekApparelRatio").getValue().toString());
                    } else {
                        weekApparelRatio = 0;
                    }

                    float weekHealthRatio;
                    if (snapshot.hasChild("weekHealthRatio")) {
                        weekHealthRatio = Integer.parseInt(snapshot.child("weekHealthRatio").getValue().toString());
                    } else {
                        weekHealthRatio = 0;
                    }

                    float weekPersonalRatio;
                    if (snapshot.hasChild("weekPersonalRatio")) {
                        weekPersonalRatio = Integer.parseInt(snapshot.child("weekPersonalRatio").getValue().toString());
                    } else {
                        weekPersonalRatio = 0;
                    }

                    float weekOthersRatio;
                    if (snapshot.hasChild("weekOthersRatio")) {
                        weekOthersRatio = Integer.parseInt(snapshot.child("weekOthersRatio").getValue().toString());
                    } else {
                        weekOthersRatio = 0;
                    }


                    float monthTotalSpentAmountRatio;
                    if (snapshot.hasChild("budget")) {
                        monthTotalSpentAmountRatio = Integer.parseInt(snapshot.child("budget").getValue().toString())/4;
                    } else {
                        monthTotalSpentAmountRatio = 0;
                    }

                    float monthPercent = (totalAmount / monthTotalSpentAmountRatio) * 100;
                    if (monthPercent < 50) {
                        binding.monthRatioSpending.setText(monthPercent + "%" + " used of " + monthTotalSpentAmountRatio);
                        binding.monthRatioIv.setImageResource(R.drawable.green);
                    } else if (monthPercent >= 50 && monthPercent < 100) {
                        binding.monthRatioSpending.setText(monthPercent + "%" + " used of " + monthTotalSpentAmountRatio);
                        binding.monthRatioIv.setImageResource(R.drawable.brown);
                    } else {
                        binding.monthRatioSpending.setText(monthPercent + "%" + " used of " + monthTotalSpentAmountRatio);
                        binding.monthRatioIv.setImageResource(R.drawable.red);
                    }


                    float foodPercent = (weekFood / weekFoodRatio) * 100;
                    if (foodPercent < 50) {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + weekFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.green);
                    } else if (foodPercent >= 50 && foodPercent < 100) {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + weekFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + weekFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.red);
                    }

                    float eduPercent = (weekEdu / weekEducationRatio) * 100;
                    if (eduPercent < 50) {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + weekEducationRatio);
                        binding.statusImageEdu.setImageResource(R.drawable.green);
                    } else if (eduPercent >= 50 && eduPercent < 100) {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + weekEducationRatio);
                        binding.statusImageEdu.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + weekEducationRatio);
                        binding.statusImageEdu.setImageResource(R.drawable.red);
                    }

                    float transportPercent = (transportTotal / tranRatio) * 100;
                    if (transportPercent < 50) {
                        binding.progressRatioTransport.setText(transportPercent + "%" + " used of Rs." + tranRatio);
                        binding.statusImageTransport.setImageResource(R.drawable.green);
                    } else if (transportPercent >= 50 && transportPercent < 100) {
                        binding.progressRatioTransport.setText(transportPercent + "%" + " used of Rs." + tranRatio);
                        binding.statusImageTransport.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioTransport.setText(transportPercent + "%" + " used of Rs." + tranRatio);
                        binding.statusImageTransport.setImageResource(R.drawable.red);
                    }

                    float housePercent = (weekHouse / weekHouseRatio) * 100;
                    if (housePercent < 50) {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + weekHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.green);
                    } else if (housePercent >= 50 && housePercent < 100) {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + weekHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + weekHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.red);
                    }

                    float entertainmentPercent = (weekEntertainment / weekEntertainmentRatio) * 100;
                    if (entertainmentPercent < 50) {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + weekEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.green);
                    } else if (entertainmentPercent >= 50 && entertainmentPercent < 100) {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + weekEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + weekEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.red);
                    }

                    float charityPercent = (weekCharity / weekCharityRatio) * 100;
                    if (charityPercent < 50) {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + weekCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.green);
                    } else if (charityPercent >= 50 && charityPercent < 100) {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + weekCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + weekCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.red);
                    }

                    float appPercent = (weekApparel / weekApparelRatio) * 100;
                    if (appPercent < 50) {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + weekApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.green);
                    } else if (appPercent >= 50 && appPercent < 100) {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + weekApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + weekApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.red);
                    }

                    float healthPercent = (weekHealth / weekHealthRatio) * 100;
                    if (healthPercent < 50) {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + weekHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.green);
                    } else if (healthPercent >= 50 && healthPercent < 100) {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + weekHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + weekHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.red);
                    }

                    float personalPercent = (weekPersonal / weekPersonalRatio) * 100;
                    if (personalPercent < 50) {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + weekPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.green);
                    } else if (personalPercent >= 50 && personalPercent < 100) {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + weekPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + weekPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.red);
                    }

                    float othersPercent = (weekOthers / weekOthersRatio) * 100;
                    if (othersPercent < 50) {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + weekOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.green);
                    } else if (othersPercent >= 50 && othersPercent < 100) {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + weekOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + weekOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.red);
                    }


                } else {
                    Toast.makeText(WeeklyAnalyticActivity.this, "No Expenditure!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
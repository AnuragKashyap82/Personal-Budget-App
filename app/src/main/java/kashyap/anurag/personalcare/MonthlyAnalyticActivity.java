package kashyap.anurag.personalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.databinding.ActivityMonthlyAnalyticBinding;

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
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonthlyAnalyticActivity extends AppCompatActivity {
    private ActivityMonthlyAnalyticBinding binding;
    private FirebaseAuth firebaseAuth;
    int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMonthlyAnalyticBinding.inflate(getLayoutInflater());
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
        getTotalMonthlyExpenditure();

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
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Education" + months.getMonths();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthEdu").setValue(totalAmount);
                } else {
                    binding.linearLayoutEducation.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthEdu").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyTransportExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Transport" + months.getMonths();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthTrans").setValue(totalAmount);
                } else {
                    binding.linearLayoutTransport.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthTrans").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyFoodExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Food" + months.getMonths();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthFood").setValue(totalAmount);
                } else {
                    binding.linearLayoutFood.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthFood").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyHouseExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "House" + months.getMonths();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthHouse").setValue(totalAmount);
                } else {
                    binding.linearLayoutFoodHouse.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthHouse").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyEntertainmentExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Entertainment" + months.getMonths();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthEntertainment").setValue(totalAmount);
                } else {
                    binding.linearLayoutEntertainment.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthEntertainment").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyCharityExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Charity" + months.getMonths();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthCharity").setValue(totalAmount);
                } else {
                    binding.linearLayoutCharity.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthCharity").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyApparelExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Apparel" + months.getMonths();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthApparel").setValue(totalAmount);
                } else {
                    binding.linearLayoutApparel.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthApparel").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyHealthExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Health" + months.getMonths();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child(" monthHealth").setValue(totalAmount);
                } else {
                    binding.linearLayoutHealth.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthHealth").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyPersonalExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Personal" + months.getMonths();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthPersonal").setValue(totalAmount);
                } else {
                    binding.linearLayoutPersonalExp.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthPersonal").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyOthersExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        String itemMonth = "Others" + months.getMonths();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemMonth").equalTo(itemMonth);
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
                    databaseReference1.child("monthOthers").setValue(totalAmount);
                } else {
                    binding.linearLayoutOther.setVisibility(View.GONE);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("monthOthers").setValue(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalMonthlyExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("month").equalTo(months.getMonths());
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
                Toast.makeText(MonthlyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    if (snapshot.hasChild("monthTrans")) {
                        transportTotal = Integer.parseInt(snapshot.child("monthTrans").getValue().toString());
                    } else {
                        transportTotal = 0;
                    }

                    int monthEdu = 0;
                    if (snapshot.hasChild("monthEdu")) {
                        monthEdu = Integer.parseInt(snapshot.child("monthEdu").getValue().toString());
                    } else {
                        monthEdu = 0;
                    }

                    int monthFood = 0;
                    if (snapshot.hasChild("monthFood")) {
                        monthFood = Integer.parseInt(snapshot.child("monthFood").getValue().toString());
                    } else {
                        monthFood = 0;
                    }

                    int monthHouse = 0;
                    if (snapshot.hasChild("monthHouse")) {
                        monthHouse = Integer.parseInt(snapshot.child("monthHouse").getValue().toString());
                    } else {
                        monthHouse = 0;
                    }

                    int monthEntertainment = 0;
                    if (snapshot.hasChild("monthEntertainment")) {
                        monthEntertainment = Integer.parseInt(snapshot.child("monthEntertainment").getValue().toString());
                    } else {
                        monthEntertainment = 0;
                    }

                    int monthCharity = 0;
                    if (snapshot.hasChild("monthCharity")) {
                        monthCharity = Integer.parseInt(snapshot.child("monthCharity").getValue().toString());
                    } else {
                        monthCharity = 0;
                    }

                    int monthApparel = 0;
                    if (snapshot.hasChild("monthApparel")) {
                        monthApparel = Integer.parseInt(snapshot.child("monthApparel").getValue().toString());
                    } else {
                        monthApparel = 0;
                    }

                    int monthHealth = 0;
                    if (snapshot.hasChild("monthHealth")) {
                        monthHealth = Integer.parseInt(snapshot.child("monthHealth").getValue().toString());
                    } else {
                        monthHealth = 0;
                    }

                    int monthPersonal = 0;
                    if (snapshot.hasChild("monthPersonal")) {
                        monthPersonal = Integer.parseInt(snapshot.child("monthPersonal").getValue().toString());
                    } else {
                        monthPersonal = 0;
                    }

                    int monthOthers = 0;
                    if (snapshot.hasChild("monthOthers")) {
                        monthOthers = Integer.parseInt(snapshot.child("monthOthers").getValue().toString());
                    } else {
                        monthOthers = 0;
                    }

                    Pie pie = AnyChart.pie();
                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Transport", transportTotal));
                    data.add(new ValueDataEntry("Food", monthFood));
                    data.add(new ValueDataEntry("House", monthHouse));
                    data.add(new ValueDataEntry("Entertainment", monthEntertainment));
                    data.add(new ValueDataEntry("Education", monthEdu));
                    data.add(new ValueDataEntry("Charity", monthCharity));
                    data.add(new ValueDataEntry("Apparel", monthApparel));
                    data.add(new ValueDataEntry("Health", monthHealth));
                    data.add(new ValueDataEntry("Personal", monthPersonal));
                    data.add(new ValueDataEntry("Others", monthOthers));

                    pie.data(data);
                    pie.title("Monthly Analytics");
                    pie.labels().position("outside");
                    pie.legend().title()
                            .text(":Items Spent On")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend().position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    binding.anyChartView.setChart(pie);

                } else {
                    Toast.makeText(MonthlyAnalyticActivity.this, "No Expenditure!!!", Toast.LENGTH_SHORT).show();
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
                    if (snapshot.hasChild("monthTrans")) {
                        transportTotal = Integer.parseInt(snapshot.child("monthTrans").getValue().toString());
                    } else {
                        transportTotal = 0;
                    }

                    int monthEdu = 0;
                    if (snapshot.hasChild("monthEdu")) {
                        monthEdu = Integer.parseInt(snapshot.child("monthEdu").getValue().toString());
                    } else {
                        monthEdu = 0;
                    }

                    int monthFood = 0;
                    if (snapshot.hasChild("monthFood")) {
                        monthFood = Integer.parseInt(snapshot.child("monthFood").getValue().toString());
                    } else {
                        monthFood = 0;
                    }

                    int monthHouse = 0;
                    if (snapshot.hasChild("monthHouse")) {
                        monthHouse = Integer.parseInt(snapshot.child("monthHouse").getValue().toString());
                    } else {
                        monthHouse = 0;
                    }

                    int monthEntertainment = 0;
                    if (snapshot.hasChild("monthEntertainment")) {
                        monthEntertainment = Integer.parseInt(snapshot.child("monthEntertainment").getValue().toString());
                    } else {
                        monthEntertainment = 0;
                    }

                    int monthCharity = 0;
                    if (snapshot.hasChild("monthCharity")) {
                        monthCharity = Integer.parseInt(snapshot.child("monthCharity").getValue().toString());
                    } else {
                        monthCharity = 0;
                    }

                    int monthApparel = 0;
                    if (snapshot.hasChild("monthApparel")) {
                        monthApparel = Integer.parseInt(snapshot.child("monthApparel").getValue().toString());
                    } else {
                        monthApparel = 0;
                    }

                    int monthHealth = 0;
                    if (snapshot.hasChild("monthHealth")) {
                        monthHealth = Integer.parseInt(snapshot.child("monthHealth").getValue().toString());
                    } else {
                        monthHealth = 0;
                    }

                    int monthPersonal = 0;
                    if (snapshot.hasChild("monthPersonal")) {
                        monthPersonal = Integer.parseInt(snapshot.child("monthPersonal").getValue().toString());
                    } else {
                        monthPersonal = 0;
                    }

                    int monthOthers = 0;
                    if (snapshot.hasChild("monthOthers")) {
                        monthOthers = Integer.parseInt(snapshot.child("monthOthers").getValue().toString());
                    } else {
                        monthOthers = 0;
                    }



                    float monthTotalSpentAmount;
                    if (snapshot.hasChild("month")) {
                        monthTotalSpentAmount = Integer.parseInt(snapshot.child("month").getValue().toString());
                    } else {
                        monthTotalSpentAmount = 0;
                    }


                    float monthTransportRatio;
                    if (snapshot.hasChild("monthTransportRatio")) {
                        monthTransportRatio = Integer.parseInt(snapshot.child("monthTransportRatio").getValue().toString());
                    } else {
                        monthTransportRatio = 0;
                    }

                    float monthEducationRatio;
                    if (snapshot.hasChild("monthEducationRatio")) {
                        monthEducationRatio = Integer.parseInt(snapshot.child("monthEducationRatio").getValue().toString());
                    } else {
                        monthEducationRatio = 0;
                    }

                    float monthFoodRatio;
                    if (snapshot.hasChild("monthFoodRatio")) {
                        monthFoodRatio = Integer.parseInt(snapshot.child("monthFoodRatio").getValue().toString());
                    } else {
                        monthFoodRatio = 0;
                    }

                    float monthHouseRatio;
                    if (snapshot.hasChild("monthHouseRatio")) {
                        monthHouseRatio = Integer.parseInt(snapshot.child("monthHouseRatio").getValue().toString());
                    } else {
                        monthHouseRatio = 0;
                    }

                    float monthEntertainmentRatio;
                    if (snapshot.hasChild("monthEntertainmentRatio")) {
                        monthEntertainmentRatio = Integer.parseInt(snapshot.child("monthEntertainmentRatio").getValue().toString());
                    } else {
                        monthEntertainmentRatio = 0;
                    }

                    float monthCharityRatio;
                    if (snapshot.hasChild("monthCharityRatio")) {
                        monthCharityRatio = Integer.parseInt(snapshot.child("monthCharityRatio").getValue().toString());
                    } else {
                        monthCharityRatio = 0;
                    }

                    float monthApparelRatio;
                    if (snapshot.hasChild("monthApparelRatio")) {
                        monthApparelRatio = Integer.parseInt(snapshot.child("monthApparelRatio").getValue().toString());
                    } else {
                        monthApparelRatio = 0;
                    }

                    float monthHealthRatio;
                    if (snapshot.hasChild("monthHealthRatio")) {
                        monthHealthRatio = Integer.parseInt(snapshot.child("monthHealthRatio").getValue().toString());
                    } else {
                        monthHealthRatio = 0;
                    }

                    float monthPersonalRatio;
                    if (snapshot.hasChild("monthPersonalRatio")) {
                        monthPersonalRatio = Integer.parseInt(snapshot.child("monthPersonalRatio").getValue().toString());
                    } else {
                        monthPersonalRatio = 0;
                    }

                    float monthOthersRatio;
                    if (snapshot.hasChild("monthOthersRatio")) {
                        monthOthersRatio = Integer.parseInt(snapshot.child("monthOthersRatio").getValue().toString());
                    } else {
                        monthOthersRatio = 0;
                    }


                    float monthTotalSpentAmountRatio;
                    if (snapshot.hasChild("budget")) {
                        monthTotalSpentAmountRatio = Integer.parseInt(snapshot.child("budget").getValue().toString());
                    } else {
                        monthTotalSpentAmountRatio = 0;
                    }

                    float monthPercent = (totalAmount / monthTotalSpentAmountRatio) * 100;
                    if (monthPercent < 50) {
                        binding.monthRatioSpending.setText(monthPercent + "%" + " used of " + monthTotalSpentAmountRatio + ". Status");
                        binding.monthRatioIv.setImageResource(R.drawable.green);
                    } else if (monthPercent >= 50 && monthPercent < 100) {
                        binding.monthRatioSpending.setText(monthPercent + "%" + " used of " + monthTotalSpentAmountRatio + ". Status");
                        binding.monthRatioIv.setImageResource(R.drawable.brown);
                    } else {
                        binding.monthRatioSpending.setText(monthPercent + "%" + " used of " + monthTotalSpentAmountRatio + ". Status");
                        binding.monthRatioIv.setImageResource(R.drawable.red);
                    }


                    float foodPercent = (monthFood / monthFoodRatio) * 100;
                    if (foodPercent < 50) {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + monthFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.green);
                    } else if (foodPercent >= 50 && foodPercent < 100) {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + monthFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + monthFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.red);
                    }

                    float eduPercent = (monthEdu / monthEducationRatio) * 100;
                    if (eduPercent < 50) {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + monthEducationRatio);
                        binding.statusImageEdu.setImageResource(R.drawable.green);
                    } else if (eduPercent >= 50 && eduPercent < 100) {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + monthEducationRatio);
                        binding.statusImageEdu.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + monthEducationRatio);
                        binding.statusImageEdu.setImageResource(R.drawable.red);
                    }

                    float transportPercent = (transportTotal / monthTransportRatio) * 100;
                    if (transportPercent < 50) {
                        binding.progressRatioTransport.setText(transportPercent + "%" + " used of Rs." + monthTransportRatio);
                        binding.statusImageTransport.setImageResource(R.drawable.green);
                    } else if (transportPercent >= 50 && transportPercent < 100) {
                        binding.progressRatioTransport.setText(transportPercent + "%" + " used of Rs." + monthTransportRatio);
                        binding.statusImageTransport.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioTransport.setText(transportPercent + "%" + " used of Rs." + monthTransportRatio);
                        binding.statusImageTransport.setImageResource(R.drawable.red);
                    }

                    float housePercent = (monthHouse / monthHouseRatio) * 100;
                    if (housePercent < 50) {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + monthHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.green);
                    } else if (housePercent >= 50 && housePercent < 100) {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + monthHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + monthHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.red);
                    }

                    float entertainmentPercent = (monthEntertainment / monthEntertainmentRatio) * 100;
                    if (entertainmentPercent < 50) {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + monthEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.green);
                    } else if (entertainmentPercent >= 50 && entertainmentPercent < 100) {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + monthEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + monthEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.red);
                    }

                    float charityPercent = (monthCharity / monthCharityRatio) * 100;
                    if (charityPercent < 50) {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + monthCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.green);
                    } else if (charityPercent >= 50 && charityPercent < 100) {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + monthCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + monthCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.red);
                    }

                    float appPercent = (monthApparel / monthApparelRatio) * 100;
                    if (appPercent < 50) {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + monthApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.green);
                    } else if (appPercent >= 50 && appPercent < 100) {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + monthApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + monthApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.red);
                    }

                    float healthPercent = (monthHealth / monthHealthRatio) * 100;
                    if (healthPercent < 50) {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + monthHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.green);
                    } else if (healthPercent >= 50 && healthPercent < 100) {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + monthHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + monthHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.red);
                    }

                    float personalPercent = (monthPersonal / monthPersonalRatio) * 100;
                    if (personalPercent < 50) {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + monthPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.green);
                    } else if (personalPercent >= 50 && personalPercent < 100) {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + monthPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + monthPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.red);
                    }

                    float othersPercent = (monthOthers / monthOthersRatio) * 100;
                    if (othersPercent < 50) {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + monthOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.green);
                    } else if (othersPercent >= 50 && othersPercent < 100) {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + monthOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + monthOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.red);
                    }


                } else {
                    Toast.makeText(MonthlyAnalyticActivity.this, "No Expenditure!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
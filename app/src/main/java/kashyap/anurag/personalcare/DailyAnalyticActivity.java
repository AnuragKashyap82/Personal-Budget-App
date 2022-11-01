package kashyap.anurag.personalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.databinding.ActivityDailyAnalyticBinding;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DailyAnalyticActivity extends AppCompatActivity {
    private ActivityDailyAnalyticBinding binding;
    private FirebaseAuth firebaseAuth;
    int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDailyAnalyticBinding.inflate(getLayoutInflater());
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
        getTotalDayExpenditure();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadGraph();
                setStatusAndImageResource();
            }
        }, 4000);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DailyAnalyticActivity.this, MainActivity.class));
            }
        });
    }

    private void getTotalDailyEducationExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Education" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayEdu").setValue(totalAmount);
                } else {
                    binding.linearLayoutEducation.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyTransportExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Transport" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayTrans").setValue(totalAmount);
                } else {
                    binding.linearLayoutTransport.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyFoodExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Food" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayFood").setValue(totalAmount);
                } else {
                    binding.linearLayoutFood.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyHouseExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "House" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayHouse").setValue(totalAmount);
                } else {
                    binding.linearLayoutFoodHouse.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyEntertainmentExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Entertainment" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.linearLayoutEntertainment.setVisibility(View.VISIBLE);
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        binding.analyticsEntertainmentAmount.setText("Spent: Rs." + totalAmount);
                    }
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayEntertainment").setValue(totalAmount);
                } else {
                    binding.linearLayoutEntertainment.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyCharityExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Charity" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayCharity").setValue(totalAmount);
                } else {
                    binding.linearLayoutCharity.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyApparelExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Apparel" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayApparel").setValue(totalAmount);
                } else {
                    binding.linearLayoutApparel.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyHealthExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Health" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayHealth").setValue(totalAmount);
                } else {
                    binding.linearLayoutHealth.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyPersonalExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Personal" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayPersonal").setValue(totalAmount);
                } else {
                    binding.linearLayoutPersonalExp.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDailyOthersExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());
        String itemDay = "Others" + date;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("itemDay").equalTo(itemDay);
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
                    databaseReference1.child("dayOthers").setValue(totalAmount);
                } else {
                    binding.linearLayoutOther.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTotalDayExpenditure() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("date").equalTo(date);
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
                Toast.makeText(DailyAnalyticActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    if (snapshot.hasChild("dayTrans")) {
                        transportTotal = Integer.parseInt(snapshot.child("dayTrans").getValue().toString());
                    } else {
                        transportTotal = 0;
                    }

                    int dayEdu = 0;
                    if (snapshot.hasChild("dayEdu")) {
                        dayEdu = Integer.parseInt(snapshot.child("dayEdu").getValue().toString());
                    } else {
                        dayEdu = 0;
                    }

                    int dayFood = 0;
                    if (snapshot.hasChild("dayFood")) {
                        dayFood = Integer.parseInt(snapshot.child("dayFood").getValue().toString());
                    } else {
                        dayFood = 0;
                    }

                    int dayHouse = 0;
                    if (snapshot.hasChild("dayHouse")) {
                        dayHouse = Integer.parseInt(snapshot.child("dayHouse").getValue().toString());
                    } else {
                        dayHouse = 0;
                    }

                    int dayEntertainment = 0;
                    if (snapshot.hasChild("dayEntertainment")) {
                        dayEntertainment = Integer.parseInt(snapshot.child("dayEntertainment").getValue().toString());
                    } else {
                        dayEntertainment = 0;
                    }

                    int dayCharity = 0;
                    if (snapshot.hasChild("dayCharity")) {
                        dayCharity = Integer.parseInt(snapshot.child("dayCharity").getValue().toString());
                    } else {
                        dayCharity = 0;
                    }

                    int dayApparel = 0;
                    if (snapshot.hasChild("dayApparel")) {
                        dayApparel = Integer.parseInt(snapshot.child("dayApparel").getValue().toString());
                    } else {
                        dayApparel = 0;
                    }

                    int dayHealth = 0;
                    if (snapshot.hasChild("dayHealth")) {
                        dayHealth = Integer.parseInt(snapshot.child("dayHealth").getValue().toString());
                    } else {
                        dayHealth = 0;
                    }

                    int dayPersonal = 0;
                    if (snapshot.hasChild("dayPersonal")) {
                        dayPersonal = Integer.parseInt(snapshot.child("dayPersonal").getValue().toString());
                    } else {
                        dayPersonal = 0;
                    }

                    int dayOthers = 0;
                    if (snapshot.hasChild("dayOthers")) {
                        dayOthers = Integer.parseInt(snapshot.child("dayOthers").getValue().toString());
                    } else {
                        dayOthers = 0;
                    }

                    Pie pie = AnyChart.pie();
                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Transport", transportTotal));
                    data.add(new ValueDataEntry("Food", dayFood));
                    data.add(new ValueDataEntry("House", dayHouse));
                    data.add(new ValueDataEntry("Entertainment", dayEntertainment));
                    data.add(new ValueDataEntry("Education", dayEdu));
                    data.add(new ValueDataEntry("Charity", dayCharity));
                    data.add(new ValueDataEntry("Apparel", dayApparel));
                    data.add(new ValueDataEntry("Health", dayHealth));
                    data.add(new ValueDataEntry("Personal", dayPersonal));
                    data.add(new ValueDataEntry("Others", dayOthers));

                    pie.data(data);
                    pie.title("Daily Analytics");
                    pie.labels().position("outside");
                    pie.legend().title()
                            .text(":Items Spent On")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend().position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    binding.anyChartView.setChart(pie);

                } else {
                    Toast.makeText(DailyAnalyticActivity.this, "No Expenditure!!!", Toast.LENGTH_SHORT).show();
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
                    if (snapshot.hasChild("dayTrans")) {
                        transportTotal = Integer.parseInt(snapshot.child("dayTrans").getValue().toString());
                    } else {
                        transportTotal = 0;
                    }

                    int dayEdu = 0;
                    if (snapshot.hasChild("dayEdu")) {
                        dayEdu = Integer.parseInt(snapshot.child("dayEdu").getValue().toString());
                    } else {
                        dayEdu = 0;
                    }

                    int dayFood = 0;
                    if (snapshot.hasChild("dayFood")) {
                        dayFood = Integer.parseInt(snapshot.child("dayFood").getValue().toString());
                    } else {
                        dayFood = 0;
                    }

                    int dayHouse = 0;
                    if (snapshot.hasChild("dayHouse")) {
                        dayHouse = Integer.parseInt(snapshot.child("dayHouse").getValue().toString());
                    } else {
                        dayHouse = 0;
                    }

                    int dayEntertainment = 0;
                    if (snapshot.hasChild("dayEntertainment")) {
                        dayEntertainment = Integer.parseInt(snapshot.child("dayEntertainment").getValue().toString());
                    } else {
                        dayEntertainment = 0;
                    }

                    int dayCharity = 0;
                    if (snapshot.hasChild("dayCharity")) {
                        dayCharity = Integer.parseInt(snapshot.child("dayCharity").getValue().toString());
                    } else {
                        dayCharity = 0;
                    }

                    int dayApparel = 0;
                    if (snapshot.hasChild("dayApparel")) {
                        dayApparel = Integer.parseInt(snapshot.child("dayApparel").getValue().toString());
                    } else {
                        dayApparel = 0;
                    }

                    int dayHealth = 0;
                    if (snapshot.hasChild("dayHealth")) {
                        dayHealth = Integer.parseInt(snapshot.child("dayHealth").getValue().toString());
                    } else {
                        dayHealth = 0;
                    }

                    int dayPersonal = 0;
                    if (snapshot.hasChild("dayPersonal")) {
                        dayPersonal = Integer.parseInt(snapshot.child("dayPersonal").getValue().toString());
                    } else {
                        dayPersonal = 0;
                    }

                    int dayOthers = 0;
                    if (snapshot.hasChild("dayOthers")) {
                        dayOthers = Integer.parseInt(snapshot.child("dayOthers").getValue().toString());
                    } else {
                        dayOthers = 0;
                    }


                    float monthTotalSpentAmount;
                    if (snapshot.hasChild("today")) {
                        monthTotalSpentAmount = Integer.parseInt(snapshot.child("today").getValue().toString());
                    } else {
                        monthTotalSpentAmount = 0;
                    }


                    float tranRatio;
                    if (snapshot.hasChild("dayTransportRatio")) {
                        tranRatio = Integer.parseInt(snapshot.child("dayTransportRatio").getValue().toString());
                    } else {
                        tranRatio = 0;
                    }

                    float dayEducationRatio;
                    if (snapshot.hasChild("dayEducationRatio")) {
                        dayEducationRatio = Integer.parseInt(snapshot.child("dayEducationRatio").getValue().toString());
                    } else {
                        dayEducationRatio = 0;
                    }

                    float dayFoodRatio;
                    if (snapshot.hasChild("dayFoodRatio")) {
                        dayFoodRatio = Integer.parseInt(snapshot.child("dayFoodRatio").getValue().toString());
                    } else {
                        dayFoodRatio = 0;
                    }

                    float dayHouseRatio;
                    if (snapshot.hasChild("dayHouseRatio")) {
                        dayHouseRatio = Integer.parseInt(snapshot.child("dayHouseRatio").getValue().toString());
                    } else {
                        dayHouseRatio = 0;
                    }

                    float dayEntertainmentRatio;
                    if (snapshot.hasChild("dayEntertainmentRatio")) {
                        dayEntertainmentRatio = Integer.parseInt(snapshot.child("dayEntertainmentRatio").getValue().toString());
                    } else {
                        dayEntertainmentRatio = 0;
                    }

                    float dayCharityRatio;
                    if (snapshot.hasChild("dayCharityRatio")) {
                        dayCharityRatio = Integer.parseInt(snapshot.child("dayCharityRatio").getValue().toString());
                    } else {
                        dayCharityRatio = 0;
                    }

                    float dayApparelRatio;
                    if (snapshot.hasChild("dayApparelRatio")) {
                        dayApparelRatio = Integer.parseInt(snapshot.child("dayApparelRatio").getValue().toString());
                    } else {
                        dayApparelRatio = 0;
                    }

                    float dayHealthRatio;
                    if (snapshot.hasChild("dayHealthRatio")) {
                        dayHealthRatio = Integer.parseInt(snapshot.child("dayHealthRatio").getValue().toString());
                    } else {
                        dayHealthRatio = 0;
                    }

                    float dayPersonalRatio;
                    if (snapshot.hasChild("dayPersonalRatio")) {
                        dayPersonalRatio = Integer.parseInt(snapshot.child("dayPersonalRatio").getValue().toString());
                    } else {
                        dayPersonalRatio = 0;
                    }

                    float dayOthersRatio;
                    if (snapshot.hasChild("dayOthersRatio")) {
                        dayOthersRatio = Integer.parseInt(snapshot.child("dayOthersRatio").getValue().toString());
                    } else {
                        dayOthersRatio = 0;
                    }


                    float monthTotalSpentAmountRatio;
                    if (snapshot.hasChild("budget")) {
                        monthTotalSpentAmountRatio = Integer.parseInt(snapshot.child("budget").getValue().toString())/30;

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


                    float foodPercent = (dayFood / dayFoodRatio) * 100;
                    if (foodPercent < 50) {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + dayFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.green);
                    } else if (foodPercent >= 50 && foodPercent < 100) {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + dayFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioFood.setText(foodPercent + "%" + " used of Rs." + dayFoodRatio);
                        binding.statusImageFood.setImageResource(R.drawable.red);
                    }

                    float eduPercent = (dayEdu / dayEducationRatio) * 100;
                    if (eduPercent < 50) {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + dayEducationRatio);
                        binding.statusImageEdu.setImageResource(R.drawable.green);
                    } else if (eduPercent >= 50 && eduPercent < 100) {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + dayEducationRatio);
                        binding.statusImageEdu.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioEdu.setText(eduPercent + "%" + " used of Rs." + dayEducationRatio);
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

                    float housePercent = (dayHouse / dayHouseRatio) * 100;
                    if (housePercent < 50) {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + dayHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.green);
                    } else if (housePercent >= 50 && housePercent < 100) {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + dayHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioHouse.setText(housePercent + "%" + " used of Rs." + dayHouseRatio);
                        binding.statusImageHouse.setImageResource(R.drawable.red);
                    }

                    float entertainmentPercent = (dayEntertainment / dayEntertainmentRatio) * 100;
                    if (entertainmentPercent < 50) {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + dayEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.green);
                    } else if (entertainmentPercent >= 50 && entertainmentPercent < 100) {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + dayEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioEnt.setText(entertainmentPercent + "%" + " used of Rs." + dayEntertainmentRatio);
                        binding.statusImageEnt.setImageResource(R.drawable.red);
                    }

                    float charityPercent = (dayCharity / dayCharityRatio) * 100;
                    if (charityPercent < 50) {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + dayCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.green);
                    } else if (charityPercent >= 50 && charityPercent < 100) {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + dayCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioCha.setText(charityPercent + "%" + " used of Rs." + dayCharityRatio);
                        binding.statusImageCha.setImageResource(R.drawable.red);
                    }

                    float appPercent = (dayApparel / dayApparelRatio) * 100;
                    if (appPercent < 50) {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + dayApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.green);
                    } else if (appPercent >= 50 && appPercent < 100) {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + dayApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioApp.setText(appPercent + "%" + " used of Rs." + dayApparelRatio);
                        binding.statusImageApp.setImageResource(R.drawable.red);
                    }

                    float healthPercent = (dayHealth / dayHealthRatio) * 100;
                    if (healthPercent < 50) {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + dayHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.green);
                    } else if (healthPercent >= 50 && healthPercent < 100) {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + dayHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioHea.setText(healthPercent + "%" + " used of Rs." + dayHealthRatio);
                        binding.statusImageHea.setImageResource(R.drawable.red);
                    }

                    float personalPercent = (dayPersonal / dayPersonalRatio) * 100;
                    if (personalPercent < 50) {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + dayPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.green);
                    } else if (personalPercent >= 50 && personalPercent < 100) {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + dayPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioPer.setText(personalPercent + "%" + " used of Rs." + dayPersonalRatio);
                        binding.statusImagePer.setImageResource(R.drawable.red);
                    }

                    float othersPercent = (dayOthers / dayOthersRatio) * 100;
                    if (othersPercent < 50) {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + dayOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.green);
                    } else if (othersPercent >= 50 && othersPercent < 100) {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + dayOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.brown);
                    } else {
                        binding.progressRatioOth.setText(othersPercent + "%" + " used of Rs." + dayOthersRatio);
                        binding.statusImageOth.setImageResource(R.drawable.red);
                    }


                } else {
                    Toast.makeText(DailyAnalyticActivity.this, "No Expenditure!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
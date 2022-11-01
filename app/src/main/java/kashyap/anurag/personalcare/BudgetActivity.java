package kashyap.anurag.personalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.Adapters.AdapterData;
import kashyap.anurag.personalcare.Model.Data;
import kashyap.anurag.personalcare.databinding.ActivityBudgetBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class BudgetActivity extends AppCompatActivity {

    private ActivityBudgetBinding binding;
    private FirebaseAuth firebaseAuth;
    private AdapterData adapterData;
    private ArrayList<Data> dataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBudgetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItems();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BudgetActivity.this, MainActivity.class));
            }
        });

        getMonthEducationBudgetRatio();
        getMonthTransportBudgetRatio();
        getMonthFoodBudgetRatio();
        getMonthHouseBudgetRatio();
        getMonthEntertainmentBudgetRatio();
        getMonthCharityBudgetRatio();
        getMonthApparelBudgetRatio();
        getMonthHealthBudgetRatio();
        getMonthPersonalBudgetRatio();
        getMonthOthersBudgetRatio();
    }

    private void addItems() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this,  R.style.BottomSheetStyle);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.input_layout, null);
        myDialog.setView(view);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(true);

        final Spinner itemSpinner = view.findViewById(R.id.itemSpinner);
        final EditText amount = view.findViewById(R.id.amount);
        final Button save = view.findViewById(R.id.save);
        final Button cancel = view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String budgetAmount = amount.getText().toString().trim();
                String budgetItem = itemSpinner.getSelectedItem().toString();

                if (budgetAmount.isEmpty()) {
                    Toast.makeText(BudgetActivity.this, "Enter Amount!!!!", Toast.LENGTH_SHORT).show();
                } else if (budgetItem == "" || budgetItem.equals("Select Item")) {
                    Toast.makeText(BudgetActivity.this, "Select Budget Item!!!", Toast.LENGTH_SHORT).show();
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
                    String id = databaseReference.push().getKey();
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar calendar = Calendar.getInstance();
                    String date = dateFormat.format(calendar.getTime());

                    MutableDateTime epoch = new MutableDateTime();
                    epoch.setDate(0);
                    DateTime now = new DateTime();

                    Weeks weeks = Weeks.weeksBetween(epoch, now);
                    Months months = Months.monthsBetween(epoch, now);

                    String itemDay = budgetItem+date;
                    String itemWeek = budgetItem+weeks.getWeeks();
                    String itemMonth = budgetItem+months.getMonths();



                    Data data = new Data(budgetItem, date, id, null, itemDay, itemWeek, itemMonth, Integer.parseInt(budgetAmount), months.getMonths(), weeks.getWeeks());
                    databaseReference.child(budgetItem).setValue(data)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        binding.progressBar.setVisibility(View.GONE);
                                        Toast.makeText(BudgetActivity.this, "Budget item added successfully!!!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        binding.progressBar.setVisibility(View.GONE);
                                        Toast.makeText(BudgetActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadAllData();
        loadBudget();
    }

    private void loadAllData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        dataArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Budget").child(firebaseAuth.getUid());
        reference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataArrayList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Data model = ds.getValue(Data.class);
                            dataArrayList.add(model);
                        }

                        binding.recyclerView.setHasFixedSize(true);
                        adapterData = new AdapterData(BudgetActivity.this, dataArrayList);
                        binding.recyclerView.setAdapter(adapterData);
                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
    }
    private void loadBudget() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Budget").child(firebaseAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalAmount = 0;
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    Data data = snapshot1.getValue(Data.class);
                    totalAmount += data.getAmount();
                    String sTotal = String.valueOf("Rs."+totalAmount);
                    binding.totalBudgetAmountTv.setText(sTotal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void getMonthEducationBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Education");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayEducationRatio = pTotal/30;
                    int weekEducationRatio = pTotal/4;
                    int monthEducationRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayEducationRatio").setValue(dayEducationRatio);
                    databaseReference1.child("weekEducationRatio").setValue(weekEducationRatio);
                    databaseReference1.child("monthEducationRatio").setValue(monthEducationRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayEducationRatio").setValue(0);
                    databaseReference1.child("weekEducationRatio").setValue(0);
                    databaseReference1.child("monthEducationRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthTransportBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Transport");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayTransportRatio = pTotal/30;
                    int weekTransportRatio = pTotal/4;
                    int monthTransportRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayTransportRatio").setValue(dayTransportRatio);
                    databaseReference1.child("weekTransportRatio").setValue(weekTransportRatio);
                    databaseReference1.child("monthTransportRatio").setValue(monthTransportRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayTransportRatio").setValue(0);
                    databaseReference1.child("weekTransportRatio").setValue(0);
                    databaseReference1.child("monthTransportRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthFoodBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Food");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayFoodRatio = pTotal/30;
                    int weekFoodRatio = pTotal/4;
                    int monthFoodRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayFoodRatio").setValue(dayFoodRatio);
                    databaseReference1.child("weekFoodRatio").setValue(weekFoodRatio);
                    databaseReference1.child("monthFoodRatio").setValue(monthFoodRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayFoodRatio").setValue(0);
                    databaseReference1.child("weekFoodRatio").setValue(0);
                    databaseReference1.child("monthFoodRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthHouseBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("House");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayHouseRatio = pTotal/30;
                    int weekHouseRatio = pTotal/4;
                    int monthHouseRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayHouseRatio").setValue(dayHouseRatio);
                    databaseReference1.child("weekHouseRatio").setValue(weekHouseRatio);
                    databaseReference1.child("monthHouseRatio").setValue(monthHouseRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayHouseRatio").setValue(0);
                    databaseReference1.child("weekHouseRatio").setValue(0);
                    databaseReference1.child("monthHouseRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthEntertainmentBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Entertainment");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayEntertainmentRatio = pTotal/30;
                    int weekEntertainmentRatio = pTotal/4;
                    int monthEntertainmentRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayEntertainmentRatio").setValue(dayEntertainmentRatio);
                    databaseReference1.child("weekEntertainmentRatio").setValue(weekEntertainmentRatio);
                    databaseReference1.child("monthEntertainmentRatio").setValue(monthEntertainmentRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayEntertainmentRatio").setValue(0);
                    databaseReference1.child("weekEntertainmentRatio").setValue(0);
                    databaseReference1.child("monthEntertainmentRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthCharityBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Charity");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayCharityRatio = pTotal/30;
                    int weekCharityRatio = pTotal/4;
                    int monthCharityRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayCharityRatio").setValue(dayCharityRatio);
                    databaseReference1.child("weekCharityRatio").setValue(weekCharityRatio);
                    databaseReference1.child("monthCharityRatio").setValue(monthCharityRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayCharityRatio").setValue(0);
                    databaseReference1.child("weekCharityRatio").setValue(0);
                    databaseReference1.child("monthCharityRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthApparelBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Apparel");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayApparelRatio = pTotal/30;
                    int weekApparelRatio = pTotal/4;
                    int monthApparelRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayApparelRatio").setValue(dayApparelRatio);
                    databaseReference1.child("weekApparelRatio").setValue(weekApparelRatio);
                    databaseReference1.child("monthApparelRatio").setValue(monthApparelRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayApparelRatio").setValue(0);
                    databaseReference1.child("weekApparelRatio").setValue(0);
                    databaseReference1.child("monthApparelRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthHealthBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Health");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayHealthRatio = pTotal/30;
                    int weekHealthRatio = pTotal/4;
                    int monthHealthRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayHealthRatio").setValue(dayHealthRatio);
                    databaseReference1.child("weekHealthRatio").setValue(weekHealthRatio);
                    databaseReference1.child("monthHealthRatio").setValue(monthHealthRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayHealthRatio").setValue(0);
                    databaseReference1.child("weekHealthRatio").setValue(0);
                    databaseReference1.child("monthHealthRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthPersonalBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Personal");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayPersonalRatio = pTotal/30;
                    int weekPersonalRatio = pTotal/4;
                    int monthPersonalRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayPersonalRatio").setValue(dayPersonalRatio);
                    databaseReference1.child("weekPersonalRatio").setValue(weekPersonalRatio);
                    databaseReference1.child("monthPersonalRatio").setValue(monthPersonalRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayPersonalRatio").setValue(0);
                    databaseReference1.child("weekPersonalRatio").setValue(0);
                    databaseReference1.child("monthPersonalRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMonthOthersBudgetRatio() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        Query query = databaseReference.orderByChild("item").equalTo("Others");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int pTotal = 0;
                    for (DataSnapshot ds: snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        pTotal = Integer.parseInt(String.valueOf(total));
                    }
                    int dayOthersRatio = pTotal/30;
                    int weekOthersRatio = pTotal/4;
                    int monthOthersRatio = pTotal;

                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayOthersRatio").setValue(dayOthersRatio);
                    databaseReference1.child("weekOthersRatio").setValue(weekOthersRatio);
                    databaseReference1.child("monthOthersRatio").setValue(monthOthersRatio);

                }else {
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Personal").child(firebaseAuth.getUid());
                    databaseReference1.child("dayOthersRatio").setValue(0);
                    databaseReference1.child("weekOthersRatio").setValue(0);
                    databaseReference1.child("monthOthersRatio").setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BudgetActivity.this, MainActivity.class));
        finishAffinity();
    }
}
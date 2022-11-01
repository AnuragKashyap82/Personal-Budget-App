package kashyap.anurag.personalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.Adapters.AdapterData;
import kashyap.anurag.personalcare.Adapters.AdapterToday;
import kashyap.anurag.personalcare.Model.Data;
import kashyap.anurag.personalcare.databinding.ActivityTodayBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import java.util.Map;

public class TodayActivity extends AppCompatActivity {
    private ActivityTodayBinding binding;
    private FirebaseAuth firebaseAuth;
    private AdapterToday adapterToday;
    private ArrayList<Data> dataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSpentItem();
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TodayActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAllData();
    }

    private void loadAllData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        dataArrayList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        String date = dateFormat.format(calendar.getTime());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Expenses").child(firebaseAuth.getUid());
        Query query = reference.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataArrayList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Data model = ds.getValue(Data.class);
                            dataArrayList.add(model);
                        }

                        int totalAmount = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            Object total = map.get("amount");
                            int pTotal = Integer.parseInt(String.valueOf(total));
                            totalAmount += pTotal;
                            String sTotal = String.valueOf("Rs."+totalAmount);
                            binding.totalBudgetAmountTv.setText(sTotal);
                        }

                        binding.recyclerView.setHasFixedSize(true);
                        adapterToday = new AdapterToday(TodayActivity.this, dataArrayList);
                        binding.recyclerView.setAdapter(adapterToday);
                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void addSpentItem() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this, R.style.BottomSheetStyle);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.input_layout, null);
        myDialog.setView(view);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(true);

        final Spinner itemSpinner = view.findViewById(R.id.itemSpinner);
        final EditText amount = view.findViewById(R.id.amount);
        final EditText note = view.findViewById(R.id.note);
        final RelativeLayout noteRl = view.findViewById(R.id.noteRl);
        final Button save = view.findViewById(R.id.save);
        final Button cancel = view.findViewById(R.id.cancel);

        noteRl.setVisibility(View.VISIBLE);

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
                String note1 = note.getText().toString().trim();

                if (budgetAmount.isEmpty()) {
                    Toast.makeText(TodayActivity.this, "Enter Amount!!!!", Toast.LENGTH_SHORT).show();
                } else if (budgetItem == "") {
                    Toast.makeText(TodayActivity.this, "Select Budget Item!!!", Toast.LENGTH_SHORT).show();
                } else if (note1.isEmpty()) {
                    Toast.makeText(TodayActivity.this, "Select Budget Item!!!", Toast.LENGTH_SHORT).show();
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
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

                    Data data = new Data(budgetItem, date, id, note1, itemDay, itemWeek, itemMonth, Integer.parseInt(budgetAmount), months.getMonths(), weeks.getWeeks());
                    databaseReference.child(id).setValue(data)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        binding.progressBar.setVisibility(View.GONE);
                                        Toast.makeText(TodayActivity.this, "Budget item added successfully!!!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        binding.progressBar.setVisibility(View.GONE);
                                        Toast.makeText(TodayActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TodayActivity.this, MainActivity.class));
        finishAffinity();
    }
}
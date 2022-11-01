package kashyap.anurag.personalcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.Adapters.AdapterWeek;
import kashyap.anurag.personalcare.Model.Data;
import kashyap.anurag.personalcare.databinding.ActivityMonthlyBinding;

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

import java.util.ArrayList;

public class MonthlyActivity extends AppCompatActivity {
    private ActivityMonthlyBinding binding;
    private FirebaseAuth firebaseAuth;
    private AdapterWeek adapterWeek;
    private ArrayList<Data> dataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMonthlyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MonthlyActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAllData();
        loadBudget();

    }

    private void loadAllData() {
        binding.progressBar.setVisibility(View.VISIBLE);

        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);

        dataArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Expenses").child(firebaseAuth.getUid());
        Query query = reference.orderByChild("month").equalTo(months.getMonths());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Data model = ds.getValue(Data.class);
                    dataArrayList.add(model);
                }

                binding.recyclerView.setHasFixedSize(true);
                adapterWeek = new AdapterWeek(MonthlyActivity.this, dataArrayList);
                binding.recyclerView.setAdapter(adapterWeek);
                binding.progressBar.setVisibility(View.GONE);

                int totalAmount = 0;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Data data = snapshot1.getValue(Data.class);
                    totalAmount += data.getAmount();
                    String sTotal = String.valueOf("Rs." + totalAmount);
                    binding.totalBudgetAmountTv.setText(sTotal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadBudget() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Expenses").child(firebaseAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}
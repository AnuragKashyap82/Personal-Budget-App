package kashyap.anurag.personalcare;

import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.databinding.ActivityChooseAnalyticBinding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseAnalyticActivity extends AppCompatActivity {
    ActivityChooseAnalyticBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseAnalyticBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.todayCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseAnalyticActivity.this, DailyAnalyticActivity.class));
            }
        });
        binding.weeklyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseAnalyticActivity.this, WeeklyAnalyticActivity.class));
            }
        });
        binding.monthlyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseAnalyticActivity.this, MonthlyAnalyticActivity.class));
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseAnalyticActivity.this, MainActivity.class));
            }
        });
    }
}
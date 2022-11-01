package kashyap.anurag.personalcare;

import androidx.appcompat.app.AppCompatActivity;
import kashyap.anurag.personalcare.databinding.ActivitySplashBinding;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    Animation animation;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        animation = AnimationUtils.loadAnimation(this, R.anim.anim);

        binding.lottie.setAnimation(animation);
        binding.appName.setAnimation(animation);
        binding.developerNameTv.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        }, 3000);
    }

    private void checkUser() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finishAffinity();
        }else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finishAffinity();
        }
    }
}
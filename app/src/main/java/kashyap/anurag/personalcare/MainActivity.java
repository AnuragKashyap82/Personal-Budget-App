package kashyap.anurag.personalcare;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;
import kashyap.anurag.personalcare.databinding.ActivityMainBinding;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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

    private FirebaseAuth firebaseAuth;

    private int totalAmountBudget = 0;
    private int totalAmountMonth = 0;
    private int totalAmountWeekly = 0;
    private int totalAmountDaily = 0;

    private String name, email;
    private String profileImage;
    private CircleImageView profileDialogIv;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;

    private String[] cameraPermission;
    private String[] storagePermission;

    private Uri image_uri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        binding.profileCardView.setEnabled(false);

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        loadBudget();
        loadTodaySpending();
        loadWeeklySpending();
        loadMonthlySpending();
        loadMyInfo();

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
        binding.profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileDialog();
            }
        });
        binding.logoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
    }

    private void loadBudget() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Budget").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmountBudget += pTotal;
                    }
                    binding.budgetTv.setText("Rs." + totalAmountBudget);
                    updateBudgetInDataBase(totalAmountBudget);
                } else {
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
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmountDaily += pTotal;
                    }
                    binding.todayTv.setText("Rs." + totalAmountDaily);
                } else {
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
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmountWeekly += pTotal;
                    }
                    binding.weekTv.setText("Rs." + totalAmountWeekly);
                } else {
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
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmountMonth += pTotal;
                        updateExpenditureInDataBase(totalAmountMonth);
                    }
                    binding.monthTv.setText("Rs." + totalAmountMonth);

                } else {
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
                if (snapshot.exists()) {
                    int budget;
                    if (snapshot.hasChild("budget")) {
                        budget = Integer.parseInt(snapshot.child("budget").getValue().toString());
                    } else {
                        budget = 0;
                    }
                    int expenditure;
                    if (snapshot.hasChild("expenditure")) {
                        expenditure = Integer.parseInt(snapshot.child("expenditure").getValue().toString());
                    } else {
                        expenditure = 0;
                    }

                    int savings = budget - expenditure;
                    binding.savingsTv.setText("Rs." + savings);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadMyInfo() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    email = snapshot.child("email").getValue().toString();
                    name = snapshot.child("name").getValue().toString();

                    if (snapshot.hasChild("profileImage")) {
                        profileImage = snapshot.child("profileImage").getValue().toString();
                    }
                    try {
                        Picasso.get().load(profileImage).placeholder(R.drawable.ic_person).into(binding.profileIv);
                    } catch (Exception e) {
                        binding.profileIv.setImageResource(R.drawable.ic_person);
                    }
                    binding.nameTv.setText(name);
                    binding.profileCardView.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showProfileDialog() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this, R.style.BottomSheetStyle);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.profile_dialog, null);
        myDialog.setView(view1);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(true);

        profileDialogIv = view1.findViewById(R.id.profileDialogIv);
        EditText nameEt = view1.findViewById(R.id.nameEt);
        TextView emailEt = view1.findViewById(R.id.emailEt);
        Button updateBtn = view1.findViewById(R.id.updateBtn);
        ProgressBar progressBarDialog = view1.findViewById(R.id.progressBarDialog);


        nameEt.setText(name);
        emailEt.setText(email);

        if (profileImage != "") {
            try {
                Picasso.get().load(profileImage).placeholder(R.drawable.ic_person).into(profileDialogIv);
            } catch (Exception e) {
                profileDialogIv.setImageResource(R.drawable.ic_person);
            }
        }

        profileDialogIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData(nameEt, profileImage, updateBtn, progressBarDialog);
            }
        });
        dialog.show();
    }

    private void validateData(EditText nameEt, String profileImage, Button
            updateBtn, ProgressBar progressBarDialog) {
        String name1 = nameEt.getText().toString().trim();
        if (name1.isEmpty()) {
            Toast.makeText(MainActivity.this, "Enter Your name!!!!", Toast.LENGTH_SHORT).show();
        } else {
            updateDataToDataBase(name1, profileImage, updateBtn, progressBarDialog);
        }
    }

    private void updateDataToDataBase(String name1, String profileImage, Button
            updateBtn, ProgressBar progressBarDialog) {
        progressBarDialog.setVisibility(View.VISIBLE);
        updateBtn.setVisibility(View.GONE);

        if (image_uri == null) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", "" + name1);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());
            databaseReference.updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBarDialog.setVisibility(View.GONE);
                            updateBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Profile Updated Successfully!!!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBarDialog.setVisibility(View.GONE);
                            updateBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        } else {
            String filePathAndName = "profile_images/" + "" + firebaseAuth.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadImageUri = uriTask.getResult();

                            if (uriTask.isSuccessful()) {

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("name", "" + name);
                                hashMap.put("profileImage", "" + downloadImageUri);

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());
                                databaseReference.updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressBarDialog.setVisibility(View.GONE);
                                                updateBtn.setVisibility(View.VISIBLE);
                                                Toast.makeText(MainActivity.this, "Profile Updated Successfully!!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressBarDialog.setVisibility(View.GONE);
                                                updateBtn.setVisibility(View.VISIBLE);
                                                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBarDialog.setVisibility(View.GONE);
                            updateBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void showImagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            if (checkCameraPermission()) {
                                pickImageCamera();
                            } else {
                                requestCameraPermission();
                            }
                        } else {
                            if (checkStoragePermission()) {
                                pickImageGallery();
                            } else {
                                requestStoragePermission();
                            }
                        }
                    }
                })
                .show();
    }

    private void pickImageCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Pick");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Image Description");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        cameraActivityResultLauncher.launch(intent);

    }

    private void pickImageGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);

    }

    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        profileDialogIv.setImageURI(image_uri);
                    } else {
                        Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }

                }
            }
    );

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        image_uri = data.getData();

                        profileDialogIv.setImageURI(image_uri);
                    } else {
                        Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) ==
                (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        Toast.makeText(this, "Storage and Camera permission granted", Toast.LENGTH_SHORT).show();
                        pickImageCamera();
                    } else {
                        Toast.makeText(this, "Camera permission are necessary...!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        Toast.makeText(this, "Storage Permission granted", Toast.LENGTH_SHORT).show();
                        pickImageGallery();
                    } else {
                        Toast.makeText(this, "Storage permission is necessary...!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void showLogoutDialog () {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this, R.style.BottomSheetStyle);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.logout_dialog, null);
        myDialog.setView(view1);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(true);

        Button cancelBtn = view1.findViewById(R.id.cancelBtn);
        Button logoutBtn = view1.findViewById(R.id.logoutBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                binding.progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });
        dialog.show();
    }
}
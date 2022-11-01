package kashyap.anurag.personalcare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import kashyap.anurag.personalcare.Model.Data;
import kashyap.anurag.personalcare.R;

public class AdapterToday extends RecyclerView.Adapter<AdapterToday.HolderToday> {

    private Context context;
    private ArrayList<Data> dataArrayList;
    private FirebaseAuth firebaseAuth;

    public AdapterToday(Context context, ArrayList<Data> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public HolderToday onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.retrive_layout, parent, false);
        return new HolderToday(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderToday holder, int position) {
        Data data = dataArrayList.get(position);
        String item = data.getItem();
        String date = data.getDate();
        String note = data.getNotes();
        int amount = data.getAmount();

        holder.item.setText("Budget Item: "+item);
        holder.dateTv.setText("On: "+date);
        holder.amountTv.setText("Amount: Rs."+Integer.parseInt(String.valueOf(amount)));

        holder.note.setVisibility(View.VISIBLE);
        holder.note.setText(note);

        if (item.equals("Education")){
            holder.imageView.setImageResource(R.drawable.ic_education);
        }else {
            holder.imageView.setImageResource(R.drawable.ic_house);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(data.getId(), item, amount, note);
            }
        });
    }

    private void showUpdateDialog(String id, String item, int amount, String note) {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(context, R.style.BottomSheetStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view1 = inflater.inflate(R.layout.update_layout, null);
        myDialog.setView(view1);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(true);

        TextView itemTv   = view1.findViewById(R.id.categoryTv);
        EditText updateAmount = view1.findViewById(R.id.updateAmount);
        EditText note1 = view1.findViewById(R.id.note);
        RelativeLayout noteRl = view1.findViewById(R.id.noteRl);
        Button deleteBtn = view1.findViewById(R.id.deleteBtn);
        Button updateBtn = view1.findViewById(R.id.updateBtn);

        noteRl.setVisibility(View.VISIBLE);

        updateAmount.setText(""+Integer.parseInt(String.valueOf(amount)));
        itemTv.setText(item);
        note1.setText(note);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                delete(id);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String budgetAmount = updateAmount.getText().toString().trim();
                String updatedNote = note1.getText().toString().trim();

                if (budgetAmount.isEmpty()) {
                    Toast.makeText(context, "Enter Amount!!!!", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Expenses").child(firebaseAuth.getUid());
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar calendar = Calendar.getInstance();
                    String date = dateFormat.format(calendar.getTime());

                    MutableDateTime epoch = new MutableDateTime();
                    epoch.setDate(0);
                    DateTime now = new DateTime();
                    Months months = Months.monthsBetween(epoch, now);

                    int month = months.getMonths();

                    HashMap hashMap = new HashMap<>();
                    hashMap.put("amount", Integer.parseInt(budgetAmount));
                    hashMap.put("date", ""+date);
                    hashMap.put("month", month);
                    hashMap.put("notes", ""+updatedNote);

                    databaseReference.child(id)
                            .updateChildren(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(context, "Today item updated successfully!!!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void delete(String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(firebaseAuth.getUid());
        databaseReference.child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Deleted Successfully!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class HolderToday extends RecyclerView.ViewHolder {

        TextView item, amountTv, note, dateTv;
        ImageView imageView;

        public HolderToday(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            amountTv = itemView.findViewById(R.id.amountTv);
            note = itemView.findViewById(R.id.note);
            dateTv = itemView.findViewById(R.id.dateTv);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

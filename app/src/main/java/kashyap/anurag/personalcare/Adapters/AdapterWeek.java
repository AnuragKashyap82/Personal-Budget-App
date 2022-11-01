package kashyap.anurag.personalcare.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class AdapterWeek extends RecyclerView.Adapter<AdapterWeek.HolderWeek> {

    private Context context;
    private ArrayList<Data> dataArrayList;
    private FirebaseAuth firebaseAuth;

    public AdapterWeek(Context context, ArrayList<Data> dataArrayList) {
        this.context = context;
        this.dataArrayList = dataArrayList;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public HolderWeek onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.retrive_layout, parent, false);
        return new HolderWeek(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderWeek holder, int position) {
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class HolderWeek extends RecyclerView.ViewHolder {

        TextView item, amountTv, note, dateTv;
        ImageView imageView;

        public HolderWeek(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            amountTv = itemView.findViewById(R.id.amountTv);
            note = itemView.findViewById(R.id.note);
            dateTv = itemView.findViewById(R.id.dateTv);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

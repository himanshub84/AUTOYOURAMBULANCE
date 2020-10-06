package e.himanshu.loginmodule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Recycle extends AppCompatActivity  {
    List<HELP> help;
    RecyclerView recyclerView;
    HelperAdapter helperAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_main);
        recyclerView=(RecyclerView) findViewById(R.id.Recyclerviewmain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        help=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("HELP");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn: snapshot.getChildren()){
                    HELP hp=sn.getValue(HELP.class);
                    help.add(hp);
                }
                helperAdapter=new HelperAdapter(help);
                recyclerView.setAdapter(helperAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}

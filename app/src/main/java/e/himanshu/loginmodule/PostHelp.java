package e.himanshu.loginmodule;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostHelp extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION=1;

    EditText postname,postnumber,postlocation,postmessage;
    String locationlink="";
    String lat="";
    String lng="";
    Button posthelpbutton;
    DatabaseReference databaseReference;
    HELP help;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posthelp);
        postname=(findViewById(R.id.editText_post_name));
        postnumber=(findViewById(R.id.editText_psot_number));
        postlocation=(findViewById(R.id.editText_post_location));
        postmessage=(findViewById(R.id.editText_post_message));
        posthelpbutton=(findViewById(R.id.button_post_message));
        help=new HELP();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("HELP");

    }

    public void post_help_button(View view) {
        loactionacces();
        help.setName(postname.getText().toString().trim());
        help.setPHN(postnumber.getText().toString().trim());
        help.setLOC(postlocation.getText().toString().trim());
        help.setLOCLINK(locationlink);
        help.setPSTMSSG(postmessage.getText().toString());
        databaseReference.push().setValue(help).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PostHelp.this, "MESSAGE POST SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PostHelp.this, "MESSAGE POST FAILED", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void loactionacces(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PostHelp.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSION);

        }else {
            final LocationRequest locationRequest=new LocationRequest();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(3000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.getFusedLocationProviderClient(PostHelp.this).requestLocationUpdates(locationRequest,new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    LocationServices.getFusedLocationProviderClient(PostHelp.this).removeLocationUpdates(this);
                    if(locationResult !=null && locationResult.getLocations().size()>0){
                        int latestlocationindex=locationResult.getLocations().size() -1;
                        double latitude =locationResult.getLocations().get(latestlocationindex).getLatitude();
                        double longitude=locationResult.getLocations().get(latestlocationindex).getLongitude();
                        lat=String.valueOf(latitude);
                        lng=String.valueOf(longitude);
                        locationlink="https://www.google.com/maps/place/"+lat+"+"+lng;
                        Log.w("location",locationlink);

                    }
                }
            }, Looper.getMainLooper());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_LOCATION_PERMISSION && grantResults.length>0){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                loactionacces();
            }else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }

}

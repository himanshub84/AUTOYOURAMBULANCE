package e.himanshu.loginmodule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    Button registerbtn;
    EditText emailet;
    EditText nameet;
    EditText passet;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        registerbtn=findViewById(R.id.button_register);
        nameet=findViewById(R.id.editText_signup_name);
        emailet=findViewById(R.id.editText_signup_email);
        passet=findViewById(R.id.editText_signup_password);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("USERS");
        progressDialog=new ProgressDialog(this);
        if(getIntent().hasExtra("MOBILE")){
            passet.setVisibility(View.GONE);
        }else{
            passet.setVisibility(View.VISIBLE);

        }

    }
    public void register(View view) {
        String name=nameet.getText().toString();
        String emailId=emailet.getText().toString();
        String password="";
        if(getIntent().hasExtra("MOBILE")){
            if(!name.equalsIgnoreCase("")){
                if(!emailId.equalsIgnoreCase("")){
                    progressDialog.setMessage("Pleasee Waittt....");
                    progressDialog.show();
                    String mobile=getIntent().getExtras().getString("MOBILE");
                    String uid=getIntent().getExtras().getString("UID");
                    UserInfo userInfo=new UserInfo(name,emailId,mobile);
                    databaseReference.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this,"User is Register Successfully",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SignUpActivity.this,"Error Register In User",Toast.LENGTH_SHORT).show();

                            }

                        }

                    });

                }else {
                    Toast.makeText(this,"Please Enter Email Id",Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this,"Please Enter The Name",Toast.LENGTH_SHORT).show();
            }

        }else {
            password=passet.getText().toString();
            registeruser(name,emailId,password);
        }

    }
    public void registeruser(final String name, final String email, String password){
        if(!name.equalsIgnoreCase("")){
            if(!email.equalsIgnoreCase("")){
                if(!password.equalsIgnoreCase("")){
                    progressDialog.setMessage("Pleasee Waittt....");
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser currentuser=firebaseAuth.getCurrentUser();
                                String uid=currentuser.getUid();
                                UserInfo userInfo=new UserInfo(name,email,"");
                                databaseReference.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        if(task.isSuccessful()){
                                            Toast.makeText(SignUpActivity.this,"User is Register Successfully",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }else{
                                Log.w("signup", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this,"Error Register In User",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(this,"Please Enter The Password",Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this,"Please Enter Valid Email",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this,"Please Enter The Name",Toast.LENGTH_SHORT).show();
        }


    }
}

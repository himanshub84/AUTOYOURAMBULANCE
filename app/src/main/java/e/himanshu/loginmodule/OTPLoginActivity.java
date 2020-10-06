package e.himanshu.loginmodule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPLoginActivity extends AppCompatActivity {
    EditText numberet;
    Button otpcheck;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String mobilenumber="";
    PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otplogin);
        numberet=findViewById(R.id.editTextText_mobile);
        otpcheck=findViewById(R.id.button_verify);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Verifying PhoneNumber.......");
        verificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signinwithmobile(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OTPLoginActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        };

    }

    public void Otplogin(View view) {
        mobilenumber="+91"+numberet.getText().toString();
        if(!mobilenumber.equalsIgnoreCase("")){
            verifymobilenum(mobilenumber);
        }else {
            Toast.makeText(this,"Please Enter Valid Mobile Number",Toast.LENGTH_SHORT).show();

        }
    }
    public void verifymobilenum(String mobile){
        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile,60, TimeUnit.SECONDS,this,verificationStateChangedCallbacks);
    }
    public void signinwithmobile(PhoneAuthCredential phoneAuthCredential){
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   progressDialog.dismiss();
                   Toast.makeText(OTPLoginActivity.this,"Mobile Number Verified Successfully",Toast.LENGTH_SHORT).show();
                   FirebaseUser currentuser=task.getResult().getUser();
                   String uid=currentuser.getUid();
                   Intent i=new Intent(OTPLoginActivity.this,SignUpActivity.class);
                   i.putExtra("MOBILE",mobilenumber);
                   i.putExtra("UID",uid);
                   startActivity(i);
                   finish();
               }else {
                   Toast.makeText(OTPLoginActivity.this,"Error Using OTP Login",Toast.LENGTH_SHORT).show();
               }
            }
        });

    }
}

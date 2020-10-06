package e.himanshu.loginmodule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText editTextemail;
    EditText editTextpassword;
    Button loginbtn;
    TextView signup;
    TextView forgotpass;
    TextView otplogin;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextemail=findViewById(R.id.editText_email);
        editTextpassword=findViewById(R.id.editText_password);
        loginbtn=findViewById(R.id.button_login);
        signup=findViewById(R.id.text_signup);
        otplogin=findViewById(R.id.text_otp);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        forgotpass=findViewById(R.id.text_forgot);
    }


    public void Signupclick(View view) {
        Intent i1=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(i1);

    }
    public void LoginUsers(View view) {
        String emailID=editTextemail.getText().toString();
        String password=editTextpassword.getText().toString();
        if(!emailID.equalsIgnoreCase("")){
            if(!password.equalsIgnoreCase("")){
                loginuser(emailID,password);

            }else {
                Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this,"Please Enter Email Id",Toast.LENGTH_SHORT).show();
        }
    }
    public void loginuser(String email,String password){
        progressDialog.setMessage("Pleasee Waittt....");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
                    Intent i3=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i3);
                    finish();
                }else {
                    String errormssge=task.getException().getMessage();
                    Toast.makeText(LoginActivity.this,""+errormssge,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void ForgotClick(View view) {
        String email=editTextemail.getText().toString();
        if(!email.equalsIgnoreCase("")){
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Please Check Your Email For Reset Password ",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this,"Error Resending The Password Email",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else {
            Toast.makeText(this,"Please Enter Valid Email Id",Toast.LENGTH_SHORT).show();
        }
    }

    public void OTPLogin(View view) {
        Intent i=new Intent(LoginActivity.this,OTPLoginActivity.class);
        startActivity(i);
        finish();
    }
}
package e.himanshu.loginmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void posthelp(View view) {
        Intent i=new Intent(MainActivity.this,PostHelp.class);
        startActivity(i);
    }

    public void helpothers(View view) {
        Intent i=new Intent(MainActivity.this,Recycle.class);
        startActivity(i);
    }
}
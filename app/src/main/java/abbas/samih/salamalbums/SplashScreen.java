package abbas.samih.salamalbums;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import abbas.samih.salamalbums.FullExample.MainSimpleLabelActivity;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        //Thread 1.
        Thread th=new Thread(){
            //Thread 2.
            @Override
            public void run() {
                // هنا المقطع الذي سيعمل بالتزامن مع مقاطع اخرى
                //Thread 3.
                int ms=3*1000;//miiliseconds.
                try {
                    sleep(ms);
                    finish();
                    //  فحص هل تم الدخول مسبقا
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    if(auth.getCurrentUser()!=null)
                        startActivity(new Intent(getApplicationContext(), MainSimpleLabelActivity.class));
                    else
                    {
                        startActivity(new Intent(getApplicationContext(),logInActivity.class));
                    }

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        // Thread 4.
        th.start();
    }
}
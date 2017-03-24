package us.jeff_wilson.mystop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class ArrivedPopupActivity extends AppCompatActivity {

    private static final String TAG = "us.jeff_wilson.mystop";

    Button dismissButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrived_popup);

        dismissButton = (Button) findViewById(R.id.dismissButton);

        dismissButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "dismiss button clicked");

                Intent i=new Intent(ArrivedPopupActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                finish();
            }
        });
    }
}

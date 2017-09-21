package ponze.service;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ponze.sevicedemo.R;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private android.widget.Button startservice;
    private android.widget.Button closeservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        this.closeservice = (Button) findViewById(R.id.close_service);
        this.startservice = (Button) findViewById(R.id.start_service);


        this.startservice.setOnClickListener(this);
        this.closeservice.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_service) {

        } else if (view.getId() == R.id.close_service) {

        }
    }
}

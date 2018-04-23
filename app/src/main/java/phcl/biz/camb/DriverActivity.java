package phcl.biz.camb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriverActivity extends AppCompatActivity {
FirebaseAuth auth;
FirebaseUser user;
    Toolbar toolbar;

    Menu topMenu;
@BindView(R.id.profilename)

    TextView txtPName;
    @BindView(R.id.btnOut)
    Button sOut;
    @BindView(R.id.btnUp) Button btnUp;
    @BindView(R.id.btnmap) Button btnmap;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }
    public void signOut(){
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_prof);
        toolbar.setTitle("Driver Profile");
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        txtPName.setText(user.getEmail());
        sOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LocatorAcitivity.class));
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DocumentActivity.class));
            }
        });
    }

}

package phcl.biz.camb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import phcl.biz.camb.model.User;
import phcl.biz.camb.model.Vehicle;

import static phcl.biz.camb.model.Constants.FIREBASE_CHILD_USERREG;
import static phcl.biz.camb.model.Constants.FIREBASE_CHILD_VEHICLEREG;

public class VehicleRegActivity extends AppCompatActivity {
    private String userId;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    Toolbar toolbar;
    @BindView(R.id.vehcapacity)
    EditText edtCapacity;
    @BindView(R.id.vehtype) EditText edtType;
    @BindView(R.id.vehweight) EditText edtWeight;
    @BindView(R.id.btnVehReg)
    Button btnVehR;

    String cap = edtCapacity.getText().toString().trim();;
    String type= edtType.getText().toString().trim();;
    String weight = edtWeight.getText().toString().trim();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclereg);
        toolbar = (Toolbar) findViewById(R.id.toolbar_veh);
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'veh' node
        mFirebaseDatabase = mFirebaseInstance.getReference(FIREBASE_CHILD_VEHICLEREG);
        btnVehR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vehicleValid()){
                    vehReg(cap, type, weight);
                }
            }
        });

    }
    public boolean vehicleValid(){
        boolean valid = true;

        if (cap.isEmpty() || cap.length() <3){
            edtCapacity.setError("enter a valid Vehicle capacity");

            valid = false;
        } else {
            edtCapacity.setError(null);
        }
        if (type.isEmpty() || type.length() <3){
            edtType.setError("Please enter a valid vehicle type");
        } else {
            edtType.setError(null);
        }
        if (weight.isEmpty() || weight.length()<=2){
            edtWeight.setError("Enter appropriate weight in tonnes");
        }else {
            edtWeight.setError(null);
        }
        return valid;
    }
    public void vehReg(String cap, String type, String weight){
            // TODO
            // In real apps this userId should be fetched
            // by implementing firebase auth
            if (TextUtils.isEmpty(userId)) {
                userId = mFirebaseDatabase.push().getKey();
            }

        Vehicle vehicle = new Vehicle(cap, type, weight);

        mFirebaseDatabase.child(userId).setValue(vehicle);

        }

}

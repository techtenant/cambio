package phcl.biz.camb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import phcl.biz.camb.model.User;

import static phcl.biz.camb.model.Constants.FIREBASE_CHILD_USERREG;

public class RegisterActivity extends AppCompatActivity {
Toolbar toolbar;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    @BindView(R.id.regemail)
    EditText edtRegEmail;
    @BindView(R.id.regfname) EditText edtFname;
    @BindView(R.id.reglname) EditText edtLName;
    @BindView(R.id.regphone) EditText edtPhone;
    @BindView(R.id.regpass) EditText edtRegPass;
    @BindView(R.id.confregpass) EditText edtConPass;
    @BindView(R.id.btnReg)
    Button btnRegi;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static final String TAG = RegisterActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar_reg);
        toolbar.setTitle("Registration");
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(FIREBASE_CHILD_USERREG);
        createAuthStateListener();
        btnRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg();
                if (validate()){
                    if (TextUtils.isEmpty(userId)) {
                        createUser(edtPhone.getText().toString().trim(), edtFname.getText().toString().trim(),
                                edtLName.getText().toString().trim());
                        Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    public boolean validate(){
        boolean valid = true;
        String email = edtRegEmail.getText().toString().trim();
        String password = edtRegPass.getText().toString().trim();
        String reEnterPassword = edtConPass.getText().toString().trim();
        String phoneNum = edtPhone.getText().toString().trim();
        String fName = edtFname.getText().toString().trim();
        String lName = edtLName.getText().toString().trim();


        if (fName.isEmpty() || fName.length() < 3) {
            edtFname.setError("at least 3 characters");
            valid = false;
        } else {
            edtFname.setError(null);
        }

        if (lName.isEmpty() || lName.length() < 3) {
            edtLName.setError("at least 3 characters");
            valid = false;
        } else {
            edtLName.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtRegEmail.setError("enter a valid email address");
            valid = false;
        } else {
            edtRegEmail.setError(null);
        }


        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            edtRegPass.setError("between 6 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtRegPass.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            edtConPass.setError("Password Do not match");
            valid = false;
        } else {
            edtConPass.setError(null);
        }
        if (phoneNum.isEmpty() || phoneNum.length() < 3) {
            edtPhone.setError("at least 3 characters");
            valid = false;
        } else {
            //phoneValidate();
            edtPhone.setError(null);
        }

        return valid;
    }
    public void reg(){
        mAuth.createUserWithEmailAndPassword(edtRegEmail.getText().toString().trim(),
                edtRegPass.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Authentication successful");
                            Toast.makeText(RegisterActivity.this, "User Created Successfully",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "User Creation failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(RegisterActivity.this, DocumentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void createUser(String phoneNum, String fName, String lName) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        User user = new User(phoneNum, fName, lName);

        mFirebaseDatabase.child(userId).setValue(user);

        //addUserChangeListener();
    }
}

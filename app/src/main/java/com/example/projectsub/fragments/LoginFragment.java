package com.example.projectsub.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectsub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LOGIN";
    private FirebaseAuth mAuth;

    private EditText emailTxtBox, passwordTxtBox;
    private Button loginBtn;
    private TextView registerTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            updateUI();
        }

        emailTxtBox = view.findViewById(R.id.emaillogin);
        passwordTxtBox = view.findViewById(R.id.passlogin);
        loginBtn = view.findViewById(R.id.loginbutton);
        registerTxt = view.findViewById(R.id.registerTextView);

        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createAccount(emailTxtBox.getText().toString(), passwordTxtBox.getText().toString());
                sharedAnimation(view);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(emailTxtBox.getText().toString(), passwordTxtBox.getText().toString());
            }
        });

        return view;
    }

    private void sharedAnimation (View view) {
        View back = view.findViewById(R.id.view_back_register);
        View front = view.findViewById(R.id.view_front_register);
        TextView name = view.findViewById(R.id.nameTextView);
        Log.d(TAG, "sharedAnimation: "+back);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        RegisterFragment fragment = new RegisterFragment();
        manager.beginTransaction()
                .addSharedElement(back, ViewCompat.getTransitionName(back))
                .addSharedElement(front, ViewCompat.getTransitionName(front))
                .addSharedElement(name, ViewCompat.getTransitionName(name))
                .addToBackStack(TAG)
                .replace(R.id.main_fragment, fragment)
                .commit();
    }

    private void createAccount (String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            Toast.makeText(getActivity(), "Authentication succeded.", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void updateUI () {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        MainPageFragment fragment = new MainPageFragment();

        manager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
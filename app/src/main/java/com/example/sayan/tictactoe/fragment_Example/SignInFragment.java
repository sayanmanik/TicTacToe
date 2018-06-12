package com.example.sayan.tictactoe.fragment_Example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sayan.tictactoe.FriendsActivity;
import com.example.sayan.tictactoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by 1605476 and 10-Jun-18
 **/
public class SignInFragment extends DialogFragment implements View.OnClickListener
{
    AppCompatButton btnSignIn, btnOldUser;
    FirebaseAuth firebaseAuth;
    EditText nameText, emailText, passwordText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;
    String Name,Email,Password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signin_fragment, container,
                false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        currentUser=firebaseAuth.getCurrentUser();

        btnSignIn = rootView.findViewById(R.id.btnSignUp);
        btnOldUser = rootView.findViewById(R.id.btnOldUser);

        nameText = rootView.findViewById(R.id.name);
        emailText = rootView.findViewById(R.id.email);
        passwordText = rootView.findViewById(R.id.password);


        btnOldUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                FragmentManager mgr = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = mgr.beginTransaction();

                LogInFragment logFragment = new LogInFragment();
                SignInFragment old =
                        (SignInFragment) mgr.findFragmentByTag("Sign In Page");
                if (old != null) {
                    ft.remove(old);
                }
                ft.addToBackStack(null);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                logFragment.show(fm, "Log In Page");


            }
        });

        btnSignIn.setOnClickListener(this);

        return rootView;
    }

    private void  sendConfirmation()
    {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getContext(),"Check your mail for confirmation",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                    }
                }
            });
        }
    }

    public String SplitString(String str)
    {
        String[] split=str.split("@");

        return split[0];
    }

    @Override
    public void onClick(View v) {
        Name = nameText.getText().toString().trim();
        Email = emailText.getText().toString().trim();
        Password = passwordText.getText().toString().trim();


        if (TextUtils.isEmpty(Name)) {
            Toast.makeText(getContext(), "name is needed", Toast.LENGTH_SHORT).show();
        }


        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(getContext(), "email is needed", Toast.LENGTH_SHORT).show();
        }


        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(getContext(), "password is needed", Toast.LENGTH_SHORT).show();
        }

        firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {

                   if(currentUser!=null)
                    {
                        databaseReference.child("Users").child(SplitString(currentUser.getEmail().toString())).child("Request").setValue(currentUser.getUid());
                    }
                    sendConfirmation();
                    Intent intent = new Intent(getContext(), FriendsActivity.class);
                    intent.putExtra("email",Email);
                    intent.putExtra("password",Password);
                    startActivity(intent);

                }
                else
                    {

                    Toast.makeText(getContext(), "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
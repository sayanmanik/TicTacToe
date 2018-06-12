package com.example.sayan.tictactoe.fragment_Example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sayan.tictactoe.FriendsActivity;
import com.example.sayan.tictactoe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by 1605476 and 10-Jun-18
 **/
public class LogInFragment extends DialogFragment
{
    AppCompatButton btnLogIn,btnOldUser;
    FirebaseAuth firebaseAuth;
    TextInputEditText email,password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container,
                false);

        firebaseAuth=FirebaseAuth.getInstance();


        email= rootView.findViewById(R.id.email);
        password=rootView.findViewById(R.id.password);

        btnLogIn=rootView.findViewById(R.id.LogIn);
        btnOldUser=rootView.findViewById(R.id.NewUser);

        final String Email=email.getText().toString().trim();
        final String Password=password.getText().toString().trim();

        getDialog().setTitle("Log In Page");

        btnOldUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                FragmentManager mgr = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=mgr.beginTransaction();

                SignInFragment signFragment=new SignInFragment();
                LogInFragment old =
                        (LogInFragment) mgr.findFragmentByTag("Log In Page");
                if (old != null) {
                    ft.remove(old);
                }
                ft.addToBackStack(null);

                FragmentManager fm=getActivity().getSupportFragmentManager();
                signFragment.show(fm,"Sign In Page");
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(TextUtils.isEmpty(Email))
                        {
                            Toast.makeText(getContext(), "email is needed", Toast.LENGTH_SHORT).show();
                        }


                        if(TextUtils.isEmpty(Password))
                        {
                            Toast.makeText(getContext(), "password is needed", Toast.LENGTH_SHORT).show();
                        }



                        if(task.isSuccessful())
                        {

                            Toast.makeText(getContext(),"Successful login",Toast.LENGTH_LONG).show();
                            getActivity().finish();
                            Intent intent=new Intent(getContext(),FriendsActivity.class);
                            intent.putExtra("email",Email);
                            intent.putExtra("password",Password);
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(getContext(),"Check email or password",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        return rootView;
    }


}

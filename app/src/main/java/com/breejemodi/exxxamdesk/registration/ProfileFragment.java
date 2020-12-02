package com.breejemodi.exxxamdesk.registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.breejemodi.exxxamdesk.GetWhoAmI;
import com.breejemodi.exxxamdesk.MainActivity;
import com.breejemodi.exxxamdesk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    private String username;

    public ProfileFragment(String username) {
        this.username = username;
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private EditText edtCollege, edtYear, edtWhoAreYou;
    private Button btnConfirmDetails;
    private ProgressBar pb2;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    private Boolean student;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btnConfirmDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edtYear.setError(null);
                edtCollege.setError(null);
                edtWhoAreYou.setError(null);

                if (edtCollege.getText().toString().isEmpty() || edtCollege.getText().toString().length() < 3) {
                    edtCollege.setError("College Name should have more than 3 characters!");
                    return;
                }
                if (edtWhoAreYou.getText().toString().isEmpty() ){
                        //|| !edtWhoAreYou.getText().toString().equals("student") || !edtWhoAreYou.getText().toString().equals("instructor")) {
                    edtWhoAreYou.setError("Invalid Personality!");
                    student = edtWhoAreYou.getText().toString().equals("student");
                    return;
                }
                if (edtYear.getText().toString().isEmpty() || edtYear.getText().toString().length() > 1) {
                    edtYear.setError("Year Number should be: (1-4)!");
                    return;
                }

                UserDetailsModel model = new UserDetailsModel(username,edtCollege.getText().toString(),edtYear.getText().toString(),edtWhoAreYou.getText().toString());

                try{
                    firestore.collection("UserDetails").document(user.getUid()).set(model);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void init(View view) {

        edtCollege = view.findViewById(R.id.edtCollege);
        edtWhoAreYou = view.findViewById(R.id.edtWhoAreYou);
        edtYear = view.findViewById(R.id.edtYear);
        btnConfirmDetails = view.findViewById(R.id.btnConfirmDetails);
        pb2 = view.findViewById(R.id.pb2);

    }

}
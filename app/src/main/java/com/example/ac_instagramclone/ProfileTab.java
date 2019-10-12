package com.example.ac_instagramclone;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileSport, edtProfileHobbies;
    private Button btnUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileSport = view.findViewById(R.id.edtProfileSport);

        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName") == null || parseUser.get("profileBio") == null ||
                parseUser.get("profileProfession") == null || parseUser.get("profileHobbies") == null
                || parseUser.get("profileSport") == null) {
            edtProfileName.setText("");
            edtProfileBio.setText("");
            edtProfileProfession.setText("");
            edtProfileHobbies.setText("");
            edtProfileSport.setText("");
        } else {
            edtProfileName.setText(parseUser.get("profileName") + "");
            edtProfileName.setText(parseUser.get("profileBio") + "");
            edtProfileName.setText(parseUser.get("profileProfession") + "");
            edtProfileName.setText(parseUser.get("profileHobbies") + "");
            edtProfileName.setText(parseUser.get("profileSport") + "");
        }


        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profileFavSport", edtProfileSport.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(),
                                    "Info Updated", FancyToast.LENGTH_SHORT,
                                    FancyToast.INFO, true).show();
                        } else {
                            FancyToast.makeText(getContext(), e.getMessage(),
                                    FancyToast.LENGTH_SHORT,
                                    FancyToast.ERROR, true).show();
                        }
                    }
                });
            }
        });

        return view;

    }

}

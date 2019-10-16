package com.example.ac_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPosts extends AppCompatActivity {
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);

        mLinearLayout = findViewById(R.id.linearLayout);

        Intent receivedIntentObject = getIntent();
        final String receivedUsersName = receivedIntentObject.getStringExtra("username");

        FancyToast.makeText(this, receivedUsersName, Toast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();

        setTitle(receivedUsersName + "'s posts");

        ParseQuery<ParseObject> parseQuery = new ParseQuery<>("Photo");
        parseQuery.whereEqualTo("username", receivedUsersName);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for (ParseObject post : objects) {
                        final TextView postDescription = new TextView(UsersPosts.this);
                        postDescription.setText(post.get("image_des") + "");
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null && e == null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView postImageView = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams image_params = new LinearLayout.LayoutParams
                                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    image_params.setMargins(5, 5, 5, 5);
                                    postImageView.setLayoutParams(image_params);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams desc_params = new LinearLayout.LayoutParams
                                            (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    desc_params.setMargins(5, 5, 5, 5);
                                    postDescription.setLayoutParams(desc_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.BLUE);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);

                                    mLinearLayout.addView(postImageView);
                                    mLinearLayout.addView(postDescription);
                                }
                            }
                        });
                    }
                } else {
                    FancyToast.makeText(UsersPosts.this, receivedUsersName +
                                    "doesn't have any posts", FancyToast.LENGTH_SHORT, FancyToast.INFO,
                            true).show();
                    finish();
                }

                progressDialog.dismiss();
            }
        });

    }
}

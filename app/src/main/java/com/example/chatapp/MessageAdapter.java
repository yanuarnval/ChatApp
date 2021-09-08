package com.example.chatapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

class MessageAdapter extends ArrayAdapter<FriendlyMessage> {

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List<FriendlyMessage> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        FriendlyMessage message = getItem(position);
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        if (convertView == null) {
            if (user.getDisplayName() == message.getName()){
                convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_messageuser, parent, false);
            }else {
                convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
            }
        }


        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);


        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            Log.d("photos", message.getPhotoUrl());
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);

            Glide.with(photoImageView.getContext()).load(message.getPhotoUrl()).into(photoImageView);


        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
        }
        authorTextView.setText(message.getName());
        return convertView;
    }
}

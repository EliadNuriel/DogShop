package com.example.dog_app;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dog_app.models.Dog;

import java.io.File;
import java.util.List;
import java.util.Set;

public class DogsRvAdapter extends RecyclerView.Adapter<DogsRvAdapter.DogsViewHolder> {

    private List<Dog> dogs;
    private DogItemClickListener listener;
    public DogsRvAdapter(List<Dog> dogs, DogItemClickListener listener) {
        this.dogs = dogs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dog_item,parent,false);
        return new DogsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DogsViewHolder holder, int position) {
        Dog dog = dogs.get(position);
        holder.bind(dog);
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    public void changeDataSet(List<Dog> dogs) {
        this.dogs = dogs;
        notifyDataSetChanged();
    }

     class DogsViewHolder extends  RecyclerView.ViewHolder{
        TextView dogName;
        TextView dogBreed;
        ImageView dogImageView;
        ImageView deleteButton;

        Button showDetails;
        public DogsViewHolder(@NonNull View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.dog_name_tv);
            dogBreed = itemView.findViewById(R.id.dog_breed_tv);
            dogImageView = itemView.findViewById(R.id.dog_image_view);
            deleteButton = itemView.findViewById(R.id.dog_remove_btn);
            showDetails = itemView.findViewById(R.id.showMore_btn);
        }

        public void bind (Dog dog) {
            byte[] bytes = FileManager.stringToByteArray(dog.getImage());
            Bitmap bmp = FileManager.bitmapFromBytes(bytes);
            dogImageView.setImageBitmap(bmp);
            dogName.setText(dog.getName());
            dogBreed.setText(dog.getBreed());
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickedDelete(dog);
                }
            });

            showDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickedShowMore(dog);
                }
            });
        }
    }
}

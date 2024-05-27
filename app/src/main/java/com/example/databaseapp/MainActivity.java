package com.example.databaseapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView;
    ProfileAdapter profileAdapter;
    DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.textCount);

        ItemTouchHelper touchHelper = new ItemTouchHelper(simpleCallback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBaseAdapter = new DataBaseAdapter(MainActivity.this);
        dataBaseAdapter.Open();


        profileAdapter = new ProfileAdapter(MainActivity.this, dataBaseAdapter.profile());
        recyclerView.setAdapter(profileAdapter);
        textView.setText("найдено " + profileAdapter.getItemCount());
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:{
                    Profile deleteProfile = dataBaseAdapter.getSingleProfile((long) viewHolder.itemView.getTag());
                    new AlertDialog.Builder(MainActivity.this).setTitle("Удаление пользователя").setMessage("Удалить пользователя" + deleteProfile.name + "?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataBaseAdapter.delete((long) viewHolder.itemView.getTag());
                            onResume();
                            Snackbar.make(recyclerView, "Пользователь " + deleteProfile.name + " удален!", Snackbar.LENGTH_LONG).setAction("Востановить?", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dataBaseAdapter.insert(deleteProfile);
                                    onResume();
                                }
                            }).show();
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onResume();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
                    break;
                }

                case ItemTouchHelper.RIGHT:{
                    editProfileDatabase(recyclerView, (long) viewHolder.itemView.getTag());
                    onResume();
                    break;
                }
            }
        }
    };
    public void editProfileDatabase(View view, long id) {
        Dialog editDialog = new Dialog(this, R.style.Base_Theme_DataBaseApp);
        Objects.requireNonNull(editDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        editDialog.setContentView(R.layout.add_profile_dialog);

        EditText editTextSname = editDialog.findViewById(R.id.personeSname);
        EditText editTextName = editDialog.findViewById(R.id.personeName);
        EditText editTextAge = editDialog.findViewById(R.id.personeAge);


        Profile profile = dataBaseAdapter.getSingleProfile(id);
        editTextSname.setText(profile.sName);
        editTextName.setText(profile.name);
        editTextAge.setText(String.valueOf(profile.age));


        editDialog.findViewById(R.id.addProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextSname.getText().toString().isEmpty()){
                    editTextSname.setError("Поле пустое");
                    editTextSname.requestFocus();
                    return;
                }
                if(editTextName.getText().toString().isEmpty()){
                    editTextName.setError("Поле пустое");
                    editTextName.requestFocus();
                    return;
                }
                if(editTextAge.getText().toString().isEmpty()){
                    editTextAge.setError("Поле пустое");
                    editTextAge.requestFocus();
                    return;
                }

                String sname = editTextSname.getText().toString();
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());

                dataBaseAdapter.update(new Profile(id, sname, name, age, R.drawable.ic_launcher_background));
                Toast.makeText(MainActivity.this, "Данные успешно обновлены", Toast.LENGTH_SHORT).show();
                onResume();
                editDialog.hide();
            }
        });
        editDialog.show();
    }

    public void addProfileDatabase(View view) {
        Dialog dialog = new Dialog(this, R.style.Base_Theme_DataBaseApp);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
        dialog.setContentView(R.layout.add_profile_dialog);

        EditText editTextSname = dialog.findViewById(R.id.personeSname);
        EditText editTextName = dialog.findViewById(R.id.personeName);
        EditText editTextAge = dialog.findViewById(R.id.personeAge);

        dialog.findViewById(R.id.addProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextSname.getText().toString().isEmpty()){
                    editTextSname.setError("Поле пустое");
                    editTextSname.requestFocus();
                    return;
                }
                if(editTextName.getText().toString().isEmpty()){
                    editTextName.setError("Поле пустое");
                    editTextName.requestFocus();
                    return;
                }
                if(editTextAge.getText().toString().isEmpty()){
                    editTextAge.setError("Поле пустое");
                    editTextAge.requestFocus();
                    return;
                }

                String sname = editTextSname.getText().toString();
                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());

                dataBaseAdapter.insert(new Profile(sname, name, age, R.drawable.ic_launcher_background));
                Toast.makeText(MainActivity.this, "Данные успешно добавлены", Toast.LENGTH_SHORT).show();
                onResume();
                dialog.hide();
            }
        });
        dialog.show();
    }
}
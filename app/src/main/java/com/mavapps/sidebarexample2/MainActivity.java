package com.mavapps.sidebarexample2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    private LinearLayout fieldContainer;
    private Button btnAddField;
    private List<TextInputEditText> fieldList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_menu_24);
        toolbar.getNavigationIcon().setTint(Color.WHITE);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                showToast("Home  selected");
            } else if (itemId == R.id.nav_settings) {
                showToast("Settings selected");
            } else if (itemId == R.id.nav_about) {
                showToast("About selected");
            }
            drawerLayout.closeDrawers();
            return true;
        });


        fieldContainer = findViewById(R.id.field_container);
        btnAddField = findViewById(R.id.btn_add_field);
        btnAddField.setOnClickListener(v -> addField());
    }

    private void addField() {

        //eto yung magsisilbing lagayan ng mga textfield at x button
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        rowLayout.setPadding(0, 16, 0, 0);

        //sa may floating textbox
        //meron tayong 2 main parts
        //1.) Input Layout -> Eto yung container mismo ng TextInputEditText (Kung wala ito, walang box and walang floating label)
        TextInputLayout inputLayout = new TextInputLayout(this);
        inputLayout.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f));
        inputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
        inputLayout.setHint("Enter text");

        //2.) TextInputEditText -> Eto yung textbox mismo, plain lang siya
        TextInputEditText editText = new TextInputEditText(inputLayout.getContext());
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        inputLayout.addView(editText);

        //set mo narin ang x button
        ImageButton btnRemove = new ImageButton(this);
        btnRemove.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        btnRemove.setBackgroundColor(Color.TRANSPARENT);
        btnRemove.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        btnRemove.setOnClickListener(v -> {
            fieldContainer.removeView(rowLayout);
            fieldList.remove(editText);
        });


        //sa rowlayout i-add mo yung inputlayout and x button
        rowLayout.addView(inputLayout);
        rowLayout.addView(btnRemove);

        // Add to container
        fieldContainer.addView(rowLayout);
        fieldList.add(editText);
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }
}
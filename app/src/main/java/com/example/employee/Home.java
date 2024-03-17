package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employee.models.Employee;

import java.util.Locale;

public class Home extends AppCompatActivity {

    Button admin;
    Spinner spinner;
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        admin = findViewById(R.id.admin);
        spinner = findViewById(R.id.spinner);

        Intent intent = new Intent(this, EmployeeScreen.class);

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.layout_spinner, new String[]{"english", "العربية"});
        spinner.setAdapter(adapter);



        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("isAdmin",true);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        spinner.setSelection(isLanguageArabic()?1:0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 1 && !first) {
                    changeLanguage("ar","DZ",true);
                } else if (position == 0 && !first){
                    changeLanguage("en","US",false);
                    Toast.makeText(Home.this, "english", Toast.LENGTH_SHORT).show();
                }
                first = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void changeLanguage(String languageCode, String countryCode, boolean isArabic) {
        Locale locale;
        if (isArabic) {
            locale = new Locale(languageCode, countryCode);
        } else {
            // Set the default locale
            locale = Locale.getDefault();
        }

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Restart the activity to apply the language changes
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private boolean isLanguageArabic() {
        Locale currentLocale = getResources().getConfiguration().locale;
        return currentLocale.getLanguage().equals("ar");
    }


}
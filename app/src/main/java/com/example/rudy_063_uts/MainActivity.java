package com.example.rudy_063_uts;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editNIK;
    private EditText editNama;
    private EditText editTL;
    private EditText editAlamat;
    private EditText textUsia;
    private RadioGroup radioGroupKewarganegaraan;
    private CheckBox it, design, services, writing, finance;
    private EditText editEmail;
    private Button buttonReset;
    private Button buttonSubmit;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private TextView edittanggal;
    private Button pilih;
    Spinner spinner;

    private Calendar myCalendar;
    String[] bankNames={"Laki-Laki","Perempuan"};

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editNIK = findViewById(R.id.editNIK);
        editNama = findViewById(R.id.editNama);
        edittanggal = findViewById(R.id.edittanggal);
        editTL = findViewById(R.id.editTL);
        editAlamat = findViewById(R.id.editAlamat);
        textUsia = findViewById(R.id.editUsia);
        radioGroupKewarganegaraan = findViewById(R.id.radioGroupKewarganegaraan);
        it = findViewById(R.id.it);
        design = findViewById(R.id.design);
        services = findViewById(R.id.services);
        writing = findViewById(R.id.writing);
        finance = findViewById(R.id.finance);
        editEmail = findViewById(R.id.editEmail);
        buttonReset = findViewById(R.id.ButtonReset);
        buttonSubmit = findViewById(R.id.ButtonSubmit);

        spinner=(Spinner)findViewById(R.id.spinner);

        myCalendar = Calendar.getInstance();

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,bankNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        edittanggal = (TextView) findViewById(R.id.edittanggal);
        pilih = (Button) findViewById(R.id.pilih);
        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForm();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                edittanggal.setText(""+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    private void calculateAge() {
        // Get current date
        Calendar today = Calendar.getInstance();

        // Calculate age based on selected date of birth
        int age = today.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < myCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        // Set the calculated age to the EditText
        textUsia.setText(String.valueOf(age));
    }
    private void resetForm() {
        editNIK.setText("");
        editNama.setText("");
        edittanggal.setText("");
        editTL.setText("");
        editAlamat.setText("");
        textUsia.setText("");
        spinner.setSelection(0);
        radioGroupKewarganegaraan.clearCheck();
        it.setChecked(false);
        design.setChecked(false);
        writing.setChecked(false);
        finance.setChecked(false);
        editEmail.setText("");
    }
    private void submitForm() {
        // Mendapatkan data dari form
        String nik = editNIK.getText().toString();
        String nama = editNama.getText().toString();
        String tglLahir = edittanggal.getText().toString();
        String tempatLahir = editTL.getText().toString();
        String alamat = editAlamat.getText().toString();
        String usia = textUsia.getText().toString();
        String jenisKelamin = spinner.getSelectedItem().toString();
        String kewarganegaraan = "";

        int selectedId = radioGroupKewarganegaraan.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            kewarganegaraan = radioButton.getText().toString();
        }

        StringBuilder kompetensi = new StringBuilder();
        if (it.isChecked()) {
            kompetensi.append("Development & IT, ");
        }
        if (design.isChecked()) {
            kompetensi.append("Design Creative, ");
        }
        if (writing.isChecked()) {
            kompetensi.append("Writing, ");
        }
        if (finance.isChecked()) {
            kompetensi.append("Finance & Accounting");
        }

        String email = editEmail.getText().toString();

        // Validate form
        if (nik.isEmpty() || nama.isEmpty() || tglLahir.isEmpty() || tempatLahir.isEmpty() ||
                alamat.isEmpty() || usia.isEmpty() || jenisKelamin.isEmpty() || kewarganegaraan.isEmpty() ||
                kompetensi.toString().isEmpty() || email.isEmpty()) {
            Toast.makeText(MainActivity.this, "Harap lengkapi semua kolom", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate email format
        if (!email.endsWith("@gmail.com") && !email.endsWith("@mail.com")) {
            Toast.makeText(MainActivity.this, "Format email harus berakhir dengan @gmail.com atau @mail.com", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat intent untuk perpindahan data ke SecondActivity
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        // Menambahkan data ke intent
        intent.putExtra("NIK", nik);
        intent.putExtra("NAMA", nama);
        intent.putExtra("TANGGAL_LAHIR", tglLahir);
        intent.putExtra("TEMPAT_LAHIR", tempatLahir);
        intent.putExtra("ALAMAT", alamat);
        intent.putExtra("USIA", usia);
        intent.putExtra("JENIS_KELAMIN", jenisKelamin);
        intent.putExtra("KEWARGANEGARAAN", kewarganegaraan);
        intent.putExtra("KOMPETENSI", kompetensi.toString());
        intent.putExtra("EMAIL", email);

        // Memulai aktivitas SecondActivity dengan intent yang telah dibuat
        startActivity(intent);
    }


}
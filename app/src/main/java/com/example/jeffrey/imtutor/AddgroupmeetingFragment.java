package com.example.jeffrey.imtutor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddgroupmeetingFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    public static String month;
    public static String date;
    public static String year;
    public static String AMPM;
    public static String Hours;
    public static String Minutes;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addmeeting,container,false);
        Spinner newDateMonths = (Spinner)view.findViewById(R.id.spinnerMonths);
        Spinner newDateDates = (Spinner)view.findViewById(R.id.spinnerDates);
        Spinner newDateYears = (Spinner)view.findViewById(R.id.spinnerYears) ;
        Spinner newTimeAMPM = (Spinner)view.findViewById(R.id.spinnerAMPM);
        Spinner newTimeHours = (Spinner)view.findViewById(R.id.spinnerHours);
        Spinner newTimeMinutes = (Spinner)view.findViewById(R.id.spinnerMinutes) ;

        newDateMonths.setOnItemSelectedListener(this);
        newDateDates.setOnItemSelectedListener(this);
        newDateYears.setOnItemSelectedListener(this);
        newTimeAMPM.setOnItemSelectedListener(this);
        newTimeHours.setOnItemSelectedListener(this);
        newTimeMinutes.setOnItemSelectedListener(this);

        List<String> dataDateMonths = new ArrayList<String>();
        dataDateMonths.add("Jan");
        dataDateMonths.add("Feb");
        dataDateMonths.add("Mar");
        dataDateMonths.add("Apr");
        dataDateMonths.add("May");
        dataDateMonths.add("Jun");
        dataDateMonths.add("Jul");
        dataDateMonths.add("Aug");
        dataDateMonths.add("Sept");
        dataDateMonths.add("Oct");
        dataDateMonths.add("Nov");
        dataDateMonths.add("Dec");
        List<String> dataDateDates = new ArrayList<String>();
        for(int i = 1;i<32;i++){
            dataDateDates.add(Integer.toString(i));
        }
        List<String> dataDateYears = new ArrayList<String>();
        dataDateYears.add("2018");
        dataDateYears.add("2019");
        dataDateYears.add("2020");
        List<String> dataTimeAMPM = new ArrayList<String>();
        dataTimeAMPM.add("a.m.");
        dataTimeAMPM.add("p.m.");
        List<String> dataTimeHours = new ArrayList<String>();
        for(int i = 1;i<13;i++){
            dataTimeHours.add(Integer.toString(i));
        }
        List<String> dataTimeMinutes = new ArrayList<String>();
        dataTimeMinutes.add("00");
        dataTimeMinutes.add("15");
        dataTimeMinutes.add("30");
        dataTimeMinutes.add("45");



        ArrayAdapter<String> dataDateMonthsAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,dataDateMonths);
        ArrayAdapter<String> dataDateDatesAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,dataDateDates);
        ArrayAdapter<String> dataDateYearsAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,dataDateYears);
        ArrayAdapter<String> dataTimeAMPMAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,dataTimeAMPM);
        ArrayAdapter<String> dataTimeHoursAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,dataTimeHours);
        ArrayAdapter<String> dataTimeMinutesAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,dataTimeMinutes);
        dataDateMonthsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataDateDatesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataDateYearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataTimeAMPMAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataTimeHoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataTimeMinutesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        newDateMonths.setAdapter(dataDateMonthsAdapter);
        newDateDates.setAdapter(dataDateDatesAdapter);
        newDateYears.setAdapter(dataDateYearsAdapter);
        newTimeAMPM.setAdapter(dataTimeAMPMAdapter);
        newTimeHours.setAdapter(dataTimeHoursAdapter);
        newTimeMinutes.setAdapter(dataTimeMinutesAdapter);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        int ida = parent.getId();
        switch (ida){
            case R.id.spinnerMonths:
                month = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerDates:
                date = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerYears:
                year = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerAMPM:
                AMPM = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerHours:
                Hours = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerMinutes:
                Minutes = parent.getItemAtPosition(position).toString();
                break;
        }
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

package com.swarankar.Activity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.swarankar.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisternewActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtStepTitle;
    private CircleImageView imgUser;
    private Button btnChangeProfile;
    private EditText edtFname;
    private EditText edtLname;
    private EditText edtPassword;
    private Spinner spinnerSubcaste;
    private Spinner spinnerGotraSelf;
    private Spinner spinnerGotraMother;
    private Spinner spinnerMaritalstatus;
    private TextView edtDob;
    private Spinner spinnerGender;
    private Button part1BtnNext;



// <--Register Profession Details -->
    private Spinner spinnerProfession;
    private EditText edtOrganizationName;
    private Spinner spinnerStatus;
    private Spinner spinnerProfessionIndustry;
    private Spinner spinnerProfessionIndustrySubCat;
    private EditText edtDesignation;
    private EditText edtHouseno;
    private EditText edtArea;
    private EditText edtPincode;
    private Spinner spinnerCountry;
    private Spinner state;
    private Spinner district;
    private EditText edtOfficeNo;
    private Button professionDetailBtnPrevious;
    private Button professionDetailBtnNext;


    //    <--Register education Details -->
    private EditText edtEducationQualification;
    private EditText edtNameOfInstitute;
    private Spinner spinnerAreaStudy;
    private Spinner spinnerStatusEducation;
    private Button registerEducationDetailBtnPrevious;
    private Button registerEducationDetailBtnNext;


    //    <--Register residetial details-->
    private EditText residetialEdtHouseno;
    private EditText residetialEdtArea;
    private EditText residetialEdtWardno;
    private EditText residetialEdtConstituency;
    private EditText residetialEdtVillage;
    private EditText residetialEdtTehsil;
    private Spinner residetialSpinnerCountry;
    private Spinner residetialSpinnerState;
    private Spinner residetialSpinnerDistrict;
    private Button registerResidetialBtnPrevious;
    private Button registerResidetialBtnNext;



    //    <--Register petmanent details-->
    private EditText permanentEdtHouseno;
    private EditText permanentEdtArea;
    private EditText permanentEdtWardno;
    private EditText permanentEdtConstituency;
    private EditText permanentEdtVillage;
    private EditText permanentEdtTehsil;
    private Spinner permanentSpinnerCountry;
    private Spinner permanentSpinnerState;
    private Spinner permanentSpinnerDistrict;
    private Button registerPermanentBtnPrevious;
    private Button registerPermanentBtnNext;


    //    <--Register contact details-->
    private EditText EdtMobileNo;
    private EditText EdtMobileNo2;
    private EditText edtStdNo;
    private EditText edtEmail;
    private Button registerContactBtnPrevious;
    private Button registerContactBtnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registernew);
        findViews();
    }



    private void findViews() {
        txtStepTitle = (TextView)findViewById( R.id.txt_step_title );
        imgUser = (CircleImageView)findViewById( R.id.img_user );
        btnChangeProfile = (Button)findViewById( R.id.btn_change_profile );
        edtFname = (EditText)findViewById( R.id.edt_fname );
        edtLname = (EditText)findViewById( R.id.edt_lname );
        edtPassword = (EditText)findViewById( R.id.edt_password );
        spinnerSubcaste = (Spinner)findViewById( R.id.spinner_subcaste );
        spinnerGotraSelf = (Spinner)findViewById( R.id.spinner_gotra_self );
        spinnerGotraMother = (Spinner)findViewById( R.id.spinner_gotra_mother );
        spinnerMaritalstatus = (Spinner)findViewById( R.id.spinner_maritalstatus );
        edtDob = (TextView)findViewById( R.id.edt_dob );
        spinnerGender = (Spinner)findViewById( R.id.spinner_gender );
        part1BtnNext = (Button)findViewById( R.id.part1_btn_next );

        btnChangeProfile.setOnClickListener(this);
        part1BtnNext.setOnClickListener(this);



//    <--Register Profession Details -->

        spinnerProfession = (Spinner)findViewById( R.id.spinner_profession );
        edtOrganizationName = (EditText)findViewById( R.id.edt_organization_name );
        spinnerStatus = (Spinner)findViewById( R.id.spinner_status );
        spinnerProfessionIndustry = (Spinner)findViewById( R.id.spinner_profession_industry );
        spinnerProfessionIndustrySubCat = (Spinner)findViewById( R.id.spinner_profession_industry_sub_cat );
        edtDesignation = (EditText)findViewById( R.id.edt_designation );
        edtHouseno = (EditText)findViewById( R.id.edt_houseno );
        edtArea = (EditText)findViewById( R.id.edt_area );
        edtPincode = (EditText)findViewById( R.id.edt_pincode );
        spinnerCountry = (Spinner)findViewById( R.id.spinner_country );
        state = (Spinner)findViewById( R.id.state );
        district = (Spinner)findViewById( R.id.district );
        edtOfficeNo = (EditText)findViewById( R.id.edt_office_no );
        professionDetailBtnPrevious = (Button)findViewById( R.id.profession_detail_btn_previous );
        professionDetailBtnNext = (Button)findViewById( R.id.profession_detail_btn_next );

        professionDetailBtnPrevious.setOnClickListener( this );
        professionDetailBtnNext.setOnClickListener( this );


//    <--Register education Details -->
        edtEducationQualification = (EditText)findViewById( R.id.edt_education_qualification );
        edtNameOfInstitute = (EditText)findViewById( R.id.edt_name_of_institute );
        spinnerAreaStudy = (Spinner)findViewById( R.id.spinner_area_study );
        spinnerStatusEducation = (Spinner)findViewById( R.id.spinner_status_education );
        registerEducationDetailBtnPrevious = (Button)findViewById( R.id.register_education_detail_btn_previous );
        registerEducationDetailBtnNext = (Button)findViewById( R.id.register_education_detail_btn_next );

        registerEducationDetailBtnPrevious.setOnClickListener( this );
        registerEducationDetailBtnNext.setOnClickListener( this );

//    <--Register residetial details-->
        residetialEdtHouseno = (EditText)findViewById( R.id.residetial_edt_houseno );
        residetialEdtArea = (EditText)findViewById( R.id.residetial_edt_area );
        residetialEdtWardno = (EditText)findViewById( R.id.residetial_edt_wardno );
        residetialEdtConstituency = (EditText)findViewById( R.id.residetial_edt_constituency );
        residetialEdtVillage = (EditText)findViewById( R.id.residetial_edt_village );
        residetialEdtTehsil = (EditText)findViewById( R.id.residetial_edt_tehsil );
        residetialSpinnerCountry = (Spinner)findViewById( R.id.residetial_spinner_country );
        residetialSpinnerState = (Spinner)findViewById( R.id.residetial_spinner_state );
        residetialSpinnerDistrict = (Spinner)findViewById( R.id.residetial_spinner_district );
        registerResidetialBtnPrevious = (Button)findViewById( R.id.register_residetial_btn_previous );
        registerResidetialBtnNext = (Button)findViewById( R.id.register_residetial_btn_next );

        registerResidetialBtnPrevious.setOnClickListener( this );
        registerResidetialBtnNext.setOnClickListener( this );


//    <--Register permanent details-->
        permanentEdtHouseno = (EditText)findViewById( R.id.permanent_edt_houseno );
        permanentEdtArea = (EditText)findViewById( R.id.permanent_edt_area );
        permanentEdtWardno = (EditText)findViewById( R.id.permanent_edt_wardno );
        permanentEdtConstituency = (EditText)findViewById( R.id.permanent_edt_constituency );
        permanentEdtVillage = (EditText)findViewById( R.id.permanent_edt_village );
        permanentEdtTehsil = (EditText)findViewById( R.id.permanent_edt_tehsil );
        permanentSpinnerCountry = (Spinner)findViewById( R.id.permanent_spinner_country );
        permanentSpinnerState = (Spinner)findViewById( R.id.permanent_spinner_state );
        permanentSpinnerDistrict = (Spinner)findViewById( R.id.permanent_spinner_district );
        registerPermanentBtnPrevious = (Button)findViewById( R.id.register_permanent__btn_previous );
        registerPermanentBtnNext = (Button)findViewById( R.id.register_permanent__btn_next );

        registerPermanentBtnPrevious.setOnClickListener( this );
        registerPermanentBtnNext.setOnClickListener( this );

        //    <--Register contact details-->
        EdtMobileNo = (EditText)findViewById( R.id._edt_mobile_no );
        EdtMobileNo2 = (EditText)findViewById( R.id._edt_mobile_no_2 );
        edtStdNo = (EditText)findViewById( R.id.edt_std_no );
        edtEmail = (EditText)findViewById( R.id.edt_email );
        registerContactBtnPrevious = (Button)findViewById( R.id.register_contact_btn_previous );
        registerContactBtnRegister = (Button)findViewById( R.id.register_contact_btn_register );

        registerContactBtnPrevious.setOnClickListener( this );
        registerContactBtnRegister.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if ( v == btnChangeProfile ) {
            // Handle clicks for btnChangeProfile
        } else if ( v == part1BtnNext ) {
            // Handle clicks for part1BtnNext
        }
        else if ( v == professionDetailBtnPrevious ) {
            // Handle clicks for professionDetailBtnPrevious
        } else if ( v == professionDetailBtnNext ) {
            // Handle clicks for professionDetailBtnNext
        }
       else if ( v == registerEducationDetailBtnPrevious ) {
            // Handle clicks for registerEducationDetailBtnPrevious
        } else if ( v == registerEducationDetailBtnNext ) {
            // Handle clicks for registerEducationDetailBtnNext
        }
        else if ( v == registerResidetialBtnPrevious ) {
            // Handle clicks for registerResidetialBtnPrevious
        } else if ( v == registerResidetialBtnNext ) {
            // Handle clicks for registerResidetialBtnNext
        }
        else if ( v == registerPermanentBtnPrevious ) {
            // Handle clicks for registerPermanentBtnPrevious
        } else if ( v == registerPermanentBtnNext ) {
            // Handle clicks for registerPermanentBtnNext
        }
        else  if ( v == registerContactBtnPrevious ) {
            // Handle clicks for registerContactBtnPrevious
        } else if ( v == registerContactBtnRegister ) {
            // Handle clicks for registerContactBtnRegister
        }
    }

















}

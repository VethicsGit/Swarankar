package com.swarankar.Activity.Fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.swarankar.Activity.Activity.ChangePassword;
import com.swarankar.Activity.Activity.FamilyActivity;
import com.swarankar.Activity.Activity.FamilyListActivity;
import com.swarankar.Activity.Activity.HomeActivity;
import com.swarankar.Activity.Activity.MainActivity;
import com.swarankar.Activity.Model.ModelCity.ModelCity;
import com.swarankar.Activity.Model.ModelCountry.ModelCountry;
import com.swarankar.Activity.Model.ModelGotra.ModelGotra;
import com.swarankar.Activity.Model.ModelProfession.ModelProfessionSociety;
import com.swarankar.Activity.Model.ModelProfession.ModelSubProfessionCategory;
import com.swarankar.Activity.Model.ModelProfession.Profession;
import com.swarankar.Activity.Model.ModelState.ModelState;
import com.swarankar.Activity.Model.ProfileDetails.ModelProfileDetails;
import com.swarankar.Activity.Model.UpdateProfile.ModelUpadateProfile;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.cropper.CropImage;
import com.swarankar.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static com.swarankar.Activity.Utils.APIClient.BASE_URL;

/**
 * Created by softeaven on 8/19/17.
 */

public class Profile1Fragment extends Fragment implements View.OnClickListener {
    ImageView  arc,profile_ok_image, profession_ok_image, education_ok_image, address_ok_image, contact_ok_image, family_ok_image;
    CheckBox permenent_address_checkbox;

    private static final int RESULT_GALLERY = 0;
    private static final int CAMERA_REQUEST = 1;
    final int CROP_PIC = 2;
    private static final int MY_CAPTURE_REQUEST_CODE = 4;

    String uri;

    String ProfileImage = "";

    final String[] items = {"Camera", "Gallery"};
    AlertDialog.Builder builder;
    private Uri picUri;

    private String imgPath;
    private LinearLayout btnLayout;
    private RelativeLayout profileBtnPersonalDetails;
    private RelativeLayout profileBtnProfessionDetails;
    private RelativeLayout profileBtnEducationDetails;
    private RelativeLayout profileBtnAddressDetails;
    private RelativeLayout profileBtnContactDetails;
    private RelativeLayout profileBtnFamilyDetails;

    //    <--Persional details-->
    ImageView imgback;

    LinearLayout lvsubcastOther;
    EditText edSubcastOther;
    private CircleImageView imgUser;
    private TextView profileTxtName;
    private TextView profileTxtEmail;
    private Button profileBnEdit;
    private Button profileBtnChangePass;
    private LinearLayout lvPersionalDetails;
    private EditText profileEdtFistname;
    private EditText profileEdtLastName;
    private EditText mobileNo;
    private Spinner spinnerSubcaste;
    private TextView textView5;
    private Spinner spinnerGotraSelf;
    private Spinner spinnerGotraMother;
    private Spinner spinnerMaritalstatus;
    private TextView edtDob;
    private Spinner spinnerGender;

    private RadioButton radiomale;
    private RadioButton radioFemale;
    private RadioGroup rdGender;
    private Button btnSignOut;

    //    <--Professional details-->
    private LinearLayout lvPerofessionDetails;
    private Spinner spinnerProfession;
    private EditText edtOrganizationName;
    private Spinner spinnerProfessionStatus;
    private Spinner spinnerProfessionIndustry;
    private Spinner spinnerProfessionIndustrySubCat;
    private EditText edtDesignation;
    private EditText edtHouseno;
    private EditText edtArea;
    private EditText edtPincode;
    private Spinner spinnerCountry;
    private Spinner spinnerState;
    private Spinner spinnerDistrict;
    private EditText edtOfficeNo;
    LinearLayout lvOtherStatus;
    EditText edOtherStatus;
    LinearLayout lvOtherProfessionIndustry, lvSubProfessionCategory;
    EditText edOtherProfessionIndustry;

    //    <--Education details-->
    private LinearLayout lvEducationDetails;
    private EditText edtEducationQualification;
    private EditText edtNameOfInstitute;
    private Spinner spinnerAreaStudy;
    private Spinner spinnerStatusEducation;
    EditText edAreaStudyOther;
    LinearLayout lvAreaStudyOther;

    //    <--Residetial details-->
    private LinearLayout lvResidetialDetails;
    private EditText residetialEdtHouseno;
    private EditText residetialEdtArea;
    private EditText residetialEdtWardno;
    private EditText residetialEdtConstituency;
    private EditText residetialEdtVillage;
    private EditText residetialEdtTehsil;
    private Spinner residetialSpinnerCountry;
    private Spinner residetialSpinnerState;
    private Spinner residetialSpinnerDistrict;

    //    <--parmenent details-->
    private LinearLayout lvParmenentAddressDetails;
    private EditText parmenentEdtHouseno;
    private EditText parmenentEdtArea;
    private EditText parmenentEdtWardno;
    private EditText parmenentEdtConstituency;
    private EditText parmenentEdtVillage;
    private EditText parmenentEdtTehsil;
    private Spinner parmenentSpinnerCountry;
    private Spinner parmenentSpinnerState;
    private Spinner parmenentSpinnerDistrict;

    //    <--Contact details-->
    private LinearLayout lvContactDetails;
    private EditText EdtMobileNo1;
    private EditText EdtMobileNo2;
    private EditText edtStdNo;
    private EditText edtEmail;
    private Button btnContact;
//    private Button btnUpdate;

    Calendar myCalendar;
    int dobyear, dobmonth, dobday;
    TextView txtTitle;
    String dob;

    String strGotraSelf = "";
    String strGotraMother = "";
    String strMaritalStatus = "";
    String strGender = "";
    String strProfession = "", strProfessionothers = "";
    String strProfessionStatus = "";
    String strProfessionIndustry, strProfessionIndustryId;
    String strProfessionSubCat = "", strProfessionSubCatId;
    String strAreaStudy = "", strAreaStudyothers = "", strProfessionStatusothers = "", strSubCastOther = "";
    String strStatusEducation = "";
    ProgressDialog loading;
    List<ModelProfessionSociety> ProfessionIndustryList;

    public List<ModelCountry> CountryList = new ArrayList<>();
    public List<ModelState> StateList;
    public static List<String> CastList = new ArrayList<>();
    public List<ModelCity> CityList;
    public List<Profession> professionlist = new ArrayList<>();
    public List<String> gotraSelfList = new ArrayList<>();
    public List<String> gotraMotherList = new ArrayList<>();

    String strOCountryId, strOCountryName, strOStateId, strOStateName, strOCityName, strOCityId;
    String strPCountryId, strPCountryName, strPStateId, strPStateName, strPCityName, strPCityId;
    String strRCountryId, strRCountryName, strRStateId, strRStateName, strRCityName, strRCityId;
    String gender;
    String subcaste;
    LinearLayout lvTitle;
    String a = "0";



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);


        btnLayout = (LinearLayout) view.findViewById(R.id.profile_btn_layout);
        lvTitle = (LinearLayout) view.findViewById(R.id.layout_title);
        profileBtnPersonalDetails =  view.findViewById(R.id.profile_btn_personal_details);
        profileBtnProfessionDetails =  view.findViewById(R.id.profile_btn_Profession_details);
        profileBtnEducationDetails = view.findViewById(R.id.profile_btn_education_details);
        profileBtnAddressDetails =view.findViewById(R.id.profile_btn_address_details);
        profileBtnContactDetails =  view.findViewById(R.id.profile_btn_contact_details);
        profileBtnFamilyDetails =  view.findViewById(R.id.profile_btn_family_details);
        profileBtnPersonalDetails.setOnClickListener(this);
        profileBtnProfessionDetails.setOnClickListener(this);
        profileBtnEducationDetails.setOnClickListener(this);
        profileBtnAddressDetails.setOnClickListener(this);
        profileBtnContactDetails.setOnClickListener(this);
        profileBtnFamilyDetails.setOnClickListener(this);
//    <--Persional details-->
      /*  profile_ok_image = (ImageView) view.findViewById(R.id.personal_ok_image);*/
        lvsubcastOther = (LinearLayout) view.findViewById(R.id.lv_subcast_other);
        edSubcastOther = (EditText) view.findViewById(R.id.edt_subacast_other);
        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        imgUser = (CircleImageView) view.findViewById(R.id.img_user);
        imgUser.setEnabled(true);
        profileTxtName = (TextView) view.findViewById(R.id.profile_txt_name);
        profileTxtEmail = (TextView) view.findViewById(R.id.profile_txt_email);
        profileBnEdit = (Button) view.findViewById(R.id.profile_bn_edit);
        profileBtnChangePass = (Button) view.findViewById(R.id.profile_btn_change_pass);
        lvPersionalDetails = (LinearLayout) view.findViewById(R.id.lv_persional_details);
        profileEdtFistname = (EditText) view.findViewById(R.id.profile_edt_fistname);
        profileEdtLastName = (EditText) view.findViewById(R.id.profile_edt_lastName);
        mobileNo = (EditText) view.findViewById(R.id.mobile_no);
        spinnerSubcaste = (Spinner) view.findViewById(R.id.spinner_subcaste);
        spinnerSubcaste.setEnabled(false);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        spinnerGotraSelf = (Spinner) view.findViewById(R.id.spinner_gotra_self);
        spinnerGotraSelf.setEnabled(false);
        spinnerGotraMother = (Spinner) view.findViewById(R.id.spinner_gotra_mother);
        spinnerGotraMother.setEnabled(false);
        spinnerMaritalstatus = (Spinner) view.findViewById(R.id.spinner_maritalstatus);
        spinnerMaritalstatus.setEnabled(false);
        edtDob = (TextView) view.findViewById(R.id.edt_dob);
        imgback = (ImageView) view.findViewById(R.id.img_profile_back);
//        spinnerGender = (Spinner)view.findViewById( R.id.spinner_gender );
//        spinnerGender.setEnabled(false);
//        btnPersonal = (Button)view.findViewById( R.id.btn_personal );
//        btnPersonal.setBackgroundResource(R.drawable.gradient_round1);
        btnSignOut = (Button) view.findViewById(R.id.btn_sign_out);
        rdGender = (RadioGroup) view.findViewById(R.id.RGgender);
        radioFemale = (RadioButton) view.findViewById(R.id.radio_female);
        radiomale = (RadioButton) view.findViewById(R.id.radiomale);

      /*  profileBnEdit.setOnClickListener(this);
        profileBtnChangePass.setOnClickListener(this);*/
        ProfessionIndustryList = new ArrayList<>();
        btnSignOut.setOnClickListener(this);
        edtDob.setOnClickListener(this);
        radiomale.setOnClickListener(this);
        radioFemale.setOnClickListener(this);
        imgUser.setOnClickListener(this);

        Glide.with(getContext()).load("https://upload.wikimedia.org/wikipedia/commons/f/fc/Nemer_Saade_Profile_Picture.jpg").into(imgUser);
        //    <--Professional details-->
      /*  profession_ok_image = (ImageView) view.findViewById(R.id.professional_ok_image);*/
        lvOtherStatus = (LinearLayout) view.findViewById(R.id.lv_profession_status_other);
        edOtherStatus = (EditText) view.findViewById(R.id.edt_profession_status_other);
        lvOtherProfessionIndustry = (LinearLayout) view.findViewById(R.id.lv_profession_other);
        lvSubProfessionCategory = (LinearLayout) view.findViewById(R.id.layout_spinner_sub_profession_industry);
        edOtherProfessionIndustry = (EditText) view.findViewById(R.id.edt_profession_other);
        lvPerofessionDetails = (LinearLayout) view.findViewById(R.id.lv_perofession_details);
        spinnerProfession = (Spinner) view.findViewById(R.id.spinner_profession);
        spinnerProfession.setEnabled(false);
        edtOrganizationName = (EditText) view.findViewById(R.id.edt_organization_name);
        spinnerProfessionStatus = (Spinner) view.findViewById(R.id.spinner_status);
        spinnerProfessionStatus.setEnabled(false);
        spinnerProfessionIndustry = (Spinner) view.findViewById(R.id.spinner_profession_industry);
        spinnerProfessionIndustry.setEnabled(false);
        spinnerProfessionIndustrySubCat = (Spinner) view.findViewById(R.id.spinner_sub_profession_industry);
        spinnerProfessionIndustrySubCat.setEnabled(false);
        edtDesignation = (EditText) view.findViewById(R.id.edt_designation);
        edtHouseno = (EditText) view.findViewById(R.id.edt_houseno);
        edtArea = (EditText) view.findViewById(R.id.edt_area);
        edtPincode = (EditText) view.findViewById(R.id.edt_pincode);
        spinnerCountry = (Spinner) view.findViewById(R.id.spinner_country);
        spinnerCountry.setEnabled(false);
        spinnerState = (Spinner) view.findViewById(R.id.spinner_state);
        spinnerState.setEnabled(false);
        spinnerDistrict = (Spinner) view.findViewById(R.id.spinner_district);
        spinnerDistrict.setEnabled(false);
        edtOfficeNo = (EditText) view.findViewById(R.id.edt_office_no);

//        professionDetailBtnPrevious = (Button)view.findViewById( R.id.profession_detail_btn_previous );
//        btnProfesional = (Button)view.findViewById( R.id.btn_professional_details );

//        professionDetailBtnPrevious.setOnClickListener( this );

        //    <--Education details-->
       /* education_ok_image = (ImageView) view.findViewById(R.id.education_ok_image);*/
        lvEducationDetails = (LinearLayout) view.findViewById(R.id.lv_education_details1);
        edAreaStudyOther = (EditText) view.findViewById(R.id.edt_area_study_other);
        lvAreaStudyOther = (LinearLayout) view.findViewById(R.id.lv_area_study_other);
        edtEducationQualification = (EditText) view.findViewById(R.id.edt_education_qualification);
        edtNameOfInstitute = (EditText) view.findViewById(R.id.edt_name_of_institute);
        spinnerAreaStudy = (Spinner) view.findViewById(R.id.spinner_area_study);
        spinnerAreaStudy.setEnabled(false);
        spinnerStatusEducation = (Spinner) view.findViewById(R.id.spinner_status_education);
        spinnerStatusEducation.setEnabled(false);
//
//        btnEducation = (Button)view.findViewById( R.id.btn_education );

        //    <--Residetial details-->
       /* address_ok_image = (ImageView) view.findViewById(R.id.address_ok_image);*/
        lvResidetialDetails = (LinearLayout) view.findViewById(R.id.lv_residetial_details);
        residetialEdtHouseno = (EditText) view.findViewById(R.id.residetial_edt_houseno);
        residetialEdtArea = (EditText) view.findViewById(R.id.residetial_edt_area);
        residetialEdtWardno = (EditText) view.findViewById(R.id.residetial_edt_wardno);
        residetialEdtConstituency = (EditText) view.findViewById(R.id.residetial_edt_constituency);
        residetialEdtVillage = (EditText) view.findViewById(R.id.residetial_edt_village);
        residetialEdtTehsil = (EditText) view.findViewById(R.id.residetial_edt_tehsil);
        residetialSpinnerCountry = (Spinner) view.findViewById(R.id.residetial_spinner_country);
        residetialSpinnerCountry.setEnabled(false);
        residetialSpinnerState = (Spinner) view.findViewById(R.id.residetial_spinner_state);
        residetialSpinnerState.setEnabled(false);
        residetialSpinnerDistrict = (Spinner) view.findViewById(R.id.residetial_spinner_district);
        residetialSpinnerDistrict.setEnabled(false);

//        btnResidetial = (Button)view.findViewById( R.id.btn_residetial_address );

        //    <--parmenent details-->
        permenent_address_checkbox = (CheckBox) view.findViewById(R.id.permenent_address_checkbox);
        permenent_address_checkbox.setEnabled(false);
        lvParmenentAddressDetails = (LinearLayout) view.findViewById(R.id.lv_parmenent_address_details);
        parmenentEdtHouseno = (EditText) view.findViewById(R.id.parmenent_edt_houseno);
        parmenentEdtArea = (EditText) view.findViewById(R.id.parmenent_edt_area);
        parmenentEdtWardno = (EditText) view.findViewById(R.id.parmenent_edt_wardno);
        parmenentEdtConstituency = (EditText) view.findViewById(R.id.parmenent_edt_constituency);
        parmenentEdtVillage = (EditText) view.findViewById(R.id.parmenent_edt_village);
        parmenentEdtTehsil = (EditText) view.findViewById(R.id.parmenent_edt_tehsil);
        parmenentSpinnerCountry = (Spinner) view.findViewById(R.id.parmenent_spinner_country);
        parmenentSpinnerCountry.setEnabled(false);
        parmenentSpinnerState = (Spinner) view.findViewById(R.id.parmenent_spinner_state);
        parmenentSpinnerState.setEnabled(false);
        parmenentSpinnerDistrict = (Spinner) view.findViewById(R.id.parmenent_spinner_district);
        parmenentSpinnerDistrict.setEnabled(false);
        //spinnerProfessionIndustrySubCat.setVisibility(View.GONE);
//        btnParmenentAddress = (Button)view.findViewById( R.id.btn_parmenent_address );

        //    <--Contact details-->
       /* contact_ok_image = (ImageView) view.findViewById(R.id.contact_ok_image);*/
        lvContactDetails = (LinearLayout) view.findViewById(R.id.lv_contact_details);
        EdtMobileNo1 = (EditText) view.findViewById(R.id._edt_mobile_no_1);
        EdtMobileNo2 = (EditText) view.findViewById(R.id._edt_mobile_no_2);
        edtStdNo = (EditText) view.findViewById(R.id.edt_std_no);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
//        btnContact = (Button)view.findViewById( R.id.btn_contact_details );
//        btnUpdate = (Button) view.findViewById(R.id.btn_update);

  /*      profileBnEdit.setVisibility(View.GONE);*/
//        btnUpdate.setVisibility(View.GONE);

//        btnContact.setOnClickListener(this);
//        btnUpdate.setOnClickListener(this);
        permenent_address_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    parmenentEdtHouseno.setText(residetialEdtHouseno.getText().toString());
                    parmenentEdtArea.setText(residetialEdtArea.getText().toString());
                    parmenentEdtWardno.setText(residetialEdtWardno.getText().toString());
                    parmenentEdtConstituency.setText(residetialEdtConstituency.getText().toString());
                    parmenentEdtVillage.setText(residetialEdtVillage.getText().toString());
                    parmenentEdtTehsil.setText(residetialEdtTehsil.getText().toString());
                    strPCountryId = strRCountryId;
                    strPCountryName = strRCountryName;
                    strPStateId = strRStateId;
                    strPStateName = strRStateName;
                    strPCityName = strRCityName;
                    strPCityId = strRCityId;

                    getPCountry(strPCountryName, strPStateName, strPCityName);

                    parmenentEdtHouseno.setEnabled(false);
                    parmenentEdtArea.setEnabled(false);
                    parmenentEdtWardno.setEnabled(false);
                    parmenentEdtConstituency.setEnabled(false);
                    parmenentEdtVillage.setEnabled(false);
                    parmenentEdtTehsil.setEnabled(false);
                    parmenentSpinnerCountry.setEnabled(false);
                    parmenentSpinnerState.setEnabled(false);
                    parmenentSpinnerDistrict.setEnabled(false);
                } else {
                    parmenentEdtHouseno.setEnabled(true);
                    parmenentEdtArea.setEnabled(true);
                    parmenentEdtWardno.setEnabled(true);
                    parmenentEdtConstituency.setEnabled(true);
                    parmenentEdtVillage.setEnabled(true);
                    parmenentEdtTehsil.setEnabled(true);
                    parmenentSpinnerCountry.setEnabled(true);
                    parmenentSpinnerState.setEnabled(true);
                    parmenentSpinnerDistrict.setEnabled(true);
                }
            }
        });
//        ProfileImage = "iVBORw0KGgoAAAANSUhEUgAABDgAAAeACAIAAACkA3BdAAAAA3NCSVQICAjb4U/gAAAgAElEQVR4nOzdd3gc1cH3/XNmZpt21axmucnGNm7BBmxKKMGhlziQG/IQSELyQG7S6CUhIQS4gUAeUsgd4E1xQoeE0AI2JWA6LrjbuOMm2ZKsYrWVdnfaef9Ye72WZFm2tNqV9P1cvnTNzs6ec2Yk2/PTKSNfffVV0RmlVLuX8T1qL9d1XdeNbziOo5RyHMd13bUbP99asaO2vqGppaXTkgEAAAAMDMFAoKhgyIjSocd8YZKWRNf1+IaUUtM0IUR8WwiR/DUueTvB6E71Bwot8e1EXInFzHfnL1y5Zv3hniYAAACA/iQWM3c3Nm3YvLWismrmF4/Pz8tN9GpIKZVSyV+FEImXBy1Z62YLkrtTEnvavXxvwSJSCgAAADAIbdyy7e2P5idnhOSvHSXv7/SYzoPKgYpLPqBdp8qaDZ+v+GxdN04BAAAAwAC0tXzHouWrOqaUrhPLgRy8R6U7476EEFvKyw+pYgAAAAADzJbtFclz2jv2rnQ/tHR36Fdcuw6a5MRSW99wSEUBAAAAGGB21dWJDoHkUPtS4roVVNpV0C6ixL+yxhcAAAAwyJmmFYlEk/ccXkoRh9qj0qnDrhsAAADAgNTzjHCYQaVdjwoAAAAAJOthUuiFHhUAAAAA6F0EFQAAAAAZh6ACAAAAIOMQVAAAAABkHIIKAAAAgIxDUAEAAACQcQgqAAAAADIOQQUAAABAxiGoAAAAAMg4BBUAAAAAGYegAgAAACDjEFQAAAAAZByCCgAAAICMQ1ABAAAAkHEIKgAAAAAyDkEFAAAAQMYhqAAAAADIOAQVAAAAABmHoAIAAAAg4xBUAAAAAGQcggoAAACAjENQAQAAAJBxCCoAAAAAMg5BBQAAAEDGIagAAAAAyDgEFQAAAAAZh6ACAAAAIOMQVAAAAABkHIIKAAAAgIxDUAEAAACQcQgqAAAAADIOQQUAAABAxiGoAAAAAMg4BBUAAAAAGYegAgAAACDjEFQAAAAAZByCCgAAAICMQ1ABAAAAkHEIKgAAAAAyjpHuBmAQ0TVNapqmSV3TZdJXTZNCCNdVjusoV7l7Oa6rXNdx3XQ3HAAAAH2NoILU0jTN6/F4vR6vxyu1rnrwNE0Ynf1AKqVMy7RMyzRNQgsAAMAgQVBBSui67vV6vR6P1+vtYVFSSp/X5/P6hBCWFQ8slm3bvdFMAAAAZCiCCnqTYRg+r9fr9RpGSn60PB6Px+MJZgnHsc09vSxWKioCAABAehFU0Dt0XQ9mBXw+f19VZwQCRiAQiJmxtrYIHSwAAAADDEEFPaVpWlYgEAgE0lJ7fFRYLBZtbYs4jpOWNgAAAKDXEVRw+KSU8YgipUxvS3w+v8/nj0QibZGIy4R7AACA/o+ggsMUCASCgUDXC3n1sUAg4PfviStKqXQ3BwAAAIePoIJDZhhGTnZI1zPxh0dKmZWVFfD7m8MtzLMHAADovzLo1+HoF/w+X15ubmamlASpabk5uemaNgMAAICey+jbTWSarKxAMCuY7lZ0VygY1DUt3Nqa7oYAAADgkBFU0F3ZoZDf30erD/eWQCCg6Vo43MoMewAAgP6FoV84OF3T8nJz+11KifN5fXm5OSl6ACUAAABShKCCg/B4PHl5uR6PJ90NOXy6buTl5vq83nQ3BAAAAN1FUEFXPB5PXm6upunpbkhPSSlzcnL8Pl+6GwIAAIBuIajggHRNy8kOpbsVvSk7O7tfdw0BAAAMHgQVHFB2dvYA6EtpJzsU1DLpIZUAAADoFHds6Fx2KDQgOx903cgODahuIgAAgAGJoIJOZGUF+ukaX93h9XrJKgAAABmOoIL2/D5fP3qq4+Hx+/08tx4AACCTEVSwH8MwQoOjtyEUDLJgMQAAQMbiKXiH6Zqrr/qvWRcMH1ba6yXvrKx66bW5D//lb71ecnfkZIeklGmpuu9lZ2dbDQ08tB4AACADEVQOxyvPPjlpwvgUFT58WOm13//emTNPu+jyK1JUxYEEAgFdH0Q/ElLKrEAg3Nqa7oYAAACgPYZ+HbJrv/+91KWUhEkTxl/7/e+lupZkUsrg4Ju2EQgEdH2gLcEMAAAwAEjTNDvuVUolf23HcRzHcVzXdRzHsiyllG3bX/+/V4fDg+I30+++9lIqRnx1tLOy6vRZ/9UHFcUFs7KysrL6rLrMEYtFm1vC6W4FAADAAPHcXx/Nz8/TNE3XdcMw9L2klJqmyf0JIZK/JqNH5ZD1TUrpy4qEEJqmDdpVsHw+v2EMogFvAAAA/QJBBUIIkRUIDJ459B1lZQ3SkAYAAJCx+EVyL5gw/Yu9Us6GpQt6pZxDpev6oO1OifN5fV5v1DStdDcEAAAAe9CjAhGkP0GIrMBgnJ8DAACQsQgqg51hGD6fP92tSD+Px8PzHwEAADIHQ78Gu7TcnRsBX/GkI4snjTcCfsPvyy4tEUK0VO2yozE7Eq1Zt6lm3UY7EuvjVvl83lhni+ABAACg7xFUBjtv3waVosnjy046Ln/MqI5vxeOKEKJo8pFTxAUNW8u3z19cu3ZTn7XN6/UJwTrFAAAAGYGgMqjFF7fum7ryx4wae8YpnUaUAx2fP2ZUw9byzfM+bthantK2xUkpvV4PU+oBAAAyAUGlcx0X4Oqtpb0OT4ra02fdKVMuvmDYsUcdxgfzx4ya8b3Ly+cv3jB3Xq+3qiOvx0tQAQAAyAQElUHN6/Gkugoj4Dv6mxd3vyOlU6NOOi67tGTFMy+meuKK1+sRrSmtAQAAAN3Cql+Dl6Zpqe5RMQK+GVdd3sOUEpc/ZtSMqy43Ar6eF9UFXTd4Sj0AAEAmIKgMXn3QnXL0Ny9OTJHvuezSkqO/eXFvlXYgXm/KLwsAAAAOiqAyeKX6jnzKxRf0Sl9KsvwxoyZccGbvltmO18PTVAAAANKPoDJ4pfSOPH/MqMObPX9Qo06a0ev5J5nH49E1/l4AAACk2aAejp9pS3sdnsM7C13TZCpvx8eecUpKC18y+9nUla/puuO6qSsfAAAAB8VvjgeplKaUosnjU9rpkT9mVNHk8akrX5MydYUDAIDMNGnC+J/ffENvlXb7LTdMmpDC25XBgKAySGlaCu/Fy046PnWFxw07dmrqCtd0PXWFAwCADDRpwvgn//zody6/9P67ftHz0h64+44rLrv0yT8/SlbpCYLKIKVrqboXNwK+/DEjU1R4QvGk8albqpgeFQAABpV4SsnJDgkh/mvWBT3MKg/cfcfXvnK+ECInO0RW6QmCyiAlU9ajUjzpyBSV3GcVaUymBwBg0EhOKXE9ySqJlBJHVukJbsgGqdT1qBRN7qOgkrqKCCoAAAwe/zXrguSUkth5GFmlXUqJy8kO/desCw6/fYPYoF71azBLXY+Kx+9PUcnt5A7JnzwhJVnFdpyWlpZUlAwAQL+was3adDeh79z3m4eys7M7Box4uvjZXfd2s5xOU4oQ4uU5r9/3m4d62MjBid8cD1IpnKPi76MHJmreVMVsPZUrDQAAgExz2533vDzn9Y77u9+v0kVKue3Oe3ravsGKoDJIpW7Vr+zSkhSV3I4nPy9FJUvJ3wsAAAaXnmQVUkqKcEMGdEKy8BcAAIPM4WUVUkrqEFQGKddVKSq5pbomRSW3YzU0pahkpVylUnV9AABAxjrUrEJKSSmCyiDluE6KSrYjsRSV3I5rWqkqmZQCAMBg1f2sQkpJNVb9GqRUynpUrGg0RSW307S7Ye2Gjako2TTNpubmVJQMAAAyXzxmdL0OGCmlDxBUBinXdVNUcu3ajcWT+uKpRrVrU5JShBBKperiAACAfqHrrCKlJKX0AYLKIJW6oFKzbuMU0RdPNapZl6qg4jgEFQAABruus0pHpJRexxyVQSp1QcWOxBq2VqSo8ISadZtSNxmGOSoAAEAceL5KR6SUVCCoDFJOyoKKEGL7/E9TV3hc5bJVqStcpfLiAACAfqQ7WYWUkiIElUEq";
//
//
//        getData();

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile1Fragment m3 = new Profile1Fragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(m3);
                trans.commit();
                manager.popBackStack();
                FragmentManager fragmentManager = getFragmentManager();

                String backStateName1 = m3.getClass().getName();
                if (!fragmentManager.popBackStackImmediate(backStateName1, 0)) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, m3, HomeActivity.TAG_FRAGMENT);
                    ft.addToBackStack(backStateName1);
                    ft.commit();
                }

                /*if (!isEmpty()) {*/
                btnSignOut.setVisibility(View.GONE);
                btnLayout.setVisibility(View.VISIBLE);
                lvPersionalDetails.setVisibility(View.GONE);
                lvPerofessionDetails.setVisibility(View.GONE);
                lvResidetialDetails.setVisibility(View.GONE);
                lvEducationDetails.setVisibility(View.GONE);
                lvParmenentAddressDetails.setVisibility(View.GONE);
                lvContactDetails.setVisibility(View.GONE);

               /* profileBnEdit.setVisibility(View.GONE);*/
//                btnUpdate.setVisibility(View.GONE);
                lvTitle.setVisibility(View.GONE);
                Disable();
                /*} else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setCancelable(false);
                    dialog.setTitle("Swarankar");
                    dialog.setMessage("Your number is not valid number,Are you want to change it?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            btnLayout.setVisibility(View.VISIBLE);
                            lvPersionalDetails.setVisibility(View.GONE);
                            lvPerofessionDetails.setVisibility(View.GONE);
                            lvResidetialDetails.setVisibility(View.GONE);
                            lvEducationDetails.setVisibility(View.GONE);
                            lvParmenentAddressDetails.setVisibility(View.GONE);
                            lvContactDetails.setVisibility(View.GONE);

                            profileBnEdit.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.GONE);
                            lvTitle.setVisibility(View.GONE);
                            Disable();
                        }
                    });
                    dialog.create().show();
                }*/

            }
        });

//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener()
//        {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//
//                if(btnLayout.getVisibility() == View.GONE) {
//
//                    btnLayout.setVisibility(View.VISIBLE);
//                    lvPersionalDetails.setVisibility(View.GONE);
//                    lvPerofessionDetails.setVisibility(View.GONE);
//                    lvResidetialDetails.setVisibility(View.GONE);
//                    lvEducationDetails.setVisibility(View.GONE);
//                    lvParmenentAddressDetails.setVisibility(View.GONE);
//                    lvContactDetails.setVisibility(View.GONE);
//
//                    profileBnEdit.setVisibility(View.GONE);
//                    btnUpdate.setVisibility(View.GONE);
//                    lvTitle.setVisibility(View.GONE);
//
//                    Disable();
//                    return true;
//                }
//                else {
//                    return false;
//                }
//            }
//
//
//        } );
        return view;

    }

    public boolean allowBackPressed() {
        if (btnLayout.getVisibility() == View.GONE) {
           /* if (!isEmpty()) {*/
            btnLayout.setVisibility(View.VISIBLE);
            lvPersionalDetails.setVisibility(View.GONE);
            lvPerofessionDetails.setVisibility(View.GONE);
            lvResidetialDetails.setVisibility(View.GONE);
            lvEducationDetails.setVisibility(View.GONE);
            lvParmenentAddressDetails.setVisibility(View.GONE);
            lvContactDetails.setVisibility(View.GONE);

         /*   profileBnEdit.setVisibility(View.GONE);*/
//            btnUpdate.setVisibility(View.GONE);
            lvTitle.setVisibility(View.GONE);

            Disable();
            return true;
            /*} else {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(false);
                dialog.setTitle("Swarankar");
                dialog.setMessage("You number is not valid number,Are you want to change it?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        a = "1";
                        dialog.dismiss();

                    }
                }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        btnLayout.setVisibility(View.VISIBLE);
                        lvPersionalDetails.setVisibility(View.GONE);
                        lvPerofessionDetails.setVisibility(View.GONE);
                        lvResidetialDetails.setVisibility(View.GONE);
                        lvEducationDetails.setVisibility(View.GONE);
                        lvParmenentAddressDetails.setVisibility(View.GONE);
                        lvContactDetails.setVisibility(View.GONE);

                        profileBnEdit.setVisibility(View.GONE);
                        btnUpdate.setVisibility(View.GONE);
                        lvTitle.setVisibility(View.GONE);
                        Disable();

                    }
                });
                dialog.create().show();
                if (a.equalsIgnoreCase("1")) {
                    return false;
                } else {
                    return true;
                }
            }*/
        } else {
            return false;
        }
    }

    private void Disable() {

        imgUser.setEnabled(true);
      /*  profileTxtName.setEnabled(false);
        profileTxtEmail.setEnabled(false);*/
//        profileEdtFistname.setEnabled(false);
        profileEdtLastName.setEnabled(false);
        mobileNo.setEnabled(false);
        spinnerSubcaste.setEnabled(false);
        spinnerGotraSelf.setEnabled(false);
        spinnerGotraMother.setEnabled(false);
        spinnerMaritalstatus.setEnabled(false);
        edtDob.setEnabled(false);
        edSubcastOther.setEnabled(false);
        radioFemale.setEnabled(false);
        radiomale.setEnabled(false);

//            spinnerGender.setEnabled(true);

        //    <--Professional details-->

        spinnerProfession.setEnabled(false);
        edtOrganizationName.setEnabled(false);
        spinnerProfessionStatus.setEnabled(false);
        spinnerProfessionIndustry.setEnabled(false);
        spinnerProfessionIndustrySubCat.setEnabled(false);
        edtDesignation.setEnabled(false);
        edtHouseno.setEnabled(false);
        edtArea.setEnabled(false);
        edtPincode.setEnabled(false);
        spinnerCountry.setEnabled(false);
        spinnerState.setEnabled(false);
        spinnerDistrict.setEnabled(false);
        edtOfficeNo.setEnabled(false);
        edOtherProfessionIndustry.setEnabled(false);
        edOtherStatus.setEnabled(false);

        //    <--Education details-->
        edtEducationQualification.setEnabled(false);
        edtNameOfInstitute.setEnabled(false);
        spinnerAreaStudy.setEnabled(false);
        spinnerStatusEducation.setEnabled(false);
        edAreaStudyOther.setEnabled(false);

        //    <--Residetial details-->
        residetialEdtHouseno.setEnabled(false);
        residetialEdtArea.setEnabled(false);
        residetialEdtWardno.setEnabled(false);
        residetialEdtConstituency.setEnabled(false);
        residetialEdtVillage.setEnabled(false);
        residetialEdtTehsil.setEnabled(false);
        residetialSpinnerCountry.setEnabled(false);
        residetialSpinnerState.setEnabled(false);
        residetialSpinnerDistrict.setEnabled(false);

        //    <--parmenent details-->
        permenent_address_checkbox.setEnabled(false);
        parmenentEdtHouseno.setEnabled(false);
        parmenentEdtArea.setEnabled(false);
        parmenentEdtWardno.setEnabled(false);
        parmenentEdtConstituency.setEnabled(false);
        parmenentEdtVillage.setEnabled(false);
        parmenentEdtTehsil.setEnabled(false);
        parmenentSpinnerCountry.setEnabled(false);
        parmenentSpinnerState.setEnabled(false);
        parmenentSpinnerDistrict.setEnabled(false);

        //    <--Contact details-->

        EdtMobileNo1.setEnabled(false);
        EdtMobileNo2.setEnabled(false);
        edtStdNo.setEnabled(false);
    }

    private void getData() {
        loading = new ProgressDialog(getActivity());
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        Call<ModelProfileDetails> call1 = apiService.userInfo1(strUserid);
        call1.enqueue(new Callback<ModelProfileDetails>() {
            @Override
            public void onResponse(Call<ModelProfileDetails> call, final Response<ModelProfileDetails> response) {
                Log.e("profile Response", response.body().toString());
                Log.e("organization", response.body().getOrganization());
                Log.e("profession industry", response.body().getProfessionIndustry());
                Log.e("sub profession industry", response.body().getSubCategory());

                profileTxtName.setText(response.body().getFirstname() + " " + response.body().getLastname());
                profileTxtEmail.setText(AndroidUtils.wordFirstCap(response.body().getEmail()));

                profileEdtFistname.setText(AndroidUtils.wordFirstCap(response.body().getFirstname()));
                profileEdtLastName.setText(AndroidUtils.wordFirstCap(response.body().getLastname()));
                edtEmail.setText(AndroidUtils.wordFirstCap(response.body().getEmail()));
                mobileNo.setText(AndroidUtils.wordFirstCap(response.body().getMobile()));
                if (response.body().getOtherSubcast() != null || !response.body().getOtherSubcast().equals("") || response.body().getOtherSubcast().equalsIgnoreCase("null"))
                    edSubcastOther.setText(AndroidUtils.wordFirstCap(response.body().getOtherSubcast()));
                edtOrganizationName.setText(AndroidUtils.wordFirstCap(response.body().getOrganization()));
                edtDesignation.setText(AndroidUtils.wordFirstCap(response.body().getDesignation()));
                edtHouseno.setText(AndroidUtils.wordFirstCap(response.body().getHouseNo()));
                edtArea.setText(AndroidUtils.wordFirstCap(response.body().getArea()));
                edtPincode.setText(AndroidUtils.wordFirstCap(response.body().getPincode()));
                edtOfficeNo.setText(AndroidUtils.wordFirstCap(response.body().getContactno()));
                edtEducationQualification.setText(AndroidUtils.wordFirstCap(response.body().getEducationalQual()));
                edtNameOfInstitute.setText(AndroidUtils.wordFirstCap(response.body().getInstitution()));
                residetialEdtHouseno.setText(AndroidUtils.wordFirstCap(response.body().getRHouseNo()));
                residetialEdtArea.setText(AndroidUtils.wordFirstCap(response.body().getRArea()));
                residetialEdtWardno.setText(AndroidUtils.wordFirstCap(response.body().getRWardNo()));
                residetialEdtConstituency.setText(AndroidUtils.wordFirstCap(response.body().getRConstituency()));
                residetialEdtVillage.setText(AndroidUtils.wordFirstCap(response.body().getRVillage()));
                residetialEdtTehsil.setText(AndroidUtils.wordFirstCap(response.body().getRTehsil()));

                parmenentEdtHouseno.setText(AndroidUtils.wordFirstCap(response.body().getPHouseNo()));
                parmenentEdtArea.setText(AndroidUtils.wordFirstCap(response.body().getPArea()));
                parmenentEdtWardno.setText(AndroidUtils.wordFirstCap(response.body().getPWardNo()));
                parmenentEdtConstituency.setText(AndroidUtils.wordFirstCap(response.body().getPConstituency()));
                parmenentEdtVillage.setText(AndroidUtils.wordFirstCap(response.body().getPVillage()));
                parmenentEdtTehsil.setText(AndroidUtils.wordFirstCap(response.body().getPTehsil()));

                EdtMobileNo1.setText(AndroidUtils.wordFirstCap(response.body().getMobile2()));
                //EdtMobileNo2.setText(AndroidUtils.wordFirstCap(response.body().getM));
                edtStdNo.setText(AndroidUtils.wordFirstCap(response.body().getLandline()));
                edtEmail.setText(AndroidUtils.wordFirstCap(response.body().getEmail()));
                if (!response.body().getEmail().isEmpty()) {
                    edtEmail.setEnabled(false);
                }
                edAreaStudyOther.setText(AndroidUtils.wordFirstCap(response.body().getAreaStudyOthers()));
                if (!response.body().getProfessionOthers().isEmpty() || response.body().getProfessionOthers() != null)
                    edOtherProfessionIndustry.setText(AndroidUtils.wordFirstCap(response.body().getProfessionOthers()));
                if (!response.body().getProfessionStatusOthers().isEmpty() || response.body().getProfessionStatusOthers() != null)
                    edOtherStatus.setText(AndroidUtils.wordFirstCap(response.body().getProfessionStatusOthers()));
                new AsyncTask<Void, Void, Bitmap>() {

                    @Override
                    protected Bitmap doInBackground(Void... params) {
                        Bitmap bitmap = null;
                        try {
//                            bitmap = Glide.with(getActivity()).load(response.body().getPicture()).asBitmap().into(500, 500).get();
                            InputStream input = new java.net.URL(response.body().getPicture()).openStream();
                            // Decode Bitmap
                            bitmap = BitmapFactory.decodeStream(input);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return bitmap;
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        if (bitmap != null) {
                            ProfileImage = AndroidUtils.BitMapToString(bitmap);
                        }
                    }
                }.execute();

                Log.e("picture", response.body().getPicture());
                Picasso.with(getActivity()).load("" + response.body().getPicture())/*.error(R.drawable.placeholder)*/.into(imgUser);
                Picasso.with(getActivity()).load("" + response.body().getPicture())/*.error(R.drawable.placeholder)*/.into(HomeActivity.navProfile);

                gender = response.body().getGender() + "";
                Log.e("gender", gender + "");
                if (radiomale.getText().toString().trim().equalsIgnoreCase(gender.toString().trim().toLowerCase() + "")) {
                    radiomale.setChecked(true);
                } else if (radioFemale.getText().toString().trim().equalsIgnoreCase(gender.toString().trim())) {
                    radioFemale.setChecked(true);
                }
                edtDob.setText(response.body().getDob());
                dob = response.body().getDob();
                getOfficeCountry(response.body().getCountry(), response.body().getState(), response.body().getCity());
                strOCountryName = response.body().getCountry();
                strOStateName = response.body().getState();
                strOCityName = response.body().getCity();
                getPCountry(response.body().getPCountry(), response.body().getPState(), response.body().getPCity());
                strPCountryName = response.body().getPCountry();
                strPStateName = response.body().getPState();
                strPCityName = response.body().getPCity();
                getRCountry(response.body().getRCountry(), response.body().getRState(), response.body().getRCity());
                strRCountryName = response.body().getRCountry();
                strRStateName = response.body().getRState();
                strRCityName = response.body().getRCity();
                getCastlist(response.body().getSubcaste());
                subcaste = response.body().getSubcaste();
                getProfession(response.body().getProfession());
                strProfession = response.body().getProfession();
                getGotraSelf(response.body().getGotraSelf());
                strGotraSelf = response.body().getGotraSelf();
                getGotraMother(response.body().getGotraMother());
                strGotraMother = response.body().getGotraMother();
                getMaratial(response.body().getMaritalStatus());
                strMaritalStatus = response.body().getMaritalStatus();
                getProfessionStatus(response.body().getProfessionStatus());
                strProfessionStatus = response.body().getProfessionStatus();

                getProfessionIndustry(response.body().getProfessionIndustry(), response.body().getSubCategory());
                strProfessionIndustry = response.body().getProfessionIndustry();
                if (!response.body().getSubCategory().isEmpty()) {
                    //getSubProfessionCategory(response.body().getSubCategory());
                    strProfessionSubCat = response.body().getSubCategory();
                    lvSubProfessionCategory.setVisibility(View.GONE);
                    spinnerProfessionIndustrySubCat.setVisibility(View.GONE);
                } else {
                    lvSubProfessionCategory.setVisibility(View.VISIBLE);
                    spinnerProfessionIndustrySubCat.setVisibility(View.VISIBLE);
                }
                getSubProfessionCategory(response.body().getSubCategory());
                strProfessionSubCat = response.body().getSubCategory();

                getAreaStudy(response.body().getAreaStudy());
                strAreaStudy = response.body().getAreaStudy();
                getEducationStatus(response.body().getStatusOfEducation());
                strStatusEducation = response.body().getStatusOfEducation();
                HomeActivity.navUsername.setText(response.body().getFirstname() + " " + response.body().getLastname());

                if (!response.body().getFirstname().isEmpty() && !response.body().getLastname().isEmpty() && !response.body().getMobile().isEmpty() && !response.body().getSubcaste().isEmpty() && !response.body().getGotraSelf().isEmpty() && !response.body().getGotraMother().isEmpty() && !response.body().getMaritalStatus().isEmpty() && !response.body().getDob().isEmpty() && !response.body().getGender().isEmpty()) {
                    profile_ok_image.setVisibility(View.VISIBLE);
                } else {
                    profile_ok_image.setVisibility(View.GONE);
                }

                Log.e("profother", "" + response.body().getProfessionOthers().length());
                if (!response.body().getProfession().isEmpty() && !response.body().getProfessionOthers().isEmpty() && !response.body().getOrganization().isEmpty() && !response.body().getProfessionStatus().isEmpty() && !response.body().getProfessionIndustry().isEmpty() && !response.body().getDesignation().isEmpty() && !response.body().getHouseNo().isEmpty() && !response.body().getArea().isEmpty() && !response.body().getPincode().isEmpty() && !response.body().getCountry().isEmpty() && !response.body().getState().isEmpty() && !response.body().getCity().isEmpty() && !response.body().getContactno().isEmpty()) {
                    profession_ok_image.setVisibility(View.VISIBLE);
                } else {
                    profession_ok_image.setVisibility(View.GONE);
                }

                if (!response.body().getEducationalQual().isEmpty() && !response.body().getInstitution().isEmpty() && !response.body().getAreaStudy().isEmpty() && !response.body().getStatusOfEducation().isEmpty()) {
                    education_ok_image.setVisibility(View.VISIBLE);
                } else {
                    education_ok_image.setVisibility(View.GONE);
                }

                if (!response.body().getMobile2().isEmpty() && !response.body().getLandline().isEmpty() && !response.body().getEmail().isEmpty()) {
                    contact_ok_image.setVisibility(View.VISIBLE);
                } else {
                    contact_ok_image.setVisibility(View.GONE);
                }

                if (!response.body().getRHouseNo().isEmpty() && !response.body().getRArea().isEmpty() && !response.body().getRWardNo().isEmpty() && !response.body().getRConstituency().isEmpty() && !response.body().getRVillage().isEmpty() && !response.body().getRTehsil().isEmpty() && !response.body().getRCountry().isEmpty() && !response.body().getRState().isEmpty() && !response.body().getRCity().isEmpty() &&

                        !response.body().getPHouseNo().isEmpty() && !response.body().getPArea().isEmpty() && !response.body().getPWardNo().isEmpty() && !response.body().getPConstituency().isEmpty() && !response.body().getPVillage().isEmpty() && !response.body().getPTehsil().isEmpty() && !response.body().getPCountry().isEmpty() && !response.body().getPState().isEmpty() && !response.body().getPCity().isEmpty()) {
                    address_ok_image.setVisibility(View.VISIBLE);
                } else {
                    address_ok_image.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelProfileDetails> call, Throwable t) {
                loading.dismiss();
                Log.e("loginData", t.getMessage() + "");
            }
        });

    }

    private void getEducationStatus(String statusOfEducation) {

        List<String> mother = new ArrayList<>();
        mother.add("Select");
        mother.add("Completed");
        mother.add("Pursuing");
        mother.add("Incomplete");

        ArrayAdapter aa0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mother);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatusEducation.setAdapter(aa0);

        for (int j = 0; j < mother.size(); j++) {
            if (mother.get(j).toString().trim().equalsIgnoreCase(statusOfEducation)) {
                spinnerStatusEducation.setSelection(j);
            }
        }

        spinnerStatusEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strStatusEducation = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getAreaStudy(String areaStudy) {

        List<String> mother = new ArrayList<>();
        mother.add("Select");
        mother.add("Architecture/Design");
        mother.add("Arts and Humanities");
        mother.add("Basics Sciences");
        mother.add("Business & Commerce");
        mother.add("Diploma/Certification Courses");
        mother.add("Education");
        mother.add("Engineering");
        mother.add("Research");
        mother.add("Hotel Management");
        mother.add("Information Sciences");
        mother.add("Journalism & Communication");
        mother.add("Law");
        mother.add("Management");
        mother.add("Medical Sciences");
        mother.add("Planning & Design");
        mother.add("Others");

        ArrayAdapter aa0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mother);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAreaStudy.setAdapter(aa0);

        for (int j = 0; j < mother.size(); j++) {
            if (mother.get(j).trim().equalsIgnoreCase(areaStudy)) {
                spinnerAreaStudy.setSelection(j);
            }

        }

        spinnerAreaStudy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strAreaStudy = adapterView.getItemAtPosition(i).toString();

                if (strAreaStudy.equalsIgnoreCase("others")) {
                    lvAreaStudyOther.setVisibility(View.VISIBLE);

                } else {
                    lvAreaStudyOther.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getProfessionIndustry(final String professionIndustryNew, final String subIndustry) {

        API apiservice = APIClient.getClient().create(API.class);

        Call<List<ModelProfessionSociety>> call1 = apiservice.get_professional_Society("tbl_professionslist");

        call1.enqueue(new Callback<List<ModelProfessionSociety>>() {
            @Override
            public void onResponse(Call<List<ModelProfessionSociety>> call, Response<List<ModelProfessionSociety>> response) {

                if (response.body().size() > 0) {
                    ProfessionIndustryList.addAll(response.body());
                }
                final List<String> newProfInd = new ArrayList<>();
                newProfInd.add("Select");
                for (int i = 0; i < response.body().size(); i++) {
                    newProfInd.add(response.body().get(i).getProfessionslist());
                }

                if (getActivity() != null) {
                    ArrayAdapter aa0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, newProfInd);
                    aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProfessionIndustry.setAdapter(aa0);
                }

                for (int j = 0; j < newProfInd.size(); j++) {
                    if (newProfInd.get(j).equalsIgnoreCase(professionIndustryNew)) {
                        spinnerProfessionIndustry.setSelection(j);
                    }
                }

                spinnerProfessionIndustry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        strProfessionIndustry = adapterView.getItemAtPosition(i).toString();
                        if (strProfessionIndustry.equalsIgnoreCase("Select")) {
                            lvSubProfessionCategory.setVisibility(View.GONE);
                            spinnerProfessionIndustrySubCat.setVisibility(View.GONE);
                        } else {
                            strProfessionIndustryId = ProfessionIndustryList.get(i - 1).getId();
                            Log.e("indid", ProfessionIndustryList.get(i - 1).getId());
                            Log.e("indname", ProfessionIndustryList.get(i - 1).getSub());
                            getSubProfessionCategory(subIndustry);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelProfessionSociety>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSubProfessionCategory(final String subProfessionIndustry) {
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelSubProfessionCategory>> call1 = apiService.get_sub_profession_category("professionlist_sub", strProfessionIndustryId);
        call1.enqueue(new Callback<List<ModelSubProfessionCategory>>() {
            @Override
            public void onResponse(Call<List<ModelSubProfessionCategory>> call, Response<List<ModelSubProfessionCategory>> response) {

                final List<ModelSubProfessionCategory> subProfessionCatList = new ArrayList<>();
                if (response.body().size() > 0) {
                    lvSubProfessionCategory.setVisibility(View.VISIBLE);
                    spinnerProfessionIndustrySubCat.setVisibility(View.VISIBLE);
                    for (int i = 0; i < response.body().size(); i++) {
                        subProfessionCatList.add(response.body().get(i));
                        Log.e("subcat", response.body().get(i).getSub_name());
                    }
                } else {
                    strProfessionSubCat = "";
                    lvSubProfessionCategory.setVisibility(View.GONE);
                    spinnerProfessionIndustrySubCat.setVisibility(View.GONE);
                }

                List<String> newSubCat = new ArrayList<>();
                if (subProfessionCatList.size() > 0) {
                    for (int s = 0; s < subProfessionCatList.size(); s++) {
                        newSubCat.add(subProfessionCatList.get(s).getSub_name());
                    }
                }
                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, newSubCat);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProfessionIndustrySubCat.setAdapter(aa11);
                }

                for (int j = 0; j < newSubCat.size(); j++) {
                    if (newSubCat.get(j).equalsIgnoreCase(subProfessionIndustry)) {
                        spinnerProfessionIndustrySubCat.setSelection(j);
                    }
                }
                spinnerProfessionIndustrySubCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        strProfessionSubCat = adapterView.getItemAtPosition(i).toString();
                        if (strProfessionSubCat.equalsIgnoreCase("Select")) {
                            strProfessionSubCat = "";
                        } else {
                            strProfessionSubCat = subProfessionCatList.get(i).getSub_name();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelSubProfessionCategory>> call, Throwable t) {
//                loading.dismiss();
                Log.e("city", t.getMessage() + "");
            }
        });
    }

    private void getProfessionStatus(String professionStatus) {

        List<String> mother = new ArrayList<>();
        mother.add("Select");
        mother.add("Working");
        mother.add("Retired");
        mother.add("Others");

        ArrayAdapter aa0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mother);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfessionStatus.setAdapter(aa0);

        for (int j = 0; j < mother.size(); j++) {
            if (mother.get(j).trim().equalsIgnoreCase(professionStatus)) {
                spinnerProfessionStatus.setSelection(j);
            }

        }

        spinnerProfessionStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strProfessionStatus = adapterView.getItemAtPosition(i).toString();
                TextView errorText = (TextView) spinnerProfessionStatus.getSelectedView();
                if (strProfessionStatus.equalsIgnoreCase("others")) {
                    lvOtherStatus.setVisibility(View.VISIBLE);
                    errorText.setTextColor(Color.parseColor("#17a6c8"));
                    errorText.setTypeface(null, Typeface.BOLD);
                } else {
                    lvOtherStatus.setVisibility(View.GONE);
                    errorText.setTextColor(Color.parseColor("#808080"));
                    errorText.setTypeface(null, Typeface.NORMAL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getMaratial(String maritalStatus) {
        List<String> mother = new ArrayList<>();
        mother.add("Select");
        mother.add("Married");
        mother.add("Unmarried");
        ArrayAdapter aa0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mother);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaritalstatus.setAdapter(aa0);

        for (int j = 0; j < mother.size(); j++) {
            if (mother.get(j).trim().equalsIgnoreCase(maritalStatus)) {
                spinnerMaritalstatus.setSelection(j);
            }

        }

        spinnerMaritalstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strMaritalStatus = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getGotraMother(final String gotraMother) {

        API apiservice = APIClient.getClient().create(API.class);

        Call<List<ModelGotra>> call1 = apiservice.get_gotra_list("gotra");

        call1.enqueue(new Callback<List<ModelGotra>>() {
            @Override
            public void onResponse(Call<List<ModelGotra>> call, Response<List<ModelGotra>> response) {
                gotraMotherList.add("Select");
                for (int i = 0; i < response.body().size(); i++) {
                    gotraMotherList.add(response.body().get(i).getName());
                }
                if (getActivity() != null) {
                    ArrayAdapter<String> aa0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gotraMotherList);
                    aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerGotraMother.setAdapter(aa0);
                }

                for (int j = 0; j < gotraMotherList.size(); j++) {
                    if (gotraMotherList.get(j).trim().equalsIgnoreCase(gotraMother)) {
                        spinnerGotraMother.setSelection(j);
                    }
                }

                spinnerGotraMother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        strGotraMother = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelGotra>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 4000);*/
    }

    private void getGotraSelf(final String gotraSelf) {

        API apiservice = APIClient.getClient().create(API.class);

        Call<List<ModelGotra>> call1 = apiservice.get_gotra_list("gotra");

        call1.enqueue(new Callback<List<ModelGotra>>() {
            @Override
            public void onResponse(Call<List<ModelGotra>> call, Response<List<ModelGotra>> response) {
                gotraSelfList.add("Select");
                for (int i = 0; i < response.body().size(); i++) {
                    gotraSelfList.add(response.body().get(i).getName());
                }
                if (getActivity() != null) {
                    ArrayAdapter<String> aa0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gotraSelfList);
                    aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerGotraSelf.setAdapter(aa0);
                }

                for (int j = 0; j < gotraSelfList.size(); j++) {
                    if (gotraSelfList.get(j).trim().equalsIgnoreCase(gotraSelf)) {
                        spinnerGotraSelf.setSelection(j);
                    }
                }

                spinnerGotraSelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        strGotraSelf = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelGotra>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 3000);*/

    }

    private void getProfession(final String professionss) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<Profession>> call1 = apiService.get_profession();
        call1.enqueue(new Callback<List<Profession>>() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<Profession>> call, Response<List<Profession>> response) {
                loading.dismiss();

//                Log.e("get_profession", response.body().size() + "");

                //List<String> prStrings = new ArrayList<>();
                if (response.body().size() > 0) {
                    professionlist.addAll(response.body());
                }

                /*if (professionlist.size() > 0) {
                    for (int i = 0; i < professionlist.size(); i++) {
                        prStrings.add(professionlist.get(i).getProfession());
                    }
                }*/

                final List<String> Profession = new ArrayList<>();
                if (professionlist.size() > 0) {
                    for (int i = 0; i < professionlist.size(); i++) {
                        if (i == 0) {
                            Profession.add("Select");
                            Profession.add(professionlist.get(i).getProfession());
                        } else {
                            Profession.add(professionlist.get(i).getProfession());
                        }
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Profession);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProfession.setAdapter(aa11);

                for (int j = 0; j < Profession.size(); j++) {
                    if (Profession.get(j).trim().equalsIgnoreCase(professionss)) {
                        spinnerProfession.setSelection(j);
                    }
                }

                spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView errorText = (TextView) spinnerProfession.getSelectedView();

                        strProfession = adapterView.getItemAtPosition(i).toString();
                        if (strProfession.equalsIgnoreCase("Others")) {
                            lvOtherProfessionIndustry.setVisibility(View.VISIBLE);
                            errorText.setTextColor(Color.parseColor("#17a6c8"));
                            errorText.setTypeface(null, Typeface.BOLD);
                        } else {
                            lvOtherProfessionIndustry.setVisibility(View.GONE);
                            errorText.setTextColor(Color.parseColor("#808080"));
                            errorText.setTypeface(null, Typeface.NORMAL);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Profession>> call, Throwable t) {
//                loading.dismiss();

                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getCastlist(String cast) {

        ArrayAdapter aa0 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, MainActivity.CastList);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubcaste.setAdapter(aa0);

        for (int j = 0; j < MainActivity.CastList.size(); j++) {
            if (MainActivity.CastList.get(j).trim().equalsIgnoreCase(cast)) {
                spinnerSubcaste.setSelection(j);
            }

        }

        spinnerSubcaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView errorText = (TextView) spinnerSubcaste.getSelectedView();
                subcaste = adapterView.getItemAtPosition(i).toString();
                if (subcaste.equalsIgnoreCase("Others")) {
                    lvsubcastOther.setVisibility(View.VISIBLE);
                    errorText.setTextColor(Color.parseColor("#17a6c8"));
                    errorText.setTypeface(null, Typeface.BOLD);
                } else {
                    lvsubcastOther.setVisibility(View.GONE);
                    errorText.setTextColor(Color.parseColor("#808080"));
                    errorText.setTypeface(null, Typeface.NORMAL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getOfficeCountry(final String countrydd, final String states, final String citys) {
        Log.e("officeCountry", countrydd);

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCountry>> call1 = apiService.get_country();
        call1.enqueue(new Callback<List<ModelCountry>>() {
            @Override
            public void onResponse(Call<List<ModelCountry>> call, Response<List<ModelCountry>> response) {
//                loading.dismiss();

                if (response.body().size() > 0) {
                    CountryList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                state.add("Select");

                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, state);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerState.setAdapter(aa11);
                }

                final List<String> city = new ArrayList<>();
                city.add("Select");

                if (getActivity() != null) {
                    ArrayAdapter aa1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, city);
                    aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDistrict.setAdapter(aa1);
                }

                final List<String> country = new ArrayList<>();
                if (CountryList.size() > 0) {
                    for (int i = 0; i < CountryList.size(); i++) {
                        if (i == 0) {
                            country.add("Select");
                            country.add(CountryList.get(i).getCountryName());
                        } else {
                            country.add(CountryList.get(i).getCountryName());
                        }
                    }

                }
                if (getActivity() != null) {
                    ArrayAdapter a = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, country);
                    a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCountry.setAdapter(a);
                }
                getState(states, citys);

                for (int j = 0; j < country.size(); j++) {
                    if (country.get(j).trim().equals(countrydd.trim())) {
                        spinnerCountry.setSelection(j);
                        strOCountryId = CountryList.get(j).getId();
                    }
                }

                spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (country.get(i).equalsIgnoreCase("Select")) {

                        } else {
                            strOCountryId = CountryList.get(i - 1).getId();
                            strOCountryName = CountryList.get(i - 1).getCountryName();
                            getState(states, citys);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCountry>> call, Throwable t) {
//                loading.dismiss();

                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getPCountry(final String countrydd, final String states, final String citys) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCountry>> call1 = apiService.get_country();
        call1.enqueue(new Callback<List<ModelCountry>>() {
            @Override
            public void onResponse(Call<List<ModelCountry>> call, Response<List<ModelCountry>> response) {
//                loading.dismiss();

//
                if (response.body().size() > 0) {
                    CountryList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                state.add("Select");

                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, state);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    parmenentSpinnerState.setAdapter(aa11);
                }

                final List<String> city = new ArrayList<>();
                city.add("Select");

                if (getActivity() != null) {
                    ArrayAdapter aa1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, city);
                    aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    parmenentSpinnerDistrict.setAdapter(aa1);
                }

                final List<String> country = new ArrayList<>();
                if (CountryList.size() > 0) {
                    for (int i = 0; i < CountryList.size(); i++) {
                        if (i == 0) {
                            country.add("Select");
                            country.add(CountryList.get(i).getCountryName());
                        } else {
                            country.add(CountryList.get(i).getCountryName());
                        }
                    }

                }
                if (getActivity() != null) {
                    ArrayAdapter a11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, country);
                    a11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    parmenentSpinnerCountry.setAdapter(a11);
                }

                for (int j = 0; j < country.size(); j++) {
                    if (country.get(j).trim().equalsIgnoreCase(countrydd.trim())) {
                        parmenentSpinnerCountry.setSelection(j);
                        strPCountryId = CountryList.get(j - 1).getId();
                    }
                }
                getPState(states, citys);
                Log.e("countryId", strPCountryId);

                parmenentSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (country.get(i).equalsIgnoreCase("Select")) {

                        } else {
                            strPCountryId = CountryList.get(i - 1).getId();
                            strPCountryName = CountryList.get(i - 1).getCountryName();

                            //Log.e("countryId", strPCountryId);
                            getPState(states, citys);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ModelCountry>> call, Throwable t) {
//                loading.dismiss();

                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getRCountry(final String countrydd, final String states, final String citys) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCountry>> call1 = apiService.get_country();
        call1.enqueue(new Callback<List<ModelCountry>>() {
            @Override
            public void onResponse(Call<List<ModelCountry>> call, retrofit2.Response<List<ModelCountry>> response) {
//                loading.dismiss();
                if (response.body().size() > 0) {
                    CountryList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                state.add("Select");

                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, state);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    residetialSpinnerState.setAdapter(aa11);
                }

                final List<String> city = new ArrayList<>();
                city.add("Select");

                if (getActivity() != null) {
                    ArrayAdapter aa1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, city);
                    aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    residetialSpinnerDistrict.setAdapter(aa1);
                }

                final List<String> country = new ArrayList<>();
                if (CountryList.size() > 0) {
                    for (int i = 0; i < CountryList.size(); i++) {
                        if (i == 0) {
                            country.add("Select");
                            country.add(CountryList.get(i).getCountryName());
                        } else
                            country.add(CountryList.get(i).getCountryName());

                    }

                }
                if (getActivity() != null) {
                    ArrayAdapter a11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, country);
                    a11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    residetialSpinnerCountry.setAdapter(a11);
                }

                for (int j = 0; j < country.size(); j++) {
                    if (country.get(j).trim().equals(countrydd.trim())) {
                        residetialSpinnerCountry.setSelection(j);
                        strRCountryId = CountryList.get(j - 1).getId();
                    }
                }
                getRState(states, citys);

                residetialSpinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (country.get(i).equals("Select")) {

                        } else {
                            strRCountryId = CountryList.get(i - 1).getId();
                            strRCountryName = CountryList.get(i - 1).getCountryName();

                            Log.e("countryId", strRCountryId);
                            getRState(states, citys);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ModelCountry>> call, Throwable t) {
//                loading.dismiss();

                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getState(final String statesss, final String cityww) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
//        Toast.makeText(getApplicationContext(),""+strCountryId,Toast.LENGTH_SHORT).show();
        Call<List<ModelState>> call1 = apiService.get_states(strOCountryId);
        call1.enqueue(new Callback<List<ModelState>>() {
            @Override
            public void onResponse(Call<List<ModelState>> call, Response<List<ModelState>> response) {

                StateList = new ArrayList<>();
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        StateList.add(response.body().get(i));
                    }
                }

                final List<String> state = new ArrayList<>();
                if (StateList.size() > 0) {
                    for (int i = 0; i < StateList.size(); i++) {
                        state.add(StateList.get(i).getStateName());
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerState.setAdapter(aa11);

                for (int j = 0; j < state.size(); j++) {
                    if (state.get(j).trim().equalsIgnoreCase(statesss.trim())) {
                        spinnerState.setSelection(j);
                    }
                }
                spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (StateList.get(i).equals("Select")) {
                            getCity("");
                        } else {
                            strOStateId = StateList.get(i).getId();
                            strOStateName = StateList.get(i).getStateName();
                            getCity(cityww);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ModelState>> call, Throwable t) {
                Log.e("states", t.getMessage() + "");
            }
        });
    }

    private void getPState(final String statesss, final String cityww) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
//        Toast.makeText(getApplicationContext(),""+strCountryId,Toast.LENGTH_SHORT).show();
        Call<List<ModelState>> call1 = apiService.get_states(strPCountryId);
        call1.enqueue(new Callback<List<ModelState>>() {
            @Override
            public void onResponse(Call<List<ModelState>> call, Response<List<ModelState>> response) {

                StateList = new ArrayList<>();
                if (response.body().size() > 0) {
                    StateList.addAll(response.body());
                }
                Log.e("state list  : ", StateList.toString());
                final List<String> state = new ArrayList<>();
                if (StateList.size() > 0) {
                    for (int i = 0; i < StateList.size(); i++) {
                        state.add(StateList.get(i).getStateName());
                    }
                }
                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, state);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    parmenentSpinnerState.setAdapter(aa11);
                }

                for (int j = 0; j < state.size(); j++) {
                    if (state.get(j).trim().equalsIgnoreCase(statesss.trim())) {
                        parmenentSpinnerState.setSelection(j);
                    }
                }
                parmenentSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        strPStateId = StateList.get(i).getId();
                        strPStateName = StateList.get(i).getStateName();
                        getPCity(cityww);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ModelState>> call, Throwable t) {
                t.printStackTrace();
                Log.e("pstates", t.getMessage());
            }
        });
    }

    private void getRState(final String statesss, final String cityww) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
//        Toast.makeText(getApplicationContext(),""+strCountryId,Toast.LENGTH_SHORT).show();
        Call<List<ModelState>> call1 = apiService.get_states(strRCountryId);
        call1.enqueue(new Callback<List<ModelState>>() {

            @Override
            public void onResponse(Call<List<ModelState>> call, Response<List<ModelState>> response) {
                Log.e("Profile frag : ", "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                StateList = new ArrayList<>();
                if (response.body().size() > 0) {
                    StateList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                if (StateList.size() > 0) {
                    for (int i = 0; i < StateList.size(); i++) {
                        state.add(StateList.get(i).getStateName());
                    }

                }
                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, state);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    residetialSpinnerState.setAdapter(aa11);
                }

                for (int j = 0; j < state.size(); j++) {
                    if (state.get(j).trim().equalsIgnoreCase(statesss.trim())) {
                        residetialSpinnerState.setSelection(j);
                    }
                }
                residetialSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        strRStateId = StateList.get(i).getId();
                        strRStateName = StateList.get(i).getStateName();
                        getRCity(cityww);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ModelState>> call, Throwable t) {
                Log.e("rstates", t.getMessage());
            }
        });
    }

    private void getCity(final String citsy) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strOStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, Response<List<ModelCity>> response) {
//                loading.dismiss();
//                try {
//                    Log.e("cityList",response.body().string()+"");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                CityList = new ArrayList<>();
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {

                        CityList.add(response.body().get(i));
                    }
                }

                final List<String> city = new ArrayList<>();
                if (CityList.size() > 0) {
                    for (int i = 0; i < CityList.size(); i++) {
                        city.add(CityList.get(i).getCityName());
                    }

                }
                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, city);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDistrict.setAdapter(aa11);
                }

                for (int j = 0; j < city.size(); j++) {
                    if (city.get(j).trim().equalsIgnoreCase(citsy.trim())) {
                        spinnerDistrict.setSelection(j);

                    }
                }
                spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        strOCityName = CityList.get(i).getCityName();
                        strOCityId = CityList.get(i).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCity>> call, Throwable t) {
//                loading.dismiss();
                Log.e("city", t.getMessage() + "");
            }
        });

    }

    private void getPCity(final String citsy) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strPStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, Response<List<ModelCity>> response) {
//                loading.dismiss();
//

//                try {
//                    Log.e("cityList",response.body().string()+"");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                CityList = new ArrayList<>();
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {

                        CityList.add(response.body().get(i));
                    }
                }

                final List<String> city = new ArrayList<>();
                if (CityList.size() > 0) {
                    for (int i = 0; i < CityList.size(); i++) {
                        city.add(CityList.get(i).getCityName());
                    }

                }
                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, city);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    parmenentSpinnerDistrict.setAdapter(aa11);
                }

                for (int j = 0; j < city.size(); j++) {
                    if (city.get(j).toString().trim().equalsIgnoreCase(citsy.toString().trim())) {
                        parmenentSpinnerDistrict.setSelection(j);

                    }
                }
                parmenentSpinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        strPCityName = CityList.get(i).getCityName();
                        strPCityId = CityList.get(i).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCity>> call, Throwable t) {
//                loading.dismiss();
                Log.e("city", t.getMessage() + "");
            }
        });
    }

    private void getRCity(final String citsy) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strRStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, Response<List<ModelCity>> response) {
//                loading.dismiss();
//

//                try {
//                    Log.e("cityList",response.body().string()+"");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                CityList = new ArrayList<>();
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {

                        CityList.add(response.body().get(i));
                    }
                }

                final List<String> city = new ArrayList<>();
                if (CityList.size() > 0) {
                    for (int i = 0; i < CityList.size(); i++) {
                        city.add(CityList.get(i).getCityName());
                    }

                }
                if (getActivity() != null) {
                    ArrayAdapter aa11 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, city);
                    aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    residetialSpinnerDistrict.setAdapter(aa11);
                }

                for (int j = 0; j < city.size(); j++) {
                    if (city.get(j).toString().trim().equalsIgnoreCase(citsy.toString().trim())) {
                        residetialSpinnerDistrict.setSelection(j);

                    }
                }
                residetialSpinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        strRCityName = CityList.get(i).getCityName();
                        strRCityId = CityList.get(i).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCity>> call, Throwable t) {
//                loading.dismiss();
                Log.e("city", t.getMessage() + "");
            }
        });
    }

    private boolean isEmpty() {
        if (lvPersionalDetails.getVisibility() == View.VISIBLE) {
            if (profileEdtFistname.getText().toString().isEmpty()) {
                profileEdtFistname.setError("Enter First Name");
                profileEdtFistname.requestFocus();
                return true;
            } else if (profileEdtLastName.getText().toString().isEmpty()) {
                profileEdtLastName.setError("Enter Last Name");
                profileEdtLastName.requestFocus();
                return true;
            } /*else if (mobileNo.getText().toString().isEmpty()) {
           mobileNo.setError("Enter mobile number");
                return true;
            } */ else if (subcaste.trim().equalsIgnoreCase("Subcaste")) {
                TextView errorText = (TextView) spinnerSubcaste.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Sub Caste");
                spinnerSubcaste.requestFocus();
                return true;
            } else if (lvsubcastOther.getVisibility() == View.VISIBLE && edSubcastOther.getText().toString().trim().isEmpty()) {
                edSubcastOther.setError("Enter Other Sub Caste");
                edSubcastOther.requestFocus();
                return true;
            } else if (strGotraSelf.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerGotraSelf.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Gotra self");
                spinnerGotraSelf.requestFocus();
                return true;
            } else if (strGotraMother.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerGotraMother.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Gotra mother");
                spinnerGotraMother.requestFocus();
                return true;
            } else if (strMaritalStatus.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerMaritalstatus.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Marital status");
                spinnerMaritalstatus.requestFocus();
                return true;
            } else if (edtDob.getText().toString().isEmpty()) {
                edtDob.setError("Select date of birth");
                edtDob.requestFocus();
                return true;
            }
        }

        if (lvPerofessionDetails.getVisibility() == View.VISIBLE) {
            if (strProfession.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerProfession.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Profession");
                spinnerProfession.requestFocus();
                return true;
            } else if (lvOtherProfessionIndustry.getVisibility() == View.VISIBLE && edOtherProfessionIndustry.getText().toString().trim().isEmpty()) {
                edOtherProfessionIndustry.setError("Enter Profession!");
                edOtherProfessionIndustry.requestFocus();
                return true;
            } else if (edtOrganizationName.getText().toString().isEmpty()) {
                edtOrganizationName.setError("Enter Organization Name!");
                edtOrganizationName.requestFocus();
                return true;
            } else if (strProfessionStatus.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerProfessionStatus.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Profession Status!");
                spinnerProfessionStatus.requestFocus();
                return true;
            } else if (lvOtherStatus.getVisibility() == View.VISIBLE && edOtherStatus.getText().toString().trim().isEmpty()) {
                edOtherStatus.setError("Enter Other Status!");
                edOtherStatus.requestFocus();
                return true;
            } else if (strProfessionIndustry.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerProfessionIndustry.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Profession Industry!");
                spinnerProfessionIndustry.requestFocus();
                return true;
            } else if (spinnerProfessionIndustrySubCat.getVisibility() == View.VISIBLE && strProfessionSubCat.equalsIgnoreCase("select")) {
                TextView errorText = (TextView) spinnerProfessionIndustrySubCat.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Sub Profession Industry!");
                spinnerProfessionIndustrySubCat.requestFocus();
                return true;
            } else if (edtDesignation.getText().toString().trim().isEmpty()) {
                edtDesignation.setError("Enter Designation!");
                edtDesignation.requestFocus();
                return true;
            } else if (edtHouseno.getText().toString().trim().isEmpty()) {
                edtHouseno.setError("Enter House Number!");
                edtHouseno.requestFocus();
                return true;
            } else if (edtArea.getText().toString().trim().isEmpty()) {
                edtArea.setError("Enter Area!");
                edtArea.requestFocus();
                return true;
            } else if (edtPincode.getText().toString().trim().isEmpty()) {
                edtPincode.setError("Enter Pincode!");
                edtPincode.requestFocus();
                return true;
            } else if (strOCountryName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerCountry.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Country");
                spinnerCountry.requestFocus();
                return true;
            } else if (strOStateName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerState.getSelectedView();
                errorText.setError("");
                errorText.setText("Select State");
                spinnerState.requestFocus();
                return true;
            } else if (strOCityName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerDistrict.getSelectedView();
                errorText.setError("");
                errorText.setText("Select District");
                spinnerDistrict.requestFocus();
                return true;
            } else if (edtOfficeNo.getText().toString().trim().isEmpty()) {
                edtOfficeNo.setError("Enter Office Number!");
                edtOfficeNo.requestFocus();
                return true;
            }
        }

        if (lvEducationDetails.getVisibility() == View.VISIBLE) {
            if (edtEducationQualification.getText().toString().isEmpty()) {
                edtEducationQualification.setError("Enter Educational Qualification!");
                edtEducationQualification.requestFocus();
                return true;
            } else if (edtNameOfInstitute.getText().toString().trim().isEmpty()) {
                edtNameOfInstitute.setError("Enter Institute Name!");
                edtNameOfInstitute.requestFocus();
                return true;
            } else if (strAreaStudy.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerAreaStudy.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Study Area!");
                spinnerAreaStudy.requestFocus();
                return true;
            } else if (strStatusEducation.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerStatusEducation.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Education Status!");
                spinnerStatusEducation.requestFocus();
                return true;
            }

        }

        if (lvResidetialDetails.getVisibility() == View.VISIBLE) {
            if (residetialEdtHouseno.getText().toString().isEmpty()) {
                residetialEdtHouseno.setError("Enter House Number!");
                residetialEdtHouseno.requestFocus();
                return true;
            } else if (residetialEdtArea.getText().toString().trim().isEmpty()) {
                residetialEdtArea.setError("Enter Area!");
                residetialEdtArea.requestFocus();
                return true;
            } else if (residetialEdtWardno.getText().toString().trim().isEmpty()) {
                residetialEdtWardno.setError("Enter Ward Number!");
                residetialEdtWardno.requestFocus();
                return true;
            } else if (residetialEdtConstituency.getText().toString().trim().isEmpty()) {
                residetialEdtConstituency.setError("Enter Constituency!");
                residetialEdtConstituency.requestFocus();
                return true;
            } else if (residetialEdtVillage.getText().toString().trim().isEmpty()) {
                residetialEdtVillage.setError("Enter Village Name!");
                residetialEdtVillage.requestFocus();
                return true;
            } else if (residetialEdtTehsil.getText().toString().trim().isEmpty()) {
                residetialEdtTehsil.setError("Enter Tehsil!");
                residetialEdtTehsil.requestFocus();
                return true;
            } else if (strRCountryName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) residetialSpinnerCountry.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Country");
                residetialSpinnerCountry.requestFocus();
                return true;
            } else if (strRStateName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) residetialSpinnerState.getSelectedView();
                errorText.setError("");
                errorText.setText("Select State");
                residetialSpinnerState.requestFocus();
                return true;
            } else if (strRCityName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) residetialSpinnerDistrict.getSelectedView();
                errorText.setError("");
                errorText.setText("Select District");
                residetialSpinnerDistrict.requestFocus();
                return true;
            } else if (parmenentEdtHouseno.getText().toString().isEmpty()) {
                parmenentEdtHouseno.setError("");
                parmenentEdtHouseno.requestFocus();
                return true;
            } else if (parmenentEdtArea.getText().toString().trim().isEmpty()) {
                parmenentEdtArea.setError("");
                parmenentEdtArea.requestFocus();
                return true;
            } else if (parmenentEdtWardno.getText().toString().trim().isEmpty()) {
                parmenentEdtWardno.setError("");
                parmenentEdtWardno.requestFocus();
                return true;
            } else if (parmenentEdtConstituency.getText().toString().trim().isEmpty()) {
                parmenentEdtConstituency.setError("");
                parmenentEdtConstituency.requestFocus();
                return true;
            } else if (parmenentEdtVillage.getText().toString().trim().isEmpty()) {
                parmenentEdtVillage.setError("");
                parmenentEdtVillage.requestFocus();
                return true;
            } else if (parmenentEdtTehsil.getText().toString().trim().isEmpty()) {
                parmenentEdtTehsil.setError("");
                parmenentEdtTehsil.requestFocus();
                return true;
            } else if (strPCountryName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) parmenentSpinnerCountry.getSelectedView();
                errorText.setError("");
                errorText.setText("Select Country");
                parmenentSpinnerCountry.requestFocus();
                return true;
            } else if (strPStateName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) parmenentSpinnerState.getSelectedView();
                errorText.setError("");
                errorText.setText("Select State");
                parmenentSpinnerState.requestFocus();
                return true;
            } else if (strPCityName.trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) parmenentSpinnerDistrict.getSelectedView();
                errorText.setError("");
                errorText.setText("Select District");
                parmenentSpinnerDistrict.requestFocus();
                return true;
            }

        }

        if (lvContactDetails.getVisibility() == View.VISIBLE) {
            if (EdtMobileNo1.getText().toString().isEmpty()) {
                EdtMobileNo1.setError("Enter Mobile/Whatsapp Number");
                return true;
            } else if (edtStdNo.getText().toString().trim().isEmpty()) {
                edtStdNo.setError("Enter STD");
                return true;
            } else if (edtEmail.getText().toString().trim().isEmpty()) {
                edtEmail.setError("Enter Email");
            }

        }

        return false;

    }

    public Uri setImageUri() {

        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        //Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".my.package.name.provider", file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }

    public String getImagePath() {
        return imgPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_GALLERY) {

                uri = String.valueOf(data.getData());

                try {

                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(uri));

                    imgUser.setImageBitmap(decodeUri(getActivity(), Uri.parse(uri), 100));
                    ProfileImage = AndroidUtils.BitMapToString(decodeUri(getActivity(), Uri.parse(uri), 100));

                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST) {
                /*picUri = data.getData();
                if (picUri != null) {
                    Log.e("picuri", picUri.toString());
                } else {
                    Log.e("picuri", "null");
                }*/
                Log.e("reached", "activity results");
                performCrop();
                /*uri = getImagePath();
                imgUser.setImageBitmap(decodeFile1(uri));

                ProfileImage = AndroidUtils.BitMapToString(decodeFile1(uri));
                Log.e("primgae", ProfileImage);*/
            } else if (requestCode == CROP_PIC) {
                String path = data.getStringExtra(CropImage.IMAGE_PATH);

                // if nothing received
                if (path == null) {

                    return;
                }

                // cropped bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(getImagePath());
                imgUser.setImageBitmap(bitmap);
                ProfileImage = AndroidUtils.BitMapToString(bitmap);
                updateData();
                /*// get the returned data
                Bundle extras = data.getExtras();

                // get the cropped bitmap
                Bitmap thePic = null;
                if (extras != null) {
                    thePic = extras.getParcelable("data");
                    ProfileImage = AndroidUtils.BitMapToString(thePic);
                    imgUser.setImageBitmap(thePic);
                } else {
                    Log.e("bitmap", "null");
                }
                *//*uri = getImagePath();
                Log.e("uri", uri);*//*
                //imgUser.setImageBitmap(decodeFile1(uri));


                //picView.setImageBitmap(thePic);*/
            }
        }
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent(getActivity(), CropImage.class);

            // tell CropImage activity to look for image to crop
            String filePath = getImagePath();
            cropIntent.putExtra(CropImage.IMAGE_PATH, filePath);

            // allow CropImage activity to rescale image
            cropIntent.putExtra(CropImage.SCALE, true);

            // if the aspect ratio is fixed to ratio 3/2
            cropIntent.putExtra(CropImage.ASPECT_X, 3);
            cropIntent.putExtra(CropImage.ASPECT_Y, 2);

            // start activity CropImage with certain request code and listen
            // for result
            startActivityForResult(cropIntent, CROP_PIC);
            /*"com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image*//*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            //cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, CROP_PIC);*/
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(getActivity(), "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
            anfe.printStackTrace();
        }
    }

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }

    public Bitmap decodeFile1(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, o2);

            ExifInterface exif = new ExifInterface(path);
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;

            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, o2.outWidth, o2.outHeight, matrix, true);
            return rotatedBitmap;

        } catch (Throwable e) {

            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        if (v == profileBtnPersonalDetails) {
            lvPersionalDetails.setVisibility(View.VISIBLE);
            lvPerofessionDetails.setVisibility(View.GONE);
            lvResidetialDetails.setVisibility(View.GONE);
            lvEducationDetails.setVisibility(View.GONE);
            lvParmenentAddressDetails.setVisibility(View.GONE);
            lvContactDetails.setVisibility(View.GONE);
            btnLayout.setVisibility(View.GONE);

//            profileBnEdit.setVisibility(View.VISIBLE);
            lvTitle.setVisibility(View.VISIBLE);

            txtTitle.setText("Personal Details");

//            btnPersonal.setBackgroundResource(R.drawable.gradient_round1);
//            btnProfesional.setBackgroundResource(R.drawable.gradient_round);
//            btnEducation.setBackgroundResource(R.drawable.gradient_round);
//            btnResidetial.setBackgroundResource(R.drawable.gradient_round);
//            btnParmenentAddress.setBackgroundResource(R.drawable.gradient_round);
//            btnContact.setBackgroundResource(R.drawable.gradient_round);
        } else if (v == profileBtnProfessionDetails) {
            lvPersionalDetails.setVisibility(View.GONE);
            lvPerofessionDetails.setVisibility(View.VISIBLE);
            lvResidetialDetails.setVisibility(View.GONE);
            lvEducationDetails.setVisibility(View.GONE);
            lvParmenentAddressDetails.setVisibility(View.GONE);
            lvContactDetails.setVisibility(View.GONE);
            btnLayout.setVisibility(View.GONE);
//            profileBnEdit.setVisibility(View.VISIBLE);
            lvTitle.setVisibility(View.VISIBLE);

            txtTitle.setText("Profession Details");

//            btnPersonal.setBackgroundResource(R.drawable.gradient_round);
//            btnProfesional.setBackgroundResource(R.drawable.gradient_round1);
//            btnEducation.setBackgroundResource(R.drawable.gradient_round);
//            btnResidetial.setBackgroundResource(R.drawable.gradient_round);
//            btnParmenentAddress.setBackgroundResource(R.drawable.gradient_round);
//            btnContact.setBackgroundResource(R.drawable.gradient_round);
        } else if (v == profileBtnEducationDetails) {
            lvPersionalDetails.setVisibility(View.GONE);
            lvPerofessionDetails.setVisibility(View.GONE);
            lvResidetialDetails.setVisibility(View.GONE);
            lvEducationDetails.setVisibility(View.VISIBLE);
            lvParmenentAddressDetails.setVisibility(View.GONE);
            lvContactDetails.setVisibility(View.GONE);
            btnLayout.setVisibility(View.GONE);
//            profileBnEdit.setVisibility(View.VISIBLE);
            lvTitle.setVisibility(View.VISIBLE);

            txtTitle.setText("Education Details");

            // Handle clicks for profileBtnEducationDetails
        } else if (v == profileBtnAddressDetails) {
            lvPersionalDetails.setVisibility(View.GONE);
            lvPerofessionDetails.setVisibility(View.GONE);
            lvResidetialDetails.setVisibility(View.VISIBLE);
            lvEducationDetails.setVisibility(View.GONE);
            lvParmenentAddressDetails.setVisibility(View.VISIBLE);
            lvContactDetails.setVisibility(View.GONE);
            btnLayout.setVisibility(View.GONE);
//            profileBnEdit.setVisibility(View.VISIBLE);
            lvTitle.setVisibility(View.VISIBLE);

            txtTitle.setText("Address Details");

        } else if (v == profileBtnContactDetails) {
            lvPersionalDetails.setVisibility(View.GONE);
            lvPerofessionDetails.setVisibility(View.GONE);
            lvResidetialDetails.setVisibility(View.GONE);
            lvEducationDetails.setVisibility(View.GONE);
            lvParmenentAddressDetails.setVisibility(View.GONE);
            lvContactDetails.setVisibility(View.VISIBLE);
            btnLayout.setVisibility(View.GONE);
//            profileBnEdit.setVisibility(View.VISIBLE);
            lvTitle.setVisibility(View.VISIBLE);

            txtTitle.setText("Contact Details");

//            btnPersonal.setBackgroundResource(R.drawable.gradient_round);
//            btnProfesional.setBackgroundResource(R.drawable.gradient_round);
//            btnEducation.setBackgroundResource(R.drawable.gradient_round);
//            btnResidetial.setBackgroundResource(R.drawable.gradient_round);
//            btnParmenentAddress.setBackgroundResource(R.drawable.gradient_round);
//            btnContact.setBackgroundResource(R.drawable.gradient_round1);

        } else if (v == profileBtnFamilyDetails) {
            /*if (Constants.loginSharedPreferences.getString(Constants.profile_completion, "").trim().equalsIgnoreCase("false")) {
                DialogFamily();
            } else {*/
            Intent i = new Intent(getActivity(), FamilyActivity.class);
            startActivity(i);
            //   }
        }
        if (v == profileBnEdit) {

            profileBnEdit.setVisibility(View.GONE);
//            btnUpdate.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            imgUser.setEnabled(true);
            profileTxtName.setEnabled(true);
            profileTxtEmail.setEnabled(true);
            profileBnEdit.setEnabled(true);
            profileEdtFistname.setEnabled(true);
            profileEdtLastName.setEnabled(true);
            mobileNo.setEnabled(false);
            spinnerSubcaste.setEnabled(true);
            spinnerGotraSelf.setEnabled(true);
            spinnerGotraMother.setEnabled(true);
            spinnerMaritalstatus.setEnabled(true);
            edtDob.setEnabled(true);
            edSubcastOther.setEnabled(true);
            radioFemale.setEnabled(true);
            radiomale.setEnabled(true);
            if (edtEmail.getText().toString().trim().isEmpty()) {
                edtEmail.setEnabled(true);
            } else {
                edtEmail.setEnabled(false);
            }
            edtDob.setTextColor(this.getResources().getColor(R.color.black));
//            spinnerGender.setEnabled(true);

            //    <--Professional details-->

            spinnerProfession.setEnabled(true);
            edtOrganizationName.setEnabled(true);
            spinnerProfessionStatus.setEnabled(true);
            spinnerProfessionIndustry.setEnabled(true);
            spinnerProfessionIndustrySubCat.setEnabled(true);
            edtDesignation.setEnabled(true);
            edtHouseno.setEnabled(true);
            edtArea.setEnabled(true);
            edtPincode.setEnabled(true);
            spinnerCountry.setEnabled(true);
            spinnerState.setEnabled(true);
            spinnerDistrict.setEnabled(true);
            edtOfficeNo.setEnabled(true);
            edOtherProfessionIndustry.setEnabled(true);
            edOtherStatus.setEnabled(true);

            //    <--Education details-->
            edtEducationQualification.setEnabled(true);
            edtNameOfInstitute.setEnabled(true);
            spinnerAreaStudy.setEnabled(true);
            spinnerStatusEducation.setEnabled(true);
            edAreaStudyOther.setEnabled(true);

            //    <--Residetial details-->

            residetialEdtHouseno.setEnabled(true);
            residetialEdtArea.setEnabled(true);
            residetialEdtWardno.setEnabled(true);
            residetialEdtConstituency.setEnabled(true);
            residetialEdtVillage.setEnabled(true);
            residetialEdtTehsil.setEnabled(true);
            residetialSpinnerCountry.setEnabled(true);
            residetialSpinnerState.setEnabled(true);
            residetialSpinnerDistrict.setEnabled(true);

            //    <--parmenent details-->
            permenent_address_checkbox.setEnabled(true);
            parmenentEdtHouseno.setEnabled(true);
            parmenentEdtArea.setEnabled(true);
            parmenentEdtWardno.setEnabled(true);
            parmenentEdtConstituency.setEnabled(true);
            parmenentEdtVillage.setEnabled(true);
            parmenentEdtTehsil.setEnabled(true);
            parmenentSpinnerCountry.setEnabled(true);
            parmenentSpinnerState.setEnabled(true);
            parmenentSpinnerDistrict.setEnabled(true);

            //    <--Contact details-->

            EdtMobileNo1.setEnabled(true);
            EdtMobileNo2.setEnabled(true);
            edtStdNo.setEnabled(true);

        } else if (v == profileBtnChangePass) {
            Intent i = new Intent(getActivity(), ChangePassword.class);
            startActivity(i);
        } else if (v == btnSignOut) {
            // Handle clicks for btnSignOut
            if (!isEmpty()) {
                updateData();
            } else {

                final Dialog alertDialog = new Dialog(getActivity());
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.drawable_back_dialog));
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alertDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                alertDialog.getWindow().setAttributes(lp);
                View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.view_alert_dialog, null);
                Button button_ok;
                ImageView btnClose;
                TextView textview;

                button_ok = (Button) rootView.findViewById(R.id.button_ok);
                textview = (TextView) rootView.findViewById(R.id.textview);
                btnClose = (ImageView) rootView.findViewById(R.id.img_close);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                textview.setText("Please fill the all the data");

                alertDialog.setContentView(rootView);
                button_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
            /*AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setCancelable(false);
            dialog.setTitle("Swarankar");
            dialog.setMessage("Are you sure you want to sign out App?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finishAffinity();
                }
            }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.create().show();*/

//

//

//         else if ( v == btnParmenentAddress ) {
//
//            lvPersionalDetails.setVisibility(View.GONE);
//            lvPerofessionDetails.setVisibility(View.GONE);
//            lvResidetialDetails.setVisibility(View.GONE);
//            lvEducationDetails.setVisibility(View.GONE);
//            lvParmenentAddressDetails.setVisibility(View.VISIBLE);
//            lvContactDetails.setVisibility(View.GONE);
//            txtTitle.setText("Permanent Details");
//
//            btnPersonal.setBackgroundResource(R.drawable.gradient_round);
//            btnProfesional.setBackgroundResource(R.drawable.gradient_round);
//            btnEducation.setBackgroundResource(R.drawable.gradient_round);
//            btnResidetial.setBackgroundResource(R.drawable.gradient_round);
//            btnParmenentAddress.setBackgroundResource(R.drawable.gradient_round1);
//            btnContact.setBackgroundResource(R.drawable.gradient_round);
//        }
     /*   else if (v == btnUpdate) {

            if (!isEmpty()) {
                updateData();
            } else {

                final Dialog alertDialog = new Dialog(getActivity());
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.drawable_back_dialog));
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alertDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                alertDialog.getWindow().setAttributes(lp);
                View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.view_alert_dialog, null);
                Button button_ok;
                ImageView btnClose;
                TextView textview;
                button_ok = (Button) rootView.findViewById(R.id.button_ok);
                textview = (TextView) rootView.findViewById(R.id.textview);
                btnClose = (ImageView) rootView.findViewById(R.id.img_close);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                textview.setText("Please fill the all the data");

                alertDialog.setContentView(rootView);
                button_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();

                    }
                });
                alertDialog.show();

            }
*/
        } else if (v == edtDob) {
            AndroidUtils.hideSoftKeyboard(getActivity());
            myCalendar = Calendar.getInstance();

            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }

            };

            new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        } else if (v == radioFemale) {
            gender = radioFemale.getText().toString().trim();
        } else if (v == radiomale) {
            gender = radiomale.getText().toString().trim();
        } else if (v == imgUser) {
//            registerForContextMenu(imgUser);
//            getActivity().openContextMenu(imgUser);

            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select Your Photo");

            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (items[which] == "Gallery") {

                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(galleryIntent, RESULT_GALLERY);

                    } else if (items[which] == "Camera") {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            // cameraIntent.putExtra("return data", true);

                            startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        } else {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAPTURE_REQUEST_CODE);
                        }
                    }

                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle("Select Action");
//        menu.add(0, 0, 0, "Pick Image");//groupId, itemId, order, title
//        menu.add(0, 1, 0, "Capture Image");
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case 0:
//                Log.d("msg", "Pick Image");
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                        == PackageManager.PERMISSION_GRANTED) {
//
//                    Log.d("msg", "Pick Photo");
//
//                    Intent intentPick = new Intent(Intent.ACTION_GET_CONTENT);
//                    intentPick.setType("image/*");
//                    startActivityForResult(Intent.createChooser(intentPick, "Select Action"), PHOTO_PICK);
//                } else {
//                    // Show rationale and request permission.
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_READWRITE_REQUEST_CODE);
//                }
//
//                break;
//            case 1:
//                Log.d("msg", "Capture Image");
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
//                        == PackageManager.PERMISSION_GRANTED) {
//
//                    Log.d("msg", "Capture Photo");
//
//                    Intent intentCapture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".my.package.name.provider", photo);
//
//                    intentCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
//                    imageUri = photoURI;
//                    selectedProfileImage = photo;
//                    startActivityForResult(intentCapture, PHOTO_CAPTURE);
//                } else {
//                    // Show rationale and request permission.
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, MY_CAPTURE_REQUEST_CODE);
//                }
//
//                break;
//            default:
//                break;
//        }
//
//
//        return super.onContextItemSelected(item);
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.d("msg", "Activity Rersult ");
//
//        if (requestCode == PHOTO_PICK) {
//
//            Bitmap bm = null;
//            if (data != null) {
//                try {
//
//                    pictureUrl = data.getData().getPath();
//                    bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
//                    selectedProfileImage = new File(FileUtils.getPath(getActivity(), data.getData()));
//                    imgUser.setImageBitmap(bm);
//                    isImageSelected = true;
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (bm != null) {
//                imgUser.setImageBitmap(bm);
//                ProfileImage = AndroidUtils.BitMapToString(bm);
//                System.out.println(ProfileImage);
////                writeToFile(ProfileImage);
//
//
//            }
//
//            Log.d("msg", "Pick");
//
//        } else if (requestCode == PHOTO_CAPTURE) {
//
//
//
//            Uri selectedImage = imageUri;
//
//
//
////            pictureUrl = imageUri.toString();
//
//            getActivity().getContentResolver().notifyChange(selectedImage, null);
//            ContentResolver cr = getActivity().getContentResolver();
//            Bitmap bitmap;
//            try {
//                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
//
//                if (bitmap != null) {
//                    imgUser.setImageBitmap(bitmap);
//                    isImageSelected = true;
//                    ProfileImage = AndroidUtils.BitMapToString(bitmap);
////                    writeToFile(ProfileImage);
//
//                }
//
//            } catch (Exception e) {
//                Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT).show();
//                Log.e("msg", "Camera " + e.toString());
//            }
//
//        }
//
//    }

//    public void writeToFile(String data)
//    {
//        // Get the directory for the user's public pictures directory.
//        final File path =
//                Environment.getExternalStoragePublicDirectory
//                        (
//                                //Environment.DIRECTORY_PICTURES
//                                Environment.DIRECTORY_DCIM + "/YourFolder/"
//                        );
//
//        // Make sure the path directory exists.
//        if(!path.exists())
//        {
//            // Make it, if it doesn't exit
//            path.mkdirs();
//        }
//
//        final File file = new File(path, "config.txt");
//
//        // Save your stream, don't forget to flush() it before closing it.
//
//        try
//        {
//            file.createNewFile();
//            FileOutputStream fOut = new FileOutputStream(file);
//            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
//            myOutWriter.append(data);
//
//            myOutWriter.close();
//
//            fOut.flush();
//            fOut.close();
//        }
//        catch (IOException e)
//        {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_CAPTURE_REQUEST_CODE) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            getActivity().startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else {
            Log.e("no permission", "no permission");
            Toast.makeText(getActivity(), "Request Failed! Permission Not Granted!", Toast.LENGTH_LONG).show();
        }
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == MY_CAPTURE_REQUEST_CODE) {
//            if (/*permissions.length >= 1 &&
//                    permissions[0] == Manifest.permission.CAMERA &&*/
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent intentCapture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//                    intentCapture.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Uri.fromFile(photo));
//                    imageUri = Uri.fromFile(photo);
//
//                    startActivityForResult(intentCapture, PHOTO_CAPTURE);
//            } else {
//                // Permission was denied. Display an error message.
//                Toast.makeText(getActivity(), "Camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        } else if (requestCode == MY_READWRITE_REQUEST_CODE) {
//            if (/*permissions.length >= 1 &&
//                    permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE &&*/
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent intentPick = new Intent(Intent.ACTION_GET_CONTENT);
//                    intentPick.setType("image/*");
//                intentPick.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    startActivityForResult(Intent.createChooser(intentPick, "Select Action"), PHOTO_PICK);
//            } else {
//                // Permission was denied. Display an error message.
//                Toast.makeText(getActivity(), "Read/Write permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    private void updateData() {

        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//
//        Log.e("org..........",""+edtOrganizationName.getText().toString());
//        Log.e("industry",""+strProfessionIndustry);
//        Log.e("housno",""+edtHouseno.getText().toString());
//        Log.e("pincode",""+edtPincode.getText().toString());
        Log.e("rcountry", "" + strRCountryName);
        Log.e("rstate", "" + strRStateName);
        Log.e("rcity", "" + strRCityName);
        Log.e("pcountry", "" + strPCountryName);
        Log.e("pstate", "" + strPStateName);
        Log.e("pcity", "" + strPCityName);

        Log.e("pind", "" + strProfessionIndustry);
        Log.e("psubind", "" + strProfessionSubCat);

//        Log.e("profileImageUpdate",""+ProfileImage);

//            Log.e("state",strOStateName);
//            Log.e("city",strOCityName);

        if (subcaste.trim().equalsIgnoreCase("Others")) {
            subcaste = "Others";
            strSubCastOther = edSubcastOther.getText().toString();
        } else {
            strSubCastOther = "";
        }

        if (strAreaStudy.trim().equalsIgnoreCase("Others")) {
            strAreaStudy = "Others";
            strAreaStudyothers = edAreaStudyOther.getText().toString();
        } else {
            strAreaStudyothers = "";
        }

        if (strProfessionStatus.trim().equalsIgnoreCase("Others")) {
            strProfessionStatus = "Others";
            strProfessionStatusothers = edOtherStatus.getText().toString();
        } else {
            strProfessionStatusothers = "";
        }

        if (strProfession.trim().equalsIgnoreCase("Others")) {
            strProfession = "Others";
            strProfessionothers = edOtherProfessionIndustry.getText().toString();
        } else {
            strProfessionothers = "";
        }

        /*if (strProfessionIndustry.trim().equalsIgnoreCase("Others")) {
            strProfessionIndustry = "Others";
            strProfessionSubCat = edOtherProfessionIndustry.getText().toString();
        } else {
            strProfessionSubCat = "";
        }*/

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        API apiservice = retrofit.create(API.class);
        Call<ModelUpadateProfile> call1 = apiservice.update_user(
                Constants.loginSharedPreferences.getString(Constants.uid, ""),
                profileEdtFistname.getText().toString(),
                profileEdtLastName.getText().toString(),
                mobileNo.getText().toString(),
                subcaste,
                strSubCastOther,
                strGotraSelf,
                strGotraMother,
                strMaritalStatus,
                dob,
                gender,
                strProfession,
                strProfessionothers,
                edtOrganizationName.getText().toString(),
                edtArea.getText().toString(),
                strAreaStudyothers,
                strProfessionStatusothers,
                strProfessionStatus,
                strProfessionIndustry,
                strProfessionSubCat,
                edtDesignation.getText().toString(),
                edtHouseno.getText().toString(),
                edtPincode.getText().toString(),
                strOCountryName,
                strOStateName,
                strOCityName,
                edtOfficeNo.getText().toString(),
                edtEmail.getText().toString(),
                edtEducationQualification.getText().toString(),
                edtNameOfInstitute.getText().toString(),
                strAreaStudy,
                strStatusEducation,
                residetialEdtHouseno.getText().toString(),
                residetialEdtArea.getText().toString(),
                residetialEdtWardno.getText().toString(),
                residetialEdtConstituency.getText().toString(),
                residetialEdtVillage.getText().toString(),
                residetialEdtTehsil.getText().toString(),
                strRCountryName,
                strRStateName,
                strRCityName,
                parmenentEdtHouseno.getText().toString(),
                parmenentEdtArea.getText().toString(),
                parmenentEdtWardno.getText().toString(),
                parmenentEdtConstituency.getText().toString(),
                parmenentEdtVillage.getText().toString(),
                parmenentEdtTehsil.getText().toString(),
                strPCountryName,
                strPStateName,
                strPCityName,
                EdtMobileNo1.getText().toString(),
                edtStdNo.getText().toString(),
                ProfileImage,
                strSubCastOther);

        call1.enqueue(new Callback<ModelUpadateProfile>() {

            @Override
            public void onResponse(Call<ModelUpadateProfile> call, Response<ModelUpadateProfile> response) {
                loading.dismiss();

                try {

//                        Log.e("edResponse",response.body().string()+"");
                    if (response.isSuccessful()) {
                        String status = response.body().getResponse();
                        if (status.equalsIgnoreCase("Success")) {
                            SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
                            editor.putString(Constants.profile_completion, response.body().getProfile_completion());
                            Log.e("profilecomp", response.body().getProfile_completion());
                            editor.apply();
                            DialogProfileNotCompleted();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                String status = response.body().getResponse();
//
//                if(status.equalsIgnoreCase("Success")){
//                  Toast.makeText(getActivity(),"success",Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<ModelUpadateProfile> call, Throwable t) {
                loading.dismiss();

                Log.e("fail", t.getMessage());

                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtDob.setText(sdf.format(myCalendar.getTime()));

        dobyear = myCalendar.get(Calendar.YEAR);
        dobmonth = myCalendar.get(Calendar.MONTH) + 1;
        dobday = myCalendar.get(Calendar.DAY_OF_MONTH);

        dob = dobday + "/" + dobmonth + "/" + dobyear;
        Log.e("dobdata", dob + "");
    }

    private void DialogProfileNotCompleted() {

        final Dialog alertDialog = new Dialog(getActivity());
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_profile_not_completed, null);
        Button button_ok;
        TextView textview;
        ImageView imgClose;

        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        textview = (TextView) rootView.findViewById(R.id.textview);
        imgClose = (ImageView) rootView.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        textview.setText("Your profile is successfully update");

        alertDialog.setContentView(rootView);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profile1Fragment m3 = new Profile1Fragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(m3);
                trans.commit();
                manager.popBackStack();
                FragmentManager fragmentManager = getFragmentManager();

                String backStateName1 = m3.getClass().getName();
                if (!fragmentManager.popBackStackImmediate(backStateName1, 0)) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, m3, HomeActivity.TAG_FRAGMENT);
                    ft.addToBackStack(backStateName1);
                    ft.commit();
                }
                alertDialog.dismiss();

            }
        });
        alertDialog.show();

    }

    private void DialogFamily() {

        final Dialog alertDialog = new Dialog(getActivity());
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_profile_not_completed, null);
        Button button_ok;
        TextView textview;
        ImageView imgClose;

        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        textview = (TextView) rootView.findViewById(R.id.textview);
        imgClose = (ImageView) rootView.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setContentView(rootView);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Profile1Fragment m3 = new Profile1Fragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(m3);
                trans.commit();
                manager.popBackStack();
                FragmentManager fragmentManager = getFragmentManager();

                String backStateName1 = m3.getClass().getName();
                if (!fragmentManager.popBackStackImmediate(backStateName1, 0)) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, m3);
                    ft.addToBackStack(backStateName1);
                    ft.commit();
                }
                alertDialog.dismiss();

            }
        });
        alertDialog.show();

    }

}

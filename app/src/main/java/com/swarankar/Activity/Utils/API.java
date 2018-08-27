package com.swarankar.Activity.Utils;

import com.swarankar.Activity.Model.EventList;
import com.swarankar.Activity.Model.ModelChangePassword.ModelChangePassword;
import com.swarankar.Activity.Model.ModelCity.ModelCity;
import com.swarankar.Activity.Model.ModelCountry.ModelCountry;
import com.swarankar.Activity.Model.ModelEventDetails.ModelEventDetails;
import com.swarankar.Activity.Model.ModelEventDetails.ModeleventRegister;
import com.swarankar.Activity.Model.ModelGotra.ModelGotra;
import com.swarankar.Activity.Model.ModelLogin.ModelLogin;
import com.swarankar.Activity.Model.ModelPeriodicals.Periodical;
import com.swarankar.Activity.Model.ModelProfession.ModelProfessionSociety;
import com.swarankar.Activity.Model.ModelProfession.ModelSubProfessionCategory;
import com.swarankar.Activity.Model.ModelProfession.Profession;
import com.swarankar.Activity.Model.ModelProfile.ModelProfile;
import com.swarankar.Activity.Model.ModelSearch.ModelSearch;
import com.swarankar.Activity.Model.ModelSociety.ModelPersonalSociety;
import com.swarankar.Activity.Model.ModelSociety.Society;
import com.swarankar.Activity.Model.ModelSociety.SocietyJoin;
import com.swarankar.Activity.Model.ModelState.ModelState;
import com.swarankar.Activity.Model.News.ModelNewsList;
import com.swarankar.Activity.Model.News.NewsPost;
import com.swarankar.Activity.Model.Notification.ModelNotification;
import com.swarankar.Activity.Model.ProfileDetails.ModelProfileDetails;
import com.swarankar.Activity.Model.RegisterResponse.RegisterResponse;
import com.swarankar.Activity.Model.Splash.ModelSplash;
import com.swarankar.Activity.Model.UpdateProfile.ModelUpadateProfile;
import com.swarankar.Activity.Model.joblist.JobListDatum;
import com.swarankar.Activity.Model.joblist.JobResponse;
import com.swarankar.Activity.Model.joblist.ModelAreaList;
import com.swarankar.Activity.Model.joblist.ModelPersonalJob;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("Register")
    Call<RegisterResponse> register(@Field("firstname") String Firstname,
                                    @Field("lastname") String lastname,
                                    @Field("gender") String gender,
                                    @Field("subcaste") String subcaste,
                                    @Field("educational_qual") String educational_qual,
                                    @Field("profession") String profession,
                                    @Field("dob") String dob_year,
                                    @Field("email") String email,
                                    @Field("mobile") String mobileno,
                                    @Field("picture") String picture,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("login/fb_login")
    Call<ResponseBody> fblogin(@Field("fbid") String fbid,
                               @Field("name") String name,
                               @Field("email") String email,
                               @Field("profile_pic") String profile_pic);

    @FormUrlEncoded
    @POST("myaccount/update_user_mobile")
    Call<ResponseBody> updateprofile(@Field("user_id") String user_id,
                                     @Field("picture") String picture,
                                     @Field("firstname") String firstname,
                                     @Field("lastname") String lastname,
                                     @Field("subcaste") String subcaste,
                                     @Field("marital_status") String marital_status,
                                     @Field("dob") String dob,
                                     @Field("gender") String gender,
                                     @Field("con") String con,
                                     @Field("state") String state,
                                     @Field("city") String city,
                                         /*@Field("educational_qual") String educational_qual,*/
                                     @Field("profession") String profession);

//    http://demo.vethics.in/swarnkar/mobile/Login?member_id=sv@s.com&password=12345678
//    ss@ss.com     pass: 12345678

    @FormUrlEncoded
    @POST("Login")
    Call<ModelLogin> login(@Field("swid") String swid, @Field("password") String password);

    @FormUrlEncoded
    @POST("login/login_generate_otp")
    Call<RegisterResponse> sendOtp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("register/confirm_otp")
    Call<ModelLogin> confirmOtp(@Field("mobile") String mobile, @Field("otp") String otp);

    @FormUrlEncoded
    @POST("Changepassword/change_password")
    Call<ModelChangePassword> changePass(@Field("cur_pass") String strCurrentpass, @Field("new_pass") String strNewpass, @Field("user_id") String userId);

    @GET("get_subcaste")
    Call<ResponseBody> get_subcaste();

    @GET("jobs/fetch_job_data")
    Call<List<JobListDatum>> fetch_job_data();

    @GET("jobs/fetch_job_data")
    Call<List<ModelPersonalJob>> fetch_job_data1(@Query("user_id") String user_id);

    @POST("Register/get_profession")
    Call<List<Profession>> get_profession();

    @POST("myaccount/get_country_mobile")
//    Call<List<ModelCountry>> get_country();
    Call<List<ModelCountry>> get_country();

    @GET("myaccount/get_states_mobile")
    Call<List<ModelState>> get_states(@Query("country_id") String strCountryId);

    @POST("myaccount/get_city_mobile")
    Call<List<ModelCity>> get_city(@Query("state_id") String strStateId);

    @POST("myaccount/get_user_info")
    Call<ModelProfile> userInfo(@Query("user_id") String userid);

    @GET("myaccount/get_user_info")
    Call<ModelProfileDetails> userInfo1(@Query("user_id") String userid);

    @GET("event/get_event")
    Call<List<EventList>> get_event();

    @GET("Event/getEventById")
    Call<ModelEventDetails> get_event_details(@Query("event_id") String eventid);

    //    http://demo.vethics.in/swarnkar/mobile/Society/index?user_id=4
    @GET("Society/index")
    Call<List<Society>> get_society(@Query("user_id") String userid);

    @FormUrlEncoded
    @POST("society/join_society")
    Call<SocietyJoin> join_society(@Field("society_id") String societyId, @Field("join_user_id") String joinUseId, @Field("member_counter") String memberCounter, @Field("society_name") String societyName);

    @GET("family/get_relation_mobile")
    Call<ResponseBody> get_relation_mobile(@Query("user_id") String userid);

    @GET("periodicals")
    Call<List<Periodical>> periodicals();

    @FormUrlEncoded
    @POST("addnews/addnews_reg")
    Call<NewsPost> addnews_reg(@Field("user_id") String user_id,
                               @Field("news_img") String picture,
                               @Field("news_name") String firstname,
                               @Field("contact_no") String lastname,
                               @Field("contact_name") String subcaste,
                               @Field("start_date") String marital_status,
                               @Field("end_date") String dob,
                               @Field("time") String gender, @Field("venue") String venue);

    @GET("addnews/newslist")
    Call<List<ModelNewsList>> newslist();

    @GET("addnews/newslist")
    Call<List<ModelNewsList>> newslist1(@Query("user_id") String user_id);

    //    http://demo.vethics.com/swarnkar/mobile/Society/get_society_by_user_id?user_id=10
//    http://demo.vethics.in/swarnkar/mobile/Society/get_society_by_id?society_id=3
    @GET("Society/get_society_by_user_id")
    Call<List<ModelPersonalSociety>> get_personal_society(@Query("user_id") String user_id);

    @GET("myaccount/get_data_from_table")
    Call<List<ModelProfessionSociety>> get_professional_Society(@Query("table_name") String table_name);

    @GET("myaccount/get_data_from_table")
    Call<List<ModelGotra>> get_gotra_list(@Query("table_name") String table_name);

    @GET("myaccount/get_data_from_table")
    Call<List<ModelSubProfessionCategory>> get_sub_profession_category(@Query("table_name") String table_name, @Query("professionslist_id") String subProfId);

    @FormUrlEncoded
    @POST("Society/edit_society")
    Call<NewsPost> edit_society(
            @Field("society_id") String society_id,
            @Field("society_name") String society_name,
            @Field("reg_status") String reg_status,
            @Field("president_name") String president_name,
            @Field("president_contact_number") String president_contact_number,
            @Field("secretary_name") String secretary_name,
            @Field("secretary_contact_number") String secretary_contact_number,
            @Field("address") String address,
            @Field("country") String country,
            @Field("state") String state,
            @Field("city") String city,
            @Field("picture") String picture
    );

    @FormUrlEncoded
    @POST("Addnews/addnews_reg")
    Call<NewsPost> newslist_update(@Field("user_id") String user_id,
                                   @Field("news_id") String news_id,
                                   @Field("news_img") String picture,
                                   @Field("news_name") String firstname,
                                   @Field("contact_no") String lastname,
                                   @Field("contact_name") String subcaste,
                                   @Field("start_date") String marital_status,
                                   @Field("end_date") String dob,
                                   @Field("time") String gender, @Field("venue") String venue);

    @GET("jobs/get_area_of_work")
    Call<List<ModelAreaList>> get_area_of_work();

    //
    @FormUrlEncoded
    @POST("jobs/add_jobs")
    Call<JobResponse> add_jobs(@Field("user_id") String user_id,
                               @Field("job_title") String picture,
                               @Field("area_of_work") String firstname,
                               @Field("department") String lastname,
                               @Field("designation") String subcaste,
                               @Field("qualification") String marital_status,
                               @Field("last_date_apply") String dob,
                               @Field("contact_name") String gender,
                               @Field("contact_email") String venue,
                               @Field("contact_no") String contact,
                               @Field("job_description") String desc);

    @FormUrlEncoded
    @POST("jobs/update_job")
    Call<JobResponse> update_job(@Field("user_id") String user_id,
                                 @Field("job_id") String job_id,
                                 @Field("job_title") String picture,
                                 @Field("area_of_work") String firstname,
                                 @Field("department") String lastname,
                                 @Field("designation") String subcaste,
                                 @Field("qualification") String marital_status,
                                 @Field("last_date_apply") String dob,
                                 @Field("contact_name") String gender,
                                 @Field("contact_email") String venue,
                                 @Field("contact_no") String contact,
                                 @Field("job_description") String desc,
                                 @Field("random") String random,
                                 @Field("updated_date") String updated_date04);

//    user_id:103
//    firstname:Nick
//    lastname:Makwan
//    mobile:94561337
//    marital_status:Unmarried
//    dob:05/08/1989
//    gender:Female
//    country:1
//    state:1
//    city:1
//    house_no:1
//    subcaste:1
//    gotra_self:pending
//    gotra_mother:pending
//    subcaste:chaudhary
//    r_con:1
//    r_state:1
//    r_city:1
//    p_con:1
//    p_state:1
//    p_city:1
//    educational_qual:Student1111
//    institution:VVP vidhya Pith
//    area_study:Research
//    area_study_others:
//    status_of_education:Incomplete
//    profession_industry:10
//    profession:Business
//    profession_others:
//    status_profession:Retired
//    prof_status_others:
//    p_house_no:1
//    p_area:Prahladnagar
//    p_ward_no:321
//    p_constituency:ConstAhmedabad
//    p_village:Ahmedabad
//    p_tehsil:Ahmedabad
//    r_house_no:1
//    r_area:Satelliter_ward_no:5
//    r_constituency:Ahmedabad
//    r_village:Ahmedabad
//    r_tehsil:AHmedabad
//    mobile2:9033102908
//    landline:079987654321
//    house_no:123
//    designation:Team lead
//    pincode:951347
//    contactno:079123456789
//    Organization:VETHIC
//    area:Prahladnagar

    @FormUrlEncoded
    @POST("myaccount/update_user_mobile")
    Call<ModelUpadateProfile> update_user(@Field("user_id") String user_id,
                                          @Field("firstname") String firstname,
                                          @Field("lastname") String lastname,
                                          @Field("mobile") String mobile,
                                          @Field("subcaste") String subcaste,
                                          @Field("other_subcast") String other_subcast,
                                          @Field("gotra_self") String gotra_self,
                                          @Field("gotra_mother") String gotra_mother,
                                          @Field("marital_status") String marital_status,
                                          @Field("dob") String dob,
                                          @Field("gender") String gender,
                                          @Field("profession") String profession,
                                          @Field("profession_others") String profession_others,
                                          @Field("organization") String Organization,
                                          @Field("area") String area,
                                          @Field("area_study_others") String area_study_others,
                                          @Field("prof_status_others") String prof_status_others,
                                          @Field("status_profession") String status_profession,
                                          @Field("profession_industry") String profession_industry,
                                          @Field("sub_category") String sub_category,
                                          @Field("designation") String designation,
                                          @Field("house_no") String house_no,

                                          @Field("pincode") String pincode,
                                          @Field("country") String country,
                                          @Field("state") String state,
                                          @Field("city") String city,
                                          @Field("contactno") String contactno,
                                          @Field("email") String email,
                                          @Field("educational_qual") String educational_qual,
                                          @Field("institution") String institution,
                                          @Field("area_study") String area_study,
                                          @Field("status_of_education") String status_of_education,
                                          @Field("r_house_no") String r_house_no,
                                          @Field("r_area") String r_area,
                                          @Field("r_ward_no") String r_ward_no,
                                          @Field("r_constituency") String r_constituency,
                                          @Field("r_village") String r_village,
                                          @Field("r_tehsil") String r_tehsil,
                                          @Field("r_con") String r_con,
                                          @Field("r_state") String r_state,
                                          @Field("r_city") String r_city,
                                          @Field("p_house_no") String p_house_no,
                                          @Field("p_area") String p_area,
                                          @Field("p_ward_no") String p_ward_no,
                                          @Field("p_constituency") String p_constituency,
                                          @Field("p_village") String p_village,
                                          @Field("p_tehsil") String p_tehsil,
                                          @Field("p_con") String p_con,
                                          @Field("p_state") String p_state,
                                          @Field("p_city") String p_city,

                                          @Field("mobile2") String mobile2,

                                          @Field("landline") String landline,
                                          @Field("picture") String picture,
                                          @Field("subcaste1") String subcaste1);

    @FormUrlEncoded
    @POST("Family/add_Family")
    Call<ResponseBody> add_family(@Field("user_id") String userid,
                                  @Field("grandfather_info") String grandfather,
                                  @Field("grandmother_info") String grandmother,
                                  @Field("father_info") String father,
                                  @Field("mother_info") String mother,
                                  @Field("insert_update") String insert_update);

    @FormUrlEncoded
    @POST("Family/add_Family")
    Call<ResponseBody> add_brother(@Field("user_id") String userid,
                                   @Field("brother_info") String brother,
                                   @Field("insert_update") String insert_update);

    @FormUrlEncoded
    @POST("Family/add_Family")
    Call<ResponseBody> add_sister(@Field("user_id") String userid,
                                  @Field("sister_info") String sister,
                                  @Field("insert_update") String insert_update);

    @FormUrlEncoded
    @POST("Family/add_Family")
    Call<ResponseBody> add_wife(@Field("user_id") String userid,
                                @Field("wife_info") String wife,
                                @Field("insert_update") String insert_update);

    @FormUrlEncoded
    @POST("Family/add_Family")
    Call<ResponseBody> add_husband(@Field("user_id") String userid,
                                   @Field("husband_info") String husband,
                                   @Field("insert_update") String insert_update);

    //    search:nirav
//    country:
//    state:
//    city:
//    subcaste:
//    profession:
//    gender:
//    educational_qualification:
//    gotra_self:
//    user_id:4
//    member_id:SW-7733
    @FormUrlEncoded
    @POST("myaccount/filter")
    Call<List<ModelSearch>> search(@Field("user_id") String userid,
                                   @Field("member_id") String member_id,
                                   @Field("search") String search,
                                   @Field("country") String country,
                                   @Field("state") String state,
                                   @Field("city") String city,
                                   @Field("subcaste") String subcaste,
                                   @Field("profession") String profession,
                                   @Field("gender") String gender,
                                   @Field("educational_qualification") String educational_qualification,
                                   @Field("gotra_self") String gotra_self,
                                   @Field("marital_status") String marital);

    @FormUrlEncoded
    @POST("myaccount/filter")
    Call<List<ModelSearch>> advance_search(@Field("user_id") String userid,
                                           @Field("member_id") String member_id,
                                           @Field("search") String search);

    @FormUrlEncoded
    @POST("myaccount/filter")
    Call<ResponseBody> search1(@Field("user_id") String userid,
                               @Field("member_id") String member_id,
                               @Field("search") String search,
                               @Field("country") String country,
                               @Field("state") String state,
                               @Field("city") String city,
                               @Field("subcaste") String subcaste,
                               @Field("profession") String profession,
                               @Field("gender") String gender,
                               @Field("educational_qualification") String educational_qualification,
                               @Field("gotra_self") String gotra_self);

    @FormUrlEncoded
    @POST("Society/add_new_society")
    Call<ModeleventRegister> addSociety(@Field("user_id") String userid,
                                        @Field("society_name") String society_name,
                                        @Field("reg_status") String reg_status,
                                        @Field("president_name") String president_name,
                                        @Field("president_contact_number") String president_contact_number,
                                        @Field("secretary_name") String secretary_name,
                                        @Field("secretary_contact_number") String secretary_contact_number,
                                        @Field("address") String address,
                                        @Field("country") String country,
                                        @Field("state") String state,
                                        @Field("city") String city,
                                        @Field("picture") String picture);

    @FormUrlEncoded
    @POST("myaccount/forget_password")
    Call<NewsPost> f_pass(@Field("email") String email);

    @Multipart
    @POST("Periodicals/register")
    Call<ResponseBody> register_periodical(@Part("user_id") RequestBody user_id,
                                           @Part("periodical_name") RequestBody periodical_name,
                                           @Part("registration_status") RequestBody registration_status,
                                           @Part("chief_editor") RequestBody chief_editor,
                                           @Part("contact_no") RequestBody contact_no,
                                           @Part("email") RequestBody email,
                                           @Part("office_address") RequestBody office_address,
                                           @Part("country") RequestBody country,
                                           @Part("state") RequestBody state,
                                           @Part("city") RequestBody city,
                                           @Part("pincode") RequestBody pincode,
                                           @Part("image_front") RequestBody image_front,
                                           @Part("image_back") RequestBody image_back, @Part("pdf") RequestBody file);

//    http://demo.vethics.in/swarnkar/mobile/myaccount/notifications?user_id=4

    @GET("myaccount/notifications")
    Call<List<ModelNotification>> notification(@Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("Jobs/delete_job")
    Call<NewsPost> job_delete(@Field("job_id") String job_id, @Field("user_id") String user_id);

    //    http://demo.vethics.in/swarnkar/mobile/Society/delete_society
    @FormUrlEncoded
    @POST("Society/delete_society")
    Call<NewsPost> delete_society(@Field("society_id") String society_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api_add_programs.php")
    Call<ResponseBody> api_addprogram(@Field("cid") String cid,
                                      @Field("diseid") String diseid,
                                      @Field("name") String name,
                                      @Field("description") String description,
                                      @Field("img1") String img1,
                                      @Field("img2") String img2,
                                      @Field("img3") String img3);

    @Multipart
    @POST("uploadfiledemo.php")
    Call<ResponseBody> uploadfiledemo(@Part("name") String cid,
                                      @Part MultipartBody.Part image);

    @GET("api_gatepass2.php")
    Call<ModelSplash> api_getpass();

}

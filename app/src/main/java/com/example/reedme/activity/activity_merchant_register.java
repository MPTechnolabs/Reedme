package com.example.reedme.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.MultipartEntity;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.example.reedme.R;
import com.example.reedme.adapter.CityAdapter;
import com.example.reedme.adapter.CountryAdapter;
import com.example.reedme.adapter.StoreAdapter;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.Constants;
import com.example.reedme.helper.CustomSimpleMessageDialog;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.model.GetCityNameList;
import com.example.reedme.model.GetCountryNameDetail;
import com.example.reedme.model.GetStateNameList;
import com.example.reedme.views.AVLoadingIndicatorView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Android on 6/24/2016.
 */
public class activity_merchant_register extends AppCompatActivity {

    public static MyJSONParser mJsonParser = null;
    JSONObject jsonObject_parent = null;
    Context context;
    ArrayList myList = new ArrayList();
    String int_SelectCountryId,int_SelectStateId;
    private RelativeLayout rel_two, rel_one;
    private Button iv_next, iv_signup;
    GetCountryNameDetail ld = new GetCountryNameDetail();

    private ImageView iv_shipping_back, iv_signup_back;

    EditText edt_FirstName, edt_LastName, edt_EmailId, edt_Password, edt_ConfirmPassword, edt_MobileNumber;
    File fileBanner;
    String base64Image;
    ImageView img_store,img_camera;
    EditText edt_City, edt_State, edt_Country, edt_Pincode, edt_Address, edt_SpecialInstruction,edt_category;
    activity_merchant_register obj_Registaration;

    Dialog dialog_CityList,dialog_CountryList, dialog_StateList;

    Boolean  bl_SelectCity = false, bl_SelectCode = false;
    String str_FirstName="", str_category="",str_LastName="", str_EmailId="", str_Password="", str_ConfirmPassword="", str_MobileNumber="",
            str_city="", str_state="", str_country="", str_pincode="", str_Address="", str_SpecialIntruction="";
    CheckBox cb_TermCondition;
    TextView txt_ShowPassword, txt_ConfirmPassword;
    Boolean bl_InputPasswordType = true;
    Boolean bl_InputConfirmPasswordType = true;
    String[] country_id = new String[]{
            "101"    };
    String[] country_name = new String[]{
            "India"
    };
    AVLoadingIndicatorView progress;
    JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        obj_Registaration = this;
        mJsonParser = new MyJSONParser();
        idMapping();
        setListeners();


    }
    private void idMapping() {

        rel_one = (RelativeLayout) findViewById(R.id.rel_one);
        rel_two = (RelativeLayout) findViewById(R.id.rel_two);
        iv_signup = (Button) findViewById(R.id.iv_signup);
        iv_next = (Button) findViewById(R.id.iv_next);
        iv_shipping_back = (ImageView) findViewById(R.id.iv_shipping_back);
        iv_signup_back = (ImageView) findViewById(R.id.iv_signup_back);
        progress = (AVLoadingIndicatorView) findViewById(R.id.progress);

        // First Page
        edt_FirstName = (EditText) findViewById(R.id.edt_fname);
        edt_LastName = (EditText) findViewById(R.id.edt_lname);
        edt_EmailId = (EditText) findViewById(R.id.edt_email);
        edt_Password = (EditText) findViewById(R.id.edt_password);
        edt_ConfirmPassword = (EditText) findViewById(R.id.edt_conpassword);
        edt_MobileNumber = (EditText) findViewById(R.id.edt_mobile_number);

        // Second  Page
        edt_City = (EditText) findViewById(R.id.edt_city);
        edt_State = (EditText) findViewById(R.id.edt_state);
        edt_Country = (EditText) findViewById(R.id.edt_country);
        edt_Pincode = (EditText) findViewById(R.id.edt_pincode);
        edt_Address = (EditText) findViewById(R.id.edt_address);
        edt_SpecialInstruction = (EditText) findViewById(R.id.edt_spe_instruction);
        edt_category = (EditText) findViewById(R.id.edt_category);
        cb_TermCondition = (CheckBox) findViewById(R.id.checkbox_terms);

        txt_ShowPassword = (TextView) findViewById(R.id.txt_show_password);
        txt_ConfirmPassword = (TextView) findViewById(R.id.txt_show_confirm_password);
        img_store = (ImageView) findViewById(R.id.img_store);
        img_camera = (ImageView) findViewById(R.id.img_camera);
    }

    private void setListeners() {

        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        iv_shipping_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_two.setVisibility(View.GONE);
                rel_one.setVisibility(View.VISIBLE);
            }
        });

        iv_signup_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_FirstName = edt_FirstName.getText().toString().trim();
                str_LastName = edt_LastName.getText().toString().trim();
                str_EmailId = edt_EmailId.getText().toString().trim();
                str_Password = edt_Password.getText().toString().trim();
                str_ConfirmPassword = edt_ConfirmPassword.getText().toString().trim();
                str_MobileNumber = edt_MobileNumber.getText().toString().trim();
                str_category = edt_category.getText().toString().trim();


                if (str_FirstName == null || str_FirstName.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter First Name", false);


                } else if (str_LastName == null || str_LastName.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter LastName", false);



                } else if (str_EmailId == null || str_EmailId.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Email Id", false);


                } else if (!Utills.isValidEmail(str_EmailId)) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Valid Email Id", false);


                }else if (str_category == null || str_category.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Business Name", false);
                }
                else if (str_Password == null || str_Password.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Password", false);


                } else if (str_ConfirmPassword == null || str_ConfirmPassword.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Confirm Password", false);


                } else if (!str_Password.matches(str_ConfirmPassword)) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Your Password Does not Match", false);


                } else if (str_MobileNumber == null || str_MobileNumber.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Mobile Number", false);


                }else if (str_MobileNumber.length() < 9) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Valid Mobile Number", false);

                }  else {

                    callRegisterApi();

                }
            }
        });

        iv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_city =  edt_City.getText().toString().trim();
                str_state =  edt_State.getText().toString().trim();
                str_country = edt_Country.getText().toString().trim();
                str_pincode = edt_Pincode.getText().toString().trim();
                str_Address = edt_Address.getText().toString().trim();
                str_category = edt_category.getText().toString().trim();
                str_SpecialIntruction = edt_SpecialInstruction.getText().toString().trim();



                if (str_city == null || str_city.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Select  City", false);


                } else if (str_state == null || str_state.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Select  State", false);


                } else if (str_country == null || str_country.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Country", false);


                } else if (str_pincode == null || str_pincode.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Pincode", false);


                } else if (str_category == null || str_category.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter Category", false);
                }else if (str_Address == null || str_Address.equals("")) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please Enter  Address", false);


                } else if (!cb_TermCondition.isChecked()) {

                    Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                        @Override
                        public void onOkayButtonClick() {
                            if (Utills.customSimpleMessageDialog != null) {
                                Utills.customSimpleMessageDialog.dismiss();
                            }
                        }

                    }, Constants.DIALOG_INFO_TITLE, "Please First Agree Terms & Condition", false);


                } else {

                    callRegisterApi();

                    //Log.e("DeviceId", str_DeviceId);

                }


            }
        });


        edt_City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bl_SelectCity == false) {

                    if (edt_State.getText().toString() == null || edt_State.getText().toString().equals("")) {

                        Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                            @Override
                            public void onOkayButtonClick() {
                                if (Utills.customSimpleMessageDialog != null) {
                                    Utills.customSimpleMessageDialog.dismiss();
                                }
                            }

                        }, Constants.DIALOG_INFO_TITLE, "Please Select State First", false);
                    } else {

                        getCityList();
                    }
                }
            }
        });

        edt_State.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bl_SelectCode == false) {

                    if (edt_Country.getText().toString() == null || edt_Country.getText().toString().equals("")) {

                        Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                            @Override
                            public void onOkayButtonClick() {
                                if (Utills.customSimpleMessageDialog != null) {
                                    Utills.customSimpleMessageDialog.dismiss();
                                }
                            }

                        }, Constants.DIALOG_INFO_TITLE, "Please Select Country First", false);
                    } else {

                        getStateList();
                    }
                }
            }
        });


        edt_Country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialougSelectCountryList();

            }
        });

        txt_ShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bl_InputPasswordType) {
                    bl_InputPasswordType = true;
                    txt_ShowPassword.setText("show");

                } else {
                    bl_InputPasswordType = false;

                    txt_ShowPassword.setText("hide");
                }


                if (!bl_InputPasswordType) {

                    edt_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);


                } else {

                    edt_Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


                }
            }
        });

        txt_ConfirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bl_InputConfirmPasswordType) {
                    bl_InputConfirmPasswordType = true;
                    txt_ConfirmPassword.setText("show");

                } else {
                    bl_InputConfirmPasswordType = false;

                    txt_ConfirmPassword.setText("hide");
                }


                if (!bl_InputConfirmPasswordType) {

                    edt_ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);


                } else {

                    edt_ConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }
        });
    }
    private void callRegisterApi() {

        if (Utills.isConnectingToInternet(obj_Registaration)) {

            //Utills.showDialog(obj_Registaration);

            // createRegistationJson();
            new JSONParse().execute();
        }
        else {

            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {

                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "No Internet", false);
        }

    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity_merchant_register.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmapOptions.inSampleSize = 8;

                    bitmap = getResizedBitmap(BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions), 200);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(getImageOrientation(f.getAbsolutePath()));
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, baos);
                    byte b[] = baos.toByteArray();
                    base64Image = Base64.encodeToString(b, Base64.DEFAULT);
                    Log.e("byte array", base64Image);


                    img_store.setImageBitmap(rotatedBitmap);
                    f.delete();
                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator;

                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".png");
                    //  new UploadProfileImageAsync(file).execute();
                    FileBanner(file);

                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);

                c.close();
                Bitmap thumbnail = getResizedBitmap((BitmapFactory.decodeFile(picturePath)), 100);


                Log.w("path from gallery", picturePath + "");

                Matrix matrix = new Matrix();
                matrix.postRotate(getImageOrientation(picturePath));
                Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail, 0, 0, thumbnail.getWidth(),
                        thumbnail.getHeight(), matrix, true);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte b[] = baos.toByteArray();
                base64Image = Base64.encodeToString(b, Base64.DEFAULT);

                img_store.setImageBitmap(rotatedBitmap);
                byte[] bytarray = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                        bytarray.length);
                File file = new File(picturePath);
                //new UploadProfileImageAsync(file).execute();
                FileBanner(file);

            }
        }
    }

    private void FileBanner(File file) {

        fileBanner = file;

    }


    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    private class JSONParse extends AsyncTask<String,String,String> {
        private ProgressDialog pDialog;
        String sResponse = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Utills.showDialog(obj_Registaration);
            progress.setVisibility(View.VISIBLE);


        }
        @Override
        protected String  doInBackground(String... args) {

            try {
                String url = "http://www.mptechnolabs.com/1reward/mechantRegister.php";
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                post.addHeader("Accept", "application/json");
                UUID uniqueKey = UUID.randomUUID();

                Part[] parts = {

                        new StringPart("m_name",str_FirstName),
                        new StringPart("registration_id","1234"),
                        new StringPart("discount_rate",""),
                        new StringPart("firstname",str_FirstName),
                        new StringPart("lastname",str_LastName),
                        new StringPart("email", str_EmailId),
                        new StringPart("password", str_Password),
                        new StringPart("mobile_no", str_MobileNumber),
                        new StringPart("m_address", str_Address),
                        new StringPart("city", str_city),
                        new StringPart("state", str_state),
                        new StringPart("country", str_country),
                        new StringPart("pincode", str_pincode),
                        new StringPart("lati", ""),
                        new StringPart("longi", ""),
                        new StringPart("m_catagory",str_category),
                        new StringPart("m_qr",uniqueKey.toString()),

                        new FilePart("m_logo", fileBanner)
                };

                Log.e("file baaner : ", fileBanner.toString());
                MultipartEntity reqEntity = new MultipartEntity(parts, post.getParams());

                post.setEntity(reqEntity);
                Log.e("multipart", reqEntity + "");


                try {
                    sResponse = EntityUtils.toString(client.execute(post).getEntity(), "UTF-8");
                    Log.d("post", post.toString());
                    Log.e("Response", sResponse);


                    object= new JSONObject(sResponse);


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    client.getConnectionManager().shutdown();
                }

            } catch (Exception e) {
                Log.e(e.getClass().getName(), e.getMessage(), e);

            }
            return sResponse;


        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //Utills.dismissDialog();
            progress.setVisibility(View.GONE);

            LoginCallAction(ParseDataProvider.getInstance(context).IsSuccessRegister(object));

        }
    }

    protected void LoginCallAction(Integer isSuccess) {

        if(isSuccess == 1) {
            try {

                String user = object.getString("user_data");
                AppPrefs.getAppPrefs(activity_merchant_register.this).setString("m_user", user);
                AppPrefs.getAppPrefs(activity_merchant_register.this).setString("m_email", str_EmailId);

                Toast.makeText(activity_merchant_register.this,"Successfully Created your account",Toast.LENGTH_LONG).show();

                Intent i_Login = new Intent(obj_Registaration, Activity_add_store.class);
                i_Login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i_Login.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i_Login);
                AppPrefs.getAppPrefs(activity_merchant_register.this).setString("store_id", isSuccess.toString());

                finish();
            }catch (Exception e)
            {}
        }
        else
        {
            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {

                    if (Utills.customSimpleMessageDialog != null) {

                        Utills.customSimpleMessageDialog.dismiss();

                    }
                }
            }, Constants.DIALOG_INFO_TITLE, "Something,Went Wrong Try Again", false);

        }
    }

    private void getStateList() {

        if (Utills.isConnectingToInternet(obj_Registaration)) {
            new GetState().execute();
        }
        else {

            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {


                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "No Internet", false);
        }
    }
    private void getCityList() {

        if (Utills.isConnectingToInternet(obj_Registaration)) {
            new GetCity().execute();
        }
        else {

            Utills.showCustomSimpleDialog(obj_Registaration, new CustomSimpleMessageDialog.SimpleDialogOnClickListener() {
                @Override
                public void onOkayButtonClick() {


                    if (Utills.customSimpleMessageDialog != null) {
                        Utills.customSimpleMessageDialog.dismiss();
                    }
                }

            }, Constants.DIALOG_INFO_TITLE, "No Internet", false);
        }
    }
    private class GetState extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();


                params.put("country_id", int_SelectCountryId);


                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/getlocation.php", params);
                Log.e("JsonObject", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            GetStoreList(ParseDataProvider.getInstance(obj_Registaration).StateNameList(jsonObject_parent));

        }
    }

    private class GetCity extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                JSONObject params = new JSONObject();


                params.put("state_id", int_SelectStateId);


                jsonObject_parent = mJsonParser.postData("http://www.mptechnolabs.com/1reward/getCity.php", params);
                Log.e("JsonObject", jsonObject_parent + "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            openDialougSelectCityList(ParseDataProvider.getInstance(obj_Registaration).CityNameList(jsonObject_parent));

        }
    }


    private void GetStoreList(final GetStateNameList SateList) {

        dialog_StateList = new Dialog(obj_Registaration);
        dialog_StateList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_StateList.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_StateList.getWindow().setGravity(Gravity.CENTER);

        dialog_StateList.setCanceledOnTouchOutside(false);

        dialog_StateList.setContentView(R.layout.dialoug_select_heard_about_us);


        TextView txt_HeadeTitle = (TextView) dialog_StateList.findViewById(R.id.txt_title_dialog);

        txt_HeadeTitle.setText("Select State");

        ListView lst_view = (ListView) dialog_StateList.findViewById(R.id.lv_select_state);


        StoreAdapter adp_AreaList = new StoreAdapter(obj_Registaration, SateList.getCategoryItems());

        lst_view.setAdapter(adp_AreaList);


        lst_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt_City.setText("");
                edt_State.setText(SateList.getCategoryItems().get(position).getName());
                int_SelectStateId = (SateList.getCategoryItems().get(position).getId());
                dialog_StateList.dismiss();

            }
        });
        dialog_StateList.show();
    }
    private void openDialougSelectCityList(final GetCityNameList cityList) {

        dialog_CityList = new Dialog(obj_Registaration);
        dialog_CityList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_CityList.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_CityList.getWindow().setGravity(Gravity.CENTER);

        dialog_CityList.setCanceledOnTouchOutside(false);

        //setting custom layout to dialog_car_variant
        dialog_CityList.setContentView(R.layout.dialoug_select_heard_about_us);


        TextView txt_HeadeTitle = (TextView) dialog_CityList.findViewById(R.id.txt_title_dialog);

        txt_HeadeTitle.setText("Select City");

        ListView lst_view = (ListView) dialog_CityList.findViewById(R.id.lv_select_state);


        CityAdapter adp_CityList = new CityAdapter(obj_Registaration, cityList.getCategoryItems());

        lst_view.setAdapter(adp_CityList);


        lst_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt_City.setText(cityList.getCategoryItems().get(position).getName());
                int_SelectStateId = (cityList.getCategoryItems().get(position).getId());
                dialog_CityList.dismiss();

            }
        });
        dialog_CityList.show();
    }


    private void openDialougSelectCountryList() {

        dialog_CountryList = new Dialog(obj_Registaration);
        dialog_CountryList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_CountryList.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_CountryList.getWindow().setGravity(Gravity.CENTER);
        dialog_CountryList.setCanceledOnTouchOutside(false);

        //setting custom layout to dialog_car_variant
        dialog_CountryList.setContentView(R.layout.dialoug_select_heard_about_us);


        TextView txt_HeadeTitle = (TextView) dialog_CountryList.findViewById(R.id.txt_title_dialog);

        txt_HeadeTitle.setText("Select Country");

        final ListView lst_view = (ListView) dialog_CountryList.findViewById(R.id.lv_select_state);


        getDataInList();
        CountryAdapter adapter = new CountryAdapter(obj_Registaration, myList);
        lst_view.setAdapter(adapter);

        lst_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                edt_State.setText("");
                edt_City.setText("");

                edt_Country.setText(ld.getName());
                int_SelectCountryId = (ld.getId());
                dialog_CountryList.dismiss();

            }
        });
        dialog_CountryList.show();
    }
    private void getDataInList() {
        for (int i = 0; i < country_id.length; i++) {
            ld.setId(country_id[i]);
            ld.setName(country_name[i]);
            // Add this object into the ArrayList myList
            myList.add(ld);
        }
    }



}

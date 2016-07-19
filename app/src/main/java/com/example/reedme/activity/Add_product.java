package com.example.reedme.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.MultipartEntity;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.example.reedme.R;
import com.example.reedme.barcodescanner.BarcodeCallback;
import com.example.reedme.barcodescanner.BarcodeResult;
import com.example.reedme.barcodescanner.CompoundBarcodeView;
import com.example.reedme.dataprovider.ParseDataProvider;
import com.example.reedme.helper.AppPrefs;
import com.example.reedme.helper.MyJSONParser;
import com.example.reedme.helper.Utills;
import com.example.reedme.views.CustomDelegate;
import com.example.reedme.views.SweetSheet;
import com.google.zxing.ResultPoint;

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
import java.util.List;

/**
 * Created by ubuntu on 15/6/16.
 */
public class Add_product extends AppCompatActivity {


    ImageView img_camera,img_product;
    EditText edt_productName;
    EditText edt_quantity;
    EditText edt_price;
    EditText edt_size;
    EditText edt_weight;
    EditText edt_description;
    Button btn_add;
    JSONObject jsonObject_parent = null;
    Context context;
    File fileBanner;
    String base64Image;
    TextView txt_barcode;
    EditText txt_result;
    private SweetSheet mSweetSheet;
    private RelativeLayout rl;
    ScrollView scrollView;
    private CompoundBarcodeView barcodeScannerView;


    String str_price ="",str_description="",str_size="",str_weight="",str_productname="",str_quantity="",barcode="";
    public static MyJSONParser mJsonParser = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        mJsonParser = new MyJSONParser();

        setMapping();
        setAction();

    }

    private void setMapping()
    {
        edt_description = (EditText) findViewById(R.id.edt_description);
        edt_price = (EditText) findViewById(R.id.edt_price);
        edt_productName = (EditText) findViewById(R.id.edt_productName);
        edt_size= (EditText) findViewById(R.id.edt_size);
        edt_quantity = (EditText) findViewById(R.id.edt_quantity);
        edt_weight = (EditText) findViewById(R.id.edt_weight);
        txt_result = (EditText) findViewById(R.id.txt_result);
        btn_add = (Button) findViewById(R.id.btn_add);
        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        img_camera = (ImageView) findViewById(R.id.img_camera);
        img_product = (ImageView) findViewById(R.id.img_product);
        txt_barcode = (TextView) findViewById(R.id.txt_barcode);
        rl = (RelativeLayout) findViewById(R.id.rl);
        setupCustomView();

    }

    private void setupCustomView() {
        mSweetSheet = new SweetSheet(rl);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation);
        View view = LayoutInflater.from(Add_product.this).inflate(R.layout.fragment_scan, null, false);
        customDelegate.setCustomView(view);
        mSweetSheet.setDelegate(customDelegate);


        barcodeScannerView = (CompoundBarcodeView)view.findViewById(R.id.zxing_barcode_scanner);
       // barcodeScannerView.resume();

        barcodeScannerView.decodeContinuous(callback);

               /* view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSweetSheet3.dismiss();
                    }
                });*/
    }
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result)
         {
            if (result.getText() != null) {
                txt_result.setText(result.getText());
                barcodeScannerView.pause();

                //barcodeScannerView.setStatusText(result.getText());
                mSweetSheet.dismiss();
            }
            //Added preview of scanned barcode
            /*ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));*/
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
       // barcodeScannerView.resume();


    }


    private void setAction()
    {
        txt_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSweetSheet.toggle();
                barcodeScannerView.pause();
                barcodeScannerView.resume();


            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate()) {

                    return;
                }
                str_description = edt_description.getText().toString();
                str_price = edt_price.getText().toString();
                str_productname = edt_productName.getText().toString();
                str_size = edt_size.getText().toString();
                str_weight = edt_weight.getText().toString();
                str_quantity =edt_quantity.getText().toString();
                barcode = txt_result.getText().toString();

                new UploadProfileImageAsync().execute();
               //new JSONParse().execute();

            }
        });
        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String productName = edt_productName.getText().toString();
        String size = edt_size.getText().toString();
        String weight= edt_weight.getText().toString();
        String quantity= edt_quantity.getText().toString();
        String barcode= txt_result.getText().toString();
        String price= edt_price.getText().toString();


        if (productName.isEmpty()) {
            this.edt_productName.setError("enter a product name");
            valid = false;
        } else {
            edt_productName.setError(null);
        }
        if (size.isEmpty()) {
            this.edt_size.setError("enter a size");
            valid = false;
        } else {
            edt_size.setError(null);
        } if (weight.isEmpty()) {
            this.edt_weight.setError("enter a weight");
            valid = false;
        } else {
            edt_weight.setError(null);
        } if (quantity.isEmpty()) {
            this.edt_quantity.setError("enter a quantity");
            valid = false;
        } else {
            edt_quantity.setError(null);
        } if (barcode.isEmpty()) {
            this.txt_result.setError("enter a barcode");
            valid = false;
        } else {
            txt_result.setError(null);
        } if (price.isEmpty()) {
            this.edt_price.setError("enter a price");
            valid = false;
        } else {
            edt_price.setError(null);
        }



        return valid;
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Add_product.this);
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


                    img_product.setImageBitmap(rotatedBitmap);
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

                img_product.setImageBitmap(rotatedBitmap);
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


    class UploadProfileImageAsync extends AsyncTask<String, Void, String> {

        String sResponse = null;

        private String mfilePath;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            Utills.showDialog(context);


        }

        @Override
        protected String doInBackground(String... params) {
            try {
//                MultipartEntityBuilder entity;
                String url = "http://www.mptechnolabs.com/1reward/addProduct.php";
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(url);
                post.addHeader("Accept", "application/json");

                Part[] parts = {
                        new StringPart("m_id", AppPrefs.getAppPrefs(Add_product.this).getString("m_user")),
                        new StringPart("product_name",str_productname),
                        new StringPart("barcode_num",barcode),
                        new StringPart("price",str_price),
                        new StringPart("size",str_size),
                        new StringPart("weight",str_weight),
                        new StringPart("description",str_description),
                        new StringPart("quntity",str_quantity),
                        new StringPart("s_id",AppPrefs.getAppPrefs(Add_product.this).getString("store_id")),
                        new FilePart("product_img", fileBanner)
                };

                Log.e ("file baaner : ",fileBanner.toString());
                MultipartEntity reqEntity = new MultipartEntity(parts, post.getParams());

                post.setEntity(reqEntity);
                Log.e("multipart",reqEntity+"");


                try {
                    sResponse = EntityUtils.toString(client.execute(post).getEntity(), "UTF-8");
                    Log.d("post",post.toString());
                    Log.e("Response", sResponse);



                    //JSONObject object = new JSONObject(sResponse);

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

        @Override
        protected void onPostExecute(String sResponse) {
//            Log.i("UpdateBanner", sResponse);
            Utills.dismissDialog();
            try {

                if (sResponse != null) {

                    Log.i("Res", sResponse);
                    Toast.makeText(Add_product.this,"successfully added",Toast.LENGTH_LONG).show();
                    edt_productName.setText("");
                    edt_description.setText("");
                    edt_price.setText("");
                    edt_quantity.setText("");
                    edt_size.setText("");
                    edt_weight.setText("");
                    txt_result.setText("");


                }

            } catch (Exception e) {
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

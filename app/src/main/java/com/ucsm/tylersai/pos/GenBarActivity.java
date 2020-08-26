package com.ucsm.tylersai.pos;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class GenBarActivity extends AppCompatActivity {
    public final static int QRcodeWidth = 500 ;
    public final static int QRcodeHeight = 230 ;
    private static final String IMAGE_DIRECTORY = "/UCSMPOS";
    Bitmap bitmap ;
    private EditText etqr;
    private ImageView iv;
    private Button btn;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_bar);

            iv = (ImageView) findViewById(R.id.iv);
            etqr = (EditText) findViewById(R.id.etqr);
            btn = (Button) findViewById(R.id.btn);
            btnSave = (Button)findViewById(R.id.btn_save);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(etqr.getText().toString().trim().length() == 0){
                        Toast.makeText(GenBarActivity.this, "Enter String!", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            bitmap = TextToImageEncode(etqr.getText().toString(),BarcodeFormat.CODE_128,QRcodeWidth, QRcodeHeight);
                            iv.setImageBitmap(bitmap);
                            // String path = saveImage(bitmap);  //give read write permission
                            Toast.makeText(GenBarActivity.this, "Barcode Generated", Toast.LENGTH_SHORT).show();
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(GenBarActivity.this);

                    marshMallowPermission.checkPermissionForExternalStorage();

                    marshMallowPermission.requestPermissionForExternalStorage();

                    String path = null;  //give read write permission
                    try {
                        path = saveImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(GenBarActivity.this, "Barcode saved to -> "+path, Toast.LENGTH_SHORT).show();
                }
            });



        }

        public String saveImage(Bitmap myBitmap) throws IOException {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File wallpaperDirectory = new File(
                    Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            // have the object build the directory structure, if needed.

            if (!wallpaperDirectory.exists()) {
                Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
                wallpaperDirectory.mkdirs();
                wallpaperDirectory.createNewFile();
            }

            try {
                File f = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + etqr.getText().toString()+ ".jpg");
                f.createNewFile();   //give read write permission
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

                return f.getAbsolutePath();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return "";

        }

    public Bitmap TextToImageEncode(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }
        /*private Bitmap TextToImageEncode(String Value) throws WriterException {
            BitMatrix bitMatrix;
            try {
                bitMatrix = new MultiFormatWriter().encode(
                        Value,
                        BarcodeFormat.CODE_128,
                        QRcodeWidth, QRcodeHeight, null
                );

            } catch (IllegalArgumentException Illegalargumentexception) {

                return null;
            }
            int bitMatrixWidth = bitMatrix.getWidth();

            int bitMatrixHeight = bitMatrix.getHeight();

            int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

            for (int y = 0; y < bitMatrixHeight; y++) {
                int offset = y * bitMatrixWidth;

                for (int x = 0; x < bitMatrixWidth; x++) {

                    pixels[offset + x] = bitMatrix.get(x, y) ?
                            getResources().getColor(R.color.black):getResources().getColor(R.color.white);
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888);

            bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
            return bitmap;
        }

*/

    }


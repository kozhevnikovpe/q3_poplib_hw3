package com.pavelekozhevnikov.lesson3;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.FileOutputStream;

public class ImageConverter {
    private Context context;

    ImageConverter(Context ctx){
        context = ctx;
    }

    String convertToPng(int resourceID, String out) {
        String TAG = "ImageConverter";
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourceID,options);
            FileOutputStream outStream = new FileOutputStream(out);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.close();
            return out;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return "";
        }
    }
}

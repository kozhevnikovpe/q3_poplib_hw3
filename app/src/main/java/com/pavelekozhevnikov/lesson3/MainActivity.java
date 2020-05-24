package com.pavelekozhevnikov.lesson3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Disposable disposable;
    private AlertDialog cancelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    private String convertResource(int resource){
        String out = getBaseContext().getFilesDir()+"/"+resource+".png";
        return new ImageConverter(getBaseContext()).convertToPng(resource,out);
    }

    private void loadConvertedFile(String path){
        cancelDialog.hide();
        if(path.length()>0){
            File imgFile = new File(path);
            if(imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
                Toast.makeText(getBaseContext(), path,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onButtonClick(View view) {
        cancelDialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("Идет конвертация")
                .setNegativeButton("Отменить", (dialog, which) -> {
                    disposable.dispose();
                }).create();

        cancelDialog.show();

        disposable = Observable.just(R.drawable.skoda)
                .map(this::convertResource)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> loadConvertedFile(result));
    }
}

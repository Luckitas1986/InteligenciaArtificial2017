package com.example.ld.facedetection_ia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

/**
 * Created by ld on 28/10/17.
 */

public class ReconocerCaraDesdeFoto extends AppCompatActivity{
    private Button btnProcesar, btnSeleccionar;
    private ImageView imageView;
    private static final int PICK_IMAGE = 100;
    private Bitmap myBitmap, tempBitmap;
    private Canvas canvas;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reconocer_cara_desde_foto);

        btnProcesar = (Button) findViewById(R.id.btnProcesar);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnSeleccionar = (Button) findViewById(R.id.btnSeleccionar);
        openGallery();
        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
        //creamos un rectangulo para el rostro detectado
        final Paint rectpaint = new Paint();
        rectpaint.setStrokeWidth(7);
        rectpaint.setColor(Color.GREEN);
        rectpaint.setStyle(Paint.Style.STROKE);

        //agregamos el funcionamiento del botón Procesar
        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .setMode(FaceDetector.FAST_MODE)
                        .build();

                if (!faceDetector.isOperational())
                {
                    Toast.makeText(ReconocerCaraDesdeFoto.this,"Face Detector no pudo iniciarse en tu dispositivo",Toast.LENGTH_SHORT).show();
                    return;
                }

                //creamos un frame para que use el bitmap y detecte las caras
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Face> sparseArray = faceDetector.detect(frame);

                //detectamos la posición del rostro para luego indicarle al canvas donde hacer el recuadro
                for (int i = 0; i<sparseArray.size(); i++)
                {
                    Face face = sparseArray.valueAt(i);
                    float x1,x2,y1,y2;
                    x1 = face.getPosition().x;
                    y1 = face.getPosition().y;
                    x2 = x1 + face.getWidth();
                    y2 = y1 + face.getHeight();
                    RectF rectF = new RectF(x1,y1,x2,y2);
                    canvas.drawRoundRect(rectF,2,2,rectpaint);

                }

                imageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
                Toast.makeText(ReconocerCaraDesdeFoto.this, "Rostros detectados: " + sparseArray.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SendMessage(View view)
    {
        openGallery();
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            try {
                //obtiene la ruta de la imagen
                imageView.setImageURI(data.getData());
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                //convierte la imagen a bitmap
                myBitmap = drawable.getBitmap();
                //asigna al ImageView la foto en bitmap
                imageView.setImageBitmap(myBitmap);
                //Necesitamos un canvas para mostrar el bitmap
                //creamos un bitmap temporal para luego asignarlo al canvas
                tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                canvas = new Canvas(tempBitmap);
                canvas.drawBitmap(myBitmap,0,0,null);

            } catch (Exception e) {

            }
        }
    }
}

package com.example.ld.facedetection_ia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.File;

/**
 * Created by ld on 29/10/17.
 */

public class ReconocerCaraDesdeCamara extends AppCompatActivity {

    private Button btnCamara;
    private ImageView imageView;
    private Bitmap myBitmap, tempBitmap;
    private Canvas canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reconocer_cara_desde_camara);

        imageView = (ImageView) findViewById(R.id.imageView);
        btnCamara = (Button) findViewById(R.id.btnCamara);

        tomarFoto();

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });
    }

    protected void tomarFoto(){
        //Creamos el Intent para llamar a la Camara
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //Creamos una carpeta en la memoria del cel
        File imagesFolder = new File(
                Environment.getExternalStorageDirectory(), "AndroidFacil");
        imagesFolder.mkdirs();
        //añadimos el nombre de la imagen
        File image = new File(imagesFolder, "foto.jpg");
        Uri uriSavedImage = Uri.fromFile(image);
        //Le decimos al Intent que queremos grabar la imagen
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        //Lanzamos la aplicacion de la camara con retorno (forResult)
        startActivityForResult(cameraIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Comprobamos que la foto se a realizado
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Creamos un bitmap con la imagen recientemente
            //almacenada en la memoria
            myBitmap = BitmapFactory.decodeFile(
                    Environment.getExternalStorageDirectory() +
                            "/AndroidFacil/" + "foto.jpg");
            //Añadimos el bitmap al imageView para
            //mostrarlo por pantalla
            imageView.setImageBitmap(myBitmap);
            reconocerRostros();
        }
    }

    protected void reconocerRostros() {
        //Necesitamos un canvas para mostrar el bitmap
        //creamos un bitmap temporal para luego asignarlo al canvas
        tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        canvas = new Canvas(tempBitmap);
        canvas.drawBitmap(myBitmap,0,0,null);
        //creamos un rectangulo para el rostro detectado
        final Paint rectpaint = new Paint();
        rectpaint.setStrokeWidth(7);
        rectpaint.setColor(Color.GREEN);
        rectpaint.setStyle(Paint.Style.STROKE);

        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        if (!faceDetector.isOperational())
        {
            Toast.makeText(ReconocerCaraDesdeCamara.this,"Face Detector no pudo iniciarse en tu dispositivo",Toast.LENGTH_SHORT).show();
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
        Toast.makeText(ReconocerCaraDesdeCamara.this, "Rostros detectados: " + sparseArray.size(), Toast.LENGTH_SHORT).show();

    }
}

package com.example.ld.facedetection_ia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
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

public class PantallaPrincipal extends AppCompatActivity {
    Button btnReconocerRostroDesdeImagen, btnReconocerRostroDesdeCamara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        btnReconocerRostroDesdeImagen = (Button) findViewById(R.id.btnReconocerPorFoto);
        btnReconocerRostroDesdeCamara = (Button) findViewById(R.id.btnReconocerDesdeCamara);

        btnReconocerRostroDesdeImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reconocerCaraDesdeFoto = new Intent(getApplicationContext(), ReconocerCaraDesdeFoto.class);
                startActivity(reconocerCaraDesdeFoto);
            }
        });

       /** btnReconocerRostroDesdeCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reconocerCaraDesdeCamara = new Intent(getApplicationContext(), ReconocerCaraDesdeCamara.class);
                startActivity(reconocerCaraDesdeCamara);
            }
        });
*/
        btnReconocerRostroDesdeCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reconocerCaraDesdeFIlmacion = new Intent(getApplicationContext(), ReconocerCaraDesdeFilmacion.class);
                startActivity(reconocerCaraDesdeFIlmacion);
            }
        });


    }
}

package com.aip.a2_tarea_permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.aip.a2_tarea_permission.databinding.ActivityMainBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Switch SwStorage;
    private Switch SwLocation;
    private Switch SwCamera;
    private Switch SwContacts;
    private Switch SwPhone;

    private final ArrayList<Switch> allPermissionSwitch = new ArrayList<>();

    private Map<String, String> permissionsLabel = new HashMap<>();

    private static final int GLOBAL_CODE = 500;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwStorage = findViewById(R.id.spn_storage);
        SwLocation = findViewById(R.id.spn_location);
        SwCamera = findViewById(R.id.spn_camera);
        SwPhone = findViewById(R.id.spn_phone);
        SwContacts = findViewById(R.id.spn_contacts);
        addAllSwitch();
        fillMap();
        checkPermissions();



    }

    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            SwStorage.setChecked(true);
            SwStorage.setClickable(false);
        } if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            SwCamera.setChecked(true);
            SwCamera.setClickable(false);
        } if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            SwLocation.setChecked(true);
            SwLocation.setClickable(false);
        } if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            SwPhone.setChecked(true);
            SwPhone.setClickable(false);
        } if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            SwContacts.setChecked(true);
            SwContacts.setClickable(false);
        }
    }

    public void getInfoForPermission(View view){

        ArrayList<String> perms = new ArrayList<>();

        if (SwStorage.isChecked()){

            perms.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        } if (SwLocation.isChecked()) {

            perms.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        } if ( SwCamera.isChecked()) {

            perms.add(Manifest.permission.CAMERA);


        }if (SwPhone.isChecked()){

            perms.add(Manifest.permission.CALL_PHONE);

        } if ( SwContacts.isChecked()) {

            perms.add(Manifest.permission.READ_CONTACTS);

        }
        grantPermissions(perms);


    }
    public void grantPermissions( ArrayList<String> permissions){

        ActivityCompat.requestPermissions(MainActivity.this, permissions.toArray(new String[0]), GLOBAL_CODE);

    }


    ///Referencia de GeeksforGeeks https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GLOBAL_CODE){
            for (String pm: permissions) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(MainActivity.this, permissionsLabel.get(pm) +" Permission Granted", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(MainActivity.this, permissionsLabel.get(pm)+" Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }

            Intent intent = new Intent(this, ShowPermissionActivity.class);
            startActivity(intent);


        }

    }
    public void addAllSwitch(){
        allPermissionSwitch.add(SwPhone);
        allPermissionSwitch.add(SwContacts);
        allPermissionSwitch.add(SwStorage);
        allPermissionSwitch.add(SwLocation);
        allPermissionSwitch.add(SwCamera);
    }

    public void fillMap(){
        permissionsLabel.put(Manifest.permission.READ_EXTERNAL_STORAGE, "Storage");
        permissionsLabel.put(Manifest.permission.ACCESS_BACKGROUND_LOCATION, "Location");
        permissionsLabel.put(Manifest.permission.READ_EXTERNAL_STORAGE, "Storage");
        permissionsLabel.put(Manifest.permission.CAMERA, "Camera");
        permissionsLabel.put(Manifest.permission.CALL_PHONE, "Phone");
        permissionsLabel.put(Manifest.permission.READ_CONTACTS, "Contact");
    }
}
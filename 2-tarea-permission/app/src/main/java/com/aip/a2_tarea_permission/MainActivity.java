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

    private Map<Integer, Switch> permissionsSwitch = new HashMap<>();

    private static final int STORAGE_CODE = 100;
    private static final int LOCATION_CODE = 200;
    private static final int CAMERA_CODE = 300;
    private static final int PHONE_CODE = 400;
    private static final int CONTACT_CODE = 500;
    private static final int GLOBAL_CODE = 600;




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
        checkPermissions();



    }

    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            SwStorage.setChecked(true);
            SwStorage.setClickable(false);
        } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            SwCamera.setChecked(true);
            SwCamera.setClickable(false);
        } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            SwLocation.setChecked(true);
            SwLocation.setClickable(false);
        } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            SwPhone.setChecked(true);
            SwPhone.setClickable(false);
        } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            SwContacts.setChecked(true);
            SwContacts.setClickable(false);
        }
    }

    public void getInfoForPermission(View view){

//        if (view.getId() == SwStorage.getId()){
//            grantPermission(STORAGE_CODE, Manifest.permission.READ_EXTERNAL_STORAGE );
//
//        } else if (view.getId() == SwLocation.getId()) {
//            grantPermission(LOCATION_CODE, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
//
//        } else if (view.getId() == SwCamera.getId()) {
//            grantPermission(CAMERA_CODE, Manifest.permission.CAMERA);
//
//        }else if (view.getId() == SwPhone.getId()){
//            grantPermission(PHONE_CODE, Manifest.permission.CALL_PHONE);
//
//        } else if (view.getId() == SwContacts.getId()) {
//            grantPermission(CONTACT_CODE, Manifest.permission.READ_CONTACTS);
//
//        }
        Map<Integer, String> map = new HashMap<>();
        ArrayList<String> perms = new ArrayList<>();

        if (SwStorage.isChecked()){
            map.put(STORAGE_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            perms.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            //grantPermission(STORAGE_CODE, Manifest.permission.READ_EXTERNAL_STORAGE );


        } if (SwLocation.isChecked()) {
            map.put(LOCATION_CODE, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            perms.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
            //grantPermission(LOCATION_CODE, Manifest.permission.ACCESS_BACKGROUND_LOCATION);


        } if ( SwCamera.isChecked()) {
            map.put(CAMERA_CODE, Manifest.permission.CAMERA);
            perms.add(Manifest.permission.CAMERA);
            //grantPermission(CAMERA_CODE, Manifest.permission.CAMERA);


        }if (SwPhone.isChecked()){
            map.put(PHONE_CODE, Manifest.permission.CALL_PHONE);
            perms.add(Manifest.permission.CALL_PHONE);
            //grantPermission(PHONE_CODE, Manifest.permission.CALL_PHONE);


        } if ( SwContacts.isChecked()) {
            map.put(CONTACT_CODE, Manifest.permission.READ_CONTACTS);
            perms.add(Manifest.permission.READ_CONTACTS);
            //grantPermission(CONTACT_CODE, Manifest.permission.READ_CONTACTS);

        }
        if (grant(perms)){
            Intent intent = new Intent(this, ShowPermissionActivity.class);
            startActivity(intent);
        }






    }
    public boolean grant( ArrayList<String> permissions){


        ActivityCompat.requestPermissions(MainActivity.this, permissions.toArray(new String[0]), GLOBAL_CODE);
        return true;
    }


//    public void grantPermission( Integer requestCode, String permissionType){
//
//        if (ContextCompat.checkSelfPermission(MainActivity.this, permissionType) == PackageManager.PERMISSION_DENIED) {
//
//            // Requesting the permission
//            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permissionType }, requestCode);
//        }
//        else {
//            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }

    ///Referencia de GeeksforGeeks https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GLOBAL_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

               Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
           } else {

               Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
//        if (requestCode == STORAGE_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //SwStorage.setClickable(false);
//                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                SwStorage.setChecked(false);
//                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == LOCATION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //SwLocation.setClickable(false);
//                Toast.makeText(MainActivity.this, "Location Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                //SwLocation.setChecked(false);
//                Toast.makeText(MainActivity.this, "Location Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == CAMERA_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //.setClickable(false);
//                Toast.makeText(MainActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                //SwCamera.setChecked(false);
//                Toast.makeText(MainActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == PHONE_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //SwPhone.setClickable(false);
//                Toast.makeText(MainActivity.this, "Phone Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                //SwPhone.setChecked(false);
//                Toast.makeText(MainActivity.this, "Phone Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == CONTACT_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                //SwContacts.setClickable(false);
//                Toast.makeText(MainActivity.this, "Contacts Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                //SwContacts.setChecked(false);
//                Toast.makeText(MainActivity.this, "Contacts Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
    }
    public void addAllSwitch(){
        allPermissionSwitch.add(SwPhone);
        allPermissionSwitch.add(SwContacts);
        allPermissionSwitch.add(SwStorage);
        allPermissionSwitch.add(SwLocation);
        allPermissionSwitch.add(SwCamera);
        permissionsSwitch.put(STORAGE_CODE, SwStorage);
        permissionsSwitch.put(LOCATION_CODE, SwLocation);
        permissionsSwitch.put(CAMERA_CODE, SwCamera);
        permissionsSwitch.put(PHONE_CODE, SwPhone);
        permissionsSwitch.put(CONTACT_CODE, SwContacts);
    }

    public void seeInfo(View view){
        Intent intent = new Intent(this, ShowPermissionActivity.class);
        startActivity(intent);
    }
}
package com.aip.a2_tarea_permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private Switch SwStorage;
    private Switch SwLocation;
    private Switch SwCamera;
    private Switch SwContacts;
    private Switch SwPhone;

    private final ArrayList<PermissionBtn> permissionBtns = new ArrayList<>();

    private Map<String, String> permissionsLabel = new HashMap<>();

    private static final int GLOBAL_CODE = 500;
    private static final int STORAGE_CODE = 100;
    private static final int LOCATION_CODE = 200;
    private static final int CAMERA_CODE = 300;
    private static final int PHONE_CODE = 400;
    private static final int CONTACT_CODE = 600;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwStorage = findViewById(R.id.spn_storage);
        SwLocation = findViewById(R.id.spn_location);
        SwCamera = findViewById(R.id.spn_camera);
        SwPhone = findViewById(R.id.spn_phone);
        SwContacts = findViewById(R.id.spn_contacts);
        fillMap();
        checkPermissions();



    }

    public void getInfoForPermission(View view){

        ArrayList<String> perms = new ArrayList<>();
        for (PermissionBtn pm: permissionBtns) {
            if (pm.getBtnPermission().isChecked() && pm.getBtnPermission().isClickable()){
                perms.add(pm.getPermissionType());
            }
        }
        grantPermissions(perms);


    }
    public void grantPermissions( ArrayList<String> permissionsString){

        ActivityCompat.requestPermissions(MainActivity.this, permissionsString.toArray(new String[0]), GLOBAL_CODE);

    }


    ///Referencia de GeeksforGeeks https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GLOBAL_CODE){
            int prueba = 0;
            for (String pm: permissions) {
                if (grantResults.length > 0 && grantResults[prueba] == PackageManager.PERMISSION_DENIED) {
                    for (PermissionBtn pmBt: permissionBtns) {
                        if (pm.equals(pmBt.permissionType)){
                            pmBt.setGranted(false);
                            pmBt.getBtnPermission().setChecked(false);
                        }
                    }

                    Toast.makeText(MainActivity.this, permissionsLabel.get(pm)+" Permission Denied", Toast.LENGTH_SHORT).show();

                }
                prueba ++;
            }

            Intent intent = new Intent(this, ShowPermissionActivity.class);
            startActivity(intent);


        }

    }
    public void checkPermissions(){
        PermissionBtn permissionBtn = new PermissionBtn(), permissionBtnL = new PermissionBtn(),
                permissionBtnC = new PermissionBtn(), permissionBtnP = new PermissionBtn(), permissionBtnCo = new PermissionBtn();
        permissionBtn.setBtnPermission(findViewById(R.id.spn_storage));
        permissionBtn.setPermissionType(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            permissionBtn.setGranted(true);
            permissionBtn.setCode(STORAGE_CODE);
            SwStorage.setChecked(true);
            SwStorage.setClickable(false);

        }permissionBtns.add(permissionBtn);
        permissionBtnC.setBtnPermission(findViewById(R.id.spn_camera));
        permissionBtnC.setPermissionType(Manifest.permission.CAMERA);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

            permissionBtnC.setGranted(true);
            permissionBtnC.setCode(CAMERA_CODE);
            SwCamera.setChecked(true);
            SwCamera.setClickable(false);


        }permissionBtns.add(permissionBtnC);
        permissionBtnL.setBtnPermission(findViewById(R.id.spn_location));
        permissionBtnL.setPermissionType(Manifest.permission.ACCESS_FINE_LOCATION);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            permissionBtnL.setGranted(true);
            permissionBtnL.setCode(LOCATION_CODE);
            SwLocation.setChecked(true);
            SwLocation.setClickable(false);


        }permissionBtns.add(permissionBtnL);
        permissionBtnP.setBtnPermission(findViewById(R.id.spn_phone));
        permissionBtnP.setPermissionType(Manifest.permission.CALL_PHONE);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            permissionBtnP.setGranted(true);
            permissionBtnP.setCode(PHONE_CODE);
            SwPhone.setChecked(true);
            SwPhone.setClickable(false);

        }permissionBtns.add(permissionBtnP);
        permissionBtnCo.setBtnPermission(findViewById(R.id.spn_contacts));
        permissionBtnCo.setPermissionType(Manifest.permission.READ_CONTACTS);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){

            permissionBtnCo.setGranted(true);
            permissionBtnCo.setCode(CONTACT_CODE);
            SwContacts.setChecked(true);
            SwContacts.setClickable(false);

        }permissionBtns.add(permissionBtnCo);

    }

    public void fillMap(){
        permissionsLabel.put(Manifest.permission.READ_EXTERNAL_STORAGE, "Storage");
        permissionsLabel.put(Manifest.permission.ACCESS_FINE_LOCATION, "Location");
        permissionsLabel.put(Manifest.permission.READ_EXTERNAL_STORAGE, "Storage");
        permissionsLabel.put(Manifest.permission.CAMERA, "Camera");
        permissionsLabel.put(Manifest.permission.CALL_PHONE, "Phone");
        permissionsLabel.put(Manifest.permission.READ_CONTACTS, "Contact");
    }
    public static class PermissionBtn {
        public Switch btnPermission;
        public String permissionType;

        public Boolean granted = false;

        public Integer code = 0;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Switch getBtnPermission() {
            return btnPermission;
        }

        public Boolean getGranted() {
            return granted;
        }

        public void setGranted(Boolean granted) {
            this.granted = granted;
        }

        public String getPermissionType() {
            return permissionType;
        }

        public void setBtnPermission(Switch btnPermission) {
            this.btnPermission = btnPermission;
        }

        public void setPermissionType(String permissionType) {
            this.permissionType = permissionType;
        }
    }
}
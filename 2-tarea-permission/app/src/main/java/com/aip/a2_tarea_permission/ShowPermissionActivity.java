package com.aip.a2_tarea_permission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowPermissionActivity extends AppCompatActivity {


    private ArrayList<PermissionBtn> permissions = new ArrayList<>();
    private static final int STORAGE_CODE = 100;
    private static final int LOCATION_CODE = 200;
    private static final int CAMERA_CODE = 300;
    private static final int PHONE_CODE = 400;
    private static final int CONTACT_CODE = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_permission);
        addAllButtons();

    }
    public void open(View view, PermissionBtn permissionBtn){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Permission granted. Are you sure you want to open this resource?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                openUp(permissionBtn);

                                //Toast.makeText(ShowPermissionActivity.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void checkPermissions(View view){
        for (PermissionBtn pm: permissions) {
            if (view.getId() == pm.getBtnPermission().getId()){
                if (pm.getGranted()){
                    open(view, pm);
                }else{
                    Toast.makeText(ShowPermissionActivity.this, "This permission is DENIED", Toast.LENGTH_SHORT).show();
                }


            }
        }

    }

    public void addAllButtons(){
        PermissionBtn permissionBtn = new PermissionBtn(), permissionBtnL = new PermissionBtn(),
                permissionBtnC = new PermissionBtn(), permissionBtnP = new PermissionBtn(), permissionBtnCo = new PermissionBtn();
        permissionBtn.setBtnPermission(findViewById(R.id.btn_storage));
        permissionBtn.setPermissionType(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(ShowPermissionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            permissionBtn.setGranted(true);
            permissionBtn.setCode(STORAGE_CODE);
        }
        permissions.add(permissionBtn);

        permissionBtnL.setBtnPermission(findViewById(R.id.btn_location));
        permissionBtnL.setPermissionType(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        if (ContextCompat.checkSelfPermission(ShowPermissionActivity.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED){
            permissionBtnL.setGranted(true);
            permissionBtnL.setCode(LOCATION_CODE);

        }
        permissions.add(permissionBtnL);

        permissionBtnC.setBtnPermission(findViewById(R.id.btn_camera));
        permissionBtnC.setPermissionType(Manifest.permission.CAMERA);
        if (ContextCompat.checkSelfPermission(ShowPermissionActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            permissionBtnC.setGranted(true);
            permissionBtnC.setCode(CAMERA_CODE);

        }
        permissions.add(permissionBtnC);

        permissionBtnP.setBtnPermission(findViewById(R.id.btn_phone));
        permissionBtnP.setPermissionType(Manifest.permission.CALL_PHONE);
        if (ContextCompat.checkSelfPermission(ShowPermissionActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            permissionBtnP.setGranted(true);
            permissionBtnP.setCode(PHONE_CODE);
        }
        permissions.add(permissionBtnP);

        permissionBtnCo.setBtnPermission(findViewById(R.id.btn_contacts));
        permissionBtnCo.setPermissionType(Manifest.permission.READ_CONTACTS);
        if (ContextCompat.checkSelfPermission(ShowPermissionActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            permissionBtnCo.setGranted(true);
            permissionBtnCo.setCode(CONTACT_CODE);
        }
        permissions.add(permissionBtnCo);



    }

    public void openUp(PermissionBtn permissionBtn){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        String phoneNumber = "829-613-3107";
        if (permissionBtn.getCode() == STORAGE_CODE){
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
            startActivity(intent);
        } else if (permissionBtn.getCode() == LOCATION_CODE) {
            // Create a Uri from an intent string. Use the result to create an Intent.
            Uri gmmIntentUri = Uri.parse("geo:40.7396924,-74.0359998");
            intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);

            startActivity(intent);
        } else if (permissionBtn.getCode() == CAMERA_CODE) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
            
        } else if (permissionBtn.getCode() == PHONE_CODE) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +phoneNumber));
            startActivity(intent);

        } else if (permissionBtn.getCode() == CONTACT_CODE) {
            intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
            startActivity(intent);

        }

    }

    public static class PermissionBtn {
        public Button btnPermission;
        public String permissionType;

        public Boolean granted = false;

        public Integer code = 0;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Button getBtnPermission() {
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

        public void setBtnPermission(Button btnPermission) {
            this.btnPermission = btnPermission;
        }

        public void setPermissionType(String permissionType) {
            this.permissionType = permissionType;
        }
    }

}

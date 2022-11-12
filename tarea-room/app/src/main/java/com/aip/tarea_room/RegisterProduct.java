package com.aip.tarea_room;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.aip.tarea_room.databinding.FragmentRegisterProductBinding;
import com.aip.tarea_room.model.Product;
import com.aip.tarea_room.model.ProductViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.*;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterProduct extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Integer SELECT_PICTURE = 200;
    Integer PICTURE_ID = 300;
    private FragmentRegisterProductBinding binding;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE = 200;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private Uri imageUri;
    private StorageTask UploadTask;
    private ProductViewModel productViewModel;
    private Boolean selectedImage = false;
    private StorageReference ref;

    public RegisterProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterProduct newInstance(String param1, String param2) {
        RegisterProduct fragment = new RegisterProduct();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        storageReference = FirebaseStorage.getInstance().getReference();
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        binding = FragmentRegisterProductBinding.inflate(inflater, container, false);
        binding.btnClear.setOnClickListener(view -> clear());
        binding.btnSave.setOnClickListener(view -> {
            if (!validateData()){
                Toast.makeText(binding.getRoot().getContext(), "Must fill all fields", Toast.LENGTH_SHORT).show();
            }else {
                if (UploadTask != null && UploadTask.isInProgress()) {
                    Toast.makeText(binding.getRoot().getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        binding.imageView2.setOnClickListener(view -> {
            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu(binding.getRoot().getContext(), view);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Choose From Storage")){
                    imageChooser("image/*");
                } else if (item.getTitle().equals("Choose From Camera")) {
                    takeImage();
                } else if (item.getTitle().equals("Choose From Gallery")) {
                    imageChooser("image/*");
                }
                Toast.makeText(binding.getRoot().getContext(),"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });

            popup.show();
        });
        return binding.getRoot();
    }
    public void clear(){
        binding.productName2.setText("");
        binding.brand2.setText("");
        binding.priceTxt2.setText("");
        binding.imageView2.setImageResource(android.R.color.transparent);
        selectedImage = false;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    void imageChooser(String type) {

        // create an instance of the
        // intent of the type image
        if (type.equals("image/*")){
            if (ActivityCompat.checkSelfPermission(binding.getRoot().getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE);
                selectedImage = true;
            }else {
                Intent i = new Intent();
                i.setType(type);
                i.setAction(Intent.ACTION_GET_CONTENT);

                // pass the constant to compare it
                // with the returned requestCode
                startActivityForResult(Intent.createChooser(i, "Select Picture"), STORAGE);
                selectedImage = true;
            }
        }else {
            Intent i = new Intent();
            i.setType(type);
            i.setAction(Intent.ACTION_GET_CONTENT);

            // pass the constant to compare it
            // with the returned requestCode
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
            selectedImage = true;
        }

    }
    void takeImage(){
        if (binding.getRoot().getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            selectedImage = true;
        }else {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Start the activity with camera_intent, and request pic id
            startActivityForResult(camera_intent, PICTURE_ID);
            selectedImage = true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(binding.getRoot().getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Start the activity with camera_intent, and request pic id
                startActivityForResult(camera_intent, PICTURE_ID);
                selectedImage = true;
            }
            else
            {
                Toast.makeText(binding.getRoot().getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == STORAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(binding.getRoot().getContext(), "storage permission granted", Toast.LENGTH_LONG).show();
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                // pass the constant to compare it
                // with the returned requestCode
                startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
                selectedImage = true;
            }
            else
            {
                Toast.makeText(binding.getRoot().getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                imageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    binding.imageView2.setImageURI(selectedImageUri);
                }
            }
            if (requestCode == PICTURE_ID) {
                // BitMap is data structure of image file which store the image in memory
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageUri = getImageUri(binding.getRoot().getContext(), photo);
                // Set the image in imageview for display
                binding.imageView2.setImageBitmap(photo);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void uploadFile() {
        Log.i("Here", "Bobo");
        if (imageUri != null) {

            // Defining the child of storageReference
            ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            UploadTask = ref.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> binding.progressBar.setProgress(0), 500);
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                insertProduct(uri.toString());
                            }
                        });
                        Toast.makeText(binding.getRoot().getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(binding.getRoot().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        binding.progressBar.setProgress((int) progress);
                    });
        } else {
            Toast.makeText(binding.getRoot().getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    public void insertProduct(String imageUrl){
        Product product = new Product();
        product.setName(binding.productName2.getText().toString());
        product.setBrand(binding.brand2.getText().toString());
        product.setPrice(Float.parseFloat(binding.priceTxt2.getText().toString()));
        product.setImageUrl(imageUrl);
        productViewModel.insert(product);
        clear();


    }

    public Boolean validateData(){
        if (binding.productName2.getText().toString().isEmpty() || binding.brand2.getText().toString().isEmpty() || binding.priceTxt2.getText().toString().isEmpty()
        || !selectedImage){
            return false;
        }
        return true;
    }



    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
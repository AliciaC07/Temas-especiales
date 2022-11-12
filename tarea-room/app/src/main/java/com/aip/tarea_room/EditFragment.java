package com.aip.tarea_room;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.aip.tarea_room.databinding.FragmentEditBinding;
import com.aip.tarea_room.databinding.FragmentRegisterProductBinding;
import com.aip.tarea_room.model.Product;
import com.aip.tarea_room.model.ProductViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentEditBinding binding;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    Integer SELECT_PICTURE = 200;
    Integer PICTURE_ID = 300;
    private static final int STORAGE = 200;
    private StorageReference storageReference;

    private Uri imageUri;
    private StorageTask UploadTask;
    private ProductViewModel productViewModel;
    private Boolean selectedImage = true;
    private StorageReference ref;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle re = getArguments();
        Product product = (Product) re.get("product");
        binding = FragmentEditBinding.inflate(inflater, container, false);

        binding.productName2edit.setText(product.getName());
        binding.brand2edit.setText(product.getBrand());
        binding.priceTxt2.setText(product.getPrice().toString());
        Picasso.get().load(product.getImageUrl())
                .into(binding.imageView2edit);

        binding.imageView2edit.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(binding.getRoot().getContext(), view);

            popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("Choose From Storage")){
                    chooseImage("image/*");
                } else if (item.getTitle().equals("Choose From Camera")) {
                    takeImage();
                } else if (item.getTitle().equals("Choose From Gallery")) {
                    chooseImage("image/*");
                }
//                Toast.makeText(binding.getRoot().getContext(),"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });

            popup.show();
        });
        binding.btnClearedit.setOnClickListener(view -> clear());


        return binding.getRoot();
    }

    public void clear(){
        binding.productName2edit.setText("");
        binding.brand2edit.setText("");
        binding.priceTxt2.setText("");
        binding.imageView2edit.setImageResource(android.R.color.transparent);
        selectedImage = false;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    void chooseImage(String type) {

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
        if (ActivityCompat.checkSelfPermission(binding.getRoot().getContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
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
                    binding.imageView2edit.setImageURI(selectedImageUri);
                }
            }
            if (requestCode == PICTURE_ID) {
                // BitMap is data structure of image file which store the image in memory
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageUri = getImageUri(binding.getRoot().getContext(), photo);
                // Set the image in imageview for display
                binding.imageView2edit.setImageBitmap(photo);
            }
        }
    }
    public Boolean validateData(){
        if (binding.productName2edit.getText().toString().isEmpty() || binding.brand2edit.getText().toString().isEmpty() || binding.priceTxt2.getText().toString().isEmpty()
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
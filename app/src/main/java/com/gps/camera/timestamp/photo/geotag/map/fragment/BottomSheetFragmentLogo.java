package com.gps.camera.timestamp.photo.geotag.map.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gps.camera.timestamp.photo.geotag.map.R;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.Default;
import com.gps.camera.timestamp.photo.geotag.map.camera.HelperClass;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Adepter.LogoAdapterGps;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Interface.OnRecyclerItemClickListener;
import com.facebook.FacebookSdk;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;

public class BottomSheetFragmentLogo extends BottomSheetDialogFragment {
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";
    public static final String TAG = "Logo_BottomSheetfragment";
    LogoAdapterGps GPSLogo_adapter;
    ImageView btn_add;
    Context context;
    ArrayList<String> image = new ArrayList<>();
    ImageView img_select;
    LinearLayout lin_recentview;
    C1281SP msp;
    OnSelectlogo onSelectlogo;
    ProgressBar progressBar;
    RecyclerView rv_image;

    public interface OnSelectlogo {
        void Onselect();
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint("RestrictedApi")
    @Override 
    public void setupDialog(Dialog dialog, int i) {
        super.setupDialog(dialog, i);
        View inflate = View.inflate(getContext(), R.layout.car_logo_bottom_sheet_fragment, null);
        this.rv_image = (RecyclerView) inflate.findViewById(R.id.rv_image);
        this.img_select = (ImageView) inflate.findViewById(R.id.img_select);
        this.btn_add = (ImageView) inflate.findViewById(R.id.btn_add);
        this.lin_recentview = (LinearLayout) inflate.findViewById(R.id.lin_recentview);
        this.progressBar = (ProgressBar) inflate.findViewById(R.id.lineprogressindicator);
        this.context = inflate.getContext();
        dialog.setContentView(inflate);
        init();
    }

    private void init() {
        this.msp = new C1281SP(getContext());
        this.image.clear();
        this.image = this.msp.loadImageuriArray(C1281SP.IMAGE_URI, getContext());
        String string = this.msp.getString(getContext(), "imge_logo", Default.LOGO_uri);
        this.btn_add.setOnClickListener(new View.OnClickListener() {
            

            public void onClick(View view) {
                BottomSheetFragmentLogo cAR_Logo_BottomSheetFragmentLogo = BottomSheetFragmentLogo.this;
                cAR_Logo_BottomSheetFragmentLogo.callImagePicker(cAR_Logo_BottomSheetFragmentLogo.getActivity());
            }
        });
        if (this.image.size() > 0) {
            this.lin_recentview.setVisibility(View.VISIBLE);
        } else {
            this.lin_recentview.setVisibility(View.GONE);
        }
        if (string.equals(Default.LOGO_uri)) {
            this.img_select.setImageResource(R.mipmap.ic_launcher);
        } else {
            this.img_select.setImageBitmap(HelperClass.decodeBase64(string));
        }
        setadapter();
    }

    private void setadapter() {
        this.rv_image.setLayoutManager(new GridLayoutManager((Context) getActivity(), 5, RecyclerView.VERTICAL, false));
        this.GPSLogo_adapter = new LogoAdapterGps(getContext(), this.image, new OnRecyclerItemClickListener() {
            

            @Override 
            public void OnClick_(int i, View view) {
                BottomSheetFragmentLogo.this.msp.setString(BottomSheetFragmentLogo.this.getContext(), "imge_logo", BottomSheetFragmentLogo.this.image.get(i));
                BottomSheetFragmentLogo.this.onSelectlogo.Onselect();
                BottomSheetFragmentLogo.this.dismiss();
            }

            @Override 
            public void OnLongClick_(int i, View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BottomSheetFragmentLogo.this.requireActivity());
                builder.setCancelable(false);
                builder.setTitle(BottomSheetFragmentLogo.this.requireActivity().getString(R.string.alert_delete_title));
                builder.setMessage(Html.fromHtml(BottomSheetFragmentLogo.this.getString(R.string.alert_delete_message)));
                builder.setPositiveButton(BottomSheetFragmentLogo.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    

                    public void onClick(DialogInterface dialogInterface, int i) {
                        BottomSheetFragmentLogo.this.deleteLogo(i);
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton(BottomSheetFragmentLogo.this.getString(R.string.no), new DialogInterface.OnClickListener() {
                    

                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        this.rv_image.post(new Runnable() {
            

            public void run() {
                BottomSheetFragmentLogo.this.progressBar.setVisibility(View.GONE);
                BottomSheetFragmentLogo.this.rv_image.setAdapter(BottomSheetFragmentLogo.this.GPSLogo_adapter);
            }
        });
    }

    
    
    private void deleteLogo(int i) {
        this.image.remove(i);
        if (this.image.size() == 0) {
            this.lin_recentview.setVisibility(View.GONE);
            this.img_select.setImageResource(R.mipmap.ic_launcher);
            this.msp.setString(getContext(), "imge_logo", Default.LOGO_uri);
            this.msp.saveImageuriArray(this.image, C1281SP.IMAGE_URI, getContext());
        } else {
            if (!this.image.contains(this.msp.getString(getContext(), "imge_logo", Default.LOGO_uri))) {
                this.msp.setString(getContext(), "imge_logo", this.image.get(i - 1));
            }
            this.img_select.setImageBitmap(HelperClass.decodeBase64(this.msp.getString(getContext(), "imge_logo", Default.LOGO_uri)));
            this.msp.saveImageuriArray(this.image, C1281SP.IMAGE_URI, getContext());
            setadapter();
        }
        this.onSelectlogo.Onselect();
    }

    private void callImagePicker(Activity activity) {
        Intent addCategory = new Intent("").setType("image/*").addCategory("android.intent.category.OPENABLE");
        addCategory.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/jpeg", "image/png"});
        startActivityForResult(addCategory, 103);
    }

    @Override 
    public void onActivityResult(int i, int i2, Intent intent) {
        Uri output;
        super.onActivityResult(i, i2, intent);
        if (i2 != 0) {
            if (i == 103 && i2 == -1 && intent != null) {
                Uri data = intent.getData();
                if (data != null) {
                    startCrop(data);
                }
            } else if (i == 69 && (output = UCrop.getOutput(intent)) != null) {
                try {
                    selectedImageGalary(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void selectedImageGalary(Uri uri) throws IOException {
        this.img_select.setImageURI(uri);
        String encodeTobase64 = HelperClass.encodeTobase64(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri));
        Collections.reverse(this.image);
        if (this.image.size() < 10) {
            this.image.add(encodeTobase64);
        } else {
            this.image.add(encodeTobase64);
            this.image.remove(0);
        }
        this.msp.setString(getContext(), "imge_logo", encodeTobase64);
        this.msp.saveImageuriArray(this.image, C1281SP.IMAGE_URI, getContext());
        this.onSelectlogo.Onselect();
        dismiss();
    }

    private void startCrop(final Uri uri) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            

            public void run() {
                UCrop useSourceImageAspectRatio = UCrop.of(uri, Uri.fromFile(new File(FacebookSdk.getCacheDir(), BottomSheetFragmentLogo.SAMPLE_CROPPED_IMAGE_NAME))).useSourceImageAspectRatio();
                UCrop.Options options = new UCrop.Options();
                options.setCompressionQuality(100);
                options.setHideBottomControls(false);
                options.setFreeStyleCropEnabled(true);
                useSourceImageAspectRatio.withOptions(options);
                useSourceImageAspectRatio.start(BottomSheetFragmentLogo.this.requireActivity());
            }
        });
    }
}

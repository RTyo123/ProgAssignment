package ua.kpi.comsys.iv8220.progassignment.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import ua.kpi.comsys.iv8220.progassignment.R;

public class GalleryFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 2;

    private static View root;
    private static ScrollView scrollView;
    private static LinearLayout scrollMain;
    private static ArrayList<ImageView> allImages;
    private static ArrayList<ArrayList<Object>> placeholderList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        scrollView = root.findViewById(R.id.scrollview_gallery);
        scrollMain = root.findViewById(R.id.linear_main);

        allImages = new ArrayList<>();
        placeholderList = new ArrayList<>();

        Button btnAddImage = root.findViewById(R.id.button_add_pic);
        btnAddImage.setOnClickListener(v -> {
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setType("image/*");
            startActivityForResult(gallery, RESULT_LOAD_IMAGE);
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK){
            Uri imageUri = data.getData();
            addImage(imageUri);
        }
    }

    private static void addImage(Uri imageUri) {
        ImageView newImage = new ImageView(root.getContext());
        newImage.setImageURI(imageUri);
        newImage.setBackgroundColor(Color.GRAY);
        ConstraintLayout.LayoutParams imageParams =
                new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
                        ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
        imageParams.dimensionRatio = "1";
        newImage.setLayoutParams(imageParams);
        newImage.setId(newImage.hashCode());
        setImagePlace(newImage, 9);

        allImages.add(newImage);
    }

    private static void setImagePlace(ImageView newImage, int all){
        ConstraintLayout tmpLayout = null;
        ConstraintSet tmpSet = null;
        if (allImages.size() > 0) {
            tmpLayout = (ConstraintLayout) getConstraintArrayList(0, placeholderList);
            if (allImages.size() % all != 0) {
                tmpLayout.addView(newImage);
            }
            tmpSet = (ConstraintSet) getConstraintArrayList(1, placeholderList);

            tmpSet.clone(tmpLayout);

            tmpSet.setMargin(newImage.getId(), ConstraintSet.START, 3);
            tmpSet.setMargin(newImage.getId(), ConstraintSet.TOP, 3);
            tmpSet.setMargin(newImage.getId(), ConstraintSet.END, 3);
            tmpSet.setMargin(newImage.getId(), ConstraintSet.BOTTOM, 3);
        }

        switch (allImages.size() % all){
            case 0:{
                placeholderList.add(new ArrayList<>());

                ConstraintLayout newConstraint = new ConstraintLayout(root.getContext());
                placeholderList.get(placeholderList.size()-1).add(newConstraint);
                newConstraint.setLayoutParams(
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                scrollMain.addView(newConstraint);

                Guideline vertical_33 = makeGuideline(ConstraintLayout.LayoutParams.VERTICAL,
                        0.333333f);
                Guideline vertical_66 = makeGuideline(ConstraintLayout.LayoutParams.VERTICAL,
                        0.666666f);

                Guideline horizontal_20 = makeGuideline(ConstraintLayout.LayoutParams.HORIZONTAL,
                        0.5f);
                Guideline horizontal_40 = makeGuideline(ConstraintLayout.LayoutParams.HORIZONTAL,
                        1f);
                Guideline horizontal_60 = makeGuideline(ConstraintLayout.LayoutParams.HORIZONTAL,
                        1f);
                Guideline horizontal_80 = makeGuideline(ConstraintLayout.LayoutParams.HORIZONTAL,
                        1f);

                newConstraint.addView(vertical_33, 0);
                newConstraint.addView(vertical_66, 1);
                newConstraint.addView(horizontal_20, 2);
                newConstraint.addView(horizontal_40, 3);
                newConstraint.addView(horizontal_60, 4);
                newConstraint.addView(horizontal_80, 5);

                newConstraint.addView(newImage);

                ConstraintSet newConstraintSet = new ConstraintSet();
                placeholderList.get(placeholderList.size()-1).add(newConstraintSet);
                newConstraintSet.clone(newConstraint);

                newConstraintSet.setMargin(newImage.getId(), ConstraintSet.START, 3);
                newConstraintSet.setMargin(newImage.getId(), ConstraintSet.TOP, 3);
                newConstraintSet.setMargin(newImage.getId(), ConstraintSet.END, 3);
                newConstraintSet.setMargin(newImage.getId(), ConstraintSet.BOTTOM, 3);

                connectInConstraint(newConstraintSet, newImage.getId(),
                        ConstraintSet.PARENT_ID, ConstraintSet.PARENT_ID,
                        vertical_66.getId(), horizontal_40.getId());

                newConstraintSet.applyTo(newConstraint);
                break;
            }

            case 1: {
                connectInConstraint(tmpSet, newImage.getId(),
                        tmpLayout.getChildAt(1).getId(), ConstraintSet.PARENT_ID,
                        ConstraintSet.PARENT_ID, tmpLayout.getChildAt(2).getId());

                tmpSet.applyTo(tmpLayout);
                break;
            }

            case 2: {
                connectInConstraint(tmpSet, newImage.getId(),
                        tmpLayout.getChildAt(1).getId(), tmpLayout.getChildAt(2).getId(),
                        ConstraintSet.PARENT_ID, tmpLayout.getChildAt(3).getId());

                tmpSet.applyTo(tmpLayout);
                break;
            }

            case 3: {
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(2).getId(), 0.333333f);
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(3).getId(), 0.666666f);

                connectInConstraint(tmpSet, newImage.getId(),
                        ConstraintSet.PARENT_ID, tmpLayout.getChildAt(3).getId(),
                        tmpLayout.getChildAt(0).getId(), tmpLayout.getChildAt(4).getId());

                tmpSet.applyTo(tmpLayout);
                break;
            }

            case 4: {
                connectInConstraint(tmpSet, newImage.getId(),
                        tmpLayout.getChildAt(0).getId(), tmpLayout.getChildAt(3).getId(),
                        tmpLayout.getChildAt(1).getId(), tmpLayout.getChildAt(4).getId());

                tmpSet.applyTo(tmpLayout);
                break;
            }

            case 5: {
                connectInConstraint(tmpSet, newImage.getId(),
                        tmpLayout.getChildAt(1).getId(), tmpLayout.getChildAt(3).getId(),
                        ConstraintSet.PARENT_ID, tmpLayout.getChildAt(4).getId());

                tmpSet.applyTo(tmpLayout);
                break;
            }

            case 6: {
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(2).getId(), 0.25f);
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(3).getId(), 0.5f);
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(4).getId(), 0.75f);

                connectInConstraint(tmpSet, newImage.getId(),
                        ConstraintSet.PARENT_ID, tmpLayout.getChildAt(4).getId(),
                        tmpLayout.getChildAt(0).getId(), tmpLayout.getChildAt(5).getId());

                tmpSet.applyTo(tmpLayout);
                break;
            }

            case 7: {
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(2).getId(), 0.2f);
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(3).getId(), 0.4f);
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(4).getId(), 0.6f);
                tmpSet.setGuidelinePercent(tmpLayout.getChildAt(5).getId(), 0.8f);

                connectInConstraint(tmpSet, newImage.getId(),
                        tmpLayout.getChildAt(0).getId(), tmpLayout.getChildAt(4).getId(),
                        ConstraintSet.PARENT_ID, ConstraintSet.PARENT_ID);

                tmpSet.applyTo(tmpLayout);
                break;
            }

            case 8: {
                connectInConstraint(tmpSet, newImage.getId(),
                        ConstraintSet.PARENT_ID, tmpLayout.getChildAt(5).getId(),
                        tmpLayout.getChildAt(0).getId(), ConstraintSet.PARENT_ID);

                tmpSet.applyTo(tmpLayout);
                break;
            }
        }
    }

    private static void connectInConstraint(ConstraintSet constraintSet, int mainView,
                                            int startView, int topView,
                                            int endView, int bottomView){
        constraintSet.connect(mainView, ConstraintSet.START,
                startView, startView == ConstraintSet.PARENT_ID ?
                                ConstraintSet.START : ConstraintSet.END);
        constraintSet.connect(mainView, ConstraintSet.TOP,
                topView, topView == ConstraintSet.PARENT_ID ?
                                ConstraintSet.TOP : ConstraintSet.BOTTOM);
        constraintSet.connect(mainView, ConstraintSet.END,
                endView, endView == ConstraintSet.PARENT_ID ?
                                ConstraintSet.END : ConstraintSet.START);
        constraintSet.connect(mainView, ConstraintSet.BOTTOM,
                bottomView, bottomView == ConstraintSet.PARENT_ID ?
                                ConstraintSet.BOTTOM : ConstraintSet.TOP);
    }

    private static Guideline makeGuideline(int orientation, float percent){
        Guideline guideline = new Guideline(root.getContext());
        guideline.setId(guideline.hashCode());

        ConstraintLayout.LayoutParams guideline_Params =
                new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
        guideline_Params.orientation = orientation;

        guideline.setLayoutParams(guideline_Params);

        guideline.setGuidelinePercent(percent);

        return guideline;
    }

    private static Object getConstraintArrayList(int index, ArrayList<ArrayList<Object>> list){
        return list.get(list.size()-1).get(index);
    }
}


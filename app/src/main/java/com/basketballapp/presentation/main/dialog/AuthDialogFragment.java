package com.basketballapp.presentation.main.dialog;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.basketballapp.R;
import com.basketballapp.presentation.auth.AuthActivity;

import eightbitlab.com.blurview.BlurView;

public class AuthDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_dialog, container, false);

        // Настройка BlurView
        BlurView blurView = view.findViewById(R.id.blurView);
        View decorView = requireActivity().getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);

        blurView.setOnClickListener(v ->
                dismiss());
        float radius = 20f;
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView) // Optionally pass RenderEffectBlur or RenderScriptBlur as the second parameter
                .setFrameClearDrawable(windowBackground) // Optional. Useful when your root has a lot of transparent background, which results in semi-transparent blurred content. This will make the background opaque
                .setBlurRadius(radius);
        blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        blurView.setClipToOutline(true);

        view.findViewById(R.id.btnLogin).setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AuthActivity.class);
            startActivity(intent);
            requireActivity().finish();
            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Устанавливаем параметры окна диалога
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }
}


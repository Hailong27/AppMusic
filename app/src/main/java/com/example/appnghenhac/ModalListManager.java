package com.example.appnghenhac;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class ModalListManager {
    private Dialog modal;

    public void showModal(Context context) {
        modal = new Dialog(context);
        modal.setContentView(R.layout.modal_list);
        modal.setCancelable(false);

        Button closeButton = modal.findViewById(R.id.btn_close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modal.dismiss();
            }
        });

        modal.show();

    }

    public void hideModal() {
        modal.dismiss();
    }
}

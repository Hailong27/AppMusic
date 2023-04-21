package com.example.appnghenhac;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModalAddListManager {
    private Dialog modal;

    public void showModal(Context context) {
        modal = new Dialog(context);
        modal.setContentView(R.layout.modal_add_list);
        modal.setCancelable(false);

        EditText title = modal.findViewById(R.id.modal_title);
        Button okButton = modal.findViewById(R.id.modal_ok_button);
        Button closeButton = modal.findViewById(R.id.modal_close_button);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
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

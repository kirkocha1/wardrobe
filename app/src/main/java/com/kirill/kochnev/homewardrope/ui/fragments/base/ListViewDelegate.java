package com.kirill.kochnev.homewardrope.ui.fragments.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;

public class ListViewDelegate<M extends IDbModel> {

    @NonNull
    private final Context context;

    public ListViewDelegate(@NonNull final Context context) {
        this.context = context;
    }

    public void onCreationStart() {

    }

//    @Override
//    public void onLongClick(final M model) {
//        if (isFullPart()) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setMessage(context.getString(R.string.delete_message))
//                    .setPositiveButton(context.getString(R.string.yes), (dialog, which) -> {
//                        getPresenter().onLongItemClick(model);
//                        dialog.dismiss();
//                    });
//            builder.create().show();
//        }
//    }

}

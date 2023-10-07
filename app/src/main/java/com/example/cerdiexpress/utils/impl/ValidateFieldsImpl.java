package com.example.cerdiexpress.utils.impl;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cerdiexpress.activities.RequestCreateActivity;
import com.example.cerdiexpress.utils.IValidateFields;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ValidateFieldsImpl implements IValidateFields {
    @Override
    public boolean validateTextView(List<TextView> textViewList, Context context) {

        AtomicBoolean flag = new AtomicBoolean(true);
        textViewList.forEach(item -> {
            if(Objects.isNull(item.getText()) || item.getText().toString().equals("")
                    || item.getText().toString().equals(" ")){
                flag.set(false);
                Toast.makeText(context,
                        "Atributo requerido " +  item.getHint().toString().toLowerCase(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return flag.get();
    }
}

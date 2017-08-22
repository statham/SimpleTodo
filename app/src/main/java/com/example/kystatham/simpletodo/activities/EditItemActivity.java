package com.example.kystatham.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    int position;
    EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        position = getIntent().getIntExtra("position", 0);
        String itemText = getIntent().getStringExtra("itemText");
        etEditItem = (EditText)findViewById(R.id.etEditItem);
        etEditItem.setText(itemText);
        etEditItem.setSelection(etEditItem.getText().length());
    }

    public void onSubmit(View v) {
        etEditItem = (EditText)findViewById(R.id.etEditItem);
        String itemText = etEditItem.getText().toString();
        Intent data = new Intent();
        data.putExtra("position", position);
        data.putExtra("itemText", itemText);
        setResult(RESULT_OK, data);
        finish();
    }
}

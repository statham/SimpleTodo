package com.example.kystatham.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kystatham.simpletodo.R;
import com.example.kystatham.simpletodo.adapters.TaskArrayAdapter;
import com.example.kystatham.simpletodo.models.Task;
import com.example.kystatham.simpletodo.models.Task_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> items;
    TaskArrayAdapter<Task> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<>();
        readFromDatabase();
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        itemsAdapter = new TaskArrayAdapter<Task>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewRemoveListener();
        setupListViewEditLIstener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int pos = data.getExtras().getInt("position");
            int taskId = items.get(pos).getId();
            Task task = SQLite.select()
                    .from(Task.class)
                    .where(Task_Table.id.eq(taskId))
                    .querySingle();
            String itemText = data.getExtras().getString("itemText");
            task.setText(itemText);
            task.save();
            readFromDatabase();
            itemsAdapter.notifyDataSetChanged();
        }
    }

    public void setupListViewRemoveListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        int taskId = items.get(pos).getId();
                        Task task = SQLite.select()
                                .from(Task.class)
                                .where(Task_Table.id.eq(taskId))
                                .querySingle();
                        task.delete();
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
    }

    public void setupListViewEditLIstener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                               View item, int pos, long id) {
                        Intent edit = new Intent(MainActivity.this, EditItemActivity.class);
                        edit.putExtra("position", pos);
                        Task task = items.get(pos);
                        edit.putExtra("itemText", task.getText());
                        startActivityForResult(edit, REQUEST_CODE);
                    }
                }
        );
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        Task task = new Task();
        task.setId(items.size());
        task.setText(itemText);
        task.save();
        itemsAdapter.add(task);
        etNewItem.setText("");
    }

    private void readFromDatabase() {
        List<Task> tasks = SQLite.select().from(Task.class).queryList();
        items.clear();
        items.addAll(new ArrayList<Task>(tasks));
    }
}

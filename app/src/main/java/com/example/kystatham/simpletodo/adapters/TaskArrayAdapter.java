package com.example.kystatham.simpletodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kystatham.simpletodo.R;
import com.example.kystatham.simpletodo.models.Task;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;

public class TaskArrayAdapter<T extends BaseModel> extends ArrayAdapter<Task> {
    public TaskArrayAdapter(Context context, int textViewResourceId, ArrayList<Task> objects) {
        super(context, textViewResourceId, objects);
    }

    private static class ViewHolder {
        private TextView taskView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.taskView = (TextView) convertView.findViewById(R.id.task_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Task task = (Task) getItem(position);
        if (task!= null) {
            viewHolder.taskView.setText(task.getText());
        }

        return convertView;
    }
}

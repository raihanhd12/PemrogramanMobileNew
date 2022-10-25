package anandaayusekarwiranti.example.miniproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anandaayusekarwiranti.example.miniproject.R;
import anandaayusekarwiranti.example.miniproject.activity.MainActivity;
import anandaayusekarwiranti.example.miniproject.object.Tasks;

public class TaskAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Tasks> tasksList;

    public TaskAdapter(MainActivity context, int layout, List<Tasks> tasksList) {
        this.context = context;
        this.layout = layout;
        this.tasksList = tasksList;
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView textViewNameTask;
        ImageView imageViewEditTask, imageViewRemoveTask;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.textViewNameTask = view.findViewById(R.id.textViewNameOfTask);
            holder.imageViewEditTask = view.findViewById(R.id.imageViewEditTask);
            holder.imageViewRemoveTask = view.findViewById(R.id.imageViewRemoveTask);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        final Tasks task = tasksList.get(i);
        holder.textViewNameTask.setText(task.getTaskName());

        // untuk mengeset tombol clik edit
        holder.imageViewEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogEdit(task.getTaskName(),task.getIdTask());
            }
        });
        // untuk mengeset tombol remove edit
        holder.imageViewRemoveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogRemoveTask(task.getTaskName(), task.getIdTask());
            }
        });
        return view;
    }
}

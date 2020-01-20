package androidkotlin.canovasc.todoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<Task> taskList;
    private Context context;
    private OnTaskListener mOnTaskListener;

    TaskAdapter(ArrayList<Task> taskList, Context context, OnTaskListener onTaskListener){
        this.taskList = taskList;
        this.context = context;
        this.mOnTaskListener = onTaskListener;
    }


    //Inflate the layout of the RecyclerView

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task,parent,false);

        return new ViewHolder(v,mOnTaskListener);
    }


    //Binds the Views and the data together

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {

        holder.taskName_et.setText(taskList.get(position).getTaskName());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    //Initiate the Views

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        EditText taskName_et;
        RadioButton task_rbtn;
        RelativeLayout foreground_rl,background_rl;
        FrameLayout recyclerview_fl;
        OnTaskListener mOnTaskListener;

        public ViewHolder(@NonNull View itemView, OnTaskListener onTaskListener) {
            super(itemView);

            taskName_et = itemView.findViewById(R.id.recyclerview_taskname_et);
            task_rbtn = itemView.findViewById(R.id.task_radiobtn);
            foreground_rl = itemView.findViewById(R.id.recyclerview_foreground_rl);
            background_rl = itemView.findViewById(R.id.recyclerview_background__rl);
            recyclerview_fl = itemView.findViewById(R.id.recyclerview_fl);

            this.mOnTaskListener = onTaskListener;

            recyclerview_fl.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnTaskListener.onTaskClick(getAdapterPosition());
            Log.i("ClickTest","Click!");
        }
    }

    public interface OnTaskListener{
        void onTaskClick(int position);
    }
}

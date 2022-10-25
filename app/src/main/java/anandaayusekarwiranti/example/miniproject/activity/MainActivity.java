package anandaayusekarwiranti.example.miniproject.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import anandaayusekarwiranti.example.miniproject.R;
import anandaayusekarwiranti.example.miniproject.adapter.TaskAdapter;
import anandaayusekarwiranti.example.miniproject.database.Database;
import anandaayusekarwiranti.example.miniproject.object.Tasks;

public class MainActivity extends AppCompatActivity {

    Database database;
    ListView listViewTasks;
    ArrayList<Tasks> tasksArrayList;
    TaskAdapter taskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mapping
        listViewTasks = findViewById(R.id.listViewTasks);
        tasksArrayList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this,R.layout.task_line,tasksArrayList);
        listViewTasks.setAdapter(taskAdapter);
        // membuat database pada saat aplikasi baru diinstall
        database = new Database(this,"note.sqlite",null,1);
        // menambahkan tabel pada database note.sqlite
        database.QueryData("CREATE TABLE IF NOT EXISTS Tasks(Id INTEGER PRIMARY KEY AUTOINCREMENT, TaskName VARCHAR(200) )");

       GetDataTask();
    }

    private  void GetDataTask(){
        // mengambil data dari tabel database dan dikembalikan dalam bentuk tabel
        Cursor dataTask = database.GetData("SELECT * FROM Tasks");
        tasksArrayList.clear();
        while (dataTask.moveToNext()){
            String taskName = dataTask.getString(1);
            int id = dataTask.getInt(0);
            tasksArrayList.add(new Tasks(id,taskName));
        }
        taskAdapter.notifyDataSetChanged();
    }

    // Membuat layout menu ketika class(ditunjukkkan dari super) ini dijalankan pertama kali
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.add_task,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() ==  R.id.menuAddTask){
            DialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }

    // dialog untuk penambahan list data baru
    private void DialogAdd(){
        Dialog dialogAdd = new Dialog(this);
        dialogAdd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdd.setContentView(R.layout.dialog_add_task);

        EditText editTextNameTask = dialogAdd.findViewById(R.id.editTextAddTask);
        Button btnAddTask = dialogAdd.findViewById(R.id.btnAddTask);
        Button btnCancelTask = dialogAdd.findViewById(R.id.btnCancelTask);

        // mengatur tombol add onclick pada dialog tambah data list
        btnAddTask.setOnClickListener(view -> {
            String taskName = editTextNameTask.getText().toString();
            if(taskName.equals("")){
                Toast.makeText(MainActivity.this, "Please enter the name of task!", Toast.LENGTH_SHORT).show();
            }else {
                // menambahkan baris data yang barusan diinputkan pada tabel
                database.QueryData("INSERT INTO Tasks VALUES(null, '"+taskName+"')");
                Toast.makeText(MainActivity.this, "Task "+taskName+" was added!", Toast.LENGTH_SHORT).show();
                dialogAdd.dismiss();
                GetDataTask();
            }
        });

        // mengatur tombol cancel pada dialog
        btnCancelTask.setOnClickListener(view -> dialogAdd.dismiss());

        dialogAdd.show();
    }

    // dialog untuk melakukan pengeditan data
    public void DialogEdit(String nameTask, int id){
        Dialog dialogEdit = new Dialog(this);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.dialog_edit_task);

        EditText editTextRenameTask = dialogEdit.findViewById(R.id.editTextRenameTask);
        Button btnConfirmEditTask = dialogEdit.findViewById(R.id.btnConfirmTask);
        Button btnCancelEditTask = dialogEdit.findViewById(R.id.btnCancelEditTask);

        editTextRenameTask.setText(nameTask);

        // mengatur tombol confrim pada dialog edit
        btnConfirmEditTask.setOnClickListener(view -> {
            String taskRename = editTextRenameTask.getText().toString().trim();
            database.QueryData("UPDATE Tasks SET TaskName = '"+taskRename+"' WHERE Id = '"+id+"' ");
            Toast.makeText(MainActivity.this, "Task "+ taskRename +"was updated!", Toast.LENGTH_SHORT).show();
            dialogEdit.dismiss();
            GetDataTask();
        });
        // mengator tombol cancel pada dialog edit
        btnCancelEditTask.setOnClickListener(view -> dialogEdit.dismiss());

        dialogEdit.show();
    }

    // dialog untuk melakukan penghapusan data list
    public void DialogRemoveTask(String taskName, int id){
        AlertDialog.Builder dialogRemoveTask = new AlertDialog.Builder(this);
        dialogRemoveTask.setMessage("Do you want to remove "+taskName+"?");
        // mengatur tombol confirm penghapusan data list
        dialogRemoveTask.setNegativeButton("Yes", (dialogInterface, i) -> {
            database.QueryData("DELETE FROM Tasks WHERE Id = '"+id+"'");
            Toast.makeText(MainActivity.this, taskName+" was removed!", Toast.LENGTH_SHORT).show();
            GetDataTask();
        });
        // mengatur tombol cencel penghapusan data list
        dialogRemoveTask.setPositiveButton("No", (dialogInterface, i) -> {
            // pada proses ini tidak melakukan apa-apa dan langsung kembali pada menu.
        });

        dialogRemoveTask.show();
    }
}
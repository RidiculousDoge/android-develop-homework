package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Priority;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract.TodoNote;
import com.byted.camp.todolist.db.TodoDbHelper;

import java.util.Calendar;


public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private RadioGroup radioGroup;
    private AppCompatRadioButton lowRadio;
    private DatePicker ddl_date;

    private int ddl_year= Calendar.getInstance().get(Calendar.YEAR);
    private int ddl_month=Calendar.getInstance().get(Calendar.MONTH);
    private int ddl_day=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

    private TodoDbHelper dbHelper;
    private SQLiteDatabase database;
    private boolean updataFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);
        final Intent intent=getIntent();

        dbHelper = new TodoDbHelper(this);
        database = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.edit_text);
        editText.setText(intent.getStringExtra("contentText"));
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }
        radioGroup = findViewById(R.id.radio_group);
        lowRadio = findViewById(R.id.btn_low);
        lowRadio.setChecked(true);

        ddl_date=findViewById(R.id.ddl_date);
        addBtn = findViewById(R.id.btn_add);
        ddl_date.init(ddl_year, ddl_month, ddl_day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                NoteActivity.this.ddl_year=year;
                NoteActivity.this.ddl_month=monthOfYear;
                NoteActivity.this.ddl_day=dayOfMonth;
                Log.d("ddl_date_change","选定日期"+NoteActivity.this.ddl_year+"年" +
                        (NoteActivity.this.ddl_month+1)+"月" + NoteActivity.this.ddl_day+"日");
            }
        });
        ddl_date.setMinDate(System.currentTimeMillis());

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                String ddl_date=NoteActivity.this.ddl_year+"-"+(NoteActivity.this.ddl_month+1)+"-"+NoteActivity.this.ddl_day;
                boolean succeed;
                //根据传进来intent的类型判断是修改还是新增
                updataFlag=(intent.getStringExtra("isModification")!=null);

                if(updataFlag){
                    Log.d("database","modify called");
                    Log.d("database_text_date",String.valueOf(intent.getStringExtra("text_date")));
                    succeed=modifyNote2Database(content.toString().trim(),
                            getSelectedPriority(),ddl_date,intent.getLongExtra("id",-1));

                    Log.d("database","result: "+succeed);
                }
                else {
                    Log.d("database","modify uncalled");
                    succeed = saveNote2Database(content.toString().trim(),
                            getSelectedPriority(),ddl_date);
                }
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();

                    Toast.makeText(NoteActivity.this,
                            "选定日期"+NoteActivity.this.ddl_year+"年"
                    +(NoteActivity.this.ddl_month+1)+"月"
                            +NoteActivity.this.ddl_day+"日",Toast.LENGTH_SHORT).show();
                    Log.d("ddl_date","选定日期"+NoteActivity.this.ddl_year+"年" +
                            (NoteActivity.this.ddl_month+1)+"月" + NoteActivity.this.ddl_day+"日");
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        database = null;
        dbHelper.close();
        dbHelper = null;
    }

    private boolean modifyNote2Database(String content,Priority priority,String ddl_date,Long id){
        /*
        * param:pos_date: 根据pos_date（待修改的todo上一次修改的时间），在数据库中找满足条件的记录并更新
        * */
        if(database==null || TextUtils.isEmpty(content)){
            return false;
        }
        ContentValues values=new ContentValues();
        values.put(TodoNote.COLUMN_CONTENT,content);
        values.put(TodoNote.COLUMN_STATE, State.TODO.intValue);
        values.put(TodoNote.COLUMN_DATE, System.currentTimeMillis());
        values.put(TodoNote.COLUMN_PRIORITY, priority.intValue);
        values.put(TodoNote.COLUMN_DDL_DATE,ddl_date);

        String selection=TodoNote._ID+" LIKE ?";
        String[] selectionArgs={String.valueOf(id)};
        Log.d("database_id",String.valueOf(id));
        //update语句执行成功返回1
        int count=database.update(TodoNote.TABLE_NAME,values,selection,selectionArgs);
        Log.d("database",String.valueOf(count));
        return count==1;
    }
    private boolean saveNote2Database(String content, Priority priority,String ddl_date) {
        if (database == null || TextUtils.isEmpty(content)) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(TodoNote.COLUMN_CONTENT, content);
        values.put(TodoNote.COLUMN_STATE, State.TODO.intValue);
        String tmp=String.valueOf(System.currentTimeMillis());
        values.put(TodoNote.COLUMN_DATE, tmp);
        values.put(TodoNote.COLUMN_PRIORITY, priority.intValue);
        values.put(TodoNote.COLUMN_DDL_DATE,ddl_date);
        Log.d("ddl","after puting value into ddl_date_column");
        Log.d("ddl","ddl_date: "+ ddl_date);
        long rowId = database.insert(TodoNote.TABLE_NAME, null, values);
        return rowId != -1;
    }

    private Priority getSelectedPriority() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.btn_high:
                return Priority.High;
            case R.id.btn_medium:
                return Priority.Medium;
            default:
                return Priority.Low;
        }
    }
}

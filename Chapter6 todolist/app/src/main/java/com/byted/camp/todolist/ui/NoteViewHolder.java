package com.byted.camp.todolist.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.byted.camp.todolist.NoteActivity;
import com.byted.camp.todolist.NoteOperator;
import com.byted.camp.todolist.R;
import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);

    private final NoteOperator operator;
    private Note note;
    private CheckBox checkBox;
    private TextView contentText;
    private TextView dateText;
    private View deleteBtn;
    private TextView ddl_date_text;

    public NoteViewHolder(@NonNull View itemView, NoteOperator operator) {
        super(itemView);
        this.operator = operator;
        itemView.setOnLongClickListener(this);

        checkBox = itemView.findViewById(R.id.checkbox);
        contentText = itemView.findViewById(R.id.text_content);
        dateText = itemView.findViewById(R.id.text_date);
        deleteBtn = itemView.findViewById(R.id.btn_delete);
        ddl_date_text=itemView.findViewById(R.id.ddl_date);
    }
    @Override
    public boolean onLongClick(View v){
        Log.d("debug","onLongClick called");
        Intent intent=new Intent(v.getContext(), NoteActivity.class);
        intent.putExtra("contentText",contentText.getText());
        intent.putExtra("text_date",dateText.getText());
        Log.d("database_holder_origin_view",String.valueOf(dateText.getText()));
        Log.d("database_holder_view",String.valueOf(intent.getStringExtra("text_date")));
        intent.putExtra("ddl_text_date",ddl_date_text.getText());
        intent.putExtra("isModification","true");
        intent.putExtra("id",note.id);
        v.getContext().startActivity(intent);
        return true;
    }

    public void bind(final Note note) {
        this.note=note;
        contentText.setText(note.getContent());
        dateText.setText(SIMPLE_DATE_FORMAT.format(note.getDate()));
        ddl_date_text.setText("ddl: "+note.getDdl_date());


        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(note.getState() == State.DONE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                note.setState(isChecked ? State.DONE : State.TODO);
                operator.updateNote(note);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator.deleteNote(note);
            }
        });

        if (note.getState() == State.DONE) {
            contentText.setTextColor(Color.GRAY);
            contentText.setPaintFlags(contentText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            contentText.setTextColor(Color.BLACK);
            contentText.setPaintFlags(contentText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        itemView.setBackgroundColor(note.getPriority().color);
    }
}

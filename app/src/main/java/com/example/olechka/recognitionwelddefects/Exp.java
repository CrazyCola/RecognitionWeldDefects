//package com.example.olechka.recognitionwelddefects;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//
//import java.util.List;
//
//public class Exp extends AppCompatActivity {
//
//    List<Todo> todo1;
//    ArrayAdapter <Todo> adapter;
//    ListView lvTodo;
//    EditText estAddTodo;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exp);
//
//        todo1 =Todo.listAll(Todo.class);
//        adapter = new ArrayAdapter<>(this, R.layout.singlelist, R.id.idTodo, todo1);
//
//        lvTodo= (ListView)findViewById(R.id.listView);
//        lvTodo.setAdapter(adapter);
//
//        estAddTodo = (EditText) findViewById(R.id.editTodo);
//
//    }
//
//    public  void btnAddClick (View view){
//        Todo todo = new Todo();
//        todo.setTodo(estAddTodo.getText().toString());
//        todo.save();
//        adapter.add(todo);
//        estAddTodo.getText().clear();
//    }
//}

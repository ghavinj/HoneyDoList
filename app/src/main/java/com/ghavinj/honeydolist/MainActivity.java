package com.ghavinj.honeydolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private Button saveButton;
    private EditText listEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listEditText = (EditText)findViewById(R.id.todoListEditText);
        saveButton = (Button)findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code goes here
                if (!listEditText.getText().toString().equals("")){
                    String message = listEditText.getText().toString();
                    writeToFile(message);
                }else{
                    //do nothing for now.
                }
            }
        });

        try {
            if (readFromFile() != null){
                listEditText.setText(readFromFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeToFile(String message){

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("todolist.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(message);
            outputStreamWriter.close(); // Always close the streams!

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readFromFile() throws IOException {

        String result = "";
        InputStream inputStream = openFileInput("todolist.txt");

        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String tempString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((tempString =  bufferedReader.readLine()) != null){
                stringBuilder.append(tempString + "\n\n");
            }

            inputStream.close();
            result = stringBuilder.toString();
        }

        return result;
    }
}

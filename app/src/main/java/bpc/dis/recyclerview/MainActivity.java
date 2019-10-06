package bpc.dis.recyclerview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            strings.add("test item: " + i);
        }
//        TestAdapter testAdapter = new TestAdapter(this, strings);
//        BpcRecyclerView bpcRecyclerView = findViewById(R.id.rv_bpc);
//        bpcRecyclerView.setAdapter(testAdapter);
    }

}

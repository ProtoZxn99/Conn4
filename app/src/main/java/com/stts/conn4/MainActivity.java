package com.stts.conn4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button easier, harder, play, play2;
    private TextView leveltitle;
    private ImageView levelimage;
    private CheckBox ab;
    int selection = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leveltitle = findViewById(R.id.leveltitle);
        levelimage = findViewById(R.id.levelimage);

        easier = findViewById(R.id.easier);
        easier.setEnabled(false);
        easier.setBackgroundColor(Color.parseColor("#ff9999"));
        easier.setTextColor(Color.parseColor("#919191"));

        harder = findViewById(R.id.harder);
        play = findViewById(R.id.play);
        play2 = findViewById(R.id.play2);
        ab = findViewById(R.id.checkBoxab);
        ab.setEnabled(false);

        easier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection--;
                if(selection==8){
                    selection=3;
                }
                replacelevel();
            }
        });

        harder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection++;
                replacelevel();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                int dif = selection;
                if(dif>3&&dif<9){
                    dif = 3;
                }
                else if(dif>=9){
                    dif = 4;
                }
                i.putExtra("dif", dif+"");
                i.putExtra("player", "0");

                if(ab.isChecked()){
                    i.putExtra("ab", "1");
                }
                else{
                    i.putExtra("ab", "0");
                }
                startActivity(i);
            }
        });

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                int dif = selection;
                if(dif>3&&dif<9){
                    dif = 3;
                }
                else if(dif>=9){
                    dif = 4;
                }
                i.putExtra("dif", dif+"");
                i.putExtra("player", "1");

                if(ab.isChecked()){
                    i.putExtra("ab", "1");
                }
                else{
                    i.putExtra("ab", "0");
                }
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.hs){
            startActivity(new Intent(MainActivity.this, Highscore.class));
        }
        return true;
    }

    private void replacelevel(){
        if(selection==0){
            leveltitle.setText("Human besides you");
            levelimage.setImageResource(R.drawable.ic_launcher_foreground);
            play2.setVisibility(View.INVISIBLE);
            play2.setEnabled(false);
            easier.setEnabled(false);
            easier.setBackgroundColor(Color.parseColor("#ff9999"));
            easier.setTextColor(Color.parseColor("#919191"));
            ab.setChecked(false);
            ab.setEnabled(false);
            //ab.setTextColor(Color.parseColor("#515151"));
        }
        else if(selection==1){
            leveltitle.setText("Easy");
            levelimage.setImageResource(R.drawable.arc);
            play2.setVisibility(View.VISIBLE);
            play2.setEnabled(true);
            easier.setEnabled(true);
            easier.setTextColor(Color.parseColor("#ffffff"));
            easier.setBackgroundColor(Color.parseColor("#ff0000"));
            ab.setChecked(false);
            ab.setEnabled(false);
            //ab.setTextColor(Color.parseColor("#515151"));
        }
        else if(selection==2){
            leveltitle.setText("Medium");
            levelimage.setImageResource(R.drawable.tesla);
            play2.setVisibility(View.VISIBLE);
            play2.setEnabled(true);
            ab.setEnabled(true);
            ab.setChecked(true);
            //ab.setTextColor(Color.parseColor("#FFFFFF"));
        }
        else if(selection==9){
            leveltitle.setText("Impossible");
            levelimage.setImageResource(R.drawable.yosi);
            play2.setVisibility(View.VISIBLE);
            play2.setEnabled(true);
            harder.setEnabled(false);
            harder.setBackgroundColor(Color.parseColor("#ff9999"));
            harder.setTextColor(Color.parseColor("#919191"));
            ab.setChecked(false);
            ab.setEnabled(false);
            //ab.setTextColor(Color.parseColor("#515151"));
        }
        else{
            leveltitle.setText("Hard");
            levelimage.setImageResource(R.drawable.einstein);
            play2.setVisibility(View.VISIBLE);
            play2.setEnabled(true);
            harder.setEnabled(true);
            harder.setTextColor(Color.parseColor("#ffffff"));
            harder.setBackgroundColor(Color.parseColor("#ff0000"));
            ab.setEnabled(true);
            ab.setChecked(true);
            //ab.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }
}

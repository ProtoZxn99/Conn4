package com.stts.conn4;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private LinearLayout superboard;
    private LinearLayout realboard;
    private ImageView imgchoice, imglevel;
    private TextView leveltitle;
    private int imgw;
    private static int board_width;
    private static int board_height;
    private static int win_requires;
    public Point ss;
    private boolean [][][] board_val;
    private int [] board_col;
    private int pc = 0;
    private int ab = 0;
    private int turn = 0;
    private boolean done = false;
    private int move4 = 0;
    int dif = 0;

    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = Room.databaseBuilder(getApplicationContext(), DB.class, "recorddb").build();

        board_height = Global.board_height;
        board_width = Global.board_width;
        win_requires = Global.win_requires;
        board_val = new boolean[3][board_height][board_width];
        board_col = new int[board_width];

        dif = Integer.parseInt(getIntent().getStringExtra("dif"));
        pc = Integer.parseInt(getIntent().getStringExtra("player"));
        ab = Integer.parseInt(getIntent().getStringExtra("ab"));

        superboard = findViewById(R.id.superboard);
        ss = new Point();
        getWindowManager().getDefaultDisplay().getSize(ss);

        imgw = (int) Math.floor(ss.x/board_width);

        imgchoice = findViewById(R.id.imgchoice);
        imgchoice.setImageResource(R.drawable.yellow);
        imgchoice.getLayoutParams().height = imgw;
        imgchoice.getLayoutParams().width = imgw;

        imglevel = findViewById(R.id.imglevel);
        leveltitle = findViewById(R.id.leveltitle);

        if(dif==1){
            imglevel.setImageResource(R.drawable.arc);
            leveltitle.setText("Easy:\nArchimedes");
        }
        else if(dif==2){
            imglevel.setImageResource(R.drawable.tesla);
            leveltitle.setText("Medium:\nNikola Tesla");
        }
        else if(dif==3){
            imglevel.setImageResource(R.drawable.einstein);
            leveltitle.setText("Hard:\nAlbert Einstein");
        }
        else if(dif==4){
            imglevel.setImageResource(R.drawable.yosi);
            leveltitle.setText("Impossible:\nYosi Kristian\nlinkedin.com/in/yosi-kristian-54b303b");
        }
        else{
            imglevel.setImageResource(R.drawable.ic_launcher_foreground);
            leveltitle.setText("PvP");
        }

        superboard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!done){
                    if(turn%2==0){
                        imgchoice.setImageResource(R.drawable.yellow);
                    }
                    else{
                        imgchoice.setImageResource(R.drawable.red);
                    }
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imgchoice.getLayoutParams();

                    int x_cord = (int) event.getRawX()-imgw/2;

                    if (x_cord < 1){
                        x_cord = 0;
                    }
                    else if (x_cord > ss.x-imgw){
                        x_cord = ss.x-imgw;
                    }

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_UP:
                            imgchoice.setVisibility(View.INVISIBLE);
                            boolean nextturn = put((int) Math.floor(event.getRawX()/imgw));
                            if(nextturn&&!done){
                                ai();
                            }
                            break;

                        case MotionEvent.ACTION_DOWN:

                            layoutParams.leftMargin = x_cord;

                            imgchoice.setLayoutParams(layoutParams);
                            imgchoice.setVisibility(View.VISIBLE);
                            break;

                        case MotionEvent.ACTION_MOVE:

                            layoutParams.leftMargin = x_cord;

                            imgchoice.setLayoutParams(layoutParams);
                            break;
                        default:
                            break;
                    }
                }
                return true;
            }
        });

        resetBoard();

        if(pc==1){
            if(dif==1||dif==2||dif==3){
                int mid = (int)Math.floor(board_width/2)-1;
                if(board_width%2==1){
                    mid++;
                }
                board_val[0][board_height-1][mid]=true;
                board_val[2][board_height-1][mid]=true;
                board_col[mid]--;
                turn++;
            }
            else if(dif==4){
                board_val[0][board_height-1][0] = true;
                board_val[2][board_height-1][0] = true;
                board_col[0]=board_height-2;
                move4++;
                turn++;
            }
        }
        else if(dif==4){
            board_val[(pc+1)%2][board_height-1][0] = true;
            board_val[2][board_height-1][0] = true;
            board_col[0]--;
            move4++;
        }
        printBoard();
    }

    private void resetBoard(){
        for (boolean[][] a : board_val){
            for (boolean[] b : a){
                for (boolean c : b){
                    c = false;
                }
            }
        }
        for (int i=0; i<board_col.length; i++){
            board_col[i] = board_height-1;
        }
    }

    private void printBoard(){
        realboard = findViewById(R.id.realboard);
        realboard.removeAllViews();
        for (int i = 0; i<board_height; i++){
            LinearLayout l = new LinearLayout(this);
            l.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,imgw));
            l.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j<board_width; j++){
                ImageView temp = new ImageView(this);
                temp.setImageResource(R.drawable.ic_launcher_background);
                if(board_val[0][i][j]){
                    temp.setImageResource(R.drawable.yellow);
                }
                else if(board_val[1][i][j]){
                    temp.setImageResource(R.drawable.red);
                }
                temp.setLayoutParams(new LinearLayout.LayoutParams(imgw, imgw));
                l.addView(temp);
            }
            realboard.addView(l);
        }
        realboard.invalidate();
    }

    private boolean put(int x){
        int choice = x;
        if(board_col[choice]>=0){
            board_val[(turn)%2][board_col[choice]][choice] = true;
            board_val[2][board_col[choice]][choice] = true;
            board_col[choice]--;
        }
        else{
            Toast.makeText(this,"Illegal move", Toast.LENGTH_SHORT).show();
            return false;
        }
        check(turn%2);
        turn++;
        printBoard();
        return true;
    }

    private void ai(){
        if(dif>0){
            if(dif==1){
                ExpectiMax m = new ExpectiMax((pc+1)%2, Global.easy_ply);
                int best = m.findmove(new Board(board_val, pc, 0, -1));
                put(best);
            }
            else if(dif==2){
                if(ab==0){
                    MiniMax m = new MiniMax((pc+1)%2,Global.medium_ply);
                    int best = m.findmove(new Board(board_val, pc, 0, -1));
                    put(best);
                }
                else{
                    MiniMaxAB m = new MiniMaxAB((pc+1)%2,Global.medium_ply);
                    int best = m.findmove(new Board(board_val, pc, 0, -1));
                    put(best);
                }
            }
            else if(dif==3){
                if(ab==0){
                    MiniMax m = new MiniMax((pc+1)%2,Global.hard_ply);
                    int best = m.findmove(new Board(board_val, pc, 0, -1));
                    put(best);
                }
                else{
                    MiniMaxAB m = new MiniMaxAB((pc+1)%2,Global.hard_ply);
                    int best = m.findmove(new Board(board_val, pc, 0, -1));
                    put(best);
                }
            }
            else if(dif==4){
                Toast.makeText(this,"Definitely a legal move", Toast.LENGTH_SHORT).show();
                board_val[pc][board_height-1][move4] = false;
                board_val[(pc+1)%2][board_height-1][move4] = true;
                board_val[2][board_height-1][move4] = true;
                board_col[move4]=6;
                if(move4<win_requires-1){
                    move4++;
                }
                check(turn%2);
                printBoard();
                turn++;
            }
        }
    }

    private void check(int pl){
        boolean mate = false;
        if(turn >= board_height*board_width){
            Toast.makeText(Main2Activity.this, "Tie", Toast.LENGTH_LONG).show();
            done = true;
        }
        else{
            for (int i = 0; i<board_height; i++){
                for(int j = 0; j<=board_width-win_requires; j++ ){
                    mate = board_val[pl][i][j];
                    for(int k=1; k<win_requires; k++){
                        mate = mate && board_val[pl][i][j+k];
                    }
                    if(mate){
                        Toast.makeText(Main2Activity.this, "Player "+pl+" wins", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if(mate){
                    break;
                }
            }
            if(!mate){
                for (int i = 0; i<=board_height-win_requires; i++){
                    for(int j = 0; j<board_width; j++ ){
                        mate = board_val[pl][i][j];
                        for(int k=1; k<win_requires; k++){
                            mate = mate && board_val[pl][i+k][j];
                        }
                        if(mate){
                            Toast.makeText(Main2Activity.this, "Player "+pl+" wins", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    if(mate){
                        break;
                    }
                }
            }
            if(!mate){
                for (int i = 0; i<=board_height-win_requires; i++){
                    for(int j = 0; j<=board_width-win_requires; j++ ){
                        mate = board_val[pl][i][j];
                        for(int k=1; k<win_requires; k++){
                            mate = mate && board_val[pl][i+k][j+k];
                        }
                        if(mate){
                            Toast.makeText(Main2Activity.this, "Player "+pl+" wins", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    if(mate){
                        break;
                    }
                }
            }
            if(!mate){
                for (int i = 0; i<=board_height-win_requires; i++){
                    for(int j = win_requires-1; j<board_width; j++ ){
                        mate = board_val[pl][i][j];
                        for(int k=1; k<win_requires; k++){
                            mate = mate && board_val[pl][i+k][j-k];
                        }
                        if(mate){
                            Toast.makeText(Main2Activity.this, "Player "+pl+" wins", Toast.LENGTH_LONG).show();
                            break;
                        }
                    }
                    if(mate){
                        break;
                    }
                }
            }
            if(mate){
                done = true;
                if(pc==pl && dif!=0){
                    //save to sqllite
                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.dialoguser);
                    dialog.setTitle("INPUT NAME");
                    TextView tv = dialog.findViewById(R.id.scored);
                    final int score = (Global.board_height*Global.board_width)-turn;
                    tv.setText("Score: "+score);
                    final EditText name = dialog.findViewById(R.id.userd);
                    Button b = dialog.findViewById(R.id.submitd);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AsyncTask<Void, Void, Long>(){
                                @Override
                                protected Long doInBackground(Void... voids) {
                                    Long status = db.DAOrecord().insertRecord(new Record(name.getText().toString(), score, dif));
                                    return status;
                                }
                            }.execute();
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }
        }
    }
}

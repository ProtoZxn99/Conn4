/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stts.conn4;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.Collections;
/**
 *
 * @author ASUS ROG
 */
public class MiniMax {
    public int ai, ply, hv, vv, dv1, dv2;
    public boolean winscore = false, losescore = false;

    public MiniMax(int ai, int ply) {
        this.ai = ai;
        this.ply = ply;
    }
    
    public int findmove(Board b){
        Board best = AI(b);
        best.printboard();
        return best.move;
    }
    
    public Board AI(Board N){
        if (N.depth>=ply||isTerminal(N)||isFull(N)) {
            N.value = evaluate(N);
            return N;
        }
        else{
            ArrayList<Board> b = Expand(N);
            
            for(int i=0; i<b.size(); i++){
                b.set(i, AI(b.get(i)));
            }
            
            Board res;
            
            if (N.depth % 2 == 0) {
                res = getMaxValue(b);
                return res;
            }
            else{
                res = getMinValue(b);
                return res;
            }
        }
    }
    
    public Board getMaxValue (ArrayList<Board> b){
        Board wanted;
        wanted = b.get(0);
        for (Board board : b) {
            if(board.value>wanted.value){
                wanted = board;
            }
        }
        return wanted;
    }
    
    public Board getMinValue (ArrayList<Board> b){
        Board wanted;
        wanted = b.get(0);
        for (Board board : b) {
            if(board.value<wanted.value){
                wanted = board;
            }
        }
        return wanted;
    }
    
    public Board getAvgValue (ArrayList<Board> b){
        Board wanted;
        wanted = b.get(0);
        int value = 0;
        for (Board board : b) {
            value+=board.value;
        }
        wanted.value = value/b.size();
        return wanted;
    }
    
    public ArrayList<Board> Expand(Board N){
        int i = (int) Math.floor((Global.board_width-1)/2);
        int ctr = 0;
        ArrayList<Board> t = new ArrayList<Board>();
        while (i <= Global.board_width-1 && i>=0) {
            int height = -1;
            while(height<Global.board_height-1){
                if(N.position[2][height+1][i]){
                    break;
                }
                height++;
            }
            if(height>=0){
                Board temp =  new Board(N.position, (N.owner+1)%2, N.depth+1, N.move);
                temp.position[(N.owner+1)%2][height][i] = true;
                temp.position[2][height][i] = true;
                if(temp.depth==1){
                    temp.move = i;
                }
                t.add(temp);
            }
            ctr++;
            if (ctr % 2 == 0) {
                i -= ctr;
            }
            else{
                i += ctr;
            }
        }
        return t;
    }
    
    public boolean isFull(Board b){
        for (int i = 0; i < Global.board_height; i++) {
            for (int j = 0; j < Global.board_width; j++) {
                if (!b.position[2][i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isTerminal(Board b){
        boolean mate = false;
        
        for (int i = 0; i<Global.board_height; i++){
            for(int j = 0; j<=Global.board_width-Global.win_requires; j++ ){
                mate = b.position[b.owner][i][j];
                for(int k=1; k<Global.win_requires; k++){
                    mate = mate && b.position[b.owner][i][j+k];
                }
                if(mate){
                    return true;
                }
            }
        }
        for (int i = 0; i<=Global.board_height-Global.win_requires; i++){
            for(int j = 0; j<Global.board_width; j++ ){
                mate = b.position[b.owner][i][j];
                for(int k=1; k<Global.win_requires; k++){
                    mate = mate && b.position[b.owner][i+k][j];
                }
                if(mate){
                    return true;
                }
            }
        }
        for (int i = 0; i<=Global.board_height-Global.win_requires; i++){
            for(int j = 0; j<=Global.board_width-Global.win_requires; j++ ){
                mate = b.position[b.owner][i][j];
                for(int k=1; k<Global.win_requires; k++){
                    mate = mate && b.position[b.owner][i+k][j+k];
                }
                if(mate){
                    return true;
                }
            }
        }
        for (int i = 0; i<=Global.board_height-Global.win_requires; i++){
            for(int j = Global.win_requires-1; j<Global.board_width; j++ ){
                mate = b.position[b.owner][i][j];
                for(int k=1; k<Global.win_requires; k++){
                    mate = mate && b.position[b.owner][i+k][j-k];
                }
                if(mate){
                    return true;
                }
            }
        }
        return false;
    }
    
    public int evaluate(Board b){
        HV horv = new HV(b);
        horv.start();
        VV verv = new VV(b);
        verv.start();
        DV1 dia1 = new DV1(b);
        dia1.start();
        DV2 dia2 = new DV2(b);
        dia2.start();
        
        try {
            horv.join();
            verv.join();
            dia1.join();
            dia2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(MiniMax.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(losescore){
            losescore = false;
            return Global.lose_ratio;
        }
        else if(winscore){
            winscore = false;
            return Global.win_ratio;
        }
        else{
            return hv+vv+dv1+dv2;
        }
    }

    
    
    class HV extends Thread{
        Board b;
        public HV(Board b){
            this.b=b;
        }
        
        @Override
        public void run(){
            int res = HorizontalValue(b, b.owner);
            int res2 = HorizontalValue(b, (b.owner+1)%2);
            hv = res - res2;
        }
    }
    
    class VV extends Thread{
        Board b;
        public VV(Board b){
            this.b=b;
        }
        
        @Override
        public void run(){
            int res = VerticalValue(b, b.owner);
            int res2 = VerticalValue(b, (b.owner+1)%2);
            vv = res - res2;
        }
    }
    
    class DV1 extends Thread{
        Board b;
        public DV1(Board b){
            this.b=b;
        }
        
        @Override
        public void run(){
            int res = Diagonal1Value(b, b.owner);
            int res2 = Diagonal1Value(b, (b.owner+1)%2);
            dv1 = res - res2;
        }
    }
    
    class DV2 extends Thread{
        Board b;
        public DV2(Board b){
            this.b=b;
        }
        
        @Override
        public void run(){
            int res = Diagonal2Value(b, b.owner);
            int res2 = Diagonal2Value(b, (b.owner+1)%2);
            dv2 = res - res2;
        }
    }
    
    public int HorizontalValue(Board b, int target){
        int value = 0;
        for(int i=0; i<Global.board_height; i++){
            for(int j=0; j<Global.board_width-Global.win_requires+1; j++){
                int length = 0;
                boolean calc = true;
                for(int k=0; k<Global.win_requires; k++){
                    if(b.position[target][i][j+k]){
                        length++;
                    }
                    else if(b.position[2][i][j+k]){
                        calc = false;
                        break;
                    }
                }
                if(calc){
                   if(length>=Global.win_requires){
                       if(b.owner!=ai){
                           losescore = true;
                           return 0;
                       }
                       winscore = true;
                       return 0;
                   }
                   else{
                       value += Math.pow(length,2);
                   }
                }
            }
        }
        return value;
    }

public int VerticalValue(Board b, int target){
    int value = 0;
        for(int i=0; i<Global.board_height-Global.win_requires+1; i++){
            for(int j=0; j<Global.board_width; j++){
                int length = 0;
                boolean calc = true;
                for(int k=0; k<Global.win_requires; k++){
                    if(b.position[target][i+k][j]){
                        length++;
                    }
                    else if(b.position[2][i+k][j]){
                        calc = false;
                        break;
                    }
                }
                if(calc){
                   if(length>=Global.win_requires){
                       if(b.owner!=ai){
                           losescore = true;
                           return 0;
                       }
                       winscore = true;
                       return 0;
                   }
                   else{
                       value += Math.pow(length,2);
                   }
                }
            }
        }
        return value;
}

public int Diagonal1Value(Board b, int target){
    int value = 0;
        for(int i=0; i<Global.board_height-Global.win_requires+1; i++){
            for(int j=0; j<Global.board_width-Global.win_requires+1; j++){
                int length = 0;
                boolean calc = true;
                for(int k=0; k<Global.win_requires; k++){
                    if(b.position[target][i+k][j+k]){
                        length++;
                    }
                    else if(b.position[2][i+k][j+k]){
                        calc = false;
                        break;
                    }
                }
                if(calc){
                   if(length>=Global.win_requires){
                       if(b.owner!=ai){
                           losescore = true;
                           return 0;
                       }
                       winscore = true;
                       return 0;
                   }
                   else{
                       value += Math.pow(length,2);
                   }
                }
            }
        }
        return value;
}

public int Diagonal2Value(Board b, int target){
    int value = 0;
        for(int i=0; i<Global.board_height-Global.win_requires+1; i++){
            for(int j=Global.board_width-1; j>Global.win_requires-2; j--){
                int length = 0;
                boolean calc = true;
                for(int k=0; k<Global.win_requires; k++){
                    if(b.position[target][i+k][j-k]){
                        length++;
                    }
                    else if(b.position[2][i+k][j-k]){
                        calc = false;
                        break;
                    }
                }
                if(calc){
                   if(length>=Global.win_requires){
                       if(b.owner!=ai){
                           losescore = true;
                           return 0;
                       }
                       winscore = true;
                       return 0;
                   }
                   else{
                       value += Math.pow(length,2);
                   }
                }
            }
        }
        return value;
    }
}

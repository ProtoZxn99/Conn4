/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stts.conn4;

/**
 *
 * @author ASUS ROG
 */
public class Board implements Comparable{
    public int owner = 0;
    public int value = 0;
    public int depth = 0;
    public int move = -1;
    public boolean [][][] position = new boolean [3][board_height][board_width];
    static int board_height = Global.board_height;
    static int board_width = Global.board_width;
    
    public Board(boolean [][][] position, int owner, int depth, int move){
        this.owner = owner;
        this.depth = depth;
        this.move = move;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < board_height; j++) {
                for (int k = 0; k < board_width; k++) {
                    this.position[i][j][k] = (boolean)position[i][j][k];
                }
            }
        }
    }
    
    
    public void printboard(){
        for (int i = 0; i < board_height; i++) {
            for (int j = 0; j < board_width; j++) {
                if(position[2][i][j]){
                    if(position[0][i][j]){
                        System.out.print("O");
                    }
                    else{
                        System.out.print("X");
                    }
                }
                else{
                    System.out.print("-");
                }
            }
            System.out.println("|");
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        Board c = (Board) o;
        return this.getValue()-c.getValue();
    }
    
    class checkValue extends Thread{
        @Override
        public void run(){
            value = HorizontalValue(owner);
            value += VerticalValue(owner);
            value += Diagonal1Value(owner);
            value += Diagonal2Value(owner);
            int enemy = (owner+1)%2;
            value -= HorizontalValue(enemy);
            value -= VerticalValue(enemy);
            value -= Diagonal1Value(enemy);
            value -= Diagonal2Value(enemy);
        }
        
        public int HorizontalValue(int target){
                int total = 0;
                int x =0, y=0;
                while(y<board_height){
                    if(position[target][y][x]){
                        int length = 0;
                        boolean start_block = true;
                        boolean end_block = true;
                        if(x>0){
                            if(!position[2][y][x-1]){
                                start_block = false;
                            }
                        }

                        while(true){
                            boolean owned = position[target][y][x];
                            if(owned){
                                length++;
                            }
                            else if(!position[2][y][x]){
                                end_block = false;
                                break;
                            }
                            else{
                                break;
                            }
                            if(x<board_width-1){
                                x++;
                            }
                            else{
                                break;
                            }
                        }
                        if(length>=Global.win_requires){
                                if(target!=owner){
                                    total+=500000;
                                }
                                total += 1000000;
                        }
                        else if(!start_block||!end_block){
                            total += Math.pow(length, 2);
                        }
                    }
                    x++;
                    if(x>=board_width){
                        x=0;
                        y++;
                    }
                }
                return total;
            }

        public int VerticalValue(int target){
            int total = 0;
            int x =0, y=0;
            while(x<board_width){
                if(position[target][y][x]){
                    int length = 0;
                    boolean start_block = true;
                    boolean end_block = true;
                    if(y>0){
                        if(!position[2][y-1][x]){
                            start_block = false;
                        }
                    }

                    while(true){
                        boolean owned = position[target][y][x];
                        if(owned){
                            length++;
                        }
                        else if(!position[2][y][x]){
                            end_block = false;
                            break;
                        }
                        else{
                            break;
                        }
                        if(y<board_height-1){
                            y++;
                        }
                        else{
                            break;
                        }
                    }
                    if(length>=Global.win_requires){
                            if(target!=owner){
                                total+=500000;
                            }
                            total += 1000000;
                    }
                    else if(!start_block||!end_block){
                        total += Math.pow(length, 2);
                    }
                }
                y++;
                if(y>=board_height){
                    x++;
                    y=0;
                }
            }
            return total;
        }

        public int Diagonal1Value(int target){
            int total = 0;
            int main_y = board_height-Global.win_requires;
            int main_x = 0;
            int y = main_y;
            int x = main_x;
            while(x<board_width){
                if(position[target][y][x]){
                    int length = 0;
                    boolean start_block = true;
                    boolean end_block = true;
                    if(y>0 && x>0){
                        if(!position[2][y-1][x-1]){
                            start_block = false;
                        }
                    }

                    while(true){
                        boolean owned = position[target][y][x];
                        if(owned){
                            length++;
                        }
                        else if(!position[2][y][x]){
                            end_block = false;
                            break;
                        }
                        else{
                            break;
                        }
                        if(y<board_height-1 && x<board_width-1){
                            x++;
                            y++;
                        }
                        else{
                            break;
                        }
                    }
                    if(length>=Global.win_requires){
                            if(target!=owner){
                                total+=500000;
                            }
                            total += 1000000;
                    }
                    else if(!start_block||!end_block){
                        total += Math.pow(length, 2);
                    }
                }
                y++;
                x++;
                if(y>=board_height || x>=board_height){
                    main_y--;
                    if(main_y<0){
                        main_y = 0;
                        main_x++;
                    }
                    y = main_y;
                    x = main_x;
                }
            }
            return total;
        }

        public int Diagonal2Value(int target){
            int total = 0;
            int main_y = 0;
            int main_x = board_height-Global.win_requires;
            int y = main_y;
            int x = main_x;
            while(y<board_height){
                if(position[target][y][x]){
                    int length = 0;
                    boolean start_block = true;
                    boolean end_block = true;
                    if(y>0 && x<board_width-1){
                        if(!position[2][y-1][x+1]){
                            start_block = false;
                        }
                    }

                    while(true){
                        boolean owned = position[target][y][x];
                        if(owned){
                            length++;
                        }
                        else if(!position[2][y][x]){
                            end_block = false;
                            break;
                        }
                        else{
                            break;
                        }
                        if(y<board_height-1 && x>0){
                            x--;
                            y++;
                        }
                        else{
                            break;
                        }
                    }
                    if(length>=Global.win_requires){
                            if(target!=owner){
                                total+=500000;
                            }
                            total += 1000000;
                    }
                    else if(!start_block||!end_block){
                        total += Math.pow(length, 2);
                    }
                }
                y++;
                x--;
                if(y>=board_height || x<0){
                    main_x++;
                    if(main_x>=board_width){
                        main_x = board_width-1;
                        main_y++;
                    }
                    y = main_y;
                    x = main_x;
                }
            }
            return total;
        }
    }
}
    
//    int valueChainHorizontal(){
//        int length = 0;
//        boolean start_block = true;
//        boolean end_block = true;
//        if(x>0){
//            if(!board[(owner+1)%2][y][x-1]){
//                start_block = false;
//            }
//        }
//        
//        while(true){
//            boolean owned = board[owner][y][x];
//            if(owned){
//                length++;
//            }
//            else if(!board[(owner+1)%2][y][x]){
//                end_block = false;
//                break;
//            }
//            else{
//                break;
//            }
//            if(x!=board_width){
//                x++;
//            }
//        }
//        if(start_block&&end_block){
//            return length;
//        }
//        else{
//            if(length==4){
//                return 1000000;
//            }
//            return (int) Math.pow(length, 2);
//        }
//    }
//    
//    int valueChainVertical(){
//        int length = 0;
//        boolean start_block = true;
//        boolean end_block = true;
//        if(y>0){
//            if(!board[(owner+1)%2][y-1][x]){
//                start_block = false;
//            }
//        }
//        
//        while(true){
//            boolean owned = board[owner][y][x];
//            if(owned){
//                length++;
//            }
//            else if(!board[(owner+1)%2][y][x]){
//                end_block = false;
//                break;
//            }
//            else{
//                break;
//            }
//            if(y!=board_width){
//                y++;
//            }
//        }
//        if(start_block&&end_block){
//            return length;
//        }
//        else{
//            return (int) Math.pow(length, 2);
//        }
//    }
//    
//    int valueChainDiagonal1(){
//        int length = 0;
//        boolean start_block = true;
//        boolean end_block = true;
//        if(y>0&&x>0){
//            if(!board[(owner+1)%2][y-1][x]){
//                start_block = false;
//            }
//        }
//        
//        while(true){
//            boolean owned = board[owner][y][x];
//            if(owned){
//                length++;
//            }
//            else if(!board[(owner+1)%2][y][x]){
//                end_block = false;
//                break;
//            }
//            else{
//                break;
//            }
//            if(y!=board_width&&x!=board_width){
//                y++;
//                x++;
//            }
//        }
//        if(start_block&&end_block){
//            return length;
//        }
//        else{
//            return (int) Math.pow(length, 2);
//        }
//    }
//    
//    int valueChainDiagonal2(){
//        int length = 0;
//        boolean start_block = true;
//        boolean end_block = true;
//        if(y>0&&x<board_width){
//            if(!board[(owner+1)%2][y-1][x+1]){
//                start_block = false;
//            }
//        }
//        
//        while(true){
//            boolean owned = board[owner][y][x];
//            if(owned){
//                length++;
//            }
//            else if(!board[(owner+1)%2][y][x]){
//                end_block = false;
//                break;
//            }
//            else{
//                break;
//            }
//            if(y!=board_width&&x!=0){
//                y++;
//                x--;
//            }
//        }
//        if(start_block&&end_block){
//            return length;
//        }
//        else{
//            return (int) Math.pow(length, 2);
//        }
//    }


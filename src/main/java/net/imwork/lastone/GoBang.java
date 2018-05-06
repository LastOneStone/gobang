package net.imwork.lastone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoBang {

    private static final int BOARD_SIZE = 15;
    private static boolean isFirstPlayer = true;
    private static String [][] stones;


    public void initBoard(){
        //init the board
        stones = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < stones.length; i++){
            for (int j = 0; j < stones.length; j++){
                stones[i][j]="+ ";

            }
        }
    }


    public void playGame() throws IOException{
        //Show the board firstly
        updateBoard();
        System.out.println("Please place the stone like 3,4:");
        //Wait for input point
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String point = null;
        while ((point = br.readLine()) != null){
            String [] position = point.split(",");
            int xPos = validateNum(position[0]);
            int yPos = validateNum(position[1]);

            if ( xPos == -1 || yPos == -1 ){
                System.out.println("Wrong position, please place the stone again:");
                continue;
            }

            placeStone(xPos,yPos);
            //Update board after placing a stone
            updateBoard();
            System.out.println("Please place the stone like 3,4: ");
        }
    }

    public int validateNum(String pos) throws IOException{
        int result;
        //“-?[0-9]+.?[0-9]+”即可匹配所有数字
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(pos);
        if (isNum.matches()){
            result = Integer.parseInt(pos);
            if (result > 0 || result < BOARD_SIZE + 1 ){
                return result;
            }else {
                return -1;
            }
        }else {
            return -1;
        }
    }

    public void placeStone(int x, int y){
        if (stones[x-1][y-1].equals("+ ")){
            if (isFirstPlayer){
                stones[x-1][y-1] = "@ ";
                isFirstPlayer = false;
            }else {
                stones[x-1][y-1] = "O ";
                isFirstPlayer =true;
            }
        }else {
            System.out.println("The position is occupied, please select again!");
        }
    }

    public void updateBoard(){
        //show the board
//        System.out.println("The GoBang board is updated as below:");
        for (int i = 0; i < stones.length; i++){
            for (int j = 0; j < stones.length; j++){
                System.out.print(stones[i][j]);

            }
            //Return for each line
            System.out.println();
        }
    }
    public static void main(String [] args) throws IOException{
        GoBang goBang = new GoBang();
        goBang.initBoard();
        goBang.playGame();
    }
}

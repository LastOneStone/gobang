package net.imwork.lastone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoBang {

    private static final int BOARD_SIZE = 15;
    private static final String BLANK = "+ ";
    private static final String stoneOfFirstPlayer = "@ ";
    private static final String stoneOfSecondPlayer = "O ";
    private static boolean isFirstPlayer = true;
    private static String [][] stones;


    public void initBoard(){
        //init the board
        stones = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < stones.length; i++){
            for (int j = 0; j < stones.length; j++){
                stones[i][j]=BLANK;

            }
        }
    }


    public void playGame() throws IOException{
        //Show the board firstly
        updateBoard();
        System.out.println("First player, please place the stone like 3,4 type quit to exit:");
        //Wait for input point
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String point = null;
        while ((point = br.readLine()) != null){
            if (point.equalsIgnoreCase("quit")){
                break;
            }else {

                String[] position = point.split(",");
                //Only tow int form a point
                if (position.length >= 2) {
                    int xPos = validateNum(position[0]);
                    int yPos = validateNum(position[1]);

                    if (xPos == -1 || yPos == -1) {
                        System.out.println("Wrong position, please place the stone again,type quit to exit:");
                        continue;
                    }

                    placeStone(xPos, yPos);
                    //Update board after placing a stone
                    updateBoard();
                    if (isWin(xPos - 1, yPos - 1, stones[xPos - 1][yPos - 1])) {
                        if (stones[xPos - 1][yPos - 1].equals(stoneOfFirstPlayer)) {
                            System.out.println("First player is WIN! Game Over!");
                        } else {
                            System.out.println("Second player is WIN! Game Over!");
                        }
                    } else {
                        if (stones[xPos - 1][yPos - 1].equals(stoneOfFirstPlayer)) {
                            System.out.println("Second player, please place the stone like 3,4,type quit to exit: ");
                        } else {
                            System.out.println("First player, please place the stone like 3,4,type quit to exit: ");
                        }
                    }
                }else {
                    System.out.println("Wrong position, please place the stone again, type quit to exit:");
                    continue;
                }
            }
        }
    }

    public int validateNum(String pos) throws IOException{
        int result;
        //“-?[0-9]+.?[0-9]+”即可匹配所有数字
        Pattern pattern = Pattern.compile("^[0-9]*");
        Matcher isNum = pattern.matcher(pos);
        if (isNum.matches()){
            if (!pos.equals("")){
                result = Integer.parseInt(pos);
            }
            else {
                return -1;
            }
            if (result > 0 && result < BOARD_SIZE + 1 ){
                return result;
            }else {
                return -1;
            }
        }else {
            return -1;
        }
    }

    public boolean isWin(int x, int y, String stone){

        // horizontal
        int count = 1;
        for (int i = 1; true ; i++){
            if( x - i < 0){
                break;
            }else if (!stone.equals(stones[x-i][y])){
                break;
            }else {
                count++;
            }
        }
        for (int i = 1; true ; i++){
            if( x + i > BOARD_SIZE-1){
                break;
            }else if (!stone.equals(stones[x+i][y])){
                break;
            }else {
                count++;
            }
        }
        if (count >= 5){
            return true;
        }
        // vertical
        count = 1;
        for (int i = 1; true ; i++){
            if( y - i < 0){
                break;
            }else if (!stone.equals(stones[x][y-i])){
                break;
            }else {
                count++;
            }
        }
        for (int i = 1; true ; i++){
            if( y + i > BOARD_SIZE-1){
                break;
            }else if (!stone.equals(stones[x][y+i])){
                break;
            }else {
                count++;
            }
        }

        if (count >= 5){
            return true;
        }
        // left
        count = 1;
        for (int i = 1; true ; i++){
            if( (y - i >= 0) && (x + i < BOARD_SIZE ) && (stone.equals(stones[x+i][y-i]))){
                count++;
            }else {
                break;
            }
        }

        for (int i = 1; true ; i++){
            if( (y + i < BOARD_SIZE) && (x-i >= 0) && (stone.equals(stones[x-i][y+i])) ){
                count++;
            }else {
                break;
            }
        }

        if (count >= 5){
            return true;
        }

        //right
        count = 1;
        for (int i = 1; true ; i++){
            if( (y - i >= 0) && (x - i >= 0 ) && (stone.equals(stones[x-i][y-i]))){
                count++;
            }else {
                break;
            }
        }

        for (int i = 1; true ; i++){
            if( (y + i < BOARD_SIZE) && (x+i < BOARD_SIZE) && (stone.equals(stones[x+i][y+i])) ){
                count++;
            }else {
                break;
            }
        }
        if (count >= 5){
            return true;
        }

        return false;

    }
    public void placeStone(int x, int y){
        if (stones[x-1][y-1].equals(BLANK)){
            if (isFirstPlayer){
                stones[x-1][y-1] = stoneOfFirstPlayer;
                isFirstPlayer = false;
            }else {
                stones[x-1][y-1] = stoneOfSecondPlayer;
                isFirstPlayer =true;
            }
        }else {
            System.out.println("The position is occupied, please select again!");
        }
    }

    public void updateBoard(){
        //show the board
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

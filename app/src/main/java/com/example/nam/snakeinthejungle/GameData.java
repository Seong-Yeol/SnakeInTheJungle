package com.example.nam.snakeinthejungle;

/**
 * Created by Nam on 2016-04-27.
 */
public class GameData {
    int [][]mFieldMat = new int[20][20];

    public static final int EMPTY = 0;
    public static final int HEAD = 1;

    public static final int NORTH = 2;
    public static final int NEAST = 3;
    public static final int EAST = 4;
    public static final int SEAST = 5;
    public static final int SOUTH = 6;
    public static final int SWEST = 7;
    public static final int WEST = 8;
    public static final int NWEST = 9;

    public static final int NTAIL = 10;
    public static final int STAIL = 11;
    public static final int ETAIL = 12;
    public static final int WTAIL = 13;

    int mHeadX;
    int mHeadY;


    public GameData(){

    }

    public boolean init() {
        int i,j;

        for(i=0; i < 20; i++){
            for(j=0; j < 20; j++){
                mFieldMat[i][j] = EMPTY;
            }
        }

        mFieldMat[1][1] = ETAIL;
        mFieldMat[2][1] = EAST;
        mFieldMat[3][1] = EAST;
        mFieldMat[4][1] = HEAD;

        mHeadX = 4;
        mHeadX = 1;

        return true;
    }

    public boolean moveSnake(int Direction){
        int i,j;
        switch (Direction){

            case NORTH:
                if( mHeadX > 0 ) {
//                    i = mHeadX;
//                    j = mHeadY+1;
                    mFieldMat[mHeadX][mHeadY--] = EMPTY;
                    mFieldMat[mHeadX][mHeadY] = HEAD;
                }
//                    heady--;
                break;
            case SOUTH:
                if( heady < 19)
//                    heady++;
                break;
            case WEST:
                if( headx > 0 )
//                    headx--;
                break;
            case EAST:
                if( headx < 19 )
//                    headx++;
                break;
        }
    }

    private void moveBody(){
        int i,j;

        for(i=0;i< 20 ;i++) {
            for (j = 0; j < 20; j++) {
                switch (mFieldMat[i][j]){
                    case NORTH:
                        mFieldMat[i][j-1] = NORTH;

                    case NEAST:
                    case EAST:
                    case SEAST:
                    case SOUTH:
                    case SWEST:
                    case WEST:
                    case NWEST:
                }
            }
        }
    }

    final int [][] getFieldMat() {
        return mFieldMat;
    }

    public int getFieldData(int x, int y){
        return mFieldMat[x][y];
    }


}

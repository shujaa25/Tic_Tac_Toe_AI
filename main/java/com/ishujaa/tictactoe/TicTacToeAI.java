package com.ishujaa.tictactoe;
//TicTacToe with MiniMax Algorithm
public class TicTacToeAI {
    final char maximizer = 'x';// human
    final char minimizer = 'o';// computer
    final char blank = 'B';// computer

    static class Move{
        int row, col;
        void setMove(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

    public boolean moreMovesPossible(char[][] board){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == blank) return true;
            }
        }
        return false;
    }

    //checks if someone has won
    int evaluateBoard(char[][] board){
        //check the rows
        for(int i=0; i<3; i++){
            if(board[i][0] == board[i][1] && board[i][1] == board[i][2]){
                if(board[i][0] == minimizer) return 1;// maximizer has won
                if(board[i][0] == maximizer) return -1;
            }
        }

        //check the columns
        for(int i=0; i<3; i++){
            if(board[0][i] == board[1][i] && board[1][i] == board[2][i]){
                if(board[0][i] == minimizer) return 1;// maximizer has won
                if(board[0][i] == maximizer) return -1;
            }
        }

        //check the first diagonal
        if(board[0][0] == board[1][1] && board[1][1] == board[2][2]){
            if(board[0][0] == minimizer) return 1;// maximizer has won
            if(board[0][0] == maximizer) return -1;
        }

        //check the second diagonal
        if(board[0][2] == board[1][1] && board[1][1] == board[2][0]){
            if(board[0][2] == minimizer) return 1;// maximizer has won
            if(board[0][2] == maximizer) return -1;
        }

        return 0;//none has won
    }

    private int minimax(char[][] board, int depth, boolean isMaximizer){
        int currentScore = evaluateBoard(board);//check if someone has already won
        if(currentScore == 1 || currentScore == -1) return currentScore; //return if someone has won
        if(!moreMovesPossible(board)) return 0;

        if(isMaximizer){
            int currentBestMove = Integer.MIN_VALUE;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j] == blank){
                        //take this move and check for the best
                        board[i][j] = minimizer;

                        //check if it results in best move
                        currentBestMove = Math.max(currentBestMove, minimax(board,depth+1, false));

                        //erase the taken move
                        board[i][j] = blank;
                    }
                }
            }
            return currentBestMove;
        }else{
            int currentBestMove = Integer.MAX_VALUE;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(board[i][j] == blank){
                        //take this move and check for the best
                        board[i][j] = maximizer;

                        //check if it results in best move
                        currentBestMove = Math.min(currentBestMove, minimax(board,depth+1, true));

                        //erase the taken move
                        board[i][j] = blank;
                    }
                }
            }
            return currentBestMove;
        }
    }

    public Move nextBestMove(char[][] board){
        int bestMoveValue = Integer.MIN_VALUE;
        Move nextMove = new Move();
        nextMove.setMove(-1,-1);

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j] == blank){
                    //take this move and check for the best
                    board[i][j] = minimizer;

                    //check if it results in best move
                    int currentMoveValue = minimax(board,0, false);

                    //erase the taken move
                    board[i][j] = blank;

                    //if the new move value is higher than the best then take that
                    if(currentMoveValue > bestMoveValue){
                        bestMoveValue = currentMoveValue;
                        nextMove.setMove(i, j);
                    }
                }
            }
        }

        return nextMove;
    }
}

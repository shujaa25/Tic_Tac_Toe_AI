package com.ishujaa.tictactoe;

import static android.view.ViewGroup.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private char[][] board;
    private Button[][] buttons;
    private Button replayBtn;
    private TextView textViewDisplay;


    private void initialize(){
        textViewDisplay.setText(R.string.new_game);
        board = new char[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j] = 'B';
            }
        }
        replayBtn.setEnabled(false);
    }

    public void replay(View view){
        initialize();
        toggleButtons(true, R.string.default_btn_text, true);
    }

    private void toggleButtons(boolean setText,int text, boolean val){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setEnabled(val);
                if(setText) buttons[i][j].setText(text);
            }
        }
    }

    private void endGame(String message, View view){
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
        replayBtn.setEnabled(true);
        toggleButtons(false, 0, false);
        textViewDisplay.setText(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.main_layout);
        replayBtn = findViewById(R.id.repl_btn);


        TicTacToeAI ticTacToeAI = new TicTacToeAI();
        final int[] loseCount = {0};

        TextView textViewCount = findViewById(R.id.count);
        buttons = new Button[3][3];

        textViewDisplay = findViewById(R.id.disp_msg);

        initialize();
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j] = new Button(this);
                GridLayoutManager.LayoutParams params = new GridLayoutManager.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                params.setMargins(2,2,2,2);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setTextSize(50);
                buttons[i][j].setId(i*10+j);
                buttons[i][j].setBackgroundColor(Color.BLACK);
                buttons[i][j].setMinWidth(300); buttons[i][j].setMinHeight(300);
                buttons[i][j].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button button = findViewById(view.getId());
                        button.setText(R.string.x_btn_text);
                        board[(int)view.getId()/10][(int)view.getId()%10] = 'x';
                        button.setEnabled(false);
                        button.setTextColor(Color.RED);
                        textViewDisplay.setText("YOUR TURN");
                        if(ticTacToeAI.moreMovesPossible(board)){
                            TicTacToeAI.Move move = ticTacToeAI.nextBestMove(board);
                            board[move.row][move.col] = 'o';
                            button = findViewById(move.row*10+move.col);
                            button.setText(R.string.o_btn_text);
                            button.setEnabled(false);
                            button.setTextColor(Color.GREEN);
                        }else if(ticTacToeAI.evaluateBoard(board) == 0){
                            endGame("MATCH TIE!", view);

                        }if(ticTacToeAI.evaluateBoard(board) > 0){
                            textViewCount.setText("Lose count: "+(++loseCount[0]));
                            endGame("YOU LOSE!", view);
                        }
                    }
                });
                if(gridLayout != null) gridLayout.addView(buttons[i][j]);
            }
        }

    }
}
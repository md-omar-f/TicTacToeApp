package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView playerOneScore, playerTwoScore;
    private TextView playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;
    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    private boolean activePlayer;

    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    private int[][] winningPositions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = findViewById(R.id.playerOneScore);
        playerTwoScore = findViewById(R.id.playerTwoScore);
        playerStatus = findViewById(R.id.playerStatus);
        resetGame = findViewById(R.id.resetGame);

        for(int i = 0; i < buttons.length; ++i) {
            String btnId = "btn_" + i;
            int resourceId = getResources().getIdentifier(btnId, "id", getPackageName());
            buttons[i] = findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }

    @Override
    public void onClick(View view) {
        //Log.i("test", "button is clicked");
        if(!((Button) view).getText().toString().equals("")) {
            return;
        }
        String buttonId = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length() - 1, buttonId.length()));
        if(activePlayer) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if(checkWinner()) {
            if(activePlayer) {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if (roundCount == 9) {
            Toast.makeText(this, "Match drawn :)", Toast.LENGTH_SHORT).show();
            playAgain();
        } else {
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount > playerTwoScoreCount) {
            playerStatus.setText("Player One is Winning!");
        } else if(playerTwoScoreCount > playerOneScoreCount) {
            playerStatus.setText("Player Two is winning!");
        } else {
            playerStatus.setText("So far, series Drawn :)");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }
    public boolean checkWinner() {
        boolean winnerResult = false;
        for(int[] winningPosition : winningPositions) {
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }
    public  void updatePlayerScore () {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }
    public void playAgain() {
        activePlayer = true;
        roundCount = 0;
        for(int i = 0; i < buttons.length; ++i) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}
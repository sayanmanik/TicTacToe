package com.example.sayan.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sayan.tictactoe.R;


public class Hard extends AppCompatActivity {

    int ActivePlayer,winner;
    Button board[][]=new Button[3][3];
    String player,opponent;
    TextView txt1,txt2,xWinner,oWinner,result;
    int o,x;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);

        txt1=findViewById(R.id.x_sign);
        txt2=findViewById(R.id.o_sign);

        xWinner=findViewById(R.id.x_result);
        oWinner=findViewById(R.id.o_result);
        result=findViewById(R.id.result);
           x = 0;
           o = 0;

           txt1.setTextColor(Color.parseColor("#efd48a"));
           txt2.setTextColor(Color.parseColor("#f9b896"));

           board[0][0]=findViewById(R.id.bu1);
           board[0][1]=findViewById(R.id.bu2);
           board[0][2]=findViewById(R.id.bu3);
           board[1][0]=findViewById(R.id.bu4);
           board[1][1]=findViewById(R.id.bu5);
           board[1][2]=findViewById(R.id.bu6);
           board[2][0]=findViewById(R.id.bu7);
           board[2][1]=findViewById(R.id.bu8);
           board[2][2]=findViewById(R.id.bu9);

        player="X";
        opponent="O";
        winner=-1;
        ActivePlayer=1;

        if(savedInstanceState!=null)
           {
               x=savedInstanceState.getInt("x val");
               o=savedInstanceState.getInt("o val");
           }

           xWinner.setText(x+"");
           oWinner.setText(o+"");

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("x val",x);
        savedInstanceState.putInt("o val",o);
    }

 public void buClick(View view)
    {

        Button buttonSelected=(Button)view;

        playGame(buttonSelected);
    }

    public void playGame(Button button) {

           selectWinner();
        if (ActivePlayer == 1) {
            button.setText(opponent);
            button.setTextColor(Color.parseColor("#FFFED86F"));
            ActivePlayer = 2;
            if(isLeft()==true&&winner==-1)
            {
          AutoPlay();
            }
            else
                Toast.makeText(this,"It is a draw!! Try again",Toast.LENGTH_LONG).show();

        } else {
            button.setText(player);
            button.setTextColor(Color.parseColor("#FFFEA16F"));
            ActivePlayer = 1;
        }
        button.setEnabled(false);
        if(winner==-1)
        selectWinner();
    }

    public void selectWinner()
    {
      int score=evaluate();

      if(score==10)
      {
          winner=2;

      }
      if(score==-10)
      {
          winner=1;

      }

      if(winner==2)
      {
          x++;
          xWinner.setText(x+"");
          txt1.setTextColor(Color.parseColor("#c0744b"));
          txt1.setShadowLayer(50.0F,0.0F,0.0F,Color.WHITE);
          result.setText("Phone Wins!!!");
      }

      if(winner==1)
      {
          o++;
          oWinner.setText(o+"");
          txt2.setTextColor(Color.parseColor("#d2ad45"));
          txt2.setShadowLayer(50.0F,0.0F,0.0F,Color.WHITE);
          result.setText("You Win!!!");
      }
        if(winner!=-1)
        {
            Toast.makeText(this,"Winner is :"+winner,Toast.LENGTH_LONG).show();
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(board[i][j].isEnabled())
                    {
                        board[i][j].setEnabled(false);
                    }
                }
            }
        }
    }

    public Move findBestMove()
    {
        int bestVal=-1000;
        int moveVal;
        Move move=new Move();
        move.row = -1;
        move.col = -1;

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j].getText()=="")
                {
                    board[i][j].setText(player);
                    moveVal=minmax(0,false);
                    board[i][j].setText("");

                    if(moveVal>bestVal)
                    {
                        bestVal=moveVal;
                        move.row=i;
                        move.col=j;
                    }
                }
            }
        }
        return move;
    }

    public boolean isLeft()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j].getText()=="")
                    return true;
            }
        }
        return false;
    }

    int min(int a,int b)
    {
        return a>b?b:a;
    }

    int max(int a,int b)
    {
        return a>b?a:b;
    }

    public int minmax(int height,boolean isMax)
    {
        int score=evaluate();

        if(score==-10)
            return score;

        if(score==10)
            return score;

        if(isLeft()==false)
            //Toast.makeText(this,"it is a draw.Try again!!!",Toast.LENGTH_LONG).show();
            return 0;

        if(isMax)
        {
            int bestVal=-100;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(board[i][j].getText()=="")
                    {
                        board[i][j].setText(player);
                        bestVal=max(bestVal,minmax(height+1,!isMax));
                        board[i][j].setText("");

                    }
                }
            }
            return bestVal;
        }
        else
        {
            int bestVal=100;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(board[i][j].getText()=="")
                    {
                        board[i][j].setText(opponent);
                        bestVal=min(bestVal,minmax(height+1,!isMax));
                        board[i][j].setText("");
                    }
                }
            }
            return bestVal;
        }
    }

    int evaluate()
    {
        //row check
        for(int i=0;i<3;i++)
        {
            if(board[i][0].getText()==board[i][1].getText()&&board[i][1].getText()==board[i][2].getText())
            {
                if(board[i][0].getText()==player)
                {
                    return 10;
                }
                if(board[i][0].getText()==opponent)
                {
                    return -10;
                }
            }
        }
        //column check
        for(int i=0;i<3;i++)
        {
            if(board[0][i].getText()==board[1][i].getText()&&board[1][i].getText()==board[2][i].getText())
            {
                if(board[0][i].getText()==player)
                {
                    return 10;
                }
                if(board[0][i].getText()==opponent)
                {
                    return -10;
                }
            }
        }
        //diagonal check
        if(board[0][0].getText()==board[1][1].getText()&&board[1][1].getText()==board[2][2].getText())
        {
            if(board[0][0].getText()==player)
                return 10;
            if(board[0][0].getText()==opponent)
                return -10;
        }

        if(board[0][2].getText()==board[1][1].getText()&&board[1][1].getText()==board[2][0].getText())
        {
            if(board[0][2].getText()==player)
                return 10;
            if(board[0][2].getText()==opponent)
                return -10;
        }

        return 0;
    }

    private void AutoPlay()
    {
        Move bestMove=findBestMove();
        int row= bestMove.row;
        int col= bestMove.col;

        board[row][col].setText(player);

        playGame(board[row][col]);

    }

    class Move
    {
        int row;
        int col;
    }

    @Override
    protected void onRestart() {

        txt1.setShadowLayer(0F,0F, 0F,0);
        txt2.setShadowLayer(0F,0F, 0F,0);
           winner=-1;
           refresh();
           super.onRestart();

    }

    public void refresh()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j].getText()!="")
                {
                    board[i][j].setText("");
                }

                if(board[i][j].isEnabled()==false)
                {
                    board[i][j].setEnabled(true);
                }
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

           switch(item.getItemId())
           {
               case R.id.menu:
                   onRestart();
                   break;
           }

           return false;
    }
}
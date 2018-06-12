package com.example.sayan.tictactoe

import android.graphics.Color
import android.graphics.Xfermode
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import  kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.R.menu
import android.view.MenuInflater
import com.example.sayan.tictactoe.R
import com.example.sayan.tictactoe.R.id.*


class MainActivity : AppCompatActivity() {

    lateinit var xWinner:TextView
    lateinit var oWinner:TextView
    lateinit var txt1:TextView
    lateinit var txt2:TextView
    lateinit var result:TextView

    internal var board = Array<Array<Button?>>(3) { arrayOfNulls(3) }

    var x:Int = 0
    var o:Int = 0
    var winner :Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        xWinner = findViewById<TextView>(R.id.x_result)
        oWinner = findViewById<TextView>(R.id.o_result)

        txt1 = findViewById<TextView>(R.id.x_sign)
        txt2 = findViewById<TextView>(R.id.o_sign)
        result=findViewById(R.id.result)

        board[0][0] = findViewById(R.id.bu1)
        board[0][1] = findViewById(R.id.bu2)
        board[0][2] = findViewById(R.id.bu3)
        board[1][0] = findViewById(R.id.bu4)
        board[1][1] = findViewById(R.id.bu5)
        board[1][2] = findViewById(R.id.bu6)
        board[2][0] = findViewById(R.id.bu7)
        board[2][1] = findViewById(R.id.bu8)
        board[2][2] = findViewById(R.id.bu9)


        if (savedInstanceState != null) {
            x = savedInstanceState.getInt("x val")
            o = savedInstanceState.getInt("o val")
        }

        txt1.setTextColor(Color.parseColor("#efd48a"))
        txt2.setTextColor(Color.parseColor("#f9b896"))

        xWinner.text=x.toString()
        oWinner.text=o.toString()
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("x val", x)
        savedInstanceState.putInt("o val", o)
    }

    fun buClick(view:View){
        val buSelected= view as Button
        var cellID=0
        when(buSelected.id){
            R.id.bu1-> cellID=1
            R.id.bu2-> cellID=2
            R.id.bu3-> cellID=3
            R.id.bu4-> cellID=4
            R.id.bu5-> cellID=5
            R.id.bu6-> cellID=6
            R.id.bu7-> cellID=7
            R.id.bu8-> cellID=8
            R.id.bu9-> cellID=9

        }
        // Toast.makeText(this,"ID:"+ cellID, Toast.LENGTH_LONG).show()

        PlayGame(cellID,buSelected)
    }

    var player1=ArrayList<Int>()
    var player2=ArrayList<Int>()
    var ActivePlayer=1

    fun PlayGame(cellID:Int,buSelected:Button){

        CheckWinner()
        if(ActivePlayer==1){
            buSelected.text="X"
            buSelected.setTextColor(Color.parseColor("#FFFED86F"))
            player1.add(cellID)
            ActivePlayer=2
            if (isLeft() == true && winner == -1) {
                AutoPlay()
            } else
                Toast.makeText(this, "It is a draw!! Try again", Toast.LENGTH_LONG).show()


        }else{
            buSelected.text="O"
            buSelected.setTextColor(Color.parseColor("#FFFEA16F"))
            player2.add(cellID)
            ActivePlayer=1
        }

        buSelected.isEnabled=false
        if(winner==-1)
        CheckWinner()
    }

    fun isLeft(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j]!!.getText() === "")
                    return true
            }
        }
        return false
    }



    fun  CheckWinner()

    {
        // row 1
        if(player1.contains(1) && player1.contains(2) && player1.contains(3)){
            winner=1
        }
        if(player2.contains(1) && player2.contains(2) && player2.contains(3)){
            winner=2
        }


        // row 2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6)){
            winner=1
        }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6)){
            winner=2
        }




        // row 3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9)){
            winner=1
        }
        if(player2.contains(7) && player2.contains(8) && player2.contains(9)){
            winner=2
        }



        // col 1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7)){
            winner=1
        }
        if(player2.contains(1) && player2.contains(4) && player2.contains(7)){
            winner=2
        }



        // col 2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8)){
            winner=1
        }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8)){
            winner=2
        }


        // col 3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9)){
            winner=1
        }
        if(player2.contains(3) && player2.contains(6) && player2.contains(9)){
            winner=2
        }

        if(player1.contains(1)&&player1.contains(5)&&player1.contains(9))
        {
            winner=1
        }


        if(player2.contains(1)&&player2.contains(5)&&player2.contains(9))
        {
            winner=2
        }


        if(player1.contains(3)&&player1.contains(5)&&player1.contains(7))
        {
            winner=1
        }
        if(player2.contains(3)&&player2.contains(5)&&player2.contains(7))
        {
            winner=2
        }

        if (winner == 1) {
            x++
            xWinner.text = x.toString()
            txt1.setTextColor(Color.parseColor("#c0744b"))
            txt1.setShadowLayer(50.0f, 0.0f, 0.0f, Color.WHITE)
            result.text = "You Win!!!"
        }

        if (winner == 2) {
            o++
            oWinner.text = o.toString()
            txt2.setTextColor(Color.parseColor("#d2ad45"))
            txt2.setShadowLayer(50.0f, 0.0f, 0.0f, Color.WHITE)
            result.text = "Phone Wins!!!"
        }

        if (winner != -1) {
            Toast.makeText(this, "Winner is :$winner", Toast.LENGTH_LONG).show()
            for (i in 0..2) {
                for (j in 0..2) {
                    if (board[i][j]!!.isEnabled()) {
                        board[i][j]!!.setEnabled(false)
                    }
                }
            }
        }
    }

    fun AutoPlay(){

        var emptyCells=ArrayList<Int>()
        for ( cellID in 1..9){

            if(!( player1.contains(cellID) || player2.contains(cellID))) {
                emptyCells.add(cellID)
            }
        }


        val r=Random()
        val randIndex=r.nextInt(emptyCells.size-0)+0
        val cellID= emptyCells[randIndex]

        var buSelect:Button?
        when(cellID){
            1 -> buSelect = findViewById(R.id.bu1)
            2 -> buSelect = findViewById(R.id.bu2)
            3 -> buSelect = findViewById(R.id.bu3)
            4 -> buSelect = findViewById(R.id.bu4)
            5 -> buSelect = findViewById(R.id.bu5)
            6 -> buSelect = findViewById(R.id.bu6)
            7 -> buSelect = findViewById(R.id.bu7)
            8 -> buSelect = findViewById(R.id.bu8)
            9 -> buSelect = findViewById(R.id.bu9)
            else -> {
                buSelect = findViewById(R.id.bu1)
            }
        }
        PlayGame(cellID,buSelect)
    }

     fun refresh() {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j]!!.getText() != "")
                {
                    board[i][j]!!.setText("")
                }
                if (board[i][j]!!.isEnabled() == false) {
                    board[i][j]!!.setEnabled(true)
                }
            }
        }
    }

    override fun onRestart() {

        txt1.setShadowLayer(0F,0F, 0F,0)
        txt2.setShadowLayer(0F,0F, 0F,0)
        player1.clear()
        player2.clear()
        winner=-1
        refresh()
        super.onRestart()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.getItemId()) {
            R.id.menu->onRestart()
        }
        return false
    }
}
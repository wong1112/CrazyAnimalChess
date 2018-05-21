package eg_draw;



import java.io.Serializable;



public class Datasave implements Serializable{



    chesspiece chess[]= new chesspiece[16];

    public Datasave (chesspiece chess[])

    {

        for(int i=0;i<16;i++)

        {

            this.chess[i]=chess[i];

        }

    }

    public chesspiece[] getchesspiece()

    {

        return chess;

    }



}
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class tictactoe extends JFrame
{
    JFrame frame;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;
    JButton b6;
    JButton b7;
    JButton b8;
    JButton b9;
    int turn;
    //int mat[][]={{0,0,0},{0,0,0},{0,0,0}}; //1=X,2=O
    int current;    //node,minlimit,maxlimit
    int tree[][]={ //-1=draw,-2=win,-3=loss 0-NOT DEFINED SO FAR
                   {5,1,8}, //0
                   {1,0,0},  //1
                   {2,0,0},  //2
                   {3,9,14},  //3  implemented
                   {4,0,0},  //4
                   {6,0,0},  //5
                   {7,0,0},  //6
                   {8,35,40},  //7 implemented
                   {9,0,0},  //8  
                   {1,15,16},  //9
                   {2,22,23},  //10
                   {4,0,0},  // 11
                   {6,0,0},  //12
                   {8,25,26},  //13
                   {9,0,0},  //14
                   {9,16,16},  //15   10=X=REMAINING
                   {90,21,21},  //16
                   {6,18,19},  //17
                   {4,58,60},  //18
                   {40,20,20},  //19
                   {4,-2,-2},  //20
                   {9,-2,-2},  //21
                   {8,0,0},  //22
                   {80,24,24},  //23
                   {8,-2,-2},  //24
                   {2,27,27},  //25
                   {20,56,56},  //26
                   {1,28,29},  //27
                   {9,30,30},  //28
                   {90,34,34},  //29
                   {6,31,32},  //30
                   {4,-1,-1},  //31
                   {40,33,33},  //32
                   {4,-2,-2},  //33
                   {9,-2,-2},  //34
                   {1,41,42},  //35
                   {3,0,0},  //36
                   {4,50,51},  //37
                   {6,0,0},  //38
                   {7,0,0},  //39
                   {9,0,0},  //40
                   {9,43,43},  //41
                   {10,57,57},  //42
                   {7,44,44},  //43
                   {3,47,47},  //44
                   {4,48,48},  //45
                   {340,49,49},  //46
                   {3,-2,-2},  //47
                   {4,-2,-2},  //48
                   {3,-2,-2},  //49  3/4
                   {6,0,0},  //50
                   {60,52,52},  //51
                   {6,-2,-2},  //52
                   {3,0,0},  //53
                   {30,55,55},  //54
                   {3,-2,-2},  //55
                   {2,-2,-2},  //56
                   {9,-2,-2},  //57
                   {2,61,62},  //58
                   {7,65,66},  //59
                   {8,69,70},  //60
                   {7,63,63},  //61
                   {8,64,64},  //62
                   {8,-2,-2},  //63
                   {7,-1,-1},  //64
                   {2,67,67}, //65
                   {8,68,68},  //66
                   {8,-1,-1},  //67
                   {2,-1,-1},  //68
                   {2,71,71},  //69
                   {7,72,72},  //70
                   {7,-1,-1}, //71
                   {2,-2,-2},  //72
                  };
    
    tictactoe()
    {
        frame=new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        

        JPanel panel=new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(new BorderLayout());

        JPanel grid=new JPanel();
        grid.setLayout(new GridLayout(3,3)); 

        ActionListener logic=new logic();

        b1=new JButton("1");
        b1.setSize(20,20); 
        b1.addActionListener(logic);    
        b2=new JButton("2");
        b2.setSize(20,20);    
        b2.addActionListener(logic);   
        b3=new JButton("3");
        b3.setSize(20,20);    
        b3.addActionListener(logic);   
        b4=new JButton("4");
        b4.setSize(20,20);    
        b4.addActionListener(logic);   
        b5=new JButton("O");
        b5.setSize(20,20);    
        b5.addActionListener(logic);   
        b6=new JButton("6");
        b6.setSize(20,20);    
        b6.addActionListener(logic);   
        b7=new JButton("7");
        b7.setSize(20,20);
        b7.addActionListener(logic);    
        b8=new JButton("8");
        b8.setSize(20,20);    
        b8.addActionListener(logic);   
        b9=new JButton("9");
        b9.setSize(20,20);    
        b9.addActionListener(logic);   
 
        JButton start=new JButton("Start");
        //start.addActionListener(logic);
        //panel.add(start,BorderLayout.SOUTH);
        
        grid.add(b1);
        grid.add(b2);
        grid.add(b3);
        grid.add(b4);
        grid.add(b5);
        grid.add(b6);
        grid.add(b7);
        grid.add(b8);
        grid.add(b9);   
           
        panel.add(grid,BorderLayout.CENTER);

        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        current=0;
        turn=1;
    }

    private void reset()
    {
        b1.setText("1");
        b2.setText("2");
        b3.setText("3");
        b4.setText("4");
        b5.setText("o");
        b6.setText("6");
        b7.setText("7");
        b8.setText("8");
        b9.setText("9");
        current=0;
        turn=0;
    }

    private int settext(String k,int t)
    {
        String s;
        int m=Integer.parseInt(k);
        //mat[m/3][m-3*(m/3)]=2-t;   
        if(t==0)
            s="O";
        else
            s="X";
        if(m==1)
        {
           b1.setText(s);
        }
        else if(m==2)
        {
           b2.setText(s);
        }
        else if(m==3) 
        {
           b3.setText(s);
        }
        else if(m==4) 
        {
           b4.setText(s);
        }
        else if(m==5) 
        {
           b5.setText(s);
        }
        else if(m==6) 
        {
           b6.setText(s);
        }
        else if(m==7) 
        {
           b7.setText(s);
        }
        else if(m==8) 
        {
           b8.setText(s);
        }
        else if(m==9) 
        {
           b9.setText(s);
        }
        else
           return(1);
        return(0);
    }

    private void play()
    {
        int next;
        do
        {
           next=(int)(tree[current][1]+(Math.random()*(tree[current][2]-tree[current][1])));
        }while(tree[next][1]==0);
        System.out.println(next+" "+tree[next][0]);
        current=next;
        settext(Integer.toString(tree[current][0]),0);
        turn=1-turn;
        check();
    } 

    private void check()
    {
        if(tree[current][1]==-2)
        {
            JOptionPane.showMessageDialog(frame,"The computer has won");
            reset();
        }
        else if(tree[current][1]==-1)
        {
            JOptionPane.showMessageDialog(frame,"Draw");
            reset();
        }
        else if(tree[current][1]==-3)
        {
            JOptionPane.showMessageDialog(frame,"You win");  
            reset();
        }  
    }

    class logic implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
          int n,i,no;
          String key=e.getActionCommand();
          //if(key.equals("Start"))
          {
             //reset();
          }
          //else
          {
            no=Integer.parseInt(key);
            if(turn==1)
            {
                n=settext(key,1);
                if(n==0) 
                {
                    //change current
                    
                    //if X
                    for(i=tree[current][1];i<=tree[current][2];i++)
                    {
                        if(tree[i][0]/10!=0) //check for presence of X
                        {   //check if user has gone for any other key  
                            if(no!=tree[i][0]/10 ||((tree[i][0]>=100)&&(no!=tree[i][0]/100 ||no!=(tree[i][0]-tree[i][0]/100*100)/10)))  
                            {
                                current=i;
                                System.out.println(current+" "+tree[current][0]);
                            } 
                        }
                    }
 
                    for(i=tree[current][1];i<=tree[current][2];i++)
                    {
                       if(tree[i][0]==no && tree[i][1]!=0)
                       {
                           current=i;  
                           System.out.println(current+" "+tree[current][0]); 
                           break;
                       }
                    }   
                    turn=1-turn;
                    check();
                    play();

                }
           }
          } 
        }
    }

    public static void main(String []args)
    {
        JFrame f=new tictactoe();
    }    
}
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.sql.*;

public class makedt extends JFrame
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
    JButton bnext;
    JButton bstep;
    JButton bend;
    JButton bstop;
    JRadioButton r1;
    JRadioButton r2;
    JRadioButton r3;
    ButtonGroup g;
    JButton bresult;


    int mat[][][]=new int[3][3][3];
    static ResultSet rs;
    static Statement s;
    static PreparedStatement ps;
    static PreparedStatement ps1;
    static Connection con;


    int level;   
    int stop;  //1=first person,2=second person,0=no person
    int result;  //0=draw 1=win
    int val; //get from db
    int turn;
    int current; //present row
    int col; //present column

   makedt()
   {
        frame=new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        

        JPanel panel=new JPanel();

        frame.getContentPane().add(panel);
        panel.setLayout(new BorderLayout());

        JPanel grid=new JPanel();
        grid.setLayout(new GridLayout(3,3)); 

        JPanel options=new JPanel();
        panel.setLayout(new GridLayout(4,2,10,10));

        b1=new JButton("1");
        b1.setSize(20,20);     
        b2=new JButton("2");
        b2.setSize(20,20);       
        b3=new JButton("3");
        b3.setSize(20,20);       
        b4=new JButton("4");
        b4.setSize(20,20);      
        b5=new JButton("o");
        b5.setSize(20,20);       
        b6=new JButton("6");
        b6.setSize(20,20);       
        b7=new JButton("7");
        b7.setSize(20,20);    
        b8=new JButton("8");
        b8.setSize(20,20);       
        b9=new JButton("9");
        b9.setSize(20,20);       
        
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

        ActionListener logic=new logic();

        bnext=new JButton("next");
        bstep=new JButton("step");
        bstop=new JButton("stop");
        bnext.addActionListener(logic);
        bend=new JButton("end");
        bend.addActionListener(logic);
        bresult=new JButton("result");  
        bresult.addActionListener(logic);

        r1=new JRadioButton("draw");
        r2=new JRadioButton("X wins");
        r3=new JRadioButton("0 wins");

        g=new ButtonGroup();
        g.add(r1);
        g.add(r2);
        g.add(r3);

        options.add(bnext);
        options.add(bstep);
        options.add(bend);
        options.add(bstop);
        options.add(r1);
        options.add(r2);
        options.add(r3);
        options.add(bresult);
      
        panel.add(options,BorderLayout.SOUTH);

        KeyListener enter=new enter();
        bstep.addKeyListener(enter);

        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        turn=0;
        current=0;  //0
        col=0;
        
   }

    private void reset()
    {
        b1.setText("1");
        b2.setText("2");
        b3.setText("3");
        b4.setText("4");
        b5.setText("O");
        b6.setText("6");
        b7.setText("7");
        b8.setText("8");
        b9.setText("9");
        turn=0;
        col=0;
        for(int i=0;i<3;i++)
           for(int j=0;j<3;j++)
           { 
               mat[i][j][0]=0;
               mat[i][j][1]=0;
           }
    }

    private void settext(int c)
    {
        String s;

        //translate col val into its corresponding val stored in mat[][][]

        int m=mat[c/3][c-3*(c/3)][2];
        mat[(m-1)/3][(m-1)-3*((m-1)/3)][turn]=1;   
        System.out.println("turn"+turn);

        if(turn==0)
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
    }


   private int check()
   {
       int i,j,k,sum=0; 
  
       for(k=0;k<3;k++)
       {
          for(i=0;i<3;i++)
          {
              for(j=0;j<3;j++)
              System.out.print(mat[i][j][k]+" ");
          }
          System.out.println();
       }

       //check for 3 marks in a row
       for(k=0;k<2;k++)
       {
         for(i=0;i<3;i++)
         {
            for(j=0;j<3;j++)
               sum=sum+mat[i][j][k];
            if(sum==3)
            {
               System.out.println("s"+i);
               return(0);
            }
            sum=0;
          }
       }

       //check for 3 marks in a col
       for(k=0;k<2;k++)
       {
         for(i=0;i<3;i++)
         {
            for(j=0;j<3;j++)
               sum=sum+mat[j][i][k];
            if(sum==3)
            {System.out.println("t"+i);
               return(0);
            }
            sum=0;
          }
       }
       
       //check for 3 marks in principal diagonal
       for(k=0;k<2;k++)
       {
          sum=mat[0][0][k]+mat[1][1][k]+mat[2][2][k];
          if(sum==3)
          {System.out.println("u"+k);
             return(0);
          }
       }
       //check for 3 marks in other diagonal
       for(k=0;k<2;k++)
       {
          sum=mat[0][2][k]+mat[1][1][k]+mat[2][0][k];
          if(sum==3)
          {System.out.println("v"+k);
             return(0);
          }
       }
       return(1);       
        
   }

   class enter implements KeyListener
   {
       public void keyReleased(KeyEvent e)
       {          
       }
       public void keyPressed(KeyEvent e)
       {
           System.out.println("c");
           int i; 
           
           try
           {
           if(col<9)
           {
               
               i=check();
                 System.out.println(i);
              if(i==0)
              {
                 col++;

               System.out.println("win"+col);
                 //show final status through radio buttons
                  if(turn==1)
                     r2.setSelected(true);
                  else
                    r3.setSelected(true);
                   
                  ps1.setInt(1,col);

                  ps1.setInt(2,turn);
                  ps1.setInt(3,current);
                  ps1.executeUpdate();
              }
               else if(i!=0 && col<8)
              {
                  col++;
                  //update buttons
                  turn=1-turn;
                  settext(col);
              }
              else 
                  col++;
           }
           else 
           {
               System.out.println("draw");
               r1.setSelected(true);
               //draw 
               ps1.setInt(1,9); 
               ps1.setInt(2,2);
               ps1.setInt(3,current);
               ps1.executeUpdate();

           }
           }
           catch(Exception err)
           {
               System.out.println("ERROR: " + err);  
               err.printStackTrace();
           }
       }
       public void keyTyped(KeyEvent e)
       {
       }
   }


   class logic implements ActionListener
   {
       public void actionPerformed(ActionEvent e)
       {
           String s=e.getActionCommand();
           if(s.equals("next"))
           {
             try
             {
                reset();
                turn=0;
                col=0;
                current++;
                
                System.out.println("current"+current);
                ps.setInt(1,current);
                ps.execute();
                rs=ps.getResultSet();
                if (rs!=null) // if rs == null, then no ResultSet 
                   while ( rs.next() ) // step through data row-by-row
                   {  
                       for(int i=0;i<3;i++)
                       {
                          for(int j=0;j<3;j++)
                          {
                             mat[i][j][2]=rs.getInt(3*i+j+1+1);  //1=sr
                             System.out.println(current+"row ="+mat[i][j][2]);
                          }
                       }
                   }
                   mat[1][1][0]=1;  //5 is already marked
               }
               catch(Exception err)
               {      
                   System.out.println("ERROR: " + err);  
                   err.printStackTrace();
               }
           }
           else if(s.equals("result"))
           {
               try
               {
                   //add result to db
                   ps1.setInt(1,col);
                   ps1.setInt(2,result);
                   ps1.setInt(3,current);
               }
               catch(Exception err)
               {      
                   System.out.println("ERROR: " + err);  
                   err.printStackTrace();
               }
           }
           else if(s.equals("end"))
           {
               try
               {
                    con.commit();            
            
                    // close prep-Statement 
                    ps.close(); 
   
                   //close connection
                   con.close(); 
               }
               catch(Exception err)
               {      
                   System.out.println("ERROR: " + err);  
                   err.printStackTrace();
               }
           }
           else if(s.equals("stop"))
           {
               //user has found out that a draw is there
               try
               {
                    ps1.setInt(1,col);
                    ps1.setInt(2,2);
                    ps1.setInt(3,current);
                    ps1.executeUpdate();
               }
               catch(Exception err)
               {      
                   System.out.println("ERROR: " + err);  
                   err.printStackTrace();
               }
           }
       }
   }

   private static void train()
   {
       int i=0;
       try
       {
            //load driver
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            //Location of an Access database
            //String filename="db1.mdb"; 

            //String database = "jdbc:odbc:Driver={Microsoft Access Driver(*.mdb)};DBQ=";
            //database+=filename.trim()+";DriverID=22;READONLY=false}"; //add on to end 

            con = DriverManager.getConnection("jdbc:odbc:db1Table1","",""); 
            
            ps=con.prepareStatement("select * from Table1 where sr=?");
            ps1=con.prepareStatement("update Table1 set stop=?,res=? where sr=?");
            
       }
       catch (Exception err) 
       {
           System.out.println("ERROR: " + err);  
           err.printStackTrace();
       }       
   }

   public static void main(String []args)
   {
       JFrame f=new makedt();
       train();  
   }
}
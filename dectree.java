import java.sql.*;
import java.io.*;

public class dectree
{
    static ResultSet rs;
    static Statement s;
    static PreparedStatement ps;
    static Connection con;
                               
    static int tree[][]=new int[150][3]; //0=val,1=start of children,2=end,3=stop 
    static int last;           //last free position in tree
    static int level;
    static int stack[][]=new int[9][2];  //top=level   0=start 1=end
    static int remove[]=new int[20]; 

   private static void removenegcases()
   {
       try
       {
           s.execute("delete * from Table1 where res=1");  //user wins
       }
       catch(Exception r)
       {
           System.out.println("ERROR: " + r);  
           r.printStackTrace();
       }
   }

    private static int maketree(int lev)   //start at that particular level
    {                                                
        int i,limit,end;               
        System.out.println("\nmaketree level="+lev+" node="+tree[stack[lev-1][0]][0]);

        if(lev<9)//9
        {
 
          //get nodes in that level
          try
          {   
                 if(lev==1)
                 {
                     ps=con.prepareStatement("select distinct a2 from Table1 where a1=? and stop>=2");
                     ps.setInt(1,tree[stack[lev-1][0]][0]);
                 }
                  else if(lev==2)
                 {
                     ps=con.prepareStatement("select distinct a3 from Table1 where a2=? and a1=? and stop>=3");
                     ps.setInt(1,tree[stack[lev-1][0]][0]); //parent
                     ps.setInt(2,tree[stack[lev-2][0]][0]);
                }
                  else if(lev==3)
                 {
                     ps=con.prepareStatement("select distinct a4 from Table1 where a3=? and a2=? and a1=? and stop>=4");
                     ps.setInt(1,tree[stack[lev-1][0]][0]); //parent
                     ps.setInt(2,tree[stack[lev-2][0]][0]); 
                     ps.setInt(3,tree[stack[lev-3][0]][0]);                      
                 }
                 else if(lev==4)
                 {
                    ps=con.prepareStatement("select distinct a5 from Table1 where a4=? and a3=? and a2=? and a1=? and stop>=5");
                    ps.setInt(1,tree[stack[lev-1][0]][0]); //parent
                    ps.setInt(2,tree[stack[lev-2][0]][0]); 
                    ps.setInt(3,tree[stack[lev-3][0]][0]); 
                    ps.setInt(4,tree[stack[lev-4][0]][0]); 

                 }
                 else if(lev==5)
                 {
                    ps=con.prepareStatement("select distinct a6 from Table1 where a5=? and a4=? and a3=? and a2=? and a1=? and stop>=6");
                    ps.setInt(1,tree[stack[lev-1][0]][0]); //parent
                    ps.setInt(2,tree[stack[lev-2][0]][0]); 
                    ps.setInt(3,tree[stack[lev-3][0]][0]); 
                    ps.setInt(4,tree[stack[lev-4][0]][0]); 
                    ps.setInt(5,tree[stack[lev-5][0]][0]); 
                 }
                 else if(lev==6)
                 {
                    ps=con.prepareStatement("select distinct a7 from Table1 where a6=? and a5=? and a4=? and a3=? and a2=? and a1=? and stop>=7");
                    ps.setInt(1,tree[stack[lev-1][0]][0]); //parent
                    ps.setInt(2,tree[stack[lev-2][0]][0]); 
                    ps.setInt(3,tree[stack[lev-3][0]][0]); 
                    ps.setInt(4,tree[stack[lev-4][0]][0]); 
                    ps.setInt(5,tree[stack[lev-5][0]][0]); 
                    ps.setInt(6,tree[stack[lev-6][0]][0]); 
                 }
                 else if(lev==7)
                 {
                    ps=con.prepareStatement("select distinct a8 from Table1 where a7=? and a6=? and a5=? and a4=? and a3=? and a2=? and a1=? and stop>=8");
                    ps.setInt(1,tree[stack[lev-1][0]][0]); //parent
                    ps.setInt(2,tree[stack[lev-2][0]][0]); 
                    ps.setInt(3,tree[stack[lev-3][0]][0]); 
                    ps.setInt(4,tree[stack[lev-4][0]][0]); 
                    ps.setInt(5,tree[stack[lev-5][0]][0]); 
                    ps.setInt(6,tree[stack[lev-6][0]][0]); 
                    ps.setInt(7,tree[stack[lev-7][0]][0]); 
                 }
                 else if(lev==8)
                {
                   ps=con.prepareStatement("select distinct a9 from Table1 where a8=? and a7=? and a6=? and a5=? and a4=? and a3=? and a2=? and a1=? and stop>=9");
                   ps.setInt(1,tree[stack[lev-1][0]][0]); //parent
                   ps.setInt(2,tree[stack[lev-2][0]][0]); 
                   ps.setInt(3,tree[stack[lev-3][0]][0]); 
                   ps.setInt(4,tree[stack[lev-4][0]][0]); 
                   ps.setInt(5,tree[stack[lev-5][0]][0]); 
                   ps.setInt(6,tree[stack[lev-6][0]][0]); 
                   ps.setInt(7,tree[stack[lev-7][0]][0]); 
                   ps.setInt(8,tree[stack[lev-8][0]][0]); 
                }

                ps.execute();
                rs=ps.getResultSet();
             
                //store the level nodes in tree

                limit=0;
                i=0;
                while ( rs.next() ) // step through data row-by-row
                {  
                    limit=rs.getRow();
                    tree[last+i][0]=rs.getInt(1);
                    System.out.println(tree[last+i][0]);
                    i++;
                }   

                if(limit!=0)
                { 
                    //store the next level in stack
                    stack[lev][0]=last;
                    stack[lev][1]=last+limit-1;

            
                    //store the tree indices in parent 
                    tree[stack[lev-1][0]][1]=last;
                    tree[stack[lev-1][0]][2]=last+limit-1;  //even works when there is no child under it
                
                    //update limit
                    last=last+limit; 
                     maketree(lev+1);  //get children if not stopped  
                }
                   
                else  //store result in that 
                {
                    //change the info stored by store tree indices in parent(even works when no chdr)
                    if(lev==1)
                    {
                        ps=con.prepareStatement("select distinct res from Table1 where a1=?");
                        ps.setInt(1,tree[stack[lev-1][0]][0]);
                    }
                    else if(lev==2)
                    {
                        ps=con.prepareStatement("select distinct res from Table1 where a1=? and a2=?");
                        ps.setInt(1,tree[stack[lev-2][0]][0]);
                        ps.setInt(2,tree[stack[lev-1][0]][0]);
                    }
                    else if(lev==3)
                    {
                        ps=con.prepareStatement("select distinct res from Table1 where a1=? and a2=? and a3=?");
                        ps.setInt(1,tree[stack[lev-3][0]][0]);
                        ps.setInt(2,tree[stack[lev-2][0]][0]);
                        ps.setInt(3,tree[stack[lev-1][0]][0]); 
                    }
                    else if(lev==4)
                    {
                        ps=con.prepareStatement("select distinct res from Table1 where a1=? and a2=? and a3=? and a4=?");
                        ps.setInt(1,tree[stack[lev-4][0]][0]);
                        ps.setInt(2,tree[stack[lev-3][0]][0]);
                        ps.setInt(3,tree[stack[lev-2][0]][0]);
                        ps.setInt(4,tree[stack[lev-1][0]][0]);
                    }
                    else if(lev==5)
                    {
                        ps=con.prepareStatement("select res from Table1 where a1=? and a2=? and a3=? and a4=? and a5=?");
                        ps.setInt(1,tree[stack[lev-5][0]][0]);
                        ps.setInt(2,tree[stack[lev-4][0]][0]);
                        ps.setInt(3,tree[stack[lev-3][0]][0]);
                        ps.setInt(4,tree[stack[lev-2][0]][0]);
                        ps.setInt(5,tree[stack[lev-1][0]][0]);
                    }
                    else if(lev==6)
                    {
                        ps=con.prepareStatement("select distinct res from Table1 where a1=? and a2=? and a3=? and a4=? and a5=? and a6=?");
                        ps.setInt(1,tree[stack[lev-6][0]][0]);
                        ps.setInt(2,tree[stack[lev-5][0]][0]);
                        ps.setInt(3,tree[stack[lev-4][0]][0]);
                        ps.setInt(4,tree[stack[lev-3][0]][0]);
                        ps.setInt(5,tree[stack[lev-2][0]][0]);
                        ps.setInt(6,tree[stack[lev-1][0]][0]);
                    }
                    else if(lev==7)
                    {
                        ps=con.prepareStatement("select distinct res from Table1 where a1=? and a2=? and a3=? and a4=? and a5=? and a6=? and a7=?");
                        ps.setInt(1,tree[stack[lev-7][0]][0]);
                        ps.setInt(2,tree[stack[lev-6][0]][0]);
                        ps.setInt(3,tree[stack[lev-5][0]][0]);
                        ps.setInt(4,tree[stack[lev-4][0]][0]);
                        ps.setInt(5,tree[stack[lev-3][0]][0]);
                        ps.setInt(6,tree[stack[lev-2][0]][0]);
                        ps.setInt(7,tree[stack[lev-1][0]][0]);
                    }
                    else if(lev==8)
                    {
                        ps=con.prepareStatement("select distinct res from Table1 where a1=? and a2=? and a3=? and a4=? and a5=? and a6=? and a7=? and a8=?");
                        ps.setInt(1,tree[stack[lev-8][0]][0]);
                        ps.setInt(2,tree[stack[lev-7][0]][0]);
                        ps.setInt(3,tree[stack[lev-6][0]][0]);
                        ps.setInt(4,tree[stack[lev-5][0]][0]);
                        ps.setInt(5,tree[stack[lev-4][0]][0]);
                        ps.setInt(6,tree[stack[lev-3][0]][0]);
                        ps.setInt(7,tree[stack[lev-2][0]][0]);
                        ps.setInt(8,tree[stack[lev-1][0]][0]);
                    }
                                     
                    ps.execute();
                    rs=ps.getResultSet();
                    rs.next();
    
                    //doubt
                    tree[stack[lev-1][0]][1]=rs.getInt(1);
                    if(tree[stack[lev-1][0]][1]==0)//result -1=draw,-2=win,-3=lose
                    {                  //db 0=win 2=draw
                        tree[stack[lev-1][0]][1]=-2;                                 
                    }
                    else if(tree[stack[lev-1][0]][1]==2)
                    {
                        tree[stack[lev-1][0]][1]=-1; 
                    } 
                }
                
                //go to next child
  
                while(stack[lev][0]<stack[lev][1])
                { 
                   stack[lev][0]++;    
                   System.out.println("child"+tree[stack[lev][0]][0]);           
                   maketree(lev+1);  //+1 extra
                }

                //when limit==0||1
                //no more children
                System.out.println("no more children");
                return(0);
            }
            catch(Exception x)
            {
                System.out.println("ERROR: " + x);  
                x.printStackTrace(); 
            }
        }
        else
        {
            try
            {
 
                ps=con.prepareStatement("select distinct res from Table1 where a1=? and a2=? and a3=? and a4=? and a5=? and a6=? and a7=? and a8=? and a9=?");
                ps.setInt(1,tree[stack[lev-9][0]][0]);
                ps.setInt(2,tree[stack[lev-8][0]][0]);
                ps.setInt(3,tree[stack[lev-7][0]][0]);
                ps.setInt(4,tree[stack[lev-6][0]][0]);
                ps.setInt(5,tree[stack[lev-5][0]][0]);
                ps.setInt(6,tree[stack[lev-4][0]][0]);
                ps.setInt(7,tree[stack[lev-3][0]][0]);
                ps.setInt(8,tree[stack[lev-2][0]][0]);
                ps.setInt(9,tree[stack[lev-1][0]][0]);
                ps.execute();
                rs=ps.getResultSet();
                 
                rs.next();
                tree[stack[lev-1][0]][1]=rs.getInt(1); 
                if(tree[stack[lev-1][0]][1]==0)//result -1=draw,-2=win,-3=lose
                {                  //db 0=win 2=draw
                    tree[stack[lev-1][0]][1]=-2;                                 
                }
                else if(tree[stack[lev-1][0]][1]==2)
                {
                    tree[stack[lev-1][0]][1]=-1; 
                }
                return(0);
             }
             catch(Exception f)
             {
                System.out.println("ERROR: " + f);  
                f.printStackTrace();
             }
        }
        return(0);
    }

    private static void compact()
    {
        int sum,i,j,k,gres,gval,first;

        for(i=0;tree[i][0]!=0;i++)  //valid nodes
        {
             if(tree[i][0]>9)  //X nodes
             {
                 continue;
             }
             //not end node
             if(tree[i][1]<0)//terminator node
             {
                continue;
             }
             //check if the node has atleast two children
             if(tree[i][2]-tree[i][1]<1)
             {
                 continue;   
             }         

             //check if all children have children by checking that children 
             //don't have negative numbers in their second field

             sum=0; 

             for(j=tree[i][1];j<=tree[i][2];j++)
             {
                 if(tree[j][1]>0)
                      sum++;
             }
             if(sum!=(tree[i][2]-tree[i][1]+1))
             {
                continue; 
             }
             

             //observation:all grangchildren node exept one are termainator nodes
             sum=0;
             for(j=tree[i][1];j<=tree[i][2];j++)
             {
                  if(tree[tree[j][1]][1]<0)
                      sum++;
             }             

             if(sum!=tree[i][2]-tree[i][1])
                 continue;

             else
             {
                System.out.println(i+" "+tree[i][0]);

                //check for each child if others have common grandchildren
                for(j=tree[i][1];j<=tree[i][2];j++)
                {
                     if(tree[tree[j][1]][1]<0)//because X is followed by result
                       continue;
                     
                     sum=0;
                     first=0;
                     gres=3;  //init so that error does not occur during compilation
                     gval=-1;
                     for(k=tree[i][1];k<=tree[i][2];k++)  //others have same grandchild value and result
                     {
                         if(j==k)
                            continue;
                         if(first==0)
                         {
                            gres=tree[tree[k][1]][1];
                            gval=tree[tree[k][1]][0];
                            first=k;
                            sum++;
                         }
                         else
                         {
                            if(gres==tree[tree[k][1]][1] && gval==tree[tree[k][1]][0])
                               sum++;
                         }
                     }
                     //check if the children and grandchildren can be combined
                     if(sum==tree[i][2]-tree[i][1])  //don't consider j==k case
                     {
                           System.out.println("can be combined");
                          //put X
                          //change range

                          //last child then put X just beore it
                          if(j==tree[i][2])
                          {
                              tree[i][1]=j-1;
                              tree[j-1][1]=first;   //point to any terminator node
                              tree[j-1][0]=tree[j][0]*10;  //X=10*value of j
                              System.out.println("new "+tree[j-1][0]+" "+tree[j-1][1]);
                          }
                          else  //put X after it
                          {
                              tree[i][1]=j;
                              tree[i][2]=j+1;
                              tree[j+1][1]=first;//point to any terminator node
                              tree[j+1][0]=tree[j][0]*10;
                              System.out.println("new "+tree[j+1][0]+" "+tree[j+1][1]);
                          }
                          break;
                     }
                }
             }
        }
    }

    private static void writetofile(String file)
    {
        try
        {
           int i=0;
           String v="";   
           FileWriter fstream=new FileWriter(file);
           BufferedWriter out=new BufferedWriter(fstream);
           while(tree[i][0]!=0)
           {
               v=tree[i][0]+" "+tree[i][1]+" "+tree[i][2];
               if(!v.equals(""))
                  out.write(v+"\n");
               i++; 
           }
           out.close();
        }
        catch(Exception n)
        {  
            System.out.println("ERROR: " + n);  
            n.printStackTrace();
        }
    }

    public static void main(String []args)
    {

        //init
        tree[0][0]=5;
        stack[0][0]=0;
        stack[0][1]=0;
        last=1;   //position at which new entry can be made 
        level=1;

        try
        {   
            //load driver
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

            con = DriverManager.getConnection("jdbc:odbc:db1Table1","","");   
            s=con.createStatement(); 

            removenegcases(); 

            maketree(level);
        
            writetofile("dtree.txt");

            compact(); 
 
            writetofile("tempdtree.txt");
           
            con.commit();
            s.close();
            con.close();

        }
        catch(Exception e)
        {
           System.out.println("ERROR: " + e);  
           e.printStackTrace();
        }


    }
}
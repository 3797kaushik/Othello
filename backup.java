import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
			// 		black =0  				white =1
			
class reversi implements MouseListener
{
	JFrame f;
	
	JLabel l[][];
	static int ba[][]=new int [8][8];
	static int ba1[][]=new int [8][8];
	static int p=0;
	JLabel turn,black,white;
	Icon image0,image1,bydefault,green;
	JLabel l1;
	JPanel p1;
	
	reversi()
	{
		f =new JFrame("OTHELLO");
		l= new JLabel[8][8];
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p1=new JPanel();
		
		p1.setLayout(new GridLayout(8,8));
		f.setSize(800,800);
		f.setLocationRelativeTo(null);
		image0 = new ImageIcon("black.jpg");      	
    	image1 = new ImageIcon("brown.jpg");
		bydefault=new ImageIcon("RED.jpg");
		green =new ImageIcon("green.jpg");
		
		////////////////////////////////////////////////////////////
		int i,j,k;
		for(i=0;i<8;i++)
		{
			for(j=0;j<8;j++)
			{
				l[i][j]= new JLabel();
				l[i][j].setSize(100,100);
				l[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				l[i][j].setIcon(bydefault);
				l[i][j].setEnabled(true);
				p1.add(l[i][j]);
				l[i][j].addMouseListener(this);
				
				ba[i][j]=3;
			}
		}
		turn =new JLabel("                                                                                BLACK's turn");
		turn.setSize(200,300);
		
		
			l[3][4].setIcon(image0);
			ba[3][4]=0;
			l[3][4].setEnabled(true);
			l[3][3].setIcon(image1);
			ba[3][3]=1;
			l[3][3].setEnabled(true);
			l[4][3].setIcon(image0);
			ba[4][3]=0;
			l[4][3].setEnabled(true);
			l[4][4].setIcon(image1);
			ba[4][4]=1;
			l[4][4].setEnabled(true);
			f.add(p1);
			f.add(turn,BorderLayout.SOUTH);
			optionSelect(0);				
	}
	////////////////////////////////////////////////////////
	int score()				//  will return the no of black disks in the bord
	{
		int i,j,k=0;
		for(i=0;i<8;i++)
		{
			for(j=0;j<8;j++)
			{
				if(ba[i][j]==0)
				{
					k++;
				}
			}
		}
		return k;
	}
	////////////////////////////////////////////////////////////////////
	int totalCount()			// total count will return no of disks in the board
	{
		int i,j,k=0;
		for(i=0;i<8;i++)
		{
			for(j=0;j<8;j++)
			{
				if(ba[i][j]==1 || ba[i][j]==0)
				{
					k++;
				}
			}
		}
		return k;
	}
	///////////////////////////////////////////////////////////////
	public void mouseClicked(MouseEvent me)
	{
	   int i,j;
       for(i=0;i<8;i++)
        {	
	       for(j=0;j<8;j++)
		   {	   			
		      if(me.getSource()==l[i][j])
		      {
			    if(ba[i][j]==3 && ba1[i][j]==5)
			    { 
       		     if(p%2==0)
			  	  {
					l[i][j].setIcon(image0);
					ba[i][j]=0;
					turn.setText("                                                                                            BLACK's turn");
					System.out.println("****************black 'sturn ");
				  }
			     else
                  {
					 l[i][j].setIcon(image1);
					 ba[i][j]=1;
					 turn.setText("                                                                                                     WHITE's turn");
					System.out.println("**************** white'sturn ");
                  }
				  p++;
				  colorChanged(i,j);
				  toDefault();
		        }
		      } 
		   }
        }
		arraydisp();
		nextMove();
	}
	public void nextMove()
	{
		int key;
		if(p%2==0)
		{
			key=0;
		}
		else
		{
			key=1;
		}
		
		if(optionSelect(key)<=0)
		{
			p++;
			if(key==0)
			{
			  key=1;
			  JOptionPane.showMessageDialog(null,"The black is missing his turn.............................");
			  System.out.println("The black is missing his turn..............");
			}
			
			else
			{
				key=0;
				JOptionPane.showMessageDialog(null,"The white is missing his turn.............................");
				System.out.println("The white is missing his turn..............");
			}
			 if(optionSelect(key)<=0)
			{
				System.out.println("the game over");
				int i=score(),j=totalCount()-score();
				String s;
				if(i>j)
				{
					s="black";
				}
				else if(i==j)
				{
				   JOptionPane.showMessageDialog(null,"The match is a tie......................");
					return;	
				}
				else
				{
					s="white";
				}
				JOptionPane.showMessageDialog(null,s + "  is the winner");
				return;
			}
		}
		else
		{
			System.out.println("the black score is " +score());
			System.out.println("the white score is " +(totalCount()-score()));
			return;
		}
		optionSelect(key);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int optionSelect(int key)
	{
		int i,j,k=0;
		boolean t=false;
		for(i=0;i<8;i++)
		{
			for(j=0;j<8;j++)
			{
				t=_verticalup(key,i,j) ;
				t= t || (_verticaldown(key,i,j)  || _horizontalleft(key,i,j)  || _horizontalalright(key,i,j) || _upleft(key,i,j) ); 
				t= t|| (_upright(key,i,j)|| _downleft(key,i,j)|| _downright(key,i,j) );
				if(ba[i][j]==3)
				{
					if(t==true)
					{	
						l[i][j].setIcon(green);
						k++;
						ba1[i][j]=5;
					}
				}
			}
		}
		return k;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	boolean _downright(int key,int i,int j)
	{
		//System.out.println("*************** down right  *******************");
		if(i==0 || j==7)
		{
			return false;
		}
		else
		{
			int x=i-1,y=j+1;
			if(ba[x][y]==key || ba[x][y]==3)
			{
				return false;
			}
			if(y>=7)
			{return false;}
			while(ba[x][y]!=key && ba[x][y]!=3 && (x>=0 && y<=7))
			{
				x--;y++;
				if(x<=0)
		     {return false;}
				if(y>=7)
			{return false;}
			}
			if(y>=7)
			{return false;}
			if(ba[x][y]==3)
			{return false;}
			if((x<=0 || y>=7) && ba[x][y]!=key)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	boolean _downleft(int key,int i,int j)
	{
		//System.out.println("*************** down left *******************");
		if(i==7 || j==0)
		{
			return false;
		}
		else
		{
			int x=i+1,y=j-1;
			if(ba[x][y]==key || ba[x][y]==3)
			{
				return false;
			}
			if(x>=7)
		   {return false;}
			while(ba[x][y]!=key && ba[x][y]!=3 && (x<=7 && y>=0))
			{
				x++;y--;
				if(x>=7)
				{return false;}
				if(y<=0)
		     {return false;}
			}
			
			if(ba[x][y]==3)
			{return false;}
			if((x>=7 || y<=0) && ba[x][y]!=key)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	boolean _upright(int key,int i,int j)
	{
		//System.out.println("*************** up right *******************");
		if(i==0 || j==0)
		{
			return false;
		}
		else
		{
			int x=i-1,y=j-1;
			if(ba[x][y]==key || ba[x][y]==3)
			{
				return false;
			}
			while(ba[x][y]!=key && ba[x][y]!=3 && (x>=0 && y>=0))
			{
				x--; y--;
				if(y<=0 ||x <=0)
		     {return false;}
			}
			if(ba[x][y]==3)
			{return false;}
		    if((x<=0 ||y<=0) && ba[x][y]!=key)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	boolean _upleft(int key,int i,int j)
	{
		//System.out.println("*************** up left *******************");
		if(i==7 || j==7)
		{
			return false;
		}
		else
		{
			int x=i+1,y=j+1;
			if(ba[x][y]==key || ba[x][y]==3)
			{
				return false;
			}
			
			while(ba[x][y]!=key && ba[x][y]!=3 && (x<=7 && y<=7))
			{if(y>=7)
			{return false;}
			if(x>=7)
		   {return false;}
				x++; y++;
			
			}
			if(ba[x][y]==3)
			{
				return false;
			}
			if((x>=7 || y>=7) &&ba[x][y]!=key)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////////
	
	///////////////////////////////////////////////////////////////////////////////
	boolean _horizontalalright(int key,int i,int j)
	{
		//System.out.println("*************** horizontal right *******************");
		int x=i,y=j-1;
	  if(j==0)
	  {
		  return false;
	  }		  
	  else
	  {
		  
		  if(ba[x][y]==key || ba[x][y]==3)
		  {
			  return false;
		  }
		  while(ba[x][y]!=key && ba[x][y]!=3 && y>=0)
		  {
			  	if(y<=0)
		   {return false;}
			  y--;
		  }
		  if(ba[x][y]==3)
		  {
			  return false;
		  }
		  if(y<=0 && ba[x][y]!=key)
		  {
			  return false;
		  }
		  else
		  {
			  return true;
		  }
	  }
	}
	boolean _horizontalleft(int key,int i,int j)
	{
		//System.out.println("*************** horizontalleft *******************");
		if(j==7)
		{
			return false;
		}
		else
		{
			int x=i,y=j+1;
			if(ba[x][y]==key || ba[x][y]==3)
			{return false;}
			if(y>=7)
			{return false;}
		    while(ba[x][y]!=key && ba[x][y]!=3 && y<=7)
			{
				y++;
				if(y>=7)
				{return false;}

			}
			if(y>=7)
			{return false;}

			if(ba[x][y]==3)
			{
				return false;
			}
			if(y>=7 &&ba[x][y]!=key)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	///////////////////////////////////////////////////////////////////
	boolean _verticalup(int key,int i,int j)
	{
		//System.out.println("*************** verticalup *******************");
		if(i==0)
		{
			return false;
		}
		else
		{
			int x=i-1,y=j;
			if(ba[x][y]==key || ba[x][y]==3)
			{return false;}
			while(ba[x][y]!=key && ba[x][y]!=3 && x>=0)
			{	if(x<=0)
		   {return false;}
				x--;
			}
			if(ba[x][y]==3)
			{
				return false;
			}
			if(x<=0 && ba[x][y]!=key)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	///////////////////////////////////////////////////////////////
	boolean _verticaldown(int key,int i,int j)
	{
		//System.out.println("*************** verticaldown *******************");
		if(i==7)
		{
			return false;
		}
		else
		{
		   int x=i+1,y=j;
		   if(x>=7)
		   {return false;}
		   if(ba[x][y]==key ||ba[x][y]==3)
			{return false;}
		   while(ba[x][y]!=key && ba[x][y]!=3 && x<=7)
		   {
			   x++;if(x>7)
		   {return false;}
		   }
		   if(ba[x][y]==3)
		   {
			   return false;
		   }
		   if(x>=7 && ba[x][y]!=key)
		   {
			   return false;
		   }
		   else
		   {
			   return true;
		   }
		}
	}
	///////////////////////////////////////////////////////////////////////////////
	public void toDefault()
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(ba[i][j]==3 || ba[i][j]==5)
				{
					ba[i][j]=3;
					ba1[i][j]=0;
					l[i][j].setIcon(bydefault);
				}
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////
	public static void main(String ar[])
	{
		new reversi();
	}
	///////////////////////////////////////////////////////////////////////////////////
	public void mouseEntered(MouseEvent m) {}	public void mouseExited(MouseEvent m) {}	public void mousePressed(MouseEvent m) {}
	public void mouseReleased(MouseEvent m) {}
	/////////////////////////////////////////////////////////////////////////////////////
	public void colorChanged(int i,int j)
	{
		int key;
		key=ba[i][j];
		verticalup(key,i,j);
		verticaldown(key,i,j);
		horizontalleft(key,i,j);
		horizontalright(key,i,j);
		upleft(key,i,j);
		upright(key,i,j);
		downleft(key,i,j);
		downright(key,i,j);
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	void downright(int key,int a,int b)
	{
		if(a==0 || b==7)
		{
			return;
		}
		if(ba[a-1][b+1]==key || ba[a-1][b+1]==3)
		{
			return;
		}
		int x=a-1,y=b+1,i=0;
		while((x>0 && y<7) && ba[x][y]!=key)
		{
			
			x--;
			y++;i++;
		}
		if(ba[x][y]==3)
		{
			return;
		}
		if((y>=7 || x<=0) && key!=ba[x][y])
		{
			return;
		}
		while(i>0)
		{
			i--;x++;y--;
			if(key==0)
					{
						l[x][y].setIcon(image0);
						ba[x][y]=0;
					}
					else
					{
						l[x][y].setIcon(image1);
						ba[x][y]=1;
					}
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////
	void downleft(int key,int a,int b)
	{
		if(a==7 || b==0)
		{
			return;
		}
		if(ba[a+1][b-1]==key || ba[a+1][b-1]==3)
		{
			return;
		}
		int x=a+1,y=b-1,i=0;
		while((x<7 && y>0) && ba[x][y]!=key)
		{
			x++;y--;
			i++;
		}
		if(ba[x][y]==3)
		{
			return;
		}
		if((x>=7 || y<=0) && key!=ba[x][y])
		{
			return;
		}
		while(i>0)
		{
			i--;x--;y++;
			if(key==0)
					{
						l[x][y].setIcon(image0);
						ba[x][y]=0;
					}
					else
					{
						l[x][y].setIcon(image1);
						ba[x][y]=1;
					}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	void upright(int key,int a,int b)
	{
		
		if(a==7 || b==7)
		{
			return;
		}
		if(ba[a+1][b+1]==key || ba[a+1][b+1]==3)
		{
			return;
		}
		int x=a+1,y=b+1,i=0;
		while((x<7 && y<7) && ba[x][y]!=key && ba[x][y]!=3)
		{
			x++;	y++; i++;
		}
		if(ba[x][y]==3)
		{
			return;
		}
		if((y>=7 || x>=7 ) && key!=ba[x][y])
		{
			return;
		}
		while(i>0)
		{
			i--;x--;y--;
			if(key==0)
					{
						l[x][y].setIcon(image0);
						ba[x][y]=0;
					}
					else
					{
						l[x][y].setIcon(image1);
						ba[x][y]=1;
					}
		}
	}
	////////////////////////////////////////////////////////////////////////////////////
	void upleft(int key,int a,int b)
	{
		if(a==0 || b==0)
		{
			return;
		}
		if(ba[a-1][b-1]==key || ba[a-1][b-1]==3)
		{
			return;
		}
		int x=a-1,y=b-1,i=0;
		while( (x>0 && y>0) && ba[x][y]!=key )
		{
			x--;y--;
			i++;
		}
		if(ba[x][y]==3)
		{
			return;
		}
		if((y<=0 || x<=0) && key!=ba[x][y] )
		{
			return;
		}
		while(i>0)
		{
			i--;
			x++;
			y++;
			if(key==0)
					{
						l[x][y].setIcon(image0);
						ba[x][y]=0;
					}
					else
					{
						l[x][y].setIcon(image1);
						ba[x][y]=1;
					}
		}
	}
	/////////////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////////
	void horizontalright(int key,int a,int b)
	{
		int x=a,y=b+1,i=0;
		if(b==7)
		{
		  return;	
		}
		if(ba[a][b+1]==key || ba[a][b+1]==3)
		{
			return;
		}
		else
		{
			while(y<7 && ba[x][y]!=key)
			{
				y++;	i++;
				if(ba[x][y]==3)
				{
					return;
				}
				//System.out.print("\n"+x+"\t"+y +"\n");
			}
			if(ba[x][y]==3)
			{
				return;
			}
			if(y>=7  && key!=ba[x][y])
			{
				return;
			}
			while(i>0)
			{
				i--;	
				y--;			
				//System.out.print("\n"+x+"\t"+y +"\n");
				if(key==0)
					{
						l[x][y].setIcon(image0);
						ba[x][y]=0;
					}
					else
					{
						l[x][y].setIcon(image1);
						ba[x][y]=1;
					}					
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////
	void verticalup(int key,int a,int b)
	{
		int cmp;
		int x=a-1,y=b,i=0;
		if(a==0)
		{
			return;
		}
		if(ba[a-1][b]==key ||ba[a-1][b]==3)
		{
			return;
		}
		else
		{
			while(x>0 &&  (key!=ba[x][y]) && (ba[x][y]!=3) )
			{
				x--;i++;
			}
			if(ba[x][y]==3)
			{
				return;
			}
			if(x<=0  &&(key!=ba[x][y]) )
			{
				return;
			}
			else
			{
				while(i>0)
				{
					x++;
					i--;
					if(key==0)
					{
						l[x][y].setIcon(image0);
						ba[x][y]=0;
					}
					else
					{
						l[x][y].setIcon(image1);
						ba[x][y]=1;
					}
				}
			}
			
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////
	public void verticaldown(int key,int a,int b)
	{
		int x=a+1,i=0;int y=b;
		if(a==7)
		{
			return;
		}
		if(ba[a+1][b]==key || ba[a+1][b]==3)
		{
			return;
		}
		while(x<7 && ba[x][y]!=key)
		{
			x++;
			i++;
		}
		if(ba[x][y]==3)
		{
			return;
		}
		if(x>=7 && key!=ba[x][y])
		{
			return;
		}
		while(i>0)
		{
			i--;
			x--; 
				if(key==0)
					{
						l[x][y].setIcon(image0);
						ba[x][y]=0;
					}
					else
					{
						l[x][y].setIcon(image1);
						ba[x][y]=1;
					}
		}
	}
//////////////////////////////////////////////////////////////////////////////////	
	public void horizontalleft(int key,int a,int b)
	{
		int i=0;
		int y=b-1,x=a;
		if(b==0) { return; }
		if(ba[a][b-1]==key  || ba[a][b-1]==3)
		{
			return;
		}
		while(y>0 && ba[x][y] !=key)
		{
			y--;i++;
			if(ba[x][y]==3)
				{
					return;
				}
		}
		if(ba[x][y]==3)
		{
			return;
		}
		if(y<=0 && key !=ba[x][y])
		{
			return;
		}
		while(i>0)
		{
			i--;
			y++;
			if(key==0)
			{
				l[x][y].setIcon(image0);
				ba[x][y]=0;
			}
			else
			{
				l[x][y].setIcon(image1);
				ba[x][y]=1;
			}
		}
	}
	///////////////////////////////////////////
	public void arraydisp()
	{
		System.out.println("*******************");
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				System.out.print(ba[i][j]+"  ");
			}
			System.out.println();
		}
	}
}
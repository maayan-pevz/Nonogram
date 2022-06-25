package nonogram3;

import java.util.LinkedList;

import java.util.Random;


//-------------מחלקה שמתארת את המטריצה-----------
public class nonogram 
{
	private int[][] matrix;
	int size;
	

	 
	//פעולה בונה
	// בונה מטריצה לפי הגודל שהשחקן בחר
	public nonogram(int size) 
	{
		this.matrix=new int[size][size];
		this.size=size;
		
	}
	
	
	// מחזיר את הגודל של המטריצה
	public int getSize() 
	{
		return size;
	}
	
	
	// מגריל שחור ופתור בצורה רנדומלית
	public void createColoredBoard() 
	{
		for (int row=0;row<size;row++ )
    	{
    		int blacks=1000;
    		//מגריל את מספר המשבצות הצבעוניות בשורה
    		while (blacks>size) 
    		{
    			Random rand=new Random();
    			blacks=rand.nextInt(size);
    		}
    		
    		int count=0;
    		
    		while( count<blacks)
    		{
    			Random rand=new Random();
    			int col=rand.nextInt(size);
    			matrix[row][col]=1;
    			count++;
    					
    		}	
    		
    		
    	}
		
		//מדפיס לקונסול את המטריצת שחור ופתור שהמחשב יצר
		
		for (int row=0;row<size;row++ ) 
		{
			System.out.println("");
			for(int col=0; col<size; col++) 
			{
				System.out.print("["+matrix[row][col]+"]");
			}
		}
		
	}

	//מחזיר מערך של רשימות, כל רשימה מתארת את הרצפים באינדקס של השורה
	public LinkedList<Integer>[] Count_Blacks_Rows()     
    {
    	
    	
    	LinkedList<Integer>[] sequence_rows = new LinkedList[size];
    	
    	for (int row=0; row<size; row++) 
    	{
    		LinkedList <Integer> sequence_row=new LinkedList <Integer>();
    		int count=0;
    		
    		
    		for (int col=0; col<size; col++)
    		{
    			
    				
    			    if(col<size-1 &&(matrix[row][col]==1 && matrix[row][col+1]==1) ) {
       				 count++;

    			    }
    			    
    			    else if (count==0 && col==size-1)
    			    {
    			    	if(matrix[row][col]==1)
    			    		sequence_row.add(1); 	
    			    }
    			    
    			    else if (count==0)
    			    {
    			    	if(matrix[row][col]==1)
    			    		sequence_row.add(1); 	
    			    }
 
 
    			    
    			    else 
    			    {
    			    	sequence_row.add(count+1);
    			    	count=0;
    			    }		
    			
    		}
    		
    		sequence_rows[row]=sequence_row;
    		
    	}
    	
    return sequence_rows;
    }
	
	//מחזיר מערך של רשימות, כל רשימה מתארת את הרצפים באינדקס של הטור
	public LinkedList<Integer>[] Count_Blacks_Cols()
	{
	LinkedList<Integer>[] sequence_cols = new LinkedList[size];
	

	for (int col=0; col<size; col++) 
	{
		LinkedList <Integer> sequence_col=new LinkedList <Integer>();
		int count=0;
		
		
		for (int row=0; row<size; row++)
		{
			
			
			 if(row<size-1 &&(matrix[row][col]==1 && matrix[row+1][col]==1) ) {
   				 count++;

			    }
			    
			    else if (count==0 && row==size-1)
			    {
			    	if(matrix[row][col]==1)
			    		sequence_col.add(1); 	
			    }
			    
			    else if (count==0)
			    {
			    	if(matrix[row][col]==1)
			    		sequence_col.add(1); 	
			    }


			    
			    else 
			    {
			    	sequence_col.add(count+1);
			    	count=0;
			    }		
			
			
		}
		
		sequence_cols[col]=sequence_col;
		
	}
	return sequence_cols;
	}
	
	
	// משנה את הערך של המטריצה במקום i,j
	public void changeValue(int i, int j) 
	{
		if (matrix[i][j]==0)
			matrix[i][j]=1;
		
		else
			matrix[i][j]=0;
	}
	
	
	// מחזיר את הערך של המטריצה במקום i,j
	public int getValue(int i, int j) 
	{
		return matrix[i][j];
	}
	
	// קובע את הערך של המטריצה במקום i,j
	public void setValue(int i, int j, int value) 
	{
		 matrix[i][j]=value;
	}
	
	
	// מחזיר את מספר הרצפים הגבוה ביותר במערך 
	public int maxRetzef (LinkedList<Integer>[] list) 
	{
		int max=0;
		for(int i=0; i<list.length; i++)
		{
			int count=0;
			for( int j: list[i]) 
			{
				count=count+1;
				}

			
			max=Math.max(max, count);	
		}
		return max;
	}
	
	
	//מחזיר את מספר הרצפים הגדול ביותר בכל המטריצה
	public int maxRetzefAll() 
	{
		LinkedList<Integer>[] rows=Count_Blacks_Rows() ;
		LinkedList<Integer>[] cols=Count_Blacks_Cols() ;
		
		return Math.max(maxRetzef(rows),maxRetzef(cols));
	}
	

// הפעולה מקבלת שני מערכים של רשימות, מחזירה אמת אם הם זהים אחרת מחזירה שקר
	public boolean isIdentical(LinkedList<Integer>[] a,LinkedList<Integer>[] b ) 
	{
		for (int i=0; i<size;i++) 
		{
			if (!(a[i].isEmpty()) &&!(b[i].isEmpty())) 
			{
				if(a[i].size()!=b[i].size())
					return false;
				
				for (int j=0; j<a[i].size();j++)
				{
					int x=a[i].get(j);
					int y=b[i].get(j);
					
					if(a[i].get(j)!=b[i].get(j))
						return false;
					
				}
					
					
			}
			else 
			{
				if (a[i].isEmpty() &&!(b[i].isEmpty())) 
					return false;
				
				if (!(a[i].isEmpty()) &&b[i].isEmpty()) 
					return false;
					
			}
				
		}
		
		return true;
	}

	// הפעולה מקבלת עצם מסוג נונוגרם ובודקת האם הרצפים בשורות ובטורים זהים לרצפים בשורות והטורים של העצם הנוכחי
   public boolean compare(nonogram other) 
   {
	   LinkedList<Integer>[] rowsOther=other.Count_Blacks_Rows();
	   LinkedList<Integer>[] colsOther=other.Count_Blacks_Cols();
	   
	   return ((isIdentical(rowsOther,Count_Blacks_Rows()))
			   &&(isIdentical(colsOther,this.Count_Blacks_Cols())));

   }

}


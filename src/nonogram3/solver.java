package nonogram3;


import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class solver 
{
	
	static int turns = 0;
	// גודל המטריצה
	int size;
	// עצם המייצג מטריצה שעליה ניישם את הפתרון
	nonogram nono;
	// דרישות החידה בטורים
	LinkedList<Integer>[] colsRequirements;
	// רדישות החידה בשורות
	LinkedList<Integer>[] rowsRequirements;
	JButton[][] buttons;
	
	// פעולה בונה 
	public solver(nonogram Nonogram, JButton[][] buttons ,nonogram screen) 
	{
		this.colsRequirements=Nonogram.Count_Blacks_Cols();
		this.rowsRequirements=Nonogram.Count_Blacks_Rows();
	    this.nono=screen;	
	    this.size=Nonogram.getSize();
	    this.buttons=buttons;

	}
	
    //פעולה בונה
	public solver(LinkedList<Integer>[] colsRequirements,LinkedList<Integer>[] rowsRequirements,JButton[][] buttons,nonogram screen) 
	{
		this.colsRequirements=colsRequirements;
		this.rowsRequirements=rowsRequirements;
	    this.buttons=buttons;
	    this.nono=screen;	
	    this.size=screen.getSize();
	}
	
	// הפעולה מקבלת פתרון שורה ובודקת האם היא עונה על התנאים
	private boolean isValidRow(int row, int col) 
	{
		
		// ההרצפים המותרים בשורה
		LinkedList<Integer> rowSeq = rowsRequirements[row];
		// הרצפים של פתרון השורה הנוכחי
		LinkedList<Integer> rowCnt = nono.Count_Blacks_Rows()[row];

		if (col==size-1) 
		{
			if (rowCnt.size()!=rowSeq.size())
				return false;
			
				for (int i=0; i<rowSeq.size();i++) 
		    {
					if (rowSeq.get(i)!=rowCnt.get(i))
						return false;
			} 
		}
		
		else {
			// אם יש יותר רצפים בפתרון מהרצפים המותרים
			if (rowCnt.size()>rowSeq.size())
				return false;
	
				for (int i=0; i<rowCnt.size();i++) 
				{
					// אם הרצף הנוכחי גדול מהרצף המותר
					 if (rowSeq.get(i)<rowCnt.get(i))
							return false;
				}
		}
		
		
		return true;
		
		
		// if there are more sequences from the rowSeqCnt length, return false
		// if there is a sequence which its length higher than all the sequences lengths	
	}
	
	// הפעולה מקבלת פתרון טור ובודקת האם הוא עונה על התנאים
		private boolean isValidCol(int row, int col) 
		{
			// ההרצפים המותרים בשורה
			LinkedList<Integer> colSeq = colsRequirements[col];
			// הרצפים של פתרון השורה הנוכחי
			LinkedList<Integer> colCnt = nono.Count_Blacks_Cols()[col];
			
			if (row==size-1) 
			{
				if (colCnt.size()!=colSeq.size())
					return false;
				
					for (int i=0; i<colSeq.size();i++) 
			    {
						if (colSeq.get(i)!=colCnt.get(i))
							return false;
				} 
			}
			else {
			
				if (colCnt.size()>colSeq.size())
					return false;
				
				for (int i=0; i<colCnt.size();i++) 
				{
					// אם הרצף הנוכחי גדול מהרצף המותר
					 if (colSeq.get(i)<colCnt.get(i))
						return false;
					
				
				}
				
		}
			return true;
		}
		
	// הפעולה מקבלת מיקום של משבצת ובודקת האם הערך שהושם במשבצת מתאים לדרישות השורה והטור של המשבצת
	public boolean valid(int i, int j) 
	{
		return isValidRow(i,j) && isValidCol(i,j);
	}
	
	//  הפעולה מקבלת משבצת ובודקת האם ההצבה מתאימה לדרישות החידה
	public boolean solve(int i, int j) 
	{
		int nextI;
		int nextJ;

		System.out.println(++turns);
		if (i==size)
			return true;
		
		if (j<size-1) 
		{
			nextI=i;
			nextJ=j+1;
		}
		else
		{
			nextI=i+1;
			nextJ=0;
		}
		
		//תחילה ננסה להשחיר את המשבצת
		nono.setValue(i, j, 1);
		animation(i,j);
		if (valid(i,j)&& solve(nextI,nextJ))
		{
		return true;
		}
		
		// ננסה להפוך את המשבצת ללבנה
		nono.setValue(i, j, 0);
		animation(i,j);
		if (valid(i,j)&& solve(nextI,nextJ)) {
		return true;
		}
        // אם הפתרון עדיין לא נכון אז דרך פתרון זאת אינה נכונה
		return false;
		
		
	}
	
	// הפעולה מחזירה את המטריצה שעליה יישמנו את הפתרון
	public nonogram getNonogram() 
	{
		return nono;
	}
	
	// הפעולה מייצרת אנימציה של המסך המייצגת את הפתרון
	public void animation(int i, int j) 
	{
		if (nono.getValue(i, j)==1) 
		{
			buttons[i][j].setBackground(Color.BLACK);
			buttons[i][j].setOpaque(true);
			Border border = new LineBorder(Color.BLACK, 1);
			buttons[i][j].setBorder(border);	       
		}
			
		else
		{
			buttons[i][j].setBackground(Color.WHITE);
		}
		
		
		

}
}

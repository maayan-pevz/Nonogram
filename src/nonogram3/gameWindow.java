package nonogram3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;







//--------------מחלקה היוצרת את מסך המשחק----------------------
public  class gameWindow extends JFrame  implements ActionListener,ItemListener
{
	//גודל המטריצה
	private int size;;
	// מי משחק-1=מחשב מגריל בעצמו את החידה,0=אדם,2=מחשב שמכניסים לו את החידה
	private int player;
	// מערך שמייצג את הכפתורים
	private JButton buttons[][];
	// מערך המייצג את הצבע של כפתור:1=שחור,0=לבן
	private nonogram screen;
	private nonogram Nonogram;
	//הצבע של המשבצות הצבעוניות במטריצה
	Color color;
	
	int maxSequence;
	 JTextField rows[][];
	 JTextField cols[][];
	
	//---------------------כל הכפתורים בחלון---------------
	// נותן רמז
	private  JButton clear_btn;
	 // אם השחקן הוא 0 אז בודק אם הפתרון נכון
	private  JButton check_btn;
	//  אם השחקן הוא 2 אז הוא פותר את החידה
	private  JButton solve_btn;
	
	////---------------------כל התיבות סימון בחלון---------
	//השחקן 
	JRadioButton  player_checkbox,comp_checkbox,comp_checkbox2;
	//גודל המסך
	JRadioButton size12,size10,size8;

	
	
	////--------------------הסיפרייה----------------------
	JMenuBar mb ;
    JMenu fileMenu;
    // מאתחל את המשחק
	JMenuItem reset;
	// סוגר את החלון
	JMenuItem exit;
	//מראה את החוקים
	JMenuItem rules;
	
	//משנה את הצבע של המשבצות הצבעוניות במטריצה
	JMenu coloreMenu;
	JMenuItem black,blue,red;

	 
	// הפעולה הבונה
	public gameWindow(int size, int player) 
	{
		setResizable(false);
		setLayout(null);
		
		// ברירת המחדל של הצבע היא שחור
		this.color=Color.BLACK;
		this.size=size;
		this.player=player;
		
		// המטריצה שמופיעה על המשחק-המטריצה של השחקן
		screen=new nonogram(size);
		//המטריצה שהמחשב יוצר-החידה
		Nonogram=new nonogram(size);
		if(player!=2)
		Nonogram.createColoredBoard();
		// מתאר את הרצפים בשורות
		LinkedList<Integer>[] nonogramSequenceRows=Nonogram.Count_Blacks_Rows();
		//מתאר את הרצפים בטורים
		LinkedList<Integer>[] nonogramSequenceCols=Nonogram.Count_Blacks_Cols();
		// מספר הרצפים הגדול ביותר
		
		if(player!=2)
	    maxSequence=Nonogram.maxRetzefAll();
		else
			maxSequence=size/2;
		
		int buttonSize=30;
		
		// גודל מסך המשחק
		setSize((maxSequence+size)*buttonSize+350+200,(maxSequence+size)*buttonSize+200);
		setBackground(Color.WHITE);
		
		
		// -------------הגדרת פנל משבצות המשחק------------
		JPanel gridPanelMain=new JPanel();
		gridPanelMain.setBounds(maxSequence*buttonSize+10, maxSequence*buttonSize+10, size*buttonSize, size*buttonSize);
		gridPanelMain.setLayout(new GridLayout(size,size));
		buttons=new JButton [size][size];
		
		for (int row = 0; row < size; row++) 
		{
            for (int col = 0; col < size; col++) 
            {
            	buttons[row][col]=new JButton();
            	buttons[row][col].setPreferredSize(new Dimension(50,50));
            	Border border = new LineBorder(Color.BLACK, 1);
            	buttons[row][col].setBorder(border);
            	buttons[row][col].setBackground(Color.WHITE);
            	
            	if (this.player==0) 
            	{
            	buttons[row][col].addActionListener(this);
            	screen.setValue(row,col,0);
            	}
            	gridPanelMain.add(buttons[row][col]);
            		
            	}
		
		}
		add (gridPanelMain);
		
	    //-------כותרת המשחק-------------------
		
		JPanel header=new JPanel();
		header.setBounds((maxSequence+size)*buttonSize+50, 20, 250+200, 150);
		ImageIcon icon1=new ImageIcon("src/pics/nonogram_1_450x100.png");
		JLabel label=new JLabel("");
		label.setIcon(icon1);
		header.add(label);
		add(header);
		
		
		
		// -------------הגדרת פנל אפשרויות חלון המשחק------------
		JPanel optionsPanel=new JPanel();
		
		optionsPanel.setBounds(((maxSequence+size)*buttonSize+50), maxSequence*buttonSize+10,250+200,size*buttonSize);
		optionsPanel.setLayout(new FlowLayout());
	    
	    JPanel spacer1=new JPanel();
		spacer1.setPreferredSize(new Dimension(240,50));
	    optionsPanel.add( spacer1);

	    // ------פנל שחקנים---
	    JPanel playerPanel=new JPanel();
	    JLabel playerLabel=new JLabel("player:");
	    
	    player_checkbox=new JRadioButton("player");
		comp_checkbox=new JRadioButton("computer");
		comp_checkbox2=new JRadioButton("solve");


	    if (player==0) 
	    {
	    	player_checkbox.setSelected(true);
	    	comp_checkbox.addItemListener(this);
	    	comp_checkbox2.addItemListener(this);
	    }
	    
	    if (player==1) 
	    {
	    	comp_checkbox.setSelected(true);
	    	player_checkbox.addItemListener(this);
	    	comp_checkbox2.addItemListener(this);
	    }
	    
	    if(player==2) {
	    	comp_checkbox2.setSelected(true);
	    	player_checkbox.addItemListener(this);
	    	comp_checkbox.addItemListener(this);

	    }
	

		ButtonGroup bgPlayers=new ButtonGroup();
		bgPlayers.add(player_checkbox);
		bgPlayers.add(comp_checkbox);
		
		playerPanel.add(playerLabel);
		playerPanel.add( player_checkbox);
		playerPanel.add(comp_checkbox);
		playerPanel.add(comp_checkbox2);


		optionsPanel.add(playerPanel);
		 
		 
		 JPanel spacer2=new JPanel();
		 spacer2.setPreferredSize(new Dimension(240,50));
		 optionsPanel.add( spacer2);
		
		 // ------פנל גדלים---
		 JPanel sizePanel=new JPanel();
		 JLabel sizeLabel=new JLabel("size:");

		 size8=new JRadioButton("8*8");
		 size10=new JRadioButton("10*10");
		 size12=new JRadioButton("12*12");

		 
		      if (size==8) {
			  size8.setSelected(true);
			  size10.addItemListener(this);
			  size12.addItemListener(this);
		      }
		      else if (size==10)
		      {
			  size10.setSelected(true);
			  size8.addItemListener(this);
			  size12.addItemListener(this);
		      }
		      else if(size==12)
		      {
			  size12.setSelected(true);
			  size8.addItemListener(this);
			  size10.addItemListener(this);
		      }
		 
		 
		 ButtonGroup bgSizes=new ButtonGroup();
		 bgSizes.add(size8);
		 bgSizes.add(size10);
		 bgSizes.add(size12);
	    
		 sizePanel.add(sizeLabel);
		 sizePanel.add(size8);
		 sizePanel.add(size10);
		 sizePanel.add(size12);

		 optionsPanel.add(sizePanel);
         
		 add(optionsPanel);
		 
		 //-------פנל הרצפים בשורות------------
		 JPanel gridPaneRows=new JPanel();
		 gridPaneRows.setBounds(0, maxSequence*buttonSize+10,  maxSequence*buttonSize, size*buttonSize);
		 gridPaneRows.setLayout(new GridLayout(size,maxSequence));
		  rows=new JTextField [size][maxSequence];
			
			for(int row=0; row< size; row++)
			{
				for(int col=0; col < maxSequence; col++	) 
				{
					rows[row][col]=new JTextField();
					rows[row][col].setPreferredSize(new Dimension(50,50));
					Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
					rows[row][col].setBorder(border);
					if(player!=2)
					rows[row][col].setEditable(false);
					rows[row][col].setBackground(Color.LIGHT_GRAY);
					gridPaneRows.add(rows[row][col]);
				}
					
						
			}
			
			// הדפסה על שורות 
			for (int r=0; r<size; r++) 
			{
				if(!(nonogramSequenceRows[r].isEmpty())) 
				{
					int col=maxSequence-1;
					
					for(int c = nonogramSequenceRows[r].size()-1; c >=0;c--)
					{
						int a=nonogramSequenceRows[r].get(c);
						rows[r][col].setText(String.valueOf(a));
						rows[r][col].setHorizontalAlignment(JTextField.CENTER);
						col=col-1;
					}
				}
				
			}
			

		
			//פנל הרצפים בטורים
			JPanel gridPaneCols = new JPanel();
			gridPaneCols.setBackground(Color.LIGHT_GRAY );
			gridPaneCols.setBounds(maxSequence*buttonSize+10,0 , size*buttonSize,maxSequence*buttonSize);
			gridPaneCols.setLayout(new GridLayout(maxSequence,size));
			 cols= new JTextField[maxSequence][size];
			for(int row =0; row < maxSequence; row++)
			{
				for(int col =0; col < size; col++) 
				{
					cols[row][col] = new JTextField();
					cols[row][col].setPreferredSize(new Dimension(50,50));
					Border border = new LineBorder(color.BLACK, 1);
					cols[row][col].setBorder(border);
					cols[row][col].setBackground(Color.LIGHT_GRAY );
					if(player!=2)
					cols[row][col].setEditable(false);
					gridPaneCols.add(cols[row][col]);
				}
			}
			
			//הדפסת מספרים בטורים
			for(int c=0; c<size; c++) 
			{
				if(!(nonogramSequenceCols[c].isEmpty()))
				{
					int row=maxSequence-1;
									
					for(int r = nonogramSequenceCols[c].size()-1; r >=0;r--) 
					{
						int a= nonogramSequenceCols[c].get(r);
						cols[row][c].setText(String.valueOf(a));
						cols[row][c].setHorizontalAlignment(JTextField.CENTER);
						row=row-1;
		
					}
				}
			}
		
		
			
			add(gridPaneRows);
			add(gridPaneCols);
		
		 // אם השחקן הוא אדם
		 if (player==0) 
		 {
			 clear_btn=new JButton("clear");
			 check_btn=new JButton("check");
			 check_btn.addActionListener(this);
			 clear_btn.addActionListener(this);
			 
			 JPanel southPanel=new JPanel();
			 southPanel.add(clear_btn );
			 southPanel.add(check_btn );
			 southPanel.setBounds(maxSequence*buttonSize, (size+maxSequence)*buttonSize+60, size*buttonSize, 60);
			 add(southPanel);
		 }
		 
		 if(player==2) 
		 { 
			 solve_btn=new JButton("solve");
			 solve_btn.addActionListener(this);
			 
			 JPanel southPanel=new JPanel();
			 southPanel.add(solve_btn );
			 southPanel.setBounds(maxSequence*buttonSize, (size+maxSequence)*buttonSize+60, size*buttonSize, 60);
			 add(southPanel);
			 
		 }
		 
		 //----------הגדרת הסיפרייה------
		 mb = new JMenuBar();
		 fileMenu= new JMenu ("file");
		 
		 reset=new JMenuItem ("reset");
		 reset.addActionListener(this);
		 exit=new JMenuItem ("exit");
		 exit.addActionListener(this);
		 rules=new JMenuItem ("rules");
		 rules.addActionListener(this);

		
		fileMenu.add(reset);
		fileMenu.add(exit);
		fileMenu.add(rules);
		
		coloreMenu= new JMenu ("colors");
		black=new JMenuItem ("black");
		black.addActionListener(this);
		blue=new JMenuItem ("blue");
		blue.addActionListener(this);
		red=new JMenuItem ("red"); 
		red.addActionListener(this);

		 
		 coloreMenu.add(black);
		 coloreMenu.add(blue);
		 coloreMenu.add(red);
		 
		 
		mb.add(fileMenu);
		mb.add(coloreMenu);

		setJMenuBar(mb);
		 


		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		 
		
		if (this.player==1)
			compSolve() ;
	}
	

	// פתרון המטריצה ע״י המחשב
	public void compSolve() 
	{
		solver sol=new solver(Nonogram,buttons,screen);
		sol.solve(0, 0);
		System.out.print(Nonogram.compare(sol.getNonogram()));

	}
		
	
	//משנה את ערך המשבצת ע״פ לחיצה
	public void Change__value(JButton bp)
	 {
			
			for (int row = 0; row < size; row++) 
			{
	            for (int col = 0; col < size; col++) 
	            {
	            	if (bp.equals(buttons[row][col]))
	            	{
	            		if (screen.getValue(row,col)==0)
	            		{
	            			screen.setValue(row, col, 1); 
	            			buttons[row][col].setBackground(color);
	            			buttons[row][col].setOpaque(true);
	            			Border border = new LineBorder(Color.BLACK, 1);
	            			buttons[row][col].setBorder(border);	            			

	            		}
	            		else
	            		{
	            			screen.setValue(row, col, 0); 
	            			buttons[row][col].setBackground(Color.WHITE);
	            		}
	            	}
	              }
	            }
	
	 }
	
	// משנה את צבע המשבצות ה״צבועות״
	public void Change_color(Object object)
	{
		
		boolean change_color=false;
		boolean clear=false;


		if (object.equals(red))  
		{
			System.out.print("red");
			color=Color.RED;
			change_color=true;
		}
		
		else if (object.equals(blue)) 
		{
			System.out.print("blue");
			color=Color.BLUE;
			change_color=true;

		}
		
		else if (object.equals(black)) 
		{
			System.out.print("black");
			color=Color.BLACK;
			change_color=true;
		}
		
	
		
		if(change_color==true)
		{
			 change_color=false;

			for (int row = 0; row < size; row++) 
			{
	            for (int col = 0; col < size; col++) 
	            {
	            	if(screen.getValue(row, col)==1) 
	            		buttons[row][col].setBackground(color);
	            		
	            	
	            		
	            }
			}
			
		}
		
		
	}
	// ״מנקה את המטריצה״ ומאפס את כל הערכים
	public void clearMatrix() 
	{
			for (int row = 0; row < size; row++) 
			{
	            for (int col = 0; col < size; col++) 
	            {
	            	if(screen.getValue(row, col)==1) {
	            		buttons[row][col].setBackground(new Color (238,238,238));
	            		screen.setValue(row,col,0);
	            	}
	            		
	            }
			}
	}
			
		
	//  הפעולה מקבלת רשימה של חוקים לשורות/טורים ובודקת האם הם מתאימים לגודל המטריצה
   public boolean verify(LinkedList<Integer>[] list) 
	{
	  
       boolean flag=true;
	   for (int i=0; i<list.length;i++)
	   {
		    int sum=0;
		    
		    if(!(list[i].isEmpty())) 
		    {
		   for(int j=0;j<list[i].size();j++)
		   {
			   sum+=list[i].get(j);
		   }
		   
		  sum+=list[i].size()-1;
		  
		  if(sum>size) 
		  {
			  flag=false;
			

		  }
	   }
	   }
	   
	   return flag;			
	}
   

	
	@Override
	public void actionPerformed(ActionEvent e)
	{

		
		//-----שינוי צבע המשבצות הצבועות-------
		 if (e.getSource()==red || e.getSource()==blue ||  e.getSource()==black )
			Change_color(e.getSource());
		 
		 else if( e.getSource()==clear_btn) 
		 {
			 clearMatrix() ;
		 }
		
		//מאתחל את המשחק
		else if(e.getSource()==reset) 
		{
			dispose();
			new gameWindow (size,player);
		}
		
		//סוגר את המשחק
		else if(e.getSource()==exit) 
		{
			dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
		}
		 
		 //פותח את חוקי המשחק
		else if(e.getSource()==rules) 
		{
			JFrame message= new JFrame();
			String str;
			ImageIcon icon;
			icon=new ImageIcon("src/pics/nonogram_rules.png");
			
				
			JLabel text=new JLabel("");
			text.setIcon(icon);
			message.add(text);
			message.pack();
			message.setVisible(true);
			
		}
	
		 
		else if(e.getSource()==solve_btn) 
		{
			//בעיה בהגדרה
			LinkedList<Integer>[] sequence_cols = new LinkedList[size];
			for (int c=0; c<size; c++)
			{	
				LinkedList <Integer> list=new LinkedList <Integer>();
					for(int r =0; r <maxSequence;r++)
					{
						if(!(cols[r][c].getText().isEmpty()))
						{
						int x=Integer.parseInt(cols[r][c].getText());
						System.out.print(x + " ");
						list.add(x);
						}
						
					}
					System.out.println();
					sequence_cols[c]=list;

				}
			
			
			LinkedList<Integer>[] sequence_rows = new LinkedList[size];
			
			for (int r=0; r<size; r++) 
			{	
				LinkedList <Integer> list=new LinkedList <Integer>();

					for(int c =0; c <maxSequence;c++)
					{
						if(!(rows[r][c].getText().isEmpty())) {
						int x=Integer.parseInt(rows[r][c].getText());
						System.out.println(x + " ");

					     list.add(x);
						} 
					}
					System.out.println();
					sequence_rows[r]=list;
				}
			
		 Boolean colsValid=verify(sequence_cols);
		 Boolean rowsValid=verify(sequence_rows);
		 
		 if(colsValid&&rowsValid) 
		 {
				
			 solver sol=new solver(sequence_cols, sequence_rows,buttons,screen);
			 sol.solve(0, 0);
			 
		 }
		 else
				System.out.println("not valid");

			 

			 
			
			
			
		}
		
		//בודק אם הפתרון של השחקן הוא נכון
		else if(e.getSource()==check_btn) 
		{

			boolean check=(Nonogram.compare(screen));
			System.out.println(check);
			JFrame message= new JFrame();
			String str;
			ImageIcon icon;
			if(check==true)
				icon=new ImageIcon("src/pics/welldone_300x200.jpeg");
			else
				icon=new ImageIcon("src/pics/tryagain_300x300.png");
				
			JLabel text=new JLabel("");
			text.setIcon(icon);
			message.add(text);
			message.pack();
			message.setVisible(true);
		}

		else 
		{
			if(player==0) 
			{
				JButton bp=(JButton)e.getSource();
				Change__value(bp);
			}
		}
	
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		//----שינוי גודל המסך-----
		 if (e.getSource() == size8) {
		
			dispose();
		   new gameWindow(8,player);

		}
		
		 if (e.getSource() == size10) {
				
				dispose();
			   new gameWindow(10,player);

			}
		 
		 if (e.getSource() == size12) {
				
				dispose();
			   new gameWindow(12,player);

			}
		 
			//-----שינוי השחקן------
			if(e.getSource()==comp_checkbox) 
			{
				dispose();
			    new gameWindow(size,1);
		
			}
			
			if(e.getSource()==comp_checkbox2) 
			{
				dispose();
			    new gameWindow(size,2);
		
			}
			
			 if(e.getSource()==player_checkbox) 
			{
			   dispose();
			   new gameWindow(size,0);
				

			}
	
	}
   
}

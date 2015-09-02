package cw_1201592;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;


import java.awt.*;              //for layout managers and more
import java.awt.event.*;        //for action events

import java.util.ArrayList;
import java.util.Collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Creates a GUI to implement searching of the dataset.
 * 
 * This class provides an interface of several operations that include:
 * <ul>
 * 	<li>counting the total number of articles in the dataset;</li>
 * 	<li>counting the total number of unique tags in the dataset;</li>
 * 	<li>counting the number of tags used to annotate a particular article;</li>
 * 	<li>displaying all the tags and their count for one particular article;</li>
 * 	<li>counting the number of times two tags co-occur in the dataset;</li>
 * 	<li>given a tag, displaying all the bookmarks that are annotated with that tag, ordered by the tag count;</li>
 *  <li>displaying the content of the original article and tag count by clicking on a bookmark;</li>
 * 	<li>given a tag, displaying up to 10 most frequently co-occurring </li>
 * </ul>
 * Action listeners are implemented by inner classes <code>taskOneListener</code>, <code>taskTwoListener</code>,
 *  <code>taskThreeListener</code>, <code>taskFiveListener</code>, <code>taskOSixListener</code>, and
 *  <code>taskSevenListener</code>. 
 * @author Boqing Liu
 *
 */
public class Sgui extends JPanel{
	/**
	 * A <code>SAXDataExtraction</code> object.
	 */
    private SAXDataExtraction dataExtract = new SAXDataExtraction();
    
    /**
     * A text field to display the number of articles in the dataset.
     */
    private JTextField textFieldNoArticle;
    /**
     * A text field to display the number of unique tags in the dataset.
     */
    private JTextField textFieldNoTag;
    /**
     * A text field to accept user input for MD5 hash value for article's url.
     */
    private JTextField textFieldHash;
    /**
     * A text field to accept user input for a tag name for counting co-occurrence times.
     */
    private JTextField textFieldTag1;
    /**
     * A text field to accept user input for a tag name for counting co-occurrence times.
     */
    private JTextField textFieldTag2;
    /**
     * A text field to display the co-occuring times of two tags.
     */
    private JTextField textFieldCoOccur; 
    /**
     * A text field to accept user input for a tag name for searching all of its articles
     */
    private JTextField textFieldTagName;
    /**
     * A button for triggering co-occurrence times counting. 
     */
    private JButton coOccurButton;
    /**
     * Display the HTML page.
     */
    private JEditorPane editorPaneDispH;//display the HTML page when click the link
    /**
     * Public used display pane.
     */
    private JEditorPane editorPaneDispB;//display book link + tagCount
    /**
     * Display ten most frequently co-occurring tags.
     */
    private static JTextArea textAreaDispTenTags;
    /**
     * Display tag and tag count by clicking an article.
     */
    private JTextPane textPaneDispTagAndCount;
    /**
     * A document that models HTML.
     */
    private HTMLDocument docs;//for editorPaneDispB
    /**
     * A document for text display.
     */
    private StyledDocument docForTextPane;
    /**
     * Attribute set for document display.
     */
    private SimpleAttributeSet attributeSet = new SimpleAttributeSet();
    
    /**
     * Constructs a GUI.
     * The interface includes labels, corresponding text fields to display 
     * or for users to input keywords for searching, editor field, text field
     * and text area to display searching results.
     * 
     */
    public Sgui() {
    	//将JPanel的布局设为borderlayout
        setLayout(new BorderLayout());

        //Create text field for task 1 (#articles).
        textFieldNoArticle = new JTextField("Enter to display",20);
        
        textFieldNoArticle.addActionListener(new taskOneListener());
        //click then text disappear
        textFieldNoArticle.addFocusListener(new FocusListener(){
        	   public void focusGained(FocusEvent e) {
        		   if("Enter to display".equalsIgnoreCase(textFieldNoArticle.getText())){
        		        textFieldNoArticle.setText("");
        		    }
        	   }
        	   public void focusLost(FocusEvent e) {
        		   if("".equals(textFieldNoArticle.getText().trim())){
        			   textFieldNoArticle.setText("Enter to display");
        		    }
        	   }
        });
        //Create label for noArticle field 
        JLabel noArticleLabel = new JLabel("Number of Articles: ");
        noArticleLabel.setLabelFor(textFieldNoArticle);
        
        //Create text field for task 2 (#unqiue tags).
        textFieldNoTag = new JTextField("Enter to display",20);
        textFieldNoTag.addActionListener(new taskTwoListener());
        //click then text disappear
        textFieldNoTag.addFocusListener(new FocusListener(){
        	   public void focusGained(FocusEvent e) {
        		   if("Enter to display".equalsIgnoreCase(textFieldNoTag.getText())){
        			   textFieldNoTag.setText("");
        		    }
        	   }
        	   public void focusLost(FocusEvent e) {
        		   if("".equals(textFieldNoTag.getText().trim())){
        			   textFieldNoTag.setText("Enter to display");
        		    }
        	   }
        });
        //Create label for noArticle field 
        JLabel noTagLabel = new JLabel("Number of Unique Tags: ");
        noArticleLabel.setLabelFor(textFieldNoTag);
        
        //Create text field for task 3,4 input: hash, output: #tags+tags+count.
        textFieldHash = new JTextField("Input then enter to search its tags(number and name) and count",40);
        textFieldHash.addActionListener(new taskThreeListener());
        //click then text disappear
        textFieldHash.addFocusListener(new FocusListener(){
        	   public void focusGained(FocusEvent e) {
        		   if("Input then enter to search its tags(number and name) and count".equalsIgnoreCase(textFieldHash.getText())){
        			   textFieldHash.setText("");
        		    }
        	   }
        	   public void focusLost(FocusEvent e) {
        		   if("".equals(textFieldHash.getText().trim())){
        			   textFieldHash.setText("Input then enter to search its tags(number and name) and count");
        		    }
        	   }
        });
        //Create label for noArticle field 
        JLabel hashLabel = new JLabel("MD5 Hash Value: ");
        hashLabel.setLabelFor(textFieldHash);
        
        //Create text field for task 5 input: two tag, output: #co-occur.
        textFieldTag1 = new JTextField(10);
        textFieldTag2 = new JTextField(10);
        textFieldTag2.addActionListener(new taskFiveListener());
        textFieldCoOccur = new JTextField(10);
        //Create label for noArticle field 
        JLabel tag1Label = new JLabel("tag1: ");
        tag1Label.setLabelFor(textFieldTag1);
        JLabel tag2Label = new JLabel("tag2: ");
        tag2Label.setLabelFor(textFieldTag2);
        JLabel coOccurLabel = new JLabel("Co-occur Times: ");
        coOccurLabel.setLabelFor(textFieldCoOccur);
        coOccurButton = new JButton("Count Co-occur");
        coOccurButton.addActionListener(new taskFiveListener());
        
        //Create text field for task 6 (#articles).
        textFieldTagName = new JTextField("Input then enter to search articles",20);
        textFieldTagName.addActionListener(new taskSixListener());
        textFieldTagName.addFocusListener(new FocusListener(){
     	   public void focusGained(FocusEvent e) {
     		   if("Input then enter to search articles".equalsIgnoreCase(textFieldTagName.getText())){
     			  textFieldTagName.setText("");
     		    }
     	   }
     	   public void focusLost(FocusEvent e) {
     		   if("".equals(textFieldTagName.getText().trim())){
     			  textFieldTagName.setText("Input then enter to search articles");
     		    }
     	   }
     });
        //Create label for tag name field 
        JLabel tagNameLabel = new JLabel("Tag name: ");
        tagNameLabel.setLabelFor(textFieldNoArticle);
        
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        
        textControlsPane.setLayout(gridbag);
        JLabel[] labels = {noArticleLabel, noTagLabel, hashLabel, tagNameLabel};
        JTextField[] textFields = {textFieldNoArticle, textFieldNoTag, textFieldHash, textFieldTagName};
        addLabelTextRows(labels, textFields, gridbag, textControlsPane);
        
        //tag1Label,tag2Label,coOccurLabel//textFieldTag1,textFieldTag2,textFieldCoOccur
        c.anchor = GridBagConstraints.EAST;
        c.weightx = 1.0;
        c.gridwidth = 2;
        //TAG1
        //set constrains for label
        c.fill = GridBagConstraints.NONE;      //reset to default
        textControlsPane.add(tag1Label, c);
        //set constrains for text field
        c.fill = GridBagConstraints.HORIZONTAL;
        textControlsPane.add(textFieldTag1, c);
        //TAG2
        //set constrains for label
        c.fill = GridBagConstraints.NONE;      //reset to default
        textControlsPane.add(tag2Label, c);
        //set constrains for text field
        c.fill = GridBagConstraints.HORIZONTAL;
        textControlsPane.add(textFieldTag2, c);
        //Button
        c.gridwidth = GridBagConstraints.REMAINDER;//last
        textControlsPane.add(coOccurButton, c);
        c.gridwidth = 2;//GridBagConstraints.RELATIVE; //next-to-last
        c.fill = GridBagConstraints.NONE;      //reset to default
        c.weightx = 0.0;                       //reset to default
        textControlsPane.add(coOccurLabel,c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        
        c.gridwidth = GridBagConstraints.REMAINDER;     //end row
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        textControlsPane.add(textFieldCoOccur,c);
        textControlsPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(""),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        //Create a jeditorpane to display #tags+tags+count/book(hyperlink)+tagCount
        //task 3&4&6&7
        editorPaneDispB = new JEditorPane();
        editorPaneDispB.setEditable(false);
        editorPaneDispB.addHyperlinkListener(new taskSevenListener());
        editorPaneDispB.setContentType("text/html");  
        docs = (HTMLDocument)editorPaneDispB.getDocument();
        editorPaneDispB.setDocument(docs);        
        StyleConstants.setFontSize(attributeSet,20);
        SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,20);
        //scroll
        JScrollPane editorBScrollPane = new JScrollPane(editorPaneDispB);
        editorBScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorBScrollPane.setPreferredSize(new Dimension(250, 250));
        editorBScrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Public Display Area"),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        editorBScrollPane.getBorder()));
        
        //Creat a text area to display the 10 suggested tags
        textAreaDispTenTags = new JTextArea();
        textAreaDispTenTags.setFont(new Font("Serif", Font.ITALIC, 16));
        textAreaDispTenTags.setLineWrap(true);
        textAreaDispTenTags.setWrapStyleWord(true);
        //scroll
        JScrollPane areaScrollPane = new JScrollPane(textAreaDispTenTags);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 250));
        areaScrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Suggest Ten Tags"),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        areaScrollPane.getBorder()));
        
        //creat a split pane for editorB and text area
        JSplitPane splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        		editorBScrollPane,areaScrollPane
                );
        splitPaneLeft.setOneTouchExpandable(true);
        splitPaneLeft.setResizeWeight(0.5);

        //Create a editor pane for HTML page.
        editorPaneDispH = new JEditorPane();
        editorPaneDispH.setText("It takes time to display the HTML page and it does display.\nPlease be patient *V*");
        JScrollPane paneHScrollPane = new JScrollPane(editorPaneDispH);
        paneHScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        paneHScrollPane.setPreferredSize(new Dimension(400, 400));
        paneHScrollPane.setMinimumSize(new Dimension(10, 10));
        paneHScrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Display HTML Page of A Bookmark"),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                                paneHScrollPane.getBorder()));
        //Create an editor pane for tags+count (part of task 7)
        textPaneDispTagAndCount = new JTextPane();
        docForTextPane = textPaneDispTagAndCount.getStyledDocument();
        textPaneDispTagAndCount.setDocument(docForTextPane);
        //scroll
        JScrollPane textScrollPane = new JScrollPane(textPaneDispTagAndCount);
        textScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textScrollPane.setPreferredSize(new Dimension(400, 400));
        textScrollPane.setMinimumSize(new Dimension(10, 10));
        textScrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Display Tags and Count of A Bookmark"),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                                textScrollPane.getBorder()));
        
        //Put the editor pane and the text pane in a split pane.
        JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                paneHScrollPane,textScrollPane
                );
        splitPaneRight.setOneTouchExpandable(true);
        splitPaneRight.setResizeWeight(0.5);
        JPanel rightPane = new JPanel(new GridLayout(1, 0));//row-1, column-0
        rightPane.add(splitPaneRight);
   
        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(textControlsPane,
                BorderLayout.PAGE_START);
        leftPane.add(splitPaneLeft,
                BorderLayout.CENTER);
        
        add(leftPane, BorderLayout.LINE_START);
        add(rightPane, BorderLayout.LINE_END);

    }
    //end of constructor
    
    //how labels and textfields layout
    private void addLabelTextRows(JLabel[] labels,
            JTextField[] textFields,
            GridBagLayout gridbag,
            Container container) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        int numLabels = labels.length;

        for (int i = 0; i < numLabels; i++) {
        	//c.gridx = i; c.gridy = 0;
            c.gridwidth = 2;//GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 0.0;                       //reset to default
            container.add(labels[i], c);
            
            
            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            container.add(textFields[i], c);
        }
    }
    /**
     * An action listener class for the action to display number of articles.
     * @author Boqing Liu
     *
     */
    //inner classes
    //count no. of articles
    private class taskOneListener implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		textFieldNoArticle.setText("");
    		textFieldNoArticle.setText(""+dataExtract.countNoArticle());
    	}
    }
    /**
     * An action listener class for the action to display number of unique tags.
     * @author Boqing Liu
     *
     */
    //count no. of unique tags
    private class taskTwoListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		textFieldNoTag.setText("");
    		textFieldNoTag.setText(""+dataExtract.countNoTag());
    	}
    }
    /**
     * An action listener class for the action to display all tags and count for a particular article.
     * @author Boqing Liu
     *
     */
    //task 3 and task 4
    //count no. of tags and display all tags and count for a particular article
    private class taskThreeListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		editorPaneDispB.setText("");
    		//task3: display number of tags used to annotate a particular article
    		try{
    	
    			docs.insertString(docs.getLength(), "Number of tags used to annotate this article: "
    						+dataExtract.countTagsOfAnArticle(textFieldHash.getText().trim())+"\n",attributeSet);
    		}catch (BadLocationException exp) {
	            exp.printStackTrace();
	        }
    		
    		//task4: display all the tags and their count
    		 LinkedHashMap<String, Integer> nameCountPair = dataExtract.displayTagAndCount(textFieldHash.getText().trim());
    		 try {
    	          docs.insertString(docs.getLength(), "\nAll the tags and their count for this article: \n"+
    		 "Count"+"\t\t"+"Tag\n",attributeSet);
    	          
    	        } catch (BadLocationException exp) {
    	            exp.printStackTrace();
    	        }
    		 Iterator<Entry<String,Integer>> lit = nameCountPair.entrySet().iterator();  
    			while (lit.hasNext()) {  
    			Map.Entry<String, Integer> theEntry = (Map.Entry<String, Integer>) lit.next();  
	    			try{
	    				docs.insertString(docs.getLength(), theEntry.getValue()+"\t",attributeSet);
	    				docs.insertString(docs.getLength(), theEntry.getKey()+"\n",attributeSet);
	    			}catch (BadLocationException exp) {
			            exp.printStackTrace();
			        }
    			} 
    	}
    }
    /**
     * An action listener class for the action to display two tags' co-occuring times.
     * @author Boqing Liu
     *
     */
    //textFieldTag1, textFieldTag2
    //two tags co-occur times
    private class taskFiveListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		Tag tag1 = new Tag(textFieldTag1.getText().trim());
    		Tag tag2 = new Tag(textFieldTag2.getText().trim());
    		int coOccurCount = dataExtract.numOfTagCooccur(tag1,tag2);
    		textFieldCoOccur.setText(""+coOccurCount);
    	}
    }
    /**
     * An action listener class for the action to display book and tag count for a particular article.
     * @author Boqing Liu
     *
     */
    //inner class for task six. input: tag output: books+tag count
    private class taskSixListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {
        //clear the text area
        editorPaneDispB.setText("");
        String theStart = "<html><body>";
		String theEnd = "</body></html>";
		String begin = "Count&#9Tag";
		Tag tag = new Tag(textFieldTagName.getText().trim());
        TreeMap<Integer, ArrayList<String>> bookmarkCountPair = 
        		dataExtract.getCountAndBookmarkOfTag(tag);
        Set<Integer> tagCount = bookmarkCountPair.keySet();
        Iterator<Integer> allTagCount = tagCount.iterator();
        Collection<ArrayList<String>> bookName = bookmarkCountPair.values();
        Iterator<ArrayList<String>> allBookName = bookName.iterator();
        String strHTML = "";
        StringTokenizer st;
        String theBookName = "";
        String theBookHash = "";
        while(allTagCount.hasNext()){
        	int theTagCount = allTagCount.next();
        	
        	if(allBookName.hasNext()){
        		ArrayList<String> bookList = allBookName.next();
        		int noOfBook = bookList.size();
        		
        		for(int i = 0; i < noOfBook; i++){
        				//book name and hash code are stored together with '|' separated
        				st = new StringTokenizer(bookList.get(i),"|");
        				String[] arr = new String[2];
        				int k = 0;
        				 while(st.hasMoreTokens() ){
        			            arr[k] = st.nextToken();
        			            k++;
        			     }
        				 theBookName = arr[0];
        				 theBookHash = arr[1];

        				 //display tag count
        				strHTML += "<p>"+theTagCount+"&#9"; 
        				
        				//display hyper link of book name
        				strHTML += "<a href=http://previous.delicious.com/url/"+theBookHash+">"+theBookName+"</a></p>";

        		}
        		
        	}
        }

        //put all html code into jeditorpane
        editorPaneDispB.setText(theStart+begin+strHTML+theEnd);
        //Display ten suggested tags
        Map<Tag, Integer> tagCoOccurCountPair = dataExtract.suggestFrequentOccurTag(tag);
        Iterator<Entry<Tag, Integer>> it = tagCoOccurCountPair.entrySet().iterator(); 
        textAreaDispTenTags.setText("");
        textAreaDispTenTags.setText("Co-occur times"+"   "+"Tag\n");
        int i = 0;
        h:
        while(it.hasNext()){  
            Map.Entry<Tag, Integer> entry = (Map.Entry<Tag, Integer>) it.next();
            textAreaDispTenTags.append(entry.getValue()+"\t"+entry.getKey().getName()+"\n");
        }  
    	}
    }
    /**
     * An action listener class for the action to display HTML page, tags and tag count for an article, 
     * @author Boqing Liu
     *
     */
    //task7
    //clicking on a bookmark will display HTML page, tags&tag count
    private class taskSevenListener implements HyperlinkListener{
    	public void hyperlinkUpdate(HyperlinkEvent e){
    		String theURL = "";
    		 if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
    		//displayHTML page
    	     try {
    	    	 editorPaneDispH.setPage(e.getURL());
    	    	 theURL = e.getDescription();
    	     } catch (Exception ex) {
    	      ex.printStackTrace();
    	      System.err.println("connection error");
    	     }
    	     
    	     //display tags & count for a particular article
    	     //extract the hash code value from the url
    	    //http://previous.delicious.com/url/ =>total 34 character
    	     textPaneDispTagAndCount.setText("");
    		 LinkedHashMap<String, Integer> nameCountPair = dataExtract.displayTagAndCount(theURL.substring(34));
    		 try {
    			 docForTextPane.insertString(docForTextPane.getLength(), "\nAll the tags and their count for this article: \n"
    					 +"Count\tTag\n",attributeSet); 
    	        } catch (BadLocationException exp) {
    	            exp.printStackTrace();
    	        }
    		 //display the information stored in the map
    		 Iterator<Entry<String,Integer>> lit = nameCountPair.entrySet().iterator();  
    		 while (lit.hasNext()) {  
    			 Map.Entry<String, Integer> theEntry = (Map.Entry<String, Integer>) lit.next();  
 	    			try{
 	    				docForTextPane.insertString(docForTextPane.getLength(), theEntry.getValue()+"\t",attributeSet);
 	    				docForTextPane.insertString(docForTextPane.getLength(), theEntry.getKey()+"\n",attributeSet);
 	    			}catch (BadLocationException exp) {
 			            exp.printStackTrace();
 			        }
     		 } 
    			
    	     
    		 }
    		 
    	}

    }
    	

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Web Documents Searching");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new Sgui());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        textAreaDispTenTags.requestFocusInWindow();
    }

}
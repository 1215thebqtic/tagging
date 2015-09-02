
package cw_1201592;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Processes the XML data and provides searchings for Web documents.
 * 
 * Instances of this class store:
 * <ul>
 * 	<li>a vector of <code>Article</code> objects extracted from the XML file;</li>
 * 	<li>a hash table, whose key is a <code>Tag</code> object and value is a list of 
 * 		this <code>Tag</code> object's associated <code>Article</code> objects;</li>
 * 	<li>an <code>Article</code> object;</li>
 * 	<li>an <code>Annotation</code> object;</li>
 * 	<li>an <code>Tag</code> object and</li>
 * 	<li>a string used to represent the string between a pair of tags in the XML file.</li>
 * </ul>
 * There are operations to extract data from the XML file and populate these 
 * information objects, to calculate the total number of articles in dataset, to 
 * calculate the total number of unique tags in the dataset, to count the number
 * of tags used to annotate a particular article, to access all the tags and their
 * count for one particular article, to count the number of times two tags co-occur
 * in the dataset, to access all the bookmarks and tag count that are annotated 
 * with a given tag and suggest up to ten most frequently co-occuring tags with
 * regard to the current tag.
 *  
 * @author Boqing Liu
 */
public class SAXDataExtraction extends DefaultHandler {

    /**
     * Stores <code>Article</code> objects extracted from the dataset.
     */
    private static Vector<Article> articles = new Vector<Article>();
    /**
     * Stores pairs of a unique <code>Tag</code> objects and a list of 
     * this <code>Tag</code> object's associated <code>Article</code> objects.
     */
    private static Hashtable<Tag,ArrayList<Article>> tags = new Hashtable<Tag,ArrayList<Article>>();
    /**
     * An <code>Article</code> object
     */
    private Article article;
    /**
     * An <code>Annotation</code> object
     */
    private Annotation annotation;
    /**
     * An <code>Tag</code> object
     */
    private Tag tag;
    /**
     * A string used to represent the string between a pair of tags in the dataset
     */
    private String temp = "";

    
    /**
     * Receive notification of the beginning of the document.
     * Prints out the starting message.
     */
    public void startDocument() throws SAXException {
        System.out.println("Start processing \"wiki10+ tag-data.xml ...\"");
    }
    /**
     * Receive notification of the end of the document.
     * Prints out the ending message.
     */
    public void endDocument() throws SAXException {
        System.out.println("End processing \"wiki10+ tag-data.xml\"");
    }
    /**
     * Receive notification of the beginning of the document.
     * @param namespaceURI The Namespace URI, or the empty string if 
     * 		  the element has no Namespace URI or if Namespace 
     *        processing is not being performed.
     * @param localName The local name (without prefix), or the 
     * 		  empty string if Namespace processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty 
     * 		  string if qualified names are not available.
     * @param atts The attributes attached to the element. 
     * 		  If there are no attributes, it shall be an empty Attributes object.
     * @exception SAXException Any SAX exception, possibly wrapping another exception.
     * 
     * This method will construct an <code>Article</code> object, a <code>Tag</code> object and 
     * an <code>Annotation</code> object when it come across corresponding elements. 
     */
    public void startElement(String namespaceURI,
            String localName,
            String qName,
            Attributes atts)
            throws SAXException {
    	    //if it meets <article>£¬construct a new Article object
	        if (qName.equalsIgnoreCase("article")) {
	            article = new Article();
	        }
	       //if it meets <tag>£¬construct a new Tag object and a new Annotation object
	        if (qName.equalsIgnoreCase("tag")) {
	        	tag = new Tag();
	        	annotation = new Annotation();
	        }
	      
    	}
    /**
     * Receive notification of character data inside an element.
     * @param ch The characters between two tags
     * @param start The start position in the character array.
     * @param length The number of characters to use from the character array.
     * @exception SAXException Any SAX exception, possibly wrapping another exception.
     * 
     * This method will extract the characters between two adjacent tags in the XML file.
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        //e.g. <name>fonts</name>£¬temp = fonts
    	temp = new String(ch, start, length);
    }
    /**
     * Receive notification of the end of an element. 
     * @param uri The Namespace URI, or the empty string if the element 
     * 		  has no Namespace URI or if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty 
     * 		  string if Namespace processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string 
     * 		  if qualified names are not available.
     * @exception SAXException Any SAX exception, possibly wrapping another exception.
     * 
     * This method stores all information into objects or data structures and sorts the linked
     * list of annotations that is ordered by the tag count.
     *
     */
    public void endElement(String uri, String localName,
            String qName) throws SAXException {
    	//if it meets </article>£¬add the article object into the vector
        if (qName.equalsIgnoreCase("Article")) {
            articles.add(article);
        } 
        //if it meets </hash>£¬assign temp to the hash attribute of an article object
        else if (qName.equalsIgnoreCase("hash")) {
            article.setTitleHash(temp);
        }
        //</tag>
        else if (qName.equalsIgnoreCase("tag")){
        	article.addAnnotation(annotation);
        }
        //</title>
        else if (qName.equalsIgnoreCase("title")){
        	article.setTitleName(temp);
        }
        //</users>
        else if (qName.equalsIgnoreCase("users")){
        	article.setUserCount(Integer.parseInt(temp));
        }
       
        //</name>
        else if (qName.equalsIgnoreCase("name")){
        	tag.setName(temp);
        	annotation.setTag(tag);
        	//if hashtable does not have this tag, then put this tag into the hashtable
        	if (tags.containsKey(tag)==false){
        		ArrayList<Article> list = new ArrayList<Article>();
        		list.add(article);
        		tags.put(tag,list);
        	}
        	//if hashtable has already had this tag, then find the corresponding list of this
        	//tag, add this article into the list
        	else{
        		tags.get(tag).add(article);
        	}
        	
        }
        //</count>
        else if (qName.equalsIgnoreCase("count")){
        	annotation.setCount(Integer.parseInt(temp));
        }
        //all of the annotations of an article have been stored in linkedlist
        //sort the linked list of annotations, ordered by the tag count
        //bubble sort
        else if (qName.equalsIgnoreCase("tags")){
        	LinkedList<Annotation> listOfAnn = article.getAnnotations();
        	for(int i=0;i<listOfAnn.size()-1;i++)
        		  for(int j=i+1;j<listOfAnn.size();j++)
        		  {
        		     Annotation annotation1 = listOfAnn.elementAt(i);
        		     Annotation annotation2 = listOfAnn.elementAt(j);
        		     //ann1>ann2, swap them, in ascending order
        		     if(annotation1.compareTo(annotation2)>0)
        		     {
        		    	 Annotation copyOfAnnotation2 = new Annotation(annotation2.getTag(),annotation2.getCount());
        		    	 Annotation eleJ = listOfAnn.elementAt(j);
        		    	 Annotation eleI = listOfAnn.elementAt(i);
        		    	 eleJ.setCount(annotation1.getCount());
        		    	 eleJ.setTag(annotation1.getTag());
        		    	 eleI.setCount(copyOfAnnotation2.getCount());
        		    	 eleI.setTag(copyOfAnnotation2.getTag());        		    	 
        		     }    
        		  }
        }
    }
    /**
     * Count the total number of articles in the dataset.
     * @return the total number of articles.
     */
    //task 1
    public int countNoArticle(){
    	// count the number of articles
        return articles.size();
    }
    /**
     * Count the total number of unique tags in the dataset.
     * @return the total number of unique tags
     */
    //task 2
    public int countNoTag(){
    	// count the number of unique tags
    	return tags.size();
    }
    /**
     * Count the number of tags used to annotate a particular article.
     *  
     * @param hash MD5 hash value of the article's URL.
     * @return the number of tags
     */
    //task 3
    public int countTagsOfAnArticle(String hash){
    	boolean hasTheArticle = false;
    	int size = 0;
    	a:
    	for(int i = 0;i < articles.size(); i++){
    		if(hash.equals(articles.elementAt(i).getTitleHash())){
    			size = articles.elementAt(i).numberOfTags();
    			hasTheArticle = true;
    			break a;
    		}
    	}
    	if(hasTheArticle == false){
    		size = 0;
    	}
    	return size;
    }
    /**
     * 
     * @param hash MD5 hash value of the article's URL.
     * @return a map with {@link Tag#name the name of the tag} referencing to 
     * 			{@link Annotation#count the count of this tag}
     */
    //task 4
    public LinkedHashMap<String,Integer> displayTagAndCount(String hash){
    	LinkedHashMap<String,Integer> nameCountPair = new LinkedHashMap<String,Integer>();  
    	a:
    	for(int i = 0; i < articles.size(); i++){
    		if(hash.equals(articles.elementAt(i).getTitleHash())){
    			
    			for(int j = 0; j < articles.elementAt(i).numberOfTags(); j++){
    				nameCountPair.put(articles.elementAt(i).getAnnotations().elementAt(j).getTag().getName()
    						,articles.elementAt(i).getAnnotations().elementAt(j).getCount());	
    			}
    			break a;
    		}
    	}
    
    	return nameCountPair;
    }
    /**
     * Count the number of times two tags co-occur in the dataset.
     * @param tag1 a <code>Tag</code> object
     * @param tag2 a <code>Tag</code> object
     * @return the number of times two tags co-occur
     */
    //task 5
    public int numOfTagCooccur(Tag tag1,Tag tag2){
    	int coOccurNo = 0;
    	ArrayList<Article> list1;
    	ArrayList<Article> list2;
    	if(tags.containsKey(tag1)==false || tags.containsKey(tag2)==false){
    		return coOccurNo;
    	}
    	list1 = tags.get(tag1);//return the value(arraylist of articles) to which the key(tag1) is mapped
    	list2 = tags.get(tag2);
    	//count the number of elements in list1 and list2 that are same
    	for(int i = 0; i<list1.size(); i++){
    		b:
    		for(int j = 0; j<list2.size(); j++){
    			if(list1.get(i)==list2.get(j)){
    				coOccurNo+=1;
    				break b;
    			}
    		}
    	}
    	return coOccurNo;
    }
    //task 6&7
    /**
     * Find all the bookmarks, hash values and tag counts that are annotated with a given tag.
     * 
     * @param tag a <code>Tag</code> object that is to be searched.
     * @return all the bookmarks, hash values and tag counts that are annotated with a given tag in a map view.
     */
    public TreeMap<Integer, ArrayList<String>> getCountAndBookmarkOfTag(Tag tag){
    	//put the articles with the same tag count value into a list 
    	//treemap will automatically sort according to the key
    	TreeMap<Integer, ArrayList<String>> bookmarkCountPair = new TreeMap<Integer, ArrayList<String>>(); 
    	String bookName, bookHash, nameAndHash;
    	if (tags.containsKey(tag)==true){
        	int sizeOfArticleList = tags.get(tag).size();//the number of articles in terms of a unique tag
        	for(int i = 0; i < sizeOfArticleList; i++ ){
        		
        		int sizeOfAnnotationOfAnArticle = tags.get(tag).get(i).getAnnotations().size();
        		c:
        		//there is no need to continue finding if the tag in the article has found
        		for(int j = 0; j < sizeOfAnnotationOfAnArticle; j++){
        			//if it finds this tag£¬
        			//then add article name, hash and tag count into map
        			if(tags.get(tag).get(i).getAnnotations().elementAt(j).getTag().equals(tag)){
        				int tagCount = tags.get(tag).get(i).getAnnotations().elementAt(j).getCount();
        				bookName = tags.get(tag).get(i).getTitleName();
        				bookHash = tags.get(tag).get(i).getTitleHash();
        				nameAndHash = bookName+"|"+bookHash;//for the gui use, to produce a URL
        				//if bookmarkCountPair doesn't have this count value, 
        				//then add this value to the map as a new key
        				//and add this article into the list that this new key points to
        				if(bookmarkCountPair.containsKey(tagCount)==false){
        					ArrayList<String> bookList = new ArrayList<String>();
        					bookList.add(nameAndHash);
        					bookmarkCountPair.put(tagCount, bookList);
        				}
        				//if bookmarkCountPair has already had this 'count' value ,
        				//then put this book into the list pointed to this count value key
        				else{
        					bookmarkCountPair.get(tagCount).add(nameAndHash);
        				}
        				break c;
        			}
        			
        		}
        			
        	}
       
        }
    	
    	return bookmarkCountPair;
    }
    //task 8
    /**
     * Suggests up to ten most frequently co-occurring tags with regard to the current tag.
     * Sorting all tags and their co-occuring times are implemented by a private method <code>sortMap</code>.
     * @param thisTag the current tag
     * @return up to ten tags and their co-occuring times in terms of a <code>Tag</code> object in a map view.
     */
    public Map<Tag, Integer> suggestFrequentOccurTag(Tag thisTag){
    	Set<Tag> allTagSet = tags.keySet();
    	Iterator<Tag> allTagIte = allTagSet.iterator();//all key
    	Map<Tag, Integer> tagCoOccurCountPair = new LinkedHashMap<Tag, Integer>();
    		
    		while(allTagIte.hasNext()){
    			Tag eachTag = allTagIte.next();
    			
    			//if it's not the tag itself, then count
    			if(thisTag.equals(eachTag)==false){
    				int eachTagCount = numOfTagCooccur(thisTag,eachTag);
    				tagCoOccurCountPair.put(eachTag, eachTagCount);
    			}
    		}
    		tagCoOccurCountPair = sortMap(tagCoOccurCountPair);
    		Map<Tag, Integer> tenTagCoOccurCountPair = new LinkedHashMap<Tag, Integer>();
    		Iterator<Entry<Tag, Integer>> it = tagCoOccurCountPair.entrySet().iterator();
    		int i = 0;
            z:
            while(it.hasNext()){  //no need to consider whether this map is null since this hasNext() method
                Map.Entry<Tag, Integer> entry = (Map.Entry<Tag, Integer>) it.next();
                //up to ten
                //if some tags' co-occur times are 0, then there is no need to put them into the map
                if(entry.getValue()==0){
                	break z;
                }
                tenTagCoOccurCountPair.put(entry.getKey(), entry.getValue());
                //suggest ten tags
                if(++i == 10){
                	break z;
                }
            }  
    		return tenTagCoOccurCountPair;
    }
    /**
     * Sort the map ordered by the value of the map.
     * This method implements the sorting by means of an anonymous class in which 
     * there is a override method <code>compare</code>. 
     * @param oldMap the map needed to sort
     * @return the map ordered by the value of the map.
     */
    //sort map according to value, helper method for task8
    private Map<Tag, Integer> sortMap(Map<Tag, Integer> oldMap) {  
        ArrayList<Map.Entry<Tag, Integer>> list = new ArrayList<Map.Entry<Tag, Integer>>(oldMap.entrySet());  
        //sort the list
        //anonymous
        Collections.sort(list, new Comparator<Map.Entry<Tag, Integer>>() {  
  
            public int compare(Entry<Tag, Integer> arg0,  
                    Entry<Tag, Integer> arg1) {  
                return  arg1.getValue() - arg0.getValue();  
            }  
        });  
        Map<Tag, Integer> newMap = new LinkedHashMap<Tag, Integer>();  
        for (int i = 0; i < list.size(); i++) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
    } 
    /**
     * Test the program.
     * @param args command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	//SAXDataExtraction anObject = new SAXDataExtraction();
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new SAXDataExtraction());
        xmlReader.parse("data/wiki10+/tag-data.xml");
        SwingUtilities.invokeLater(new Runnable() {
	        public void run(){
	            //Turn off metal's use of bold fonts
	            UIManager.put("swing.boldMetal", Boolean.FALSE);
	            Sgui.createAndShowGUI();
	        }
	    });
    }
    

}

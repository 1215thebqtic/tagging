package cw_1201592;

/**
 * Stores data for an article.
 * 
 * 	Instances of this class store:
 * 	<ul>
 * 		<li>the hash value of the article's URL;</li>
 * 		<li>the title for the bookmarked article, as a string;</li>
 * 		<li>an integer value for number of users who have tagged the article; and</li>
 * 		<li>a linked list of its annotations</li>
 * 	</ul>
 * 	There are operations to get and set these fields, to add an annotation instance 
 * 	to the linked list and to calculated the size of the linked list of annotations.
 * 
 * @author Boqing Liu
 */
public class Article {
	/**
	 * The MD5 hash value of the article's URL.
	 */
    private String titleHash;
    /**
     * The title for the bookmarked article
     */
    private String titleName;
    /**
     * The number of users who have tagged the article.
     */
    private int userCount;
    /**
     * The linked list of all of annotations of the article
     */
    private LinkedList<Annotation> annotations;
    
    /**
     * Constructs a new <code>Article</code> instance with the linked list of annotations instantiated.
     */
	public Article(){
    	annotations = new LinkedList<Annotation>();
    }
    
    //setter and getter
	/**
	 * Returns the linked list of the annotations of the article.
	 * @return the linked list of the <code>annotations</code> of the article.
	 */
    public LinkedList<Annotation> getAnnotations() {
		return annotations;
	}
    /**
     * Sets the MD5 hash value of the article's URL.
     * @param titleHash the MD5 hash value of the article's URL
     */
    public void setTitleHash(String titleHash){
        this.titleHash = titleHash;
    }
    /**
     * Returns the MD5 hash value of the article's URL.
     * @return the MD5 hash value of the article's URL
     */
    public String getTitleHash(){
        return titleHash;
    }
    /**
     * Sets the title for the article.
     * @param titleName the title of the article
     */
    public void setTitleName(String titleName){
    	this.titleName = titleName;
    }
    /**
     * Returns the title of the article.
     * @return the title of the article
     */
    public String getTitleName(){
    	return titleName;
    }
    /**
     * Sets the number of users who have tagged the article.
     * @param userCount the number of users who have tagged the article
     */
    public void setUserCount(int userCount){
    	this.userCount = userCount;
    }
    /**
     * Returns the number of users who have tagged the article.
     * @return the number of users who have tagged the article.
     */
    public int getUserCount(){
    	return userCount;
    }
    /**
     * Adds an <code>Annotation</code> object into the linked list <code>annotations</code>.
     * @param annotation the <code>Annotation</code> object to be added
     */
    //add an annotation
    public void addAnnotation(Annotation annotation){
    	annotations.add(annotation);
    }
    //count tags for an article
    /**
     * The size of <code>annotations</code>.
     * Counts the number of items (tagging behaviors) in the <code>annotations</code> list.
     * 
     * @return the number of items in the <code>annotations</code> list
     */
    public int numberOfTags(){
    	return annotations.size();
    }

}

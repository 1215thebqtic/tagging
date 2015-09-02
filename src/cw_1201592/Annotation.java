package cw_1201592;
/**
 *  Stores data for a tagging behavior.
 *  
 *  Instances of this class store:
 *  <ul>
 *  	<li>the tag of an annotation, as an ADT {@link Tag Tag} and</li>
 * 		<li>an integer value for the count of the annotation.</li>
 *  </ul>
 *  Each of these has a getter and setter method.
 *  
 * @author Boqing Liu
 *
 */

public class Annotation implements Comparable<Annotation>{
	/**
	 * The tag of the annotation
	 */
	private Tag tag;
	/**
	 * The tag count of the annotation
	 */
	private int count;
	
	/**
	 * Constructs a new <code>Annotation</code> instance without any information.
	 */
	public Annotation(){
		
	}
	/**
	 * Constructs a new <code>Annotation</code> instance with the given data.
	 * @param tag the tag of the annotation
	 * @param count the tag count of the annotation
	 */
	public Annotation(Tag tag, int count){
		this.tag = tag;
		this.count = count;
	}
	//getter and setter
	/**
	 * Returns the tag of the annotation.
	 * 
	 * @return the tag of the annotation
	 */
	public Tag getTag() {
		return tag;
	}
	/**
	 * Returns the tag count for the annotation.
	 * @return the tag count value
	 */
	public int getCount() {
		return count;
	}
	/**
	 * Sets the tag of the annotation.
	 * @param tag the tag of the annotation to be set
	 */
	public void setTag(Tag tag) {
			this.tag = tag;
	}
	/**
	 * Sets the tag count of the annotation.
	 * @param count the count value of the annotation to be set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	
	/**
	 * Compares this <code>Annotation</code> object with the specified object in terms of <code>count</code>.
	 * @param ann the <code>Annotation</code> object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
	 */
	public int compareTo(Annotation ann){
		return this.getCount()-ann.getCount();
	}
	
	
	
	
}

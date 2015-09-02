package cw_1201592;
/**
 * Stores data for a tag provided by users.
 * 
 * Instances of this class store:
 * <ul>
 * 	<li>the name of the tag, as a string.</li>
 * </ul>
 * There are operations to set and get the field value, to 
 * get the hash code the <code>Tag</code> object and to compare
 * whether two <code>Tag</code> objects are equal or not in 
 * terms of <code>name</code>.
 * @author Boqing Liu
 *
 */
public class Tag {
	/**
	 * The name of the tag
	 */
	private String name;
	
	/**
	 * Constructs a <code>Tag</code> instance without any information.
	 */
	public Tag(){
		
	}
	/**
	 * Constructs a <code>Tag</code> instance with a <code>name</code>.
	 * @param name the name of the tag
	 */
	public Tag(String name){
		this.name = name;
		
	}
	
	//getter and setter
	/**
	 * Returns the name of the tag.
	 * @return the name of the tag
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name for the tag.
	 * @param name the name of the tag
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the hash code value for a <code>Tag</code> object.
	 * @return the hash code value of <code>name</code>
	 * 
	 */
	public int hashCode(){
		return name.hashCode();
	}
	/**
	 * Indicates whether some other <code>Tag</code> object is 
	 * "equal to" this one in terms of <code>name</code>.
	 * 
	 * Two <code>Tag</code> objects are considered as equal if 
	 * they share a same <code>name</code>.
	 * 
	 * @param obj the reference object with which to compare
	 * @return <code>true</code> if this object's <code>name</code>
	 * is the same as the obj argument's; <code>false</code> otherwise.
	 */
	public boolean equals(Object obj){
		if(obj instanceof Tag){
			Tag theTag = (Tag)obj;
			if(name.equals(theTag.name))
				return true;
		}
	
		return false;
	}
	
}

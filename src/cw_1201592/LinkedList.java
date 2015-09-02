package cw_1201592;
/**
 *  Doubly-linked lists to store annotations.
 *  There are operations to add to the start and end of the list,
 *  to calculate the length of the list, to remove the last
 *  element of the list, and to find the component at the specific index.
 *  The doubly-linked list is implemented in the inner
 *  class <code>LLNode</code>.
 *
 *
 * @author Boqing Liu
 *
 */
public class LinkedList<A>
{

   /**
    *  This stores the linked list of Annotations.
    *  Its value is null for an empty list;
    *  otherwise it points to the first LLNode in the list.
    *
    */
   private LLNode listStart;


   /**
    *  This points to the last LLNode in the list.
    *  The value is null if the list is empty.
    *
    */
   private LLNode listEnd;


   /**
    * Creates a new <code>LList</code> instance.
    * Does nothing; listStart is null when empty.
    *
    */
   public LinkedList()
   {
   }

  
   /**
    *  Add an element to the start of the list.
    *
    * @param b the item to be added
    *
    */
   public void add(A b)
   {
      // make a new node whose tail is listStart
      listStart = new LLNode(b, listStart);

      // if the list was empty before, the new node is also the last
      if (listEnd == null)
      {
         listEnd = listStart;
      }

   }
   /**
    *  Returns the component at the specific index
    * @param i an index into this linked list
    * @return the component at the specific index
    */
   public A elementAt(int i)
   {
	   int len = 0;
	   LLNode n = listStart;
	   A objectFound = null;
	   a:
	   while(n != null)
	   {
		   
		   if(len == i){
			   objectFound = n.head();
			   break a;
		   }
		   len++;
		   n = n.tail();
	   }
	   return objectFound;
   }
   /**
    *  The size of the list.
    *  Counts the number of annotations in the list.
    *
    * @return the number of items in the list
    */
   public int size()
   {
      // stores the number of nodes found so far
      int len = 0;

      // this variable traverses each node in the list
      LLNode n = listStart;

      // find the end of the list
      while (n != null)
      {
         // found a node, so count it...
         len++;

         // ...and move on
         n = n.tail();
      }

      /*
       * n is null when we exit the loop,
       * so we're at the end, and have counted every node
       */
      return len;
   }


   /**
    *  Add an annotation item to the end of the list.
    *
    * @param b the item to be added to the list
    *
    */
   public void addend(A b)
   {
      if (listStart == null)
      {
         // the list is empty, so just add the card
         add(b);
      }
      else
      {
         // the list is not empty,
         // so stick b at the end (using the LLNode method)
         listEnd.addend(b);

         // and point to the new end of the list
         listEnd = listEnd.tail();
      }
   }


   /**
    * Remove the last annotation element of the list.
    * If the list is empty, this method will have no effect;
    * i.e., the list will stay empty.
    *
    */
   public void removeLast()
   {
      if (listEnd != null)
      {
         // list is not empty; is there only one element in the list?
         if (listEnd.prev() == null)
         {
            /*
             * this is the start of the list;
             * i.e., there is just one node in the list,
             * so set the list to empty (null)
             */
            listStart = null;
            listEnd = null;
         }
         else
         {
            /*
             * there is a previous node,
             * so this becomes the end of the list
             */
            listEnd = listEnd.prev();

            /*
             * now remove the link to the old last node
             */
            listEnd.chop();
         }
      }
      // else: list is empty, do nothing
   }



   /**
    * A node in a doubly-linked list.
    *  A helper class to implement linked lists, each node consists
    *  of an item (of type A), a pointer to the next node,
    *  and a pointer to the previous node.
    *  The empty list is represented by null.  Thus, for example,
    *  a list of length 1 is a node whose tail and previous node are both null.
    *
    * @author Boqing Liu
    */
   private class LLNode
   {

      /**
       *  An element in the list.
       *
       */
      private A value;

      /**
       *  The list that follows from the current node.
       *
       */
      private LLNode tail;


      /**
       *  The node preceding this in the doubly-linked list.
       *
       */
      private LLNode prev;


      /**
       * Creates a new <code>LLNode</code> instance as a list of length 1;
       * the tail and prev pointers are both null.
       *
       * @param b the item to store at the current node.
       */
      public LLNode(A b)
      {
         value = b;
      }

      /**
       * Creates a list by creating a node with a given value and tail.
       * The {@link #prev prev} node is null.
       *
       * @param b the item at the start of the list
       * @param t the tail of the list
       */
      public LLNode(A b, LLNode t)
      {
         value = b;
         tail = t;
         if (t != null)
         {
            t.prev = this;
         }
      }


      /**
       * Get the value of the list.
       *
       * @return the item at the start of the list (the current node)
       */
      public A head()
      {
         return value;
      }


      /**
       *  Get the tail of the list.
       *
       * @return the node linked to from the current node
       *         (null if the current node is the end of the list)
       */
      public LLNode tail()
      {
         return tail;
      }


      /**
       *  Get the previous node in the list.
       *
       * @return the node previous to the current node
       *         (null if the current node is the start of the list)
       */
      public LLNode prev()
      {
         return prev;
      }


      /**
       *  Add an annotation item to the end of the list.
       *
       * @param b the annotation item to add
       */
      public void addend(A b)
      {
         tail = new LLNode(b);
         tail.prev = this;
      }


      /**
       * Cut the list at the current node.
       * The current node becomes the end of the list.
       *
       */
      public void chop()
      {
         tail = null;
      }

   }

}


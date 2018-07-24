/*
 * Name		: Andrew Hartwell
 * cruzID	: arhartwe
 * Assignment	: pa3
*/ 

public class List
{

	private Node front;
	private Node back;
	private Node cursor;
	private Node temp;
	private int length = length();

	List()
	{
		this.front = null;
		this.back = null;
		this.cursor = null;
		this.temp = null;
	}

	private class Node
	{
		private Object data;
		private Node next;
		private Node prev;

		Node(Object data)
		{
			this.data = data;
		}
	}

	int length() // Returns the number of elements in this List
	{

		int i = 0;
		temp = front;

		while(temp != null)
		{
			i++;
			temp = temp.next;
		}
		length = i;
		if(length == 0)
		{
			return 0;
		}
		return length;	
	}

	int index() // If cursor is defined, returns the index of the cursor element,
	    // otherwise returns -1; 
	    {	
		    int index = 0;

		    if(cursor != null)
		    {
			    temp = front;
			    while(temp != cursor)
			    {
				    index++;
				    temp = temp.next;	
			    }
			    return index;
		    }

		    return -1;

	    }

	Object front() // Returns front element. Pre: length()>0
	{
		if(length() > 0)
		{
			return front.data;
		}
		else
		{
			System.err.println("Front Does Not Exist");
			System.exit(1);
			return -1;	
		}	
	}

	Object back() // Returns back element. Pre: length()>0
	{
		if(length() > 0)
		{
			return back.data;
		}
		else
		{
			System.err.println("Back Does Not Exist");
			System.exit(1);
			return -1;
		}
	}

	Object get() // Returns cursor element. Pre: length > 0
	{
		if(length() > 0)
		{
			return cursor.data;
		}
		else
		{
			System.err.println("Cursor Does Not Exist");
			System.exit(1);
			return -1;
		}
	}

	public boolean equals(List L) // Returns true if and only if this List and L are the same object
		// sequence. The states of the cursors in the two Lists are not used
		// in determining equality.
	{
		temp = front;
		Node compTemp = L.front;

		while(temp != null && compTemp != null)
		{
			if(temp.data != compTemp.data)	
			{
				return false;
			}

			temp = temp.next;
			compTemp = compTemp.next;			
		}	
		return (temp == null && compTemp == null);
	}

	void clear() // Resets this List to its original empty state
	{
		if(length() == 0)
		{
			front = null;
			back = null;
			length = 0;
		}
		else
		{
			while(front.next != null)
			{
				deleteFront();
			}
			front = null;
		}
	}

	void moveFront() // If List is non-empty, places the cursor under the front element,
	     // otherwise does nothing.
	     {
		     if(length() > 0)
		     {
			     cursor = front;
		     }
	     }

	void moveBack() // If List is non-empty, places the cursor under the back element,
	     // otherwise does nothing.
	     {
		     if(length() > 0)
		     {
			     cursor = back;
		     }
	     }	

	void movePrev() // If cursor is defined and not at front, moves cursor one step toward
		// front of this List, if cursor is defined and at front, cursor becomes
		// undefined, if cursor is undefined, does nothing.
	{
		if(cursor != null && cursor != front)
		{
			cursor = cursor.prev;
		}
		else if(cursor != null && cursor == front)
		{
			cursor = null;
		}
		else
		{
			return;
		}	
	}

	void moveNext() // If cursor is defined and not at back, moves cursor one step toward 
		// back of this List, if cursor is defined and at back, cursor becomes 
		// undefined, if cursor is undefined does nothing.
	{
		if(cursor != null && cursor != back)
		{
			cursor = cursor.next;
		}
		else if(cursor != null && cursor == back)
		{
			cursor = null;
		}
		else
		{
			return;
		}
	}

	void prepend(Object data) // Insert new element into this List. If List is non-empty,
	     // insertion takes place before front element.
	     {	
		     Node node = new Node(data);

		     if(back == null)
		     {
			     back = node;
			     back.prev = null;
			     back.next = null;
		     }
		     else	
		     {
			     temp = back;

			     while(temp.prev != null)
			     {
				     temp = temp.prev;
			     }

			     temp.prev = node;
			     node.next = temp;
			     node.prev = null;
		     }
		     front = node;
	     }

	void append(Object data) // Insert new element into this List. if List is non-empty,
	     // insertion takes place after back element;
	     {	
		     Node node = new Node(data);

		     if(front == null)
		     {
			     front = node;
			     front.prev = null;
			     front.next = null;
		     }
		     else
		     {
			     temp = front;

			     while(temp.next != null)
			     {
				     temp = temp.next;
			     }

			     temp.next = node;
			     node.prev = temp;
			     node.next = null;
		     }
		     back = node;
	     }		

	void insertBefore(Object data) // Insert new element before cursor.
		// Pre: length() > 0, index() >= 0
	{
		Node node = new Node(data);
		if(cursor.prev == null)
		{
			front = node;
			cursor.prev = node;
			node.prev = null;
			node.next = cursor; 
		}
		else
		{
			cursor.prev.next = node;
			node.prev = cursor.prev;
			node.next = cursor;
			cursor.prev = node;
		}
	}

	void insertAfter(Object data) // Inserts new element after cursor.
		// Pre: length()> 0, index() >= 0
	{
		Node node = new Node(data);
		if(cursor.next == null)
		{
			back = node;
			cursor.next = node;
			node.next = null;
			node.prev = cursor;
		}
		else
		{
			cursor.next.prev = node;
			node.next = cursor.next;
			node.prev = cursor;
			cursor.next = node;
		}
	}

	void deleteFront() // Deletes the front element. Pre: length()>0
	{
		if(length() > 0)
		{
			if(cursor == front)
			{
				cursor = null;
			}
			if(length() > 2)
			{
				front = front.next;
				front.prev = null;
			}
			else if(length() == 2)
			{
				front = front.next;
				front.prev = null; 
				front.next = null;
			}
			else 
			{
				front = null;
				back = null;
			}
		}
	}

	void deleteBack() // Deletes the back element. Pre: length()>0
	{
		if(length() > 0)
		{
			if(cursor == back)
			{
				cursor = null;
			}
			if(length() > 2)
			{
				back = back.prev;
				back.next = null;
			}
			else if(length() == 2)
			{
				back = back.prev;
				back.prev = null;
				back.next = null;
			}
			else
			{
				front = null;
				back = null;
			}
		}	
	}

	void delete() // Deletes cursor element, making cursor undefined.
		// Pre: length() > 0, index >= 0
	{
		if(length()> 0 && index() >= 0)
		{
			if(length() == 1)
			{	
				clear();
			}	
			else if(cursor == front)
			{
				front = cursor.next;
				cursor = null;
			}
			else if(cursor == back)
			{	
				back = cursor.prev;
				back.next = null;
				cursor = null;
			}
			else
			{
				cursor.prev.next = cursor.next;
				cursor.next.prev = cursor.prev;
				cursor = null;
			}
		}
	}

	public String toString()
	{
		if(front == null)
		{
			return "";
		}

		else
		{
			String printList = front.data + " ";
			Node cursor = front;
			while(cursor.next != null)
			{
				cursor = cursor.next;
				if(cursor.next == null)
				{
					printList += cursor.data;
				}
				else
				{
					printList += cursor.data + " ";
				}
			}

			return printList;
		}		

	}
}





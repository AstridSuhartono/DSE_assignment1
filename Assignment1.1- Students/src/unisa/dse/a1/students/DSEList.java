package unisa.dse.a1.students;

import unisa.dse.a1.interfaces.List;

/**
 * @author simont
 *
 */
public class DSEList implements List {

	public Node head;
	private Node tail;

	public DSEList() {
		head = null;
		tail = null;
	}

	public DSEList(Node head_) {
		head = head_;
	}

	// Takes a list then adds each element into a new list
	public DSEList(DSEList other) { // Copy constructor.
		// create a new list called copy
		DSEList copy = new DSEList();
		// copy the empty other list into copy list
		if (other.head == null) {
			this.head = null;
		}
		// deep copy each nodes in the other list into copy list
		else {
			copy.head = new Node(other.head.next, null, other.head.getString());
			Node currentNode = copy.head.next;
			Node prevNode = copy.head;
			while (currentNode != null) { // iterate through each nodes and copy into the copy list
				Node newNode = new Node(currentNode.next, prevNode, currentNode.getString());
				prevNode.next = newNode;
				prevNode = newNode;
				currentNode = currentNode.next;
			}
			this.head = copy.head;
		}
	}

	// remove the String at the parameter's index
	public String remove(int index) {
		if(index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		else if (head == null) {
			throw new NullPointerException();
		}
		Node currentNode = head;
		Node prevNode = null;
		if (index == 0 && head.next != null) {
			String item = head.getString();
			head = head.next;
			head.prev = null;
			return item;
		} else if (index == this.size() - 1 && currentNode.next != null) {
			while(currentNode.next != null) {
				prevNode = currentNode;
				currentNode = currentNode.next;
			}
			tail = prevNode;
			prevNode.next = null;
			
		} else {
			for(int i = 0; i < index; i++) {
				prevNode = currentNode;
				currentNode = currentNode.next;
			}
			if(currentNode.next != null) {
				prevNode.next = currentNode.next;
				currentNode.next.prev = prevNode;
			}	
		} 
		return currentNode.getString();
	}

	// returns the index of the String parameter
	public int indexOf(String obj) {
		if(obj == null) {
			return -1;
		}
		Node nodeRef = head;
		int count = 0;
		while(nodeRef != null){
			if (!nodeRef.getString().equals(obj)) {
				 nodeRef = nodeRef.next;
				 count++;
			}
		}
		return count;
	}

	// returns String at parameter's index
	public String get(int index) {
		if (index < 0 || index >= this.size()) {
			return null;
		}
		Node nodeRef = head;
		// iterate through the list to get the node
		for (int i = 0; i < index && nodeRef != null; i++) {
			nodeRef = nodeRef.next;
		}
		return nodeRef.getString();
	}

	// checks if there is a list
	public boolean isEmpty() {
		return head == null;
	}

	// return the size of the list
	public int size() {
		int count = 0;
		Node nodeRef = head;
		while (nodeRef != null) {
			count++;
			nodeRef = nodeRef.next;
		}
		return count;
	}

	// Take each element of the list a writes them to a string
	@Override
	public String toString() {
		Node nodeRef = head;
		String result = "";
		while (nodeRef != null) {
			result += nodeRef.getString() + " "; // append the element's String
			nodeRef = nodeRef.next;
		}
		return result.trim();
	}

	// add the parameter String at of the end of the list
	public boolean add(String obj) {
		if (obj == null) {
			throw new NullPointerException();
		} else {
			// add the obj to head node if the list is still empty
			if (head == null) {
				head = new Node(null, null, obj);
				tail = head;
			} else {
				Node nodeRef = head;
				while (nodeRef.next != null) {
					nodeRef = nodeRef.next;
				}
				nodeRef.next = new Node(null, nodeRef, obj);
				tail = nodeRef.next;
			}
			return true;
		}
	}

	// add String at parameter's index
	public boolean add(int index, String obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		Node nodeRef = head;
		if (index == 0) {
			if(head == null) {
				head = new Node(null,null,obj);
				tail = head;
			} else {
				head = new Node(nodeRef,null,obj);
				nodeRef.prev = head;
			}	
		}
		else if(index == this.size()) {
			Node newNode = new Node(null,tail,obj);
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		} else {
			for(int i = 0; i < index && nodeRef != null; i++) {
				nodeRef = nodeRef.next;
			}
			Node newNode = new Node(nodeRef,nodeRef.prev,obj);
			nodeRef.prev.next = newNode;
			nodeRef.prev = newNode;
		}
		return true;
	}

	// searches list for parameter's String return true if found
	public boolean contains(String obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		Node nodeRef = head;
		while(nodeRef != null){
			if (nodeRef.getString().equals(obj)) {
				return true;
			}
			nodeRef = nodeRef.next;
		}
		return false;
	}

	// removes the parameter's String form the list
	public boolean remove(String obj) {
		if(obj == null || head == null) {
			throw new NullPointerException();
		}
		Node currentNode = head;
		Node prevNode = null;
		//iterate the list while the node is not null and the element not equals obj
		while(currentNode != null && !currentNode.getString().equals(obj)) {
			prevNode = currentNode;
			currentNode = currentNode.next;
		}
		//shift head.next node into the head node
		if(prevNode == null) {
			head = head.next;
			if(head != null) {
				head.prev = null;
			}
		}
		else {
			prevNode.next = currentNode.next;
			if(prevNode.next != null) {
				prevNode.next.prev = prevNode;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) { // check if there is any data in the list
			return false;
		}
		// check if the other has the same class with the compared list
		else if (getClass() != other.getClass()) {
			return false;
		} else {
			DSEList otherList = (DSEList) other; // cast Object other into DSEList
			if (size() != otherList.size()) { // Compare the size of both the lists
				return false;
			}
			// compare each data inside the nodes of both the lists
			Node nodeRef = head;
			Node otherNodeRef = otherList.head;
			while (nodeRef != null) {
				if (!(nodeRef.getString().equals(otherNodeRef.getString()))) {
					return false;
				}
				nodeRef = nodeRef.next;
				otherNodeRef = otherNodeRef.next;
			}
			return true;
		}
	}

}
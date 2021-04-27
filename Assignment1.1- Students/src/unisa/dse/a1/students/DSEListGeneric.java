package unisa.dse.a1.students;

import unisa.dse.a1.interfaces.ListGeneric;

/**
 * @author simont
 * @param <T>
 *
 */
public class DSEListGeneric<T> implements ListGeneric<T> {
	
	public NodeGeneric<T> head;
	private NodeGeneric<T> tail;

	public DSEListGeneric() {
		head = null;
		tail = null;
	}
	public DSEListGeneric(NodeGeneric<T> head_) {
		head = head_;
	}
	
	//Takes a list then adds each element into a new list
	public DSEListGeneric(DSEListGeneric<T> other) { // Copy constructor. 
		DSEListGeneric<T> copy = new DSEListGeneric<T>();
		// copy the empty other list into copy list
		if (other.head == null) {
			this.head = null;
		}
		// deep copy each nodes in the other list into copy list
		else {
			copy.head = new NodeGeneric<T>(other.head.next, null, other.head.get());
			NodeGeneric<T> currentNode = copy.head.next;
			NodeGeneric<T> prevNode = copy.head;
			while (currentNode != null) { // iterate through each nodes and copy into the copy list
				NodeGeneric<T> newNode = new NodeGeneric<T>(currentNode.next, prevNode, currentNode.get());
				prevNode.next = newNode;
				prevNode = newNode;
				currentNode = currentNode.next;
			}
			this.head = copy.head;
		}
	}

	//remove the String at the parameter's index
	public T remove(int index) {
		if(index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		else if (head == null) {
			throw new NullPointerException();
		}
		NodeGeneric<T> currentNode = head;
		NodeGeneric<T> prevNode = null;
		if (index == 0 && head.next != null) {
			T item = head.get();
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
		return currentNode.get();
	}

	//returns the index of the String parameter 
	public int indexOf(T obj) {
		if(obj == null) {
			return -1;
		}
		NodeGeneric<T> nodeRef = head;
		int count = 0;
		while(nodeRef != null){
			if (!nodeRef.get().equals(obj)) {
				 nodeRef = nodeRef.next;
				 count++;
			}
		}
		return count;
	}
	
	//returns String at parameter's index
	public T get(int index) {
		if (index < 0 || index >= this.size()) {
			return null;
		}
		NodeGeneric<T> nodeRef = head;
		// iterate through the list to get the node
		for (int i = 0; i < index && nodeRef != null; i++) {
			nodeRef = nodeRef.next;
		}
		return nodeRef.get();
	}

	//checks if there is a list
	public boolean isEmpty() {
		return head == null;
	}

	//return the size of the list
	public int size() {
		int count = 0;
		NodeGeneric<T> nodeRef = head;
		while (nodeRef != null) {
			count++;
			nodeRef = nodeRef.next;
		}
		return count;
	}
	
	//Take each element of the list a writes them to a string 
	@Override
	public String toString() {
		NodeGeneric<T> nodeRef = head;
		String result = "";
		while (nodeRef != null) {
			result += nodeRef.get() + " "; // append the element's String
			nodeRef = nodeRef.next;
		}
		return result.trim();
	}

	//add the parameter String at of the end of the list
	public boolean add(T obj) {
		if (obj == null) {
			throw new NullPointerException();
		} else {
			// add the obj to head node if the list is still empty
			if (head == null) {
				head = new NodeGeneric<T>(null, null, obj);
				tail = head;
			} else {
				NodeGeneric<T> nodeRef = head;
				while (nodeRef.next != null) {
					nodeRef = nodeRef.next;
				}
				nodeRef.next = new NodeGeneric<T>(null, nodeRef, obj);
				tail = nodeRef.next;
			}
			return true;
		}
	}

	//add String at parameter's index
	public boolean add(int index, T obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		NodeGeneric<T> nodeRef = head;
		if (index == 0) {
			if(head == null) {
				head = new NodeGeneric<T>(null,null,obj);
				tail = head;
			} else {
				head = new NodeGeneric<T>(nodeRef,null,obj);
				nodeRef.prev = head;
			}	
		}
		else if(index == this.size()) {
			NodeGeneric<T> newNode = new NodeGeneric<T>(null,tail,obj);
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		} else {
			for(int i = 0; i < index && nodeRef != null; i++) {
				nodeRef = nodeRef.next;
			}
			NodeGeneric<T> newNode = new NodeGeneric<T>(nodeRef,nodeRef.prev,obj);
			nodeRef.prev.next = newNode;
			nodeRef.prev = newNode;
		}
		return true;
	}

	//searches list for parameter's String return true if found
	public boolean contains(T obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		NodeGeneric<T> nodeRef = head;
		while(nodeRef != null){
			if (nodeRef.get().equals(obj)) {
				return true;
			}
			nodeRef = nodeRef.next;
		}
		return false;
	}

	//removes the parameter's String form the list
	public boolean remove(T obj) {
		if(obj == null || head == null) {
			throw new NullPointerException();
		}
		NodeGeneric<T> currentNode = head;
		NodeGeneric<T> prevNode = null;
		//iterate the list while the node is not null and the element not equals obj
		while(currentNode != null && !currentNode.get().equals(obj)) {
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
			DSEListGeneric<T> otherList = (DSEListGeneric<T>) other; // cast Object other into DSEList
			if (size() != otherList.size()) { // Compare the size of both the lists
				return false;
			}
			// compare each data inside the nodes of both the lists
			NodeGeneric<T> nodeRef = head;
			NodeGeneric<T> otherNodeRef = otherList.head;
			while (nodeRef != null) {
				if (!(nodeRef.get().equals(otherNodeRef.get()))) {
					return false;
				}
				nodeRef = nodeRef.next;
				otherNodeRef = otherNodeRef.next;
			}
			return true;
		}
	}
	
}

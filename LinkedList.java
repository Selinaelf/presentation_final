package chat2;

public class LinkedList {
	public CourseNode head;

    public LinkedList() {
        this.head = null;
    }

    public void add(Course data) {
        CourseNode newNode = new CourseNode(data);
        if (head == null) {
            head = newNode;
        } else {
            CourseNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void printList() {
        CourseNode current = head;
        while (current != null) {
            System.out.println(current.data.courseName + " - " + current.data.startTime);
            current = current.next;
        }
    }
    
  
	}


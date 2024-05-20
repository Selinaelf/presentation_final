package chat2;

public class BinaryTreeNode {
	Course data;
    BinaryTreeNode left, right;
    LinkedList coursesWithSameStartTime;

    public BinaryTreeNode(Course data) {
        this.data = data;
        this.coursesWithSameStartTime = new LinkedList();
        this.left = null;
        this.right = null;
    }
    
     public int countCoursesByDay(String startTime,String day) {
        CourseNode current = coursesWithSameStartTime.head;
        int count = 0;
        while (current != null) {
            if ((current.data.startTime.equals(startTime)) && (current.data.day.equals(day))) {
                count++;
                }
                current = current.next;
            }
          return count;
            }
     


     
     
     
        
       
    
}

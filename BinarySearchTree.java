package chat2;

import java.util.ArrayList;
import java.util.Scanner;

public class BinarySearchTree {
	private AddedCourses addedCourseNames;
	
	public BinaryTreeNode root;
	String[][]dersp= {{"","Monday","","Tuesday","","Wed","","Thurs","","Friday"},
	        {"8.30"," "," "," "," "," "," "," "," "," "},
            {"9.30"," "," "," "," "," "," "," "," "," "},
            {"10.30"," "," "," "," "," "," "," "," "," "},
            {"11.30"," "," "," "," "," "," "," "," "," "},
            {"12.30"," "," "," "," "," "," "," "," "," "},
            {"13.30"," "," "," "," "," "," "," "," "," "},
            {"14.30"," "," "," "," "," "," "," "," "," "},
            {"15.30"," "," "," "," "," "," "," "," "," "},
            {"16.30"," "," "," "," "," "," "," "," "," "},
            {"17.30"," "," "," "," "," "," "," "," "," "},
            };
	
    public BinarySearchTree() {
        this.root = null;
        addedCourseNames = new AddedCourses();
    }

    public void addCourse(Course course) {
        root = addCourseRecursive(root, course);
    }
    
    public void printdersp(String[][] dersp) {
		for(int row=0;row<dersp.length;row++) {
			for(int col=0;col<dersp[0].length;col++) {
				System.out.print(dersp[row][col]+"\t");
			}
			System.out.println();		
		}
		
	}

    public BinaryTreeNode addCourseRecursive(BinaryTreeNode current, Course course) {
        if (current == null) {
            return new BinaryTreeNode(course);
        }

        if (course.startTime.equals(current.data.startTime)) {
            if (current.coursesWithSameStartTime == null) {
                current.coursesWithSameStartTime = new LinkedList();
            }
            current.coursesWithSameStartTime.add(course);
        } else if (course.startTime.compareTo(current.data.startTime) < 0) {
            current.left = addCourseRecursive(current.left, course);
        } else {
            current.right = addCourseRecursive(current.right, course);
        }

        return current;
    }
    

    public void printInOrder() {
        printInOrderRecursive(root);
    }
    
    public String[][] addSuggestionList(String[][] dersp, Course lesson) {
        boolean isAlreadyAdded = false;
        for (int row = 0; row < dersp.length; row++) {
            for (int col = 1; col < dersp[row].length; col++) {
                if (lesson.day.equals(dersp[0][col]) && lesson.startTime.equals(dersp[row][0])) {
                    if (dersp[row][col].equals(" ")) {
                        dersp[row][col]=lesson.courseName + "-" +lesson.sectionNumber;
                        addedCourseNames.addCourseName(lesson.courseName);
                    } else {
                        isAlreadyAdded = true;
                        System.out.println("There is a conflict at " + lesson.day + " " + lesson.startTime);
                    }
                }
            }
        }

        if (isAlreadyAdded) {
            return dersp; // Zaten eklenmişse, değişiklik yapmadan geri dön
        }

        return dersp;
    }
    private void printCoursesWithNoConflict(CourseNode head, String day) {
	    CourseNode current = head;
	    while (current != null) {
	        if (current.data.day.equals(day)) {
	            // Ders daha önce eklenmemişse ve çakışma yoksa, ekleyin
	            if (!addedCourseNames.contains(current.data.courseName)) {
	               // addedCourseNames.addCourseName(current.data.courseName);
	                dersp = addSuggestionList(dersp, current.data);
	                System.out.println(current.data.courseName + " has no conflict and It's added to your schedule successfully");
	                System.out.println();
	                // Ders daha önce eklenmişse ve çakışma olmadığı için yine eklenecekse eklenmesini önle.
	            } else {
	                System.out.println(current.data.courseName + " has already been added to the schedule.");
	                System.out.println();
	            }
	        }
	        current = current.next;
	    }
	    printdersp(dersp);
	}
//
//  private void printCoursesWithNoConflict(CourseNode head, String day) {
//	    CourseNode current = head;
//	    while (current != null) {
//	        if (current.data.day.equals(day)) {
//	            dersp = addSuggestionList(dersp, current.data);
//	            System.out.println(current.data.courseName + " has no conflict and Its added to your schedule succesfully");
//	            System.out.println();
//	        }
//	        current = current.next;
//	    }
//	    printdersp(dersp);
//	}
 
 
 private ArrayList<Course> printCoursesWithConflict(CourseNode head, String day) {
	    ArrayList<Course> conflictList =new ArrayList<Course>();
	    CourseNode current = head;
	    while (current != null) {
	        if (current.data.day.equals(day)) {
	            System.out.println(current.data.courseName + " has conflict");
	            conflictList.add(current.data);
	        }
	        current = current.next;
	    }
	    
	   // System.out.println(conflictList);
	    return conflictList;
	}
 
 
 
 //2 TANE TEK SECTİONLU VARSA ALAMAZSIN-seçim yaptırmak //1 tane varsa dersp ekle
 public void checkAndPrintConflicts(BinaryTreeNode node,String day,ArrayList<Course>conflictList,ArrayList<Course>oneSectionList) {
	 ArrayList<Course> MoreThenoneSectionList =new ArrayList<Course>();
	 for(int i = 0; i < conflictList.size(); i++) {
		 if(conflictList.get(i).totalSections > 1) {
			 MoreThenoneSectionList.add(conflictList.get(i)); //day-conflict-fazla section
		 }
		 else {
			 oneSectionList.add(conflictList.get(i)); ///day-conflict-one section
		 }
			 }
	 	 
	 if(oneSectionList.size()==1) {
		 dersp = addSuggestionList(dersp, oneSectionList.get(0)); 
		 printdersp(dersp);
		 }
	 
	 else if (oneSectionList.size()>1) {
		 Scanner scanner = new Scanner(System.in);
		 System.out.println("You cant choose these lessons.Please choose one of the above lessons to add to your schedule: ");		
		 for(int i=0;i<oneSectionList.size();i++) {
				System.out.println(oneSectionList.get(i).courseName);
				
		 }      String choice=scanner.next();
		        System.out.println("your choıce is: "+choice);
		        System.out.println(oneSectionList.get(0).courseName);
		        
		        if (choice.equals(oneSectionList.get(0).courseName) ) {
		            dersp = addSuggestionList(dersp, oneSectionList.get(0));
		           
		        }
		        else if(choice .equals(oneSectionList.get(1).courseName)) {
		        	dersp = addSuggestionList(dersp, oneSectionList.get(1));
		           
		        }
		        else if(oneSectionList.size()>2 && choice.equals(oneSectionList.get(2).courseName)) {
		        	dersp = addSuggestionList(dersp, oneSectionList.get(2));
		           
		        }
		 }
	 System.out.println("-------------------- ");
	 System.out.println("Çakışan ve cok sectionlu olanlar: ");
	 for(int i=0;i<MoreThenoneSectionList.size();i++) {
		 try {
		 dersp = addSuggestionList(dersp, MoreThenoneSectionList.get(i));
		 }
		 catch(Exception e) {
			 System.out.println("Çakışma var eklenemedi "+ MoreThenoneSectionList.get(i).courseName +" - "+  MoreThenoneSectionList.get(i).sectionNumber );}
		 }
	 }
             
 
	 	

 private void printInOrderRecursive(BinaryTreeNode node) {
	    if (node != null) {
	        printInOrderRecursive(node.left);
	        System.out.println("Aynı saatte başlayan dersler linkedlisti:  " + node.data.startTime);
	        System.out.println(node.data.courseName);

	        CourseNode current = node.coursesWithSameStartTime.head;
	        while (current != null) {
	            System.out.println(current.data.courseName);
	            current = current.next;
	        }

	        int mondayCount = node.countCoursesByDay(node.data.startTime, "Monday");
	        int tuesdayCount = node.countCoursesByDay(node.data.startTime, "Tuesday");
	        int wednesdayCount = node.countCoursesByDay(node.data.startTime, "Wed");
	        int thursdayCount = node.countCoursesByDay(node.data.startTime, "Thurs");
	        int fridayCount = node.countCoursesByDay(node.data.startTime, "Friday");

	        if (mondayCount == 1) {
	            printCoursesWithNoConflict(node.coursesWithSameStartTime.head, "Monday");
	        }
	        if(mondayCount >= 2){
	        	ArrayList<Course> monday_conflictList =new ArrayList<Course>();
	        	monday_conflictList = printCoursesWithConflict(node.coursesWithSameStartTime.head, "Monday"); 	
	        	ArrayList<Course> monday_OneSectionList =new ArrayList<Course>();  
	        	checkAndPrintConflicts(node,"Monday",monday_conflictList,monday_OneSectionList);
	        }
	        
	        if (tuesdayCount == 1) {
	            printCoursesWithNoConflict(node.coursesWithSameStartTime.head, "Tuesday");
	        }
	        
	        if(tuesdayCount >= 2){
	        	ArrayList<Course> tuesday_conflictList =new ArrayList<Course>();
	        	tuesday_conflictList = printCoursesWithConflict(node.coursesWithSameStartTime.head, "Tuesday"); 	
	        	ArrayList<Course> tuesday_OneSectionList =new ArrayList<Course>();            
	        	checkAndPrintConflicts(node,"Tuesday",tuesday_conflictList,tuesday_OneSectionList);
	         }

	        if (wednesdayCount == 1) {
	            printCoursesWithNoConflict(node.coursesWithSameStartTime.head, "Wed");
	        }
	        if (wednesdayCount >= 2) {
	        	ArrayList<Course> wednesday_conflictList =new ArrayList<Course>();
	        	wednesday_conflictList = printCoursesWithConflict(node.coursesWithSameStartTime.head, "Wed"); 	
	        	ArrayList<Course> wednesday_OneSectionList =new ArrayList<Course>();            
	        	checkAndPrintConflicts(node,"Wednesday",wednesday_conflictList,wednesday_OneSectionList);
	        }

	        if (thursdayCount == 1) {
	        	printCoursesWithNoConflict(node.coursesWithSameStartTime.head, "Thurs");
	        
	        }
	        if (thursdayCount >= 2) {
	        	ArrayList<Course> thursday_conflictList =new ArrayList<Course>();
	            thursday_conflictList = printCoursesWithConflict(node.coursesWithSameStartTime.head, "Thurs"); 	
		        ArrayList<Course> thursday_OneSectionList =new ArrayList<Course>();            
		        checkAndPrintConflicts(node,"Thursday",thursday_conflictList,thursday_OneSectionList);
		    }

	        
	        if (fridayCount == 1) {
	            printCoursesWithNoConflict(node.coursesWithSameStartTime.head, "Friday");
	        }
	        if (fridayCount >= 2) {
	        	ArrayList<Course> friday_conflictList =new ArrayList<Course>();
	            friday_conflictList = printCoursesWithConflict(node.coursesWithSameStartTime.head, "Friday"); 	
		        ArrayList<Course> friday_OneSectionList =new ArrayList<Course>();            
		        checkAndPrintConflicts(node,"Friday",friday_conflictList,friday_OneSectionList);
	        }

	        System.out.println("----------- ");

	        printInOrderRecursive(node.right);
	    }
	}
    
}

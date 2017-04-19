package play.playSequence;

import org.omg.PortableServer.POA;

import java.util.Scanner;

/**
 * Created by jieluo on 2017-04-09.
 */
public class Sequence {

    Node head;
    Node discuss;

    public Sequence(){
        this.head = null;
    }
    void add(Node next){
        Node temp = head;
        if(head == null) {
            head = next;
        }else {
            while (head.next != null)
                head = head.next;
            head.add(next);
            head = temp;
        }
    }

    void repeatStep(){
        // everytime hit enter, it repeats one step.
        Node temp = head;
        Scanner in = new Scanner(System.in);
        while(head!=null) {
            System.out.println(head.x + ", " + head.y);
            String s = in.nextLine();
            if(s.equals("discuss")){
                // store the discuss point
                Node pointToDiscuss = head;

                // start discussion
                startDiscuss(pointToDiscuss);


                // end of discussion
                head = pointToDiscuss;
                head = head.next;
            }else {
                head = head.next;
            }
        }
    }

    void startDiscuss(Node pointToDiscuss){
        boolean keepDiscussing = true;
        while(keepDiscussing) {
            Scanner in = new Scanner(System.in);
            boolean endDiscuss = false;
            while (!endDiscuss) {
                // this part are auto adding discuss nodes.
                // will be improved to be like input 1,1
                System.out.println("Add a new node in discussion: ");
                head.discuss(new Node(-1, -1));
                head = head.discuss;
                head.discuss(new Node(-2, -2));
                head = head.discuss;
                String s = in.nextLine();
                if (s.equals("end")) { // clear this discussion
                    endDiscuss = true;
                }
            }

            while(pointToDiscuss.discuss != null){
                System.out.print(" -> (" + pointToDiscuss.discuss.x + ","+pointToDiscuss.discuss.y+")");
                pointToDiscuss = pointToDiscuss.discuss;
            }
            System.out.println();
            System.out.println("Do you want to keep discussing?");

            if(in.nextLine().equals("no"))
                keepDiscussing = false;
        }
    }

    public static void main(String[] args) {
        Sequence s = new Sequence();
        // this is the storage of every step
        s.add(new Node(0,0));
        s.add(new Node(1,1));
        s.add(new Node(2,2));
        s.add(new Node(3,3));
        s.add(new Node(4,4));

        s.printOriginalStep();
        System.out.println("Start repeating:");
        s.repeatStep();
    }


    void printOriginalStep(){
        Node temp = head;
        while(temp != null){
            System.out.println(temp.x + ", "+temp.y);
            System.out.println("|");
            temp = temp.next;
        }
    }
    void printEverything(){
        Node temp = head;
        while(temp!=null){
            System.out.print(temp.x + ", "+temp.y);
            if(temp.discuss!=null){
                Node point = temp;
                while(temp.discuss!= null){
                    System.out.print(" -> "+temp.discuss.x+", "+temp.discuss.y);
                    temp = temp.discuss;
                }
                temp = point;
            }
            System.out.println();
            System.out.println("|");
            temp = temp.next;
        }
    }
}

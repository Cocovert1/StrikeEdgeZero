import java.io.*;
import java.util.NoSuchElementException;

class CircularArrayQueue{
    private int front;
    private int rear;
    private int[] numArray;

    public CircularArrayQueue(int initSize){
        this.front = -1;
        this.rear = -1;
        this.numArray = new int[initSize];
    }

    public void enqueue(int num){
        if(isEmpty()){
            front++;
        }
        rear = (rear + 1) % numArray.length;
        numArray[rear] = num;
    }

    public int dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int temp = numArray[front];
        if(front == rear){
            front = -1;
            rear = -1;
        }else{
            numArray[front] = 0;
            front = (front + 1) % numArray.length;
        }
        return temp;
    }

    public int size(){
        //return ((numArray.length - front) + rear + 1) % numArray.length;
        return (front > rear ? (numArray.length - front + rear + 1) : (rear - front + 1));
    }

    public int front(){
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return numArray[front];
    }

    public boolean isEmpty(){
        return front == -1;
    }

    public String toString(){
        String s = "";
        for(int i : numArray){
            s = s + i + " ";
        }
        return s;
    }
}

public class Part2_B {
    public static void main(String[] args) throws IOException {
        int nbrofinputs = readnbrofinputs("Part2_A_in.txt");
        String input[] = new String[nbrofinputs];
        readinputs("Part2_A_in.txt", nbrofinputs, input);

        int arr[][] = new int[nbrofinputs][];

        //fill in the 2d array of integers
        for(int i = 0; i < nbrofinputs; i++){
            String tempstr[] = input[i].split(" ");
            int tempint[] = new int[tempstr.length];
            for(int k = 0; k < tempstr.length; k++){
                tempint[k] = Integer.parseInt(tempstr[k]);
            }
            arr[i] = tempint;
        }

        //prints the results in a .out txt file
        PrintWriter writer = new PrintWriter("Part2_B_out.txt");
        for(int i = 0; i < nbrofinputs; i++){
            int k = EdgeGame(arr[i], arr[i].length);
            writer.write("For game " + (i+1) + " the answer is " + k + "\n");
        }
        writer.close();
    }

    //finds the nbr of inputs ie. the first line
    public static int readnbrofinputs(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            int inputs = Integer.parseInt(line.trim());
            return inputs;
        }
        br.close();
        return 0;
    }

    //read the inputs
    public static void readinputs(String filename, int size, String input[]) throws IOException {
        //creates a buffered reader and reads the correct numbers
        BufferedReader br = new BufferedReader(new FileReader(filename));
        br.readLine();
        for(int i = 0; i < size; i++){
            String temp = br.readLine();
            input[i] = temp.substring(temp.indexOf(" 0")+3) + " 0";
        }
        br.close();
    }

    //will move us right in the queue
    public static void moveRight(CircularArrayQueue q1, CircularArrayQueue q2, int times){
        for(int i = 0; i < times; i++){
            int temp = q1.dequeue();
            q2.enqueue(temp);
        }
    }

    //will move us left in the queue
    public static void moveLeft(CircularArrayQueue q1, CircularArrayQueue q2){
        int temp1 = q1.size();
        int temp2 = q1.front();
        int temp3 = q2.size();

        for(int i = 0; i < temp1; i++){
            q2.enqueue(q1.dequeue());
        }
        for(int i = 0; i < temp3; i++){
            q1.enqueue(q2.dequeue());
        }
        for(int i = 0; i < temp1; i++){
            q1.enqueue(q2.dequeue());
        }
        for(int i = 0; i < temp3 - temp2; i++){
            q2.enqueue(q1.dequeue());
        }
    }

    //plays the striking edge zero game
    public static int EdgeGame(int arr[], int length){
        //creates the queues
        CircularArrayQueue q1 = new CircularArrayQueue(length);
        CircularArrayQueue q2 = new CircularArrayQueue(length);
        for(int i = 0; i < length; i++){
            q1.enqueue(arr[i]);
        }

        //loop that will allow us to iterate through the queues
        while(!q1.isEmpty()){

            //checks if we go out of bounds
            if(q1.front() >= length){
                return 0;
            }

            //checks if we can ONLY move right and moves right
            if((length - q1.size()) <= q1.front() && ((q1.size() - q1.front()) >= 0)){
                int temp = q1.front();
                moveRight(q1, q2, q1.front());

                if((temp == q1.front()) && ((q1.size() - q1.front()) < 0)){
                    return 0;
                }
            }

            //checks if we can ONLY move left and moves left
            if(((length - q1.size()) > q1.front()) && ((q1.size() - q1.front()) < 0)){
                moveLeft(q1, q2);
            }

            //checks if we can move BOTH left and rigth and enters the another loop
            if((length - q1.size()) >= q1.front() && (length - q1.front()) >= q1.size()){
                //store values of the start of our loop, the far right bound and the far left bound
                int originalsize = q1.size();
                int rightsize;
                int leftsize;
                boolean isloop = true;

                //goes rightmost and stores that value
                while(((q1.size() - q1.front()) >= 0)){
                    moveRight(q1, q2, q1.front());
                }
                rightsize = q1.size();

                //goes leftmost and stores that value
                while(((length - q1.size()) >= q1.front())){
                    moveLeft(q1, q2);
                }
                leftsize = q1.size();

                //goes into a loop to itterate through the queue
                while(isloop){
                    //tries to move right
                    if(((q1.size() - q1.front()) >= 0)){
                        int temp = q1.front();
                        moveRight(q1, q2, q1.front());

                        //checks if it is stuck in back and forth loop
                        if(temp == q1.front() && ((length - q1.size()) >= q1.front() && (length - q1.front()) < q1.size())){
                            return 0;
                        }

                        //checks if it reaches the end
                        if(q1.size() == 1){
                            return 1;
                        }
                    }

                    //checks if it can only move left and moves left
                    if((length - q1.size()) < q1.front() && (length - q1.front()) >= q1.size()){
                        moveLeft(q1, q2);
                    }

                    //checks if we have come back to the left side or the original starting point and moves right
                    if(q1.size() == leftsize || q1.size() == originalsize){
                        moveRight(q1, q2, q1.front());
                    }

                    //checks if we have reached a new value and moves from there
                    if(q1.size() != leftsize && q1.size() != originalsize && q1.size() != rightsize){
                        //while loop to try out that new value
                        while(q1.size() != rightsize){
                            //moves right as much as it can
                            if(!((q1.size() - q1.front()) == rightsize)){
                                moveRight(q1 ,q2, q1.front());
                            }
                            break;
                        }

                        //moves left and checks for new values to try going right again
                        while(q1.size() != leftsize){
                            //checks if it doesnt reach the original point and moves left and then right as much as it can
                            if(((q1.size() + q1.front()) != originalsize)){
                                moveLeft(q1, q2);
                                while(q1.size() != rightsize){
                                    moveRight(q1, q2, q1.front());
                                    if(q1.size() == 1){
                                        return 1;
                                    }
                                }
                                break;
                            }
                            moveLeft(q1 ,q2);
                        }
                    }
                }


            }
        }
        return 0;
    }
}


import java.io.*;

/*
This will solve the Striking Edge Zero game using recursion. It is O(n) since it at most run through the whole array
before stopping.
 */

public class Part2_A {
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

        PrintWriter writer = new PrintWriter("Part2_A_out.txt");
        for(int i = 0; i < nbrofinputs; i++){
            boolean temp = EdgeZero(arr[i], 0, 0, 0);
            int value = 0;
            if(temp == true){
                value = 1;
            }
            if(temp == false){
                value = 0;
            }
            writer.write("For game " + (i+1) + " the answer is " + value + "\n");
        }
        writer.close();

    }

    //find the nbr of inputs
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
        BufferedReader br = new BufferedReader(new FileReader(filename));
        br.readLine();
        for(int i = 0; i < size; i++){
            String temp = br.readLine();
            input[i] = temp.substring(temp.indexOf(" 0")+3) + " 0";
        }
        br.close();
    }

    private static boolean EdgeZero(int arr[], int position, int index, int count) {
        //validity check
        if(position<0 || position >=arr.length){
            return false;
        }

        //get the next left and right positions
        int left = position - arr[position];
        int right = position + arr[position];

        //check if has reached the 0
        if(right == arr.length-1){
            return true;
        }

        //checking if the values around it are 1
        if(arr[position] == 1 && (arr[right] == 1 || arr[left] == 1)){
            return EdgeZero(arr, right, index, count);
        }

        //checking if it goes back to the same step
        if(left >= 0 && arr[position] == arr[left]){
            return false;
        }

        //right side is out of bounds so we do the left side
        if(right >= arr.length && left >= 0){
            return EdgeZero(arr, left, index, count);
        }

        //right side because left side is out of bounds
        if(left < 0 && right < arr.length){
            return EdgeZero(arr, right, index, count);
        }

        //check both left and right
        if(left >= 0 && right <= arr.length){
            //store the index arr and if index == org ind, return false
            if(position == index){
                return false;
            }

            if(count == 0){
                index = position;
                count++;
            }

            if(EdgeZero(arr, left, index, count)){
                return true;
            }

            if(EdgeZero(arr, right, index, count)){
                return true;
            }
        }
        //returns false if nothing is found
        return false;
    }
}

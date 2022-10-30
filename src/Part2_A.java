import java.io.*;

public class Part2_A {
    public static void main(String[] args) throws IOException {
        int nbrofinputs = readnbrofinputs("file.txt");
        String input[] = new String[nbrofinputs];
        readinputs("file.txt", nbrofinputs, input);

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

        for(int i = 0; i < nbrofinputs; i++){
            System.out.println(EdgeZero(arr[i], 0));
        }
    }

    //find the nbr of inputs
    public static int readnbrofinputs(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
            while ((line = br.readLine()) != null) {
                int inputs = Integer.parseInt(line.trim());
                return inputs;
            }
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
    }

    private static boolean EdgeZero(int arr[], int position) {
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
            return EdgeZero(arr, right);
        }

        //checking if it goes back to the same step
        if(left >= 0 && arr[position] == arr[left]){
            return false;
        }

        //right side is out of bounds so we do the left side
        if(right >= arr.length && left >= 0){
            return EdgeZero(arr, left);
        }

        //right side because left side is out of bounds
        if(left < 0 && right < arr.length){
            return EdgeZero(arr, right);
        }

        //check both left and right
        if(left >= 0 && right <= arr.length){
            if(EdgeZero(arr, left)){
                return true;
            }
            if(EdgeZero(arr, right)){
                return true;
            }
        }

        //returns false if nothing is found
        return false;
    }
}

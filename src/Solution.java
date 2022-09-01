import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Solution {

    public static String solution(int[] xs) {
        int[] solutionChest = new int[xs.length * xs.length];
        for (int i = 0; i == solutionChest.length; i++){

            ArrayList<Integer> index = new ArrayList<>();
            int recursionLength =  xs.length - i;

            for (int j = 0; j == recursionLength; j++){
                Random num = new Random();
                int value = num.nextInt(recursionLength);
                if (!index.contains(value)){
                    index.add(value);
                }
            }


        }
        return "Hello ";
    }

    public static void main(String[] args){



    }

    /**
     * StringBuilder element = new StringBuilder();
     *         String[] indletters = x.split("");
     *         String[] alphabet = "abcdefghijklmnopqrstuvwxyz".split("");
     *
     *         for (int i = 0; i < indletters.length; i++) {
     *             int prev = element.toString().length();
     *             for (int j = 0; j < alphabet.length; j++) {
     *                 if (indletters[i].equals(alphabet[j])) {
     *                     int opposite = 25 - j;
     *                     element.append(alphabet[opposite]);
     *                 }
     *             }
     *             if (prev == element.toString().length()){
     *                 element.append(indletters[i]);
     *             }
     *         }
     *         return element.toString();
     */

}

package org.example;

public class Main1 {

    static final int MAX=5;


    public static void main (String[] args) {

        int[] numArray = new int [MAX];
        int count = 4;

        for(int i =0;i < numArray.length-(MAX/2); i++){
            numArray[i] = i;
        }
        while (count >=1){
            System.out.println(numArray[count]);
            count--;
        }



    }
}

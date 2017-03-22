package com.vinay.hashing;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

class HashingOperations{
    int maxSize,currentSize;
    public String vals[];
    public String keys[];
    double loadLimit;
    int primeNumber;
    
    HashingOperations(int size)
    {
        currentSize=0;
        maxSize=size;
        vals= new String[maxSize];
        keys= new String[maxSize];
    }
    
    public double getLoadFactor()
    {
        return (double)currentSize/(double)maxSize;
    }
    
    public int hash(String key)
    {
        return key.hashCode()%maxSize;
    }
    
    public void insertQuad(String key, String value)
    {
        int hashValue=hash(key);
        int tempi=hashValue,h=1;
        if(getLoadFactor()<=loadLimit)
        {
            do{
                if(vals[tempi]==null)
                {
                    currentSize++;
                    keys[hashValue]=key;
                    vals[hashValue]=value;
                    return;
                }
                tempi=(tempi+h*h++)%maxSize;
            }
            while(tempi!=hashValue);
        }
        else
        {
            String tempKey[]=keys;
            String tempVal[]=vals;
            clear();
            maxSize=maxSize*2;
            keys=new String[maxSize];
            vals=new String[maxSize];
            reHash(tempVal, tempKey, "quad");
            insertQuad(key, value);
        }
    }
    
    public void insertLinear(String key, String value)
    {
        try{
        int hashValue=hash(key);
        if(getLoadFactor()<=loadLimit)
        {
            if(vals[hashValue]==null)
            {
                currentSize++;
                keys[hashValue]=key;
                vals[hashValue]=value;
                return;
            }

            for(int i=0;i<maxSize;i++)
            {
                if(hashValue==maxSize-1)
                    hashValue=0;

                if(vals[hashValue]==null)
                {
                    currentSize++;
                    keys[hashValue]=key;
                    vals[hashValue]=value;
                    return;
                }
                hashValue+=1;
            }
        }
        else
        {
            String tempKey[]=keys;
            String tempVal[]=vals;
            clear();
            maxSize=maxSize*2;
            keys=new String[maxSize];
            vals=new String[maxSize];
            reHash(tempVal, tempKey, "linear");
            insertLinear(key, value);
        }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void insertLinearThreading(String key, String value)
    {
        try{
        int hashValue=hash(key);
        if(getLoadFactor()<=loadLimit)
        {
            if(vals[hashValue]==null)
            {
                currentSize++;
                keys[hashValue]=key;
                vals[hashValue]=value;
                return;
            }

            for(int i=0;i<maxSize;i++)
            {
                if(hashValue==maxSize-1)
                    hashValue=0;

                if(vals[hashValue]==null)
                {
                    currentSize++;
                    keys[hashValue]=key;
                    vals[hashValue]=value;
                    return;
                }
                hashValue+=1;
            }
        }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    
    public void reHash(String tempVal[],String tempKey[], String type)
    {
        int newHashKey;
        if(type.equals("linear"))
        for(int i=0;i<tempKey.length;i++)
        {
            if(tempKey[i]!=null)
                insertLinear(tempKey[i], tempVal[i]);
        }
        
        if(type.equals("quad"))
        for(int i=0;i<tempKey.length;i++)
        {
            if(tempKey[i]!=null)
                insertQuad(tempKey[i], tempVal[i]);
        }
        
        if(type.equals("double"))
        for(int i=0;i<tempKey.length;i++)
        {
            if(tempKey[i]!=null)
                insertDoubleHashing(tempKey[i], tempVal[i]);
        }
    }
    
    public void clear()
    {
        currentSize=0;
        keys= new String[0];
        vals=new String[0];
    }
    
    public boolean linearSearch(String key,String value)
    {
        int hashKey=hash(key);
        if(vals[hashKey].equals(value))
            return true;
        
        while(vals[hashKey]!=null)
        {
            hashKey+=1;
            if(vals[hashKey].equals(value))
                return true;
            
        }
        return false;
    }
    
    public void display()
    {
        for(int i=0;i<maxSize;i++)
        {
            System.out.println("Key :"+keys[i]+"  Value :"+vals[i]);
            
        }
    }
    
    public int getMaxSize()
    {
        return maxSize;
    }
    
    public int getCurrentSize()
    {
        return currentSize;
    }
         
    public int getPrime(int number)
    {
        for (int i = number - 1; i >= 1; i--)
        {
            int fact = 0;
            for (int j = 2; j <= (int) Math.sqrt(i); j++)
                if (i % j == 0)
                    fact++;
            if (fact == 0)
                return i;
        }
        /* Return a prime number */
        return 3;
    }
    public int hashTwoFunction(String key)
    {
     
        
        if(primeNumber-(key.hashCode()%primeNumber)>0)
            return primeNumber-(key.hashCode()%primeNumber);
        
        return primeNumber-(key.hashCode()%primeNumber)+maxSize;
            
    }
    public void insertDoubleHashing(String key, String value)
    {
        int hashValue=hash(key);
        if(getLoadFactor()<=loadLimit)
        {
            if(vals[hashValue]==null)
            {
                currentSize++;
                keys[hashValue]=key;
                vals[hashValue]=value;
                return;
            }
            int hops=0;
            int number=maxSize;
            int hashValue2=hashTwoFunction(key);
            if(vals[(int)hashValue2]==null)
                {
                    currentSize++;
                    vals[(int)hashValue2]=value;
                    keys[(int)hashValue2]=key;
                    return;
                }
            while(hops!=maxSize)
            {
                try{
                hops++;
                int finalHash = (hashValue+(hops*hashValue2))%maxSize;  
                if(finalHash<maxSize)
                if(vals[finalHash]==null)
                {
                    currentSize++;
                    vals[finalHash]=value;
                    keys[finalHash]=key;
                    return;
                }}
                catch(Exception e)
                {
                    e.getMessage();
                }
             
            }
            
        }
        else
        {
            String tempKey[]=keys;
            String tempVal[]=vals;
            clear();
            maxSize=maxSize*2;
            primeNumber=getPrime(maxSize);
            keys=new String[maxSize];
            vals=new String[maxSize];
            reHash(tempVal, tempKey, "double");
            insertDoubleHashing(key, value);
        }
    }
    
    
}

public class Hashing{
    static Thread t;
    static HashingOperations hash1;
    static HashingOperations hash2;
    public static void main(String args[])
    {
        Scanner s= new Scanner(System.in);
        
        System.out.println("Please enter your choice:\n1.Part 1\n2.Part 2");
        int choice=s.nextInt();
        Random rand= new Random();
        switch(choice)
        {
            case 1:
            
                    System.out.println("Enter the size of the Hash Table: ");
                    int inputNumber=s.nextInt();
                    HashingOperations ho = new HashingOperations(inputNumber);

                    

                    String inputKeys[]=new String[ho.maxSize];
                    String inputVals[]=new String[ho.maxSize];
                    ho.loadLimit=1.0;
                    ho.primeNumber = ho.getPrime(inputNumber);
                    for(int i=0;i<inputNumber;i++)
                    {

                        inputKeys[i]=String.valueOf(i+5);
                        inputVals[i]="Val-"+(i+5);

                    }

                    System.out.println("-------Linear Probing Insertion----------");
                    /*
                    //
                    // Linear Probing
                    //
                    */
                    long startTime=System.currentTimeMillis();
                    for(int i=0;i<inputNumber;i++)
                    {
                        ho.insertLinear(inputKeys[i], inputVals[i]);
                    }

                    long endTime=System.currentTimeMillis();
                    System.out.println("Start Time :"+startTime);
                    System.out.println("End Time :"+endTime);
                    double finalTime=endTime-startTime;
                    System.out.println("Time Elapsed for 0.1 Load factor is "+finalTime);
                    System.out.println("Current size: "+ho.getCurrentSize());
                    System.out.println("Max size: "+ho.getMaxSize());
                    System.out.println("Load Factor: "+ho.getLoadFactor());
                    //ho.display();


                    /*
                    //
                    // Linear Search
                    //
                    */
                    System.out.println("-------Linear Probing Search----------");


                    for(int i=0;i<5;i++)
                    {
                        startTime=System.currentTimeMillis();
                        int num=rand.nextInt(inputNumber);
                        System.out.println("Searching :"+inputKeys[num]+" | "+inputVals[num]);
                        System.out.println("Found :"+ho.linearSearch(inputKeys[num], inputVals[num]));
                        endTime=System.currentTimeMillis();
                    finalTime=endTime-startTime;
                    System.out.println("Time Elapsed for Searching is: "+finalTime+" ms");
                    }



            //        System.out.println("Current size: "+ho.getCurrentSize());
            //        System.out.println("Max size: "+ho.getMaxSize());
            //        System.out.println("Load Factor: "+ho.getLoadFactor());


                    /*
                    //
                    // Quadratic Probing
                    //
                    */
                    System.out.println("-------Quadratic Probing Insertion----------");
                    ho.clear();
                    ho.keys=new String[inputNumber];
                    ho.vals=new String[inputNumber];
                    ho.maxSize=inputNumber;
                    startTime=System.currentTimeMillis();
                    for(int i=0;i<inputNumber;i++)
                    {
                        ho.insertQuad(inputKeys[i], inputVals[i]);
                    }

                    endTime=System.currentTimeMillis();
                    System.out.println("Start Time :"+startTime);
                    System.out.println("End Time :"+endTime);
                    finalTime=endTime-startTime;
                    System.out.println("Time Elapsed for 0.1 Load factor is "+finalTime);
                    System.out.println("Current size: "+ho.getCurrentSize());
                    System.out.println("Max size: "+ho.getMaxSize());
                    System.out.println("Load Factor: "+ho.getLoadFactor());
                    //ho.display();



                    /*
                    //
                    // Double Hashing Probing
                    //
                    */
                    System.out.println("-------Double Hashing Probing Insertion----------");
                    ho.clear();
                    ho.keys=new String[inputNumber];
                    ho.vals=new String[inputNumber];
                    ho.maxSize=inputNumber;
                    startTime=System.currentTimeMillis();
                    for(int i=0;i<inputNumber;i++)
                    {
                        ho.insertDoubleHashing(inputKeys[i], inputVals[i]);
                    }


                    endTime=System.currentTimeMillis();
                    System.out.println("Start Time :"+startTime);
                    System.out.println("End Time :"+endTime);
                    finalTime=endTime-startTime;
                    System.out.println("Time Elapsed for 0.1 Load factor is "+finalTime);
                    System.out.println("Current size: "+ho.getCurrentSize());
                    System.out.println("Max size: "+ho.getMaxSize());
                    System.out.println("Load Factor: "+ho.getLoadFactor());
                    //ho.display();


                
            break;
            
            case 2:
                System.out.println("Please enter the size of initial hash table:");
                int inputSize = s.nextInt();
                hash1 = new HashingOperations(inputSize);
                hash2 = new HashingOperations(inputSize*3);
                String inputValue[]=new String[inputSize*2]; 
                String inputKey[]=new String[inputSize*2]; 
                for(int i=0;i<(inputSize*2);i++)
                {
                    inputKey[i]=String.valueOf(i);
                    inputValue[i]="Val-"+i;
                }
                hash2.loadLimit=1.0;
                hash1.loadLimit=0.5;
                int i;
                for(i=0;i<inputKey.length;i++)
                {
                    if(hash1.getLoadFactor()>0.5)
                    {
                        hash2.insertLinearThreading(inputKey[i], inputValue[i]);
                            System.out.println(inputKey[i]+" "+inputValue[i]);
                            start(inputSize);
                    }
                    if(hash1.getLoadFactor()<=0.5)
                    hash1.insertLinearThreading(inputKey[i],inputValue[i]);
                    
//                        while(i<inputKey.length)
//                        {
//                            
//                            
//                            i++;
//                            
//                        }
//                        break;
                    
                    try {         
                       t.sleep(500);
                   } catch (InterruptedException ex) {
                       System.out.println("Error in code");
                   }
                }
                System.out.println("------------------------------");
                hash1.display();
                System.out.println("------------------------------");
                hash2.display();
                
            break;
        }
        
        
        
//        while(true)
//        {
//            System.out.println("Menu:\n1.Insert\n2.Get Max and Current size and Load Factor\n3.Display\n4.Search Key and Value\n5.Exit");
//            int choice=s.nextInt();
//            switch(choice)
//            {
//                case 1:
//                    
//                    System.out.println("Enter Key and Value");
//                    s.nextLine();
//                    String input=s.nextLine();
//                    
//                    String keyvalue[]=input.split(" ");
//                    ho.insert(keyvalue[0], keyvalue[1]);
//                    break;
//                case 2:
//                    System.out.println("Current size: "+ho.getCurrentSize());
//                    System.out.println("Max size: "+ho.getMaxSize());
//                    System.out.println("Load Factor: "+ho.getLoadFactor());
//                    break;
//                case 3:
//                    ho.display();
//                    break;
//                    
//                case 4:
//                    System.out.println("Please enter the Key and Value to be searched:");
//                    s.nextLine();
//                    String inp=s.nextLine();
//                    String in[]=inp.split(" ");
//                    System.out.println(ho.search(in[0], in[1]));
//                    break;
//                case 5: 
//                    System.exit(0);
//                    
//                default:
//                    System.out.println("Please enter a valid input.");
//            }
//        }
        
        
    }
    public static void start(int inputSize)
    {
       for(int i=0; i<inputSize; i++){
           if(hash1.getLoadFactor()>0.1)
           {
      t=new Thread("T-"+i){
        public void run(){
            
          Random r=new Random();
          int num=r.nextInt(inputSize);
          if(hash1.keys[num]!=null)
          {
              String key=hash1.keys[num];
              String value=hash1.vals[num];
              hash1.keys[num]=null;
              hash1.vals[num]=null;
              hash1.currentSize--;
              hash2.insertLinearThreading(key, value);
              System.out.println("-- "+key+" "+value);
          }
          
t.interrupt();

        }
        
      };
           
      
      t.start();
           
           try {         
               t.sleep(500);
           } catch (InterruptedException ex) {
               System.out.println("Error in code");
           }
           }
    }
    
    }
    
}
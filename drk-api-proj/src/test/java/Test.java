/**
 * @author cropdata-ujwal
 * @package PACKAGE_NAME
 * @date 28/11/20
 * @time 10:13 AM
 */
public class Test
{
   /* public static void main(String[] args) {
        int[] a = {10,20,20,30,1,1,2,1,4};
        Set<Integer> hs = new LinkedHashSet<>();

        for (int i = 0;i<a.length ;i++ ) {
            hs.add(a[i]);
        }
        System.out.println(hs);
    }*/
 /*  public static void main(String[] args)
   {*/

       /*Integer[] a = {10,20,20,30,1,1,2,1,4};
       Arrays.sort(a); //{1,1,1,2,4,10,20,20,30}
       List<Integer> integerList1 = new ArrayList<>();
       List<Integer> integerList = new ArrayList<>(Arrays.asList(a));
       System.out.println(integerList.size());
       for (int i =0; i < integerList.size()-1; i++){

//           System.out.println("initial value of i -----> " + integerList.get(i) + "increment i ---> " + integerList.get(integerList.get(i + 1)));

               if (!(integerList.get(i).equals(integerList.get(i + 1))))
               {
                   integerList1.add(integerList.get(i));
           }
       }
       System.out.println( integerList1);*/

      /* int a = 1;
       int b = 0;
       int c = b/a;
       System.out.println(c);*/
    /*   String[] array = {"ABC", "2", "10", "0", "ABC", "2"};
       List<String> list = Arrays.asList(array);
       List<String> sortList = new ArrayList<>();
       Collections.sort(list);
       System.out.println(list);
       System.out.println(Arrays.toString(array));

       for (String arr: list){
           if(!sortList.contains(arr)){
               sortList.add(arr);
           }
       }
       System.out.println(sortList.toString());*/
      /* int number = 0;
       int reverse =0;
       int numCopy = 0;
       int counter1 = 0;
       int counter2 = 0;

       Scanner scan = new Scanner(System.in);
       System.out.println("Enter how many numbers you want to enter");
       int n = scan.nextInt();
       System.out.println("Enter "+n +" Elements");
       numCopy = n;

       String[] array = new String[n];

       for(int i = 0; i < n; i++)
       {

           array[i] = new Integer(scan.nextInt()).toString();
           String rev="";
           for(int k=array[i].length()-1;k>=0;k--){
               rev +=array[i].charAt(k);
           }
           if(array[i].equals(rev))
           {
               if(i==n-1/2){

               }else{
                   counter1 = counter1 + 1;
               }

           }
           else{
               counter2 = counter2 + 1;
           }

       }

       System.out.println(counter1);


       System.out.println(counter2);
   }*/

/*    static int m1(int n) {
        if(n==1 || n==2) {
            return 1;
        }
        return m1(n-1) + m1(n-2);

    }
    public static void main(String[] args) {

        int n = 12;

        for (int i =1;i<=n ;i++ ) {
            System.out.println(m1(i));
        }
    }*/

     /*void main()
    {
        System.out.println("Hello World");
    }

    // static void main(int a) {
    // 	System.out.println("Hello World111");
    // }


    static class M extends Test
    {

         void main()
        {
            System.out.println("Hello Goku");
        }
    }

   static class Instance
    {
        public static void main(String[] args)
        {

            Test i = new M();

            i.main();
        }*/
     static char m1() {
         int[] count = new int[256];
         int max = -1;
         char result = ' ';

         for (int i = 0; i < "Helloooooo".length() ; i ++ ) {

             count["Helloooooo".charAt(i)]++;
         }
         for (int i = 0; i < "Helloooooo".length() ; i ++ ) {

             if(max < count["Helloooooo".charAt(i)] ) {
                 max = count["Helloooooo".charAt(i)];
                 result = "Helloooooo".charAt(i);
             }
         }
         return result;
     }
    public static void main(String[] args) {
        System.out.println(m1());
        try
        {

            int i = 0/10;
        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally
        {
            System.out.println("test");
        }

    }
   /* static String sorted(String sss) {
        char[] arr = sss.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
    public static void main(String[] args) {
        String s = new String("TRIANGLE");
        String ss = new String("INTEGRAL");

        String s1 = sorted(s);
        String ss1 = sorted(ss);

        if(s1.equals(ss1)) {
            System.out.println("Strings are anagaram");
            System.out.println(s1);
            System.out.println(ss1);
        }else {
            System.out.println("Strings are not anagaram");
            System.out.println(s1);
            System.out.println(ss1);
        }
    }*/

   /* public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);
        String str = new String();
        str = scan.nextLine();
        int n = scan.nextInt();
        int[] arr = new int[n];

        for (int i=0;i<n ;i++ ) {
            arr[i] = scan.nextInt();
        }
        int length = removeDuplicate(arr,n);

        for (int i=0;i<length;i++ ) {
            System.out.print(arr[i]+" ");
        }

    }

    static public int removeDuplicate(int arr[],int n) {
        if(n==0||n==1) {
            return n;
        }
        int[] temp = new int[n];
        int k = 0;

        for (int i=0;i<n-1 ;i++ ) {
            if(arr[i]!=arr[i+1]) {
                temp[k++] = arr[i];
            }
        }
        temp[k++] = arr[n-1];

        for (int j=0;j<temp.length ;j++ ) {
            arr[j] = temp[j];
        }
        return k;
    }*/
    }

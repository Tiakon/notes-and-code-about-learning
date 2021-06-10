
public class b {

    public static void main(String[] args) {
        System.out.println("hello world!");
	try{
	Thread.sleep(5000);
	}
	catch(Exception e){}
        System.out.println(args[0]);
        System.out.println(args[1]);
    }

}

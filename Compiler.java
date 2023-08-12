import java.util.*;
import java.util.stream.*;
public class Compiler
{
    public static Scanner input = new Scanner(System.in);
    public static byte[] memory;
    public static String total;
    public static int pointer;
    public static final int maxPointer = 300000;
    public static String user;
    public static int inputPointer;
    public static int currentPrinted;
    public static long startTime;
    public static boolean timeOut;
	public static void main(String[] args) {
	   start();
	}
	public static void menu(){
	    System.out.println("1) Convert text to BrainF");
	    System.out.println("2) Compile BrainF ");
	    System.out.println("3) Toggle timeout (15 seconds) -> " + (timeOut ? "On" : "Off"));
	    System.out.print("Choose an option (1-3):: ");
	}
	public static void text(){
	    System.out.println("1) No Loops");
	    System.out.println("2) With Loops");
	    System.out.print("Choose an option (1/2):: ");
	}
	public static void start(){
	    do{
	    menu();
	    String chosen = input.nextLine().trim();
	    while(!chosen.equals("1")&&!chosen.equals("2")&&!chosen.equals("3")){
	        System.out.println("Invalid input!\n");
	        menu();
	        chosen = input.nextLine().trim();
	    }
	    if(chosen.equals("1")){
	        text();
	        chosen = input.nextLine().trim();
	        while(!chosen.equals("1")&&!chosen.equals("2")){
	        System.out.println("Invalid input!\n");
	        text();
	        chosen = input.nextLine().trim();
	    }
	    System.out.println("Enter the text to convert!");
	    String text = input.nextLine();
	    System.out.println();
	    System.out.println("::Conversion::");
	    System.out.println((chosen.equals("1")?basic(text):advanced(text))+"\n\n");
	    } else if (chosen.equals("2")){
	       brainF();
	    } else {
	        timeOut = !timeOut;
	        System.out.println("Toggled " + (timeOut ? "On" : "Off")+"\n");
	    }
	    
	    
	    }while(true);
	    
	}
	public static void brainF(){
	    System.out.println("\nEnter the brainF you want to compile! ( -1 to stop )");
	    String userBrainF = input.nextLine().trim().replaceAll("\\s","");
	    while(true){
	        String inp = input.nextLine().trim().replaceAll("\\s","");
	        if(inp.equals("-1")){
	            break;
	        } else {
	            userBrainF += inp;
	        }
	    }
	    if(valid(userBrainF)){
	    if(userBrainF.indexOf(",")!=-1){
	        System.out.print("Enter the stdin ( -1 to stop ):: ");
	        String stdin = input.nextLine();
	        while(true){
	        String inp = input.nextLine().trim().replaceAll("\\s","");
	        if(inp.equals("-1")){
	            break;
	        } else {
	            stdin += inp;
	        }
	        }
	        
	        int totalComma = (userBrainF.chars().map(s -> (char)s).filter(s -> s==',').reduce(0,(a,b)->(a+b)))/44;
	        if(stdin.length()>=totalComma)
	            reset(stdin);
	            
	    } else {
	        reset(null);
	    }
	    
	    System.out.println("OUPUT :: ");
	    startTime = System.currentTimeMillis();
	    compile(userBrainF.replaceAll("[^+\\-\\[\\]<>.,]",""));
	    if(currentPrinted<total.length()){
                System.out.print(total.substring(currentPrinted));
                currentPrinted = total.length();
            }
        System.out.println(total.length()==0?"Your BRAINF code ran with NO output!":"");
	    if(System.currentTimeMillis()>startTime+15000 && timeOut)System.out.println("Process Timed Out!");
	    } else 
	        System.out.println("INVALID BRAINF!");
	   System.out.println();
	   
	}
	public static boolean valid(String brainF){
	    int counter = 0;
	    for(char c : brainF.toCharArray()){
	        if(c=='['){
	            counter++;
	        }
	        if(c==']'){
	            counter--;
	        }
	        if(counter<0)return false;
	        
	    }
	    return true;
	}
	public static String basic(String text){
	    String total = "";
	    int previous = text.charAt(0);
	    total+="+".repeat(previous)+".";
	    for(int i = 1;i<text.length();i++){
	        int current = text.charAt(i);
	        if(previous>current){
	            total+="-".repeat(previous-current);
	        } else if(previous<current){
	            total+="+".repeat(current-previous);
	        }
	        previous = current;
	        total+=".";
	    }
	    return total;
	}
	public static String advanced(String text){
	    String total = "";
	    int previous = 0;
	    for(int i = 0;i<text.length();i++){
	        int current = Math.abs(text.charAt(i)-previous);
	        if(current<3){
	            if(text.charAt(i)>previous){
	                total+="+".repeat(current);
	            } else {
	                total+="-".repeat(current);
	            }
	        }else {
	        if(prime(current)){
	            
	            if(smallest(current+1)+(current+1)/smallest(current+1) > smallest(current-1)+(current-1)/smallest(current-1)){
	                if(text.charAt(i)>previous){
	                    int num1 = smallest(current-1);
	                    int num2 = (current-1)/smallest(current-1);
	                    total+=">"+"+".repeat(num1)+"[-<"+"+".repeat(num2)+">]<";
	                    total+="+";
	                } else {
	                    int num1 = smallest(current-1);
	                    int num2 = (current-1)/smallest(current-1);
	                    total+=">"+"+".repeat(num1)+"[-<"+"-".repeat(num2)+">]<";
	                    total+="-";
	                }
	            } else {
	                 if(text.charAt(i)>previous){
	                    int num1 = smallest(current+1);
	                    int num2 = (current+1)/smallest(current+1);
	                    
	                    total+=">"+"+".repeat(num1)+"[-<"+"+".repeat(num2)+">]<";
	                    total+="-";
	                } else {
	                    int num1 = smallest(current+1);
	                    int num2 = (current+1)/smallest(current+1);
	                    total+=">"+"+".repeat(num1)+"[-<"+"-".repeat(num2)+">]<";
	                    total+="+";
	                }
	            }
	                
	            } else {
	                
	                if(smallest(current+1)+(current+1)/smallest(current+1) > smallest(current-1)+(current-1)/smallest(current-1)){
	                    if(smallest(current)+current/smallest(current)>smallest(current-1)+(current-1)/smallest(current-1)){
	                if(text.charAt(i)>previous){
	                    int num1 = smallest(current-1);
	                    int num2 = (current-1)/smallest(current-1);
	                    
	                    total+=">"+"+".repeat(num1)+"[-<"+"+".repeat(num2)+">]<";
	                    total+="+";
	                } else {
	                    int num1 = smallest(current-1);
	                    int num2 = (current-1)/smallest(current-1);
	                    
	                    total+=">"+"+".repeat(num1)+"[-<"+"-".repeat(num2)+">]<";
	                    total+="-";
	                }
	                    } else {
	                         if(text.charAt(i)>previous){
	                    int num1 = smallest(current);
	                    int num2 = (current)/smallest(current);
	                    
	                    total+=">"+"+".repeat(num1)+"[-<"+"+".repeat(num2)+">]<";
	                    
	                } else {
	                    int num1 = smallest(current);
	                    int num2 = (current)/smallest(current);
	                    
	                    total+=">"+"+".repeat(num1)+"[-<"+"-".repeat(num2)+">]<";
	                    
	                }
	                    }
	            } else {
	                 if(smallest(current)+current/smallest(current)>smallest(current+1)+(current+1)/smallest(current+1)){
	                 if(text.charAt(i)>previous){
	                    
	                    int num1 = smallest(current+1);
	                    int num2 = (current+1)/smallest(current+1);
	                    
	                    total+=">"+"+".repeat(num1)+"[-<"+"+".repeat(num2)+">]<";
	                    total+="-";
	                } else {
	                    int num1 = smallest(current+1);
	                    int num2 = (current+1)/smallest(current+1);
	                    total+=">"+"+".repeat(num1)+"[-<"+"-".repeat(num2)+">]<";
	                    total+="+";
	                }
	                 } else {
	                     if(text.charAt(i)>previous){
	                    
	                    int num1 = smallest(current);
	                    int num2 = (current)/smallest(current);
	                    
	                    total+=">"+"+".repeat(num1)+"[-<"+"+".repeat(num2)+">]<";
	                    
	                } else {
	                    int num1 = smallest(current);
	                    int num2 = (current)/smallest(current);
	                    total+=">"+"+".repeat(num1)+"[-<"+"-".repeat(num2)+">]<";
	                    
	                }
	                 }
	            }
	                
	            }
	        }
	        total+=".";
	        previous = text.charAt(i);
	        
	    }
	    return total;
	    
	}
	public static boolean prime(int num){
	    for(int i = 2;i<=Math.sqrt(num);i++){
	        if(num%i==0)
	            return false;
	    }
	    return true;
	}
	public static int smallest(int num){
	    int small = num, large = num;
	    for(int i = 1;i<=Math.sqrt(num);i++){
	        if(num%i==0 && num/i +i < small+large){
	            small = i;
	            large = num/i;
	        }
	    }
	    return small;
	}
public static boolean reset(String input){
        total = "";
        pointer = 0;
        memory = new byte[300000];
        user = input==null?"":input;
        inputPointer = 0;
        currentPrinted = 0;
        return true;
    }
    public static void compile(String brainF){
        for(int i = 0;i<brainF.length();i++){
            if(currentPrinted<total.length()){
                System.out.print(total.substring(currentPrinted));
                currentPrinted = total.length();
            }
            char current = brainF.charAt(i);
            switch(current){
                case '>':
                    pointer++;
                    if(pointer==maxPointer)
                        pointer = 0;
                    break;
                case '<':
                    pointer--;
                    if(pointer==-1)
                        pointer = maxPointer - 1;
                    break;
                case '+':
                    memory[pointer]++;
                    break;
                case '-':
                    memory[pointer]--;
                    break;
                case '.':
                    total += (char)(int)memory[pointer];
                    break;
                case ',':
                    if(inputPointer==user.length())return;
                    int val  = user.charAt(inputPointer++);
                    memory[pointer] = Byte.valueOf("+"+val);
            }
            if(current == '['){
                String section = findLoop(brainF.substring(i));
                while(memory[pointer]!=0){
                    if(timeOut&&System.currentTimeMillis()>startTime+15000){
                             return;
                    }
                    compile(section.substring(1,section.length()-1));

                }
                i+=section.length()-1;
            }
        }
    }
    public static String findLoop(String brainF){
        int counter = 1;
        int index = 0;
        for(int i = 1;i<brainF.length();i++){
            counter+= brainF.charAt(i)=='[' ? 1 : brainF.charAt(i)== ']' ? -1 : 0;
            if(counter==0){
                index = i;
                break;
            }
        }
        return brainF.substring(0,index+1);
    }
}
	
	










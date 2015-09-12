public class Subset {

    public static void main(String[] args) {

        if(args.length > 0 && args[0].matches("\\d+")){
            RandomizedQueue<String> queue = new RandomizedQueue<>();

            int number = Integer.parseInt(args[0]);
            for (int i = 0; i < number; i++) {
                String string = StdIn.readString();
                queue.enqueue(string);
            }
            for (int i = 0; i < number; i++) {
                System.out.println(queue.dequeue());
            }
        }else{
            System.out.println("Wrong parameters");
        }
    }
}

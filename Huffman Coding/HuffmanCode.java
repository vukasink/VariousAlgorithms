import java.util.PriorityQueue;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Iterator;

/**
 * 
 * @author Vukasin Karadzic, vukasin.karadzic@gmail.com
 */
public class HuffmanCode {
    
    private PriorityQueue<LetterNode> queue;
    
    class LetterNode{
        
        char letter;        
        double probability;
        LetterNode left, right;
        String codeWord;
        
        LetterNode(char letter, double probability, LetterNode left, LetterNode right, String codeWord){
            this.letter = letter;           
            this.probability = probability;
            this.left = left;
            this.right = right;
            this.codeWord = codeWord;
        }
        
        void setLeftNode(LetterNode left){
            this.left = left; 
        }

        void setRightNode(LetterNode right){
            this.right = right; 
        }
        
        @Override
        public String toString(){
            return "Letter " + letter + " is encoded as: " + codeWord;
        }
        
    }
    
   /**
    * Constructor
    * @param inputFile name of the file, in which each line is made of a
    * letter (char) and its frequency (double)
    * @throws IOException 
    */
    public HuffmanCode(String inputFile) throws IOException{
        queue = new PriorityQueue<>(1, new ComparatorByProbability());
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line = br.readLine();
        while(line != null){
            String[] tokens = line.split(" ");
            char letter = tokens[0].charAt(0);
            double probability = Double.parseDouble(tokens[1]);
            queue.add(new LetterNode(letter, probability, null, null, ""));
            line = br.readLine();
        }
    }
    
   /**
    * This method encodes the letters in PriorityQueue queue.
    */
    public void encode(){
        LetterNode n1 = queue.poll();
        LetterNode n2 = queue.poll();
        while(n1 != null && n2 != null){
            LetterNode newNode = new LetterNode('.', n1.probability + n2.probability, n1, n2, "");
            queue.add(newNode);
            n1 = queue.poll();
            n2 = queue.poll();
        }
        LetterNode root = n1;
        root.left.codeWord += "0";
        root.right.codeWord += "1";
        encodeLetters(root.left);
        encodeLetters(root.right);
        queue.poll();
        fillQueue(root);
    }
    
   /**
    * Side method for method encode. Goes from root to the leafs, in each
    * step adds '0' or '1' to the codeword.
    * @param root 
    */
    private static void encodeLetters(LetterNode root){
        if(root.left != null){
            root.left.codeWord = root.codeWord + "0";
            root.right.codeWord = root.codeWord + "1";
            encodeLetters(root.left);
            encodeLetters(root.right);
        }
    }
    
   /**
    * Side method for method encode. Now that our leafs (that is letters)
    * encoded, we are going through the tree looking for leafs, and adding
    * them back to the PriorityQueue queue.
    * @param root 
    */
    private void fillQueue(LetterNode root){
        if(root.left == null && root.right == null){
            queue.add(new LetterNode(root.letter, root.probability, null, null, root.codeWord));
        }
        else{
            fillQueue(root.left);
            fillQueue(root.right);
        }
    }
    
   /**
    * Prints letters and their codewords.
    */
    public void printQueue(){
        System.out.println("Encoded letters:");
        Iterator itr = queue.iterator();
        while(itr.hasNext())
            System.out.println(itr.next());
    }
    
}

/**
 * @author Vukasin Karadzic, vukasin.karadzic@gmail.com
 */
class ComparatorByProbability implements Comparator<HuffmanCode.LetterNode>{

    @Override
    public int compare(HuffmanCode.LetterNode node1, HuffmanCode.LetterNode node2){
        if(node1.probability - node2.probability < 0)
            return -1;
        else if(node2.probability == node1.probability)
            return 0;
        else 
            return 1;
    }
    
}

package main;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;


/**
 *
 * @author user
 */

public class Cipher {
    
    public static String caesarEncode(String msg, int shift){
        
        shift = shift%26; 
        StringBuilder s =  new StringBuilder("");
        int len = msg.length();
        
        int c=0;
        char cx;
        
        for(int x = 0; x < len; x++){
                       
            cx = msg.charAt(x);
            c = (int)cx;
            
            if((cx >='a' && cx <='z') || (cx >='A' && cx <='Z')){
                
                if(Character.isLowerCase(cx))
                    c = (int)cx + shift - (int)'a';   
                else
                    c = (int)cx + shift - (int)'A';
                                
                if (c >= 26)
                    cx = (char)(cx - (26 - shift));
                else if (c < 0) 
                    cx = (char)(cx + (26 + shift));
                else
                    cx = (char)(cx + shift);
            }
                
            s.append(cx);
        }
        
        return s.toString();
    }
    
    public static String caesarDecode(String msg, int shift){
        return caesarEncode(msg, -shift);
    }

    private static String vigenereCipher(String msg, String key, CipherOperation so){
        
        StringBuilder s =  new StringBuilder("");
        
        int textLen = msg.length();
        int keyLen = key.length();
        int iKey=0;
        
        char ct;
        char ck;
        
        int ctShift=0;
        int ckShift=0;
        
        for(int iText = 0; iText < textLen; iText++){
            if(iKey >= keyLen-1)iKey=0;
            
            ct = msg.charAt(iText);
            ck = key.charAt(iKey);
            
            if(((ct >='a' && ct <='z') || (ct >='A' && ct <='Z'))&& 
               ((ck >='a' && ck <='z') || (ck >='A' && ck <='Z'))){
                
                if(Character.isLowerCase(ct))
                    ctShift = ct-'a'; 
                else 
                    ctShift = ct-'A';
                
                if(Character.isLowerCase(ck))
                    ckShift = ck-'a';
                else
                    ckShift = ck-'A';
                              
                if(Character.isLowerCase(ct))
                    if(so.equals(CipherOperation.ENCODE))
                        ct = (char)('a'+ ((char)(ctShift+ckShift)%26));
                    else
                        ct = (char)('a'+ ((char)(ctShift-ckShift+26)%26));
                else 
                    if(so.equals(CipherOperation.ENCODE))
                        ct = (char)('A'+ ((char)(ctShift+ckShift)%26));
                    else
                        ct = (char)('A'+ ((char)(ctShift-ckShift+26)%26));
            }
            s.append(ct);
            
            iKey++;
        }
        return s.toString();
    }
    
    public static String vigenereEncode(String msg, String key){

        return vigenereCipher(msg, key, CipherOperation.ENCODE);
    }   
    
    public static String vigenereDecode(String msg, String key){

        return vigenereCipher(msg, key, CipherOperation.DECODE);
    }  
    
    private enum CipherOperation {
        ENCODE, DECODE
    }
    
    //function returned frequencies of some text
    public static Map<Character,Double> getFrequenciesMap(String text) {
        
        int textLen = text.length();
         
        Map<Character,Integer> map = new LinkedHashMap<>();      
        for(int i = 0; i < 26; i++)
            map.put((char)('a'+i), 0);
        
        for(int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            
            if((c >='a' && c <='z') || (c >='A' && c <='Z')){
                
                c=Character.toLowerCase(c); 
                map.put(c,  map.get(c)+1);
            }
        }
        
        Map<Character,Double> frequenciesMap = new LinkedHashMap<>();    
         
        for(Character c: map.keySet()) 
            frequenciesMap.put(c, 100*(double)map.get(c)/textLen);
        
                
        return frequenciesMap;
    }
    
    //function returned sorted of some map
    public static <K, V extends Comparable>  
        List<Map.Entry<K, V>>  getSortedEntries(Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =new ArrayList<>(map.entrySet());
        Collections.sort(list, 
                (Map.Entry<K, V> o1, Map.Entry<K, V> o2) -> 
                (o2.getValue()).compareTo( o1.getValue() ));

        return list;
    }


    //frequency analasys method
    private static String frequencyAnalasys(String msg, boolean isCaesar){
        
        char[] alphabetList = {'e','t','a','o','i','n','s','h',
        'r','d','l','c','u','m','w','f','g','y','p','b','v','k','j','x','q','z'};
        
        Map<Character,Double> inputFreq = getFrequenciesMap(msg);
        List<Map.Entry<Character, Double>> inputList = getSortedEntries(inputFreq);
        
        
        if(isCaesar){
            
            Character maxFreqChar = inputList.get(0).getKey();
            int caesarShift = maxFreqChar-alphabetList[0];
            JOptionPane.showMessageDialog(null, "Key(shift) to caesar decode:"+caesarShift);
            
            return caesarDecode(msg,caesarShift);
        }
        
        Map<Character,Character> compareMap = new LinkedHashMap<>();
        
        for(int i=0; i<26; i++)
            compareMap.put(inputList.get(i).getKey(),alphabetList[i]);
       
        StringBuilder s =  new StringBuilder("");
        
        int len = msg.length();
        
        char c;
        boolean upperCase;
        
        
        for(int i = 0; i < len; i++){
            
            c = msg.charAt(i);
            if((c >='a' && c <='z') || (c >='A' && c <='Z')){
                
                upperCase = Character.isUpperCase(c);
                 
                if(upperCase)c=Character.toLowerCase(c);
                c = compareMap.get(c);
                if(upperCase)c= Character.toUpperCase(c);
                
            }
            s.append(c);
        }
       
        return s.toString();
    }
    
    //common frequency analasys replace input set of character with alphabet
    public static String freqAnalasys(String msg){
        
        return frequencyAnalasys(msg, false);
    }
    
    //caesar frequency analasys finds characters shift
    public static String freqAnalasysCaesar(String msg){
        
        return frequencyAnalasys(msg, true);
    }
    
    
    public static String format(String text) {
	return text.toUpperCase().replaceAll("[^A-Z]", "");
    }
        
    public static int[] letterFrequency(String text) {
	int[] frequencies = new int[26];

	text = format(text);
        System.out.println(text);
	for (int i = 0; i < text.length(); i++) {
            frequencies[text.charAt(i) - 'A']++;
          
	}

	return frequencies;
    }

    public static double calcIC(String text) {
	return calcIC(letterFrequency(text));
    }

    public static double calcIC(int[] frequencies) {
	double ic = 0;
	int sum = 0;
	for (int i = 0; i < frequencies.length; i++) {
            sum += frequencies[i];
	}

	for (int i = 0; i < frequencies.length; i++) {
            double top = frequencies[i] * (frequencies[i] - 1);
            double bottom = sum * (sum - 1);
            ic += top / bottom;
	}
	return ic;
    }

    public static double estimateKeyLength(String text) {
	double ic = calcIC(text);
	double top = 0.027 * text.length();
	double bottom = (text.length() - 1) * ic - 0.038 * text.length()
				+ 0.065;
	return top / bottom;
    }
}

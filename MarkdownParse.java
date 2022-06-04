//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        boolean flag = true;
        while(currentIndex < markdown.length()) {
            flag = true;
            int openBracket = markdown.indexOf("[", currentIndex);
            int closeBracket = markdown.indexOf("]", openBracket);
            int secondcloseBracket = markdown.indexOf("]", closeBracket+1); 
            int openParen = markdown.indexOf("(", closeBracket);
            int closeParen = markdown.indexOf(")", openParen);
            int secondCloseParen = markdown.indexOf(")", closeParen+1);
            while(markdown.charAt(closeParen+1) == ')'){
                closeParen++;
            }
            while(markdown.charAt(closeBracket+1) == ']'){
                closeBracket++;
            }

            if (secondcloseBracket == openParen - 1){
                closeBracket = secondcloseBracket;
            }

            if( !(openBracket!=-1 && closeBracket!=-1 && openParen!=-1 && closeParen!=-1) ) break;
            if(openBracket >= 1) {
                char chkEM = markdown.charAt(openBracket-1);
                if((chkEM=='!')) flag=false;
                if((chkEM=='`')) flag=false;
            }
            if(openParen-closeBracket!=1) flag = false;
            if(closeParen-openParen==1) flag = false;
            if(closeBracket-openBracket==1) flag = false;

            if(flag) {
                toReturn.add(markdown.substring(openParen + 1, closeParen));
            }
            currentIndex = closeParen + 1;
        }

        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);   
    }

 
    //adding a line for commit on ieng6 using vim
    //deleted an extra key
}

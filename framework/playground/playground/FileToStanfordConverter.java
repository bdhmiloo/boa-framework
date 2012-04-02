package playground;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.io.File;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;

public class FileToStanfordConverter {

	private Reader stringReader;
    private DocumentPreprocessor preprocessor;
    private StringBuilder stringBuilder;
    private String ausgabe;
	
	public FileToStanfordConverter()
	{
		
	}
	
    
	public String FileToString(String FilePath)
	{
		File file = new File(FilePath);
		String text = "";
		int nextchar;
		
		try
		{
			FileReader reader = new FileReader(file);
			do
			{
				nextchar = reader.read();
				if(nextchar!=-1) text+= (char)(nextchar);

			}while(nextchar!= -1);
			
    		while(text.startsWith("\n"))
    		{
    			text = text.substring(1);
    		}			
			
			reader.close();		
			return text;
		}
		catch(FileNotFoundException eFile)
		{
			System.out.println(eFile);
		}
		catch(IOException eIO)
		{
			System.out.println(eIO);
		}
		
		
		return "";
	}
    
	
	public void deleteSentence(String sentence, String FilePath)
	{
		File file = new File(FilePath);
		String removedSentence, text, newText;
		
		try
		{
			text = FileToString(FilePath);
			removedSentence = nextSentence(text);
			newText = text.replace(removedSentence, "");
			
			file.delete();
			
			File tempFile = new File(FilePath);
			FileWriter writer = new FileWriter(tempFile);
			writer.write(newText);
			
			writer.close();			
		}
		catch(FileNotFoundException eFile)
		{
			System.out.println(eFile);
		}
		catch(IOException eIO)
		{
			System.out.println(eIO);
		}
	}
	
       
    public String nextSentence(String text)
    {
    	try
    	{
    		String sentence="";
    		int i =0;
    		
    		while(text.charAt(i)!='\n')
    		{
    			sentence+=text.charAt(i);
    			i++;

    			if(i == text.length()) break;
    		}
    	
           return sentence;
    	}
    	catch(ArrayIndexOutOfBoundsException eA)
    	{
    			
    	}
    
    	return "";
    }

}

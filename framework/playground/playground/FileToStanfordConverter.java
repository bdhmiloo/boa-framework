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
			removedSentence = segmentString(text);
			newText = text.replace(removedSentence, "");
			if(!newText.equals("")&&!newText.startsWith(" ")) newText = newText.substring(2);
			
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
	
    /**
     * TODO export to library
     * 
     * @param sentence
     * @return
     */
       
    public String segmentString(String sentence)
    {
            
            try {
                    
                    this.stringReader = new StringReader(sentence);
                    this.preprocessor = new DocumentPreprocessor(stringReader,  DocumentPreprocessor.DocType.Plain);
                    String text="";
                    boolean firstword = true;
                    
                    HashSet<String> set = new HashSet<String>();
                    set.add(".");
                    set.add("!");
                    set.add("?");
                    set.add(",");
                    
                    Iterator<List<HasWord>> iter = this.preprocessor.iterator();
                    while ( iter.hasNext() ) {
                            
                            for ( HasWord word : iter.next() ) {
                                    if(!firstword && !set.contains(word.toString()))text+= " " + word.toString();
                                    else 
                                    {
                                    	text+= word.toString();
                                    	firstword = false;
                                    }
                            }
                            return text.trim();
                    }
            }
            catch (ArrayIndexOutOfBoundsException aioobe) {
                    
                   // logger.debug("Could not segment string...", aioobe);
            }
            return "";
    }


}

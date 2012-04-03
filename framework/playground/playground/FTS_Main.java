package playground;
import java.util.ArrayList;
import java.util.Iterator;


public class FTS_Main {
	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args)
	{
			/*FileToStanfordConverter converter = new FileToStanfordConverter();
		
			System.out.println("TEST:\n");
			
			String ausgabe = converter.FileToString("c:/users/Bosthorst/Java/test.txt");
			
			System.out.println(ausgabe);
			
			ausgabe = converter.nextSentence(ausgabe);
			
			System.out.println(ausgabe);
			
			converter.sentenceToXML(ausgabe, "c:/users/Bosthorst/Java/test.xml");
			
			converter.deleteSentence(ausgabe, "c:/users/Bosthorst/Java/test.txt");
			
			ausgabe = converter.FileToString("c:/users/Bosthorst/Java/test.txt");
			
			System.out.println(ausgabe);*/
		
		SentenceServer serv = new SentenceServer();
		ArrayList<String> myList = new ArrayList<String>();
		
		myList = serv.getSentence();
		
		Iterator<String> it = myList.iterator();
		
		while(it.hasNext())
		{
			System.out.print(it.next()+" ");
		}
		
		ArrayList<playground.Annotation> myAnno  = new ArrayList<playground.Annotation>();
		serv.returnSentence(myList,myAnno);
	}

}

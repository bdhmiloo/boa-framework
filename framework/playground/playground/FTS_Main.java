package playground;

public class FTS_Main {
	
	
	public static void main(String[] args)
	{
			FileToStanfordConverter converter = new FileToStanfordConverter();
		
			System.out.println("TEST:\n");
			
			String ausgabe = converter.FileToString("c:/users/Bosthorst/Java/test.txt");
			
			System.out.println("\n"+ausgabe);
			
			ausgabe = converter.nextSentence(ausgabe);
			
			System.out.println("\n"+ausgabe);
			
			converter.deleteSentence(ausgabe, "c:/users/Bosthorst/Java/test.txt");
			
			ausgabe = converter.FileToString("c:/users/Bosthorst/Java/test.txt");
			
			System.out.println("\n"+ausgabe);
		
	}

}

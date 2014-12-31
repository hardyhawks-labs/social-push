package com.getfbstuff.socialpublish.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;

public class Utility {
	
	Set<String> stopWords = new HashSet<String>();
	Set<String> adultWord = new HashSet<String>();
	List<String> randomMessage = new ArrayList<String>(); 
	
	public Utility() {
		stopWords.addAll(Arrays.asList(new String[]{"without", "see", "unless", "due", "also", "must", "might", "like", "]", "[", "}", "{", "<", ">", "?", "\"", "\\", "/", ")", "(", "will", "may", "can", "much", "every", "the", "in", "other", "this", "the", "many", "any", "an", "or", "for", "in", "an", "an ", "is", "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren’t", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can’t", "cannot", "could",
				"couldn’t", "did", "didn’t", "do", "does", "doesn’t", "doing", "don’t", "down", "during", "each", "few", "for", "from", "further", "had", "hadn’t", "has", "hasn’t", "have", "haven’t", "having",
				"he", "he’d", "he’ll", "he’s", "her", "here", "here’s", "hers", "herself", "him", "himself", "his", "how", "how’s", "i ", " i", "i’d", "i’ll", "i’m", "i’ve", "if", "in", "into", "is",
				"isn’t", "it", "it’s", "its", "itself", "let’s", "me", "more", "most", "mustn’t", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "ought", "our", "ours", "ourselves",
				"out", "over", "own", "same", "shan’t", "she", "she’d", "she’ll", "she’s", "should", "shouldn’t", "so", "some", "such", "than",
				"that", "that’s", "their", "theirs", "them", "themselves", "then", "there", "there’s", "these", "they", "they’d", "they’ll", "they’re", "they’ve",
				"this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn’t", "we", "we’d", "we’ll", "we’re", "we’ve",
				"were", "weren’t", "what", "what’s", "when", "when’s", "where", "where’s", "which", "while", "who", "who’s", "whom",
				"why", "why’s", "with", "won’t", "would", "wouldn’t", "you", "you’d", "you’ll", "you’re", "you’ve", "your", "yours", "yourself", "yourselves",
				"Without", "See", "Unless", "Due", "Also", "Must", "Might", "Like", "Will", "May", "Can", "Much", "Every", "The", "In", "Other", "This", "The", "Many", "Any", "An", "Or", "For", "In", "An", "An ", "Is", "A", "About", "Above", "After", "Again", "Against", "All", "Am", "An", "And", "Any", "Are", "Aren’t", "As", "At", "Be", "Because", "Been", "Before", "Being", "Below", "Between", "Both", "But", "By", "Can’t", "Cannot", "Could",
				"Couldn’t", "Did", "Didn’t", "Do", "Does", "Doesn’t", "Doing", "Don’t", "Down", "During", "Each", "Few", "For", "From", "Further", "Had", "Hadn’t", "Has", "Hasn’t", "Have", "Haven’t", "Having",
				"He", "He’d", "He’ll", "He’s", "Her", "Here", "Here’s", "Hers", "Herself", "Him", "Himself", "His", "How", "How’s", "I ", " I", "I’d", "I’ll", "I’m", "I’ve", "If", "In", "Into", "Is",
				"Isn’t", "It", "It’s", "Its", "Itself", "Let’s", "Me", "More", "Most", "Mustn’t", "My", "Myself", "No", "Nor", "Not", "Of", "Off", "On", "Once", "Only", "Ought", "Our", "Ours", "Ourselves",
				"Out", "Over", "Own", "Same", "Shan’t", "She", "She’d", "She’ll", "She’s", "Should", "Shouldn’t", "So", "Some", "Such", "Than",
				"That", "That’s", "Their", "Theirs", "Them", "Themselves", "Then", "There", "There’s", "These", "They", "They’d", "They’ll", "They’re", "They’ve",
				"This", "Those", "Through", "To", "Too", "Under", "Until", "Up", "Very", "Was", "Wasn’t", "We", "We’d", "We’ll", "We’re", "We’ve",
				"Were", "Weren’t", "What", "What’s", "When", "When’s", "Where", "Where’s", "Which", "While", "Who", "Who’s", "Whom",
				"Why", "Why’s", "With", "Won’t", "Would", "Wouldn’t", "You", "You’d", "You’ll", "You’re", "You’ve", "Your", "Yours", "Yourself", "Yourselves"}));
		
		adultWord.addAll(Arrays.asList(new String[]{"porn","mms","sex","pornography","fuck","fucking","brazzen","3some","anal","asphyxiation",
				"bdsm","blowjob","crothel","callgirl","cannibal","cannibalism","cock","clit","dildo","gangbang","hardcore","porno","xxx","xxxx",
				"threesome"}));
		randomMessage.addAll(Arrays.asList(new String[]{"You will probably love this!", "Intersting video","This is crazy"}));
	}
	
	public String removeStopWord(String message) {
		String stopWordFree = null;
		//use a linked list to make removal faster, you don't need random access here
		List<String> text = new LinkedList<String>(); 
		//tokenize string into words
		String[] result = message.split("\\s");
		
		//fill text
        text.addAll(Arrays.asList(result));

		Iterator<String> textIterator = text.iterator();
		while( textIterator.hasNext() ) {
		  //this assumes there are no null entries in the list       
		  //and all stopwords are stored in lower case
		  if( stopWords.contains( textIterator.next().toLowerCase() )) {
		    textIterator.remove();
		  }
		  
		}
		stopWordFree = Joiner.on(" ").join(text);
		return stopWordFree;
	}
	
	public String removeAdultWord(String message) {
		String adultWordFree = null;
		//use a linked list to make removal faster, you don't need random access here
		List<String> text = new LinkedList<String>(); 
		//tokenize string into words
		String[] result = message.split("\\s");
		
		//fill text
        text.addAll(Arrays.asList(result));

		Iterator<String> textIterator = text.iterator();
		while( textIterator.hasNext() ) {
		  //this assumes there are no null entries in the list       
		  //and all stopwords are stored in lower case
		  if( adultWord.contains( textIterator.next().toLowerCase() )) {
		    textIterator.remove();
		  }
		  
		}
		adultWordFree = Joiner.on(" ").join(text);
		return adultWordFree;
	}
	
	
	public String removeLink(String message) {
		String urlValidationRegex = "(https?|ftp)://(www\\d?|[a-zA-Z0-9]+)?.[a-zA-Z0-9-]+(\\:|.)([a-zA-Z0-9.]+|(\\d+)?)([/?:].*)?";
		Pattern p = Pattern.compile(urlValidationRegex);
		Matcher m = p.matcher(message);
		StringBuffer sb = new StringBuffer();
		while(m.find()){
		    String found =m.group(0); 
		    m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public String getRandomMessage(){
		 	Random rand = new Random();
		 	int max = randomMessage.size()-1;
		 	int min = 0;
		    // nextInt is normally exclusive of the top value,
		    // so add 1 to make it inclusive
		    int randomNum = rand.nextInt((max - min) + 1) + min;

		    return randomMessage.get(randomNum);
	}
}

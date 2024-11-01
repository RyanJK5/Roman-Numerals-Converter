package romannumeralsconverter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RomanNumeralConverter
{
    @SuppressWarnings("unchecked")
	private static final LinkedHashMap<Character, Integer> ROMAN_NUMERALS = (LinkedHashMap<Character, Integer>) makeRomanNumeralLinkedHashMap(false);
    @SuppressWarnings("unchecked")
    private static final LinkedHashMap<Integer, Character> REVERSE_ROMAN_NUMERALS = (LinkedHashMap<Integer, Character>) makeRomanNumeralLinkedHashMap(true);
    private static final int MAX_REPEATED_SYMBOL_LENGTH = 3;
    
	// takes in whether or not the hashmap should have ints or chars in the first column
	// true if chars, false if ints
	// very type unsafe because of this, but it's the most efficient here and it won't
	// be released to anyone anyway
    private static LinkedHashMap<?, ?> makeRomanNumeralLinkedHashMap(boolean reverse)
    {
    	// add entries to hash map in **ASCENDING ORDER**
    	LinkedHashMap<Character, Integer> result = new LinkedHashMap<>();
    	LinkedHashMap<Integer, Character> result2 = new LinkedHashMap<>();
    	
    	if(reverse)
    	{
	    	result2.put(1,'I');
	    	result2.put(5,'V');
	    	result2.put(10,'X');
	    	result2.put(50,'L');
	    	result2.put(100,'C');
	    	result2.put(500,'D');
	    	result2.put(1000,'M');
	    	return result2;
    	}
        result.put('I',1);
        result.put('V',5);
        result.put('X',10);
        result.put('L',50);
        result.put('C',100);
        result.put('D',500);
        result.put('M',1000);
        return result;
    }

    // loops through entry and LinkedHashMap, if one of the characters of 
    // the entry is not in the LinkedHashMap, it is not a valid entry
    private static boolean isRomanNumerals(String entry)
    {
        for(int i = 0; i < entry.length(); i++)
        {
        	boolean successful = false;
            for(char key : ROMAN_NUMERALS.keySet())
            {
                if(entry.charAt(i) == key)
                {
                	successful = true;
                	break;
                }
            }
            
            if(!successful)
            {
            	return false;
            }
        }
        return true;
    }
    
    // Takes in a long and returns a string representing a Roman numeral
    // please don't read through this algorithm it will make your eyes bleed
    // Takes in a long value and returns a string representation of that
    // value as Roman numerals
    // it's a long for consistency with convertToNumber
    // don't look through this algorithm it will make your eyes bleed
    public static String convertToRomanNumeral(long entry)
    {
    	if(entry < 0 || entry > 3999999)
    	{
    		return "-3";
    	}
    	String result = "";
    	
    	// break the number into an array of values that match ROMAN_NUMERALS
    	ArrayList<Integer> valueList = new ArrayList<>();
    	
    	for(int i = ROMAN_NUMERALS.size() - 1; i >= 0; i--)
    	{
    		int currentValue = (int) ROMAN_NUMERALS.values().toArray()[i];
    		
    		while(entry - currentValue >= 0)
    		{
    			entry -= currentValue;
    			valueList.add(currentValue);
    		}
    	}
    	//MDCCCCLXXXXVIIII
    	//MCCXLXVIVIII
    	//MCMXCIX
    	
    	
    	for(int i = ROMAN_NUMERALS.size() - 2; i >= 0; i--)
    	{
    		ArrayList<Integer> dupeList = new ArrayList<>();
    		for(int j = 0; j < valueList.size(); j++)
    		{
    			dupeList.add(valueList.get(j));
    		}
    		
    		Integer currentValue = (Integer) ROMAN_NUMERALS.values().toArray()[i];
    		
    		boolean multipleValues = true;
    		for(int j = 0; j <= MAX_REPEATED_SYMBOL_LENGTH; j++)
    		{
    			if(dupeList.contains(currentValue))
    			{
    				dupeList.remove(currentValue);
    			}
    			else
    			{
    				multipleValues = false;
    				break;
    			}
    		}
    		
    		if(multipleValues)
    		{
    			if(i % 2 == 0)
    			{
    				for(int j = 0; j < MAX_REPEATED_SYMBOL_LENGTH; j++)
        			{
        				valueList.remove(valueList.indexOf(currentValue));
        			}
        			valueList.add(valueList.indexOf(currentValue) + 1, 
    						(int) ROMAN_NUMERALS.values().toArray()[i + 1]);
    			}
    			else
    			{
    				for(int j = 0; j <= MAX_REPEATED_SYMBOL_LENGTH; j++)
        			{
        				valueList.remove(i + 1);
        			}
    				
    				for(int j = 0; j < 2; j++)
    				{
    					valueList.add(valueList.indexOf(currentValue) + 1, 
        						(int) ROMAN_NUMERALS.values().toArray()[i + 1]);
    				}
    			}
    		}
    	}
    	
    	// DCD -> CM
    	for(int i = 1; i < valueList.size() - 1; i++)
    	{
    		Integer last = valueList.get(i - 1);
    		Integer current = valueList.get(i);
    		Integer next = valueList.get(i + 1);
    		Object[] romanArray = (Object[]) ROMAN_NUMERALS.values().toArray();
    		Integer upNext = 0;
    		
    		if(last.equals(next) && !current.equals(last))
    		{
    			for(int j = 0; j < romanArray.length; j++)
        		{
        			if(romanArray[j].equals(next))
        			{
        				upNext = (Integer) romanArray[j + 1];
        				break;
        			}
        		}
    			valueList.remove(last);
    			valueList.set(valueList.indexOf(next), 
    					upNext);
    		}
    	}

    	for(int i : valueList)
    	{
    		result += REVERSE_ROMAN_NUMERALS.get(i);
    	}

    	return result;
    }
    
    
    // Takes in a string of Roman numerals and returns that in actual numbers
    // this one isn't as bad as convertToRomanNumerals but you should probably
    // stay away from it if you want to avoid cataracts
    public static long convertToNumber(String entry)
    {
    	if(!isRomanNumerals(entry))
    	{
    		return -2;
    	}
    	
    	entry += " ";
    	// seperate the number into terms ex. IV, XL, VII
    	LinkedHashMap<String, Integer> terms = new LinkedHashMap<>();
    	for(int i = 0; i < entry.length() - 1; i++)
    	{
    		char current = entry.charAt(i);
    		char next = entry.charAt(i + 1);
    		
			if(ROMAN_NUMERALS.get(next) != null &&
			   ROMAN_NUMERALS.get(next) - ROMAN_NUMERALS.get(current) != ROMAN_NUMERALS.get(current))
    		{
				if(ROMAN_NUMERALS.get(current) < ROMAN_NUMERALS.get(next))
				{
					terms.put(entry.substring(i, i + 2),
				   			  ROMAN_NUMERALS.get(next) - ROMAN_NUMERALS.get(current));
					i++;
					continue;
				}
				else if(ROMAN_NUMERALS.get(current) == ROMAN_NUMERALS.get(next))
				{
					String repeatedResult = Character.toString(current);
					int repeatedIntResult = ROMAN_NUMERALS.get(current);
					int totalMoved = 0;
					for(int j = i + 1; j < entry.length(); j++)
					{
						if(entry.charAt(j) != current)
						{
							break;
						}
						repeatedResult += entry.charAt(j);
						repeatedIntResult += ROMAN_NUMERALS.get(entry.charAt(j));
						totalMoved++;
					}
					terms.put(repeatedResult, 
							  repeatedIntResult);
					i += totalMoved;
					continue;
				}
			} 
			terms.put(Character.toString(current), ROMAN_NUMERALS.get(current));
    	}
    	
    	// check for invalid roman numeral syntax and add all values together
    	int lastValue = Integer.MAX_VALUE;
    	int total = 0;
    	
    	for(int currentValue : terms.values())
    	{
    		if(currentValue > lastValue)
    		{
    			return -1;
    		}
    		
    		lastValue = currentValue;
    		total += currentValue;
    	}
    	return total;
    }
    
}
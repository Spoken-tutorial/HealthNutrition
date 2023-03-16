package com.health.controller;
import java.util.*; 

/*
 * To sort HashMapValue
 * Author: Alok Kumar
 */
public class HashMapSorting {

	
	
	/*public HashMap<Integer, String> sortValues(HashMap<Integer, String> map) {
		List list = new LinkedList(map.entrySet());
		
		Collections.sort(list, new Comparator() {
			
			public int compare(Object o1, Object o2) {
				return((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
			}
		});
		
		HashMap sortedHashMap= new LinkedHashMap<>();
		
		
		for(Iterator it=list.iterator(); it.hasNext();) {
			Map.Entry entry=(Map.Entry)it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		
		
		return sortedHashMap;
		
	}*/
	
	public  static HashMap<Integer, String> sortByValue(HashMap<Integer, String> hm)
	{
		// Create a list from elements of HashMap
		// Create a list from elements of HashMap
				List<Map.Entry<Integer, String> > list =
					new LinkedList<Map.Entry<Integer, String> >(hm.entrySet());

				// Sort the list
				Collections.sort(list, new Comparator<Map.Entry<Integer, String> >() {
					public int compare(Map.Entry<Integer, String> o1,
									Map.Entry<Integer, String> o2)
					{
						return (o1.getValue()).compareTo(o2.getValue());
					}
				});
				
				// put data from sorted list to hashmap
				HashMap<Integer, String> temp = new LinkedHashMap<Integer, String>();
				for (Map.Entry<Integer, String> aa : list) {
					temp.put(aa.getKey(), aa.getValue());
				}
				return temp;
	}
	
	
	
	
	public static HashMap<String, Integer> sortHashMapByValue(HashMap<String, Integer> hm)

	    {

	        // Create a list from elements of HashMap

	        List<Map.Entry<String, Integer> > list =

	               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
	 

	        // Sort the list

	        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {

	            public int compare(Map.Entry<String, Integer> o1, 

	                               Map.Entry<String, Integer> o2)

	            {

	                return (o1.getValue()).compareTo(o2.getValue());

	            }

	        });

	         

	        // put data from sorted list to hashmap 

	        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();

	        for (Map.Entry<String, Integer> aa : list) {

	            temp.put(aa.getKey(), aa.getValue());

	        }

	        return temp;

	    }
   
	
	

}

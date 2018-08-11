import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;


public class NameCollection {

//	private HashSet hash;
	private HashMap hashMap;
	
	NameCollection(){
		this.hashMap = new HashMap<String, PositionCollection>();
	}
	
	public void addPlace(Place plc) {
		if (checkName(plc.getName())) {
			getPlaces(plc.getName()).addPlace(plc);
		}
		else {
			PositionCollection newPC = new PositionCollection();
			newPC.addPlace(plc);
			hashMap.put(plc.getName(), newPC);
		}
	}
	
	public boolean checkName(String s) {
		if (hashMap.containsKey(s))
			return true;
		else
			return false;
	}
	
	
	public PositionCollection getPlaces(String s) {
		return (PositionCollection) hashMap.get(s);
	}
	
	public String getKeys() {
		return hashMap.keySet().toString();
	}
	
	public void removePlace(Place plc) {
		System.out.println("test");
		getPlaces(plc.getName()).removePlace(plc.getPosition());
		if (getPlaces(plc.getName()).getSize() == 0) {
			hashMap.remove(plc.getName());
		}
	}
	
	public int getSize() {
		return hashMap.size();
	}
	
	public String toString() {
		String s = "";
		Iterator iter = hashMap.entrySet().iterator();
		 while (iter.hasNext()) {
		    	Map.Entry current = (Map.Entry) iter.next();
		        s = s + current.toString() + "\n";
			}
		 s = s.substring(0, s.length() - 2);
		 return s;
	}
	
	public void clear() {
		hashMap.clear();
	}
	
	
	
}

import java.util.*;


public class PositionCollection {

	private HashMap hashMap;
	
	PositionCollection(){
		this.hashMap = new HashMap<Position, Place>();
	}
	
	public void addPlace(Place plc) {
		hashMap.put(plc.getPosition(), plc);
	}
	
	public boolean checkPosition(Position p) {
		if (hashMap.containsKey(p))
			return true;
		else
			return false;
	}
	
	
	public Place getPlace(Position p) {
		return (Place) hashMap.get(p);
	}
	
	public String getKeys() {
		return hashMap.keySet().toString();
	}
	
	public void removePlace(Position p) {
		hashMap.remove(p);
	}
	
	public int getSize() {
		return hashMap.size();
	}
	
	public HashMap getHashMap(){
		return this.hashMap;
	}
	
	public Iterator<Place> getIterator(){
		Iterator iter = hashMap.entrySet().iterator();
		return iter;
	}
	
	public void clear() {
		hashMap.clear();
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
}

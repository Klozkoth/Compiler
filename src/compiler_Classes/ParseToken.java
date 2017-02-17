package compiler_Classes;

public class ParseToken {

	int parseVal; //Integer value representing data
	String name; //String representing the id. Ex. my_num
	String type; //String representing type
	String data; //String containing what the name is
	String holder;
	String val;
	
	public ParseToken(int val, String str) {
		parseVal = val;
		name = str;
	}
	
	public ParseToken(int val, String str, String types) {
		parseVal = val;
		name = str;
		type = types;
	}
	
	public ParseToken() {
		// Default constructor
	}

	public int getVal() {
		return parseVal;
	}
	
	public String getName() {
		return name;
	}
	
	public void setType(String types) {
		type = types;
	}
	
	public void setData(String datum) {
		data = datum;
	}
}

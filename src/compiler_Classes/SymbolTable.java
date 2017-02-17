package compiler_Classes;

import java.util.*;

public class SymbolTable {
	static HashMap<String, ArrayList<Object>> table;

	public SymbolTable() {}
	
	/**
	 * Creates a new HashMap with the specified number of elements
	 * @param num
	 */
	public void CreateST(int num) {
		table = new HashMap<String, ArrayList<Object>>(num);
	}
	
	/**
	 * Inserts an element into the HashMap with the key of name
	 * and the value of everything contained in list. 
	 * @param name is used to lookup the token
	 * @param list contains all details about the token
	 */
	public void insert(String name, ArrayList<Object> list) {
		if(table.containsKey(name) == false)
			table.put(name, list);
	}
	
	/**
	 * Checks to see if a token is in the symbol table.
	 * @param name
	 * @return true if the token is contained, otherwise false
	 */
	public boolean lookup(String name) {
		if(table.containsKey(name) == true)
			return true;
		else
			return false;
	}
	
	/**
	 * Deletes the token from the HashMap
	 * @param name
	 */
	public void delete(String name) {
		table.remove(name);
	}
	
	/**
	 * Returns the value of the specified token
	 * @param name
	 * @return
	 */
	public Object returnValue(String name) {
		return table.get(name).get(1);
	}
	
	/**
	 * Returns the type of the specified token
	 * @param name
	 * @return
	 */
	public String returnType(String name) {
		return (String)table.get(name).get(0);
	}
	
	/**
	 * Returns the size of the specified token
	 * @param name
	 * @return
	 */
	public String returnSize(String name) {
		if(table.get(name).get(2).toString().equals("")) {
			return "100";
		}
		else {
			return (table.get(name).get(2)).toString();
		}
	}
	
	/**
	 * Sets the size value of the specified token
	 * @param name
	 * @param size
	 */
	public void setSize(String name, int size) {
		table.get(name).set(2, size);
	}
	
	/**
	 * Sets the type value of the specified token
	 * @param name
	 * @param size
	 */
	public void setType(String name, String type) {
		table.get(name).set(0, type);
	}
	
	/**
	 * Sets the value of the specified token
	 * @param name
	 * @param size
	 */
	public void setValue(String name, String value) {
		table.get(name).set(1, value);
	}
	
	public void printAll(String name) {
		System.out.print("Name is: "+name+", type is: "+returnType(name)+", value is: "+returnValue(name));
		System.out.println();
	}
}

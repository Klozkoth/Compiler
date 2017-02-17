package compiler_Classes;

public class Lookup {

	static Object[][] lookTable = { 
			
			//Reserved Words(terminals)
			{"BEGIN", 1}, {"BOOLEAN", 2}, {"CHAR", 3}, {"DECLARE", 4}, 
			{"DBMS_OUTPUT.PUT_LINE", 5}, {"DBMS_OUTPUT.PUT", 6}, {"DBMS_OUTPUT.NEW_LINE", 7}, {"END", 8}, 
			{"FALSE", 9}, {"IF", 10}, {"INT", 11}, {"LOOP", 12}, {"NOT", 13}, {"NULL", 14}, {"NUMBER", 15},
			{"POSITIVE", 16}, {"SMALLINT", 17}, {"THEN", 18}, {"TRUE", 19}, {"WHILE", 20},
			
			//Operators and other terminals
			{">", 21}, {">=", 22}, {"=", 23}, {"<=", 24}, {"<", 25}, {"<>", 26}, {"+", 27}, {"-", 28}, 
			{"*", 29}, {"/", 30}, {"MOD", 31}, {"id", 32}, {"num", 33}, {'c', 34}, {'e', 35}, {"(", 36}, {")", 37}, 
			{"$", 38}, {":=", 39}, {",", 40}, {"&", 41}, {";", 42},
			
			//Non-terminals
			{"block", 100}, {"declarations", 101}, {"declare_rest", 102}, {"default", 103}, {"data_type", 104}, 
			{"characters", 105}, {"size", 106}, {"size_option", 107}, {"numbers", 108},	{"compound_statement", 109}, 
			{"optional_statements", 110}, {"statement_list", 111}, {"statement", 112}, {"lefthandside", 113}, 
			{"righthandside", 114}, {"expression", 115}, {"simple_expression", 116},
			{"term", 117}, {"factor", 118}, {"relop", 119}, {"addop", 120}, {"mulop", 121}
			};
	
	/**
	 * Searches through the array for the entered word or operator. If it is not in the array, it then checks
	 * to see if the input follows the rules for num or id. If it does not fulfill any of these rules then the
	 * method returns -1 to denote an error in the input.
	 * 
	 * @param key
	 * @return An integer related to the passed in word. Otherwise return -1 to denote error.
	 */
	public static int search(String key) {
		for(int i = 0; i < lookTable.length; i++) {
			if(key.equals(lookTable[i][0])) {
				return (int) lookTable[i][1];	//If the key is found, return the int assigned to it
			}
		}
		if(key.toString().matches("-?\\d+")) {
			return (int) lookTable[32][1];		//if the key is a number, return the int assigned to num
		}
		if(key.toString().matches("[a-zA-Z][a-zA-Z0-9_]*")) {
			return (int) lookTable[31][1];		//If the key is an id, return the int assigned to id
			
		}
		if(key.toString().startsWith("'") && key.toString().endsWith("'")) {
			return (int) lookTable[33][1];		//If the key is a single char, return the int assigned to it.
		}
		return -1;		//Return -1 to denote error
	}
	
	public static String search(int val) {
		for(int i = 0; i < lookTable.length; i++) {
			if(val == (int)lookTable[i][1]) {
				return (lookTable[i][0]).toString();	//If the key is found, return the int assigned to it
			}
		}
		return "";
	}
}

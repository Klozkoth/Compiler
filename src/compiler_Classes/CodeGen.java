package compiler_Classes;

import java.util.*;

public class CodeGen {

	static int position = 0;
	static Stack<ParseToken> itemStack = new Stack<ParseToken>();
	static boolean check = false;
	static Stack<Node> bpStack = new Stack<Node>();

	/**
	 * This method handles assignment. It determines if the id is in the symbol table and if so, it sets the
	 * value of the id to the specified value. It then invokes the assignNode method to create the 
	 * intermediate code for mini and mice.
	 * 
	 * @param pt is the ArrayList that contains the value and the id that the value will be stored to
	 */
	public static void assignStatement(ArrayList<ParseToken> pt) {
		ParseToken id = itemStack.peek();
		ParseToken temp = id;
		// assigns the values to the id in the symbol table
		if (Parser.symbol.lookup(id.name) == true)
		{
			if(pt.get(0).type.equals("BOOLEAN") && Parser.symbol.returnType(id.name).equals("BOOLEAN")) {
				Parser.symbol.setValue(id.name, pt.get(0).val);
			}
			else if(pt.get(0).type.equals("CHAR") && Parser.symbol.returnType(id.name).equals("CHAR")) {
				Parser.symbol.setValue(id.name, pt.get(0).val);
			}
			else if((pt.get(0).type.equals("INT") || pt.get(0).type.equals("NUMBER") || 
					pt.get(0).type.equals("SMALLINT") || pt.get(0).type.equals("POSITIVE")) 
					&& !(Parser.symbol.returnType(id.name).equals("BOOLEAN") || Parser.symbol.returnType(id.name).equals("CHAR")))
			{
				int length = pt.get(0).val.length();
				if(pt.get(0).val.contains("-")) {
					length = length - 1;
				}
				
				if(length > Integer.parseInt(Parser.symbol.returnSize(id.name))){
					System.out.println("Error: Value greater than size allocated for token "+id.name);
					System.exit(5);
				}
				if(Integer.parseInt(pt.get(0).val) < 0) {
					if(Parser.symbol.returnType(id.name).equals("POSITIVE")) {
						System.out.println("Error: Cannot assign negative value to POSITIVE type");
						System.exit(5);
					}
					else {
						Parser.symbol.setValue(id.name, pt.get(0).val);
					}
				}
				if(length <= 4) {
					Parser.symbol.setValue(id.name, pt.get(0).val);
				}
				else if(length <= 8) {
					if(Parser.symbol.returnType(id.name).equals("SMALLINT")) {
						System.out.println("Error: Cannot assign value over 4 digits to SMALLINT type");
						System.exit(5);
					}
					else {
						Parser.symbol.setValue(id.name, pt.get(0).val);
					}
				}
				else {
					if(Parser.symbol.returnType(id.name).equals("SMALLINT") || Parser.symbol.returnType(id.name).equals("INT")) {
						System.out.println("Error: Cannot assign value over 8 digits to INT type ");
						System.exit(5);
					}
					else {
						Parser.symbol.setValue(id.name, pt.get(0).val);
					}
				}
			}
			else {
				System.out.println("Error: Type mismatch. Cannot assign "+pt.get(0).type+" to "+Parser.symbol.returnType(id.name));
				System.exit(5);
			}
			
		}
		ParseNode.assignNode(position, temp, pt.get(0));
		position++;
	}

	/**
	 * This method handles the multiplication type of operations for code generation.
	 * 
	 * @param pt is the ArrayList that contains all of the operators, operands, and the storage location
	 */
	public static void mulOpStatement(ArrayList<ParseToken> pt) {
		ParseToken id = itemStack.peek();
		int mohsen = 0;
		int temp = 0;
		if(id.type == null) {
			id.type = "";
		}
		if(pt.get(0).val.equals("TRUE") || pt.get(0).val.equals("FALSE")) {
			System.out.println("Error: Cannot assign BOOLEAN to a number");
			System.exit(5);
		}
		if(id.type.equals("BOOLEAN") || id.type.equals("CHAR")) {
			System.out.println("Error: Cannot assign number to a "+id.type);
			System.exit(5);
		}
		// Multiplication
		if (pt.get(1).val.equals("*")) {
			mohsen = 1;
			
			if (Parser.symbol.lookup(pt.get(0).val) == true && Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()) 
						* Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString()));
			} 
			else if (Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(pt.get(0).val) * 
						Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString()));
			} 
			else if (Parser.symbol.lookup(pt.get(0).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()) 
						* (Integer.parseInt(pt.get(2).val)));
			} 
			else {
				temp = (Integer.parseInt(pt.get(0).val) * 
						(Integer.parseInt(pt.get(2).val)));				
			}
		}
		
		// Division
		if (pt.get(1).val.equals("/")) 
		{
			mohsen = 2;
			if (Parser.symbol.lookup(pt.get(0).val) == true
					&& Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString())
						/ Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()));
			} 
			else if (Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString()) 
						/ Integer.parseInt(pt.get(0).val));
			} 
			else if (Parser.symbol.lookup(pt.get(0).val) == true) 
			{
				temp = (Integer.parseInt(pt.get(2).val) / 
						Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()));
			} 
			else 
			{
				temp = (Integer.parseInt(pt.get(2).val) / (Integer.parseInt(pt.get(0).val)));
			}
		}
		
		// Modulo
		if (pt.get(1).val.equals("MOD")) 
		{
			mohsen = 3;
			if (Parser.symbol.lookup(pt.get(0).val) == true
					&& Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString()) 
						% Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()));
			} 
			else if (Parser.symbol.lookup(pt.get(0).val) == true) 
			{
				temp = (Integer.parseInt(pt.get(2).val) 
						% Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()));
			} 
			else if (Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString()) 
						% (Integer.parseInt(pt.get(0).val)));
			} 
			else 
			{
				temp = (Integer.parseInt(pt.get(2).val) % (Integer.parseInt(pt.get(0).val)));
			}
		}
		
		//Type Checking
		int length = Integer.toString(temp).length();
		if(Integer.toString(temp).contains("-")) {
			length = length - 1;
		}
		
		if(length > Integer.parseInt(Parser.symbol.returnSize(id.name))){
			System.out.println("Error: Value greater than size allocated for token "+id.name);
			System.exit(5);
		}
		if((pt.get(0).type.equals("INT") || pt.get(0).type.equals("NUMBER") || 
				pt.get(0).type.equals("SMALLINT") || pt.get(0).type.equals("POSITIVE")) 
				&& !(Parser.symbol.returnType(id.name).equals("BOOLEAN") || Parser.symbol.returnType(id.name).equals("CHAR")))
		{
			if(temp < 0) {
				if(Parser.symbol.returnType(id.name).equals("POSITIVE")) {
					System.out.println("Error: Cannot assign negative value to POSITIVE type");
					System.exit(5);
				}
				else {
					id.val = String.valueOf(temp);
					Parser.symbol.setValue(id.name, id.val);
				}
			}
			if(length <= 4) {
				id.val = String.valueOf(temp);
				Parser.symbol.setValue(id.name, id.val);
			}
			else if(length <= 8) {
				if(Parser.symbol.returnType(id.name).equals("SMALLINT")) {
					System.out.println("Error: Cannot assign value over 4 digits to SMALLINT type");
					System.exit(5);
				}
				else {
					id.val = String.valueOf(temp);
					Parser.symbol.setValue(id.name, id.val);
				}
			}
			else {
				if(Parser.symbol.returnType(id.name).equals("SMALLINT") || Parser.symbol.returnType(id.name).equals("INT")) {
					System.out.println("Error: Cannot assign value over 8 digits to INT type ");
					System.exit(5);
				}
				else {
					id.val = String.valueOf(temp);
					Parser.symbol.setValue(id.name, id.val);
				}
			}
		}
		else {
			System.out.println("Error: Type mismatch. Cannot assign "+pt.get(0).type+" to "+Parser.symbol.returnType(id.name));
			System.exit(5);
		}
		
		ParseNode.mulNode(mohsen, position, id, pt);
		position++;
	}

	/**
	 * This method handles all of the addition type actions for code generation
	 * 
	 * @param pt is the ArrayList that contains all of the operators, operands, and the storage location
	 */
	public static void addOpStatement(ArrayList<ParseToken> pt) {
		ParseToken id = itemStack.peek();
		int chitty = 0;
		int temp = 0;
		if(id.type == null) {
			id.type = "";
		}
		if(pt.get(0).val.equals("TRUE") || pt.get(0).val.equals("FALSE")) {
			System.out.println("Error: Cannot assign BOOLEAN to a number");
			System.exit(5);
		}
		if(id.type.equals("BOOLEAN") || id.type.equals("CHAR")) {
			System.out.println("Error: Cannot assign number to a "+id.type);
			System.exit(5);
		}
		//Addition
		if (pt.get(1).val.equals("+")) {
			chitty = 1;
			
			if (Parser.symbol.lookup(pt.get(0).val) == true && Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()) 
						+ Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString()));
			} 
			else if (Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(pt.get(0).val) + 
						Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString()));
			} 
			else if (Parser.symbol.lookup(pt.get(0).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()) 
						+ (Integer.parseInt(pt.get(2).val)));
			} 
			else {
				temp = (Integer.parseInt(pt.get(0).val) + 
						(Integer.parseInt(pt.get(2).val)));				
			}
		}
		
		// Subtraction
		if (pt.get(1).val.equals("-")) 
		{
			chitty = 2;
			if (Parser.symbol.lookup(pt.get(0).val) == true
					&& Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString())
						- Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()));
			} 
			else if (Parser.symbol.lookup(pt.get(2).val) == true) 
			{
				temp = (Integer.parseInt(Parser.symbol.returnValue((pt.get(2).val)).toString()) 
						- Integer.parseInt(pt.get(0).val));
			} 
			else if (Parser.symbol.lookup(pt.get(0).val) == true) 
			{
				temp = (Integer.parseInt(pt.get(2).val) -
						Integer.parseInt(Parser.symbol.returnValue((pt.get(0).val)).toString()));
			} 
			else 
			{
				temp = (Integer.parseInt(pt.get(2).val) / (Integer.parseInt(pt.get(0).val)));
			}
		}
		
		//Type Checking
		
		int length = Integer.toString(temp).length();
		if(Integer.toString(temp).contains("-")) {
			length = length - 1;
		}
		
		if(length > Integer.parseInt(Parser.symbol.returnSize(id.name))){
			System.out.println("Error: Value greater than size allocated for token "+id.name);
			System.exit(5);
		}
		if((pt.get(0).type.equals("INT") || pt.get(0).type.equals("NUMBER") || 
				pt.get(0).type.equals("SMALLINT") || pt.get(0).type.equals("POSITIVE")) 
				&& !(Parser.symbol.returnType(id.name).equals("BOOLEAN") || Parser.symbol.returnType(id.name).equals("CHAR")))
		{
			
			if(temp < 0) {
				if(Parser.symbol.returnType(id.name).equals("POSITIVE")) {
					System.out.println("Error: Cannot assign negative value to POSITIVE type");
					System.exit(5);
				}
				else {
					id.val = String.valueOf(temp);
					Parser.symbol.setValue(id.name, id.val);
				}
			}
			if(length <= 4) {
				id.val = String.valueOf(temp);
				Parser.symbol.setValue(id.name, id.val);
			}
			else if(length <= 8) {
				if(Parser.symbol.returnType(id.name).equals("SMALLINT")) {
					System.out.println("Error: Cannot assign value over 4 digits to SMALLINT type");
					System.exit(5);
				}
				else {
					id.val = String.valueOf(temp);
					Parser.symbol.setValue(id.name, id.val);
				}
			}
			else {
				if(Parser.symbol.returnType(id.name).equals("SMALLINT") || Parser.symbol.returnType(id.name).equals("INT")) {
					System.out.println("Error: Cannot assign value over 8 digits to INT type ");
					System.exit(5);
				}
				else {
					id.val = String.valueOf(temp);
					Parser.symbol.setValue(id.name, id.val);
				}
			}
		}
		else {
			System.out.println("Error: Type mismatch. Cannot assign "+pt.get(0).type+" to "+Parser.symbol.returnType(id.name));
			System.exit(5);
		}

		ParseNode.addNode(chitty, position, id, pt);
		position++;
	}
	
	/**
	 * Handles the relational operations for IF statements and WHILE loops
	 * 
	 * @param pt contains the items being compared and the operator
	 */
	public static void relOpStatement(ArrayList<ParseToken> pt) {
		Node bp = new Node();
		bp.position = position;
		int comp1;
		int comp2;

		if(Parser.symbol.lookup(pt.get(2).val) && Parser.symbol.lookup(pt.get(0).val)) 
		{
			comp1 = Integer.parseInt(Parser.symbol.returnValue(pt.get(2).val).toString());
			comp2 = Integer.parseInt(Parser.symbol.returnValue(pt.get(0).val).toString());
			
			bp.firstValue = pt.get(2).val;
			bp.secondValue = pt.get(0).val;
		}
		else if(Parser.symbol.lookup(pt.get(0).val)) {
			
			comp1 = Integer.parseInt(pt.get(2).val);
			comp2 = Integer.parseInt(Parser.symbol.returnValue(pt.get(0).val).toString());
			
			bp.firstValue = comp1;
			bp.secondValue = pt.get(0).val;
		}
		else if (Parser.symbol.lookup(pt.get(2).val)) {
			
			if(Parser.symbol.returnType(pt.get(2).val).equals("BOOLEAN"))
			{
				bp.firstValue = pt.get(2).val;
				if(pt.get(0).val.equals("TRUE"))
				{
					bp.secondValue = "#100"; //TRUE = 100
				}
				else {
					bp.secondValue = "#101"; //FALSE = 101
				}
				
			}
			
			else
			{
				comp1 = Integer.parseInt(Parser.symbol.returnValue(pt.get(2).val).toString());
				comp2 = Integer.parseInt(pt.get(0).val);
				
				bp.firstValue = pt.get(2).val;
				bp.secondValue = "#" +comp2;
			}
		}
		else {
			comp1 = Integer.parseInt(pt.get(2).val);
			comp2 = Integer.parseInt(pt.get(0).val);
			
			bp.firstValue = comp1;
			bp.secondValue = comp2;
		}
		
		if (pt.get(1).val.equals(">")) 
		{
			check = true;
			bp.operationJump = "JLE";
			position++;
		}
		if (pt.get(1).val.equals(">=")) 
		{
				check = true;
				bp.operationJump = "JLT";
				position++;

		}
		if (pt.get(1).val.equals("=")) 
		{
				check = true;
				bp.operationJump = "JNE";
				position++;

		}
		if (pt.get(1).val.equals("<=")) 
		{
				check = true;
				bp.operationJump = "JGT";
				position++;

		}
		if (pt.get(1).val.equals("<")) 
		{
				check = true;
				bp.operationJump = "JGE";
				position++;

		}
		if (pt.get(1).val.equals("<>")) 
		{
				check = true;
				bp.operationJump = "JEQ";
				position++;

		}
		bpStack.push(bp);
	}
}

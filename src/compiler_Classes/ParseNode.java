package compiler_Classes;

import java.io.*;
import java.util.*;

public class ParseNode {

	static ArrayList<Node> output = new ArrayList<Node>();
	
	
	/**
	 * Stores values from one ParseToken into a Node that is then added onto the output
	 * ArrayList.
	 * 
	 * @param pos is the line number of the assign statement
	 * @param x is the ParseToken that is getting items stored into it
	 * @param y is the ParseToken whose data is being stored
	 */
	public static void assignNode(int pos, ParseToken x, ParseToken y) {
		Node assignment = new Node();
		assignment.operationJump = "STO";
		assignment.position = pos;
		if(Parser.symbol.lookup(y.val)==true)
		{
			assignment.firstValue = y.val;
		}
		
		else {
			if(y.type.equals("CHAR"))
			{
				assignment.firstValue = "#" + (int)y.val.toCharArray()[1];
			}
			
			else if(y.type.equals("BOOLEAN"))
			{
				if(y.val.equals("TRUE"))
				{
					assignment.firstValue = "#100"; //TRUE = 100
				}
				
				else if(y.val.equals("FALSE")) {
					assignment.firstValue = "#-100"; //FALSE = -100
				}
				
				else {
					assignment.firstValue = "#102"; //NULL = 102
				}
				
			}
			
			else{
				assignment.firstValue = "#" + y.val;
			}
			
		}
		assignment.secondValue = "";
		assignment.jumpToThirdValue = x.name;
		
		output.add(assignment);
	}
	
	/**
	 * Creates a node that contains all relevant output to be run in mini and mice.
	 * The node is then inserted into the output ArrayList. This method deals with 
	 * Multiplication, division, and modulo.
	 * 
	 * @param op determines which operation is happening
	 * @param pos is the line position for the output file
	 * @param assign is the node that the data will be assigned to
	 * @param list is the ArrayList containing the operations and operands
	 */
	public static void mulNode(int op, int pos, ParseToken assign, ArrayList<ParseToken> list)
	{
		Node mulOpOutput = new Node();
		if(op == 1)
		{
			mulOpOutput.operationJump = "MUL";
		}
		
		if(op == 2)
		{
			mulOpOutput.operationJump = "DIV";
		}
		
		if(op == 3)
		{
			mulOpOutput.operationJump = "MOD";
		}
		mulOpOutput.position = pos;
		
		if(Parser.symbol.lookup(list.get(2).val)==true 
				&& Parser.symbol.lookup(list.get(0).val)==true)
		{
			mulOpOutput.firstValue = list.get(2).val;
			mulOpOutput.secondValue = list.get(0).val;
		}
		
		else if(Parser.symbol.lookup(list.get(2).val)==true)
		{
			mulOpOutput.firstValue = list.get(2).val;
			mulOpOutput.secondValue = "#" + list.get(0).val;
		}
		
		else if(Parser.symbol.lookup(list.get(0).val)==true)
		{
			mulOpOutput.firstValue = "#" + list.get(2).val;
			mulOpOutput.secondValue = list.get(0).val;
		}
		
		else {
			mulOpOutput.firstValue = "#" + list.get(2).val;
			mulOpOutput.secondValue = "#" + list.get(0).val;
		}
		
		mulOpOutput.jumpToThirdValue = assign.name;
		
		output.add(mulOpOutput);
	}
	
	/**
	 * Creates a node that contains all relevant output to be run in mini and mice.
	 * The node is then inserted into the output ArrayList. This method deals with addition and subtraction.
	 * 
	 * @param op determines which operation is happening
	 * @param pos is the line position for the output file
	 * @param assign is the node that the data will be assigned to
	 * @param list is the ArrayList containing the operations and operands
	 */
	public static void addNode(int op, int pos, ParseToken assign, ArrayList<ParseToken> list)
	{
		Node addOpOutput = new Node();
		if(op == 1)
		{
			addOpOutput.operationJump = "ADD";
		}
		
		if(op == 2)
		{
			addOpOutput.operationJump = "SUB";
		}
		
		addOpOutput.position = pos;
		
		if(Parser.symbol.lookup(list.get(2).val)==true 
				&& Parser.symbol.lookup(list.get(0).val)==true)
		{
			addOpOutput.firstValue = list.get(2).val;
			addOpOutput.secondValue = list.get(0).val;
		}
		
		else if(Parser.symbol.lookup(list.get(2).val)==true)
		{
			addOpOutput.firstValue = list.get(2).val;
			addOpOutput.secondValue = "#" + list.get(0).val;
		}
		
		else if(Parser.symbol.lookup(list.get(0).val)==true)
		{
			addOpOutput.firstValue = "#" + list.get(2).val;
			addOpOutput.secondValue = list.get(0).val;
		}
		
		else {
			addOpOutput.firstValue = "#" + list.get(2).val;
			addOpOutput.secondValue = "#" + list.get(0).val;
		}
		
		addOpOutput.jumpToThirdValue = assign.name;
		
		output.add(addOpOutput);
	}
	
	/**
	 * Sorts all of the nodes in the ArrayList output by their line number positions.
	 * It then creates a file in the mini folder that contains all of the output from the nodes.
	 */
	public static void printOut() {
		int j;
	    boolean flag = true;
	    Node temp;

	    while(flag)
	    {
	    	flag = false;
	        for(j = 0; j < output.size()-1; j++)
	        {
	        	if ( output.get(j).position > output.get(j+1).position )
	            {
	        		temp = output.get(j);
	        		output.set(j, output.get(j+1) );
                    output.set(j+1, temp);
	                flag = true;  
	            } 
	        } 
	    }
	    PrintWriter writer;
		try {
			
			writer = new PrintWriter("output.txt", "UTF-8");
			while(!(output.isEmpty())) {
		    	writer.println(output.get(0).position+" " + output.get(0).operationJump+" "+output.get(0).firstValue
		    			+", "+output.get(0).secondValue+", "+output.get(0).jumpToThirdValue);
		    	output.remove(0);
		    	
		    }
		    writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}

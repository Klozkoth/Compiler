package compiler_Classes;

import java.util.*;

public class Parser {

	static Scanner scan = new Scanner();
	static SymbolTable symbol = new SymbolTable();
	static String input = "";
	static Stack<Object> stack = new Stack<Object>();
	static Stack<ParseToken> tokenStack = new Stack<ParseToken>();
	static int i = 0;
	static boolean accept = false;
	static boolean error = false;
	static boolean id_dec = false;
	static boolean dec_check = true;
	static boolean conditional = false;
	static boolean assign = true;
	static ArrayList<ParseToken> pTokens = new ArrayList<ParseToken>();
	static boolean operation = false;
	static boolean not = false;
	
	public static void main(String args[]) {
		String type = "";
		stack.push(0);
		symbol.CreateST(10);
		
		while(!(scan.input.isEmpty())) {
			input = scan.input.get(0);
			int inputNum = Lookup.search(input);
			scan.input.remove(0);
			if((inputNum == 32) && (scan.input.get(0).equals("BOOLEAN") || scan.input.get(0).equals("INT") || scan.input.get(0).equals("SMALLINT")
					|| scan.input.get(0).equals("POSITIVE") || scan.input.get(0).equals("CHAR"))) {
				if(input.length() > 20) {
					System.out.println("Error: identifier" +input+" exceeds size");
					return;
				}
				type = scan.input.get(0);
				ParseToken pt = new ParseToken(inputNum, input, type);
				pt.setData("id");
				pTokens.add(pt);
			}
			else if(inputNum == 33) {
				if(pTokens.get(pTokens.size()-1).val.equals("-") && (pTokens.get(pTokens.size()-2).val.equals(":=") 
																	|| pTokens.get(pTokens.size()-2).val.equals("-"))) {
					input = "-"+input;
					pTokens.remove(pTokens.size()-1);
				}
				type = "NUMBER";
				ParseToken pt = new ParseToken(inputNum, input, type);
				pt.setData("num");
				pt.val = input;
				pTokens.add(pt);
			}
			else if(inputNum == 34) {
				if(input.length() > 3) {
					System.out.println("Error: Characters may only contain one letter");
					return;
				}
				type = "CHAR";
				ParseToken pt = new ParseToken(inputNum, input, type);
				pt.val = input;
				pTokens.add(pt);
			}
			else if(inputNum == 9 || inputNum == 14 || inputNum == 19) {
				type = "BOOLEAN";
				ParseToken pt = new ParseToken(inputNum, input, type);
				pt.val = input;
				pTokens.add(pt);
			}
			else {
				ParseToken pt = new ParseToken(inputNum, input);
				pt.val = input;
				pTokens.add(pt);
			}
		}
		parse();
		Node idk = new Node();
		idk.operationJump = "HLT";
		idk.firstValue = "";
		idk.secondValue = "";
		idk.jumpToThirdValue = "";
		idk.position = CodeGen.position;
		ParseNode.output.add(idk);
		
		ParseNode.printOut();
	}

	public static void parse() {

		int inputNum = pTokens.get(i).getVal();
		input = pTokens.get(i).getName();
		
		if (inputNum == 4) {
			id_dec = true;
		}
		
		i++;
		int action = 0;

		//Parse until either accepting state or error
		while ((accept != true) && (error != true)) {
			action = ParseTables.searchSR((int) stack.peek(), inputNum);

			if (action == 110) {
				System.out.println("Accept!");
				accept = true;
				return;
			}

			if (action == -110) {
				System.out.println("Error: General syntax error on: "+input);
				error = true;
				return;
			}

			// Shift
			if (action > 0) {
				stack.push(inputNum);
				stack.push(action);
				if(i <= pTokens.size()) {
					tokenStack.push(pTokens.get(i-1));
				}
				
				if(inputNum == 39) {
					assign = false;
				}
				
				if(inputNum == 42 || inputNum == 8) {
					assign = true;
				}
				
				if(inputNum == 8 && Lookup.search(pTokens.get(i).getName()) == 10) {
					Node temp = CodeGen.bpStack.pop();
					temp.jumpToThirdValue = "#"+(CodeGen.position);
					ParseNode.output.add(temp);
					operation = false;
				}
				
				if(inputNum == 8 && Lookup.search(pTokens.get(i).getName()) == 12) {
					if(CodeGen.bpStack.isEmpty()) {
						System.out.println("Error: Unable to backpatch. Conditionals need an operator statement.");
						System.exit(5);
					}
					Node temp = CodeGen.bpStack.pop();
					Node whileJump = new Node();
					whileJump.jumpToThirdValue = "#"+(temp.position);
					whileJump.operationJump = "JMP";
					whileJump.position = CodeGen.position;
					whileJump.firstValue = "";
					whileJump.secondValue = "";
					ParseNode.output.add(whileJump);
					
					temp.jumpToThirdValue = "#"+(CodeGen.position+1);
					ParseNode.output.add(temp);
					
					CodeGen.position++;
				}
				
				//If the input is an id and assignment isn't happening, push the id token onto the code gen stack
				if(inputNum == 32 && assign == true) {					
					CodeGen.itemStack.push(pTokens.get(i-1));
				}

				if (i == pTokens.size()) {
					inputNum = -1;
				} else {
					input = pTokens.get(i).getName();

					inputNum = Lookup.search(input);
					
					if(inputNum == 10 || inputNum == 20) {
						conditional = true;
					}
					if(inputNum == 12 || inputNum == 18) {
						conditional = false;
					}
					
					if(inputNum == 1) {
						id_dec = false;
					}
					if (inputNum == 32 && conditional == false) {
						tokenize(input);
						if(dec_check == false) {
							error = true;
							System.out.println("Error! Unable to declare id outside of Declare block!\n"+"Incorrect input is: "+input);
							return;
						}
					}
					i++;
				}
			}

			// Reduce
			int j = 0;
			if (action < 0) {
				ParseToken tempToken = new ParseToken();
				
				ArrayList<ParseToken> genArray = new ArrayList<ParseToken>();
				int tempAction = (action * -1) - 1;
				for (j = 0; j < GrammarLookup.grammarTable[tempAction][1]; j++) {
					stack.pop();
					
					//Temp token stores the top token of the stack and then that token is popped into the genArray
					tempToken = tokenStack.peek();
					genArray.add(tokenStack.pop());
					
					if ((int) stack.pop() != GrammarLookup.grammarTable[tempAction][j + 2]) {
						System.out.println("Error: In the reduction on token "+input);
						error = true;
						return;
					}
				}
				//Operators (mul, rel, add)
				if(tempAction == 40) {
					CodeGen.mulOpStatement(genArray);
					operation = true;
				}
				if(tempAction == 38) {
					CodeGen.addOpStatement(genArray);
					operation = true;
				}
				if(tempAction == 36) {
					ParseToken relToken = new ParseToken();
					relToken.val = "=";
					genArray.add(relToken);
					
					relToken.val = "TRUE";
					genArray.add(relToken);
					CodeGen.relOpStatement(genArray);
				}
				
				//Assignment (:=) 
				if(tempAction == 5 || tempAction == 32) {
					if(operation == false && not == false) {
						CodeGen.assignStatement(genArray);
					}
					operation = false;
					not = false;
				}
				
				//User input
				if(tempAction == 29)
				{
					Node questionNode = new Node();
					questionNode.firstValue = "#-2";
					questionNode.secondValue = "#63";
					questionNode.jumpToThirdValue = "";
					questionNode.operationJump = "SYS";
					questionNode.position = CodeGen.position;
					CodeGen.position++;
					ParseNode.output.add(questionNode);
					
					Node readNode = new Node();
					readNode.operationJump = "SYS";
					if(symbol.returnType((genArray.get(0).name)).equals("CHAR"))
					{
						readNode.firstValue = "#2";
					}
					
					else {
						readNode.firstValue = "#1";
					}
					readNode.secondValue = "";
					readNode.jumpToThirdValue = genArray.get(0).name;
					readNode.position = CodeGen.position;
					CodeGen.position++;
					ParseNode.output.add(readNode);
					
					operation = false;
				}
				
				//NOT-ing 
				if(tempAction == 46)
				{
					Node notNode = new Node();
					notNode.firstValue = "";
					notNode.secondValue = "";
					notNode.jumpToThirdValue = genArray.get(0).val;
					notNode.operationJump = "NEG";
					notNode.position = CodeGen.position;
					CodeGen.position++;
					ParseNode.output.add(notNode);
					not = true;
				}
				
				//DBMS_OUTPUT.STATEMENTS
				if(tempAction == 26 || tempAction == 27 || tempAction == 28) {
					int outputType = 0;
					if(tempAction == 26 || tempAction == 27) {
						String pt = symbol.returnType(genArray.get(1).name);
						if(pt.equals("CHAR")) {
						outputType = 2;
						}
						else {
							outputType = 1;
						}
						Node output = new Node();
						output.jumpToThirdValue = "";
						output.operationJump = "SYS";
						output.position = CodeGen.position;
						output.firstValue = "#-"+outputType;
						output.secondValue = genArray.get(1).name;
						ParseNode.output.add(output);
						CodeGen.position++;
						
					}
					
					if(tempAction == 26 || tempAction == 28) {
						Node output = new Node();
						output.jumpToThirdValue = "";
						output.operationJump = "SYS";
						output.position = CodeGen.position;
						output.firstValue = "#0";
						output.secondValue = "";
						ParseNode.output.add(output);
						CodeGen.position++;
					}
				}
				
				int temp = (int) stack.peek();
				if (j == 0) {
					stack.push(GrammarLookup.grammarTable[tempAction][j + 3]);
					
					//A new parse token is created using the name and values that the reduction caused. Ex. The token was num, 33, with a value of 25.
					//The reduction then set it to factor, 118, 25. This also ensure the type is consistent throughout reduction. It then pushes the 
					//New parse token onto the tokenStack.
					ParseToken pt = new ParseToken((int)stack.peek(), Lookup.search((int)stack.peek()));
					pt.type = tempToken.type;
					pt.data = tempToken.data;
					pt.val = tempToken.val;
					tokenStack.push(pt);
					
					stack.push(ParseTables.searchGT(temp, (int) stack.peek()));
				} 
				else {
					stack.push(GrammarLookup.grammarTable[tempAction][j + 2]);
					
					//A new parse token is created using the name and values that the reduction caused. Ex. The token was num, 33, with a value of 25.
					//The reduction then set it to factor, 118, 25. This also ensure the type is consistent throughout reduction. It then pushes the 
					//New parse token onto the tokenStack.
					ParseToken pt = new ParseToken((int)stack.peek(), Lookup.search((int)stack.peek()));
					pt.type = tempToken.type;
					pt.data = tempToken.data;
					pt.val = tempToken.val;
					tokenStack.push(pt);
					
					stack.push(ParseTables.searchGT(temp, (int) stack.peek()));
				}
			}
		}
	}
	
	/**
	 * Adds identifiers to the symbol table.
	 * @param input is the name of the identifier to be inserted.
	 */
	private static void tokenize(String input) {
		ArrayList<Object> iden = new ArrayList<Object>();
		for(int i = 0; i < 4; i++) {
			iden.add(i, "");
		}
		
		if(id_dec == true) {
				if(pTokens.get(i+1).getName().equals("BOOLEAN") || pTokens.get(i+1).getName().equals("INT") || pTokens.get(i+1).getName().equals("SMALLINT")
					|| pTokens.get(i+1).getName().equals("POSITIVE") || pTokens.get(i+1).getName().equals("CHAR")) {
						iden.set(0, pTokens.get(i+1).getName());
						if(pTokens.get(i+2).val.equals("(") && pTokens.get(i+4).val.equals(")")) {
							iden.set(2, Integer.parseInt(pTokens.get(i+3).val));
							if(pTokens.get(i+1).getName().equals("SMALLINT") && Integer.parseInt(pTokens.get(i+3).val) > 4) {
								System.out.println("Error: Cannot set size of SMALLINT above 4");
								System.exit(5);
							}
							if(pTokens.get(i+1).getName().equals("INT") && Integer.parseInt(pTokens.get(i+3).val) > 8) {
								System.out.println("Error: Cannot set size of INT above 8");
								System.exit(5);
							}
						}
						else {
							iden.set(2, "");
						}
					}
			symbol.insert(input, iden);
		}
		
		if(id_dec == false) {
			if(!(symbol.lookup(input))) {
				dec_check = false;
				return;
			}
		}
	}
}
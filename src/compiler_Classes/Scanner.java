package compiler_Classes;

import java.io.*;
import java.util.*;

public class Scanner {

	ArrayList<String> input = new ArrayList<String>();
	
	public Scanner() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("scan.txt"));
			String line;
			boolean multLineComment = false;
			while ((line = br.readLine()) != null) {
				String token = "";
				char[] characters = line.toCharArray();
				for(int i = 0; i < characters.length; i++) {

					//Whitespace
					if(Character.isWhitespace(characters[i])) {
						if(token != "")
							input.add(token);
						token = "";
					}

					//Single line comments
					else if(Character.toString(characters[i]).matches("-") && Character.toString(characters[i+1]).matches("-")) {
						break;
					}

					//Multiple line comments
					else if(Character.toString(characters[i]).equals("/") && Character.toString(characters[i+1]).equals("*")) {
						multLineComment = true;
						break;
					}
					else if(Character.toString(characters[i]).equals("*") && multLineComment == true) {
						break;
					}

					//Single char
					else if(Character.toString(characters[i]).matches("'")) {
						if(token != "") {
							input.add(token);
							token = "";
						}
						token += characters[i];
						do {
							i++;
							token += characters[i];
						} while(!(Character.toString(characters[i]).equals("'")));
						input.add(token);
						token = "";
					}
					
					//:= operator
					else if(Character.toString(characters[i]).matches(":") && Character.toString(characters[i+1]).matches("=")) {
						if(token != "") {
							input.add(token);
							token = "";
						}
						token += characters[i];
						token += characters[i+1];
						i++;
						input.add(token);
						token = "";
					}
					
					//>= operator
					else if(Character.toString(characters[i]).matches(">") && Character.toString(characters[i+1]).matches("=")) {
						if(token != "") {
							input.add(token);
							token = "";
						}
						token += characters[i];
						token += characters[i+1];
						i++;
						input.add(token);
						token = "";
					}
					
					//<= operator
					else if(Character.toString(characters[i]).matches("<") && Character.toString(characters[i+1]).matches("=")) {
						if(token != "") {
							input.add(token);
							token = "";
						}
						token += characters[i];
						token += characters[i+1];
						i++;
						input.add(token);
						token = "";
					}
					
					//<> operator
					else if(Character.toString(characters[i]).matches("<") && Character.toString(characters[i+1]).matches(">")) {
						if(token != "") {
							input.add(token);
							token = "";
						}
						token += characters[i];
						token += characters[i+1];
						i++;
						input.add(token);
						token = "";
					}

					//Alphanumeric characters
					else if(Character.toString(characters[i]).matches("[a-zA-Z_0-9]")) {
						token += characters[i];
						if(i+1 == characters.length)
							input.add(token);
						multLineComment = false;
					}

					//Output statements
					else if(Character.toString(characters[i]).matches(".")) {
						if(token.equalsIgnoreCase("DBMS_OUTPUT")) {
							token += characters[i];
						}
						else {
							if(token != "") {
								input.add(token);
								token = "";
							}
							token += characters[i];
							input.add(token);
							token = "";
						}
					}
				}
			}
			br.close();
		}
		catch(IOException e) {
			System.out.println("File not found!");
			System.exit(5);
		}
	}
}
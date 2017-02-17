package compiler_Classes;

public class ParseTables {
	
	static int[][] goToSparce = {
			{0, 100, 2}, {0, 101, 3}, {1, 102, 5}, {3, 109, 8}, {4, 104, 15},
			{4, 105, 16}, {4, 108, 17}, {7, 109, 26}, {7, 110, 27}, {7, 111, 28},
			{7, 112, 29}, {7, 113, 30}, {15, 103, 37}, {23, 115, 48}, {23, 116, 49}, 
			{23, 117, 50}, {23, 118, 51}, {24, 115, 52}, {24, 116, 49}, {24, 117, 50}, 
			{24, 118, 51}, {32, 106, 57}, {33, 106, 58}, {34, 106, 59}, {35, 106, 60},
			{36, 115, 63}, {36, 116, 49}, {36, 117, 50}, {36, 118, 51}, {36, 114, 62},
			{38, 115, 63}, {38, 116, 49}, {38, 117, 50}, {38, 118, 51}, {38, 114, 65},
			{46, 118, 68}, {49, 119, 78}, {49, 120, 79}, {50, 121, 83}, {55, 109, 26}, 
			{55, 112, 86}, {55, 113, 30}, {56, 107, 88}, {64, 102, 93}, {69, 109, 26}, 
			{69, 112, 96}, {69, 113, 30}, {78, 116, 97}, {78, 117, 50}, {78, 118, 51}, 
			{79, 117, 98}, {79, 118, 51}, {83, 118, 99}, {84, 109, 26}, {84, 112, 100}, 
			{84, 113, 30}, {97, 120, 79}, {98, 121, 83}
	};
	
	static int[][] shift_Red = {
			
			{0, -1, -3}, {0, 4, 1}, 
			{1, -1, -5}, {1, 32, 4}, 
			{2, -1, 6},
			{3, 1, 7}, {3, -1, -110},
			{4, 2, 10}, {4, 3, 11}, {4, 11, 12}, {4, 15, 14}, {4, 16, 9}, {4, 17, 13}, {4, -1, -110},
			{5, -1, -2}, 
			{6, -1, 110}, 
			{7, 1, 7}, {7, 5, 20}, {7, 6, 21}, {7, 7, 22}, {7, 10, 23}, {7, 14, 25}, 
			{7, 20, 24}, {7, 32, 18}, {7, 41, 19}, {7, -1, -110},
			{8, 38, 31}, {8, -1, -110},
			{9, 36, 32}, {9, -1, -110}, 
			{10, -1, -10},
			{11, -1, -11},
			{12, 36, 33}, {12, -1, -110},
			{13, 36, 34}, {13, -1, -110},
			{14, 36, 35}, {14, -1, -110},
			{15, -1, -7}, {15, 39, 36},
			{16, -1, -8},
			{17, -1, -9}, 		
			{18, 39, 38}, {18, -1, -110},
			{19, 32, 39}, {19, -1, -110},
			{20, -1, -110}, {20, 36, 40},
			{21, 36, 41}, {21, -1, -110},
			{22, -1, -29},
			{23, 9, 45}, {23, 13, 46}, {23, 14, 47}, {23, 19, 44}, {23, 32, 42}, {23, 33, 43}, {23, -1, -110},
			{24, 9, 45}, {24, 13, 46}, {24, 14, 47}, {24, 19, 44}, {24, 32, 42}, {24, 33, 43}, {24, -1, -110},
			{25, 42, 53}, {25, -1, -110},
			{26, -1, -26},
			{27, 8, 54}, {27, -1, -110},
			{28, -1, -22}, {28, 42, 55},
			{29, -1, -23},
			{30, -1, -25},
			{31, -1, -1},
			{32, -1, -13}, {32, 33, 56},
			{33, -1, -13}, {33, 33, 56},
			{34, -1, -13}, {34, 33, 56},
			{35, -1, -13}, {35, 33, 56},
			{36, 9, 45}, {36, 13, 46}, {36, 14, 47}, {36, 19, 44}, {36, 32, 42}, {36, 33, 43}, {36, 34, 61}, {36, -1, -110},
			{37, 42, 64}, {37, -1, -110},
			{38, 9, 45}, {38, 13, 46}, {38, 14, 47}, {38, 19, 44}, {38, 32, 42}, {38, 33, 43}, {38, 34, 61}, {38, -1, -110},
			{39, -1, -30},
			{40, -1, -110},	{40, 32, 66}, 		
			{41, 32, 67}, {41, -1, -110},
			{42, -1, -42},
			{43, -1, -43},
			{44, -1, -44},
			{45, -1, -45},			
			{46, 9, 45}, {46, 13, 46}, {46, 14, 47}, {46, 19, 44}, {46, 32, 42}, {46, 33, 43}, {46, -1, -110},
			{47, -1 , -46},
			{48, -1, -110}, {48, 18, 69},
			{49, -1, -36}, {49, 21, 70}, {49, 22, 73}, {49, 23, 71}, {49, 24, 74}, {49, 25, 72}, {49, 26, 75}, {49, 27, 76}, {49, 28, 77}, 
			{50, -1, -38}, {50, 29, 80}, {50, 30, 81}, {50, 31, 82},		
			{51, -1, -40},
			{52, 12, 84}, {52, -1, -110},
			{53, -1, -21},			
			{54, 42, 85}, {54, -1, -110},
			{55, 1, 7}, {55, 5, 20}, {55, 6, 21}, {55, 7, 22}, {55, 10, 23}, {55, 20, 24},{55, 32, 18}, {55, 41, 19}, {55, -1, -110},
			{56, -1, -15}, {56, 40, 87},			
			{57, 37, 89}, {57, -1, -110},
			{58, 37, 90}, {58, -1, -110},
			{59, 37, 91}, {59, -1, -110},
			{60, 37, 92}, {60, -1, -110},
			{61, -1, -35}, 
			{62, -1, -6},
			{63, -1, -34},
			{64, -1, -5}, {64, 32, 4},			
			{65, -1, -33},
			{66, 37, 94}, {66, -1, -110},
			{67, -1, -110},	{67, 37, 95}, 	
			{68, -1, -47},
			{69, -1, -110}, {69, 1, 7}, {69, 5, 20}, {69, 6, 21}, {69, 7, 22}, {69, 10, 23}, {69, 20, 24}, {69, 32, 18}, {69, 41, 19}, 
			{70, -1, -48},
			{71, -1, -50},
			{72, -1, -52}, 
			{73, -1, -49},
			{74, -1, -51},
			{75, -1, -53},
			{76, -1, -54},			
			{77, -1, -55},
			{78, 9, 45}, {78, 13, 46}, {78, 14, 47}, {78, 19, 44}, {78, 32, 42}, {78, 33, 43}, {78, -1, -110},
			{79, 9, 45}, {79, 13, 46}, {79, 14, 47}, {79, 19, 44}, {79, 32, 42}, {79, 33, 43}, {79, -1, -110},
			{80, -1, -56},
			{81, -1, -57},			
			{82, -1, -58},
			{83, 9, 45}, {83, 13, 46}, {83, 14, 47}, {83, 19, 44}, {83, 32, 42}, {83, 33, 43}, {83, -1, -110},
			{84, -1, -110}, {84, 1, 7}, {84, 5, 20}, {84, 6, 21}, {84, 7, 22}, {84, 10, 23}, {84, 20, 24}, {84, 32, 18}, {84, 41, 19},
			{85, -1, -20},
			{86, -1, -24},			
			{87, 33, 102}, {87, -1, -110},
			{88, -1, -12},
			{89, -1, -19},
			{90, -1, -17},
			{91, -1, -18},
			{92, -1, -16},
			{93, -1, -4},
			{94, -1, -27},
			{95, -1, -28},
			{96, -1, 99}, {96, 8, 102},
			{97, -1, -37}, {97, 27, 76}, {97, 28, 77},
			{98, -1, -39}, {98, 29, 80}, {98, 30, 81}, {98, 31, 82}, 
			{99, -1, -41},
			{100, -1, -110}, {100, 8, 103},
			{101, -1, -14},
			{102, 10, 104}, {102, -1, 99},
			{103, 12, 105}, {103, -1, 99},
			{104, -1, -31},
			{105, -1, -32}
	};
	
	public static int searchSR(int stackTop, int input) {
		for(int i = 0; i < shift_Red.length; i++) {
			if(stackTop == shift_Red[i][0] && input == shift_Red[i][1]) {
				return shift_Red[i][2];	//If the key is found, return the int assigned to it
			}
		}
		for(int i = 0; i < shift_Red.length; i++) {
			if((stackTop == shift_Red[i][0]) && (-1 == shift_Red[i][1])) {
				return shift_Red[i][2];	//If the key is found, return the int assigned to it
			}
		}
		return -110; //Return error
	}
	
	
	public static int searchGT(int stackTop, int input) {
		for(int i = 0; i < 57; i++) {
			if(stackTop == goToSparce[i][0] && input == goToSparce[i][1]) {
				return goToSparce[i][2];	//If the key is found, return the int assigned to it
			}
		}
		return -110; //Return error
	}
}
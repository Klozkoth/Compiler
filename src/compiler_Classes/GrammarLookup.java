package compiler_Classes;

public class GrammarLookup {

	static int[][] grammarTable = { 
			{0, 3, 38, 109, 101, 100}, {1, 2, 102, 4, 101},
			{2, 0, 35, 101}, {3, 5, 102, 42, 103, 104, 32, 102}, {4, 0, 35, 102},
			{5, 2, 114, 39, 103}, {6, 0, 35, 103}, {7, 1, 105, 104}, {8, 1, 108, 104},
			{9, 1, 2, 104}, {10, 1, 3, 105}, {11, 2, 107, 33, 106}, {12, 0, 35, 106},
			{13, 2, 33, 40, 107}, {14, 0, 35, 107}, {15, 4, 37, 106, 36, 15, 108},
			{16, 4, 37, 106, 36, 11, 108}, {17, 4, 37, 106, 36, 17, 108}, {18, 4, 37, 106, 36, 16, 108},
			{19, 4, 42, 8, 110, 1, 109}, {20, 2, 42, 14, 110}, {21, 1, 111, 110}, {22, 1, 112, 111},
			{23, 3, 112, 42, 111, 111}, {24, 1, 113, 112}, {25, 1, 109, 112}, {26, 4, 37, 32, 36, 5, 112},
			{27, 4, 37, 32, 36, 6, 112}, {28, 1, 7, 112}, {29, 2, 32, 41, 112}, 
			{30, 6, 10, 8, 112, 18, 115, 10, 112}, {31, 6, 12, 8, 112, 12, 115, 20, 112}, 
			{32, 3, 114, 39, 32, 113}, {33, 1, 115, 114}, {34, 1, 34, 114}, {35, 1, 116, 115},
			{36, 3, 116, 119, 116, 115}, {37, 1, 117, 116}, {38, 3, 117, 120, 116, 116}, {39, 1, 118, 117},
			{40, 3, 118, 121, 117, 117}, {41, 1, 32, 118}, {42, 1, 33, 118}, {43, 1, 19, 118}, 
			{44, 1, 9, 118}, {45, 1, 14, 118}, {46, 2, 118, 13, 118}, {47, 1, 21, 119}, {48, 1, 22, 119},
			{49, 1, 23, 119}, {50, 1, 24, 119}, {51, 1, 25, 119}, {52, 1, 26, 119}, {53, 1, 27, 120},
			{54, 1, 28, 120}, {55, 1, 29, 121}, {56, 1, 30, 121}, {57, 1, 31, 121}
	};
}
import java.util.ArrayList;
import java.util.Scanner;

public class LZ77 {
	
	public static void compress() {
		
		ArrayList<Tag> tags = new ArrayList<Tag>();				
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the string you wish to compress.");
		String s = scanner.next();
		scanner.close();
		
		for(int i = 0; i < s.length(); i++) {
			
			// Converting a character to a string
			String temp = Character.toString(s.charAt(i));

			for(int j = i; j < s.length(); j++) {
				
				if(s.substring(0, i).contains(temp)) {
					
					if(j == s.length() - 1) {
						
							for(int k = i - 1; k >= 0; k--) {
								
								if(s.substring(k, i).contains(temp.substring(0, temp.length() - 1))) {
									Tag tag = new Tag();
									tag.position = i - k;
									tag.length = temp.length() - 1;
									tag.charAscii = (int) s.charAt(j);
									tags.add(tag);
									i = j;
									break;
								}
								else {
									continue;
								}
							}	
					}
					else {
						temp += Character.toString(s.charAt(j+1));
					}
									
				}
				else { 
					if(temp.length() == 1) {
						Tag tag = new Tag();
						tag.position = 0;
						tag.length = 0;
						tag.charAscii = (int) s.charAt(j); 
						tags.add(tag);
						break;
					}
					else if(temp.length() > 1) {
						String existedString = temp.substring(0, temp.length() - 1);
						
						// for loop to get the position
						for(int k = i - 1; k >= 0; k--) {
							
							if(s.substring(k, i).contains(existedString)) {
								Tag tag = new Tag();
								tag.position = i - k;
								tag.length = temp.length() - 1;
								tag.charAscii = (int) s.charAt(j);
								tags.add(tag);
								break;
							}
							else {
								continue;
							}
						}
						i = j;
						break;
					}	
				}			
			}			
		}
		
		System.out.println("Tags");
		for(int i = 0; i < tags.size(); i++) {
			System.out.println(i + 1 + "- <" + tags.get(i).position + ", " + tags.get(i).length + ", " + tags.get(i).charAscii + ">");
		}
		
		System.out.println("\nOriginal Size = " + s.length() + " Symbols * 8 Bits/Symbol = " + s.length() * 8 + " Bits");
		
	}
	
	static void decompress() {
		int position = 0;
		int length = 0;
		int ascii = 0;
		int index = 0;
		String text = "";
		System.out.println("How many tags do you wish to enter?");
		Scanner scanner = new Scanner(System.in);
		int tagsNumber = scanner.nextInt();
		System.out.println("Insert each tag in a new line with its contents space seperated in this order (Position, Length, Ascii)");
		for (int i = 0; i < tagsNumber; i++) {
			position = scanner.nextInt();
			length = scanner.nextInt();
			ascii = scanner.nextInt();
			
			index = text.length() - position;
			text += text.substring(index, index + length);
			text += Character.toString((char)ascii);
		}
		scanner.close();
		System.out.println(text);
	}
	
	

	public static void main(String[] args) {
		
		System.out.println("Enter 1 for compression or 2 for decompression");
		Scanner scanner = new Scanner(System.in);
		if (scanner.nextInt() == 1) {
			compress();
		}
		else if (scanner.nextInt() == 2) {
			decompress();
		}
		scanner.close();
	}
}

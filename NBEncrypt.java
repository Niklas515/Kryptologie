/*
 * Java program to encrypt message
 */
import javax.swing.JOptionPane;
//import java.util.*;

public class NBEncrypt {  
	public static void main(String[] args) {
		//input Klartext
		String klartext = "DIESISTEINBEISPIEL";
		do {
			klartext = JOptionPane.showInputDialog("Geben Sie eine Nachricht ein", klartext);	
		} while (klartext.matches(".*[^A-Z].*")); 	//wiederhole solange Buchstaben außer A-Z gefunden werden

		//input Passwort
		String passwort = "CODE";
		do {
			passwort = JOptionPane.showInputDialog("Geben Sie ein Passwort ein", passwort);	
		} while (passwort.matches(".*[^A-Z].*")); 	//wiederhole solange Buchstaben außer A-Z gefunden werden

		//erweitere Klartext auf Vielfaches des Passworts
		int t=klartext.length()/passwort.length();
		int r=klartext.length()-t*passwort.length();
		//System.out.println(klartext.length()+" / "+passwort.length()+" = "+t+" Rest "+r+"\n");		
		if (r>0){
			for(int i = 0;i<(passwort.length()-r);i++) {
				klartext = klartext + "X";
			}
		}
		//System.out.println(klartext);
		
		
		/* 
		Verschluesselung nach Transpositionsverfahren
        =============================================
		*/
		//ermittle Reihenfolge aus Passwort
		int[] arrOrderInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			for(int j=0; j<passwort.length(); j++) {
				if(passwort.charAt(i)>passwort.charAt(j)) {				// der größere Buchstabe rutscht nach hinten 
					arrOrderInt[i] = arrOrderInt[i] + 1;
				} else if(passwort.charAt(i)==passwort.charAt(j)) {     // bei gleichen Buchstaben ... 
					if(i<j) {											// ... rutscht der vordere nach hinten	
						arrOrderInt[i] = arrOrderInt[i] + 1;
						}
				}
					//System.out.println("i: "+i+" "+arrOrderInt[i]);
				
				}
			}
			
		//transpose Klartext 
		int a=0, j=0, k=0;
		char c;
		String codetext="", q="";
		for(int i = 0;i<klartext.length();i++) {
			//transpose letter
			c = klartext.charAt(k * passwort.length() + arrOrderInt[j]);
			System.out.println(i+" "+c+" "+k+" "+arrOrderInt[j]); 	
			q = Character.toString(c);
			
			codetext = codetext + c;									// schreibe Codetext
			if(j<(passwort.length()-1)) {j=j+1;} else {j=0;k=k+1;}
		}
		
		System.out.println("\n"+"Codetext nach TV: " + codetext+"\n");	
		
		
		/* 
		Verschluesselung nach Substitutionsverfahren
		============================================
		*/
		//erstelle Zuordnungstabelle
		int[] arrMarkupInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			c = passwort.charAt(i);
			q = Character.toString(passwort.charAt(i));
			arrMarkupInt[i] = ((int) c)-64;								// Buchstabennummer Passwort
			//System.out.println("i: "+i+" "+arrMarkupInt[i]);
		}
		
		//substitute Klartext
		a=0; j=0;
		klartext = codetext;											// switch TV+SV/SV
		codetext = "";
		for(int i = 0;i<klartext.length();i++) {
			c = klartext.charAt(i);
			q = Character.toString(klartext.charAt(i));
			a = ((int) c)-64;											// Buchstabennummer Klartext
			//System.out.println("i: "+i+" "+a);
			
			//substitute Buchstabe
			if(a + arrMarkupInt[j]>26) {
				a = a + arrMarkupInt[j]-26;								// Plus Buchstabennummer Passwort
				} else {
				a = a + arrMarkupInt[j];
				}
			c = (char) (a+64);
						
			codetext = codetext + c;									// schreibe Codetext
			System.out.println(klartext.charAt(i)+" + "+
				passwort.charAt(j)+" = " +c);
			if(j<(passwort.length()-1)) {j=j+1;} else {j=0;}
		}
		System.out.println("\n"+"Codetext nach SV: " + codetext+"\n");	

		//output Codetext
		codetext = JOptionPane.showInputDialog("Die verschluesselte Nachricht lautet ", codetext);		
		System.exit(0);	
	}
   
	
}

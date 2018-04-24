/*
 * Java program to decrypt message
 */
import javax.swing.JOptionPane;

public class NBDecrypt {  
	public static void main(String[] args) {
		//input Codetext
		String codetext = "GHMJLTWYLTRGLXWUHMPC";
		do {
			codetext = JOptionPane.showInputDialog("Geben Sie eine Nachricht ein", codetext);	
		} while (codetext.matches(".*[^A-Z].*")); 	//wiederhole solange Buchstaben außer A-Z gefunden werden

		//input Passwort
		String passwort = "CODE";
		do {
			passwort = JOptionPane.showInputDialog("Geben Sie ein Passwort ein", passwort);	
		} while (passwort.matches(".*[^A-Z].*")); 	//wiederhole solange Buchstaben außer A-Z gefunden werden

		
		/* 
		Entschluesselung nach Substitutionsverfahren
		============================================
		*/		
		//erstelle Zuordnungstabelle
		char c;
		String q="";
		int[] arrMarkupInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			c = passwort.charAt(i);
			q = Character.toString(passwort.charAt(i));
			arrMarkupInt[i] = ((int) c)-64;								// Buchstabennummer Passwort
			//System.out.println("i: "+i+" "+arrMarkupInt[i]);
		}	
		
		//substitute Codetext
		int a=0, j=0;
		String klartext="";
		for(int i = 0;i<codetext.length();i++) {
			c = codetext.charAt(i);
			q = Character.toString(codetext.charAt(i));
			a = ((int) c)-64;											// Buchstabennummer Codetext	

			//substitute Buchstabe
			if(a<arrMarkupInt[j]){
				a = a - arrMarkupInt[j]+26;								// Minus Buchstabennummer Passwort
				} else {
				a = a - arrMarkupInt[j];
				}
			c = (char) (a+64);
			
			klartext = klartext + c;									// schreibe Klartext
			System.out.println(codetext.charAt(i)+" - "+
				passwort.charAt(j)+" = " +c);
			if(j<(passwort.length()-1)) {j=j+1;} else {j=0;}
		}
		System.out.println("\n"+"Klartext nach SV: " + klartext+"\n");	

		
		/* 
		Entschluesselung nach Transpositionsverfahren
        =============================================
		*/
		//ermittle Reihenfolge aus Passwort
		int[] arrOrderInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			for(j=0; j<passwort.length(); j++) {
				if(passwort.charAt(i)>passwort.charAt(j)) {				// der größere Buchstabe rutscht nach hinten 
					arrOrderInt[i] = arrOrderInt[i] + 1;
				} else if(passwort.charAt(i)==passwort.charAt(j)) {     // bei gleichen Buchstaben ... 
					if(i<j) {											// ... rutscht der vordere nach hinten	
						arrOrderInt[i] = arrOrderInt[i] + 1;
						}
				}
					System.out.println("i: "+i+" "+arrOrderInt[i]);
				}
			}
		
		//Umkehrung der Reihenfolge aus Passwort
		int[] arrReverseOrderInt = new int[passwort.length()];
		for(int i=0; i<passwort.length(); i++) {
			arrReverseOrderInt[arrOrderInt[i]] = i;
		}		
		
		//transpose Codetext 
		codetext = klartext;
		klartext=""; q="";
		int k=0; j=0;
		for(int i = 0;i<codetext.length();i++) {
			//transpose letter
			c = codetext.charAt(k * passwort.length() + arrReverseOrderInt[j]);
			q = Character.toString(c);
			//System.out.println(i+" "+c+" "+k+" "+arrReverseOrderInt[j]); 	
			
			klartext = klartext + c;									// schreibe Klartext
			if(j<(passwort.length()-1)) {j=j+1;} else {j=0;k=k+1;}
		}
		System.out.println("\n"+"Klartext nach TV: " + klartext+"\n");	
				
		
		//entferne Platzhalter 
		for(int i = klartext.length()-1;i>(klartext.length()-passwort.length());i--) {
			c = klartext.charAt(i);
			q = Character.toString(c);
			if (q.equals("X")) {										// check Platzhalter
				//System.out.println(klartext+" cut "+q);	
				klartext = klartext.substring(0, klartext.length() - 1);
			}
		}
		//System.out.println("\n"+"Klartext final: " + klartext+"\n");	
	
		
		//output Klartext
		klartext = JOptionPane.showInputDialog("Die entschluesselte Nachricht lautet ", klartext);	
		System.exit(0);	
	}
   
}

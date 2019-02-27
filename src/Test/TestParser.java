package Test;

import java.util.StringTokenizer;

import org.wit.rpt.swrl.IndividualPropertyAtom;

public class TestParser {
	public static void main(String[] args) {

		String name = "lift_hasstate_free";
		String content = "hasstate(   lift,   free)";
		IndividualPropertyAtom ipa = parseripa(content);
        String propertys = ipa.getpropertyPredicate();
        String argu1 = ipa.getArgument1();
        String argu2 = ipa.getArgument2();
        System.out.println(propertys+","+argu1+","+argu2);
	}
	
	public static IndividualPropertyAtom parseripa(String txt){
            String[] a=txt.split("\\(|\\,|\\)");
            String c = a[0];
            String propertyPre=DelChar(c);
    		String b=a[1];
    		String arg1=DelChar(b);
    		String d=a[2];
    		String arg2=DelChar(d);
    	        System.out.println(arg1);
    	        System.out.println(propertyPre);
    	        System.out.println(arg2);
    	        String IPAtomname=arg1+"_"+propertyPre+"_"+arg2;
    		IndividualPropertyAtom ipa = new IndividualPropertyAtom(IPAtomname,propertyPre,arg1,arg2);
    		return ipa;
		
	}
	public static String DelChar(String c){
		  StringTokenizer st1 = new StringTokenizer(c);
	        String str ="";
	        while (st1.hasMoreTokens()) {
	          str = str + st1.nextToken(" ");
	        }
	        str = str.trim();
		return str;
	}

}

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GenClassfromString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		FileReader fr = null;
		String FILENAME = "F:/workspace/zd/GenerateClasses/src/CopyBook.txt";
		String array[][] =  new String[300][3];
		List<MethodNames> l = new ArrayList<MethodNames>();
		try {
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));
			int i =0;
			while ((sCurrentLine = br.readLine()) != null) {
			//	System.out.println(sCurrentLine);	
				sCurrentLine= sCurrentLine.replaceAll("\\s", "");
				if(sCurrentLine.contains("REDEFINE")){
					continue;
				}
				MethodNames m = new MethodNames();
				try {
					String lev = sCurrentLine.substring(0, sCurrentLine.indexOf("#"));
					try {
						int level = Integer.parseInt(lev);
						m.setLevel(level);
						
					}catch(NumberFormatException e) {
						e.printStackTrace();
					}
					
			//	if(sCurrentLine.substring(0, 1).equals("1")||sCurrentLine.substring(0, 2).equals("01")) {
					String s = sCurrentLine.substring(sCurrentLine.indexOf("#")+1, sCurrentLine.indexOf("("));
					// remove special character //
					 Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
				        Matcher match= pt.matcher(s);
				        while(match.find())
				        {
				            String r= match.group();
				        s=s.replaceAll("\\"+r, "");
				        }
					
					String n = sCurrentLine.substring(sCurrentLine.indexOf("(")+1, sCurrentLine.indexOf(")"));
					
					m.setMethodVariable(s);
					m.setMethodLength(n.substring(1,n.length()));
					l.add(m);	
				
				
				}
				catch(Exception e) {
					
				}  
				 i++;
			}
			for(MethodNames e :l) {
				System.out.println(e.getMethodVariable() + " " + e.getMethodLength());
			}
			
			generateBeanClass(l);
			generateSubstringClass(l);
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void generateBeanClass(List<MethodNames> l ){
		// write to file .java //

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String content = "public class CopyBean{";

			fw = new FileWriter("F:/workspace/zd/GenerateClasses/src/CopyBean.java");
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.write("\n");
			bw.write("\n");
			// write the variable names //
			// set the current as null
			MethodNames c= null;
			for(MethodNames e :l) {
				if(c!=null) {
				int temp1 = c.getLevel();
				int temp2 = e.getLevel();
				if(temp1>=temp2) {
				String temp = "private String " +c.getMethodVariable() + ";";
				bw.write(temp);
				bw.write("\n");
				}
				}
				c=e;
			} 
			// write the last value 
			if(c!=null) {		
				String temp = "private String " +c.getMethodVariable() + ";";
				bw.write(temp);
				bw.write("\n");
			}
			
			
			// setter methods //
			// setting c = null ;
			c =null;
			bw.write("\n");
			for(MethodNames e :l) {
				if(c!=null) {
					int temp1 = c.getLevel();
					int temp2 = e.getLevel();
					if(temp1>=temp2) {
						String temp = " public void set" +c.getMethodVariable().substring(0,1).toUpperCase() +
								c.getMethodVariable().substring(1,c.getMethodVariable().length()) + "( String "+ c.getMethodVariable()+"){";
						bw.write(temp);
						bw.write("\n");
						temp = " \t this." + c.getMethodVariable() + " = " + c.getMethodVariable()+";";
						bw.write(temp);
						
						bw.write("\n");
						bw.write("\t}");
						bw.write("\n");
					}
				}
				c=e;
			}
			
			if(c!=null) {
				String temp = " public void set" +c.getMethodVariable().substring(0,1).toUpperCase() +
						c.getMethodVariable().substring(1,c.getMethodVariable().length()) + "( String "+ c.getMethodVariable()+"){";
				bw.write(temp);
				bw.write("\n");
				temp = " \t this." + c.getMethodVariable() + " = " + c.getMethodVariable()+";";
				bw.write(temp);
				
				bw.write("\n");
				bw.write("\t}");
				bw.write("\n");
			}
			bw.write("\n");
			
			// getter methods //
			// set c as null
			c=null;
			bw.write("\n");
			for(MethodNames e :l) {
				if(c!=null) {
					int temp1 = c.getLevel();
					int temp2 = e.getLevel();
					if(temp1>=temp2) {
						String temp = "  public String get" +c.getMethodVariable().substring(0,1).toUpperCase() +
								c.getMethodVariable().substring(1,c.getMethodVariable().length()) + "(){";
						bw.write(temp);
						bw.write("\n");
						temp = "\t return " + c.getMethodVariable() + ";";
						bw.write(temp);
						
						bw.write("\n");
						bw.write("\t }");
						bw.write("\n");
					}
				}
					c=e;
			}
				
				if(c!=null) {
					String temp = "  public String get" +c.getMethodVariable().substring(0,1).toUpperCase() +
							c.getMethodVariable().substring(1,c.getMethodVariable().length()) + "(){";
					bw.write(temp);
					bw.write("\n");
					temp = "\t return " + c.getMethodVariable() + ";";
					bw.write(temp);
					
					bw.write("\n");
					bw.write("\t }");
					bw.write("\n");
				}
			
			bw.write("\n");
					
			bw.write("}");
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public static void generateSubstringClass(List<MethodNames> l ){
		// write to file .java //

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String content = "public class Substring{";

			fw = new FileWriter("F:/workspace/zd/GenerateClasses/src/Substring.java");
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.write("\n");
			bw.write("\n");
			
			// write the methofd Name //
			String methodName = "public static CopyBean genSubstring(String in) { ";
			bw.write(methodName);
			bw.write("\n");
			bw.write("\n");
			
			 methodName = "CopyBean c = new CopyBean(); ";
			bw.write(methodName);
			bw.write("\n");
			
			// substring methods //
			int i = 0; 
			int j = 0 ;
			bw.write("\n");
			MethodNames c = null;
			for(MethodNames e :l) {
				if(c!=null) {
					if(c.getLevel()>=e.getLevel()) {
					j =i+ Integer.parseInt(c.getMethodLength());
					
					String temp = "  c.set" +c.getMethodVariable().substring(0,1).toUpperCase() +
							c.getMethodVariable().substring(1,c.getMethodVariable().length()) + 
							"( in.substring(" + i +","+ j +"));";
					i = j;
					bw.write(temp);
					bw.write("\n");
				}
				}
				c=e;
				
			}
			
			if(c!=null) {
				j =i+ Integer.parseInt(c.getMethodLength());
				
				String temp = "  c.set" +c.getMethodVariable().substring(0,1).toUpperCase() +
						c.getMethodVariable().substring(1,c.getMethodVariable().length()) + 
						"( in.substring(" + i +","+ j +"));";
				i = j;
				bw.write(temp);
				bw.write("\n");
			}
			
			bw.write("\n");
			bw.write("return c;");
			bw.write("\n");
			bw.write("\t}");		
			bw.write("\n");
			bw.write("}");
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

}
